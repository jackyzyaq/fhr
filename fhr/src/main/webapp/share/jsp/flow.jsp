<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%@ page import="com.yq.faurecia.service.*"%>
<%@ page import="com.yq.authority.pojo.UserInfo" %>
<%
	//emp_id是操作者的id
	int emp_id = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("emp_id"), "0"));
	String flow_type = StringUtils.defaultIfEmpty(request.getParameter("flow_type"), "");
	int days = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("days"), "0"));
	
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(st);
	FlowInfoService flowInfoService = (FlowInfoService) ctx.getBean("flowInfoService");
	
	List<FlowInfo> flowInfoResult = flowInfoService.getFlowBy(emp_id,flow_type,days);
	int flow_id = flowInfoService.getFlowIdByList(flowInfoResult);
	
%>
<script type="text/javascript">
$(function(){
	<%if(flow_id==0){%>
		showInfo('没找到审批流，请联系HR!！');
	<%}%>
});

</script>
<div id="wizard" class="wizard">
	<%if(flowInfoResult!=null&&flowInfoResult.size()==1){ %>
    <ul class="hormenu">
    	<%
    		FlowInfo fi = flowInfoResult.get(0);
    		for(String step:StringUtils.defaultIfEmpty(fi.getStep_info(), "").split("]")){
    	%>
    	<li>
        	<a class="selected">
            	<span class="h2"><%=step.split(",")[1].split("\\|")[3] %></span>
                <span class="dot"><span></span></span>
                <span class="label"><%=step.split(",")[1].split("\\|")[1]%></span>
            </a>
        </li>
    	<%} %>
    </ul>
    <input type="hidden" id="flow_id" name="flow_id" value="<%=fi.getId()%>"/>
    <%}else{ %>
    <input type="hidden" id="flow_id" name="flow_id" value="0"/>
    <%} %>
    <br clear="all" /><br /><br />
</div><!--#wizard-->

