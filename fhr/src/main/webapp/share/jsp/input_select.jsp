<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%
String input_div = StringUtils.defaultIfEmpty(request.getParameter("input_div"), "t");
String select_div = StringUtils.defaultIfEmpty(request.getParameter("select_div"), "t");
String parent_div = StringUtils.defaultIfEmpty(request.getParameter("parent_div"), "t");

//格式：***|***|***|***
String data = StringUtils.defaultIfEmpty(request.getParameter("data"), "");
%>
    <script type="text/javascript">
    $(function(){
    //获取select
		var engine = $('select[name="<%=select_div%>"]');
		//可编辑select具体实现
 		var <%="_"+parent_div+"_"+input_div+"_"%> = false;
        $("#<%=parent_div+" #"+input_div%>").focus(function () {
            <%="_"+parent_div+"_"+input_div+"_"%> = true;
            $(this).next().css('display', 'block');
        }).blur(function () {
            if (<%="_"+parent_div+"_"+input_div+"_"%>) {
                $(this).next().css('display', 'none');
            }
        }).keyup(function () {
            var queryCondition = $("#<%=parent_div+" #"+input_div%>").val().toLowerCase();
        }).next().mousedown(function () {
            <%="_"+parent_div+"_"+input_div+"_"%> = false;
        }).change(function () {
            $(this).css('display', 'none').prev().val(this.value);
        });
    });
    </script>
	<input id="<%=input_div%>" name="<%=input_div%>" class="mediuminput" type="text"/>
	<select id="<%=select_div%>" name="<%=select_div%>"  size="10" style="min-width:20%;height: auto; position: absolute; display: none;">
		<option value="-" selected>-</option>
		<%for(String d:data.split("\\|")){ %>
		<option value="<%=d%>"><%=d%></option>
		<%} %>
	</select>