<%@page import="com.yq.faurecia.service.DepartmentInfoService"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.company.etop5.pojo.*"%>
<%@ page import="com.yq.company.etop5.service.*"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%@ page import="com.yq.authority.pojo.*"%>
<%@ page import="com.yq.authority.service.*"%>
<%
	Map<Integer, MenuInfo> menuInfoMap = (Map<Integer, MenuInfo>) session.getAttribute("menuRole");
	String number = StringUtils.defaultIfEmpty(request.getParameter("number"), "");
	int mi229 = 229;
	MenuInfo mi229Parent = null;
	if(!StringUtils.isEmpty(Util.getMenuAllIdsById(mi229,menuInfoMap).split(",")[0])){
		mi229Parent = menuInfoMap.get(Integer.parseInt(Util.getMenuAllIdsById(mi229,menuInfoMap).split(",")[0]));
	}

%>
<a style="cursor: pointer;" onclick="jumpLabelPage('/portal/portal_left.jsp','<%=mi229Parent.getId()%>','<%=mi229%>','<%="number:"+number%>');"><%=number %></a>