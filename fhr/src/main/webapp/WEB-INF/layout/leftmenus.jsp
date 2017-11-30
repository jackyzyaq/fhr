<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
	<div id="leftmenus" style="margin-bottom: 20px;">
       <dl class="menu-content" id="menu13" >
        	 	<dt><i class="icon-1 person-icon"></i><span>人员管理</span></dt>
        	<dd><a id="menus_user" href="${ctx}/manager/user/list">用户管理</a></dd>
       		<dd><a id="menus_userGroup" href="${ctx}/manager/userGroup/list">用户组管理</a></dd>
        	<dd><a id="menus_adminUser" href="${ctx}/manager/adminUser/adminUserList">管理员账号管理</a></dd>
       </dl>
   </div>
