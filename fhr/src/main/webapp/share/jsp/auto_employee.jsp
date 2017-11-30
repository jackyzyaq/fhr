<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%
String deptIdsRole = (String) session.getAttribute("deptIdsRole");
String emp_name = StringUtils.defaultIfEmpty(request.getParameter("emp_name"), "");
String emp_id_str = StringUtils.defaultIfEmpty(request.getParameter("emp_id_str"), "t");
String dept_id_str = StringUtils.defaultIfEmpty(request.getParameter("dept_id_str"), "t");
String dept_name_str = StringUtils.defaultIfEmpty(request.getParameter("dept_name_str"), "t");
String emp_code_str = StringUtils.defaultIfEmpty(request.getParameter("emp_code_str"), "t");
String searchBtn_str = StringUtils.defaultIfEmpty(request.getParameter("searchBtn_str"), "t");
String parent_div = StringUtils.defaultIfEmpty(request.getParameter("parent_div"), "t");

%>
<style type="text/css">
.autocomplete-suggestions { border: 1px solid #999; background: #FFF; cursor: default; overflow: auto; -webkit-box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); -moz-box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); }
.autocomplete-suggestion { padding: 2px 5px; white-space: nowrap; overflow: hidden; }
.autocomplete-no-suggestion { padding: 2px 5px;}
.autocomplete-selected { background: #F0F0F0; }
.autocomplete-suggestions strong { font-weight: bold; color: #000; }
.autocomplete-group { padding: 2px 5px; }
.autocomplete-group strong { font-weight: bold; font-size: 16px; color: #000; display: block; border-bottom: 1px solid #000; }
</style>
    <script type="text/javascript" src="${ctx }/js/autocomplete/scripts/jquery.autocomplete.js"></script>
	<script type="text/javascript">
	$(function () {
	    $('<%=parent_div%> #emp_name').autocomplete({
	        serviceUrl: '${ctx}/common/employeeInfo/autoComplete.do?dept_ids=<%=deptIdsRole%>',
	        paramName:'emp_name',
    		onSelect: function (suggestion) {
        		//alert('You selected: ' + suggestion.zh_name + ', ' + suggestion.cgi);
        		//$("#emp_id").val(suggestion.id);
        		if($("<%=parent_div%> #<%=emp_id_str%>").length>0){
        			$("<%=parent_div%> #<%=emp_id_str%>").val(suggestion.emp_id);
        		}
        		if($("<%=parent_div%> #<%=dept_id_str%>").length>0){
        			$("<%=parent_div%> #<%=dept_id_str%>").val(suggestion.dept_id);
        		}
        		if($("<%=parent_div%> #<%=dept_name_str%>").length>0){
        			$("<%=parent_div%> #<%=dept_name_str%>").val(suggestion.dept_name);
        		}
        		if($("<%=parent_div%> #<%=emp_code_str%>").length>0){
        			$("<%=parent_div%> #<%=emp_code_str%>").val(suggestion.emp_code);
        		}
        		
        		if($("<%=parent_div%> #<%=searchBtn_str%>").length>0){
        			$("<%=parent_div%> #<%=searchBtn_str%>").trigger("click");
        		}
    		},
    		onSearchComplete: function (query, suggestions) {
    		},
    		onSearchStart: function () {
    			if($("<%=parent_div%> #<%=emp_id_str%>").length>0){
	        		$("<%=parent_div%> #<%=emp_id_str%>").val('');
	   				$("<%=parent_div%> #<%=emp_id_str%>").trigger('click');
	        	}
	        	if($("<%=parent_div%> #<%=dept_id_str%>").length>0){
	        		$("<%=parent_div%> #<%=dept_id_str%>").val('');
	   				$("<%=parent_div%> #<%=dept_id_str%>").trigger('click');
	        	}
	        	if($("<%=parent_div%> #<%=dept_name_str%>").length>0){
	        		$("<%=parent_div%> #<%=dept_name_str%>").val('');
	   				$("<%=parent_div%> #<%=dept_name_str%>").trigger('click');
	        	}
	        	if($("<%=parent_div%> #<%=emp_code_str%>").length>0){
	        		$("<%=parent_div%> #<%=emp_code_str%>").val('');
	   				$("<%=parent_div%> #<%=emp_code_str%>").trigger('click');
	        	}
    		},
    		formatResult: function (suggestion, currentValue) {
    			currentValue = suggestion.value+'('+suggestion.dept_name+')';
    			return currentValue;
    		}
	    });
	});
	</script>
    <input type="text" name="emp_name" id="emp_name" class="longinput" value="<%=emp_name%>"/>
