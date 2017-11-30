package com.aqap.matrix.faurecia.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.account.BonusPointsDao;
import com.aqap.matrix.faurecia.entity.account.BonusPoints;
import com.aqap.matrix.faurecia.entity.account.BonusPoints;
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
public class BonusPointsService {
	
	@Autowired
	private BonusPointsDao bpd;

	@Transactional(readOnly = true)
	public BonusPoints getBonusPointsById(String id) {
		return bpd.findOne(id);
	}

	@Transactional(readOnly = false)
	public BonusPoints save(BonusPoints entity) {
		
		return bpd.save(entity);
	}

	/**
	 * 获取所有的附件
	 */
	public List<BonusPoints> getBonusPointsList() {
		Sort sort = new Sort(Sort.Direction.DESC, "update_time");
		return (List<BonusPoints>) bpd.findAll(sort);
	}

	public Page<BonusPoints> getBonusPointss(int pageno,int pagesize,final BonusPoints its) {
		
		Specification<BonusPoints> s = new Specification<BonusPoints>() {
			@Override
			public Predicate toPredicate(Root<BonusPoints> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Order> orderlist = new ArrayList<Order>();
				List<Predicate> predicates = Lists.newArrayList();
				
				
				if (StringUtils.isNotEmpty(its.getProductName())) {
					Predicate pfrom = builder.like(root.get("productName").as(String.class),"%"+its.getProductName()+"%");
					predicates.add(pfrom);
				}
				
				if (StringUtils.isNotEmpty(its.getEMPName())) {
					Predicate pfrom = builder.like(root.get("EMPName").as(String.class),"%"+its.getEMPName()+"%");
					predicates.add(pfrom);
				}
				
				if (StringUtils.isNotEmpty(its.getEMPId())) {
					Predicate pfrom = builder.equal(root.get("EMPId").as(String.class),its.getEMPId());
					predicates.add(pfrom);
				}
				
				if (its.getBPValues() != null ) {
					Predicate pfrom = builder.lessThanOrEqualTo(root.get("BPValues").as(Long.class),its.getBPValues());
					predicates.add(pfrom);
				}
				
				if (its.getStatus() != 0 ) {
					Predicate pfrom = builder.equal(root.get("status").as(Integer.class),its.getStatus());
					predicates.add(pfrom);
				}
				
				if (its.getType() != 0 ) {
					Predicate pfrom = builder.equal(root.get("type").as(Integer.class),its.getType());
					predicates.add(pfrom);
				}
				
				//orderlist.add(builder.desc(root.get("update_time")));
				query.orderBy(orderlist);
				
				Predicate p = builder.and(predicates.toArray(new Predicate[predicates.size()]));
				return p;
			}
			
		};
		
		Pageable pageable = new PageRequest(pageno - 1, pagesize);
		
		return bpd.findAll(s, pageable);//(s, pageable);
	
	}
	
	public Long countBPByEMPId(String empid){
		BonusPoints its = new BonusPoints();
		its.setEMPId(empid);
		Long bp = 0l;
		Page<BonusPoints> p = this.getBonusPointss(1, 10000, its);
		List<BonusPoints> l = p.getContent();
		if(null != l) {
			for(int i = 0;i<l.size();i++){
				its = l.get(i);
				bp = bp + its.getBPValues();
			}
		}
		return bp;
	}

	public BonusPoints findOne(String id) {
		
		return bpd.findOne(id);
	}
}
