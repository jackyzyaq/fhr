package com.aqap.matrix.faurecia.entity.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "reward_products")
public class RewardProducts implements Serializable {

	private static final long serialVersionUID = 4956417101611733270L;
	private String proname;// 产品分名
	private String RPDesc;// 产品描述
	private Long BPValues; //对应的积分值
	private int prostock; //图片库存
	private String proimg; //图片地址

	// 模板创建时间
	private Date createdTime = new Date();
	// 更新时间
	private Date updateTime = new Date();
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
	 * @return the rPDesc
	 */
	public String getRPDesc() {
		return RPDesc;
	}

	/**
	 * @return the bPValues
	 */
	public Long getBPValues() {
		return BPValues;
	}

	/**
	 * @return the prostock
	 */
	public int getProstock() {
		return prostock;
	}

	/**
	 * @return the proimg
	 */
	public String getProimg() {
		return proimg;
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
	 * @param rPDesc the rPDesc to set
	 */
	public void setRPDesc(String rPDesc) {
		RPDesc = rPDesc;
	}

	/**
	 * @param bPValues the bPValues to set
	 */
	public void setBPValues(Long bPValues) {
		BPValues = bPValues;
	}

	/**
	 * @param prostock the prostock to set
	 */
	public void setProstock(int prostock) {
		this.prostock = prostock;
	}

	/**
	 * @param proimg the proimg to set
	 */
	public void setProimg(String proimg) {
		this.proimg = proimg;
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
	 * @return the proname
	 */
	public String getProname() {
		return proname;
	}

	/**
	 * @param proname the proname to set
	 */
	public void setProname(String proname) {
		this.proname = proname;
	}
	
	
}
