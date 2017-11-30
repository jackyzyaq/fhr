package com.aqap.matrix.faurecia.entity;



import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * id生成策略uuid2
 * 
 */
//JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1015667797022320514L;
	protected String id;

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
