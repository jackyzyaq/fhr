package com.aqap.matrix.faurecia.web.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aqap.matrix.faurecia.entity.account.User;
import com.aqap.matrix.faurecia.model.ReturnMessage;
import com.aqap.matrix.faurecia.service.UserService;
import com.aqap.matrix.faurecia.service.wx.WxService;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.wx.WxUtils;


/**
 * 用户后台管理
 * @author Jacky Yi
 * @date 2017-10-6
 */
@Controller
@RequestMapping(value = "/manager/user")
public class UserManagerController {

	@Autowired
	private UserService userManagerService;
	@Autowired
	private WxService wxService;
	
	private static Logger logger = LoggerFactory.getLogger(UserManagerController.class);
	  
	/**
	 * 用户列表
	 * @param model
	 * @param pageNumber
	 * @param pageSize
	 * @param nickName
	 * @param userName
	 * @param mobile
	 * @param bdstatus  微信绑定状态  1已绑定 2未绑定
	 * @param status 账号状态(0有效 1 失效)
	 * @param state  类型 专家
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
			@RequestParam(value = "nickName", defaultValue = "") String nickName,
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "mobile", defaultValue = "") String mobile,
			@RequestParam(value = "bdstatus", defaultValue = "") Long bdstatus,
			@RequestParam(value = "status", defaultValue = "") Long status,
			@RequestParam(value = "userType", defaultValue = "") Long userType) {
		Map<String,Object> parammap = new HashMap<String, Object>();
		parammap.put("nickName", nickName);
		parammap.put("userName", userName);
		parammap.put("mobile", mobile);
		parammap.put("bdstatus", bdstatus);
		parammap.put("status", status);
		parammap.put("userType", userType);
		
		Page<User> page = userManagerService.findUserManagerPage(nickName,userName,mobile,bdstatus,status,userType,null,null,null,null,pageNumber,pageSize);
		model.addAttribute("page", page);
		model.addAttribute("params", parammap);
		
		return "/manager/user/userList";
	}
	
	
	/**
	 * 用户详情表
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/form")
	public String form(Model model,
			@RequestParam(value = "id", defaultValue = "",required=false) String id) {
		User info = null;
		if(StringUtils.isNotEmpty(id)){
			info = userManagerService.findOne(id);
		}
		model.addAttribute("info", info);
		return "/manager/user/userForm";
	}
	
	/**
	 * 用户保存
	 * @param model
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(Model model,@ModelAttribute("user") User user,
			HttpServletRequest request) {
		String thumbId = request.getParameter("thumbId");
		return userManagerService.saveManager(user,thumbId);
	}
	
	/**
	 * 用户字段更新
	 * @param model
	 * @param id
	 * @param field
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public ReturnMessage update(Model model,
			@RequestParam(value = "id", defaultValue = "",required=true) String id,
			@RequestParam(value = "field", defaultValue = "",required=true) String field,
			@RequestParam(value = "param", defaultValue = "",required=true) String param) {
		
		
		if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(field) && StringUtils.isNotBlank(param) ){
			return userManagerService.updateField(id,field,param);
		}
		return null;
	}
	
	
	@RequestMapping(value = "/selectlist")
	public Object selectlist(Model model,
			@RequestParam(value = "pageNo", defaultValue = "1",required=false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10",required=false) int pageSize,
			@RequestParam(value = "nickName", defaultValue = "",required=false) String nickName,
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "mobile", defaultValue = "") String mobile,
			@RequestParam(value = "state", defaultValue = "") Long state,
			HttpServletRequest request){
		
		Map<String,Object> parammap = new HashMap<String, Object>();
		parammap.put("nickName", nickName);
		parammap.put("userName", userName);
		parammap.put("mobile", mobile);
		parammap.put("state", state);
		
		Page<User> page = userManagerService.findUserManagerPage(nickName,userName,mobile,null,state,null,null,null,null,null, pageNumber,pageSize);
		model.addAttribute("page", page);
		
		model.addAttribute("params", parammap);
		
		
		model.addAttribute("layoutstyle", "justpage");
		return "/manager/select/userMultiselect";
	}
	
	
	/**
	 * 获取人员多选
	 * @param model
	 * @param pageNumber
	 * @param pageSize
	 * @param nickName
	 * @param userName
	 * @param mobile
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectMultilist")
	public Map<String,Object> selectMultilist(Model model,
			@RequestParam(value = "pageNo", defaultValue = "1",required=false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10",required=false) int pageSize,
			@RequestParam(value = "nickName", defaultValue = "",required=false) String nickName,
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "mobile", defaultValue = "") String mobile,
			@RequestParam(value = "usertype", defaultValue = "") Long userType,
			@RequestParam(value = "productGroupId", defaultValue = "") String productGroupId,
			@RequestParam(value = "province", defaultValue = "") String province,
			@RequestParam(value = "city", defaultValue = "") String city,
			@RequestParam(value = "userGroupId", defaultValue = "") String userGroupId,
			HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<User> usersbyug = null;
		
		if(StringUtils.isNotBlank(userGroupId)){
			//usersbyug= userGroupManagerService.findById(userGroupId);
		}
		
		Page<User> list = userManagerService.findUserManagerPage(nickName,userName,mobile,null,User.STATUS_SUCCESS,userType,productGroupId,province,city,usersbyug,pageNumber,pageSize);
		
		
		map.put("list", list.getContent());
		map.put("pageNo",pageNumber);
		map.put("totalPages", list.getTotalPages());
		
		
		return map;
	}
	
	/**
	 * 通过openid获取微信用户
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wxuserinfo")
	public Object wxuserinfo(Model model,HttpServletRequest request){
		
		return "/manager/user/wxuserForm";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getwxuserinfo")
	public Map<String,Object> getwxuserinfo(Model model,
			@RequestParam(value = "openid", defaultValue = "") String openid
			,HttpServletRequest request){
		Map<String, Object> userMap = null;
		if(StringUtils.isNotBlank(openid)){
			userMap = WxUtils.getUserInfo(openid, WxUtils.getAccessTokenResult());
		}
		
		return userMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatewxuserinfo")
	public ReturnMessage updatewxuserinfo(Model model,
			@RequestParam(value = "openid", defaultValue = "") String openid
			,HttpServletRequest request){
		ReturnMessage rm = new ReturnMessage();
		String result = CommonString.FAIL;
		String message = "更新失败";
		if(StringUtils.isNotBlank(openid)){
			
			List<User> users = userManagerService.findByOpenid(openid);
			if(users.size() > 0 ){
				if(users.size() == 1){
					User user = users.get(0);
					ReturnMessage urm = wxService.saveWxInfo(user,request);
					
					if(null != urm && "0".equals(urm.getResult())){
						result = CommonString.SUCCESS;
						message = "更新信息成功，人员openid："+openid+",userid:"+user.getId()+",username:"+user.getUserName();
					}else{
						message = "更新信息失败，人员openid："+openid+",userid:"+user.getId()+",username:"+user.getUserName();
					}
				}else{
					message = "存在多个相同openid数据";
				}
			}
		}else{
			message = "更新信息失败，请输入openid";
		}
		
		rm.setResult(result);
		rm.setMessage(message);
		return rm;
	}
}
