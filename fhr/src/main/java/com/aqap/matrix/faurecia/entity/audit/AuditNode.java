package com.aqap.matrix.faurecia.entity.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.aqap.matrix.faurecia.entity.IdEntity;

/**
 * 附件
 * 
 * @author Jacky Yi
 * @date 2017-10-17 16:58:37
 */
@Entity
@Table(name = "audit_node")
public class AuditNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7236955830435224246L;
	
	
	private String id;
	private String name;// 节点名称
	private String nodeDesc;// 节点描述
	// 模板创建时间
	private Date createdTime = new Date();
	// 更新时间
	private Date updateTime = new Date();
	
	//审批流程节点顺序
	private Integer idxNumber;
	
	//节点用户ID
	private String userId;
	
	//节点员工ID
	private String EMId;
	
	//节点部门ID
	private Integer deptId;
	
	//节点状态
	private  int  status;
	
	//下一节点
	private int nextNode;
	
	//审批的节点表单
	
	private String fromId;
	
	//审批建议
	private String comments;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nodeDesc
	 */
	public String getNodeDesc() {
		return nodeDesc;
	}

	/**
	 * @param nodeDesc the nodeDesc to set
	 */
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
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
	 * @return the idxNumber
	 */
	public Integer getIdxNumber() {
		return idxNumber;
	}

	/**
	 * @param idxNumber the idxNumber to set
	 */
	public void setIdxNumber(Integer idxNumber) {
		this.idxNumber = idxNumber;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the eMId
	 */
	public String getEMId() {
		return EMId;
	}

	/**
	 * @param eMId the eMId to set
	 */
	public void setEMId(String eMId) {
		EMId = eMId;
	}

	/**
	 * @return the deptId
	 */
	public Integer getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
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
	 * @return the nextNode
	 */
	public int getNextNode() {
		return nextNode;
	}

	/**
	 * @param nextNode the nextNode to set
	 */
	public void setNextNode(int nextNode) {
		this.nextNode = nextNode;
	}

	/**
	 * @return the fromId
	 */
	public String getFromId() {
		return fromId;
	}

	/**
	 * @param fromId the fromId to set
	 */
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
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
	

}
