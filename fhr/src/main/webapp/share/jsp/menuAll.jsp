<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.authority.pojo.*"%>
<%@ page import="com.yq.authority.service.*"%>
<%
    Map<Integer,MenuInfo> menuInfoMap = (Map<Integer,MenuInfo>)session.getAttribute("menuRole");
    String menu_id = StringUtils.defaultIfEmpty(request.getParameter("menu_id"), "0");
    ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(st);
	MenuInfoService menuInfoService = (MenuInfoService) ctx.getBean("menuInfoService");
	String allMenu = menuInfoService.getMenuAllNameById(Integer.parseInt(menu_id));
%>
<%if(!StringUtils.isEmpty(allMenu)){ %>
<div>
	<ul class="breadcrumbs breadcrumbs2">
	<%for(String m:allMenu.split(">>")){ %>
		<li><a style="cursor:pointer;"><%=m %></a></li>
	<%} %>
	</ul>	
</div>
<%} %>

