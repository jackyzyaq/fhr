<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%@ page import="com.yq.company.etop5.service.*"%>
<%@ page import="com.yq.company.etop5.pojo.*"%>
<%
	ServletContext st = this.getServletConfig().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(st);
	KPITypeService kpiTypeService = (KPITypeService) ctx.getBean("KPITypeService");
	
	boolean show_sub = StringUtils.isEmpty(request.getParameter("show_sub"))?false:Boolean.parseBoolean(request.getParameter("show_sub"));
	int kpi_type_id = StringUtils.isEmpty(request.getParameter("kpi_type_id"))?-1:Integer.parseInt(request.getParameter("kpi_type_id"));
	int parent_kpi_type_id = StringUtils.isEmpty(request.getParameter("parent_kpi_type_id"))?0:Integer.parseInt(request.getParameter("parent_kpi_type_id"));
	String parent_kpi_type_name = StringUtils.isEmpty(request.getParameter("parent_kpi_type_name"))?"-根目录-":request.getParameter("parent_kpi_type_name");
	List<KPIType> kpi_typeInfos = null;
	KPIType di = new KPIType();
	di.setState(1);
	if(show_sub)
		di.setParent_id(0);
	String bigType = StringUtils.defaultIfEmpty(request.getParameter("bigType"), "");
	if(!StringUtils.isEmpty(bigType)){
		di.setName(bigType);
		kpi_typeInfos = kpiTypeService.findByCondition(di,null);
		di.setName("");
		String ids = "0,";
		if(kpi_typeInfos!=null&&!kpi_typeInfos.isEmpty()){
			for(KPIType kt:kpi_typeInfos){
				ids +=kpiTypeService.getSubIdsById(kt.getId(), null)+",";
			}
		}
		di.setIds(ids.endsWith(",")?ids.substring(0,ids.length()-1):ids);
	}
	kpi_typeInfos = kpiTypeService.findByCondition(di,null);
	
%>
<script type="text/javascript">
var tmpVal =[
	<%
	if(kpi_typeInfos!=null&&kpi_typeInfos.size()>0){
	for(int i=0;i<kpi_typeInfos.size();i++){
		KPIType a=kpi_typeInfos.get(i);
		String all_name = kpiTypeService.getKPITypeAllNameById(a.getId());
	%>
	{id:<%=a.getId()%>, pId:<%=a.getParent_id()%>,pName:"", name:"<%=a.getName()%>",value:"<%=all_name%>",chkDisabled:<%=(kpi_type_id==a.getId().intValue()?"true":"false")%> }
	<%
	if((i+1)!=kpi_typeInfos.size())out.print(",");
	}
	} %>
 ];
 var type_zNodes_val = tmpVal;
 $(function(){
 	if(parseInt($("#parent").css('width'))<150){
 		$("#parentTree").css('width',"150px");
 	}else{
 		$("#parentTree").css('width',$("#parent").css('width'));
 	}
 	$("#parentTree").css('height',"250px");
 });
</script>
<link rel="stylesheet" href="${ctx}/js/ztree/css/demo1.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="${ctx}/js/ztree/js/jquery.ztree.excheck-3.2.js"></script>
<script type="text/javascript" src="${ctx}/share/js/tree_radio1.js"></script>
<input id="parent" type="text" title="所属类型" class="mediuminput" readonly value="<%=parent_kpi_type_name %>" onclick="type_show('parent','requestParam_parent_id','parentTree','parentContent',type_zNodes_val);"/>
<span id="parentContent" class="kpi_typeContent" style="display:none; position: absolute;top:0px;z-index:99999">
	<ul id="parentTree" class="ztree" style="margin-top:0px;margin-left: 0px;"></ul>
</span>
<input type="hidden" name="parent_id" value="<%=parent_kpi_type_id %>" id="requestParam_parent_id"/>

