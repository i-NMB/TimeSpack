
const { createEditor, createToolbar } = window.wangEditor

const editorConfig = {
    placeholder: `每一颗心都跳动着独一无二的故事，每一份思考都蕴含着深邃的智慧。有一份对文字的热爱，有一个属于自己的故事。这里是故事的起点，是思想的舞台。`,
    onChange(editor) {
      const html = editor.getHtml()
      // console.log('editor content', html)
      // 也可以同步到 <textarea>
    },
	MENU_CONF: {}
}

editorConfig.MENU_CONF['uploadImage'] = {
	server: 'http://127.0.0.1:6066/api/upImg',
    // form-data fieldName ，默认值 'wangeditor-uploaded-image'
    fieldName: 'file',

    // 单个文件的最大体积限制，默认为 2M
    maxFileSize: 5 * 1024 * 1024, // 1M

    // 最多可上传几个文件，默认为 100
    maxNumberOfFiles: 10,

    // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
    allowedFileTypes: ['image/*'],

    // 跨域是否传递 cookie ，默认为 false
    withCredentials: true,

    // 超时时间，默认为 10 秒
    timeout: 10 * 1000, // 5 秒
}

const editor = createEditor({
    selector: '#editor-container',
    html: '<p><br></p>',
    config: editorConfig,
    mode: 'default', // or 'simple','default'
})

const toolbarConfig = {}

const toolbar = createToolbar({
    editor,
    selector: '#toolbar-container',
    config: toolbarConfig,
    mode: 'default', // or 'simple'
})



function writeArticle(){
	if($("#selectcs").find("option:selected").val()==""){
		showPop("请选择分类","error");
		return;
	}
	if($("#title").val()==""){
		showPop("文章标题为空","error");
		return;
	}
	if($("#selectcs").find("option:selected").val()!=3&&editor.getHtml()=="<p><br></p>"){
		showPop("文章内容为空","error");
		return;
	}
	if($("#selectcs").find("option:selected").val()!=2&&$("#upImg").val()==""){
		showPop("图片为空","error");
		return;
	}
	var data = {
	    title: $("#title").val(),
	    content: editor.getHtml(),
		coverImg: $("#upImg").val(),
		state: "已发布",
		categoryId: $("#selectcs").find("option:selected").val()
	};
	console.log(JSON.stringify(data));
	$.ajax({
	    type: "POST",
	    url: "http://127.0.0.1:6066/api/article/add",
	    contentType: "application/json", // 设置contentType
	    data: JSON.stringify(data), // 使用$.param方法将数据转换为键值对字符串
	    success: function(response) {
	        // 请求成功时的回调函数
			if(response.message!="操作成功"){
				showPop(response.message, "error");
				return;
			}else if(response.message=="操作成功"){
				showPop("文章发布成功", "success");
				setTimeout(() => {
				    window.parent.location.reload();
				}, 5000)
				return;
			}
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
			showPop("请求失败，请重试:"+errorThrown, "error");
	        // 请求失败时的回调函数
	        console.error("发送验证码错误：", "状态："+textStatus, "原因："+errorThrown);
	    }
	});
}