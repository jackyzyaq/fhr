package com.aqap.matrix.faurecia.service.manager;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.manager.AdminUserDao;
import com.aqap.matrix.faurecia.entity.manager.AdminUser;
import com.aqap.matrix.faurecia.model.ReturnMessage;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.Function;

/**
 * 后台管理员
 * 
 * @author lyh
 * @date 2017-6-27 22:22:29
 * 
 */
@Component
@Transactional(readOnly = true)
public class AdminUserService {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(AdminUserService.class);
	
	@Autowired  
	private AdminUserDao adminUserDao;
	@Autowired
	private OperatelogService operatelogService;
	
	public AdminUser findByLoginname(String loginName) {
		AdminUser user = adminUserDao.findByLoginname(loginName);
		return user;
	}
	
	public Page<AdminUser> list(int pageno,int pagesize,Specification<AdminUser> spec){
		Sort sort1 = new Sort(Direction.DESC,"issuperadmin");
		Sort sort2 = new Sort(Direction.ASC,"status");
		Sort sort3 = new Sort(Direction.DESC,"createtime");
		Sort sortall = sort1.and(sort2).and(sort3);
		
		Pageable pageable = new PageRequest(pageno - 1, pagesize,sortall);	
		return adminUserDao.findAll(spec,pageable);
	}
	
	public AdminUser userform(String userid){
		if(StringUtils.isNotEmpty(userid)){
			AdminUser u =  adminUserDao.findOne(userid);
			if(u != null){
				String password = u.getPassword();
				String loginname = u.getLoginname();
				String p = Function.getDecryptPassword(CommonString.PARTYID, loginname, password);
				u.setPassword(p);
				return u;
			}
		}
		return null;
	}
	
	
	
	@Transactional(readOnly = false)
	public ReturnMessage delete(String userid){
		ReturnMessage rm = new ReturnMessage();
		String result = CommonString.DATAUNCORRECT;
		if(StringUtils.isNotEmpty(userid)){
			AdminUser u = adminUserDao.findOne(userid);
			if(u != null){
				u.setStatus("3");
				adminUserDao.save(u);
				result = CommonString.SUCCESS;
			}
		}
		rm.setResult(result);
		return rm;
	}
	
	
	@Transactional(readOnly = false)
	public ReturnMessage stop(String userid){
		ReturnMessage rm = new ReturnMessage();
		String result = CommonString.DATAUNCORRECT;
		if(StringUtils.isNotEmpty(userid)){
			AdminUser u = adminUserDao.findOne(userid);
			if(u != null){
				u.setStatus("2");
				adminUserDao.save(u);
				result = CommonString.SUCCESS;
			}
		}
		rm.setResult(result);
		return rm;
	}
	
	@Transactional(readOnly = false)
	public ReturnMessage normal(String userid){
		ReturnMessage rm = new ReturnMessage();
		String result = CommonString.DATAUNCORRECT;
		if(StringUtils.isNotEmpty(userid)){
			AdminUser u = adminUserDao.findOne(userid);
			if(u != null){
				u.setStatus("0");
				adminUserDao.save(u);
				result = CommonString.SUCCESS;
			}
		}
		rm.setResult(result);
		return rm;
	}
	
	@Transactional(readOnly = false)
	public ReturnMessage resetpassword(String userid){
		ReturnMessage rm = new ReturnMessage();
		String result = CommonString.DATAUNCORRECT;
		if(StringUtils.isNotEmpty(userid)){
			AdminUser u = adminUserDao.findOne(userid);
			if(u != null){
				String loginname = u.getLoginname();
				String password = Function.getEnvironment("user_init_pass");
				String newpassword = Function.getEncryptPassword(Function.getEnvironment("partyid"), loginname, password)[1];
				u.setPassword(newpassword);
				adminUserDao.save(u);
				result = CommonString.SUCCESS;
			}
		}
		rm.setResult(result);
		return rm;
	}
	
	
	
	
	@Transactional(readOnly = false)
	public ReturnMessage save(AdminUser user){
		ReturnMessage rm = new ReturnMessage();
		String result = CommonString.DATAUNCORRECT;
		String messgae = "";
		result = checkUser(user);
		if(CommonString.SUCCESS.equals(result)){
			String userid = user.getId();
			String loginname = user.getLoginname();
			
				//新密码
				String password = user.getPassword();
				if(StringUtils.isBlank(password)){
					password = Function.getEnvironment("user_init_pass");
				} 
				String newpassword = Function.getEncryptPassword(CommonString.PARTYID, loginname, password)[1];
				if(StringUtils.isNotEmpty(userid)){
					AdminUser uu = adminUserDao.findOne(userid);
					if(uu != null){
						uu.setLoginname(loginname);
						uu.setUsername(user.getUsername());
						uu.setPassword(newpassword);
						
						
						adminUserDao.save(uu);
						
						//操作记录
						String content = "修改用户信息 "+loginname;
						operatelogService.save("user",content);
						result = CommonString.SUCCESS;
					}else{
						result = CommonString.DATAUNCORRECT;
					}
				}else{
					
					user.setPassword(newpassword);
					user.setStatus("0");
					user.setCreatetime(Function.getCurrentDate());
					adminUserDao.save(user);
					
					//操作记录
					String content = "创建用户 "+loginname;
					operatelogService.save("user",content);
					result = CommonString.SUCCESS;
				}
			}else{
				messgae = "帐号重复，请换个帐号";
				result = CommonString.FAIL;
			}
		
		rm.setResult(result);
		rm.setMessage(messgae);
		return rm;
		
	}
	
	
	private String checkUser(AdminUser user){
		String result = CommonString.DATAUNCORRECT;
		if(user != null){
			String userid = user.getId();
			String loginname = user.getLoginname();
			AdminUser checkuser = adminUserDao.findByLoginname(loginname);
			//检测loginname
			if(checkuser != null){
				if(StringUtils.isNotEmpty(userid)){
					if(userid.equals(checkuser.getId())){
						result = CommonString.SUCCESS;
					}else{
						result = CommonString.HASEXIST;
					}
				}else{
					result = CommonString.HASEXIST;
				}
			}else{
				result = CommonString.SUCCESS;
			}
		}
		return result;
	}
	
	
	
	

	
	
	

	public static void main(String[] args) {
		String s = Function.getEncryptPassword(CommonString.PARTYID, "ttt", "111111")[1];
		String p = Function.getDecryptPassword(CommonString.PARTYID, "test", "DrMfgLiud2U2JTCdQGZ5VL+dJR0sQ6mNAf5CxafZHP4=");
		System.out.println(s+"==="+p); 
	}
}
