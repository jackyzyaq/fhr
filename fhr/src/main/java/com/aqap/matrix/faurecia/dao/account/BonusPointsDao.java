package com.aqap.matrix.faurecia.dao.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aqap.matrix.faurecia.entity.account.BonusPoints;



public interface BonusPointsDao extends PagingAndSortingRepository<BonusPoints,String>,JpaSpecificationExecutor<BonusPoints> {
	List<BonusPoints> findByIdIn(List<String> ids);
	
	/*Page<AuditForm> findByTypeidAndRewordvalue(int type_id,String rwvalue,Pageable page);*/
}
