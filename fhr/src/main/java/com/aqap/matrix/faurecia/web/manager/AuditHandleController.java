package com.aqap.matrix.faurecia.web.manager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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

import com.aqap.matrix.faurecia.entity.account.BonusPoints;
import com.aqap.matrix.faurecia.entity.account.ImproveTargetSetting;
import com.aqap.matrix.faurecia.entity.audit.AuditForm;
import com.aqap.matrix.faurecia.entity.audit.AuditNode;
import com.aqap.matrix.faurecia.service.BonusPointsService;
import com.aqap.matrix.faurecia.service.ImproveTargetSettingService;
import com.aqap.matrix.faurecia.service.audit.AuditFormService;
import com.aqap.matrix.faurecia.service.audit.AuditNodeService;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.aqap.matrix.faurecia.utils.Page;
import com.aqap.matrix.faurecia.utils.ReflectPOJO;
import com.aqap.matrix.faurecia.utils.Util;

@Controller
@RequestMapping(value = "/fhrapi/audit/", produces={"text/html;charset=UTF-8;","application/json;"})
public class AuditHandleController {

	private static Logger logger = LoggerFactory.getLogger(AuditHandleController.class);
	
	@Autowired
	AuditFormService auditFromService;
	
	@Autowired
	AuditNodeService auditNodeService;
	
	@Autowired
	ImproveTargetSettingService its;
	
