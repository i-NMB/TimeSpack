const searchParams = new URLSearchParams(window.location.search);
// console.log("http://127.0.0.1:6066/api/article/detail?id="+searchParams.get('id'));   // 输出: id
if(searchParams.get('id')==null||searchParams.get('id')==""){
	$('main').remove();
			$(".uk-hidden").html(`<div class="uk-alert-danger" uk-alert>
	    <h3>查询文章错误</h3>
	    <p>请求的文章不存在 404 NotFound</p>
	</div>`);
			$(".uk-hidden").removeClass("uk-hidden");
}
fetchData(apiLinks.Article_Detail_Link + '?id=' + searchParams.get('id')).then(res => {
    if(res.data.title == "查询的文章数据为空"){
		$('main').remove();
		$(".uk-hidden").html(`<div class="uk-alert-danger" uk-alert>
    <h3>查询文章错误</h3>
    <p>请求的文章不存在 404 NotFound</p>
</div>`);
		
		$(".uk-hidden").removeClass("uk-hidden");
	}
}).catch(error => {
    console.error('请求出现问题：', error);
});
async function initplaintext() {
    // try {
    var getPlaintext = await get_article(apiLinks.Article_Detail_Link + '?id=' + searchParams.get('id'));///article/detail?id=1
    var commentNumber = await get_article(apiLinks.Comment_Number_Link + '?articleId=' + searchParams.get('id'));
        const app = Vue.createApp({
            data() {
                return {
                    plain_text: getPlaintext.data,
					userName: '',
					categoryName: '',
					commentNum: commentNumber.data,
					like_state: false,
                }
            },
			async created() {
				this.userName = await this.getUserName(); // 在组件创建后立即获取用户名
				this.categoryName = await this.getCategoryName();
			},
			methods: {
				markdownToHtml(markdown) {
					// 使用 marked 将 Markdown 转换为 HTML
					return marked.parse(markdown);
			    },
				async getUserName() {
                    var user = await get_article(apiLinks.User_Get_Link + '?userId=' + getPlaintext.data.createUser);//获取文章发布者的昵称
					return user.data.nickname;
				},
				async getCategoryName() {
                    var category = await get_article(apiLinks.Category_Detail_Link + '?id=' + getPlaintext.data.categoryId);
					return category.data.categoryName;
				},
				async like(likeID){
					try {
                        const response = await fetch(apiLinks.Article_Like_Link + `?id=${likeID}`);
						if (response.status==401) {
							showPop(`点赞失败，未登录`, "error");
							this.like_state = false;
							throw new Error(`未登录`);
						}else if(!response.ok){
							showPop(`点赞失败 ${response.status}`, "error");
							this.like_state = false;
							throw new Error(`网络请求错误! 状态: ${response.status}`);
						}
						const data = await response.json();
						if (data.code === 0) {
							showPop("点赞成功","success");
							this.like_state = true;
						} else {
							showPop(`点赞失败，${data.message}`, "error");
							console.error(data.message);
							this.like_state = false;
						}
					} catch (error) {
						console.error('There was a problem with the fetch operation:', error);
					}
				}
			},
			computed: {
				userNameHtml() {
					// 确保 userName 是一个字符串
					return this.userName ? this.userName : '';
				},
				categoryNameHtml() {
					// 确保 userName 是一个字符串
					return this.categoryName ? this.categoryName : '';
				},
				updateTimeHtml(){
					return this.plain_text.createTime==this.plain_text.updateTime ? '' : '最后更新于'+this.plain_text.updateTime;
				}
			}
        });
        app.mount('#plaintext');
    // } catch (error) {
    //     console.error('Error:', error);
    // }
}

initplaintext();

