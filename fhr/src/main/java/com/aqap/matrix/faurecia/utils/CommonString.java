package com.aqap.matrix.faurecia.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * 常用数据串变量
 * @author Administrator
 *
 */
public class CommonString {
	
	//数据不正确
	public static final String DATAUNCORRECT = "-1";
	//成功
	public static final String SUCCESS = "0";
	//失败
	public static final String FAIL = "1";
	//不存在
	public static final String NOEXIST = "2";
	//已存在
	public static final String HASEXIST = "3";
	//锁定
	public static final String CARDLOCK = "99";

	
	//数据不正确
	public static final String DATAUNCORRECT_TIP = "参数不正确，请填写正确的参数";
	
	public static final String DATAERROR_TIP = "数据异常";
	
	public static final String SYSTEMERROR_TIP = "系统异常";
	
	public static final String DATANOTEXIST_TIP = "数据已不存在，请刷新再操作";
	
	public static final String RESUBMIT_TIP = "请勿重复提交";
	
	public static final String SUCCESS_TIP = "提交成功";
	
	public static final String PARTYID = "fhrprokey";
	public static final String EMPLOYEE_INFO = "EMPLOYEE_INFO";
	
    public static final String SUCCESS_MSG = "操作成功！";
    public static final String FAIL_MSG = "操作失败！";
    
    public static final String SUCCESS_FLAG = "S";
    public static final String FAIL_FLAG = "F";
	
	
	//session 会话过期
	public static String LOGINOUT = "-2";
	//系统错误
	public static String SYSTEM_ERROR = "-1";
	//执行成功
	public static String LOGIN_SUCCESS = "0";
	//数据不准确
	public static String DATA_UNCORRECT = "1";	
	//用户帐户已存在
	public static String ACCOUNT_EXIST = "2";
	//手机号码不正确
	public static String MOBILE_UNCORRECT = "3";
	//邮箱不正确
	public static String EMAIL_UNCORRECT = "4";
	//手机验证码不正确
	public static String MOBILE_CAPTCHA_UNCORRECT = "5";
	//邮件发送失败
	public static String EMAIL_SEND_ERROR = "6";
	//手机信息发送失败
	public static String MOBILE_SEND_ERROR = "7";
	//手机验证码过期失效
	public static String MOBILE_CODE_TIMEOUT = "8";
	//手机验证不一致
	public static String MOBILE_CHECK_NOTSAME = "9";
	//验证码不正确
	public static String CAPTCHA_UNCORRECT = "10";
	//帐号不存在
	public static String ACCOUNT_NOTEXIST = "11";
	//用户名或密码错误
	public static String ACCOUNT_PASSWOR_WRONG = "12";
	//登录认证错误
	public static String AUTHENTICATION_EXCEPTION = "13";
	//密码错误
	public static String PASSWORD_ERROR = "14";
	//验证码不存在
	public static String VALIDATECODE_NOEXIST = "15";
	//密码不合格
	public static String PASSWORD_FAIL = "16";
	
	public static String getCodeMessage(String code){
		String message = "";
		if(code != null && !"".equals(code)){
			if(SUCCESS.equals(code)){
				message = "执行成功";
			}else if(DATA_UNCORRECT.equals(code)){
				message = "数据不正确";
			}else if(ACCOUNT_EXIST.equals(code)){
				message = "用户帐户已存在";
			}else if(MOBILE_UNCORRECT.equals(code)){
				message = "手机号码不正确";
			}else if(EMAIL_UNCORRECT.equals(code)){
				message = "邮箱不正确";
			}else if(MOBILE_CAPTCHA_UNCORRECT.equals(code)){
				message = "手机验证码不正确";
			}else if(EMAIL_SEND_ERROR.equals(code)){
				message = "邮件发送失败";
			}else if(MOBILE_SEND_ERROR.equals(code)){
				message = "手机信息发送失败";
			}else if(MOBILE_CODE_TIMEOUT.equals(code)){
				message = "手机验证码过期";
			}else if(MOBILE_CHECK_NOTSAME.equals(code)){
				message = "手机验证码不一致";
			}else if(CAPTCHA_UNCORRECT.equals(code)){
				message = "验证码不正确";
			}else if(ACCOUNT_NOTEXIST.equals(code)){
				message = "帐号不存在";
			}else if(ACCOUNT_PASSWOR_WRONG.equals(code)){
				message = "用户名或密码错误";
			}else if(AUTHENTICATION_EXCEPTION.equals(code)){
				message = "登录认证错误";
			}else if(PASSWORD_ERROR.equals(code)){
				message = "密码不正确";
			}else if(VALIDATECODE_NOEXIST.equals(code)){
				message = "验证码不存在";
			}else if(PASSWORD_FAIL.equals(code)){
				message = "密码设置不合格";
			}else{
				message = "错误";
			}	
		}
		return message;
		
	}
	
