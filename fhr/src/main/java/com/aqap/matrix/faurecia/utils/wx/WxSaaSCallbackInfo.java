package com.aqap.matrix.faurecia.utils.wx;  

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.aqap.matrix.faurecia.model.CallBackInfo;
import com.aqap.matrix.faurecia.utils.Function;
import com.aqap.matrix.faurecia.utils.aes.WXBizMsgCrypt;

/**  
 * 微信授权逻辑
 * @author lyh  
 * @date 2017-8-30 
 */
public class WxSaaSCallbackInfo {
	
	private static Logger logger = LoggerFactory.getLogger(WxSaaSCallbackInfo.class);
	
	/**
	 * 获取解密后的回调信息
	 * @param encryptPostData 未解密的PostData
	 * @param msgSignature
	 * @param timestamp
	 * @param nonce
	 * @param corpid 用于创建解密类
	 * @return 返回解密后的内容
	 */
	public CallBackInfo getDecryptCallbcakInfo(String encryptPostData, 
			String msgSignature, String timestamp, String nonce, String corpid){
		CallBackInfo callbackInfo  = new CallBackInfo();
		try{
			WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(Function.getEnvironment("SUITE_TOKEN"), 
	        		Function.getEnvironment("SUITE_ENCODING_AES_KEY"), corpid);
			String decryptPostData = wxBizMsgCrypt.DecryptMsg(msgSignature, timestamp, nonce, encryptPostData);
			logger.info("decryptPostData=" + decryptPostData);
			
			//解析xml
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(decryptPostData);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			Element root = document.getDocumentElement();
			
			Node infoTypeNode = root.getElementsByTagName("InfoType").item(0);
			Node suiteIdNode = root.getElementsByTagName("SuiteId").item(0);
			Node timestampNode = root.getElementsByTagName("TimeStamp").item(0);
			Node authCorpIdNode = root.getElementsByTagName("AuthCorpId").item(0);
			Node authCodeNode = root.getElementsByTagName("AuthCode").item(0);
			Node suiteTicketNode = root.getElementsByTagName("SuiteTicket").item(0);
			Node seqNode = root.getElementsByTagName("Seq").item(0);
			
			if(infoTypeNode != null){
				callbackInfo.setInfoType(infoTypeNode.getTextContent());
			}
			if(suiteIdNode != null){
				callbackInfo.setSuiteId(suiteIdNode.getTextContent());
			}
			if(timestampNode != null){
				callbackInfo.setTimestamp(timestampNode.getTextContent());
			}
			if(authCorpIdNode != null){
				callbackInfo.setAuthCorpId(authCorpIdNode.getTextContent());
			}
			if(authCodeNode != null){
				callbackInfo.setAuthCode(authCodeNode.getTextContent());
			}
			if(suiteTicketNode != null){
				callbackInfo.setSuiteTicket(suiteTicketNode.getTextContent());
			}
			if(seqNode != null){
				callbackInfo.setSeq(seqNode.getTextContent());
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return callbackInfo;
	}

}
  