<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="zh"> <!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
	<link rel="Bookmark" type="image/x-icon" href="${ctx}/static/images/favicon.ico" />
	<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon" />
	<meta charset="utf-8" />
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<title>合理化建议管理系统</title>
	<link href="${ctx}/static/css/login.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	
	
	<div>
		<div  class="header" align="center"> 
			合理化建议管理系统
		</div>
		<div align="center" style="margin-top: 20px;">
			<div class="content">
				<div class="title">登入</div>
				<div id="errortip" class="error_tip" style="margin-top: 20px;">
					<c:if test="${not empty resultmessage}">
				  		${resultmessage}
				  	</c:if>
				</div>
				<form id="loginform" action="${ctx}/mlogon/in" method="post"  >
					<div style="margin-top:6px;">
							<div class="input" >
								<input class="input_all name" name="loginvalue"  placeholder="用户名" value="" type="text" onfocus="this.className='input_all name_now'" onblur="this.className='input_all name'" >
							</div>
							<div class="input">
								<input class="input_all password" name="passvalue" value="" type="password" placeholder="密码" value="" onfocus="this.className='input_all password_now'" onblur="this.className='input_all password'" >
							</div>
							<div class="checkbox" style="display: none;">
								<input type="checkbox"  name="rememberMe" /><span>记住密码</span>
							</div> 
							<div class="enter">
								<input class="button" type="submit" value="">
							</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	

</body>
</html>

