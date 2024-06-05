function showPop(poptext,state){
	if(state==="success"){
		var style_show = {background: "linear-gradient(to right, #00b09b, #96c93d)",}
	}else if(state==="error"){
		var style_show = {background: "linear-gradient(45deg, #ff9a9e 0%, #fad0c4 99%, #fad0c4 100%)",}
	}else if(state==="warning"){
		var style_show = {background: "linear-gradient(120deg, #f6d365 0%, #fda085 100%)",}
	}
	Toastify({
	  text: poptext,
	  duration: 3000,
	  // destination: "https://github.com/apvarun/toastify-js",
	  // newWindow: true,
	  // close: true,
	  gravity: "bottom", // `top` or `bottom`
	  position: "right", // `left`, `center` or `right`
	  stopOnFocus: true, // Prevents dismissing of toast on hover
	  style: style_show,
	  // onClick: function(){} // Callback after click
	}).showToast();
}