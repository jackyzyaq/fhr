package com.aqap.matrix.faurecia.model;

import java.io.Serializable;
import java.util.Map;

public class ReturnMessage implements Serializable{

	private static final long serialVersionUID = 1L;

	private String result;

	private String message = "";
	
	private Map<String,Object> map;
	
//	public static ReturnMessage getInitReturn() {
//		ReturnMessage rm = new ReturnMessage();
//		rm.setResult(CommonString.DATAUNCORRECT);
//		rm.setMessage(CommonString.DATAERROR_TIP);
//		return rm;
//	}
//
//
//	public static ReturnMessage getSuccessReturn() {
//		ReturnMessage rm = new ReturnMessage();
//		rm.setResult(CommonString.SUCCESS);
//		rm.setMessage(CommonString.SUCCESS_TIP);
//		return rm;
//	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
