package com.aqap.matrix.faurecia.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 附件
 * 
 * @author lyh
 * @date 2017-5-17 16:58:37
 */
@Entity
@Table(name = "attachment")
public class Attachment extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String originalName;// 名称
	private String name;// 随机名称
	private String address;// 地址
	private String fileExt;// 附件后缀
	private Long size;// 文件大小 kb
	private Date createTime = new Date();// 创建时间
	private String descn;// 备注
	private String type;// 分类
	private String realPath;
	// 微信临时素材库id
	private String mediaId;
	// mediaId更新时间
	private Date updateTime = new Date();

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Attachment(String originalName, String name, String address,
			String fileExt, Date createTime, String descn, String type,
			Long size, String realPath) {
		super();
		this.originalName = originalName;
		this.name = name;
		this.address = address;
		this.fileExt = fileExt;
		this.size = size;
		this.descn = descn;
		this.type = type;
		this.createTime = createTime;
		this.realPath = realPath;

	}

	/**
	 * 
	 */
	public Attachment() {

	}

	// Property accessors

	public String getType() {
		return type;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	@SuppressWarnings("rawtypes")
	public Map toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("name", this.getName());
		map.put("address", this.getAddress());
		map.put("fileExt", this.getFileExt());
		map.put("size", this.getSize());
		map.put("descn", this.getDescn());
		map.put("type", this.getType());
		map.put("realPath", this.getRealPath());
		return map;
	}

}
