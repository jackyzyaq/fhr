package com.aqap.matrix.faurecia.service.wx;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONObject;
import com.aqap.matrix.faurecia.entity.account.User;
import com.aqap.matrix.faurecia.model.ReturnMessage;
import com.aqap.matrix.faurecia.model.TemplateData;
import com.aqap.matrix.faurecia.model.WxTemplate;
import com.aqap.matrix.faurecia.service.wxweb.UserWxService;
import com.aqap.matrix.faurecia.utils.CaptchaException;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.Function;
import com.aqap.matrix.faurecia.utils.UsernamePasswordCaptchaToken;
import com.aqap.matrix.faurecia.utils.wx.WxUtils;
import com.google.common.collect.Maps;

@Component
@Transactional(readOnly = true)
public class WxUtilService {
	private static Logger logger = LoggerFactory.getLogger(WxUtilService.class);
	
	@Autowired
	private UserWxService userService;
	@Autowired
	private WxService wxService;
	
	/**
	 * 获取用户
	 * @param request
	 * @return
	 */
	@Transactional(readOnly=false)
	public User dealuserinfo(HttpServletRequest request) {
		User user = getWxUserInfo(request);
		if(user == null){
			String code = request.getParameter("code");
			if(StringUtils.isNotBlank(code)){
				logger.debug("用户授权code:" + code);
				try{
					Map<String, Object> map = WxUtils.getAccessTokenByCode(code);
					if (map != null && map.get("access_token")!=null && map.get("openid")!=null) {
						//获取openid和token
						String openid = map.get("openid").toString();
						String access_token = map.get("access_token").toString();
						
						if(StringUtils.isNotEmpty(openid)){
							
							Map<String,Object> userMap = WxUtils.getUserInfoByUserInfo(openid,access_token);
							request.getSession().setAttribute("openid", openid);
							request.getSession().setAttribute("authType", CommonString.AUTO_TYPE_USERINFO);
							request.getSession().setAttribute("userinfoscope_usermap", userMap);
							
							user = userService.findByOpenidAndStatus(openid,User.STATUS_SUCCESS);
							
							if(user != null){
								ReturnMessage urm = wxService.saveWxInfo(user,request);
								
								if(null != urm && "0".equals(urm.getResult())){
									logger.info("更新信息成功，人员openid："+openid+",userid:"+user.getId()+",username:"+user.getUserName());
								}else{
									logger.info("更新信息失败，人员openid："+openid+",userid:"+user.getId()+",username:"+user.getUserName());
								}
								
								
//								if(null != user){
//									Company company = user.getCompany();
//									if(null != company && company.getStatus() != Company.STATUS_SUCCESS){
//										user.setCompany(null);
//									}
//								}
								request.getSession().setAttribute("isadmin", user.getIsAdmin());
								request.getSession().setAttribute("wxuserinfo", user);
								
								
								//授权
							}
						}
					}
				}catch (Exception e) {
					logger.debug(e.getMessage());
				}
			}
		}else{
			request.getSession().setAttribute("authType", CommonString.AUTO_TYPE_USERINFO);
		}
		

		
		
		return user;
	}
	
	/**
	 * 从session获取用户信息
	 * @param request
	 * @return
	 */
	public User getWxUserInfo(HttpServletRequest request) {
		Object user = request.getSession().getAttribute("wxuserinfo");
		if(user != null){
			User nuser = (User) user;
			//用户状态正常
			if(nuser.getStatus() == User.STATUS_SUCCESS){
//				Company company = nuser.getCompany();
//				if(null != company && company.getStatus() != Company.STATUS_SUCCESS){
//					nuser.setCompany(null);
//				}
				return nuser;
			}
		}
		return null;
	}


