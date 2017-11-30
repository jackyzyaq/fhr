package com.aqap.matrix.faurecia.service.manager;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
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

import com.aqap.matrix.faurecia.dao.manager.OperatelogDao;
import com.aqap.matrix.faurecia.entity.manager.AdminUser;
import com.aqap.matrix.faurecia.entity.manager.Operatelog;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.Function;


/**
 * 操作记录
 * 
 * @author lyh
 * @date 2017-6-27 22:22:46
 * 
 */
@Component
@Transactional(readOnly = true)
public class OperatelogService {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(OperatelogService.class);
	
	@Autowired
	private OperatelogDao operatelogDao;
	
	@Transactional(readOnly = false)
	public String save(String module,String content){
		if(StringUtils.isNotEmpty(content)){
			AdminUser user = (AdminUser) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
			if(user != null){
				Operatelog op = new Operatelog();
				op.setUser(user);
				op.setModule(module);
				op.setContent(content);
				op.setCreatetime(Function.getCurrentDate());
				operatelogDao.save(op);
				return CommonString.SUCCESS;
			}
			
		}
		return CommonString.FAIL;
	}
	
	public Page<Operatelog> list(int pageno,int pagesize){
		Sort sort = new Sort(Direction.DESC,"createtime");
		Pageable pageable = new PageRequest(pageno - 1, pagesize,sort);
		return operatelogDao.findAll(pageable);
	}
	
	public Page<Operatelog> list(int pageno,int pagesize,Specification<Operatelog> spec){
		Sort sort = new Sort(Direction.DESC,"createtime");
		Pageable pageable = new PageRequest(pageno - 1, pagesize,sort);
		return operatelogDao.findAll(spec, pageable);
	}
	
	
	
}