	public static String URL_LOG_LIST_KEY1 = "URL_LOG_LIST_1";
	public static String URL_LOG_LIST_KEY2 = "URL_LOG_LIST_2";
	public static String CURRENT_URL_LOG_LIST_KEYNAME = URL_LOG_LIST_KEY1;
	

	public static String HS_LOG_LIST_KEY1 = "HS_LOG_LIST_1";
	public static String HS_LOG_LIST_KEY2 = "HS_LOG_LIST_2";
	public static String CURRENT_HS_LOG_LIST_KEYNAME = HS_LOG_LIST_KEY1;
	
	public static String WX_LOG_LIST_KEY1 = "WX_LOG_LIST_1";
	public static String WX_LOG_LIST_KEY2 = "WX_LOG_LIST_2";
	public static String CURRENT_WX_LOG_LIST_KEYNAME = WX_LOG_LIST_KEY1;
	
	
	//关注
	public static String IS_SUBSCRIBE = "1";
	public static String UN_SUBSCRIBE = "0";
	//返回状态
	public static String CODE_SUCCESSFUL = "0";
	public static String CODE_FALSE = "1";
	
	
	//授权类型
	public static String AUTO_TYPE_USERINFO = "userinfo";
	public static String AUTO_TYPE_BASEINFO = "baseinfo";
	
	public static Map<Integer, String> AUDIT_STEP_MAP(){
		Map<Integer, String> m = new HashMap<Integer, String>();
			m.put(1, "提交建议");
			m.put(2, "部门领导审批");
			m.put(3, "实施部门领导审批");
			m.put(4, "实施者执行");  
			m.put(5, "提案人确认已执行");
			m.put(6, "已推广");
		return m;
	}
	
	public static Map<Integer, String> AUDIT_STATUS_ANME_MAP(){
		Map<Integer, String> m = new HashMap<Integer, String>();
			m.put(1, "已提交");
			m.put(2, "部门领导已采纳,交由实施部门审批");
			m.put(3, "部门领导已采纳,交由提交者实施");
			m.put(4, "提交部门未采纳");
			m.put(5, "实施部门已采纳");
			m.put(6, "实施部门未采纳");
			m.put(7, "已实施");
			m.put(8, "提案人已确认实施");
			m.put(9, "已推广");
		return m;
	} 
	
	public static List<String> MONTH_OF_DAY() {
		List<String> list= new ArrayList<String>();
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar cale = Calendar.getInstance(); 
		cale.add(Calendar.MONTH,1);//设置为1号,当前日期既为本月第一天 
		cale.set(Calendar.DAY_OF_MONTH,0);
		// String lastDay = format.format(cale.getTime());
		int day = cale.get(Calendar.DAY_OF_MONTH);
		int month = cale.get(Calendar.MONTH)+1;
		int year = cale.get(Calendar.YEAR);
		int i = 1;
		String m = month < 10?"0"+month:""+month;
		while(i <= day) {
			String d = i < 10?"0"+i:""+i;
			list.add(year+"-"+m+"-"+d);
			
			i++;
		}
		return list;
	}
	
	public static void main(String ar[]) {
		CommonString.MONTH_OF_DAY();
	}
}
