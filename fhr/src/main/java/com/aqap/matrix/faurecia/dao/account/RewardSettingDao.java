package com.aqap.matrix.faurecia.dao.account;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aqap.matrix.faurecia.entity.account.RewardSetting;
import com.aqap.matrix.faurecia.entity.audit.AuditForm;



public interface RewardSettingDao extends PagingAndSortingRepository<RewardSetting,String>,JpaSpecificationExecutor<RewardSetting> {
	List<RewardSetting> findByIdIn(List<String> ids);
	
	/*Page<AuditForm> findByTypeidAndRewordvalue(int type_id,String rwvalue,Pageable page);*/
}
