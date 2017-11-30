package com.aqap.matrix.faurecia.web.manager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aqap.matrix.faurecia.entity.account.ImproveTargetSetting;
import com.aqap.matrix.faurecia.service.ImproveTargetSettingService;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.Page;
import com.aqap.matrix.faurecia.utils.ReflectPOJO;
import com.aqap.matrix.faurecia.utils.Util;

@Controller
@RequestMapping(value = "/fhrapi/itarget/", produces={"text/html;charset=UTF-8;","application/json;"})
public class ImproveTargetSettingController {

	private static Logger logger = LoggerFactory.getLogger(ImproveTargetSettingController.class);
	
	@Autowired
	ImproveTargetSettingService its;

	  
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
	@RequestMapping(value = "/index", produces={"text/html;charset=UTF-8;","application/json;"})
	public void index(Page page, ImproveTargetSetting form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 page.setSidx(request.getParameter("sidx") != null && !request.getParameter("sidx").toString().equals("")?request.getParameter("sidx").toString():"update_date");
	     page.setSord(request.getParameter("sord") != null && !request.getParameter("sord").toString().equals("")?request.getParameter("sord").toString():"desc");
	     
	     StringBuffer sb = new StringBuffer("");
	     org.springframework.data.domain.Page<ImproveTargetSetting> p = its.getImproveTargetSettings(page.getPageIndex(),page.getPageSize(),form);
	     List<ImproveTargetSetting> result = p.getContent();
	     page.setTotalCount(result.size());
         sb.append("{\'totalCount\':" + p.getTotalElements() + ",\'pageSize\':" + page.getPageSize() + ",\'pageIndex\':" + page.getPageIndex() + ",");

	     
	     sb.append("\'rows\':[");
         Iterator<?> var10 = result.iterator();

         while(var10.hasNext()) {
        	 ImproveTargetSetting json = (ImproveTargetSetting)var10.next();
            sb.append("{");
            ArrayList attrList = new ArrayList();
            ReflectPOJO.getAttrList(json, attrList);
           // sb.append("\'flow_type\':").append("\'" + Global.flow_type[1] + "\',");
            Iterator<Object> var13 = attrList.iterator();

            while(var13.hasNext()) {
               String attr = (String)var13.next();
               String temp = Util.convertToString(ReflectPOJO.invokGetMethod(json, attr));
               //System.out.println(attr+":"+temp);
               sb.append("\'" + attr + "\':").append("\'" + temp + "\',");
            }

            if(attrList.size() > 0) {
               sb.deleteCharAt(sb.length() - 1);
            }

            attrList = null;
            sb.append("},");
         }

         if(result.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
         }

         sb.append("]}");
         JSONObject json1 = JSONObject.fromObject(sb.toString());
         logger.info("data json:"+json1.toString());
         response.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
         response.setHeader("Cache-Control", "no-cache");
         response.getWriter().println(json1.toString());

	}
	
	/**
	 * 保存部门目标
	 * @param model
	 * @param AuditForm
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public void save(Model model,@ModelAttribute("improvetarget") ImproveTargetSetting itsform,
			HttpServletRequest request,HttpServletResponse response,String gotoaction) {
		if(StringUtils.isNotEmpty(gotoaction) && gotoaction.equals("edit")){
			ImproveTargetSetting f = its.findByOne(itsform.getId());
			f.setTargetMonthly(itsform.getTargetMonthly());
			f.setTargetDeptId(itsform.getTargetDeptId());
			f.setTargetDeptName(itsform.getTargetDeptName());
			f.setTargetValue(itsform.getTargetValue());
			f.setUpdateTime(new Date());
			itsform = f;
		}
		if(null == itsform.getTargetMonthly() || null  == itsform.getTargetDeptId() || null  == itsform.getTargetValue()){
			commonJSONResponse(null,false,response);
			
		} else {
			its.save(itsform);
			commonJSONResponse(null,true,response);
		}
	}
	
	/**
	 *编辑表单 
	 * @param model
	 * @param response
	 * @param request
	 * @param formId
	 */
	@ResponseBody
	@RequestMapping(value="edit")
	public void edit(Model model,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value="id") String formId ){
		logger.info(formId);
		if(StringUtils.isNotEmpty(formId)) {
			ImproveTargetSetting form  = its.findByOne(formId);
			
			JSONObject o = JSONObject.fromObject(form);
			logger.info(o.toString());
			//logger.info(o.get("executeDeadline").toString());
			commonJSONResponse(o,true,response);
		} else {
			commonJSONResponse(null,false,response);
		}
	}
	
	
	/**
	 * 
	 * @param json
	 * @param flag
	 * @param response
	 */
	private void commonJSONResponse(JSONObject json,boolean flag,HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try {
        	if(!flag) {
        		json = new JSONObject();
    			json.put("code", CommonString.FAIL_FLAG);
    			json.put("msg", CommonString.FAIL_MSG);
        		
    		} else if(null == json) {
    			
    			json = new JSONObject();
	    		json.put("code", CommonString.SUCCESS_FLAG);
	    		json.put("msg", CommonString.SUCCESS_MSG);
    		} else {
    			json.put("code", CommonString.SUCCESS_FLAG);
	    		json.put("msg", CommonString.SUCCESS_MSG);
    		}
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
