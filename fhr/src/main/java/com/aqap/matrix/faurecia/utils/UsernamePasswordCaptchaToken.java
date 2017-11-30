package com.aqap.matrix.faurecia.utils;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordCaptchaToken extends UsernamePasswordToken{
	private static final long serialVersionUID = 1L;
    private String captcha;
    
    private String usertype;
    
    public String getCaptcha(){
         return captcha;
    }
    public void setCaptcha(String captcha) {
         this.captcha = captcha;
    }

    public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public UsernamePasswordCaptchaToken() {
         super();
    }
    public UsernamePasswordCaptchaToken(String username, char[] password,
               boolean rememberMe, String host,String captcha) {        
         super(username, password, rememberMe, host);
         this.captcha = captcha;
    }
    
    public UsernamePasswordCaptchaToken(String username, char[] password,
            boolean rememberMe, String host,String captcha,String usertype) {        
      super(username, password, rememberMe, host);
      this.captcha = captcha;
      this.usertype = usertype;
 }
}