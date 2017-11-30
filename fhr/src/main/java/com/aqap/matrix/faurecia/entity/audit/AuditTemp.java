package com.aqap.matrix.faurecia.entity.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.aqap.matrix.faurecia.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 附件
 * 
 * @author Jacky Yi
 * @date 2017-10-17 16:58:37
 */
@Entity
@Table(name = "audit_temp")
public class AuditTemp extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7236955830435224246L;
	// Fields
	@JsonIgnore
	private String name;// 随机名称
	private String tempDesc;// 地址
	// 模板创建时间
	private Date createdTime = new Date();
	// mediaId更新时间
	private Date updateTime = new Date();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getTempDesc() {
		return tempDesc;
	}

	public void setTempDesc(String tempDesc) {
		this.tempDesc = tempDesc;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	

	@SuppressWarnings("rawtypes")
	public Map toMap() {
		
		return new HashMap();
	}

}
