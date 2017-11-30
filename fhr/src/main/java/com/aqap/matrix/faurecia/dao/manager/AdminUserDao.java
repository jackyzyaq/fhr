package com.aqap.matrix.faurecia.dao.manager;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aqap.matrix.faurecia.entity.manager.AdminUser;


public interface AdminUserDao extends PagingAndSortingRepository<AdminUser, String>,
		JpaSpecificationExecutor<AdminUser> {
	
	
	AdminUser findByLoginname(String loginname);

	
	
}
