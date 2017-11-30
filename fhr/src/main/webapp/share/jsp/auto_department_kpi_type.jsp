<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/share/jsp/cartTag.jsp"%>
<%
String kpi_type = StringUtils.defaultIfEmpty(request.getParameter("kpi_type"), "");
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
	    $('<%=parent_div%> #kpi_type').autocomplete({
	        serviceUrl: '${ctx}/common/departmentKPI/autoComplete.do?',
	        paramName:'kpi_type',
    		onSelect: function (suggestion) {
    		},
    		onSearchComplete: function (query, suggestions) {
    		},
    		onSearchStart: function () {
    		},
    		formatResult: function (suggestion, currentValue) {
    			currentValue = suggestion.value;
    			return currentValue;
    		}
	    });
	});
	</script>
    <input type="text" name="kpi_type" id="kpi_type" class="longinput" value="<%=kpi_type%>"/>
