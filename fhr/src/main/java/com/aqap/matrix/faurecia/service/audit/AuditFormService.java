package com.aqap.matrix.faurecia.service.audit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.audit.AuditFormDao;
import com.aqap.matrix.faurecia.entity.audit.AuditForm;
import com.aqap.matrix.faurecia.service.manager.OperatelogService;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.google.common.collect.Lists;

/**
 * 审批表单
 * @author lyh
 * @date 2017-6-27 22:22:46
 * 
 */
@Component
@Transactional(readOnly = true)
public class AuditFormService {

	private Logger logger = LoggerFactory.getLogger(OperatelogService.class);
	
	@Autowired
	private AuditFormDao auditFormDao;
	
	@Transactional(readOnly = false)
	public String save(String module,String content){
		
		auditFormDao.save(new AuditForm());
		return CommonString.FAIL;
	}
	
	@Transactional(readOnly = false)
	public AuditForm save(AuditForm form){

		return auditFormDao.save(form);
	}
	
	@Transactional(readOnly = true)
	public AuditForm getFormById(String id){

		return auditFormDao.findOne(id);
	}
	
	/**
	 * 
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	public Page<AuditForm> list(int pageno,int pagesize){
		Sort sort = new Sort(Direction.DESC,"createdTime");
		Pageable pageable = new PageRequest(pageno - 1, pagesize,sort);
		return auditFormDao.findAll(pageable);
	}
	
	/**
	 * 
	 * @param pageno
	 * @param pagesize
	 * @param form
	 * @return Page<AuditForm>
	 */
	public Page<AuditForm> getAuditsByConditions2(int pageno,int pagesize,final AuditForm form){
		

		
		List<Integer> l = new ArrayList<Integer>();//Arrays.asList(form.getDepts());
		for(String did : form.getDepts().split(",")) {
			l.add(Integer.valueOf(did.trim()));
		}
		//l.toArray();
		Sort sort = new Sort(Direction.DESC,"updateTime");
		Pageable pageable = new PageRequest(pageno - 1, pagesize,sort);
		
		return auditFormDao.findBySubmitEMIdOrSubmitterDeptIdIn(form.getSubmitEMId(), l, pageable);//(s, pageable);
	}
	
	/**
	 * 
	 * @param pageno
	 * @param pagesize
	 * @param form
	 * @return Page<AuditForm>
	 */
	public Page<AuditForm> getAuditsByConditions(int pageno,int pagesize,final AuditForm form){
		
		Specification<AuditForm> s = new Specification<AuditForm>() {
			@Override
			public Predicate toPredicate(Root<AuditForm> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Order> orderlist = new ArrayList<Order>();
				List<Predicate> predicates = Lists.newArrayList();
				
				if (StringUtils.isNotEmpty(form.getDepts()) 
						&& StringUtils.isNotEmpty(form.getSubmitEMId())
							&& StringUtils.isNotEmpty(form.getExecutorId())
								&& StringUtils.isNotEmpty(form.getExecutorMgrId())) {
					try {
						In<Integer> in = builder.in(root.<Integer>get("submitterDeptId"));
						String[] dids = form.getDepts().split(",");
						for(String did : dids) {
							in.value(Integer.valueOf(did.trim()));
						}
						builder.or(in);
						Predicate pfrom = builder.equal(root.get("submitEMId"), form.getSubmitEMId());
						Predicate executor = builder.equal(root.get("executorId"), form.getExecutorId());
						Predicate executorMgr = builder.equal(root.get("executorMgrId"), form.getExecutorMgrId());
						predicates.add(builder.or(pfrom,in,executor,executorMgr));
					} catch (Exception e){
						logger.error(e.getMessage());
						e.printStackTrace();
					}
				}else if (StringUtils.isNotEmpty(form.getDepts())){
					In<Integer> in = builder.in(root.<Integer>get("submitterDeptId"));
					String[] dids = form.getDepts().split(",");
					for(String did : dids) {
						in.value(Integer.valueOf(did.trim()));
					}
					builder.or(in);
					predicates.add(builder.or(in));
				}else if (StringUtils.isNotEmpty(form.getSubmitEMId())){
					Predicate pfrom = builder.equal(root.get("submitEMId"), form.getSubmitEMId());
					predicates.add(builder.or(pfrom));
				}
				
				if (StringUtils.isNotEmpty(form.getEMName())) {
					Predicate pfrom = builder.like(root.get("EMName").as(String.class),"%"+ form.getEMName() +"%");
					predicates.add(pfrom);
				}
				
				if (StringUtils.isNotEmpty(form.getSubmitterDept())) {
					Predicate pfrom = builder.like(root.get("submitterDept").as(String.class),"%"+ form.getSubmitterDept() +"%");
					predicates.add(pfrom);
				}
				
				if (form.getStatus()!=0) {
					Predicate pfrom = builder.equal(root.get("status"),form.getStatus());
					predicates.add(pfrom);
				}
				
				if (StringUtils.isNotEmpty(form.getFormCode())) {
					Predicate pfrom = builder.like(root.get("formCode").as(String.class),"%"+ form.getFormCode() +"%");
					predicates.add(pfrom);
				}
				
				if (StringUtils.isNotEmpty(form.getNextAuditEMPId())) {
					Predicate pfrom = builder.equal(root.get("nextAuditEMPId").as(String.class),form.getNextAuditEMPId());
					predicates.add(pfrom);
				}
				//开始时间
				/*if(null != form.getCreatedTime()){
					//开始时间   大于等于 定义值
					//Date startTimeDate = Function.getDate(startTime, "yyyy-MM-dd hh:mm:ss");
					@SuppressWarnings("unchecked")
					Predicate pstartTime = builder.greaterThanOrEqualTo((Path)root.get("createdTime"),form.getCreatedTime());
					predicates.add(pstartTime);
				}
				*/
				//结束时间
				/*if(null != form.getCreatedTime()){
					//结束时间大于当前时间
					@SuppressWarnings("unchecked")
					Predicate pendTime = builder.lessThanOrEqualTo((Path)root.get("createdTime"),form.getCreatedTime());
					predicates.add(pendTime);
				}*/
				
				
				//orderlist.add(builder.desc(root.get("updateTime")));
				//orderlist.add(builder.desc(root.get("createdTime")));
				
				query.orderBy(orderlist);
				//query.where(in);
				
				Predicate p = builder.and(predicates.toArray(new Predicate[predicates.size()]));
				return p;
			}
			
		};
		
		Pageable pageable = new PageRequest(pageno - 1, pagesize);
		return auditFormDao.findAll(s, pageable);//(s, pageable);
	}
	
