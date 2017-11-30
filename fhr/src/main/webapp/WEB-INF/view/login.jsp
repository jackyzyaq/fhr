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
	<title>大咖说管理平台</title>
	<link href="${ctx}/static/css/login.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript">
		function showerrortip(content){
			if(content){
				document.getElementById("errortip").innerHTML = content;
			}
		}
	</script>
</head>
<body>
	
	
	<div>
		<div  class="header" align="center"> 
			大咖说管理平台
		</div>
		<div align="center" style="margin-top: 20px;">
			<div class="content">
				<div class="title">登入系统</div>
				<div id="errortip" class="error_tip" style="margin-top: 20px;"></div>
				<form id="loginform" action="${ctx}/logon/v2" method="post"  >
					<div style="margin-top:6px;">
							<div class="input" >
								<input class="input_all name" name="loginvalue"  placeholder="用户名" type="text" onfocus="this.className='input_all name_now'" onblur="this.className='input_all name'" >
							</div>
							<div class="input">
								<input class="input_all password" name="passvalue"  type="password" placeholder="密码" onfocus="this.className='input_all password_now'" onblur="this.className='input_all password'" >
							</div>
							<div class="checkbox" style="display: none;">
								<input type="checkbox"  name="rememberMe" /><span>记住密码</span>
							</div> 
							<div class="enter">
								<input class="button" type="submit" value="" onclick="login()">
							</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<c:if test="${shiroLoginFailure=='org.apache.shiro.authc.UnknownAccountException'}"> <script>showerrortip('账号不存在！')</script></c:if>
	<c:if test="${shiroLoginFailure=='org.apache.shiro.authc.IncorrectCredentialsException'}"><script>showerrortip('密码错误！')</script></c:if>
	<c:if test="${shiroLoginFailure=='com.wechat.matrix.wxdemo.utils.AccountStopException'}"><script>showerrortip('帐号已停用,请联系管理员！')</script></c:if>
	<c:if test="${shiroLoginFailure=='org.apache.shiro.authc.AuthenticationException'}"><script>showerrortip('认证失败！')</script></c:if>
	
	

</body>
</html>

