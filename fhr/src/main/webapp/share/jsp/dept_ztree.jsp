<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.faurecia.service.*"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(st);
	DepartmentInfoService deptInfoService = (DepartmentInfoService) ctx.getBean("departmentInfoService");
	
	boolean show_sub = StringUtils.isEmpty(request.getParameter("show_sub"))?false:Boolean.parseBoolean(request.getParameter("show_sub"));
	int dept_id = StringUtils.isEmpty(request.getParameter("dept_id"))?-1:Integer.parseInt(request.getParameter("dept_id"));
	int parent_dept_id = StringUtils.isEmpty(request.getParameter("parent_dept_id"))?0:Integer.parseInt(request.getParameter("parent_dept_id"));
	String parent_dept_name = StringUtils.isEmpty(request.getParameter("parent_dept_name"))?"根目录":request.getParameter("parent_dept_name");
	
	List<DepartmentInfo> deptInfos = null;
	
	DepartmentInfo di = new DepartmentInfo();
	di.setState(1);
	if(show_sub)
		di.setParent_id(0);
	deptInfos = deptInfoService.findByCondition(di,null);
%>
<script type="text/javascript">
var tmpVal =[
	<%
	if(deptInfos!=null&&deptInfos.size()>0){
	for(int i=0;i<deptInfos.size();i++){
		DepartmentInfo a=deptInfos.get(i);
	%>
	{id:<%=a.getId()%>, pId:<%=a.getParent_id()%>,pName:"", name:"<%=a.getDept_name()%>",value:"<%=a.getDept_code()%>",chkDisabled:<%=(dept_id==a.getId().intValue()?"true":"false")%> }
	<%
	if((i+1)!=deptInfos.size())out.print(",");
	}
	} %>
 ];
 var type_zNodes_val = tmpVal;
 $(function(){
 	if(parseInt($("#parent").css('width'))<150){
 		$("#parentTree").css('width',"150px");
 	}else{
 		$("#parentTree").css('width',$("#parent").css('width'));
 	}
 	$("#parentTree").css('height',"250px");
 });
</script>
<link rel="stylesheet" href="${ctx}/js/ztree/css/demo1.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.2.js"></script>
<script type="text/javascript" src="${ctx}/share/js/tree_radio.js"></script>
<input id="parent" type="text" title="所属部门" class="mediuminput" readonly value="<%=parent_dept_name %>" onclick="type_show('parent','requestParam_parent_id','parentTree','parentContent',type_zNodes_val);"/>
<span id="parentContent" class="deptContent" style="display:none; position: absolute;top:0px;z-index:99999">
	<ul id="parentTree" class="ztree" style="margin-top:0px;margin-left: 0px;"></ul>
</span>
<input type="hidden" name="parent_id" value="<%=parent_dept_id %>" id="requestParam_parent_id"/>

