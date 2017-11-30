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
	String dept_id = StringUtils.defaultIfEmpty(request.getParameter("dept_id"), "");
	String dept_name = StringUtils.defaultIfEmpty(request.getParameter("dept_name"), "");
	boolean noRequireParentNode = Boolean.parseBoolean(StringUtils.defaultIfEmpty(request.getParameter("noRequireParentNode"), "false"));
%>
<script type="text/javascript">
var tmpVal =[
	<%
	if(deptInfoMap!=null&&deptInfoMap.size()>0){
	int count=0;
	for(Integer key:deptInfoMap.keySet()){
		DepartmentInfo a=deptInfoMap.get(key);
		if(a.getState().intValue()==0)continue;
		if(noRequireParentNode&&a.getNode_count()>0)continue;
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
 	if(width_<260)
 		width_=260;
 	$("#parentTree").css('width',width_+'px');
 });
</script>
<link rel="stylesheet" href="${ctx}/js/ztree/css/demo1.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.2.js"></script>
<script type="text/javascript" src="${ctx}/share/js/tree_radio.js"></script>
<style>
<!--
.menuContent {
	left:auto !important;
	top:auto !important;
}
-->
</style>
<input id="dept_name" type="text" style="width: 80%;" readonly value="<%=dept_name %>" title="部门" onclick="type_show('dept_name','requestParam_dept_id','parentTree','parentContent',type_zNodes_val,true)"/>
<div id="parentContent" class="menuContent1" style="display:none ;  position: absolute ; z-index:99999 ;">
	<ul id="parentTree" class="ztree" style="margin-top:0;"></ul>
</div>
<input type="hidden" name="dept_id" value="<%=dept_id %>" id="requestParam_dept_id"/>
&nbsp;
