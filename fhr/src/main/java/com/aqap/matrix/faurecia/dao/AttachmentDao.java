package com.aqap.matrix.faurecia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aqap.matrix.faurecia.entity.Attachment;



public interface AttachmentDao extends PagingAndSortingRepository<Attachment,String>,JpaSpecificationExecutor<Attachment> {
	List<Attachment> findByIdIn(List<String> ids);

}
