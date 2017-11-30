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

import org.apache.commons.lang.time.DateFormatUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.aqap.matrix.faurecia.entity.account.ImproveTargetSetting;
import com.aqap.matrix.faurecia.entity.account.RewardSetting;
import com.aqap.matrix.faurecia.entity.audit.AuditForm;
import com.aqap.matrix.faurecia.service.RewardSettingService;
import com.aqap.matrix.faurecia.service.audit.AuditFormService;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.Page;
import com.aqap.matrix.faurecia.utils.ReflectPOJO;
import com.aqap.matrix.faurecia.utils.Util;

@Controller
@RequestMapping(value = "/fhrapi/rewardset/", produces={"text/html;charset=UTF-8;","application/json;"})
public class RewardSettingController {

	private static Logger logger = LoggerFactory.getLogger(RewardSettingController.class);
	
	@Autowired
	RewardSettingService its;
	@Autowired
	AuditFormService afs;
	  
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
	@RequestMapping(value = "/index", produces={"text/html;charset=UTF-8;","application/json;"})
	public void index(Page page, RewardSetting form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 page.setSidx(request.getParameter("sidx") != null && !request.getParameter("sidx").toString().equals("")?request.getParameter("sidx").toString():"update_date");
	     page.setSord(request.getParameter("sord") != null && !request.getParameter("sord").toString().equals("")?request.getParameter("sord").toString():"desc");
	     
	     StringBuffer sb = new StringBuffer("");
	     org.springframework.data.domain.Page<ImproveTargetSetting> p = null;//its.get(page.getPageIndex(),page.getPageSize(),form);
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
	public void save(Model model,@ModelAttribute("rewardsetting") RewardSetting form,
			HttpServletRequest request,HttpServletResponse response) {
		if(StringUtils.isNotEmpty(form.getImprove_id())){
			form.setReword_value(DateFormatUtils.format(new Date(), "yyyyMM"));
			form.setType_id(1);//月度最佳
			
			org.springframework.data.domain.Page<RewardSetting> p = its.getRewardSettings(1, 1, form);
			List<RewardSetting> i = p.getContent();
			RewardSetting newone = new RewardSetting();
			
			AuditForm af = afs.findOne(form.getImprove_id());
			form.setImprove_code(af.getFormCode());
			form.setImprove_dept_id(String.valueOf(af.getSubmitterDeptId()));
			form.setImprove_dept_name(af.getSubmitterDept());
			form.setImprove_emp_id(af.getSubmitEMId());
			form.setImprove_emp_name(af.getEMName());
			form.setImprove_solution(af.getProposedSolution());
			
			if(i != null && i.size() > 0) {
				newone = i.get(0);
				form.setId(newone.getId());
				form.setUpdate_time(new Date());
				its.save(form);
			}else {
				its.save(form);
			}
			
			commonJSONResponse(null,true,response);
			return ;
		}
			commonJSONResponse(null,false,response);
			return ;
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
