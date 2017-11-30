package com.aqap.matrix.faurecia.service.wx;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.entity.account.User;
import com.aqap.matrix.faurecia.model.ReturnMessage;
import com.aqap.matrix.faurecia.service.wxweb.UserWxService;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.EmojiFilter;
import com.aqap.matrix.faurecia.utils.Function;
import com.aqap.matrix.faurecia.utils.wx.WxUtils;

@Component
@Transactional(readOnly = true)
public class WxService {
	
	private static Logger logger = LoggerFactory.getLogger(WxService.class);
	
	@Autowired
	private UserWxService userWxService;

	/**
	 * 保存微信信息,保存 session
	 * @param u
	 * @return
	 */
	@Transactional(readOnly=false)
	public ReturnMessage saveWxInfo(User user,HttpServletRequest request) {
		
		String result = CommonString.FAIL;
		String message = CommonString.DATAUNCORRECT_TIP;
		ReturnMessage rm = new ReturnMessage();
		
		String openid = user.getOpenid();
		if(StringUtils.isNotBlank(openid)){
			
			//通过openid获取信息
			Map<String, Object> userMap = WxUtils.getUserInfo(openid, WxUtils.getAccessTokenResult());
			
			String ggopenid = Function.getMapString(userMap,"openid");
			if(StringUtils.isNotEmpty(ggopenid)){
				String subscribe = Function.getMapString(userMap, "subscribe");
				
				String nickname = Function.getMapString(userMap,"nickname");
				//没关注则获取session中数据
				if("0".equals(subscribe)){
					userMap =(Map<String, Object>) request.getSession().getAttribute("userinfoscope_usermap");
				}
				user.setOpenid(openid);
				
				
				nickname = Function.getMapString(userMap,"nickname");
				nickname =  EmojiFilter.filterEmoji(nickname);
				String sex = Function.getMapString(userMap,"sex");
				//性别
				Integer sex_i = 0;
				if(StringUtils.isNotEmpty(sex)){
					sex_i = Integer.parseInt(sex);
				}
				String city = Function.getMapString(userMap,"city");
				String country = Function.getMapString(userMap,"country");
				String province = Function.getMapString(userMap,"province");
				String headimgurl = Function.getMapString(userMap,"headimgurl");
				user.setNickName(nickname);
				user.setSex(sex_i);
				user.setCity(city);
				user.setCountry(country);
				user.setProvince(province);
				user.setUpdateTime(new Date());
				user.setHeadImgUrl(headimgurl);
				
				user = userWxService.save(user);

				result = CommonString.SUCCESS;
				message = "保存成功";
			}else{
				message = "保存失败";
			}
		}
		
		rm.setResult(result);
		rm.setMessage(message);
		return rm;
	}
	
	
	
	

}
