package com.aqap.matrix.faurecia.entity.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.aqap.matrix.faurecia.entity.IdEntity;
@Entity
@Table(name = "bonus_points")
public class BonusPoints implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6934944116265190672L;
	private String name;// 积分名
	private String BPDesc;// 积分描述
	private int type;//产品类型 - 备用
	private Long BPValues; //积分值
	private int countMethod;//1、加 2、减 3、乘 4、除
	private String id;
	
	private String EMPId;
	private Integer deptId;
	private String EMPName;
	private String deptName;
	private String improveId;
	private String improveCode;
	private String productId;
	private String productName;
	private String orderId;
	private Date created_time = new Date();
	private Date update_time = new Date();
	private int status;//1、提交 2、完成

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the bPDesc
	 */
	public String getBPDesc() {
		return BPDesc;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the bPValues
	 */
	public Long getBPValues() {
		return BPValues;
	}

	/**
	 * @return the countMethod
	 */
	public int getCountMethod() {
		return countMethod;
	}

	/**
	 * @return the eMPId
	 */
	public String getEMPId() {
		return EMPId;
	}

	/**
	 * @return the deptId
	 */
	public Integer getDeptId() {
		return deptId;
	}

	/**
	 * @return the improveId
	 */
	public String getImproveId() {
		return improveId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @return the created_time
	 */
	public Date getCreated_time() {
		return created_time;
	}

	/**
	 * @return the update_time
	 */
	public Date getUpdate_time() {
		return update_time;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param bPDesc the bPDesc to set
	 */
	public void setBPDesc(String bPDesc) {
		BPDesc = bPDesc;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @param bPValues the bPValues to set
	 */
	public void setBPValues(Long bPValues) {
		BPValues = bPValues;
	}

	/**
	 * @param countMethod the countMethod to set
	 */
	public void setCountMethod(int countMethod) {
		this.countMethod = countMethod;
	}

	/**
	 * @param eMPId the eMPId to set
	 */
	public void setEMPId(String eMPId) {
		EMPId = eMPId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/**
	 * @param improveId the improveId to set
	 */
	public void setImproveId(String improveId) {
		this.improveId = improveId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @param created_time the created_time to set
	 */
	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	/**
	 * @param update_time the update_time to set
	 */
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
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
	 * @return the eMPName
	 */
	public String getEMPName() {
		return EMPName;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param eMPName the eMPName to set
	 */
	public void setEMPName(String eMPName) {
		EMPName = eMPName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the improveCode
	 */
	public String getImproveCode() {
		return improveCode;
	}

	/**
	 * @param improveCode the improveCode to set
	 */
	public void setImproveCode(String improveCode) {
		this.improveCode = improveCode;
	}
	
	
	
}
