package com.aqap.matrix.faurecia.service.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.aqap.matrix.faurecia.entity.IdEntity;
import com.aqap.matrix.faurecia.entity.audit.AuditForm;


public class ImproveSources  extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3535247024870305512L;
	private String name;// 选项名
	
	private AuditForm auditForm;//表单ID
	
	@ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "auditFormId")
	@NotFound(action = NotFoundAction.IGNORE)
	public AuditForm getAuditForm() {
		return auditForm;
	}
	public void setAuditForm(AuditForm auditForm) {
		this.auditForm = auditForm;
	}
	// 创建时间
	private Date createdTime = new Date();
	// 更新时间
	private Date updateTime = new Date();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
}
