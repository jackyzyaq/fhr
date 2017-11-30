<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.authority.service.*"%>
<%@ page import="com.yq.authority.pojo.*"%>
<%
	int id = StringUtils.isEmpty(request.getParameter("id"))?-1:Integer.parseInt(request.getParameter("id"));
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(st);
	MenuInfoService menuInfoService = (MenuInfoService) ctx.getBean("menuInfoService");
	
	MenuInfo mi = new MenuInfo();
	mi.setState(1);
	List<MenuInfo> menuInfos = menuInfoService.findByCondition(mi,null);
%>
<script type="text/javascript">
var tmpVal =[
	{id:0, pId:-1,pName:"",orderNum:0, name:"根",value:"_root_",open:true,chkDisabled:true,childOuter:false },
	<%
	if(menuInfos!=null&&menuInfos.size()>0){
	for(int i=0;i<menuInfos.size();i++){
		MenuInfo a=menuInfos.get(i);
	%>
	{id:<%=a.getId()%>, pId:<%=a.getParent_id()%>,pName:"",orderNum:<%=a.getOrderNum()==null?0:a.getOrderNum().intValue()%>, name:"<%=a.getMenu_name()%>",value:"<%=a.getMenu_code()%>",open:true,chkDisabled:<%=(id==a.getId().intValue()?"true":"false")%>,<%=a.getNode_count().intValue()>0?"childOuter:false":"dropInner:false"%> }
	<%
	if((i+1)!=menuInfos.size())out.print(",");
	}
	} %>
 ];
 var type_zNodes_val = tmpVal;
 $(function(){
 	$("#menuDrag").css('width','60%');
 	type_show('menuDrag',type_zNodes_val);
 });
</script>
<link rel="stylesheet" href="${ctx}/js/ztree/css/demo1.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.exedit-3.2.min.js"></script>
<script type="text/javascript" src="${ctx}/share/js/tree_drag.js"></script>

<ul id="menuDrag" class="ztree"></ul>