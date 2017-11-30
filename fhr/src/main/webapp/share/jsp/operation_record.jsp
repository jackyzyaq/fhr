<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.common.pojo.*"%>
<%@ page import="com.yq.common.service.*"%>
<%
	SimpleDateFormat sdf = new SimpleDateFormat(Global.DATE_FORMAT_STR_A);
	String pojo_object = StringUtils.defaultIfEmpty(request.getParameter("pojo_object"), "tttt");
	String pojo_attr = StringUtils.defaultIfEmpty(request.getParameter("pojo_attr"), "tttt");
	int id = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("id"), "tttt"));
	
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(st);
	OperationRecordService operationRecordService = (OperationRecordService) ctx.getBean("operationRecordService");
	
	OperationRecord or = new OperationRecord();
	or.setOperation_object(pojo_object);
	//or.setObject_id(id);
	or.setOperation_content("id||||"+id+"||||id");
	List<OperationRecord> orList = operationRecordService.findByCondition(or, null);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/common/shareJsp/cartHead.jsp" />
<script type="text/javascript" src="${ctx }/faurecia/common/class_info/js/class_info.js"></script>
<script type="text/javascript" src="${ctx }/faurecia/common/class_info/js/class_info_ext.js"></script>
</head>
<body>
	<div id="contentwrapper" class="contentwrapper" style="width:90%;">
		<form class="stdform stdform2" onSubmit="return false;">
			<div>
				<div class="widgetbox">
					<div class="widgetcontent padding0 statement">
				   		<table id="class_info_table" cellpadding="0" cellspacing="0" border="0" class="stdtable">
					        <thead>
					            <tr>
					                <th class="head1">操作者</th>
					                <th class="head1">对象记录</th>
					                <th class="head1">操作时间</th>
					            </tr>
					        </thead>
					        <tbody>
					        <%if(orList!=null&&orList.size()>0){ 
					        	for(OperationRecord orTmp:orList){
					        	if(pojo_attr.equals("tttt"))continue;
					        %>
					        <tr>
					        	<td style="font-weight:bold;" align="center"><%=StringUtils.defaultIfEmpty(orTmp.getUser_name(), "") %></td>
					        	<td style="font-weight:bold;" align="center"><%=StringUtils.defaultIfEmpty(StringUtils.substringBetween(StringUtils.defaultIfEmpty(orTmp.getOperation_content(), ""), pojo_attr, pojo_attr),"").replace("||||","") %></td>
					        	<td style="font-weight:bold;" align="center"><%=Util.convertToString(orTmp.getCreate_date()) %></td>
					        </tr>
					        <%}}else{ %>
					        <tr>
					        	<td colspan="3">无数据</td>
					        </tr>
					        <%} %>
			         		</tbody>
			       		</table>
			     	</div>
			   </div>				
			</div>
		</form>
	</div>
</body>
</html>