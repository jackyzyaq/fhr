package com.aqap.matrix.faurecia.entity.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.aqap.matrix.faurecia.entity.IdEntity;
@Entity
@Table(name = "fhr_dept")
public class FHRDepartment extends IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2167347480746674536L;
	public static final String STATUS_SUCCESS = "SUCCESS";
	private String name;// 部门名
	private String formDesc;// 部门描述
	// 模板创建时间
	private Date createdTime = new Date();
	// 更新时间
	private Date updateTime = new Date();
	//上级部门
	private String parentDeptId;
	//部门顺序
	private int deptOrder;
	
	//用户部门多对多
	private List<User> bizUsers = new ArrayList<User>();
	
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "DEPT_User", 
            joinColumns = { @JoinColumn(name = "DEPT_ID", referencedColumnName = "id") }, 
            inverseJoinColumns = { @JoinColumn(name = "User_ID", referencedColumnName = "id") })
	public List<User> getBizUsers() {
		return bizUsers;
	}
	public void setBizUsers(List<User> bizUsers) {
		this.bizUsers = bizUsers;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFormDesc() {
		return formDesc;
	}
	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
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
	
	public String getParentDeptId() {
		return parentDeptId;
	}
	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}
	public int getDeptOrder() {
		return deptOrder;
	}
	public void setDeptOrder(int deptOrder) {
		this.deptOrder = deptOrder;
	}
}
