<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE HTML>
<html>
  <head> 
    <meta charset="UTF-8"> 
    <link rel="Bookmark" type="image/x-icon" href="${ctx}/images/icon/favicon.ico" />
	<link rel="icon" href="${ctx}/images/icon/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${ctx}/images/icon/favicon.ico" type="image/x-icon" />
	<title>合理化建议管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css?v=2" />
    <link rel="stylesheet" href="${ctx}/static/css/manager_style01.css?v=107" />
    <link rel="stylesheet" href="${ctx}/static/components/bootstrapdatetimepicker/css/bootstrap-datetimepicker.min.css">
    <link href="${ctx}/static/components/ickeck/css/minimal/blue.css" rel="stylesheet">
    <link href="${ctx}/static/components/ickeck/css/flat/blue.css" rel="stylesheet">
    
    <style>
    	.datetimepicker table tr td span.active, .datetimepicker table tr td span.active:hover, .datetimepicker table tr td span.active.disabled, .datetimepicker table tr td span.active.disabled:hover{
    		background-image: none !important;
    		background-color: #ff8903 !important;
    	}
    	
    	.datetimepicker table tr td.today, .datetimepicker table tr td.today:hover, .datetimepicker table tr td.disabled.today, .datetimepicker table tr td.disabled.today:hover{
    		background-image: none !important;
    		background-color: #ff8903 !important;
    	}
    	
    	
    </style>
   
    <script type="text/javascript" src="${ctx}/static/js/common.js?v=1005"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/bootstrap.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/notify.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/placeholder.js"></script>
	<script type="text/javascript" src="${ctx}/static/components/ickeck/js/icheck.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/components/bootstrapdatetimepicker/js/bootstrap-datetimepicker.min.js" ></script>
 	<script type="text/javascript" src="${ctx}/static/components/bootstrapdatetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" ></script>
 	
 	<!-- validation -->
	<script type="text/javascript" src="${ctx}/static/components/jquery-validation/1.10.0/jquery.validate.js"></script>
	<script type="text/javascript" src="${ctx}/static/components/jquery-validation/1.10.0/localization/messages_zh.js"></script>
    
    <script type="text/javascript" src="${ctx}/static/components/layer/layer.js"></script>
    <decorator:head></decorator:head>
    
  </head>
  <body >
  
  		
  		<c:if test="${layoutstyle != 'justpage'}">
  			<%@ include file="../layout/header.jsp" %>
	    	<div style="clear: both;"></div>
	    	<div align="center" style="margin-top: 20px;margin-bottom: 20px;padding-left: 20px;padding-right: 20px;" class="maincontent">
	    		<table style="background-color: #ffffff;width: 1200px;min-height: 500px;">
		    		<tr>
		    			<td style="vertical-align: top;border-right:solid 1px #d9dadc;width:14%;max-width: 200px;" align="left">
		    				<%@ include file="../layout/leftmenus.jsp" %>
		    			</td>
		    			<td style="vertical-align: top;width:86%;max-width: 960px;" align="left">
		    				<decorator:body></decorator:body>
		    			</td>
		    		</tr>
		    	</table>	
	    	</div>
			<div style="clear: both;"></div>
			<%@ include file="../layout/footer.jsp" %>	
  		</c:if>
  		
  		<c:if test="${layoutstyle == 'justpage'}">
  			<style>
  				html,body{
  					background-color: #ffffff;
  				}
  			</style>
	  		<div align="center" style="margin-top: 20px;margin-bottom: 20px;padding-left: 20px;padding-right: 20px;background-color: #ffffff;" >
	  			<decorator:body></decorator:body>
	  		</div>
  		</c:if>
  		
    	
		
		
		<div class="modal" id="selectmodal" tabindex="-1" role="dialog">
		   	<div class="modal-dialog self-modal" role="document" style="width:400px;">
		   		<div class="modal-content">
		   			<div class="modal-header">
		   				<h4 class="modal-title">选择提示</h4>
		   			</div>
		   			<div class="modal-body" style="padding:0;">
		   				<div id="selectmodal_content" align="left" style="padding: 20px;"></div>
		   			</div>
		   			<div class="modal-footer text-center">
		   				<button id="selectmodal_close" type="button" data-dismiss="modal" class="btn btn-default">取消</button>
		   				<button id="selectmodal_ok" type="button" class="btn btn-primary btn-edit-employee btn-yellow">确认</button>
		   			</div>
		   		</div>
		   	</div>
		</div>
	

  </body>
</html>
