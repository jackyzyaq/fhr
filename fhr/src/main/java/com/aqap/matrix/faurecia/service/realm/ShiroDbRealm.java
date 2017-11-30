package com.aqap.matrix.faurecia.service.realm;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqap.matrix.faurecia.entity.account.User;
import com.aqap.matrix.faurecia.entity.manager.AdminUser;
import com.aqap.matrix.faurecia.service.AccountService;
import com.aqap.matrix.faurecia.service.manager.OperatelogService;
import com.aqap.matrix.faurecia.utils.AccountStopException;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.Function;
import com.aqap.matrix.faurecia.utils.UsernamePasswordCaptchaToken;
import com.google.common.base.Objects;


public class ShiroDbRealm extends AuthorizingRealm {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);

	protected AccountService accountService;
	

	private OperatelogService operatelogService;
	

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	public OperatelogService getOperatelogService() {
		return operatelogService;
	}

	public void setOperatelogService(OperatelogService operatelogService) {
		this.operatelogService = operatelogService;
	}

	

	/**
	 * 认证回调函数,登录时调用.
	 * @throws AccountStopException 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authcToken;
		String accountName = token.getUsername();
		
		if (accountName != null && !"".equals(accountName)) {
			String type = token.getUsertype();
			if("wx".equals(type)){
				User user = null;
				user = accountService.findByOpenidAndStatus(accountName, User.STATUS_SUCCESS);
				if (user != null){
					Long status = user.getIsAdmin();
					String usertype = "";
					if(status == User.IS_ADMIN_YSE){
						usertype = "admin";
					}else if(status == User.IS_ADMIN_SUPER){
						usertype = "superadmin";
					}else if(status == User.IS_ADMIN_NO){
						usertype = "staff";
					}
					
					SecurityUtils.getSubject().getSession().setAttribute("gjrole", usertype);
					return new SimpleAuthenticationInfo(user.getOpenid(), "",getName());
				} else {
					// 用户不存在
					throw new UnknownAccountException();
				}
			}else if("manager".equals(type)){

				String inputPassword = String.valueOf(token.getPassword());
				AdminUser user = null;
				user = accountService.findByLoginname(accountName);
				if(null == user){
					user = new AdminUser();
					user.setLoginname(accountName);
					user.setPassword("uKMPtNfP+VZOguJg5rTQOg==");
				}
				if (user != null){
					
					String status = user.getStatus();
					if(StringUtils.isBlank(status)){
						status = "0";
					}

					String dbPassword = Function.getDecryptPassword(CommonString.PARTYID,user.getLoginname(), user.getPassword());
					logger.debug("解密的密码:" + dbPassword);
					// 用户名、密码正确
					if (dbPassword.equals(inputPassword)) {
						SecurityUtils.getSubject().getSession().setAttribute("currentAdminUser", user);
						String logmsg = "用户名："+user.getLoginname()+",成功登入系统，登入时间："+Function.getCurrentDate();
						operatelogService.save("login",logmsg);
						
						SecurityUtils.getSubject().getSession().setAttribute("gjrole", "manager");
						
						return new SimpleAuthenticationInfo(user.getLoginname(), dbPassword.toCharArray(),getName());
					} else {
						throw new IncorrectCredentialsException();// 密码不正确
					}
				} else {
					// 用户不存在
					throw new UnknownAccountException();
				}
				
			
				
			}
			
		}
		return null;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String role = (String) SecurityUtils.getSubject().getSession().getAttribute("gjrole");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if(StringUtils.isNotBlank(role)){
			info.addRole(role);
		}
		return info;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String loginName;
		public String name;

		public ShiroUser(Long id, String loginName, String name) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null)
					return false;
			} else if (!loginName.equals(other.loginName))
				return false;
			return true;
		}
	}
	
	
	
	public static void main(String[] args) {
	}

}
