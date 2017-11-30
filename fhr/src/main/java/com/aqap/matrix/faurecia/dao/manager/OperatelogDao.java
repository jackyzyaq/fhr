package com.aqap.matrix.faurecia.dao.manager;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aqap.matrix.faurecia.entity.manager.Operatelog;


public interface OperatelogDao extends PagingAndSortingRepository<Operatelog, String>,
		JpaSpecificationExecutor<Operatelog> { 
	
	
}
