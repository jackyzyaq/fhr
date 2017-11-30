<%@ include file="/share/jsp/cartTag.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<link rel="stylesheet" href="${ctx }/share/css/form.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/share/css/style.css" type="text/css" />
<link rel="stylesheet" href="${ctx }/js/ama/css/style.default.css" type="text/css" />
<script type="text/javascript" src="${ctx }/js/ama/js/plugins/jquery-1.7.min.js"></script>
<script type="text/javascript" src="${ctx }/js/ama/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="${ctx }/js/ama/js/plugins/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx }/js/ama/js/plugins/jquery.uniform.min.js"></script>
<script type="text/javascript" src="${ctx }/js/ama/js/plugins/jquery.flot.min.js"></script>
<script type="text/javascript" src="${ctx }/js/ama/js/plugins/jquery.flot.resize.min.js"></script>
<script type="text/javascript" src="${ctx }/js/ama/js/plugins/jquery.slimscroll.js"></script>
<script type="text/javascript" src="${ctx }/js/ama/js/plugins/jquery.alerts.js"></script>
<script type="text/javascript" src="${ctx }/js/ama/js/plugins/jquery.jgrowl.js"></script>
<script type="text/javascript" src="${ctx }/js/ama/js/plugins/ui.spinner.min.js"></script>


<script type="text/javascript" src="${ctx }/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.fileDownload.js"></script>


<script type="text/javascript" src="${ctx }/share/js/util.js"></script>
<script type="text/javascript" src="${ctx }/share/js/general.js"></script>
<script type="text/javascript" src="${ctx }/share/js/guid.js"></script>

<script type="text/javascript" src="${ctx}/js/DatePicker/WdatePicker.js"></script>
<%-- <%
Integer _menu_id_ = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("menu_id"), "0"));
Integer _parent_menu_id_ = Global.menuInfoMap.get(_menu_id_)==null?0:Global.menuInfoMap.get(_menu_id_).getParent_id();
%>
<script type="text/javascript">
var _menu_id = <%=_menu_id_%>;
var _parent_menu_id = <%=_parent_menu_id_%>;
</script>
 --%>