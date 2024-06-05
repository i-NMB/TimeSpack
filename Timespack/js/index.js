
async function get_article(urls) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: urls,
            type: 'get',
            data: {},
            async: true,
            success: function(data) {
                resolve(data);
            },
            error: function(error) {
                reject(error);
            }
        });
    });
}

async function initApp() {
    try {
        var getText = await get_article('http://127.0.0.1:6066/api/article/listWeighting?categoryId=2&pageNum=1&pageSize=15');
		var getPic = await get_article('http://127.0.0.1:6066/api/article/listWeighting?categoryId=3&pageNum=1&pageSize=10');
		var getPicAndText = await get_article('http://127.0.0.1:6066/api/article/listWeighting?categoryId=4&pageNum=1&pageSize=10')
        const app = Vue.createApp({
            data() {
                return {
                    plain_text: getText.data.items,
					userAvatarTitles: [],
					pics: getPic.data.items,
					pic_texts: getPicAndText.data.items
                }
            },
			methods: {
				markdownToHtml(markdown) {
					// 使用 marked 将 Markdown 转换为 HTML
					return marked.parse(markdown);
			    },
				async getUserAvatar(){
					var itemss = this.plain_text.entries();
					for (const [index, item] of itemss) {
						  const userAvatarResponse = await fetch(`http://127.0.0.1:6066/api/user/getUser?userId=${item.createUser}`);
						  if (userAvatarResponse.ok) {
							const userAvatarData = await userAvatarResponse.json();
							if (userAvatarData.code === 0) {
							  this.userAvatarTitles[index] = userAvatarData.data.userPic;
							} else {
							  console.error("请求文章标题失败", articleData.message);
							}
						  } else {
							console.error("网络请求失败", response.status);
						  }
					
					}
				},
			},
			async created() {
				await this.getUserAvatar();
			},
        });
        app.mount('#app');
    } catch (error) {
        console.error('Error:', error);
    }
}
// 调用初始化函数
initApp();
