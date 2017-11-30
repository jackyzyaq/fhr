package com.aqap.matrix.faurecia.utils.wx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.aqap.matrix.faurecia.entity.account.User;
import com.aqap.matrix.faurecia.service.UserService;
import com.aqap.matrix.faurecia.service.wx.WxService;
import com.aqap.matrix.faurecia.utils.MyX509TrustManager;
import com.google.common.collect.Maps;
import com.xguanjia.core.modules.mapper.JsonMapper;

public class BizWxUtils {
	@Autowired
	private UserService userManagerService;
	@Autowired
	private WxService wxService;

	private static Logger logger = LoggerFactory.getLogger(BizWxUtils.class);
	
	private static  String corp_id = "wx62bf39aa355fc464";//Function.getEnvironment("CORP_ID"); 
	private static  String corp_secret = "IAT84VbHI6jR_rJGMav5qQr2thR5lxyTu5LF4xUkT94";//Function.getEnvironment("CORP_SECRET");
	
	private static String BIZ_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corp_id+"&corpsecret="+corp_secret;
	private static String HTTP_GET = "GET";
	private static String HTTP_POST = "POST";
	private static Long TOKEN_EXPIRE = 7000000l;


	private static Map<String,String> tokenmap = new HashMap<String, String>();
	
	
	private static String getAccessToken(){
		String token = "";
		try {
			if(StringUtils.isNotEmpty(BizWxUtils.corp_id) && StringUtils.isNotEmpty(BizWxUtils.corp_secret)){
				JSONObject _jtoken = BizWxUtils.httpRequest(BIZ_ACCESS_TOKEN_URL, HTTP_GET, null);
				if(_jtoken != null){
					if(StringUtils.isNotBlank(_jtoken.getString("access_token"))){
						token = _jtoken.getString("access_token");
						logger.info("请求access_token:"+token);
					}else {
						logger.info("access_token请求失败 -> errcode:"+_jtoken.getString("errcode") +"errmsg"+_jtoken.getString("errmsg"));
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取企业微信token异常");
			logger.error(e.getMessage());
		}
		if(StringUtils.isBlank(token)){
			logger.error("获取企业微信token异常");
		}
		return token;
	}
	
	/**
	 * 获取access_token
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getAccessTokenResult(){
		
		if(tokenmap.containsKey("accesstoken") && tokenmap.containsKey("accesstokendate") && tokenmap.containsKey("jsapi_ticket")){
			String tdates = tokenmap.get("accesstokendate");
			Long tdate = Long.parseLong(tdates);
			Long currentdate = new Date().getTime();
			if(currentdate - tdate > TOKEN_EXPIRE){
				String access_token = getAccessTokenCount();
				if(StringUtils.isNotEmpty(access_token)){
					String jsapi_ticket = getJsapiTicket(access_token);
					tokenmap.put("accesstoken", access_token);
					tokenmap.put("jsapi_ticket", jsapi_ticket);
					tokenmap.put("accesstokendate", new Date().getTime()+"");
					return access_token;
				}
			}else{
				return tokenmap.get("accesstoken");
			}
		}else{
			String access_token = getAccessTokenCount();
			if(StringUtils.isNotEmpty(access_token)){
//				String jsapi_ticket = getJsapiTicket(access_token);
//				tokenmap.put("accesstoken", access_token);
//				tokenmap.put("jsapi_ticket", jsapi_ticket);
//				tokenmap.put("accesstokendate", new Date().getTime()+"");
				return access_token;
			}
		}
		return "";
	}
	
	/**
	 * 获取jsapi_ticket
	 * @return
	 */
	public static String getJsapiTicketResult(){
		
		if(tokenmap.containsKey("accesstoken") && tokenmap.containsKey("accesstokendate") && tokenmap.containsKey("jsapi_ticket")){
			String tdates = tokenmap.get("accesstokendate");
			Long tdate = Long.parseLong(tdates);
			Long currentdate = new Date().getTime();
			if(currentdate - tdate > TOKEN_EXPIRE){
				String access_token = getAccessTokenCount();
				if(StringUtils.isNotEmpty(access_token)){
					String jsapi_ticket = getJsapiTicket(access_token);
					tokenmap.put("accesstoken", access_token);
					tokenmap.put("jsapi_ticket", jsapi_ticket);
					tokenmap.put("accesstokendate", new Date().getTime()+"");
					return jsapi_ticket;
				}
			}else{
				return tokenmap.get("jsapi_ticket");
			}
		}else{
			String access_token = getAccessTokenCount();
			if(StringUtils.isNotEmpty(access_token)){
				String jsapi_ticket = getJsapiTicket(access_token);
				tokenmap.put("accesstoken", access_token);
				tokenmap.put("jsapi_ticket", jsapi_ticket);
				tokenmap.put("accesstokendate", new Date().getTime()+"");
				return jsapi_ticket;
			}
		}
		return "";
	}
	
	/**
	 * 获取access_token
	 */
	private static int getnum = 5;
	private static String getAccessTokenCount(){
		getnum = 0;
		String access_token = "";
		try {
			access_token = getAccessToken();
			getnum++;
			while(StringUtils.isEmpty(access_token)){
				if(getnum >= 1){
					break;
				}
				access_token = getAccessToken();
				getnum++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return access_token;
	}
	
	
	/**
	 * 获取jsapi_ticket
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 * @throws JSONException
	 */
	public static String getJsapiTicket(String accessToken) {
		String ticket = "";
		// 获取公众号jsapi_ticket的链接
		String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		if (accessToken != null) {
			String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken);
			// String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				Object objticket = jsonObject.get("ticket");
				if(null != objticket && StringUtils.isNotBlank((String) jsonObject.get("ticket"))){
					ticket =  String.valueOf(objticket);
				}
			}
		} else {
			logger.debug("*****token为空 获取ticket失败******");
		}

		return ticket;
	}
	
	
	public static String createTextMessage(String formuser,String touser,String content){
		String xml = "<xml><ToUserName><![CDATA["+touser+"]]></ToUserName><FromUserName><![CDATA["+formuser+"]]></FromUserName><CreateTime>"+System.currentTimeMillis()+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["+content+"]]></Content></xml>";
		return xml;
	}
	
	
	/**
	 * 生成二维码
	 * 
	 * @return
	 */
	public static Map<String, Object> getQrcode(String ticket, Map<String, Object> outputStr) {
		String qrcodeUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + ticket;
		JSONObject jsonObject = httpRequest(qrcodeUrl, "POST", JsonMapper.nonDefaultMapper().toJson(outputStr));
		Map<String, Object> map = Maps.newHashMap();
		if (null != jsonObject) {
			map.put("ticket", jsonObject.get("ticket"));
			map.put("expire_seconds", jsonObject.get("expire_seconds"));
			map.put("url", jsonObject.get("url"));
		}
		return map;
	}
	
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
			logger.info("请求返回信息："+jsonObject);
		} catch (ConnectException ce) {
			logger.error("请求失败："+ce.getMessage());
			ce.printStackTrace();
		} catch (Exception e) {
			logger.error("请求失败："+e.getMessage());
			e.printStackTrace();
		}
		return jsonObject;
	}


