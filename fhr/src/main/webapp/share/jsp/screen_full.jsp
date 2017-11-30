<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%
	String fn = StringUtils.defaultIfEmpty(request.getParameter("fn"), "");//调用js fn函数
%>
[<a id="fullscreenbtn" style="cursor:pointer">&nbsp;全屏&nbsp;</a>]
<script type="text/javascript" src="${ctx }/js/screenfull.js"></script>
<script>
$(function(){
// 对整个页面进行全屏操作
	$("#fullscreenbtn").click(function(){
		if($("#fullscreenbtn").text().Trim()=="全屏"){
			var body_div_inner_html = '';
			if($("body #body_div").lenght>0){
			}else{
				body_div_inner_html = $("body").html();
				$("body").html("<div id=\"body_div\"></div>");
				$("body #body_div").html(body_div_inner_html);
			}
			//如果当前文档允许全屏模式，这个属性值返回true。它也被用来检测浏览器是否支持全屏模式
			//夸浏览器的写法
			if (document.mozFullScreenEnabled || 
				document.fullscreenEnabled || 
				document.webkitFullscreenEnabled || 
				document.msFullscreenEnabled) { 
		    	if(screenfull.enabled){
					//screenfull.toggle();
					//screenfull.toggle($("#pipd_table")[0]);
					screenfull.request($("body")[0]);
					$("body #body_div").css("background","#fff");
					$("body #body_div").css("height",window.screen.height);
					$("body #body_div").css("width",window.screen.width);
					$("#fullscreenbtn").html("&nbsp;退出&nbsp;");
				}else {
			    }
	 		}
		}else{
			exitFullscreen();
		}
	});
	$( document ).bind(
	    'fullscreenchange webkitfullscreenchange mozfullscreenchange',
	    function(){
	        //todo code
	        if(isFullScreen()){
	        	<%if(!fn.trim().equals("")){%>
	        	eval('<%=fn%>()');
	        	<%}%>
	        }else{
	        	document.location.reload();
	        }
	    }
	);
});
function isFullScreen() {
    return document.fullscreen ||
           document.webkitIsFullScreen ||
           document.mozFullScreen ||
           false;
}
function exitFullscreen() {
    if (document.exitFullscreen) {
        document.exitFullscreen();
    }
    else if (document.webkitCancelFullScreen) {
        document.webkitCancelFullScreen();
    }
    else if (document.mozCancelFullScreen) {
        document.mozCancelFullScreen();
    }
    else if (document.msExitFullscreen) {
		document.msExitFullscreen();
	}
}
</script>	