	/**
	 * 静默获取用户信息
	 * @param request
	 * @return
	 */
	public User dealBaseInfo(HttpServletRequest request) {
		User user = getWxUserInfo(request);
		if(user == null){
			String code = request.getParameter("code");
			if(StringUtils.isNotBlank(code)){
				logger.debug("用户授权code:" + code);
				try{
					Map<String, Object> map = WxUtils.getAccessTokenByCode(code);
					if (map != null && map.get("access_token")!=null && map.get("openid")!=null) {
						//获取openid和token
						String openid = map.get("openid").toString();
						if(StringUtils.isNotEmpty(openid)){
							request.getSession().setAttribute("openid", openid);
							request.getSession().setAttribute("authType", CommonString.AUTO_TYPE_BASEINFO);
							user = userService.findByOpenid(openid);
							if(null != user){
								request.getSession().setAttribute("userid", user.getId());
								request.getSession().setAttribute("wxuserinfo", user);
								
							}
						}
					}
				}catch (Exception e) {
					logger.debug(e.getMessage());
				}
			}
		}
		return user;
	}


	/**
	 * xx通知
	 * @param first
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param remark
	 * @param url
	 * @param openId
	 * @param accessToken
	 */
	public int invitation(String first, String keyword1, String keyword2, String keyword3, String remark,
			String url, String openId, String accessToken) {
		
		String templateid = Function.getEnvironment("");
		
		WxTemplate temp = new WxTemplate();
		temp.setUrl(url);
		temp.setTouser(openId);
		temp.setTopcolor("#000000");
		temp.setTemplate_id(templateid);
		Map<String, TemplateData> map = Maps.newHashMap();
		TemplateData templateData = new TemplateData();
		templateData.setColor("#000000");
		templateData.setValue(first);
		map.put("first", templateData);
		templateData = new TemplateData();
		templateData.setColor("#000000");
		templateData.setValue(remark);
		map.put("remark", templateData);
		templateData = new TemplateData();
		templateData.setColor("#000000");
		templateData.setValue(keyword1);
		map.put("keyword1", templateData);
		templateData = new TemplateData();
		templateData.setColor("#000000");
		templateData.setValue(keyword2);
		map.put("keyword2", templateData);
		templateData = new TemplateData();
		templateData.setColor("#000000");
		templateData.setValue(keyword3);
		map.put("keyword3", templateData);
		temp.setData(map);
		int result = sendTemplateMessage(accessToken, openId, temp);
		return result;
	}
	
	
	/**
	 * 调研通知
	 * @param first
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param remark
	 * @param url
	 * @param openId
	 * @param accessToken
	 * @return
	 */
	public int researchNotice(String first, String keyword1, String keyword2, String keyword3, String remark,
			String url, String openId, String accessToken) {
		
		String templateid = Function.getEnvironment("template_research_id");
		
		WxTemplate temp = new WxTemplate();
		temp.setUrl(url);
		temp.setTouser(openId);
		temp.setTopcolor("#000000");
		temp.setTemplate_id(templateid);
		Map<String, TemplateData> map = Maps.newHashMap();
		TemplateData templateData = new TemplateData();
		templateData.setColor("#000000");
		templateData.setValue(first);
		map.put("first", templateData);
		templateData = new TemplateData();
		templateData.setColor("#000000");
		templateData.setValue(remark);
		map.put("remark", templateData);
		templateData = new TemplateData();
		templateData.setColor("#000000");
		templateData.setValue(keyword1);
		map.put("keyword1", templateData);
		templateData = new TemplateData();
		templateData.setColor("#000000");
		templateData.setValue(keyword2);
		map.put("keyword2", templateData);
		templateData = new TemplateData();
		templateData.setColor("#000000");
		templateData.setValue(keyword3);
		map.put("keyword3", templateData);
		temp.setData(map);
		int result = sendTemplateMessage(accessToken, openId, temp);
		return result;
	}
	

	/**
	 * 发送模板消息
	 */
	public static int sendTemplateMessage(String accessToken, String openId, WxTemplate temp) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

		JSONObject jsonObject = WxUtils.httpRequest(url, "POST", JSONObject.toJSONString(temp));
		int result = 0;
		if (null != jsonObject) {
			if (0 != jsonObject.getInteger("errcode")) {
				result = jsonObject.getInteger("errcode");
				logger.error(result + jsonObject.getString("errmsg"));
			}
		}
		logger.info("发送消息模板:"+jsonObject.toString());
		
