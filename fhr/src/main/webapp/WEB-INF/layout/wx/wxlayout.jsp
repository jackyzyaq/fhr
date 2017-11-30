<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML >
<html>
	<head>
		<meta charset="utf-8">
    	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    	
    	<title><sitemesh:title></sitemesh:title></title>
    	
    	
    	<style type="text/css">
    		body,html {
				margin: 0px;
				padding: 0px;
				background-color: #fefefe;
				font-family: "微软雅黑";
			}
			*{font-family: "微软雅黑";}
    	</style>
    	
    	
    	<link rel="stylesheet" href="${ctx}/static/components/weui-master/style/weui.css"/>
	<link rel="stylesheet" href="${ctx}/static/components/weui-master/style/weui2.css"/>
	<link rel="stylesheet" href="${ctx}/static/components/weui-master/style/weui3.css"/>
	<link rel="stylesheet" href="${ctx}/static/css/style01.css?v=2"/>
    	<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
    	<script type="text/javascript" src="${ctx}/static/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/components/weui-master/updown.js"></script>
	  
	<script type="text/javascript" src="${ctx}/static/components/weui-master/zepto.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/components/weui-master/picker.js"></script>
	<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js "></script>
    	<sitemesh:head></sitemesh:head>
  </head>
  
  <body class="page-bg" style="min-height: 100%;">
   	<sitemesh:body></sitemesh:body>
  </body>
</html>
