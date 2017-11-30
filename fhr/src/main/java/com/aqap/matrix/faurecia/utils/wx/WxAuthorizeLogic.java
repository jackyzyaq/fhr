package com.aqap.matrix.faurecia.utils.wx;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqap.matrix.faurecia.model.CallBackInfo;
import com.aqap.matrix.faurecia.utils.Function;
import com.aqap.matrix.faurecia.utils.aes.AesException;
import com.aqap.matrix.faurecia.utils.aes.WXBizMsgCrypt;

/**  
 *  微信授权逻辑
 * @author lyh  
 * @date 2017-8-30 
 */
public class WxAuthorizeLogic {
	private static Logger logger = LoggerFactory.getLogger(WxAuthorizeLogic.class);
	
	
	/**
	 * 验证回调URL有效性
	 * @param msgSignature url中的签名
	 * @param timestamp url中的时间戳
	 * @param nonce url中的随机字符串
	 * @param echostr 回显字符串
	 * @param corpid 用于创建解密类
	 * @return 返回解密后的明文字符串
	 * @throws AesException
	 */
    public String verifyURL(String msgSignature, String timestamp, 
            String nonce, String echostr, String corpid) throws AesException{
        //注意创建解密对象时使用的是CORP_ID而不是SUITE_ID
        WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(Function.getEnvironment("CORP_TOKEN"), 
        		Function.getEnvironment("CORP_SECRET"), Function.getEnvironment("CORP_ID"));
        String result = wxBizMsgCrypt.VerifyURL(msgSignature, timestamp, nonce, echostr);
        logger.info("VerifyURLResult=" + result);
        return result;
    }


	public boolean dealSuiteTicketEvent(CallBackInfo callbackInfo) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean dealCreateAuthEvent(CallBackInfo callbackInfo) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean dealChangeAuthEvent(CallBackInfo callbackInfo) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean dealCancelAuthEvent(CallBackInfo callbackInfo) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean dealContactSyncEvent(CallBackInfo callbackInfo) {
		// TODO Auto-generated method stub
		return false;
	}


}
  