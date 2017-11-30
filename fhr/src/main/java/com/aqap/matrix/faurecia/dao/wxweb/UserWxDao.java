package com.aqap.matrix.faurecia.dao.wxweb;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aqap.matrix.faurecia.entity.account.User;


public interface UserWxDao extends PagingAndSortingRepository<User, String>,
		JpaSpecificationExecutor<User> {

	List<User> findByOpenid(String openid);

	User findByOpenidAndStatus(String openid, Long status);

	User findByMobile(String mobile);
	
	@Modifying
	@Query("update User u  set u.status = ?1  where  u.id = ?2")
	void updateStatus(Long status, String id);
	
	@Modifying
	@Query("update User u  set u.subscribe = ?1 ,u.updateTime = ?2 where  u.openid = ?3")
	void updateSubscribeByOpenid(String subscribe, Date date, String openid);


}
