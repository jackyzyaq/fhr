package com.aqap.matrix.faurecia.entity.manager;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.aqap.matrix.faurecia.entity.IdEntity;

/**
 * 操作记录实体表
 * @author Administrator
 *
 */
@Entity
@Table(name = "operatelog")
public class Operatelog extends IdEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//创建时间
	private String createtime;
	
	private AdminUser user;//操作用户
	
	//记录操作内容
	private String content;
	
	//模块
	private String module;
	
	
	

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "userid")
	@NotFound(action=NotFoundAction.IGNORE)
	public AdminUser getUser() {
		return user;
	}

	public void setUser(AdminUser user) {
		this.user = user;
	}
	
	
	
}