async function initcomment(){
	const App = {
	        data() {
	            return {
	                currentPage: 1,
	                pageSize: 5,
	                total: 0,
	                currentPageData: [], // 直接存储当前页面的数据
					usersInfo: [],
					like_state: false,
	            };
	        },
	        computed: {
	            totalPages() {
	                return Math.ceil(this.total / this.pageSize);
	            },
	        },
	        methods: {
	            async fetchData(page) {
	                try {
                        const response = await fetch(apiLinks.Comment_Get_By_Paging_Link + `?auditingState=true&pageNum=${page}&pageSize=${this.pageSize}&articleId=${searchParams.get('id')}`);
	                    if (!response.ok) {
	                        throw new Error(`网络请求错误! status: ${response.status}`);
	                    }
	                    const data = await response.json();
	
	                    if (data.code === 0) {
	                        this.total = data.data.total;
	                        this.currentPageData = data.data.items; // 直接更新当前页面的数据
							const items = data.data.items;
							// 存储用户信息的Promise数组
							const userPromises = items.map(item => this.getUserInfo(item.createUser));
							// 等待所有用户信息请求完成
							this.usersInfo = await Promise.all(userPromises);
	                    } else {
							showPop(`评论分页失败，服务器返回：${data.message}`, "error");
	                        console.error("从服务器获取评论分页失败："+data.message);
	                    }
	                } catch (error) {
						showPop(`评论分页连接时出现问题：${error}`, "error");
	                    console.error('评论分页连接时出现问题：', error);
	                }
	            },
				async getUserInfo(userId) {
					try {
                        const response = await fetch(apiLinks.User_Get_Link + `?userId=${userId}`);
						if (!response.ok) {
							throw new Error(`网络请求错误! status: ${response.status}`);
						}
						const data = await response.json();

						if (data.code === 0) {
							return data.data;
						} else {
							showPop(`获取评论用户信息失败，服务器返回：${data.message}`, "error");
							console.error(data.message);
						}
					} catch (error) {
						showPop(`获取评论用户信息链接时出现问题：${error}`, "error");
	                    console.error('获取评论用户信息连接时出现问题：', error);
					}
				},
	            goToPage(page) {
	                if (page >= 1 && page <= this.totalPages) {
	                    this.currentPage = page;
	                    this.fetchData(page);
	                }
	            },
				gotoCommenting(){
					// window.location.hash = "#commenting";
					$("#commenting")[0].scrollIntoView({behavior: "smooth"});
				},
				async like(likeID){
					try {
                        const response = await fetch(apiLinks.Comment_Like_Link + `?id=${likeID}`);
						if (response.status==401) {
							showPop(`评论点赞失败，未登录`, "error");
							this.like_state = false;
							throw new Error(`未登录`);
						}else if(!response.ok){
							showPop(`评论点赞失败 ${response.status}`, "error");
							this.like_state = false;
							throw new Error(`评论点赞网络请求错误! status: ${response.status}`);
						}
						const data = await response.json();
						if (data.code === 0) {
							showPop("评论点赞成功","success");
							this.fetchData(this.currentPage);
						} else {
							showPop(`评论点赞失败，${data.message}`, "error");
							console.error(data.message);
							this.like_state = false;
						}
					} catch (error) {
						console.error('评论点赞操作连接时出现问题:', error);
					}
				}
	        },
	        mounted() {
	            this.fetchData(this.currentPage);
	        },
	    };
	    Vue.createApp(App).mount('#comments');
}
initcomment();

function beComment(){
	var data = {
	    content: $("#writecomment").val(),
		articleId: searchParams.get('id'),
	};
	$.ajax({
	    type: "POST",
        url: apiLinks.Comment_Add_Link,
	    contentType: "application/x-www-form-urlencoded", // 设置contentType
	    data: $.param(data), // 使用$.param方法将数据转换为键值对字符串
	    success: function(response) {
			if(response.code!=0){
				showPop(response.message,"error");
				return;
			}else if(response.code==0){
				showPop("评论成功","success");
				location.reload();
				// UIkit.notification({message: '<span uk-icon=\'icon: check\'></span> 操作成功', status: 'success', pos: 'top-center'});
				return;
			}
	    },
	    error: function(jqXHR, textStatus) {
			if(jqXHR.status===401){
				showPop("评论失败，请登录后刷新页面再试", "error");
				return;
			}
			showPop("请求失败", "error");
			showPop("错误状态："+textStatus+" "+ jqXHR.status, "error");
			// UIkit.notification({message: errorThrown, status: 'danger', pos: 'top-center'});
	        // 请求失败时的回调函数
	        console.error("登录失败原因：", "状态："+textStatus, "状态码："+jqXHR.status);
	    }
	});
}