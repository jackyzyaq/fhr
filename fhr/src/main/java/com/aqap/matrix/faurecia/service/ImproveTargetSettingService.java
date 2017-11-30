package com.aqap.matrix.faurecia.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.account.ImproveTargetSettingDao;
import com.aqap.matrix.faurecia.entity.account.ImproveTargetSetting;
import com.aqap.matrix.faurecia.entity.audit.AuditForm;
import com.aqap.matrix.faurecia.service.manager.OperatelogService;
import com.google.common.collect.Lists;

/**
 * 附件管理
 * 
 * @author:Kirk Zhou
 * @date:2013-6-29下午04:40:58
 */
@Component
@Transactional(readOnly = true)
public class ImproveTargetSettingService {
	
	private Logger logger = LoggerFactory.getLogger(OperatelogService.class);
	@Autowired
	private ImproveTargetSettingDao itsDao;

	public List< ImproveTargetSetting> getImproveTargetSettingByIds(String ids) {
		if (StringUtils.isNotEmpty(ids)){
			String[] idarr = ids.split(",");
			List<String> idList = new ArrayList<String>();
			if(idarr != null && idarr.length > 0){
				int idlenght = idarr.length;
				for (int i = 0; i < idlenght; i++) {
					String iid = idarr[i];
					if (StringUtils.isNotEmpty(iid)) {
						idList.add(iid);
					}
				}
			}
			if(idList != null && idList.size() > 0){
				return itsDao.findByIdIn(idList);
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public ImproveTargetSetting getImproveTargetSetting(String id) {
		return itsDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public ImproveTargetSetting addImproveTargetSetting(ImproveTargetSetting entity) {
		return itsDao.save(entity);
	}

	@Transactional(readOnly = false)
	public ImproveTargetSetting save(ImproveTargetSetting entity) {
		entity.setTargetMonth(DateFormatUtils.format(entity.getTargetMonthly(), "yyyyMM"));
		entity.setTargetDay(DateFormatUtils.format(entity.getTargetMonthly(), "yyyyMMdd"));
		entity.setTargetYear(DateFormatUtils.format(entity.getTargetMonthly(), "yyyy"));
		

		return itsDao.save(entity);
	}

	@Transactional(readOnly = false)
	public ImproveTargetSetting saves(ImproveTargetSetting entity) {

		return itsDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void delImproveTargetSetting(String id) {
		itsDao.delete(id);
	}

	/**
	 * 获取所有的附件
	 */
	public List<ImproveTargetSetting> getImproveTargetSettingList() {
		return (List<ImproveTargetSetting>) itsDao.findAll();
	}

	

	public ImproveTargetSetting findByOne(String id) {
		return itsDao.findOne(id);
	}
	
	public Page<ImproveTargetSetting> getImproveTargetSettings(int pageno,int pagesize,final ImproveTargetSetting its) {
		
		Specification<ImproveTargetSetting> s = new Specification<ImproveTargetSetting>() {
			@Override
			public Predicate toPredicate(Root<ImproveTargetSetting> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Order> orderlist = new ArrayList<Order>();
				List<Predicate> predicates = Lists.newArrayList();
				
				if (StringUtils.isNotEmpty(its.getDepts()) ) {
					try {
						In<Integer> in = builder.in(root.<Integer>get("targetDeptId"));
						String[] dids = its.getDepts().split(",");
						for(String did : dids) {
							in.value(Integer.valueOf(did.trim()));
						}
						builder.or(in);
						predicates.add(in);
					} catch (Exception e){
						logger.error(e.getMessage());
						e.printStackTrace();
					}
				}
				if (StringUtils.isNotEmpty(its.getTargetMonth())) {
					Predicate pfrom = builder.like(root.get("targetMonth").as(String.class),"%"+its.getTargetMonth().replace("-", "")+"%");
					predicates.add(pfrom);
				}
				
				if (StringUtils.isNotEmpty(its.getTargetDeptName())) {
					Predicate pfrom = builder.like(root.get("targetDeptName").as(String.class),"%"+its.getTargetDeptName()+"%");
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
		
		return this.itsDao.findAll(s, pageable);//(s, pageable);
	
	}
	
	/**
	 * 
	 * @param its
	 * @return
	 */
	
	public List<ImproveTargetSetting> getImproveTargetSettings(final ImproveTargetSetting its) {
		
		Specification<ImproveTargetSetting> s = new Specification<ImproveTargetSetting>() {
			@Override
			public Predicate toPredicate(Root<ImproveTargetSetting> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Order> orderlist = new ArrayList<Order>();
				List<Predicate> predicates = Lists.newArrayList();
				
				if (StringUtils.isNotEmpty(its.getDepts()) ) {
					try {
						In<Integer> in = builder.in(root.<Integer>get("targetDeptId"));
						String[] dids = its.getDepts().split(",");
						for(String did : dids) {
							in.value(Integer.valueOf(did.trim()));
						}
						builder.or(in);
						predicates.add(in);
					} catch (Exception e){
						logger.error(e.getMessage());
						e.printStackTrace();
					}
				}
				if (StringUtils.isNotEmpty(its.getTargetMonth())) {
					Predicate pfrom = builder.like(root.get("targetMonth").as(String.class),its.getTargetMonth());
					predicates.add(pfrom);
				}
				
				if (StringUtils.isNotEmpty(its.getTargetYear())) {
					Predicate pfrom = builder.equal(root.get("targetYear").as(String.class),its.getTargetYear());
					predicates.add(pfrom);
				}
				orderlist.add(builder.desc(root.get("targetMonth")));
				
				Predicate p = builder.and(predicates.toArray(new Predicate[predicates.size()]));
				return p;
			}
			
		};
		return this.itsDao.findAll(s);//(s, pageable);
	
	}
	
	/**
	 * 获取部门的指标值
	 * @param its
	 * @return
	 */
	public List<Object[]> getTargetRptData(final ImproveTargetSetting its){
		
		String[] dids = its.getDepts().split(",");
		List<Integer> deptid = new ArrayList<Integer>();
		for(String did : dids) {
			deptid.add(Integer.valueOf(did.trim()));
		}
		List<Object[]> list = itsDao.getTargetRptData(its.getTargetYear(),deptid);
		
		return list;
	}

}
