<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.authority.service.*"%>
<%@ page import="com.yq.authority.pojo.*"%>
<%
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(st);
	RoleInfoService roleInfoService = (RoleInfoService) ctx.getBean("roleInfoService");
	
	boolean show_sub = StringUtils.isEmpty(request.getParameter("show_sub"))?false:Boolean.parseBoolean(request.getParameter("show_sub"));
	String role_id = StringUtils.defaultIfEmpty(request.getParameter("role_id"),"0");
	String role_name = StringUtils.isEmpty(request.getParameter("role_name"))?"根目录":request.getParameter("role_name");
	
	List<RoleInfo> roleInfos = null;
	
	RoleInfo mi = new RoleInfo();
	mi.setState(1);
	if(show_sub)
		mi.setParent_id(0);
	roleInfos = roleInfoService.findByCondition(mi,null);
%>
<script type="text/javascript">
var tmpVal =[
	<%
	if(roleInfos!=null&&roleInfos.size()>0){
	for(int i=0;i<roleInfos.size();i++){
		RoleInfo a=roleInfos.get(i);
	%>
	{id:<%=a.getId()%>, pId:<%=a.getParent_id()%>,pName:"", name:"<%=a.getRole_name()%>",value:"<%=a.getRole_code()%>",open:false }
	<%
	if((i+1)!=roleInfos.size())out.print(",");
	}
	} %>
 ];
 var type_zNodes_val = tmpVal;
 $(function(){
 	$("#parentTree").css('width',$("#parent").css('width'));
 	$("#parentTree").css('height',200);
 });
</script>
<link rel="stylesheet" href="${ctx}/js/ztree/css/demo1.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.2.js"></script>
<script type="text/javascript" src="${ctx}/share/js/tree_checkbox.js"></script>
<input id="parent" type="text" class="mediuminput" style="width:250px" readonly value="<%=role_name %>" onclick="type_show('parent','requestParam_parent_id','parentTree','parentContent',type_zNodes_val,true);"/>
<span id="parentContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="parentTree" class="ztree" style="margin-top:0px;margin-left:0px;"></ul>
</span>
<input type="hidden" name="parent_id" value="<%=role_id %>" id="requestParam_parent_id"/>

