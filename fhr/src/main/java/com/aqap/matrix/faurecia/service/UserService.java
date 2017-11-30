package com.aqap.matrix.faurecia.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.wxweb.UserWxDao;
import com.aqap.matrix.faurecia.entity.account.User;
import com.aqap.matrix.faurecia.model.ReturnMessage;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.google.common.collect.Lists;

@Component
@Transactional(readOnly = true)
public class UserService {
	
	@Autowired  
	private UserWxDao userManagerDao;
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	/**
	 * 获取后台用户列表
	 * @param nickName
	 * @param userName
	 * @param mobile
	 * @param bdstatus
	 * @param status
	 * @param state
	 * @param userType
	 * @param productGroupId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<User> findUserManagerPage(final String nickName,final  String userName,
			final String mobile,final  Long bdstatus,final  Long status,final  Long userType,
			final String productGroupId ,final String province,final String city,final List<User> usersbyug,
			int pageNumber, int pageSize) {
		
		Specification<User> s = new Specification<User>() {
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Predicate> predicates = Lists.newArrayList();
				
				
				Predicate pid = builder.notEqual((Path)root.get("id"), "-1");
				predicates.add(pid);
				
				//昵称
				if (StringUtils.isNotBlank(nickName)) {
					Predicate pnickName = builder.like((Path)root.get("nickName"), "%"+nickName+"%");
					predicates.add(pnickName);
				}
				//姓名
				if (StringUtils.isNotBlank(userName)) {
					Predicate puserName = builder.like((Path)root.get("userName"), "%"+userName+"%");
					predicates.add(puserName);
				}
				//手机号
				if (StringUtils.isNotBlank(mobile)) {
					Predicate pmobile = builder.like((Path)root.get("mobile"), "%"+mobile+"%");
					predicates.add(pmobile);
				}
				//微信绑定状态  1已绑定 2未绑定
				if(null != bdstatus){
					if(bdstatus == 1){
						Predicate pbdstatus = builder.isNotNull(root.get("openid"));
						predicates.add(pbdstatus);
					}else if(bdstatus == 2){
						Predicate pbdstatus = builder.isNull(root.get("openid"));
						predicates.add(pbdstatus);
					}
				}
				//账号状态(0有效 1 失效)
				if (null != status) {
					Predicate pstatus = builder.equal(root.get("status"),status);
					predicates.add(pstatus);
				}
				//用户类别
				if (null != userType) {
					Predicate puserType = builder.equal(root.get("userType"),userType);
					predicates.add(puserType);
				}
				
				//用户产品组
				if(StringUtils.isNotBlank(productGroupId)){
					Predicate pproductGroupId = builder.equal(root.get("productGroup").get("id"),productGroupId);
					predicates.add(pproductGroupId);
				}
				
				//省份
				if(StringUtils.isNotBlank(province)){
					Predicate pprovince = builder.equal(root.get("bdprovince"),province);
					predicates.add(pprovince);
				}
				
				//城市
				if(StringUtils.isNotBlank(city)){
					Predicate pcity = builder.equal(root.get("bdcity"),city);
					predicates.add(pcity);
				}
				
				//用户组
				if(usersbyug != null && usersbyug.size() > 0){
					In in = builder.in(root.get("id"));
					for (int i = 0; i < usersbyug.size(); i++) {
						if(usersbyug.get(i) != null){
							in.value(usersbyug.get(i).getId());
						}
					}
					predicates.add(in);
				}
				
				
				List<Order> orderlist = new ArrayList<Order>();
				orderlist.add(builder.desc(root.get("createTime")));
				
				query.orderBy(orderlist);
				Predicate p = builder.and(predicates.toArray(new Predicate[predicates.size()]));
				return p;
				
				}
			};
			Page<User> p = userManagerDao.findAll(s, new PageRequest(pageNumber - 1, pageSize));
			
			return p;
	}

	public User findOne(String id) {
		return userManagerDao.findOne(id);
	}

	
	
	/**
	 * 更新字段
	 * @param id
	 * @param field
	 * @param param
	 * @return
	 */
	public ReturnMessage updateField(String id, String field, String param) {
		
		String result = CommonString.DATAUNCORRECT;
		String message = CommonString.DATAUNCORRECT_TIP;
		ReturnMessage rm = new ReturnMessage();
		
		//更新状态
		if("status".equals(field)){
			Long status = null;
			try{
				status = Long.parseLong(param);
				if(status == User.STATUS_SUCCESS){
					userManagerDao.updateStatus(status,id);
					result = CommonString.SUCCESS;
					message = "更新成功";
				}
			}catch (Exception e) {
				logger.error("param转类型错误"+e.getMessage());
			}
		}
		
		rm.setResult(result);
		rm.setMessage(message);
		return rm;
	}

	/**
	 * 保存
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = false)
	public ReturnMessage saveManager(User user,String thumbId) {
		String result = CommonString.DATAUNCORRECT;
		String message = CommonString.DATAUNCORRECT_TIP;
		ReturnMessage rm = new ReturnMessage();
		if(user != null){
			String id = user.getId();
			String userName = user.getUserName();
			Long status = user.getStatus();
			String mobile = user.getMobile();
			
			if(StringUtils.isNotEmpty(id)){
				User oldinfo = userManagerDao.findOne(id);
				if(oldinfo == null){
					result = CommonString.FAIL;
					message = CommonString.DATANOTEXIST_TIP;
					rm.setResult(result);
					rm.setMessage(message);
					return rm;
				}
				oldinfo.setUpdateTime(new Date());
				user = oldinfo;

				user.setUserName(userName);
				user.setStatus(status);
				user.setMobile(mobile);
				user.setUpdateTime(new Date());
				
			}else{
				user.setCreateTime(new Date());
			}
			
			try{
				userManagerDao.save(user);
				result = CommonString.SUCCESS;
				message = "保存成功";
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		rm.setResult(result);
		rm.setMessage(message);
		return rm;
	}

	public User getAdminUser() {
		String id = "-1";
		return userManagerDao.findOne(id);
	}

	/**
	 * 通过用户手机号查询
	 * @param mobile
	 * @return
	 */
	public User findByMobile(String mobile) {
		return userManagerDao.findByMobile(mobile);
	}

	@Transactional(readOnly=false)
	public void saveList(List<User> userlist) {
		userManagerDao.save(userlist);
	}

	/**
	 * 通过openid 更新用户是否关注
	 * @param opeid
	 * @param subscribe 0未关注  1已关注
	 * @return
	 */
	@Transactional(readOnly=false)
	public void updateSubscribeByOpenid(String openid,String subscribe) {
		if(StringUtils.isNotBlank(openid) && null != subscribe){
			Date date = new Date();
			userManagerDao.updateSubscribeByOpenid(subscribe ,date, openid);
		}
	}

	public List<User> findByOpenid(String openid) {
		return userManagerDao.findByOpenid(openid);
	}

	public List<User> findByMobileAndStatus(String mobile, Long statusSuccess) {
		//return userManagerDao.findByMobileAndStatus(mobile,statusSuccess);
		
		return null;
	}

	
	public User createInitNewUser(String username ,String mobile) {
		User user = new User();
		user.setUserName(username);
		user.setMobile(mobile);
		user.setStatus(User.STATUS_SUCCESS);
		//user.setUserType(User.USER_TYPE_REP);
		
		return user;
	}

	@Transactional(readOnly=false)
	public User save(User user) {
		return userManagerDao.save(user);
	}


}
