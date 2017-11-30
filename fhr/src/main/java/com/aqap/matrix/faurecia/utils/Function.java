package com.aqap.matrix.faurecia.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqap.matrix.faurecia.entity.manager.AdminUser;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Function {

	private static Logger logger = LoggerFactory.getLogger(Function.class);
	
	private static Properties prop;
	
	public static String getDecryptPassword(String partyId, String userId, String password) {
		return Function.decrypt(Function.decrypt(password, partyId), userId);
	}
	
	public static String decrypt(String src, String enKey) {		
		return new String(decrypt(getBase64Decode(src),getEnKey(enKey)));
	}
	
	private static byte[] decrypt(byte[] src, byte[] enKey) {
		byte[] encryptedData = null;
		try {
			DESKeySpec dks = new DESKeySpec(enKey);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			encryptedData = cipher.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedData;
	}
	
	private static byte[] getBase64Decode(String str) {
		byte[] requestValue = new byte[str.length()];
		try {
			BASE64Decoder base64en = new BASE64Decoder();
			requestValue = base64en.decodeBuffer(str);
		} catch (Exception e) {
			
		}
		return requestValue;
	}
	
	private static String getBase64Encode(byte[] src) {
		String requestValue = "";
		try {
			BASE64Encoder base64en = new BASE64Encoder();
			requestValue = base64en.encode(src);
		} catch (Exception e) {
			
		}
		return requestValue;
	}
	
	private static byte[] getEnKey(String spKey) {
		byte[] desKey = null;
		try {
			byte[] desKey1 = md5(spKey);
			desKey = new byte[24];
			int i = 0;
			while (i < desKey1.length && i < 24) {
				desKey[i] = desKey1[i];
				i++;
			}
			if (i < 24) {
				desKey[i] = 0;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return desKey;
	}
	
	private static byte[] md5(String strSrc) {
		byte[] returnByte = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			returnByte = md5.digest(strSrc.getBytes("GBK"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnByte;
	}
	
	
	
	public static boolean isNullOrSpace(String src) {
		if (src == null || "".equals(src)) {
			return true;
		}
		return false;
	}
	
	public static String getEnvironment(String name) {
		if (prop == null)

			try {
				prop = new Properties();
				prop.load(Function.class.getResourceAsStream("/env.properties"));
			} catch (IOException e) {
				return null;
			}finally{
				
			}
		return StringUtils.trimToEmpty(prop.getProperty(name));
	}
	
	
	public static String[] getEncryptPassword(String partyId, String userId, String password) {
		if(isNullOrSpace(password)){
			String userInitPass = Function.getEnvironment("user_init_pass"); //�û���ʼ����
			if(userInitPass == null){
				password = Function.generatePwd();
			}
			else{
				password = userInitPass.trim(); 
				if (isNullOrSpace(password)){
					password = Function.generatePwd();
				}
			}
		}
		return new String[]{password,encrypt(encrypt(password, userId), partyId)};
	}
	
	private static byte[] encrypt(byte[] src, byte[] enKey) {
		byte[] encryptedData = null;
		try {
			DESKeySpec dks = new DESKeySpec(enKey);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encryptedData = cipher.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedData;
	}
	
	public static String encrypt(String src, String enKey) {
		return new String(getBase64Encode(encrypt(src.getBytes(),getEnKey(enKey))));
	}
	
	public static String generatePwd(){
		String table = "0123456789abcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] c = table.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			sb.append(c[new Double(Math.random() * c.length).intValue()]);
		}
		return sb.toString();
	}
	
	public static String getCurrentDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	
	public static String getYesterdayFlowDate(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		return format.format(c.getTime());
	}
	
	public static String getCurrentShortDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}
	
	
	public static String getFlowDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		return format.format(new Date());
	}
	
	public static AdminUser getCurrentUser(){
		AdminUser u = (AdminUser) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
		return u;
	}
	
	
	public static Integer changeStrToInteger(String num){
		Integer i = 0;
		if(StringUtils.isNotEmpty(num)){
			try {
				i = Integer.parseInt(num);
			} catch (Exception e) {
				i = 0;
			}
		}
		return i;
	}
	
	public static Integer treateNullInteger(Integer num){
		if(num == null){
			num = 0;
		}
		return num;
	}
	
	
	public static String treateNullString(Object str){
		String r = "";
		if(str != null){
			r = str.toString();
		}
		return r;
	}
	
	
	public static String getWebRootPath(HttpServletRequest request) {
		if(request != null){
			return request.getSession().getServletContext().getRealPath("/");
		}
		return "";
	}
	
	
	public static void dealFolderPath(String path){
		if(StringUtils.isNotEmpty(path)){
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
		}
	}
	
	public static void deleteFile(String path){
		if(StringUtils.isNotEmpty(path)){
			File file = new File(path);
			if(file.exists()){
				file.delete();
			}
		}
	}
	
	
	
	public static Map<String,String> getbb(){
		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat cformat = new SimpleDateFormat("yyMMdd");
		String shortdate = shortsdf.format(d);
		Map<String,String> map = new HashMap<String, String>();
		String bbtype = "";
		try {
			Date colock1 = format.parse(shortdate+" 08:00:00");
			Date colock2 = format.parse(shortdate+" 20:00:00");	
			long nowlong = d.getTime();
			long c1 = colock1.getTime();
			long c2 = colock2.getTime();
			String dt = "";
			if(nowlong < c1){
				bbtype = "night";
				c.add(Calendar.DATE, -1);
				dt = cformat.format(c.getTime());
			}else if(nowlong >= c1 && nowlong < c2){
				bbtype = "morning";
				dt = cformat.format(d);
			}else{
				bbtype = "night";
				dt = cformat.format(d);
			}
			map.put("bbtype", bbtype);
			map.put("datetime", dt);
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static void dealSearParam(Map<String, Object> searchParams,HttpServletRequest request){
		List<Map<String,String>> list = dealParam(searchParams);
		request.setAttribute("searchparamlist", list);
	}
	 
	public static List<Map<String,String>> dealParam(Map<String, Object> map){
		List<Map<String,String>> list = null;
		if(map != null){
			list = new ArrayList<Map<String,String>>();
			Set<String> keys = map.keySet();
			for (String key : keys) {
				if(map.get(key) != null){
				String v = map.get(key).toString();
				if(StringUtils.isNotEmpty(v)){
					Map<String,String> map1 = new HashMap<String, String>();
					map1.put("key", key);
					map1.put("value", v);
					list.add(map1);
				}
				}
			}
		}
		return list;
	}

	public static String getMapString(Map<String,Object> map,String param){
		if(map != null && StringUtils.isNotEmpty(param)){
			if(map.containsKey(param)){
				return Function.treateNullString(map.get(param));
			}
		}
		return "";
	}

	/**
	 * 获取当前连接
	 * @param request
	 * @return
	 */
	public static String getBackUrl(HttpServletRequest request) {
		String strBackUrl = "http://" + request.getServerName() //服务器地址  
					        + ":"   
					        + request.getServerPort()           //端口号  
					        + request.getContextPath()      //项目名称  
					        + request.getServletPath();      //请求页面或其他地址  
		
		if(StringUtils.isNotBlank(request.getQueryString())){
			strBackUrl += "?" + (request.getQueryString()); //参数  
		}
		return strBackUrl;
	}

	
	
	/**
	 * 获取连接  http://wx.xxxx.com:80/wx
	 * @param request
	 * @return
	 */
	public static String getCtxUrl(HttpServletRequest request) {
		String ctxurl = "http://" + request.getServerName() //服务器地址  
					        + ":"   
					        + request.getServerPort()           //端口号  
					        + request.getContextPath();      //项目名称  
		return ctxurl;
	}
	
  /**
   * 封装成授权链接
   * @param url
   * @param infotype snsapi_base：静默  userinfo ：授权
   * @return
   */
	public static String dealuserinfoUrl(String url, String infotype) {
		  String appid = getEnvironment("app_id");
		  String dealurl = "";
		  String newurl = "";
		  String scope = "snsapi_base";
		  if("userinfo".equals(infotype)){
			  scope = "snsapi_userinfo";
		  }
		  try{
			dealurl =  URLEncoder.encode(url,"utf-8");
			newurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+ dealurl+ "&response_type=code&scope="+scope+"&state=STATE#wechat_redirect";
		  }catch (UnsupportedEncodingException e) {
			  logger.error(e.getMessage());
		  }
		  return newurl;
	  }
	

	/**
     * 获得IP
     * @Description：
     * @return: 
     * @return String:
     */
    public static String getIpAddr(HttpServletRequest request){
			String ip = request.getHeader("x-forwarded-for");
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("Proxy-Client-IP");
	        }
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	            ip = request.getRemoteAddr();
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		     ip = request.getHeader("http_client_ip");  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		     ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		    }  
		    if (ip != null && ip.indexOf(",") != -1) {  
			 ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
			} 
	        if("0:0:0:0:0:0:0:1".equals(ip))
	        {
	        	ip="127.0.0.1";
	        	InetAddress addr;
				try {
					addr = InetAddress.getLocalHost();
					ip= addr.getHostAddress().toString();//获得本机IP
				} catch (UnknownHostException e) {
					ip="127.0.0.1";
				}
				
	        }
	        return ip;
	    }

	/**
	 * 格式化指定日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return 格式化的日期字符串
	 */
	public static String getFormatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * 获取随机6位数
	 * 100000-999999
	 * @return
	 */
	public static int getCode() {
        int intx;//定义两变量
        Random ne=new Random();//实例化一个random的对象ne
        intx = ne.nextInt(999999-100000+1)+100000;//为变量赋随机值100000-999999
        
        int qrcode = 0 != intx ? 0+ intx : 10000;
        
        return qrcode;
    }
	
	public static Date getDate(String time,String sdftype){
		SimpleDateFormat sdf=new SimpleDateFormat(sdftype);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return date;
	}
	
	private static String mobilereg = "^1[3-8]\\d{9}$"; 
	public static boolean checkIsMobile(String mobile){
		if(StringUtils.isNotEmpty(mobile)){
			  Pattern pattern = Pattern.compile(mobilereg);
			  Matcher matcher = pattern.matcher(mobile);
			  if(matcher.find()){
				  return true;
			  }
		}
		return false;
	}
	
	public static void main(String[] args) {
		//System.out.println(getFlowDate());
		//String s = getEncryptPassword(Function.getEnvironment("partyid"), "admin", "888888")[1];
		
		//System.out.println(getDecryptPassword("dwkq", "admin", "dux7tWtTVCHK56aPqqDrrQ=="));
		
		//String m = getEncryptPassword("barcode", "1112333213", "123456")[1];
		//System.out.println(s);
//		System.out.println(getCode());
		String a = "123456";
		System.out.println("!!!!!!!"+a.length());
		
		System.out.println(getEncryptPassword(CommonString.PARTYID, "fhrm", "123456")[1]);
	}

	
}
