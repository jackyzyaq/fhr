package com.aqap.matrix.faurecia.web.manager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aqap.matrix.faurecia.entity.account.ImproveTargetSetting;
import com.aqap.matrix.faurecia.entity.account.RewardSetting;
import com.aqap.matrix.faurecia.entity.audit.AuditForm;
import com.aqap.matrix.faurecia.service.BonusPointsService;
import com.aqap.matrix.faurecia.service.ImproveTargetSettingService;
import com.aqap.matrix.faurecia.service.RewardSettingService;
import com.aqap.matrix.faurecia.service.audit.AuditFormService;
import com.aqap.matrix.faurecia.service.audit.AuditNodeService;
import com.aqap.matrix.faurecia.utils.CommonString;

@Controller
@RequestMapping(value = "/fhrapi/db/", produces={"text/html;charset=UTF-8;","application/json;"})
public class ImproveDashboardController {

	private static Logger logger = LoggerFactory.getLogger(ImproveDashboardController.class);
	
	@Autowired
	AuditFormService auditFromService;
	
	@Autowired
	AuditNodeService auditNodeService;
	
	@Autowired
	ImproveTargetSettingService its;
	
	@Autowired
	RewardSettingService rss;

	@Autowired
	private BonusPointsService bps;
	
	Map<String, Integer> map = new HashMap<String, Integer>();
	Map<String, Integer> currentMonthMap = new HashMap<String, Integer>();
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * 合理化建议首页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="dbindex")
	public void dbindex(Model model,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value="depts") String depts ){
		
		AuditForm form =new AuditForm();
		ImproveTargetSetting itsform = new ImproveTargetSetting();
		form.setDepts(depts);
		itsform.setDepts(depts);
		List<Object[]> mlist = auditFromService.getDBMonthData(form);
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(this.monthRPTDataFormat(mlist));
		sb.append(",");
		sb.append(this.dailyRPTDataFormat(auditFromService.getDBDaliyData(form)));
		sb.append(",");
		//获取目标值
		Calendar   cal_1=Calendar.getInstance();//获取当前日期 
		itsform.setTargetYear(String.valueOf(cal_1.get(Calendar.YEAR)));
		sb.append(getDBTargetData(its.getTargetRptData(itsform)));
		sb.append(",");
		//本月最佳数据
		RewardSetting rs  = new RewardSetting();
		rs.setReword_value(DateFormatUtils.format(new Date(), "yyyyMM"));
		rs.setType_id(1);
		org.springframework.data.domain.Page<RewardSetting> p = rss.getRewardSettings(1, 1, rs);
		List<RewardSetting> i = p.getContent();
		sb.append(getRewardInfo(i));
		sb.append(",");
		//消息提醒
		String EMPId = request.getParameter("EMPId");
		form.setDepts("");
		form.setNextAuditEMPId(EMPId);
		//审批数
		if(StringUtils.isNotEmpty(EMPId)) {
			Page<AuditForm> audits = this.auditFromService.getAuditsByConditions(1, 10000, form);
			sb.append("\"auditcheck\":["+audits.getContent().size());
			sb.append("]");
			sb.append(",");
			//积分
			Long bp = bps.countBPByEMPId(EMPId);
			sb.append("\"bonuspoints\":["+ bp);
			sb.append("]");
		}else {
			sb.append("\"auditcheck\":["+0);
			sb.append("]");
			sb.append(",");
			sb.append("\"bonuspoints\":["+0);
			sb.append("]");
		}
		sb.append(",");
		//当月目标
		String month  = DateFormatUtils.format(new Date(), "yyyyMM");
		sb.append("\"imptarget\":["+ map.get(month));
		sb.append("]");
		sb.append(",");
		sb.append("\"currentmonthimp\":["+ currentMonthMap.get(month));
		sb.append("]");
		sb.append(",");
		sb.append("}");
		
		
		commonJSONResponse(JSONObject.fromObject(sb.toString()),true,response);
	}

	/**
	 * 获取本月最佳信息
	 * @param i
	 */
	private String getRewardInfo(List<RewardSetting> i) {
		StringBuilder sb = new StringBuilder();
		RewardSetting rs = new RewardSetting();
		sb.append("\"rewards\":[");
		if(i!=null && i.size() > 0) {
			rs = i.get(0);
		}
		sb.append(JSONObject.fromObject(rs).toString());
		sb.append("]");
		
		return sb.toString();
	}

	private Object getDBTargetData(List<Object[]> mlist) {
		StringBuilder sb = new StringBuilder();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int c_month = Calendar.getInstance().get(Calendar.MONTH)+1;
		JSONObject jo = new JSONObject();
		sb.append("\"targetmonthdata\":[");
		if(mlist == null || mlist.size() < 1){
			mlist = new ArrayList<Object[]>();
		}
			int m = 1;
			while(m <=12){
				boolean flag = false;
				int l = mlist.size();
				String month = ""+year;
				if(m <10) {
					month = month+"0"+m;
				}else {
					month = month+m;
				} 
				Object[] obj = null;
				for (int i = 0; i < l; i++) {
					obj = mlist.get(i);
					if(null != obj && null != obj[1] && month.equals(String.valueOf(obj[1]))) {
						jo.accumulate("month", month);
						jo.accumulate("vdata",Integer.valueOf(String.valueOf(obj[0] != null?obj[0]:"0")));
						flag = true;
						map.put(month,Integer.valueOf(String.valueOf(obj[0] != null?obj[0]:"0")));
						break;
					}
				}
				if(!flag){
					jo.accumulate("month", month);
					jo.accumulate("vdata", 0);
					map.put(month, 0);
				}
				m++;
			}
		sb.append(jo.toString());
		sb.append("],");
		
		sb.append("\"targetdailydata\":[");
		JSONObject jdaily = new JSONObject();
		
		//目标日数据
		double fb = Double.valueOf(map.get(String.valueOf(year+""+c_month)))/Double.valueOf(CommonString.MONTH_OF_DAY().size());
		Integer dailyTarget = (int)Math.ceil(fb);
		int md = 1;
		while(md <= CommonString.MONTH_OF_DAY().size()){
			String daily = "";
			if(md <10) {
				daily = "0"+md;
			}else {
				daily = ""+md;
			} 
			jdaily.accumulate("daily", daily);
			jdaily.accumulate("vdata", dailyTarget);
			md++;
		}
		sb.append(jdaily.toString());
		sb.append("]");

		return sb.toString();
	}

	private Object dailyRPTDataFormat(List<Object[]> mlist) {
		StringBuilder sb = new StringBuilder();
		JSONObject jo = new JSONObject();
		sb.append("\"dailydata\":[");
		if(mlist != null){
			int m = 1;
			while(m <= CommonString.MONTH_OF_DAY().size()){
				boolean flag = false;
				int l = mlist.size();
				String month = "";
				if(m <10) {
					month = "0"+m;
				}else {
					month = ""+m;
				} 
				Object[] obj = null;
				for (int i = 0; i < l; i++) {
					obj = mlist.get(i);
					if(null != obj && null != obj[1] && CommonString.MONTH_OF_DAY().get(m-1).equals(String.valueOf(obj[1]))) {
						jo.accumulate("daily", month);
						jo.accumulate("vdata",Integer.valueOf(String.valueOf(obj[0] != null?obj[0]:"0")));
						flag = true;
						break;
					}
				}
				if(!flag){
					jo.accumulate("daily", month);
					jo.accumulate("vdata", 0);
				}
				m++;
			}
			sb.append(jo.toString());
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String monthRPTDataFormat(List<Object[]> mlist){
		StringBuilder sb = new StringBuilder();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		JSONObject jo = new JSONObject();
		sb.append("\"monthdata\":[");
		if(mlist != null){
			int m = 1;
			while(m <=12){
				boolean flag = false;
				int l = mlist.size();
				String month = ""+year;
				if(m <10) {
					month = month+"0"+m;
				}else {
					month = month+m;
				} 
				Object[] obj = null;
				for (int i = 0; i < l; i++) {
					obj = mlist.get(i);
					if(null != obj && null != obj[1] && month.equals(String.valueOf(obj[1]))) {
						jo.accumulate("month", month);
						jo.accumulate("vdata",Integer.valueOf(String.valueOf(obj[0] != null?obj[0]:"0")));
						currentMonthMap.put(month, Integer.valueOf(String.valueOf(obj[0] != null?obj[0]:"0")));
						flag = true;
						break;
					}
				}
				if(!flag){
					jo.accumulate("month", month);
					jo.accumulate("vdata", 0);
					currentMonthMap.put(month, 0);
				}
				m++;
			}
			sb.append(jo.toString());
		}
		sb.append("]");
		return sb.toString();
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
}
