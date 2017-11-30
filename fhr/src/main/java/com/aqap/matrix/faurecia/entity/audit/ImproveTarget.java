package com.aqap.matrix.faurecia.entity.audit;


/*@Entity
@Table(name = "improve_target")*/
public class ImproveTarget //extends IdEntity implements Serializable 
{/*
	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 2692147357082637014L;
	private String name;// 选项名
	private AuditForm auditForm;//表单ID
	
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
	
	@ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "auditFormId")
	@NotFound(action = NotFoundAction.IGNORE)
	public AuditForm getAuditForm() {
		return auditForm;
	}
	public void setAuditForm(AuditForm auditForm) {
		this.auditForm = auditForm;
	}
	
	
*/}
