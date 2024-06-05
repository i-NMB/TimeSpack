// window.onscroll = function() {
// 	$(".first-banner").fadeOut(300);
// // 获取当前滚动位置
// 	var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
// // 如果滚动位置大于导航栏的初始位置，则将导航栏固定在页面顶部
// 	if (scrollTop > 0) {
// 	$(".nav").css("position","fixed");
// 	$(".nav").css("top","0");
// 	$(".nav").css("width","100%")
// 	} else {
// 	// 否则，将导航栏恢复到初始位置
// 	$(".nav").removeAttr("style");
// 	// $(".uk-navbar-container").css("position","none");
// 	}
// 	//测试
// }

function handleScrollAndLoad() {
    // 获取当前滚动位置
    var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;

    // 检查滚动位置，并根据需要调整导航栏
    if (scrollTop > 0) {
        $(".first-banner").fadeOut(500);
        $(".nav").css({
            "position": "fixed",
            "top": "0",
            "width": "100%"
        });
    } else {
        $(".nav").removeAttr("style");
    }
}

// 在页面滚动时调用
window.onscroll = handleScrollAndLoad;

// 在页面加载时调用
window.onload = handleScrollAndLoad;


