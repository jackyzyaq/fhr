<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.faurecia.service.*"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%
	int dept_id = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("dept_id"),"0"));
	String deptIdsRole = (String) session.getAttribute("deptIdsRole");
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(st);
	EmployeeInfoService employeeInfoService = (EmployeeInfoService) ctx.getBean("employeeInfoService");
	
	boolean show_sub = StringUtils.isEmpty(request.getParameter("show_sub"))?false:Boolean.parseBoolean(request.getParameter("show_sub"));
	String emp_id = StringUtils.defaultIfEmpty(request.getParameter("emp_id"),"0");
	String emp_name = StringUtils.defaultIfEmpty(request.getParameter("emp_name"),"根目录");
	
	DepartmentInfoService deptInfoService = (DepartmentInfoService) ctx.getBean("departmentInfoService");
	
	List<DepartmentInfo> list = new ArrayList<DepartmentInfo>();
	deptInfoService.getSubDeptById(dept_id, list);
	
	String dept_ids = "";
	if(list!=null){
		for(int i=0;i<list.size();i++){
			DepartmentInfo a = list.get(i);
			if(a==null)continue;
			dept_ids += a.getId() +",";
		}
		dept_ids = dept_ids.substring(0,dept_ids.length()-1);
	}
	List<EmployeeInfo> employeeInfos = null;
	EmployeeInfo ei = new EmployeeInfo();
	ei.setState(1);
	ei.setDept_ids(dept_ids);
	employeeInfos = employeeInfoService.findByCondition(ei,null);
%>
<script type="text/javascript">
var tmpValEmpDep =[
		<%
		if(list!=null){
		for(int i=0;i<list.size();i++){
			DepartmentInfo a = list.get(i);
			if(a==null)continue;
		%>
		{id:<%=a.getId()-1000000%>, pId:<%=a.getParent_id()-1000000%>,pName:"", name:"<%=a.getDept_name()%>",open:false,nocheck:true},
		<%
		}
		} 
		if(employeeInfos!=null&&employeeInfos.size()>0){
		for(int i=0;i<employeeInfos.size();i++){
			EmployeeInfo a=employeeInfos.get(i);
		%>
		{id:<%=a.getId()%>, pId:<%=a.getDept_id()-1000000%>,pName:"", name:"<%=a.getZh_name()+"("+Util.alternateZero(a.getId())+")"%>",value:"<%=a.getId()%>",open:false }
		<%
		if((i+1)!=employeeInfos.size())out.print(",");
		}
		} %>		
 ];
 var type_zNodes_val_emp_dep = tmpValEmpDep;
 $(function(){
 	var width_ = parseInt($("#emp_name").css('width').replace('px',''));
 	if(width_<150)
 		width_+=100;
 	$("#empTree").css('width',(width_)+'px');
 });
 function emp_ztree(){
 	type_show('emp_name','emp_id','empTree','empContent',type_zNodes_val_emp_dep);
 }
</script>
<input id="emp_name" type="text" class="longinput" readonly value="<%=emp_name %>" onclick="emp_ztree();"/>
<span id="empContent" style="display:none; position: absolute;z-index:99999">
	<ul id="empTree" class="ztree" style="margin-top:0;"></ul>
</span>
<input type="hidden" name="emp_id" value="<%=emp_id %>" id="emp_id"/>

