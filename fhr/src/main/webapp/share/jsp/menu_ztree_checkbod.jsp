<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.authority.service.*"%>
<%@ page import="com.yq.authority.pojo.*"%>
<%
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(st);
	MenuInfoService menuInfoService = (MenuInfoService) ctx.getBean("menuInfoService");
	
	boolean show_sub = StringUtils.isEmpty(request.getParameter("show_sub"))?false:Boolean.parseBoolean(request.getParameter("show_sub"));
	String parent_menu_id = StringUtils.defaultIfEmpty(request.getParameter("parent_menu_id"),"0");
	String parent_menu_name = StringUtils.isEmpty(request.getParameter("parent_menu_name"))?"根目录":request.getParameter("parent_menu_name");
	
	List<MenuInfo> menuInfos = null;
	
	MenuInfo mi = new MenuInfo();
	mi.setState(1);
	if(show_sub)
		mi.setParent_id(0);
	menuInfos = menuInfoService.findByCondition(mi,null);
%>
<script type="text/javascript">
var tmpVal =[
	<%
	if(menuInfos!=null&&menuInfos.size()>0){
	for(int i=0;i<menuInfos.size();i++){
		MenuInfo a=menuInfos.get(i);
	%>
	{id:<%=a.getId()%>, pId:<%=a.getParent_id()%>,pName:"", name:"<%=a.getMenu_name()%>",value:"<%=a.getMenu_code()%>",open:false }
	<%
	if((i+1)!=menuInfos.size())out.print(",");
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
<input id="parent" type="text" class="mediuminput" readonly value="<%=parent_menu_name %>" onclick=""/>
<span id="parentContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="parentTree" class="ztree" style="margin-top:0;"></ul>
</span>
<input type="hidden" name="parent_id" value="<%=parent_menu_id %>" id="requestParam_parent_id"/>

