const article = Vue.createApp({
    data() {
        return {
            categoryData: '',
			val: '2',
			ok: false,
        }
    },
	async created() {
		this.categoryData = await get_info(Category_All_Link);
		this.val = this.categoryData.data[0].categoryId;
	},
	methods: {
		markdownToHtml(markdown) {
			// 使用 marked 将 Markdown 转换为 HTML
			return marked.parse(markdown);
	    },
	},
	watch: {
		val(val,oldval){
			console.log(val);
		}
	}
});

async function initPublish() {
    try {
        article.mount('#article');
    } catch (error) {
        console.error('Error:', error);
    }
}
// 调用初始化函数
initPublish();
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