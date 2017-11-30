<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.authority.service.*"%>
<%@ page import="com.yq.authority.pojo.*"%>
<%@ page import="com.yq.faurecia.pojo.*"%>
<%@ page import="com.yq.faurecia.service.*"%>
<%
	int flow_id = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("flow_id"),"0"));
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(st);
	DepartmentInfoService deptInfoService = (DepartmentInfoService) ctx.getBean("departmentInfoService");
	FlowInfoService flowInfoService = (FlowInfoService) ctx.getBean("flowInfoService");
	
	FlowInfo flowInfo = flowInfoService.queryById(flow_id);
	String empids = "";
	List<DepartmentInfo> result2 = null;
	if(flowInfo!=null){
		for(String step:StringUtils.defaultIfEmpty(flowInfo.getStep_info(), "").split("]")){
			empids +=step.split(",")[1].split("\\|")[0]+",";
		}
		empids = empids.substring(0,empids.length()-1);
		result2 = deptInfoService.findDeptAndEmp(empids);
	}
	List<DepartmentInfo> result = deptInfoService.findDeptAndEmp(null);
	
%>
<jsp:include page="/common/shareJsp/cartZTreeHead.jsp" />
<script type="text/javascript" src="${ctx }/share/js/tow_tree_drag.js"></script>
<script type="text/javascript">
	var zDeptNodes =[
		<%
		if(result!=null&&result.size()>0){
		for(int i=0;i<result.size();i++){
			DepartmentInfo a=result.get(i);
			if(StringUtils.isEmpty(a.getDept_name())||a.getDept_name().indexOf("|")>-1&&(","+empids+",").indexOf(","+Math.abs(a.getId())+",")>-1)continue;
		%>
		{id:<%=a.getId()%>, pId:<%=a.getParent_id()%>,pName:"", name:"<%=a.getDept_name()%>",open:<%=a.getNode_count()==1?"true":"false"%>,chkDisabled:true,node_count:<%=a.getNode_count().intValue()%>}
		<%
		if((i+1)!=result.size())out.print(",");
		}
		} %>
	];
	var zDeptNodes2 =[
		<%
		if(result2!=null&&result2.size()>0){
		for(int i=0;i<result2.size();i++){
			DepartmentInfo a=result2.get(i);
		%>
		{id:<%=a.getId()%>, pId:<%=a.getParent_id()%>,pName:"", name:"<%=a.getDept_name()%>",open:<%=a.getNode_count()==1?"true":"false"%>,node_count:<%=a.getNode_count().intValue()%>}
		<%
		if((i+1)!=result2.size())out.print(",");
		}
		} %>
	];
	/**
	 * 获取id:orderNum排列的json
	 * @param objTreeDemo
	 * @param tree_zNodes
	 */
	function getDragNodesJson() {
		var _str_='';
		var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
		var nodes = zTree.transformToArray(zTree.getNodes());
		for(var i=0;i<nodes.length;i++){
			_str_ += '['+(i+1)+','+Math.abs(nodes[i].id)+'\|'+nodes[i].name+','+(i+1)+']';
		}
		return _str_;
	}

	$(function(){
		type_show(zDeptNodes,zDeptNodes2,'treeDemo','treeDemo2');
	});
</script>
<div>
	<div class="one_half">
		<ul id="treeDemo" class="ztree" style="width:100%"></ul>
	</div>
	<div class="one_half last">
		<ul id="treeDemo2" class="ztree" style="width:100%"></ul>
	</div>				
</div>