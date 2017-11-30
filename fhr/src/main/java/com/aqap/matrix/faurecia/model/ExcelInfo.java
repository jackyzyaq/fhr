package com.aqap.matrix.faurecia.model;

/**
 * excel写入实体
 * 
 * @author lyh
 * 
 */
public class ExcelInfo {
	private int j;// 表示列
	private int i;// 表示行
	private String type;//类型 0 是文字 1是图片
	private String content;// 内容

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
