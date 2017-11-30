<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%@ page import="com.yq.faurecia.service.*"%>
<%
    String dept_id = StringUtils.defaultIfEmpty(request.getParameter("dept_id"), "0");
	String zh_name = StringUtils.defaultIfEmpty(request.getParameter("zh_name"), "");

    ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(st);
	EmployeeInfoService employeeInfoService = (EmployeeInfoService) ctx.getBean("employeeInfoService");
	
	String allName = employeeInfoService.getLeaderAllNameByDeptId(Integer.parseInt(dept_id),zh_name);
%>
<%if(!StringUtils.isEmpty(allName)){ %>
<span>&nbsp; (&nbsp;<%=allName %> &nbsp;)</span>
<%} %>

