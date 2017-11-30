package com.aqap.matrix.faurecia.entity.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "reward_setting")
public class RewardSetting implements Serializable {

	/**
	 * 排行榜设置
	 */
	private static final long serialVersionUID = 6934944116265190672L;
	private String type_name;// 排行榜名
	private String id;
	private Integer dept_id;
	private String empid;
	private String improve_id;
	private String improve_code;
	private String improve_emp_id;
	private String improve_emp_name;
	private String improve_dept_name;
	private String improve_dept_id;
	private String improve_solution;
	private int type_id;
	private String dept_name;
	private String reword_value;
	private String setting_emp_name;
	private Date createdTime = new Date();
	private Date updateTime = new Date();
	
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
	 * @return the type_name
	 */
	public String getType_name() {
		return type_name;
	}

	/**
	 * @return the dept_id
	 */
	public Integer getDept_id() {
		return dept_id;
	}

	/**
	 * @return the empid
	 */
	public String getEmpid() {
		return empid;
	}

	/**
	 * @return the improve_id
	 */
	public String getImprove_id() {
		return improve_id;
	}

	/**
	 * @return the type_id
	 */
	public int getType_id() {
		return type_id;
	}

	/**
	 * @return the dept_name
	 */
	public String getDept_name() {
		return dept_name;
	}

	/**
	 * @return the reword_value
	 */
	public String getReword_value() {
		return reword_value;
	}

	/**
	 * @return the setting_emp_name
	 */
	public String getSetting_emp_name() {
		return setting_emp_name;
	}

	/**
	 * @param type_name the type_name to set
	 */
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	/**
	 * @param dept_id the dept_id to set
	 */
	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	/**
	 * @param empid the empid to set
	 */
	public void setEmpid(String empid) {
		this.empid = empid;
	}

	/**
	 * @param improve_id the improve_id to set
	 */
	public void setImprove_id(String improve_id) {
		this.improve_id = improve_id;
	}

	/**
	 * @param type_id the type_id to set
	 */
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	/**
	 * @param dept_name the dept_name to set
	 */
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	/**
	 * @param reword_value the reword_value to set
	 */
	public void setReword_value(String reword_value) {
		this.reword_value = reword_value;
	}

	/**
	 * @param setting_emp_name the setting_emp_name to set
	 */
	public void setSetting_emp_name(String setting_emp_name) {
		this.setting_emp_name = setting_emp_name;
	}

	/**
	 * @return the improve_code
	 */
	public String getImprove_code() {
		return improve_code;
	}

	/**
	 * @return the improve_emp_id
	 */
	public String getImprove_emp_id() {
		return improve_emp_id;
	}

	/**
	 * @return the improve_emp_name
	 */
	public String getImprove_emp_name() {
		return improve_emp_name;
	}

	/**
	 * @return the improve_dept_name
	 */
	public String getImprove_dept_name() {
		return improve_dept_name;
	}

	/**
	 * @return the improve_dept_id
	 */
	public String getImprove_dept_id() {
		return improve_dept_id;
	}

	/**
	 * @return the improve_solution
	 */
	public String getImprove_solution() {
		return improve_solution;
	}

	/**
	 * @param improve_code the improve_code to set
	 */
	public void setImprove_code(String improve_code) {
		this.improve_code = improve_code;
	}

	/**
	 * @param improve_emp_id the improve_emp_id to set
	 */
	public void setImprove_emp_id(String improve_emp_id) {
		this.improve_emp_id = improve_emp_id;
	}

	/**
	 * @param improve_emp_name the improve_emp_name to set
	 */
	public void setImprove_emp_name(String improve_emp_name) {
		this.improve_emp_name = improve_emp_name;
	}

	/**
	 * @param improve_dept_name the improve_dept_name to set
	 */
	public void setImprove_dept_name(String improve_dept_name) {
		this.improve_dept_name = improve_dept_name;
	}

	/**
	 * @param improve_dept_id the improve_dept_id to set
	 */
	public void setImprove_dept_id(String improve_dept_id) {
		this.improve_dept_id = improve_dept_id;
	}

	/**
	 * @param improve_solution the improve_solution to set
	 */
	public void setImprove_solution(String improve_solution) {
		this.improve_solution = improve_solution;
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