		/**
	 * 通过code换取网页授权access_token
	 * 
	 * @param ticket
	 * @param outputStr
	 * @return
	 */
//	public static Map<String, Object> getAccessTokenByCode(String code) {
//		String qrcodeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid
//				+ "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
//		JSONObject jsonObject = httpRequest(qrcodeUrl, "GET", null);
//		Map<String, Object> map = Maps.newHashMap();
//		if (null != jsonObject){
//			map.put("access_token", jsonObject.get("access_token"));
//			map.put("expires_in", jsonObject.get("expires_in"));
//			map.put("refresh_token", jsonObject.get("refresh_token"));
//			map.put("openid", jsonObject.get("openid"));
//			map.put("scope", jsonObject.get("scope"));
//		}
//		if(!jsonObject.containsKey("openid")){
//			logger.error("获取微信的openid失败");
//		}
//		return map;
//	}

		/**
	 * 根据openid获取用户信息
	 * 
	 * @param code
	 * @return 
	 */
	public static Map<String, Object> getUserInfo(String openid, String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";

		JSONObject jsonObject = httpRequest(url, "GET", null);
		logger.info("【根据openid获取用户信息】"+jsonObject.toString());
		Map<String, Object> map = Maps.newHashMap();
		if (null != jsonObject) {
			// 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
			map.put("subscribe", jsonObject.get("subscribe"));
			// 用户的标识，对当前公众号唯一
			map.put("openid", jsonObject.get("openid"));
			// 用户的昵称
			map.put("nickname", jsonObject.get("nickname"));
			// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
			map.put("sex", jsonObject.get("sex"));
			// 用户所在城市
			map.put("city", jsonObject.get("city"));
			// 用户所在国家
			map.put("country", jsonObject.get("country"));
			// 用户所在省份
			map.put("province", jsonObject.get("province"));
			// 用户的语言，简体中文为zh_CN
			map.put("language", jsonObject.get("language"));
			// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
			map.put("headimgurl", jsonObject.get("headimgurl"));
			// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
			map.put("subscribe_time", jsonObject.get("subscribe_time"));
			// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
			map.put("unionid", jsonObject.get("unionid"));
			// 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
			map.put("remark", jsonObject.get("remark"));
			// 用户所在的分组ID
			map.put("groupid", jsonObject.get("groupid"));
		}
		return map;
	}
	
