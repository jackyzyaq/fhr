package com.aqap.matrix.faurecia.service.wxweb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.wxweb.UserWxDao;
import com.aqap.matrix.faurecia.entity.account.User;

@Component
@Transactional(readOnly = true)
public class UserWxService {
	@Autowired  
	private UserWxDao userWxDao;

	@Transactional(readOnly=false)
	public User save(User user) {
		return userWxDao.save(user);
	}

	public User findByOpenidAndStatus(String openid, Long status) {
		return userWxDao.findByOpenidAndStatus(openid,status);
	}

	public User findByOpenid(String openid) {
		User user = null;
		List<User> users = userWxDao.findByOpenid(openid);
		if(users.size() > 0){
			user = users.get(0);
		}
		return user;
	}

	public User findById(String id) {
		return userWxDao.findOne(id);
	}
	
	
}
