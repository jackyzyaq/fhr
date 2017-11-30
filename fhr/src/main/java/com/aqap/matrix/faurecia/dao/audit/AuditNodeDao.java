package com.aqap.matrix.faurecia.dao.audit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aqap.matrix.faurecia.entity.audit.AuditNode;

/**
 * 附件
 * 
 * @author Jacky Yi
 * @date 2017-10-17 16:58:37
 */
public interface AuditNodeDao extends PagingAndSortingRepository<AuditNode,String>,JpaSpecificationExecutor<AuditNode> {
	List<AuditNode> findByIdIn(List<String> ids);
	
	
	@Query("select max(idxNumber) from AuditNode where formId = ? ")
	public java.lang.Integer getLatestNodeIdx(String fromId);

}
