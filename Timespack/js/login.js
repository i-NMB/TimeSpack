function login(){
	var data = {
	    username: $("#login_name").val(),
		password: $("#login_password").val(),
		code: $("#login_code").val()
	};
	console.log(data);
	$.ajax({
	    type: "POST",
	    url: Login_link,
	    contentType: "application/x-www-form-urlencoded", // 设置contentType
	    data: $.param(data), // 使用$.param方法将数据转换为键值对字符串
	    success: function(response) {
			if(response.code!=0){
				showPop(response.message,"error");
				return;
			}else if(response.code==0){
				showPop(response.message,"success");
				showPop("登录成功即将转跳","success");
				setTimeout(() => {
        		  window.location.replace("/");
        		}, 2000);
				// UIkit.notification({message: '<span uk-icon=\'icon: check\'></span> 操作成功', status: 'success', pos: 'top-center'});
				return;
			}
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
			showPop("请求失败", "error");
			showPop("错误状态："+textStatus+" "+ errorThrown, "error");
			// UIkit.notification({message: errorThrown, status: 'danger', pos: 'top-center'});
	        // 请求失败时的回调函数
	        console.error("登录失败原因：", "状态："+textStatus, "原因："+errorThrown);
	    }
	});
}

fetchData(User_Information_Link).then(res => {
    console.log(res);
    showPop("已登录");
	window.location.replace("/");
}).catch(error => {
});

function sendMailCode(e){
	$.post(Email_Sending_API_Link,{ email: $("#mail").val()}, function(data) {
		if(data.message!=="操作成功") showPop(data.message,"error");
		if(data.message==="操作成功") {
			showPop(data.message+"：邮件发送成功","success");
			mail_t = setInterval(function () {
					mail_countdown(e)
				}, 1000)
			mail_countdown(e);
			}

	}).fail(function(e) {
		// 这个回调函数会在请求失败时被调用
		console.log(e);
		showPop("请求失败", "error");
		showPop("错误状态："+e.status+" "+ e.statusText, "error");
	});
	
}

function sendPhoneCode(e){
	$.post(Phone_Sending_API_Link,{ phone: $("#phone").val()}, function(data) {
		if(data.message!=="操作成功") showPop(data.message,"error");
		if(data.message==="操作成功"){ 
			showPop(data.message+"：短信发送成功","success");
			phone_t = setInterval(function () {
					phone_countdown(e)
				}, 1000)
			phone_countdown(e);
			};
	}).fail(function(e) {
		// 这个回调函数会在请求失败时被调用
		console.log(e);
		showPop("请求失败", "error");
		showPop("错误状态："+e.status+" "+ e.statusText, "error");
	});
	
}

function reg(){
	if($("#password").val()!=$("#repassword").val()){
		$(".lock").addClass("uk-form-danger");
		$("#password").addClass("uk-form-danger");
		$("#repassword").addClass("uk-form-danger");
		showPop("两次密码不一致", "warning");
		showPop("请再次确认您的密码", "error");
		return;
	}else{
		$(".lock").removeClass("uk-form-danger");
		$("#password").removeClass("uk-form-danger");
		$("#repassword").removeClass("uk-form-danger");
	}
	var data = {
	    username: $("#username").val(),
	    password: $("#repassword").val(),
		email: $("#mail").val(),
		emailCode: $("#emailCode").val(),
		phone: $("#phone").val(),
		phoneCode: $("#phoneCode").val(),
		code: $("#code").val()
	};
	$.ajax({
	    type: "POST",
	    url: Registration_Link,
	    contentType: "application/x-www-form-urlencoded", // 设置contentType
	    data: $.param(data), // 使用$.param方法将数据转换为键值对字符串
	    success: function(response) {
	        // 请求成功时的回调函数
	        console.log("Success:", response);
			if(response.message!="操作成功"){
				showPop(response.message, "error");
				return;
			}else if(response.message=="操作成功"){
				showPop("注册成功", "success");
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

var mail_time = 60;
function mail_countdown(e){
    if (mail_time == 0) {
    //这里时设置当时间到0的时候重新设置点击事件，并且默认time修改为60
        e.setAttribute("onclick","sendMailCode(this)");
        e.innerText="获取";
        mail_time = 60;
        clearInterval(mail_t);
    }else{
    //这里是显示时间倒计时的时候点击不生效
        e.setAttribute("onclick", '');
        e.innerHTML=mail_time;
        mail_time--;
    }
}

var phone_time = 60;
function phone_countdown(e){
    if (phone_time == 0) {
    //这里时设置当时间到0的时候重新设置点击事件，并且默认time修改为60
        e.setAttribute("onclick","sendMailCode(this)");
        e.innerText="获取";
        phone_time = 60;
        clearInterval(phone_t);
    }else{
    //这里是显示时间倒计时的时候点击不生效
        e.setAttribute("onclick", '');
        e.innerHTML=phone_time;
        phone_time--;
    }
}

