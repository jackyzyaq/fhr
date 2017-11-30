<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%
	String root_div_id = StringUtils.defaultIfEmpty(request.getParameter("root_div_id"), "tttt");
	String pojo_object = StringUtils.defaultIfEmpty(request.getParameter("pojo_object"), "tttt");
	String id = StringUtils.defaultIfEmpty(request.getParameter("id"), "0");
%>
<script type="text/javascript" src="${ctx }/js/menu/js/jquery.contextmenu.js"></script>
<link rel="stylesheet" href="${ctx }/js/menu/css/jquery.contextmenu.css" type="text/css" />
<script type="text/javascript">
	var root_div_id = '<%=root_div_id%>';
	$(function () {
		var valForm = $("#"+root_div_id).find("*");
		$.each(valForm,function(i,v){
				if($(this).is('input')){
					if(v.type!='file'&&v.type!='hidden'&&typeof(v.id) != "undefined") {
						rightClickMenu(v.id,v.name,v.title);
					}
				}
				if($(this).is('select')){
					if (typeof($(this).attr("multiple")) == "undefined") {
						if(typeof(v.id) != "undefined") {
							rightClickMenu(v.id,v.name,v.title);
						}
					}else{
					}
				}
				if($(this).is('textarea')){
					if(typeof(v.id) != "undefined") {
						rightClickMenu(v.id,v.name,v.title);
					}
				}
		});
	});
	function rightClickMenu(field_id,fieldname,title){
		if(field_id.length>0){
			$('#'+root_div_id+' #'+field_id).contextPopup({
				//title: 'My Popup Menu',
				items: [
					{label:'查询历史记录',icon:'${ctx }/js/menu/icons/book-open-list.png', action:function() {
						showHtml(ctx+"/share/jsp/operation_record.jsp?id=<%=id%>&pojo_object=<%=pojo_object%>&pojo_attr="+fieldname,title,500);
						}
					}
				]
			});
		}
	}
</script>
