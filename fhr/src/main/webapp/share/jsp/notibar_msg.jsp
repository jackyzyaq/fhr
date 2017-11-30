<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%
//msg_type:info,success,alert,error,announcement
String msg_type = StringUtils.defaultIfEmpty(request.getParameter("msg_type"), "info");
String text = StringUtils.defaultIfEmpty(request.getParameter("text"), "");

if(!msg_type.trim().equals("announcement")){
	msg_type = "msg"+msg_type;
}
%>
<script type="text/javascript">
	///// NOTIFICATION CLOSE BUTTON /////
	$('.notibar .close').click(function(){
		$(this).parent().fadeOut(function(){
			$(this).remove();
		});
	});
</script>
<div class="notibar <%=msg_type%>">
    <a class="close"></a>
    <p><%=text %></p>
</div>