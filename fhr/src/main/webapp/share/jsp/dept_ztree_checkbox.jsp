<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.authority.service.*"%>
<%@ page import="com.yq.authority.pojo.*"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%@ page import="com.yq.faurecia.service.*"%>
<%
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(st);
	DepartmentInfoService departmentInfoService=(DepartmentInfoService)ctx.getBean("departmentInfoService");
	
	boolean show_sub = StringUtils.isEmpty(request.getParameter("show_sub"))?false:Boolean.parseBoolean(request.getParameter("show_sub"));
	String dept_id = StringUtils.defaultIfEmpty(request.getParameter("dept_id"),"0");
	String dept_name = StringUtils.isEmpty(request.getParameter("dept_name"))?"根目录":request.getParameter("dept_name");
	
	List<DepartmentInfo> deptInfos = null;
	
	DepartmentInfo deptInfo = new DepartmentInfo();
	deptInfo.setState(1);
	deptInfos = departmentInfoService.findByCondition(deptInfo,null);
%>
<script type="text/javascript">
var tmpVal =[
	<%
	if(deptInfos!=null&&deptInfos.size()>0){
	for(int i=0;i<deptInfos.size();i++){
		DepartmentInfo a=deptInfos.get(i);
	%>
	{id:<%=a.getId()%>, pId:<%=a.getParent_id()%>,pName:"", name:"<%=a.getDept_name()%>",value:"<%=a.getDept_code()%>",open:false }
	<%
	if((i+1)!=deptInfos.size())out.print(",");
	}
	} %>
 ];
 var type_zNodes_val = tmpVal;
 $(function(){
 	$("#parentTree").css('width',$("#parent").css('width'));
 	type_show('parent','requestParam_parent_id','parentTree','parentContent',type_zNodes_val);
 });
</script>
<link rel="stylesheet" href="${ctx}/js/ztree/css/demo1.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.2.js"></script>
<script type="text/javascript" src="${ctx}/share/js/tree_checkbox.js"></script>
<input id="parent" type="text" class="mediuminput" readonly value="<%=dept_name %>" onclick=""/>
<span id="parentContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="parentTree" class="ztree" style="margin-top:0;"></ul>
</span>
<input type="hidden" name="parent_id" value="<%=dept_id %>" id="requestParam_parent_id"/>