	@Autowired
	BonusPointsService bps;
	  
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
	@RequestMapping(value = "/index", produces={"text/html;charset=UTF-8;","application/json;"})
	public void index(Page page, AuditForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 page.setSidx(request.getParameter("sidx") != null && !request.getParameter("sidx").toString().equals("")?request.getParameter("sidx").toString():"update_date");
	     page.setSord(request.getParameter("sord") != null && !request.getParameter("sord").toString().equals("")?request.getParameter("sord").toString():"desc");
	     
	     String nextempid = request.getParameter("nextEMPId");
	     if(StringUtils.isNotEmpty(nextempid))
	    	 form.setNextAuditEMPId(nextempid);
	     
	     StringBuffer sb = new StringBuffer("");
	     org.springframework.data.domain.Page<AuditForm> p = auditFromService.getAuditsByConditions(page.getPageIndex(),page.getPageSize(),form);
	     List<AuditForm> result = p.getContent();
	     page.setTotalCount(result.size());
         sb.append("{\'totalCount\':" + p.getTotalElements() + ",\'pageSize\':" + page.getPageSize() + ",\'pageIndex\':" + page.getPageIndex() + ",");

	     
	     sb.append("\'rows\':[");
         Iterator<?> var10 = result.iterator();

         while(var10.hasNext()) {
        	AuditForm json = (AuditForm)var10.next();
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
		
		//request.getSession().setAttribute(CommonString.EMPLOYEE_INFO, emInfo);
		
		//return json1.toString();
	}
	
	/**
	 * 保存建议单
	 * @param model
	 * @param AuditForm
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public void save(Model model,@ModelAttribute("auditForm") AuditForm auditForm,
			HttpServletRequest request,HttpServletResponse response,String gotoaction) {
		
		if (StringUtils.isNotEmpty(gotoaction) && gotoaction.equals("edit")){
			AuditForm form = auditFromService.findOne(auditForm.getId());
			form.setImproveSourcesList(auditForm.getImproveSourcesList());
			form.setImproveTargetList(auditForm.getImproveTargetList());
			form.setCurrentSituation(auditForm.getCurrentSituation());
			form.setProposedSolution(auditForm.getProposedSolution());
			form.setUpdateTime(new Date());
			auditForm = form;
			
			auditForm = auditFromService.save(auditForm);
		    logger.info(auditForm.getEMName()+" 提交合理化建议成功，表单ID为："+ auditForm.getId());
		} else {
			auditForm.setFormCode(genaratFormCode());
			auditForm.setStatus(1);
			auditForm.setAuditStep(2);
			auditForm.setStatusTxt(CommonString.AUDIT_STATUS_ANME_MAP().get(Integer.valueOf(1)));
			
			auditForm = auditFromService.save(auditForm);
			 
			if(null != auditForm && StringUtils.isNotEmpty(auditForm.getId())) {
				logger.info(auditForm.getEMName()+" 提交合理化建议成功，表单ID为："+ auditForm.getId());
				AuditNode node = new AuditNode();
				node.setName(CommonString.AUDIT_STEP_MAP().get(Integer.valueOf(1)));
			    node.setFromId(auditForm.getId());
			    node.setDeptId(StringUtils.isEmpty(auditForm.getNextAuditDept())?-1:Integer.valueOf(auditForm.getSubmitterDeptId()));
			    node.setEMId(auditForm.getSubmitEMId());
			    node.setUserId(auditForm.getSubmitUserId());
			    node.setIdxNumber(1);
			    node.setNextNode(auditForm.getAuditStep());
			    node.setStatus(auditForm.getStatus());
			    node.setComments(auditForm.getComment());
			    node.setNodeDesc(CommonString.AUDIT_STATUS_ANME_MAP().get(Integer.valueOf(auditForm.getStatus())));
			    //保存审批节点
			    node = auditNodeService.save(node); 
			    logger.info(auditForm.getEMName()+" 提交合理化建议成功，节点ID为："+ node.getId());
			    
			    //保存积分
			    saveBonusPoints(auditForm);
			} else {
				commonJSONResponse(null,false,response);
			}
		}
        commonJSONResponse(null,true,response);
	}
	
	/**
	 * 	保存积分
	 * @param auditForm
	 */
	private void saveBonusPoints(AuditForm auditForm) {
		BonusPoints bp = new BonusPoints();
		bp.setImproveId(auditForm.getId());
		bp.setImproveCode(auditForm.getFormCode());
		bp.setEMPId(auditForm.getSubmitEMId());
		bp.setEMPName(auditForm.getEMName());
		bp.setDeptId(auditForm.getSubmitterDeptId());
		bp.setDeptName(auditForm.getSubmitterDept());
		bp.setBPValues(1l);
		bp.setType(1);
		bp.setStatus(2);
		bp.setCreatedTime(new Date());
		bp.setUpdateTime(new Date());
		bp = this.bps.save(bp);
		
		logger.info(auditForm.getEMName()+" 提交合理化建议成功，并获取积分："+ bp.getId());
	}

	/**
	 * 建议审批
	 * @param model
	 * @param AuditForm
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	@ResponseBody
	public void audit(Model model,@ModelAttribute("auditForm") AuditForm auditForm,
			HttpServletRequest request,HttpServletResponse response,String action) {
		if(StringUtils.isNotEmpty(auditForm.getId())) {
			AuditForm form = auditFromService.findOne(auditForm.getId());
			if(StringUtils.isNotEmpty(form.getNextAuditEMPId()) && 
					form.getNextAuditEMPId().equals(form.getSubmitEMId()) && auditForm.getStatus() == 7) {
				auditForm.setStatus(8);
			}
			switch (auditForm.getStatus()) {
				case 2: //采纳，并指定实施部门
					form.setLineManagerId(form.getNextAuditEMPId());
					form.setLineManagerDept(form.getNextAuditDept());
					form.setLineManagerName(form.getNextAuditEMPName());
					form.setStatusTxt(CommonString.AUDIT_STATUS_ANME_MAP().get(2));
					form.setAuditStep(3);
					form.setLineMgrComment(auditForm.getLineMgrComment());
					form = this.setNextData(auditForm,form);
					
					auditForm.setComment(auditForm.getLineMgrComment());
					auditForm.setAuditStep(2);
					
	                break;
	             case 3: //采纳，由提交人实施
	            	 form.setLineManagerId(form.getNextAuditEMPId());
	            	 form.setLineManagerDept(form.getNextAuditDept());
	            	 form.setLineManagerName(form.getNextAuditEMPName());
	            	 form.setStatusTxt(CommonString.AUDIT_STATUS_ANME_MAP().get(3));
	            	 form.setAuditStep(4);
	            	 form.setLineMgrComment(auditForm.getLineMgrComment());
	            	 
	            	 auditForm.setComment(auditForm.getLineMgrComment());
	            	 auditForm.setAuditStep(2);
	            	 
	            	 form = this.setNextData(auditForm,form);
	                break;
	             case 4: //不采纳
	            	 form.setLineManagerId(form.getNextAuditEMPId());
	            	 form.setLineManagerDept(form.getNextAuditDept());
	            	 form.setLineManagerName(form.getNextAuditEMPName());
	            	 form.setStatusTxt(CommonString.AUDIT_STATUS_ANME_MAP().get(4));
	            	 form.setStatus(auditForm.getStatus());
	            	 form.setLineMgrComment(auditForm.getLineMgrComment());
	            	 auditForm.setComment(auditForm.getLineMgrComment());
	            	 form = this.setNextData(auditForm,form);
	            	 
	            	 auditForm.setAuditStep(2);
	            	 
                 break;
				case 5: //实施部门领导采纳，并指定实施人
					form.setExecutorMgrId(form.getNextAuditEMPId());
					form.setExecutorMgrDeptId(form.getNextAuditDept());
					form.setExecutorMgrName(form.getNextAuditEMPName());
					form.setExecutorMgrDept(form.getNextAuditDeptName());
					form.setExecuteDeadline(auditForm.getExecuteDeadline());
					form.setStatusTxt(CommonString.AUDIT_STATUS_ANME_MAP().get(5));
					form.setAuditStep(4);
					form.setExeMgrComment(auditForm.getExeMgrComment());
					auditForm.setComment(auditForm.getExeMgrComment());
					auditForm.setAuditStep(3);
					
					form = this.setNextData(auditForm,form);
                 break;
				case 6://实施部门领导不采纳
					form.setExecutorMgrId(form.getNextAuditEMPId());
					form.setExecutorMgrDeptId(form.getNextAuditDept());
					form.setExecutorMgrName(form.getNextAuditEMPName());
					form.setExecutorMgrDept(form.getNextAuditDeptName());
					form.setStatusTxt(CommonString.AUDIT_STATUS_ANME_MAP().get(6));
					form.setStatus(auditForm.getStatus());
					form.setExeMgrComment(auditForm.getExeMgrComment());
					auditForm.setComment(auditForm.getExeMgrComment());
					form = this.setNextData(auditForm,form);
					auditForm.setAuditStep(3);
					
                 break;
				case 7: //实施人确认实施，并交由提交人确认
					form.setStatusTxt(CommonString.AUDIT_STATUS_ANME_MAP().get(7));
					form.setExecutorId(form.getNextAuditEMPId());
					form.setExecutorName(form.getExecutorName());
					form.setExecutorDeptId(form.getNextAuditDept());
					form.setExecuteDate(auditForm.getExecuteDate());
					form.setAuditStep(5);
					form.setExeComment(auditForm.getExeComment());
					auditForm.setComment(auditForm.getExeComment());
					
					auditForm.setAuditStep(4);
					form = this.setNextData(auditForm,form);
                 break;
				case 8://提交人确认
					form.setStatusTxt(CommonString.AUDIT_STATUS_ANME_MAP().get(8));
					form.setSubmitterConfirm(form.getNextAuditEMPId());
					form.setConfirmDate(auditForm.getConfirmDate());
					form.setAuditStep(6);
					form.setSubmitterComment(auditForm.getSubmitterComment());
					form = this.setNextData(auditForm,form);
					
					auditForm.setComment(auditForm.getSubmitterComment());
					auditForm.setAuditStep(5);
					break;
				case 9://提交人确认isGeneralize
					form.setStatusTxt(CommonString.AUDIT_STATUS_ANME_MAP().get(9));
					form.setGeneralizaterId(auditForm.getGeneralizaterId());
					form.setGeneralizerComment(auditForm.getGeneralizerComment());
					form.setGeneralizaterName(auditForm.getGeneralizaterName());
					form.setGeneralizaterDept(auditForm.getGeneralizaterDept());
					form.setGeneralizaterDeptId(auditForm.getGeneralizaterDeptId());
					form.setGeneralizationNum(auditForm.getGeneralizationNum());
					form.setGeneralizerComment(auditForm.getGeneralizerComment());
					
					auditForm.setComment(auditForm.getGeneralizerComment());
					auditForm.setAuditStep(6);
					form = this.setNextData(auditForm,form);
					
					break;
				default : 
					commonJSONResponse(null,false,response);
					break;
			}
			form.setLatestAuditName(auditForm.getLatestAuditName());//最新审核人
			AuditNode node = this.saveNodeInfo(auditForm);
			form.setUpdateTime(new Date());
			auditFromService.save(form);
		    logger.info(auditForm.getNextAuditEMPName()+" 审核合理化建议成功，表单ID为："+ auditForm.getId());
		    node = auditNodeService.save(node); 
		    logger.info(auditForm.getNextAuditEMPName()+" 审核合理化建议成功，节点ID为："+ node.getId());
		    commonJSONResponse(null,true,response);
		    
		    return ;
		}else {
			commonJSONResponse(null,false,response);
			return ;
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
	@RequestMapping(value="goaudit")
	public void goaudit(Model model,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value="id") String formId ){
			
		this.edit(model, response, request, formId);
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
			AuditForm form  = auditFromService.findOne(formId);
			
			JSONObject o = JSONObject.fromObject(form);
			logger.info(o.toString());
			//logger.info(o.get("executeDeadline").toString());
			commonJSONResponse(o,true,response);
		} else {
			commonJSONResponse(null,false,response);
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
	@RequestMapping(value="nodes")
	public void nodes(Model model,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value="form_id") String formId ){
		logger.info(formId);
		List<AuditNode> nodes = auditNodeService.findNodeByFormId(formId);
		JSONArray array = JSONArray.fromObject(nodes);
		StringBuilder sb = new StringBuilder();
		sb.append("{\"nodes\":");
		sb.append(array.toString());
		sb.append("},");
		
		commonJSONResponse(JSONObject.fromObject(sb.toString()),true,response);
	}
	
	private String genaratFormCode(){
		StringBuilder str=new StringBuilder();//定义变长字符串
		str.append("FII");
	    Random random=new Random();
	    //随机生成数字，并添加到字符串
	    for(int i=0;i<10;i++){
	        str.append(random.nextInt(10));
	    }
	    return str.toString();
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
        	logger.info(json.toString());
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private AuditNode saveNodeInfo(AuditForm auditForm) {
		AuditNode node = new AuditNode();
		node.setName(CommonString.AUDIT_STEP_MAP().get(Integer.valueOf(auditForm.getAuditStep())));
	    node.setFromId(auditForm.getId());
	    node.setDeptId(StringUtils.isEmpty(auditForm.getNextAuditDept())?-1:Integer.valueOf(auditForm.getSubmitterDeptId()));
	    node.setEMId(auditForm.getSubmitUserId());
	    node.setUserId(auditForm.getSubmitUserId());
	    node.setIdxNumber(auditForm.getAuditStep());
	    node.setNextNode(auditForm.getAuditStep());
	    node.setStatus(auditForm.getStatus());
	    node.setComments(auditForm.getComment());
	    node.setNodeDesc(CommonString.AUDIT_STATUS_ANME_MAP().get(Integer.valueOf(auditForm.getStatus())));
	    
	    return node;
	}
 
	private AuditForm setNextData(AuditForm auditForm,AuditForm oldForm){
		if(auditForm.getStatus() == 4 || 
				auditForm.getStatus() == 6 ||
					auditForm.getStatus() == 8 ||
						auditForm.getStatus() == 9){
			oldForm.setNextAuditEMPId(null);
			oldForm.setNextAuditDept(null);
			oldForm.setNextAuditDeptName(null);
			oldForm.setNextAuditEMPName(null);
			oldForm.setStatus(auditForm.getStatus());
			
			return oldForm;
		}
		
		auditForm.setNextAuditEMPId(StringUtils.isEmpty(auditForm.getNextAuditEMPId())?"-1":auditForm.getNextAuditEMPId().replace(",", ""));
		oldForm.setNextAuditDept(auditForm.getNextAuditDept());
		oldForm.setNextAuditDeptName(auditForm.getNextAuditDeptName());
		oldForm.setNextAuditEMPId(auditForm.getNextAuditEMPId());
		oldForm.setNextAuditEMPName(auditForm.getNextAuditEMPName());
		oldForm.setStatus(auditForm.getStatus());
		/*oldForm.setAuditStep(auditForm.getAuditStep());
		oldForm.setStatusTxt(auditForm.getStatusTxt());*/
		
		return oldForm;
	}
}
