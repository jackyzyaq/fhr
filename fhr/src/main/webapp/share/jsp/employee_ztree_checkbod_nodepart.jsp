<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.faurecia.service.*"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%
	String deptIdsRole = (String) session.getAttribute("deptIdsRole");
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(st);
	EmployeeInfoService employeeInfoService = (EmployeeInfoService) ctx.getBean("employeeInfoService");
	
	boolean show_sub = StringUtils.isEmpty(request.getParameter("show_sub"))?false:Boolean.parseBoolean(request.getParameter("show_sub"));
	List<EmployeeInfo> employeeInfos = null;
	
	EmployeeInfo ei = new EmployeeInfo();
	//ei.setState(1);
	ei.setDept_ids(deptIdsRole);
	employeeInfos = employeeInfoService.findByCondition(ei,null);
	
	String emp_id = StringUtils.defaultIfEmpty(request.getParameter("emp_id"),"0");
	String emp_name = StringUtils.defaultIfEmpty(request.getParameter("emp_name"),"根目录");
	
%>
<script type="text/javascript">
var tmpVal =[
		{id:-1, pId:-2,pName:"全选", name:"全选",value:"",open:true },
		<%
		if(employeeInfos!=null&&employeeInfos.size()>0){
		for(int i=0;i<employeeInfos.size();i++){
			EmployeeInfo a=employeeInfos.get(i);
		%>
		{id:<%=a.getId()%>, pId:-1,pName:"", name:"<%=a.getZh_name()+"("+Util.alternateZero(a.getId())+")"%>",value:"<%=a.getId()%>",open:false }
		<%
		if((i+1)!=employeeInfos.size())out.print(",");
		}
		} %>		
 ];
 var type_zNodes_val = tmpVal;
 $(function(){
 	var width_ = parseInt($("#emp_name").css('width').replace('px',''));
 	if(width_<150)
 		width_+=100;
 	$("#empTree").css('width',(width_)+'px');
 });
 function emp_ztree(){
 	type_show('emp_name','emp_id','empTree','empContent',type_zNodes_val);
 }
</script>
<link rel="stylesheet" href="${ctx}/js/ztree/css/demo1.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.2.js"></script>
<script type="text/javascript" src="${ctx}/share/js/tree_checkbox.js"></script>
<input id="emp_name" type="text" class="longinput" readonly value="<%=emp_name %>" onclick="emp_ztree();"/>
<span id="empContent" class="menuContent" style="display:none; position: absolute;z-index:99999">
	<ul id="empTree" class="ztree" style="margin-top:0;"></ul>
</span>
<input type="hidden" name="emp_id" value="<%=emp_id %>" id="emp_id"/>