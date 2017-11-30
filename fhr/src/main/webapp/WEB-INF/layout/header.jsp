<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="guide-bar" style="background-color: #f2f3ed;">
	<div class="container clearfix" style="height:100%;">
		<div class="icon fl">
			<a href="${ctx}/index">
				<img src="${ctx}/static/images/flogo.jpg" style="height: 50px;margin-top: 5px;">
			</a> 
		</div> 
       
		<div class="login-content fr">
			<ul class="clearfix">
					<li class="login-r dropdown-modal drop-down-content">
    					<div class="rel blur-element">
    						<div id="select-company">
								<span id="current-company" data-cid="2992" class="current-company-name">${customerName}</span>
							</div>
    					</div>
    				</li>
								
				<li class="login-r">
					<a href="${ctx}/logon/logOut" class="logout">
						<i class="icon logout-icon"></i>
					</a>
				</li>
			</ul>
		</div>
	</div>
</div>