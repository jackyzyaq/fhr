package com.aqap.matrix.faurecia.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.account.RewardProductsDao;
import com.aqap.matrix.faurecia.entity.account.RewardProducts;
import com.aqap.matrix.faurecia.entity.account.RewardProducts;
import com.aqap.matrix.faurecia.entity.audit.AuditForm;
import com.google.common.collect.Lists;

/**
 * 附件管理
 * 
 * @author:Kirk Zhou
 * @date:2013-6-29下午04:40:58
 */
@Component
@Transactional(readOnly = true)
public class RewardProductsService {
	
	@Autowired
	private RewardProductsDao rpdao;

	@Transactional(readOnly = true)
	public RewardProducts getRewardProductsById(String id) {
		return rpdao.findOne(id);
	}

	@Transactional(readOnly = false)
	public RewardProducts save(RewardProducts entity) {
		
		return rpdao.save(entity);
	}

	/**
	 * 获取所有的附件
	 */
	public List<RewardProducts> getRewardProductsList() {
		Sort sort = new Sort(Sort.Direction.DESC, "update_time");
		return (List<RewardProducts>) rpdao.findAll(sort);
	}

	public Page<RewardProducts> getRewardProducts(int pageno,int pagesize,final RewardProducts rp) {
		
		Specification<RewardProducts> s = new Specification<RewardProducts>() {
			@Override
			public Predicate toPredicate(Root<RewardProducts> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Order> orderlist = new ArrayList<Order>();
				List<Predicate> predicates = Lists.newArrayList();
				
				
				if (StringUtils.isNotEmpty(rp.getProname())) {
					Predicate pfrom = builder.like(root.get("proname").as(String.class),"%"+rp.getProname()+"%");
					predicates.add(pfrom);
				}
				
				if (rp.getBPValues() != null ) {
					Predicate pfrom = builder.lessThanOrEqualTo(root.get("BPValues").as(Long.class),rp.getBPValues());
					predicates.add(pfrom);
				}
				
				if (rp.getProstock() != 0 ) {
					Predicate pfrom = builder.greaterThan(root.get("prostock").as(Integer.class),rp.getProstock());
					predicates.add(pfrom);
				}
				
				query.orderBy(orderlist);
				
				Predicate p = builder.and(predicates.toArray(new Predicate[predicates.size()]));
				return p;
			}
			
		};
		
		Pageable pageable = new PageRequest(pageno - 1, pagesize);
		
		return rpdao.findAll(s, pageable);//(s, pageable);
	
	}

	public RewardProducts findOne(String formId) {


		return rpdao.findOne(formId);
	}
}
