package com.aqap.matrix.faurecia.utils;

import org.apache.shiro.authc.AuthenticationException;

public class AccountStopException extends AuthenticationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AccountStopException(){
		super();
		
	}               
    public AccountStopException(String message) {        
        super(message);                             
    }
}
