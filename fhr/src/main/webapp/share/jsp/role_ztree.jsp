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
	int role_id = StringUtils.isEmpty(request.getParameter("role_id"))?-1:Integer.parseInt(request.getParameter("role_id"));
	int parent_role_id = StringUtils.isEmpty(request.getParameter("parent_role_id"))?0:Integer.parseInt(request.getParameter("parent_role_id"));
	String parent_role_name = StringUtils.isEmpty(request.getParameter("parent_role_name"))?"根目录":request.getParameter("parent_role_name");
	
	List<RoleInfo> roleInfos = null;
	
	RoleInfo ri = new RoleInfo();
	ri.setState(1);
	if(show_sub)
		ri.setParent_id(0);
	roleInfos = roleInfoService.findByCondition(ri,null);
%>
<script type="text/javascript">
var tmpVal =[
	<%
	if(roleInfos!=null&&roleInfos.size()>0){
	for(int i=0;i<roleInfos.size();i++){
		RoleInfo a=roleInfos.get(i);
	%>
	{id:<%=a.getId()%>, pId:<%=a.getParent_id()%>,pName:"", name:"<%=a.getRole_name()%>",value:"<%=a.getRole_code()%>",chkDisabled:<%=(role_id==a.getId().intValue()?"true":"false")%> }
	<%
	if((i+1)!=roleInfos.size())out.print(",");
	}
	} %>
 ];
 var type_zNodes_val = tmpVal;
 $(function(){
 	$("#parentTree").css('width',$("#parent").css('width'));
 });
</script>
<link rel="stylesheet" href="${ctx}/js/ztree/css/demo1.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.2.js"></script>
<script type="text/javascript" src="${ctx}/share/js/tree_radio.js"></script>
<input id="parent" type="text" class="mediuminput" readonly value="<%=parent_role_name %>" onclick="type_show('parent','requestParam_parent_id','parentTree','parentContent',type_zNodes_val);"/>
<span id="parentContent" class="roleContent" style="display:none; position: absolute;">
	<ul id="parentTree" class="ztree" style="margin-top:0;"></ul>
</span>
<input type="hidden" name="parent_id" value="<%=parent_role_id %>" id="requestParam_parent_id"/>

