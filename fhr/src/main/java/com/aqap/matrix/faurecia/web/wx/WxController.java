package com.aqap.matrix.faurecia.web.wx;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aqap.matrix.faurecia.entity.account.User;
import com.aqap.matrix.faurecia.model.CallBackInfo;
import com.aqap.matrix.faurecia.service.wx.WxUtilService;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.Function;
import com.aqap.matrix.faurecia.utils.wx.WxAuthorizeLogic;
import com.aqap.matrix.faurecia.utils.wx.WxSaaSCallbackInfo;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * 微信页面控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/wx")
public class WxController {
	
	@Autowired
	private WxUtilService wxUtilService;
	
	@RequestMapping
	public String index(Model model){
		return "/wxweb/index";
	}
	
	
	@RequestMapping(value="/dealauth")
	public String dealauth(Model model,HttpServletRequest request){
		
		//先登出
		wxUtilService.logout();
		
		User user = wxUtilService.dealuserinfo(request);
		
		Object openidobj = request.getSession().getAttribute("openid");
		Object authType = request.getSession().getAttribute("authType");
		//判断是否存在openid，不存在就userinfo授权
		if(openidobj != null && StringUtils.isNotEmpty(openidobj.toString())
				&& authType != null &&  authType.toString().equals(CommonString.AUTO_TYPE_USERINFO)){
			//查询用户是否存在公司
			if(null != user){
				String openid = user.getOpenid();
				wxUtilService.wxlogin(request, openid, "");
				//进入首页
				return "redirect:/wx/staff/index";
			}
			//进入注册页面
			return "redirect:/wx/company";
		}else{
			String url = Function.getBackUrl(request);
			String trueurl = "";
			trueurl = Function.dealuserinfoUrl(url, "userinfo");
			return "redirect:"+trueurl;
		}
	}
	

	/**
	 * 微信回调响应
	 * @param req
	 * @return
	 */
    @RequestMapping(value="/callback")
    @ResponseBody
    public String callback(HttpServletRequest req){
        
        /** url中$CORPID$模板替换后的corpid **/
        String corpid ="wx62bf39aa355fc464";// req.getParameter("corpid");
        /** url中的签名 **/
        String msgSignature = req.getParameter("msg_signature");
        /** url中的时间戳 **/
        String timestamp = req.getParameter("timestamp");
        /** url中的随机字符串 **/
        String nonce = req.getParameter("nonce");
        /** 创建套件时验证回调url有效性时传入**/
        String echostr = req.getParameter("echostr");

        WxAuthorizeLogic wxAuthorizeLogic = new WxAuthorizeLogic();
        WxSaaSCallbackInfo wxSaaSCallbackInfo = new WxSaaSCallbackInfo();
        String result = "";
        try {
            if(StringUtils.isNotBlank(echostr)){    
                /*
                 * 验证回调url有效性
                 * 响应需对echostr参数解密并原样返回echostr明文(不能加引号，不能带bom头，不能带换行符)
                 * 注：创建解密对象时传入的是CORP_ID而不是SUITE_ID
                 */
            	WXBizMsgCrypt WXBizc = new WXBizMsgCrypt("Ld9A1v7S9ZLH0ihPsNf9b39gUKsB","YudcFXtvn6nPLUDl18vltoKV4K0tPWNdJK8h9KE5JMg","wx62bf39aa355fc464");
                String verifyURLResult = WXBizc.VerifyURL(msgSignature, timestamp, nonce, echostr);
                		//.verifyURL(msgSignature, 
                     //   timestamp, nonce, echostr, corpid);
                return verifyURLResult;
            }else {
                //获取加密信息
                String postData = IOUtils.toString(req.getInputStream(), "utf-8");
                CallBackInfo callbackInfo = null;
                if("$CORPID$".equals(corpid)){  //第三方回调协议的推送不会替换$CORPID$，普通消息推送才会替换
                    //获取解密信息，注意传入的是SUITE_ID不是CORP_ID
                    callbackInfo = wxSaaSCallbackInfo.getDecryptCallbcakInfo(
                            postData, msgSignature, timestamp, nonce, Function.getEnvironment("SUITE_ID"));
                }else{
                    //普通消息推送时是使用URL中的$CORPID$进行解密
                    callbackInfo = wxSaaSCallbackInfo.getDecryptCallbcakInfo(
                            postData, msgSignature, timestamp, nonce, corpid);
                }

                //处理不同类型的推送事件
                boolean isResponseSuccess = false;
                String infoType = callbackInfo.getInfoType();
                if(StringUtils.isBlank(infoType)){
                    return result;
                }
                if (infoType == "suite_ticket") {
					//推送suite_ticket
					isResponseSuccess = wxAuthorizeLogic.dealSuiteTicketEvent(callbackInfo);
				} else if (infoType == "create_auth") {
					//授权成功推送auth_code事件
					isResponseSuccess = wxAuthorizeLogic.dealCreateAuthEvent(callbackInfo);
				} else if (infoType == "change_auth") {
					//变更授权的通知
					isResponseSuccess = wxAuthorizeLogic.dealChangeAuthEvent(callbackInfo);
				} else if (infoType == "cancel_auth") {
					//取消授权的通知
					isResponseSuccess = wxAuthorizeLogic.dealCancelAuthEvent(callbackInfo);
				} else if (infoType == "contact_sync") {
					//通讯录变更通知
					isResponseSuccess = wxAuthorizeLogic.dealContactSyncEvent(callbackInfo);
				}
                
                /*
                 * 响应回调
                 * 应用提供商在收到推送消息后需要返回字符串success,
                 * 授权时返回值不是 success 时，会把返回内容当作错误信息显示（需要以UTF8编码）
                 */
                if(isResponseSuccess){
                	result = "success";
                }else{
                	result = "false";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return result;
    }
    
    
    
}
