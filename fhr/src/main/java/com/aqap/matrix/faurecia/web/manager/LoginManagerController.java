package com.aqap.matrix.faurecia.web.manager;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aqap.matrix.faurecia.entity.audit.AuditForm;
import com.aqap.matrix.faurecia.utils.CaptchaException;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.UsernamePasswordCaptchaToken;
import com.yq.faurecia.pojo.EmployeeInfo;




/**
 * 后台登录
 * @author lyh
 * @date 2017-1-5
 * http://localhost:8088/meeting/mlogon
 */
@Controller
@RequestMapping(value = "/mlogon")
public class LoginManagerController
{
	private static Logger logger = LoggerFactory.getLogger(LoginManagerController.class);
	

	@RequestMapping(method = RequestMethod.GET)   
	public String login()
	{
		return "/manager/login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,
			Model model)
	{	
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,userName);  
		return "/manager/login";     
	}
	
	/**
	 * 提交登录
	 * @param model
	 * @param response
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/in")
	public String in(Model model,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value="loginvalue") String username,//手机号
			@RequestParam(value="passvalue") String password){
		
		//获取验证码
		String result = "";
		/*
		String query = request.getQueryString();
		logger.info("请求参数："+query);
		net.sf.json.JSONObject jsonObject = new JSONObject();
		Map<?, ?> map = request.getParameterMap();
		Iterator<?> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			String[] values = (String[])map.get(key);
			jsonObject.accumulate(key, values[0]);
			logger.info("key:"+key);
			logger.info("values length"+ values.length);
			logger.info("values"+ values[0]);
		}
		JSONObject emJSON = (JSONObject)jsonObject.get("emParams");
		EmployeeInfo emInfo = (EmployeeInfo) JSONObject.toBean(emJSON,EmployeeInfo.class);
		//logger.info(emInfo.getZh_name());
		//logger.info(emInfo.getCreate_date().toLocaleString());
*/		AuditForm aForm = new AuditForm();
		aForm.setId("formId");
		aForm.setFormDesc("formDesc");
		JSONObject formJson = (JSONObject) JSONObject.fromObject(aForm);
		//result = request.getParameter("callback");
		//result = result + "(" + formJson.toString() + ")";
		
		
		if(username != null && !"".equals(username) && password != null && !"".equals(password) ){
		try {
			//subject理解成权限对象。类似user
			Subject subject = SecurityUtils.getSubject();
			//创建用户名和密码的令牌
			UsernamePasswordCaptchaToken token = new UsernamePasswordCaptchaToken();
			token.setUsername(username);
			token.setPassword(password.toCharArray());
			token.setUsertype("manager");
			try {
				subject.login(token);
			} catch (UnknownAccountException e) {
				request.setAttribute("shiroLoginFailure",e.getClass().getName());
				logger.debug(e.getMessage());
				result = CommonString.ACCOUNT_NOTEXIST;
			} catch (IncorrectCredentialsException e) {
				request.setAttribute("shiroLoginFailure",e.getClass().getName());
				logger.debug(e.getMessage());
				e.printStackTrace();
				result = CommonString.ACCOUNT_PASSWOR_WRONG;
			}catch (CaptchaException e) {
				request.setAttribute("shiroLoginFailure",e.getClass().getName());
				logger.debug(e.getMessage());
				result = CommonString.CAPTCHA_UNCORRECT;
			}
			//验证是否成功登录的方法
			if (subject.isAuthenticated()){
				result = CommonString.LOGIN_SUCCESS;
				subject.getSession().setAttribute("MEETING_MGR_SUCCESS", "MEETING_MGR_SUCCESS");
				
				return formJson.toString();//"redirect:/manager/index";
			}
		}catch (Exception e) {
			e.printStackTrace();
			result = CommonString.SYSTEM_ERROR;
		}
		}else{
			result = CommonString.DATA_UNCORRECT;
		}
		String message = CommonString.getCodeMessage(result);
		model.addAttribute("resultcode", result);
		model.addAttribute("resultmessage", message);
		
		return result;
	}


	/**
	 * 登出
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("logOut")
	public String logOut(){
		SecurityUtils.getSubject().logout();
		return "redirect:/mlogon";
	}
	
	
}
