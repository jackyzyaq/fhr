package com.aqap.matrix.faurecia.entity.manager;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.aqap.matrix.faurecia.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sys_admin_user")
public class AdminUser extends IdEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//登录名
	private String loginname;
	//密码
	@JsonIgnore
	private String password;
	//姓名
	private String username;
	//性别
	private String sex;
	//状态   1：帐号异常 2:帐号删除停用  3：逻辑删除
	private String status;
	//创建时间
	private String createtime;
	//是否是超级管理员    0：是
	private String issuperadmin;
	@JsonIgnore
	private List<Operatelog> operatelogList;
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Operatelog> getOperatelogList() {
		return operatelogList;
	}

	public void setOperatelogList(List<Operatelog> operatelogList) {
		this.operatelogList = operatelogList;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIssuperadmin() {
		return issuperadmin;
	}

	public void setIssuperadmin(String issuperadmin) {
		this.issuperadmin = issuperadmin;
	}

	
	
}
