<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.authority.service.*"%>
<%@ page import="com.yq.authority.pojo.*"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%@ page import="com.yq.faurecia.service.*"%>
<%
	Map<Integer,DepartmentInfo> deptInfoMap = (Map<Integer,DepartmentInfo>)session.getAttribute("deptRole");
	String deptIdsRole = StringUtils.defaultIfEmpty((String)session.getAttribute("deptIdsRole"),"0");
	String deptNamesRole = StringUtils.defaultIfEmpty((String)session.getAttribute("deptNamesRole"),"");
	boolean show_sub = StringUtils.isEmpty(request.getParameter("show_sub"))?false:Boolean.parseBoolean(request.getParameter("show_sub"));
	
%>
<script type="text/javascript">
var tmpVal =[
	<%
	if(deptInfoMap!=null&&deptInfoMap.size()>0){
	int count=0;
	for(Integer key:deptInfoMap.keySet()){
		DepartmentInfo a=deptInfoMap.get(key);
	%>
	{id:<%=a.getId()%>, pId:<%=a.getParent_id()%>,pName:"", name:"<%=a.getDept_name()%>",value:"<%=a.getDept_code()%>",open:false }
	<%
	if((count+1)!=deptInfoMap.size())out.print(",");
	count++;}
	} %>
 ];
 var type_zNodes_val = tmpVal;
 $(function(){
 	var width_ = parseInt($("#dept_name").css('width').replace('px',''));
 	$("#dept_name").css('width',width_+'px');
 });
</script>
<link rel="stylesheet" href="${ctx}/js/ztree/css/demo1.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.2.js"></script>
<script type="text/javascript" src="${ctx}/share/js/tree_checkbox.js"></script>
部门：&nbsp;<input id="dept_name" type="text" style="width: 25%;" readonly value="<%=deptNamesRole %>" title="<%=deptNamesRole %>" onclick="type_show('dept_name','requestParam_dept_ids','parentTree','parentContent',type_zNodes_val,true)"/>
<span id="parentContent" class="menuContent" style="display:none; position: absolute;z-index:99999">
	<ul id="parentTree" class="ztree" style="margin-top:0;"></ul>
</span>
<input type="hidden" name="dept_ids" value="<%=deptIdsRole %>" id="requestParam_dept_ids"/>
&nbsp;