	/**
	 * 根据传入的form id 查找审批列表
	 * @param ids
	 * @return
	 */
	public List<AuditForm> getAuditFormsBysIDs(List<String> ids){

		
		return (List<AuditForm>) auditFormDao.findByIdIn(ids);
	} 
	
	
	public List<AuditForm> getAllForms(){
		
		return (List<AuditForm>) auditFormDao.findAll();
	}
	
	public AuditForm findOne(String formId){
			return auditFormDao.findOne(formId);
		}
	
	/**
	 * 
	 * @param pageno
	 * @param pagesize
	 * @param form
	 * @return Page<AuditForm>
	 */
	public Page<AuditForm> getReptDataByConditions(int pageno,int pagesize,final AuditForm form){
		
		Specification<AuditForm> s = new Specification<AuditForm>() {
			@Override
			public Predicate toPredicate(Root<AuditForm> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			  query.multiselect(root.get("srcip"), root.get("dstip"));
			  query.groupBy(root.get("srcip"));
			  
			return query.getRestriction();
			}
			};
		Pageable pageable = new PageRequest(pageno - 1, pagesize);
		return auditFormDao.findAll(s, pageable);//(s, pageable);
	}
	
	/**
	 * 
	 * @return
	 */
	
	public List<Object[]> getDBMonthData(AuditForm form){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar   cal_1=Calendar.getInstance();
		int year = cal_1.get(Calendar.YEAR);
		//获取当年第一天
		cal_1.clear();
		cal_1.set(Calendar.YEAR,year);
		String firstDay = format.format(cal_1.getTime());
		 
		//获取当年最后一天
		cal_1.clear();
		cal_1.set(Calendar.YEAR,year);
		cal_1.roll(Calendar.DAY_OF_YEAR, -1);
		String lastDay = format.format(cal_1.getTime());
		
		//统计所属部门
		String[] dids = form.getDepts().split(",");
		List<Integer> deptid = new ArrayList<Integer>();
		for(String did : dids) {
			deptid.add(Integer.valueOf(did.trim()));
		}
		
		List<Object[]> list = auditFormDao.getMontRept(firstDay,lastDay,deptid);
		
		return list;
	}
	
	public List<Object[]> getDBDaliyData(AuditForm form){
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 Calendar   cal_1=Calendar.getInstance();//获取当前日期 
		 cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		 String firstDay = format.format(cal_1.getTime());
		 
		//获取前月的最后一天
		 Calendar cale = Calendar.getInstance();   
		 cale.add(Calendar.MONTH,1);//设置为1号,当前日期既为本月第一天 
		 cale.set(Calendar.DAY_OF_MONTH,0);
		 String lastDay = format.format(cale.getTime());
		
		String[] dids = form.getDepts().split(",");
		List<Integer> deptid = new ArrayList<Integer>();
		for(String did : dids) {
			deptid.add(Integer.valueOf(did.trim()));
		}
		
		List<Object[]> list = auditFormDao.getDailyRept(firstDay,lastDay,deptid);
		
		return list;
	}
	
	 public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		  /*Calendar   cal_1=Calendar.getInstance();//获取当前日期 
		 cal_1.add(Calendar.MONTH, 0);
		 cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		 String firstDay = format.format(cal_1.getTime());
		 int year = cal_1.get(Calendar.YEAR);
		 System.out.println(firstDay);
		 
		//获取前月的最后一天
		 Calendar cale = Calendar.getInstance();   
		 cale.add(Calendar.MONTH,1);//设置为1号,当前日期既为本月第一天 
		 cale.set(Calendar.DAY_OF_MONTH,0);
		 String lastDay = format.format(cale.getTime());
		 System.out.println(lastDay);
		 
		 System.out.println(DateFormatUtils.format(new Date(), "yyyyMM"));*/
		 
		 Calendar   cal_1=Calendar.getInstance();//获取当前日期
		 int year = cal_1.get(Calendar.YEAR);
		 cal_1.clear();
		 cal_1.set(Calendar.YEAR,year);//设置为1号,当前日期既为本月第一天 
		 System.out.println(format.format(cal_1.getTime()));
		 
	 }
	 
	 
}

	

