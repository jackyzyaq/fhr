package com.aqap.matrix.faurecia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.manager.AdminUserDao;
import com.aqap.matrix.faurecia.dao.wxweb.UserWxDao;
import com.aqap.matrix.faurecia.entity.account.User;
import com.aqap.matrix.faurecia.entity.manager.AdminUser;



/**
 * 用户帐户业务
 * 
 * @author jyb
 * @date 2015-1-10
 * 
 */
@Component
@Transactional(readOnly = true)
public class AccountService {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired  
	private AdminUserDao adminUserDao;
	@Autowired
	private UserWxDao userWxDao;
	
	
	public AdminUser findByLoginname(String loginName) {
		AdminUser user = adminUserDao.findByLoginname(loginName);
		return user;
	}

	
	public User findByOpenid(String openid) {
		User user = null;
		
		List<User> users = userWxDao.findByOpenid(openid);
		if(users.size()>0){
			user = users.get(0);
		}
		
		return user;
	}
	
	/**
	 * 通过Openid和用户状态查询
	 * @param openid
	 * @param status
	 * @return
	 */
	public User findByOpenidAndStatus(String openid, Long status) {
		return userWxDao.findByOpenidAndStatus(openid,status);
	}
	
}
