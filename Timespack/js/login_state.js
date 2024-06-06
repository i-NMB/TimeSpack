function fetchData(urls) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', urls);
		xhr.onload = function () {
		    if (xhr.status === 200) {
		        resolve(JSON.parse(xhr.responseText));
		    }else if (xhr.status === 401){
				goto_login();
			}else {
		        reject(new Error(xhr.statusText));
		    }
		};
		xhr.send();
        xhr.onerror = function () {
            reject(new Error('请求登录接口出现错误'));
        };
    });
}

fetchData(User_Information_Link).then(res => {
    const app = {
      data() {
        return {
          login_info: res.data
        }
      }
    }
	Vue.createApp(app).mount('#login')
}).catch(error => {
    console.error('请求出现问题：', error);
});


function goto_login(){
	$("#login").html('<li class="uk-active"><a href="./login.html">立即登录</a></li>')
}

function clearAllCookie() {
	fetchData(Login_Out_Link).then(res => {
	    if(res.code==0){
			showPop(res.message+"，即将刷新","success");
		}
		setTimeout(() => {
		  document.location.reload();
		}, 2000);
	}).catch(error => {
	    console.error('请求出现问题：', error);
	});
}

$.ajaxSetup({
   error: function(xhr, textStatus, errorThrown) {
      if(xhr.status === 401) {
         // 在这里进行处理未经授权的操作
		 showPop('未登录',"error");
		 return;
      }
	  showPop('请求错误，详见控制台',"error");
   }
});