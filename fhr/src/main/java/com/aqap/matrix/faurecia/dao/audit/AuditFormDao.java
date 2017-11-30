package com.aqap.matrix.faurecia.dao.audit;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.aqap.matrix.faurecia.entity.audit.AuditForm;

/**
 * 附件
 * 
 * @author Jacky Yi
 * @date 2017-10-17 16:58:37
 */

public interface AuditFormDao extends PagingAndSortingRepository<AuditForm,String>,JpaSpecificationExecutor<AuditForm> {
	List<AuditForm> findByIdIn(List<String> ids);
	Page<AuditForm> findBySubmitEMIdOrSubmitterDeptIdIn(String submitEMId,List<Integer> depts,Pageable page);

	/*@Query(value="select r from AuditForm r where r.submitEMId = ?1 and r.depts in ?2 group by user.id order by r.createTime asc")
	public Page<AuditForm> pageGroupByResearchId(String researchId,String username ,Pageable pageable);*/
	
	@Query(value="SELECT COUNT(1) , convert(char(6),created_time,112) cmonth from audit_form "
			+ "			where created_time between :startDate and :endDate and submitter_dept_id in (:deptids)"
			+ "				 group by convert(char(6),created_time,112) order by cmonth desc", nativeQuery=true)
	public List<Object[]> getMontRept(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("deptids") List<Integer> deptids);
	
	@Query(value="SELECT COUNT(1) , CONVERT(varchar(100), created_time, 23) cday from audit_form "
			+ "			where created_time between :startDate and :endDate and submitter_dept_id in (:deptids)"
			+ "				 group by CONVERT(varchar(100), created_time, 23) order by cday desc", nativeQuery=true)
	//@Query(value="SELECT COUNT(1), CONVERT(varchar(100), created_time, 23) cday from audit_form where submitter_dept_id in (:deptids)  GROUP BY CONVERT(varchar(100), created_time, 23) order by cday desc", nativeQuery=true)
	public List<Object[]> getDailyRept(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("deptids") List<Integer> deptids);
}