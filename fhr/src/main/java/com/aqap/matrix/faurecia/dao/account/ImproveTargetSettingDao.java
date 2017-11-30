package com.aqap.matrix.faurecia.dao.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.aqap.matrix.faurecia.entity.account.ImproveTargetSetting;



public interface ImproveTargetSettingDao extends PagingAndSortingRepository<ImproveTargetSetting,String>,JpaSpecificationExecutor<ImproveTargetSetting> {
	List<ImproveTargetSetting> findByIdIn(List<String> ids);
	
	@Query(value="select sum(target_value) s,target_month tm from improve_target_setting "
			+ "			where target_year = :targetYear and target_dept_id in (:deptids)"
			+ "				 GROUP BY target_month order BY target_month", nativeQuery=true)
	List<Object[]> getTargetRptData(@Param("targetYear") String targetYear, @Param("deptids") List<Integer> deptid);

}