		return result;
	}
	
	
	
	/**
	 * 微信登录
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	public void wxlogin(HttpServletRequest request, String username,String password){
		//获取验证码
		String result = "";
	
		//subject理解成权限对象。类似user
		Subject subject = SecurityUtils.getSubject();
//		//先登出
//		if(subject != null && subject.isAuthenticated()){
//			subject.logout();
//		}
		
		if(username != null && !"".equals(username)&& !subject.isAuthenticated()){
			try {
				//subject理解成权限对象。类似user
				//创建用户名和密码的令牌
				UsernamePasswordCaptchaToken token = new UsernamePasswordCaptchaToken();
				token.setUsername(username);
				token.setPassword(password.toCharArray());
				token.setUsertype("wx");
				try {
					subject.login(token);
				} catch (UnknownAccountException e) {
					request.setAttribute("shiroLoginFailure",e.getClass().getName());
					logger.debug(e.getMessage());
					result = CommonString.ACCOUNT_NOTEXIST;
				} catch (IncorrectCredentialsException e) {
					request.setAttribute("shiroLoginFailure",e.getClass().getName());
					logger.debug(e.getMessage());
					result = CommonString.ACCOUNT_PASSWOR_WRONG;
				}catch (CaptchaException e) {
					request.setAttribute("shiroLoginFailure",e.getClass().getName());
					logger.debug(e.getMessage());
					result = CommonString.CAPTCHA_UNCORRECT;
				}
				//验证是否成功登录的方法
				if (subject.isAuthenticated()){
					result = CommonString.LOGIN_SUCCESS;
					logger.info("用户："+username+"，登录成功");
				}
			}catch (Exception e) {
				e.printStackTrace();
				result = CommonString.SYSTEM_ERROR;
			}
			}else{
				result = CommonString.DATA_UNCORRECT;
			}
		
		
	}



	/**
	 * 跳转中间提示
	 * @param tiptype  类型 ture false 提示不同图标
	 * @param title	 标题
	 * @param message
	 * @param url1
	 * @param url2
	 * @param model
	 * @param request
	 * @return
	 */
	public String tomsg(String msgtype, String msgtitle, String msgmessage,
			String msgurl1,String msgurl1name,String msgurl2, String msgurl2name, Model model,
			HttpServletRequest request) {
		model.addAttribute("msgtype",msgtype);
		model.addAttribute("msgtitle",msgtitle);
		model.addAttribute("msgmessage",msgmessage);
		model.addAttribute("msgurl1",msgurl1);
		model.addAttribute("msgurl1name",msgurl1name);
		model.addAttribute("msgurl2",msgurl2);
		model.addAttribute("msgurl2name",msgurl2name);
		
		return "/wxweb/tomsg";
	}


	/**
	 * 登出
	 */
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		if(subject != null && subject.isAuthenticated()){
			subject.logout();
		}
	}

	/**
	 * 更新用户session
	 * @param request
	 */
	public void updateUserSession(HttpServletRequest request) {
		User user = getWxUserInfo(request);
		if(null != user){
			String userid = user.getId();
			user = userService.findById(userid);
			request.getSession().setAttribute("wxuserinfo",user);
		}
		
	}
	
	
	/**
	 * 从微信服务器下载多媒体文件
	 *
	 * @author lyh
	 * @date 2017-7-3
	 */
	public static String downloadMediaFromWx(String accessToken, String mediaId, String fileSavePath,HttpServletRequest request)
			throws IOException {
		
		if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(mediaId)){
			return null;
		}
		String download_media_url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		String requestUrl = download_media_url.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		InputStream in = conn.getInputStream();
		
		String savePath = request.getRealPath("");
		String allfileSavePath = savePath + fileSavePath;
		
		File dir = new File(allfileSavePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!allfileSavePath.endsWith("/")) {
			allfileSavePath += "/";
		}

		String ContentDisposition = conn.getHeaderField("Content-disposition");
		String weixinServerFileName = ContentDisposition.substring(ContentDisposition.indexOf("filename") + 10,
				ContentDisposition.length() - 1);
		allfileSavePath += weixinServerFileName;
		fileSavePath += weixinServerFileName;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(allfileSavePath));
		byte[] data = new byte[1024];
		int len = -1;

		while ((len = in.read(data)) != -1) {
			bos.write(data, 0, len);
		}

		bos.close();
		in.close();
		conn.disconnect();

		return fileSavePath;
	}
	
}
