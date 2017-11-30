package com.aqap.matrix.faurecia.entity.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.aqap.matrix.faurecia.entity.Attachment;
import com.aqap.matrix.faurecia.entity.IdEntity;
import com.google.common.collect.Lists;

/**
 * 附件
 * 
 * @author Jacky Yi
 * @date 2017-10-17 16:58:37
 */
@Entity
@Table(name = "audit_form")
public class AuditForm implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 7236955830435224246L;
	
	private String id;
	// 附件列表
	private List<Attachment> attachments = Lists.newArrayList();
	
	// Fields
	private String formName;// 表单名
	private String formDesc;// 表单描述
	//表单CODE
	private String formCode;
	
	// 模板创建时间
	private Date createdTime = new Date();
	// 更新时间
	private Date updateTime = new Date();
	
	//当前状态
	private  int  status;
	
	//模板ID
	private String audit_temp_id;
	
	/**
	 * 表单正式信息
	 */
	//表单提交用户ID
	private String submitUserId;
	//表单提交用户ID
	private String submitUserName;
	//表单员工提交
	private String submitEMId;
	//表单提交用户名
	private String EMName;
	//工号EMP
	private String EMPNmber;
	//申请人部门Id
	private Integer submitterDeptId;
	//申请人部门名称
	private String submitterDept;
	//表单版本
	private String fromVersion;
	//所属小组
	private String GAPGroup;
	//合理化来源
	private String improveSourcesList;
	//提升方向
	private String improveTargetList;
	//目前状况
	private String currentSituation;
	//建议方案
	private String proposedSolution;
	//上级是否采纳
	private int isManagerAdopted;
	//上级审批确认人
	private String lineManagerId;
	//上级审批确认人
	private String lineManagerName;
	//上级审批确认部门
	private String lineManagerDept;
	//实施部门是否采纳
	private int isExecutionAdopted;
	//实施部门领导
	private String executorMgrId;
	//实施部门领导
	private String executorMgrName;
	//实施部门领导
	private String executorMgrDept;
	//指定实施人
	private String executorMgrDeptId;
	//实施人
	private String executorId;
	//实施人
	private String executorName;
	//实施人部门
	private String executorDeptId;
	//实施期限日期
	private Date executeDeadline;
	//实施日期
	private Date executeDate;
	
	//是否建议推广
	private int isGeneralize;
	//推广单号
	private String generalizationNum;
	//建议推广人
	private String generalizaterId;
	//建议推广人
	private String generalizaterName;
	//建议推广人部门Id
	private String generalizaterDeptId;
	//建议推广人部门
	private String generalizaterDept;
	private String generalizeNum;
	
	//建议人确认
	private String submitterConfirm;
	private Date confirmDate;
	
	//下一审批人信息
	private String nextAuditEMPId;
	private String nextAuditEMPName;
	private String nextAuditDept;
	private String nextAuditDeptName;
	
	private int auditStep;
	
	@Transient
	private String depts;
	@Transient
	private String statusTxt;
	
	/**
	 * 备注
	 */
	private String lineMgrComment;
	private String exeMgrComment;
	private String exeComment;
	private String submitterComment;
	private String generalizerComment;
	@Transient
	private String comment;
	private String latestAuditName;
	
	private String files;
	
	
	/**
	 * @return the depts
	 */
	@Transient
	public String getDepts() {
		return depts;
	}

	/**
	 * @param depts the depts to set
	 */
	public void setDepts(String depts) {
		this.depts = depts;
	}

	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Id
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the submitterConfirm
	 */
	public String getSubmitterConfirm() {
		return submitterConfirm;
	}
	/**
	 * @param submitterConfirm the submitterConfirm to set
	 */
	public void setSubmitterConfirm(String submitterConfirm) {
		this.submitterConfirm = submitterConfirm;
	}

	/**
	 * @return the formName
	 */
	public String getFormName() {
		return formName;
	}
	/**
	 * @param formName the formName to set
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}
	/**
	 * @return the formDesc
	 */
	public String getFormDesc() {
		return formDesc;
	}
	/**
	 * @param formDesc the formDesc to set
	 */
	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}
	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the audit_temp_id
	 */
	public String getAudit_temp_id() {
		return audit_temp_id;
	}
	/**
	 * @param audit_temp_id the audit_temp_id to set
	 */
	public void setAudit_temp_id(String audit_temp_id) {
		this.audit_temp_id = audit_temp_id;
	}
	
	/**
	 * @return the submitEMId
	 */
	public String getSubmitEMId() {
		return submitEMId;
	}
	/**
	 * @param submitEMId the submitEMId to set
	 */
	public void setSubmitEMId(String submitEMId) {
		this.submitEMId = submitEMId;
	}
	/**
	 * @return the eMName
	 */
	public String getEMName() {
		return EMName;
	}
	/**
	 * @param eMName the eMName to set
	 */
	public void setEMName(String eMName) {
		EMName = eMName;
	}
	/**
	 * @return the eMPNmber
	 */
	public String getEMPNmber() {
		return EMPNmber;
	}
	/**
	 * @param eMPNmber the eMPNmber to set
	 */
	public void setEMPNmber(String eMPNmber) {
		EMPNmber = eMPNmber;
	}
	/**
	 * @return the submitterDeptId
	 */
	public Integer getSubmitterDeptId() {
		return submitterDeptId;
	}
	/**
	 * @param submitterDeptId the submitterDeptId to set
	 */
	public void setSubmitterDeptId(Integer submitterDeptId) {
		this.submitterDeptId = submitterDeptId;
	}
	/**
	 * @return the fromVersion
	 */
	public String getFromVersion() {
		return fromVersion;
	}
	/**
	 * @param fromVersion the fromVersion to set
	 */
	public void setFromVersion(String fromVersion) {
		this.fromVersion = fromVersion;
	}
	/**
	 * @return the gAPGroup
	 */
	public String getGAPGroup() {
		return GAPGroup;
	}
	/**
	 * @param gAPGroup the gAPGroup to set
	 */
	public void setGAPGroup(String gAPGroup) {
		GAPGroup = gAPGroup;
	}
	/**
	 * @return the improveSourcesList
	 */
	public String getImproveSourcesList() {
		return improveSourcesList;
	}
	/**
	 * @param improveSourcesList the improveSourcesList to set
	 */
	public void setImproveSourcesList(String improveSourcesList) {
		this.improveSourcesList = improveSourcesList;
	}
	/**
	 * @return the improveTargetList
	 */
	public String getImproveTargetList() {
		return improveTargetList;
	}
	/**
	 * @param improveTargetList the improveTargetList to set
	 */
	public void setImproveTargetList(String improveTargetList) {
		this.improveTargetList = improveTargetList;
	}
	/**
	 * @return the currentSituation
	 */
	public String getCurrentSituation() {
		return currentSituation;
	}
	/**
	 * @param currentSituation the currentSituation to set
	 */
	public void setCurrentSituation(String currentSituation) {
		this.currentSituation = currentSituation;
	}
	/**
	 * @return the proposedSolution
	 */
	public String getProposedSolution() {
		return proposedSolution;
	}
	/**
	 * @param proposedSolution the proposedSolution to set
	 */
	public void setProposedSolution(String proposedSolution) {
		this.proposedSolution = proposedSolution;
	}
	/**
	 * @return the isManagerAdopted
	 */
	public int getIsManagerAdopted() {
		return isManagerAdopted;
	}
	/**
	 * @param isManagerAdopted the isManagerAdopted to set
	 */
	public void setIsManagerAdopted(int isManagerAdopted) {
		this.isManagerAdopted = isManagerAdopted;
	}
	/**
	 * @return the lineManagerId
	 */
	public String getLineManagerId() {
		return lineManagerId;
	}
	/**
	 * @param lineManagerId the lineManagerId to set
	 */
	public void setLineManagerId(String lineManagerId) {
		this.lineManagerId = lineManagerId;
	}
	/**
	 * @return the isExecutionAdopted
	 */
	public int getIsExecutionAdopted() {
		return isExecutionAdopted;
	}
	/**
	 * @param isExecutionAdopted the isExecutionAdopted to set
	 */
	public void setIsExecutionAdopted(int isExecutionAdopted) {
		this.isExecutionAdopted = isExecutionAdopted;
	}
	/**
	 * @return the executorMgrId
	 */
	public String getExecutorMgrId() {
		return executorMgrId;
	}
	/**
	 * @param executorMgrId the executorMgrId to set
	 */
	public void setExecutorMgrId(String executorMgrId) {
		this.executorMgrId = executorMgrId;
	}
	/**
	 * @return the executorMgrDeptId
	 */
	public String getExecutorMgrDeptId() {
		return executorMgrDeptId;
	}
	/**
	 * @param executorMgrDeptId the executorMgrDeptId to set
	 */
	public void setExecutorMgrDeptId(String executorMgrDeptId) {
		this.executorMgrDeptId = executorMgrDeptId;
	}
	/**
	 * @return the executorId
	 */
	public String getExecutorId() {
		return executorId;
	}
	/**
	 * @param executorId the executorId to set
	 */
	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}
	/**
	 * @return the executorDeptId
	 */
	public String getExecutorDeptId() {
		return executorDeptId;
	}
	/**
	 * @param executorDeptId the executorDeptId to set
	 */
	public void setExecutorDeptId(String executorDeptId) {
		this.executorDeptId = executorDeptId;
	}
	/**
	 * @return the executeDeadline
	 */
	public Date getExecuteDeadline() {
		return executeDeadline;
	}
	/**
	 * @param executeDeadline the executeDeadline to set
	 */
	public void setExecuteDeadline(Date executeDeadline) {
		this.executeDeadline = executeDeadline;
	}
	/**
	 * @return the executeDate
	 */
	public Date getExecuteDate() {
		return executeDate;
	}
	/**
	 * @param executeDate the executeDate to set
	 */
	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}
	/**
	 * @return the isGeneralize
	 */
	public int getIsGeneralize() {
		return isGeneralize;
	}
	/**
	 * @param isGeneralize the isGeneralize to set
	 */
	public void setIsGeneralize(int isGeneralize) {
		this.isGeneralize = isGeneralize;
	}
	/**
	 * @return the generalizationNum
	 */
	public String getGeneralizationNum() {
		return generalizationNum;
	}
	/**
	 * @param generalizationNum the generalizationNum to set
	 */
	public void setGeneralizationNum(String generalizationNum) {
		this.generalizationNum = generalizationNum;
	}
	/**
	 * @return the generalizaterId
	 */
	public String getGeneralizaterId() {
		return generalizaterId;
	}
	/**
	 * @param generalizaterId the generalizaterId to set
	 */
	public void setGeneralizaterId(String generalizaterId) {
		this.generalizaterId = generalizaterId;
	}
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "form_attachment_mapping", joinColumns = @JoinColumn(name = "form_id"), inverseJoinColumns = @JoinColumn(name = "attachment_id"))
	@OrderBy(value = "createTime ASC")
	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	/**
	 * @return the formCode
	 */
	public String getFormCode() {
		return formCode;
	}
	/**
	 * @param formCode the formCode to set
	 */
	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}
	/**
	 * @return the submitterDept
	 */
	public String getSubmitterDept() {
		return submitterDept;
	}
	/**
	 * @param submitterDept the submitterDept to set
	 */
	public void setSubmitterDept(String submitterDept) {
		this.submitterDept = submitterDept;
	}
	/**
	 * @return the lineManagerName
	 */
	public String getLineManagerName() {
		return lineManagerName;
	}
	/**
	 * @param lineManagerName the lineManagerName to set
	 */
	public void setLineManagerName(String lineManagerName) {
		this.lineManagerName = lineManagerName;
	}
	/**
	 * @return the lineManagerDept
	 */
	public String getLineManagerDept() {
		return lineManagerDept;
	}
	/**
	 * @param lineManagerDept the lineManagerDept to set
	 */
	public void setLineManagerDept(String lineManagerDept) {
		this.lineManagerDept = lineManagerDept;
	}
	/**
	 * @return the executorMgrName
	 */
	public String getExecutorMgrName() {
		return executorMgrName;
	}
	/**
	 * @param executorMgrName the executorMgrName to set
	 */
	public void setExecutorMgrName(String executorMgrName) {
		this.executorMgrName = executorMgrName;
	}
	/**
	 * @return the executorMgrDept
	 */
	public String getExecutorMgrDept() {
		return executorMgrDept;
	}
	/**
	 * @param executorMgrDept the executorMgrDept to set
	 */
	public void setExecutorMgrDept(String executorMgrDept) {
		this.executorMgrDept = executorMgrDept;
	}
	/**
	 * @return the executorName
	 */
	public String getExecutorName() {
		return executorName;
	}
	/**
	 * @param executorName the executorName to set
	 */
	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}
	/**
	 * @return the generalizaterName
	 */
	public String getGeneralizaterName() {
		return generalizaterName;
	}
	/**
	 * @param generalizaterName the generalizaterName to set
	 */
	public void setGeneralizaterName(String generalizaterName) {
		this.generalizaterName = generalizaterName;
	}
	/**
	 * @return the generalizaterDeptId
	 */
	public String getGeneralizaterDeptId() {
		return generalizaterDeptId;
	}
	/**
	 * @param generalizaterDeptId the generalizaterDeptId to set
	 */
	public void setGeneralizaterDeptId(String generalizaterDeptId) {
		this.generalizaterDeptId = generalizaterDeptId;
	}
	/**
	 * @return the generalizaterDept
	 */
	public String getGeneralizaterDept() {
		return generalizaterDept;
	}
	/**
	 * @param generalizaterDept the generalizaterDept to set
	 */
	public void setGeneralizaterDept(String generalizaterDept) {
		this.generalizaterDept = generalizaterDept;
	}
	/**
	 * @return the submitUserId
	 */
	public String getSubmitUserId() {
		return submitUserId;
	}
	/**
	 * @param submitUserId the submitUserId to set
	 */
	public void setSubmitUserId(String submitUserId) {
		this.submitUserId = submitUserId;
	}
	/**
	 * @return the submitUserName
	 */
	public String getSubmitUserName() {
		return submitUserName;
	}
	/**
	 * @param submitUserName the submitUserName to set
	 */
	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}
	/**
	 * @return the nextAuditEMPId
	 */
	public String getNextAuditEMPId() {
		return nextAuditEMPId;
	}
	/**
	 * @param nextAuditEMPId the nextAuditEMPId to set
	 */
	public void setNextAuditEMPId(String nextAuditEMPId) {
		this.nextAuditEMPId = nextAuditEMPId;
	}
	/**
	 * @return the nextAuditEMPName
	 */
	public String getNextAuditEMPName() {
		return nextAuditEMPName;
	}
	/**
	 * @param nextAuditEMPName the nextAuditEMPName to set
	 */
	public void setNextAuditEMPName(String nextAuditEMPName) {
		this.nextAuditEMPName = nextAuditEMPName;
	}
	/**
	 * @return the nextAuditDept
	 */
	public String getNextAuditDept() {
		return nextAuditDept;
	}
	/**
	 * @param nextAuditDept the nextAuditDept to set
	 */
	public void setNextAuditDept(String nextAuditDept) {
		this.nextAuditDept = nextAuditDept;
	}
	/**
	 * @return the nextAuditDeptName
	 */
	public String getNextAuditDeptName() {
		return nextAuditDeptName;
	}
	/**
	 * @param nextAuditDeptName the nextAuditDeptName to set
	 */
	public void setNextAuditDeptName(String nextAuditDeptName) {
		this.nextAuditDeptName = nextAuditDeptName;
	}

	/**
	 * @return the generalizeNum
	 */
	public String getGeneralizeNum() {
		return generalizeNum;
	}

	/**
	 * @param generalizeNum the generalizeNum to set
	 */
	public void setGeneralizeNum(String generalizeNum) {
		this.generalizeNum = generalizeNum;
	}

	/**
	 * @return the statusTxt
	 */
	public String getStatusTxt() {
		return statusTxt;
	}

	/**
	 * @param statusTxt the statusTxt to set
	 */
	public void setStatusTxt(String statusTxt) {
		this.statusTxt = statusTxt;
	}

	/**
	 * @return the auditStep
	 */
	public int getAuditStep() {
		return auditStep;
	}

	/**
	 * @param auditStep the auditStep to set
	 */
	public void setAuditStep(int auditStep) {
		this.auditStep = auditStep;
	}

	/**
	 * @return the lineMgrComment
	 */
	public String getLineMgrComment() {
		return lineMgrComment;
	}

	/**
	 * @return the exeMgrComment
	 */
	public String getExeMgrComment() {
		return exeMgrComment;
	}

	/**
	 * @return the exeComment
	 */
	public String getExeComment() {
		return exeComment;
	}

	/**
	 * @return the submitterComment
	 */
	public String getSubmitterComment() {
		return submitterComment;
	}

	/**
	 * @return the generalizerComment
	 */
	public String getGeneralizerComment() {
		return generalizerComment;
	}

	/**
	 * @param lineMgrComment the lineMgrComment to set
	 */
	public void setLineMgrComment(String lineMgrComment) {
		this.lineMgrComment = lineMgrComment;
	}

	/**
	 * @param exeMgrComment the exeMgrComment to set
	 */
	public void setExeMgrComment(String exeMgrComment) {
		this.exeMgrComment = exeMgrComment;
	}

	/**
	 * @param exeComment the exeComment to set
	 */
	public void setExeComment(String exeComment) {
		this.exeComment = exeComment;
	}

	/**
	 * @param submitterComment the submitterComment to set
	 */
	public void setSubmitterComment(String submitterComment) {
		this.submitterComment = submitterComment;
	}

	/**
	 * @param generalizerComment the generalizerComment to set
	 */
	public void setGeneralizerComment(String generalizerComment) {
		this.generalizerComment = generalizerComment;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the latestAuditName
	 */
	public String getLatestAuditName() {
		return latestAuditName;
	}

	/**
	 * @param latestAuditName the latestAuditName to set
	 */
	public void setLatestAuditName(String latestAuditName) {
		this.latestAuditName = latestAuditName;
	}

	/**
	 * @return the confirmDate
	 */
	public Date getConfirmDate() {
		return confirmDate;
	}

	/**
	 * @param confirmDate the confirmDate to set
	 */
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	/**
	 * @return the files
	 */
	public String getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(String files) {
		this.files = files;
	}
	
	

}
