package com.aqap.matrix.faurecia.entity.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.aqap.matrix.faurecia.entity.IdEntity;
/**
 * @author diuhan
 *
 */
@Entity
@Table(name = "")
public class ImproveTargetSetting implements Serializable {

	private static final long serialVersionUID = 2044187024461193690L;
	/**
	 * 排行榜设置
	 */
	private String targetMonth;
	private String targetYear;
	private String targetDay;
	private Long targetValue;
	private int status;
	private Integer targetDeptId;
	private String targetDeptName;
	private Integer empId;
	private String empName;
	private Date targetMonthly;
	private Date createdTime = new Date();
	private Date updateTime = new Date();
	
	@Transient
	private String depts;
	private String id;

	
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
	 * @return the targetMonth
	 */
	public String getTargetMonth() {
		return targetMonth;
	}
	/**
	 * @return the targetYear
	 */
	public String getTargetYear() {
		return targetYear;
	}
	/**
	 * @return the targetDay
	 */
	public String getTargetDay() {
		return targetDay;
	}
	/**
	 * @return the targetValue
	 */
	public Long getTargetValue() {
		return targetValue;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @return the targetDeptId
	 */
	public Integer getTargetDeptId() {
		return targetDeptId;
	}
	/**
	 * @return the empId
	 */
	public Integer getEmpId() {
		return empId;
	}
	/**
	 * @return the targetMonthly
	 */
	public Date getTargetMonthly() {
		return targetMonthly;
	}
	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param targetMonth the targetMonth to set
	 */
	public void setTargetMonth(String targetMonth) {
		this.targetMonth = targetMonth;
	}
	/**
	 * @param targetYear the targetYear to set
	 */
	public void setTargetYear(String targetYear) {
		this.targetYear = targetYear;
	}
	/**
	 * @param targetDay the targetDay to set
	 */
	public void setTargetDay(String targetDay) {
		this.targetDay = targetDay;
	}
	/**
	 * @param targetValue the targetValue to set
	 */
	public void setTargetValue(Long targetValue) {
		this.targetValue = targetValue;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @param targetDeptId the targetDeptId to set
	 */
	public void setTargetDeptId(Integer targetDeptId) {
		this.targetDeptId = targetDeptId;
	}
	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	/**
	 * @param targetMonthly the targetMonthly to set
	 */
	public void setTargetMonthly(Date targetMonthly) {
		this.targetMonthly = targetMonthly;
	}
	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the depts
	 */
	public String getDepts() {
		return depts;
	}
	/**
	 * @param depts the depts to set
	 */
	public void setDepts(String depts) {
		this.depts = depts;
	}
	/**
	 * @return the targetDeptName
	 */
	public String getTargetDeptName() {
		return targetDeptName;
	}
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param targetDeptName the targetDeptName to set
	 */
	public void setTargetDeptName(String targetDeptName) {
		this.targetDeptName = targetDeptName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	
}
