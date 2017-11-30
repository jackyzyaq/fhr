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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.account.RewardSettingDao;
import com.aqap.matrix.faurecia.entity.account.RewardSetting;
import com.google.common.collect.Lists;

/**
 * 附件管理
 * 
 * @author:Kirk Zhou
 * @date:2013-6-29下午04:40:58
 */
@Component
@Transactional(readOnly = true)
public class RewardSettingService {
	
	@Autowired
	private RewardSettingDao rsdao;
	
	/*@Transactional(readOnly = true)
	public Page<?> getRewardSettingByEntity(RewardSetting rs) {
		
		Pageable pageable = new PageRequest(1, 1000);
		return rsdao.findByTypeidAndRewordvalue(rs.getType_id(), rs.getReword_value(), pageable);
	}*/

	@Transactional(readOnly = true)
	public RewardSetting getRewardSettingById(String id) {
		return rsdao.findOne(id);
	}

	@Transactional(readOnly = false)
	public RewardSetting save(RewardSetting entity) {
		
		return rsdao.save(entity);
	}

	/**
	 * 获取所有的附件
	 */
	public List<RewardSetting> getRewardSettingList() {
		Sort sort = new Sort(Sort.Direction.DESC, "update_time");
		return (List<RewardSetting>) rsdao.findAll(sort);
	}

	public Page<RewardSetting> getRewardSettings(int pageno,int pagesize,final RewardSetting its) {
		
		Specification<RewardSetting> s = new Specification<RewardSetting>() {
			@Override
			public Predicate toPredicate(Root<RewardSetting> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Order> orderlist = new ArrayList<Order>();
				List<Predicate> predicates = Lists.newArrayList();
				
				
				if (StringUtils.isNotEmpty(its.getReword_value())) {
					Predicate pfrom = builder.equal(root.get("reword_value").as(String.class),its.getReword_value());
					predicates.add(pfrom);
				}
				
				if (its.getType_id() != 0) {
					Predicate pfrom = builder.equal(root.get("type_id").as(String.class),its.getType_id());
					predicates.add(pfrom);
				}
				
				
				//query.orderBy(orderlist);
				//query.where(in);
				
				Predicate p = builder.and(predicates.toArray(new Predicate[predicates.size()]));
				return p;
			}
			
		};
		Sort sort = new Sort(Direction.DESC, "updateTime");
		Pageable pageable = new PageRequest(pageno - 1, pagesize,sort);
		
		return rsdao.findAll(s, pageable);//(s, pageable);
	
	}
}