	public static Map<String, Object> getUserInfoByUserInfo(String openid, String accessToken) {

		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid
				+ "&lang=zh_CN";
		
		JSONObject jsonObject = httpRequest(url, "GET", null);
		logger.info("【根据openid获取用户信息，userinfo】"+jsonObject.toString());
		Map<String, Object> map = Maps.newHashMap();
		
		if (null != jsonObject) {
			// 用户的标识，对当前公众号唯一
			map.put("openid", jsonObject.get("openid"));
			// 用户的昵称
			map.put("nickname", jsonObject.get("nickname"));
			// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
			map.put("sex", jsonObject.get("sex"));
			// 用户所在城市
			map.put("city", jsonObject.get("city"));
			// 用户所在国家
			map.put("country", jsonObject.get("country"));
			// 用户所在省份
			map.put("province", jsonObject.get("province"));
			// 用户的语言，简体中文为zh_CN
			map.put("language", jsonObject.get("language"));
			// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
			map.put("headimgurl", jsonObject.get("headimgurl"));
			// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
			map.put("unionid", jsonObject.get("unionid"));
		}
		return map;
	}
	
	public static void initWeixinJs(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String shareurl = request.getServletPath().toString();
		String pq = request.getQueryString();
		if(StringUtils.isNotEmpty(pq)){
			url = url +"?"+pq;
			shareurl = shareurl + "?" +pq;
		}
		
		Map<String, String> info = Sign.sign(BizWxUtils.getJsapiTicketResult(), url.toString());
		request.setAttribute("nonceStr", info.get("nonceStr"));
		request.setAttribute("timestamp", info.get("timestamp"));
		request.setAttribute("signature", info.get("signature"));
		request.setAttribute("appid",BizWxUtils.corp_id);
		request.setAttribute("shareurl",shareurl);
	}

	
	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 * @throws JSONException
	 */
//	public static AccessToken getAccessToken() {
//		// 获取公众号access_token的链接
//		String access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
//		AccessToken accessToken = null;
//		
//		String requestUrl = access_token.replace("APPID", appid).replace("APPSECRET", secret);
//		try {
//
//			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
//			// 如果请求成功
//			if (null != jsonObject) {
//				try {
//					accessToken = new AccessToken();
//					accessToken.setAccess_token(jsonObject.getString("access_token"));
//					accessToken.setExpires_in(jsonObject.getInteger("expires_in"));
//				} catch (JSONException e) {
//					accessToken = null;
//					// 获取token失败
//					logger.debug("获取token失败 errcode:{} errmsg:{}" + jsonObject.getInteger("expires_in")
//							+ jsonObject.getString("errmsg"));
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return null;
//		}
//
//		return accessToken;
//	}
	/**
	 * 获取部门信息
	 * 如果获取整个组织架构，ID传空
	 * @param ID
	 * @return
	 */
	public static JSONObject  syncBizDEPTInfo(String id) {
		String URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";
		URL = URL.replace("ACCESS_TOKEN", BizWxUtils.getAccessTokenResult()).replace("ID", id==null?"":id);
		JSONObject jsonObject = httpRequest(URL, "GET", null);
		
		logger.info("获取的部门信息:"+jsonObject);
		return jsonObject;
		
	}
	
	public static JSONObject  syncBizUserInfo(String dept_id,String fetch_child) {
		String URL = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";
		URL = URL.replace("ACCESS_TOKEN", BizWxUtils.getAccessTokenResult())
						.replace("DEPARTMENT_ID", dept_id==null?"":dept_id)
							.replace("FETCH_CHILD", fetch_child == null ?"0":fetch_child);
		
		JSONObject jsonObject = httpRequest(URL, "GET", null);
		logger.info("获取的部门用户信息:"+jsonObject);
		
		return jsonObject;
	}
	
	public static void main(String[] args) {
		//BizWxUtils.syncBizDEPTInfo("4");
		JSONObject jsonObject = BizWxUtils.syncBizUserInfo("4", "1");
		User u = new User();
		u = JSONObject.toJavaObject(jsonObject, User.class);
		
		logger.info(u.getBizname());
	}
}
