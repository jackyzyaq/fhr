<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%
	String isfull = (StringUtils.isEmpty(request.getParameter("isfull"))?"1":"0");//暂时不用
	String url = request.getRequestURI()+"?"+Util.getNumber()+"&"+Util.getUUID()+"&"+request.getQueryString()+"&isfull="+isfull;
%>
[<a id="fullscreenbtn" style="cursor:pointer">&nbsp;全屏&nbsp;</a>]
<script>
$(function(){
// 对整个页面进行全屏操作
	$("#fullscreenbtn").click(function(){
		var sUrl = '<%=url%>';
		click_no(sUrl);
	});
});
</script>	