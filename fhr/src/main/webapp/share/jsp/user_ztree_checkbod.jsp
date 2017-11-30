<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.authority.service.*"%>
<%@ page import="com.yq.authority.pojo.*"%>
<%
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(st);
	UserInfoService userInfoService = (UserInfoService) ctx.getBean("userInfoService");
	
	boolean show_sub = StringUtils.isEmpty(request.getParameter("show_sub"))?false:Boolean.parseBoolean(request.getParameter("show_sub"));
	String user_id = StringUtils.defaultIfEmpty(request.getParameter("user_id"),"0");
	String user_name = StringUtils.isEmpty(request.getParameter("user_name"))?"根目录":request.getParameter("user_name");
	
	List<UserInfo> userInfos = null;
	
	UserInfo userInfo = new UserInfo();
	userInfo.setState(1);
	userInfos = userInfoService.findByCondition(userInfo,null);
%>
<script type="text/javascript">
var tmpVal =[
	<%
	if(userInfos!=null&&userInfos.size()>0){
	for(int i=0;i<userInfos.size();i++){
		UserInfo a=userInfos.get(i);
	%>
	{id:<%=a.getId()%>, pId:0,pName:"", name:"<%=a.getZh_name()+"("+(a.getName())+")"%>",value:"<%=a.getName()%>",open:false }
	<%
	if((i+1)!=userInfos.size())out.print(",");
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
<input id="parent" type="text" class="mediuminput" readonly value="<%=user_name %>" onclick=""/>
<span id="parentContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="parentTree" class="ztree" style="margin-top:0;"></ul>
</span>
<input type="hidden" name="parent_id" value="<%=user_id %>" id="requestParam_parent_id"/>

