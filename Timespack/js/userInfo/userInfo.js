async function userinfo() {
    try {
        const app = Vue.createApp({
            data() {
                return {
                    user: {},
					yiyan: {},
					currentPage: 1,
					pageSize: 5,
					total: 0,
					currentPageData: [],
					
					currentPageAdmin: 1,
					pageSizeAdmin: 5,
					totalAdmin: 0,
					currentPageDataAdmin: [],
					
					commentCurrentPage: 1,
					commentPageSize: 10,
					commentTotal: 0,
					commentCurrentPageData: [],
					articleTitles: [],
					commentAdminCurrentPage: 1,
					commentAdminPageSize: 10,
					commentAdminTotal: 0,
					commentAdminCurrentPageData: [],
					articleAdminTitles:[],
					allViewByArt: 0,
					allLikeByArt: 0,
					allLikeByCom: 0,
					allArt: 0,
					allCom: 0,
					maxLikeArt: {},
					maxViewArt: {},
					maxLikeCom: {},
					
                }
            },
			async created() {
				this.user = await this.getUserName(); // 在组件创建后立即获取用户实体
				this.yiyan = await this.getyiyan();
				await this.getViewByArt();
				await this.getLikeByArt();
				await this.getLikeByCom();
				await this.getAllArt();
				await this.getAllCom();
				await this.getMaxLikeArt();
				await this.getMaxViewArt();
				await this.getMaxLikeCom();
			},
			methods: {
				async getUserName() {
                    var user = await get_info(apiLinks.User_Information_Link);//http://127.0.0.1:6066/api/user/userInfo、../user.json
					return user.data;
				},
				async getyiyan() {
                    var yiyan = await get_info(apiLinks.yiyan_Link);
					return yiyan.data;
				},
				async patchNickname() {
					if($("#nickname").val()==this.user.nickname){
						showPop("新旧信息一致","warning");
						showPop("信息未修改","warning");
						return;
					}
                    var backinfo = await patch_info(apiLinks.Update_User_Nickname_Link, {"nickname": $("#nickname").val()});
					if(backinfo.code==0){
						showPop("信息修改成功","success");
						this.user = await this.getUserName();
					}else{
						showPop("信息修改失败","error");
						showPop("原因："+backinfo.message,"warning");
					}
				},
				async sendNewMail() {
					if($("#newMail").val()==this.user.email){
						showPop("邮箱未修改","error");
						showPop("新邮箱与旧邮箱一致，无需发送邮件验证邮箱","warning");
						return;
					}
					showPop("邮箱发送中","success");
                    var backinfo = await post_info(apiLinks.Email_Sending_API_Link, {"email": $("#newMail").val()});
					if(backinfo.code==0){
						showPop(backinfo.message+"：邮件发送成功","success");
						mail_t = setInterval(function () {
								mail_countdown()
							}, 1000)
						mail_countdown();
					}else{
						showPop("邮箱发送失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async sendNewPhone() {
					if($("#newPhone").val()==this.user.phone){
						showPop("手机号未修改","error");
						showPop("新手机号与旧手机号一致，无需验证手机号","warning");
						return;
					}
					showPop("短信发送中","success");
                    var backinfo = await post_info(apiLinks.Phone_Sending_API_Link, {"phone": $("#newPhone").val()});
					if(backinfo.code==0){
						showPop(backinfo.message+"：短信发送成功","success");
						phone_t = setInterval(function () {
								phone_countdown()
							}, 1000)
						phone_countdown();
					}else{
						showPop("短信发送失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async sendMail() {
					showPop("邮件发送中","success");
                    var backinfo = await post_info(apiLinks.Logined_Email_Sending_Link, {});
					if(backinfo.code==0){
						showPop(backinfo.message+"：邮件发送成功","success");
						mail_old_t = setInterval(function () {
								mail_old_countdown();
							}, 1000)
						mail_old_countdown();
					}else{
						showPop("邮箱发送失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async sendPhone() {
					showPop("短信发送中","success");
                    var backinfo = await post_info(apiLinks.Logined_Phone_Sending_Link, {});
					if(backinfo.code==0){
						showPop(backinfo.message+"：短信发送成功","success");
						phone_old_t = setInterval(function () {
								phone_old_countdown();
							}, 1000)
						phone_old_countdown();
					}else{
						showPop("短信发送失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async updateMail(){
					showPop("更新邮箱地址中","success");
                    var backinfo = await patch_info(apiLinks.Update_User_Mail_Link, {
                        "mail": $("#newMail").val(),
                        "mailCode": $("#newMailCode").val(),
                        "phoneCode": $("#phoneCode").val()
                    });
					if(backinfo.code==0){
						showPop(backinfo.message,"success");
						this.user = await this.getUserName();
					}else{
						showPop("更新邮箱地址失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async updatePhone(){
					showPop("更新手机号中","success");
                    var backinfo = await patch_info(apiLinks.Update_User_Phone_Link, {
                        "phone": $("#newPhone").val(),
                        "phoneCode": $("#newPhoneCode").val(),
                        "mailCode": $("#mailCode").val()
                    });
					if(backinfo.code==0){
						showPop(backinfo.message,"success");
						this.user = await this.getUserName();
					}else{
						showPop("更新手机号失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async changePasswordByMail(){
					showPop("密码修改中","success");
                    var backinfo = await patch_info(apiLinks.Update_User_Password_By_Mail_API_Link, {
                        "newPassword": $("#newPassword").val(),
                        "mailCode": $("#mailCodeByPassword").val()
                    });
					if(backinfo.code==0){
						showPop(backinfo.message,"success");
						location.reload();
					}else{
						showPop("密码修改失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async changePasswordByPhone(){
					showPop("密码修改中","success");
                    var backinfo = await patch_info(apiLinks.Update_User_Password_By_Phone_API_Link, {
                        "newPassword": $("#newPassword").val(),
                        "phoneCode": $("#phoneCodeByPassword").val()
                    });
					if(backinfo.code==0){
						showPop(backinfo.message,"success");
						location.reload();
					}else{
						showPop("密码修改失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async fetchData(page) {
				    try {
                        const response = await fetch(apiLinks.Article_All_Myself_Link + `?pageNum=${page}&pageSize=${this.pageSize}`);//http://127.0.0.1:6066/api/article/oneself?pageNum=${page}&pageSize=${this.pageSize}
				        if (!response.ok) {
				            throw new Error(`HTTP error! status: ${response.status}`);
				        }
				        const data = await response.json();
					
				        if (data.code === 0) {
				            this.total = data.data.total;
				            this.currentPageData = data.data.items; // 直接更新当前页面的数据
				        } else {
				            console.error(data.message);
				        }
				    } catch (error) {
				        console.error('There was a problem with the fetch operation:', error);
				    }
				},
				goToPage(page) {
				    if (page >= 1 && page <= this.totalPages) {
				        this.currentPage = page;
				        this.fetchData(page);
				    }
				},
				markdownToHtml(markdown) {
					// 使用 marked 将 Markdown 转换为 HTML
					return marked.parse(markdown);
				},
				gotoTop(){
					// window.location.hash = "#commenting";
					$("#top")[0].scrollIntoView({behavior: "smooth"});
				},
				async deleteArticle(articleID){
					showPop("文章删除中","success");
                    var backinfo = await delete_info(apiLinks.Article_Delete_Link, {"id": articleID});
					if(backinfo.code==0){
						showPop(backinfo.message,"success");
						this.fetchData(this.currentPage);
						this.fetchDataAdmin(this.currentPageAdmin);
						// location.reload();
					}else{
						showPop("文章删除失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				getToggleTarget(articleId) {return `target: #modal-example${articleId}`;},
				getToggleTargetAdmin(articleId) {return `target: #modal-example-Admin${articleId}`;},
				getCommentToggleTarget(commentatorId) {return `target: #modal-${commentatorId}`;},
				handle(url) {
				    window.location.href = url;
				},
				async commentFetchData(page) {
				    try {
                        const response = await fetch(apiLinks.Comment_All_Myself_Link + `?pageNum=${page}&pageSize=${this.commentPageSize}`);//http://127.0.0.1:6066/api/comment/oneself?pageNum=${page}&pageSize=${this.commentPageSize}
				        if (!response.ok) {
				            throw new Error(`网络请求失败: ${response.status}`);
				        }
				        const data = await response.json();
				        if (data.code === 0) {
				            this.commentTotal = data.data.total;
				            this.commentCurrentPageData = data.data.items; // 直接更新当前页面的数据
							for (const [index, comment] of data.data.items.entries()) {
                                const articleResponse = await fetch(apiLinks.Article_Detail_Link + `?id=${comment.articleId}`);
								  if (articleResponse.ok) {
									const articleData = await articleResponse.json();
									if (articleData.code === 0) {
									  // this.articleTitles[index]=articleData.data.title;
									  this.articleTitles[index] = articleData.data.title
									} else {
									  console.error("请求文章标题失败", articleData.message);
									}
								  } else {
									console.error("网络请求失败", response.status);
								  }
							}
				        } else {
				            console.error(data.message);
				        }
				    } catch (error) {
				        console.error('获取操作出现问题：', error);
				    }
				},
				commentGoToPage(page) {
				    if (page >= 1 && page <= this.commentTotalPages) {
				        this.commentCurrentPage = page;
				        this.commentFetchData(page);
				    }
				},
				getCommentAdminToggleTarget(commentatorId) {return `target: #modal-Admin${commentatorId}`;},
				async commentAdminFetchData(page) {
				    try {
                        const response = await fetch(apiLinks.Comment_Admin_Link + `?auditingState=false&pageNum=${page}&pageSize=${this.commentAdminPageSize}`);//http://127.0.0.1:6066/api/comment/admin?auditingState=false&pageNum=${page}&pageSize=${this.commentAdminPageSize}
				        if (!response.ok) {
				            throw new Error(`网络请求失败: ${response.status}`);
				        }
				        const data = await response.json();
				        if (data.code === 0) {
				            this.commentAdminTotal = data.data.total;
				            this.commentAdminCurrentPageData = data.data.items; // 直接更新当前页面的数据
							for (const [index, comment] of data.data.items.entries()) {
                                const articleResponse = await fetch(apiLinks.Article_Detail_Link + `?id=${comment.articleId}`);
								  if (articleResponse.ok) {
									const articleData = await articleResponse.json();
									if (articleData.code === 0) {
									  // this.articleTitles[index]=articleData.data.title;
									  this.articleAdminTitles[index] = articleData.data.title
									} else {
									  console.error("请求文章标题失败", articleData.message);
									}
								  } else {
									console.error("网络请求失败", response.status);
								  }
							}
				        } else {
				            console.error(data.message);
				        }
				    } catch (error) {
				        console.error('获取操作出现问题：', error);
				    }
				},
				commentAdminGoToPage(page) {
				    if (page >= 1 && page <= this.commentAdminTotalPages) {
				        this.commentAdminCurrentPage = page;
				        this.commentAdminFetchData(page);
				    }
				},
				async adminComment(commentID){
                    var backinfo = await patch_info(apiLinks.Comment_Pass_Link + `?id=${commentID}`, {});
					if(backinfo.code==0){
						showPop("审核通过!","success");
						this.commentFetchData(this.commentCurrentPage);
						this.commentAdminFetchData(this.commentAdminCurrentPage);
					}else{
						showPop("审核请求失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async adminArticle(id){
                    var backinfo = await patch_info(apiLinks.Article_Pass_Link + `?id=${id}`, {});
					if(backinfo.code==0){
						showPop("审核通过!","success");
						this.fetchDataAdmin(this.currentPageAdmin);
					}else{
						showPop("审核请求失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async fetchDataAdmin(page) {
				    try {
                        const response = await fetch(apiLinks.Article_Admin_Link + `?auditingState=false&pageNum=${page}&pageSize=${this.pageSizeAdmin}`);//http://127.0.0.1:6066/api/article/admin?auditingState=false&pageNum=${page}&pageSize=${this.pageSizeAdmin}
				        if (!response.ok) {
				            throw new Error(`HTTP error! status: ${response.status}`);
				        }
				        const data = await response.json();
					
				        if (data.code === 0) {
				            this.totalAdmin = data.data.total;
				            this.currentPageDataAdmin = data.data.items; // 直接更新当前页面的数据
				        } else {
				            console.error(data.message);
				        }
				    } catch (error) {
				        console.error('There was a problem with the fetch operation:', error);
				    }
				},
				goToPageAdmin(page) {
				    if (page >= 1 && page <= this.totalPagesAdmin) {
				        this.currentPageAdmin = page;
				        this.fetchDataAdmin(page);
				    }
				},
				
				async getArticle(articleID){
                    var backinfo = await get_info(apiLinks.Article_Detail_Link + `?id=${articleID}`);
					if(backinfo.code==0){
						return backinfo.data.title;
					}else{
						showPop("请求评论对应的文章名失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async deleteComment(commentId){
					showPop("评论删除中","success");
                    var backinfo = await delete_info(apiLinks.Comment_Delete_Link, {"commentId": commentId});
					if(backinfo.code==0){
						showPop(backinfo.message,"success");
						this.commentFetchData(this.commentCurrentPage);
						this.commentAdminFetchData(this.commentAdminCurrentPage);
						// location.reload();
					}else{
						showPop("评论删除失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async getViewByArt(){
                    var backinfo = await get_info(apiLinks.User_All_View_From_Article_Link);
					if(backinfo.code==0){
						this.allViewByArt = backinfo.data;
					}else{
						showPop("获取文章总浏览量失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async getLikeByArt(){
                    var backinfo = await get_info(apiLinks.User_All_Like_From_Article_Link);
					if(backinfo.code==0){
						this.allLikeByArt = backinfo.data;
					}else{
						showPop("获取文章总点赞量失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async getLikeByCom(){
                    var backinfo = await get_info(apiLinks.User_All_Like_From_Comment_Link);
					if(backinfo.code==0){
						this.allLikeByCom = backinfo.data;
					}else{
						showPop("获取评论总赞同量失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async getAllArt(){
                    var backinfo = await get_info(apiLinks.User_All_Number_From_Article_Link);
					if(backinfo.code==0){
						this.allArt = backinfo.data;
					}else{
						showPop("获取发布文章数量失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async getAllCom(){
                    var backinfo = await get_info(apiLinks.User_All_Number_From_Comment_Link);
					if(backinfo.code==0){
						this.allCom = backinfo.data;
					}else{
						showPop("获取发布评论数量失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async getMaxLikeArt(){
                    var backinfo = await get_info(apiLinks.User_Max_Like_From_Article_Link);
					if(backinfo.code==0){
						this.maxLikeArt = backinfo.data;
					}else{
						showPop("获取最赞文章失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async getMaxViewArt(){
                    var backinfo = await get_info(apiLinks.User_Max_View_From_Article_Link);
					if(backinfo.code==0){
						this.maxViewArt = backinfo.data;
					}else{
						showPop("获取最多人看失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
				async getMaxLikeCom(){
                    var backinfo = await get_info(apiLinks.User_Max_Like_From_Comment_Link);
					if(backinfo.code==0){
						this.maxLikeCom = backinfo.data;
					}else{
						showPop("获取最多人看失败","error");
						showPop("原因："+backinfo.message,"error");
					}
				},
			},
			computed: {
			    totalPages() {
			        return Math.ceil(this.total / this.pageSize);
			    },
				commentTotalPages() {
				    return Math.ceil(this.commentTotal / this.commentPageSize);
				},
				commentAdminTotalPages() {
				    return Math.ceil(this.commentAdminTotal / this.commentAdminPageSize);
				},
				totalPagesAdmin(){
					return Math.ceil(this.totalAdmin / this.pageSizeAdmin);
				},
			},
	        mounted() {
	            this.fetchData(this.currentPage);
				this.commentFetchData(this.commentCurrentPage);
				this.commentAdminFetchData(this.commentAdminCurrentPage);
				this.fetchDataAdmin(this.currentPageAdmin);
	        },
        });
		app.mount('#top');
    } catch (error) {
        console.error('Error:', error);
    }
}
userinfo();
async function get_info(urls) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: urls,
            type: 'get',
            data: {},
            async: true,
            success: function(data) {
                resolve(data);
            }
        });
    });
}
async function patch_info(urls,datas) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: urls,
            type: 'patch',
            data: $.param(datas),
            async: true,
            success: function(data) {
                resolve(data);
            }
        });
    });
}
async function post_info(urls,datas) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: urls,
            type: 'post',
            data: $.param(datas),
            async: true,
            success: function(data) {
                resolve(data);
            }
        });
    });
}
async function delete_info(urls,datas) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: urls,
            type: 'delete',
            data: $.param(datas),
            async: true,
            success: function(data) {
                resolve(data);
            }
        });
    });
}

var mail_time = 60;
function mail_countdown(){
    if (mail_time == 0) {
    //这里时设置当时间到0的时候重新设置点击事件，并且默认time修改为60
        document.getElementById("sendNewMail").removeAttribute("disabled");
        document.getElementById("sendNewMail").innerText="获取";
        mail_time = 60;
        clearInterval(mail_t);
    }else{
    //这里是显示时间倒计时的时候点击不生效
        document.getElementById("sendNewMail").setAttribute("disabled", '');
        document.getElementById("sendNewMail").innerHTML=mail_time;
        mail_time--;
    }
}

var mail_old_time = 60;
function mail_old_countdown(){
    if (mail_old_time == 0) {
    //这里时设置当时间到0的时候重新设置点击事件，并且默认time修改为60
        document.getElementById("sendMail").removeAttribute("disabled");
        document.getElementById("sendMail").innerText="获取";
        mail_old_time = 60;
        clearInterval(mail_old_t);
    }else{
    //这里是显示时间倒计时的时候点击不生效
        document.getElementById("sendMail").setAttribute("disabled", '');
        document.getElementById("sendMail").innerHTML=mail_old_time;
        mail_old_time--;
    }
}

var phone_time = 60;
function phone_countdown(){
    if (phone_time == 0) {
    //这里时设置当时间到0的时候重新设置点击事件，并且默认time修改为60
        document.getElementById("sendNewPhone").removeAttribute("disabled");
        document.getElementById("sendNewPhone").innerText="获取";
        phone_time = 60;
        clearInterval(phone_t);
    }else{
    //这里是显示时间倒计时的时候点击不生效
        document.getElementById("sendNewPhone").setAttribute("disabled", '');
        document.getElementById("sendNewPhone").innerHTML=phone_time;
        phone_time--;
    }
}
var phone_old_time = 60;
function phone_old_countdown(){
    if (phone_old_time == 0) {
    //这里时设置当时间到0的时候重新设置点击事件，并且默认time修改为60
        document.getElementById("sendPhone").removeAttribute("disabled");
        document.getElementById("sendPhone").innerText="获取";
        phone_old_time = 60;
        clearInterval(phone_old_t);
    }else{
    //这里是显示时间倒计时的时候点击不生效
        document.getElementById("sendPhone").setAttribute("disabled", '');
        document.getElementById("sendPhone").innerHTML=phone_old_time;
        phone_old_time--;
    }
}

async function updataAvatar(urlAvatar){
    var backinfo = await patch_info(apiLinks.Update_User_Pic_Link, {"avatarUrl": urlAvatar});
	if(backinfo.code==0){
		showPop(backinfo.message+"：头像更新成功","success");
		$("#avatar").html(`<button class="uk-button uk-button-primary uk-button-small" onclick="javascript:location.reload();">刷新数据</button>`);
	}else{
		showPop("头像更新失败","error");
		showPop("原因："+backinfo.message,"error");
	}
}

