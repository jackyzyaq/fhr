<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%@ page import="com.yq.faurecia.service.*"%>
<%@ page import="com.yq.authority.pojo.UserInfo" %>
<%
	//handle_id
	int handle_id = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("handle_id"), "0"));
	int flow_id = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("flow_id"), "0"));
	int next_check_emp_id = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("next_check_emp_id"), "0"));
	int status = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("status"), "-1"));
	String flow_type = StringUtils.defaultIfEmpty(request.getParameter("flow_type"), "");
	SimpleDateFormat sdf = new SimpleDateFormat(Global.DATE_FORMAT_STR_B);
	
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(st);
	FlowInfoService flowInfoService = (FlowInfoService) ctx.getBean("flowInfoService");
	FlowStepService flowStepService = (FlowStepService) ctx.getBean("flowStepService");
	
	FlowInfo flowInfo = flowInfoService.queryById(flow_id, null);
	Map<Integer,String> flowMap = Util.convertToMap(flowInfo);
	
	FlowStep fs = new FlowStep();
	fs.setFlow_id(flow_id);
	fs.setHandle_id(handle_id);
	List<FlowStep> fsList = flowStepService.findByCondition(fs, null);
	
%>
<script type="text/javascript">
$(function(){
});
</script>
<div class="widgetbox">
    <div class="title"><h3>审核步骤</h3></div>
    <div class="widgetcontent padding0 statement">
       <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
            <colgroup>
                <col class="con0" />
                <col class="con1" />
                <col class="con0" />
            </colgroup>
            <thead>
                <tr>
                    <th class="head0">审核环节</th>
                    <th class="head1">审核人</th>
                    <th class="head0">审核人部门</th>
                    <th class="head1">审核人动作</th>
                    <th class="head0">审核时间</th>
                    <th class="head0">审核人意见</th>
                </tr>
            </thead>
            <tbody>
            	<%
            	//已经审批的最后审批者的步骤
            	if(fsList!=null&&fsList.size()>0){ 
            		for(FlowStep flowStep : fsList){
            	%>
            	<tr>
                    <td>第<%=flowMap.get(flowStep.getEmp_id()).split(",")[0] %>环节</td>
                    <td><%=flowStep.getEmp_name() %></td>
                    <td><%=flowMap.get(flowStep.getEmp_id()).split(",")[1].split("\\|")[1]%></td>
                    <td><%=Global.flow_check_state_name[flowStep.getState()] %></td>
                    <td><%=sdf.format(flowStep.getState_date()) %></td>
                    <td><%=flowStep.getRemark() %></td>
                </tr>
            	<%} }%>
	            <%
	            if(status!=Global.flow_check_status[2]&&flowMap.get(next_check_emp_id)!=null){
	            int last_step = (next_check_emp_id==0?0:Integer.parseInt(flowMap.get(next_check_emp_id).split(",")[0]));
	    		for(String step:StringUtils.defaultIfEmpty(flowInfo.getStep_info(), "").split("]")){
		    		if(Integer.parseInt(step.split(",")[0].replace("[", ""))<last_step)continue;
		    	%>
		        <tr>
                    <td>第<%=step.split(",")[0].replace("[", "") %>环节</td>
                    <td><%=step.split(",")[1].split("\\|")[3] %></td>
                    <td><%=step.split(",")[1].split("\\|")[1]%></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
		    	<% } }%>
            </tbody>
        </table>
    </div><!--widgetcontent-->
</div><!--widgetbox-->