package com.aqap.matrix.faurecia.service.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.audit.AuditNodeDao;
import com.aqap.matrix.faurecia.entity.audit.AuditNode;
import com.aqap.matrix.faurecia.service.manager.OperatelogService;
import com.aqap.matrix.faurecia.utils.CommonString;
import com.google.common.collect.Lists;

/**
 * 审批节点
 * @author lyh
 * @date 2017-6-27 22:22:46
 * 
 */
@Component
@Transactional(readOnly = true)
public class AuditNodeService {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(OperatelogService.class);
	
	@Autowired
	private AuditNodeDao auditNodeDao;
	
	@Transactional(readOnly = false)
	public String save(String module,String content){
		
		auditNodeDao.save(new AuditNode());
		return CommonString.FAIL;
	}
	
	@Transactional(readOnly = false)
	public AuditNode save(AuditNode node){
		
		auditNodeDao.save(node);
		
		return auditNodeDao.save(node);
	}
	
	public Page<AuditNode> list(int pageno,int pagesize){
		Sort sort = new Sort(Direction.DESC,"createtime");
		Pageable pageable = new PageRequest(pageno - 1, pagesize,sort);
		return auditNodeDao.findAll(pageable);
	}
	
	public Page<AuditNode> list(int pageno,int pagesize,Specification<AuditNode> spec){
		Sort sort = new Sort(Direction.DESC,"createtime");
		Pageable pageable = new PageRequest(pageno - 1, pagesize,sort);
		return auditNodeDao.findAll(spec, pageable);
	}
	
	/**
	 *根据节点ID查节点信息
	 * @param Specification spec
	 * @return
	 */
	public AuditNode findNodeBySpec(AuditNode node){
		
		return auditNodeDao.findOne(node.getId());
	}
	
	/**
	 * 根据审批 Form ID查找节点
	 * @param Specification spec
	 * @return
	 */
	public List<AuditNode> findNodeByFormId(final String formId){
		
		Specification<AuditNode> s = new Specification<AuditNode>() {
			@Override
			public Predicate toPredicate(Root<AuditNode> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Order> orderlist = new ArrayList<Order>();
				List<Predicate> predicates = Lists.newArrayList();
				
				if (StringUtils.isNotEmpty(formId)) {
					Predicate pfrom = builder.equal(root.get("fromId"), formId);
					predicates.add(pfrom);
				}
				
				orderlist.add(builder.desc(root.get("idxNumber")));
				orderlist.add(builder.desc(root.get("updateTime")));
				
				query.orderBy(orderlist);
				
				Predicate p = builder.and(predicates.toArray(new Predicate[predicates.size()]));
				
				return p;
			}
			
		};
		
		return auditNodeDao.findAll(s);
	}
	
	/**
	 * 
	 
	@Resource
	private EntityManagerFactory entityManagerFactory;
	public Integer getLatestNodeIdx(String formId){
		EntityManager em = entityManagerFactory.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class); //查询结果
	
		//在哪个表查
		Root<AuditNode> root = cq.from(AuditNode.class);
		em.equals((Path)root.get("fromId"), formId);
		cq.select(cb.greatest((Path)root.get("idxNumber")));
	
		//结果
		TypedQuery<Integer> typedQuery = em.createQuery(cq);
		return typedQuery.getSingleResult();

	}*/
	
	/**
	 * 获取审批表单中最新的节点
	 * @param formId
	 * @return
	 */
	public Integer getLatestNodeIdx(String formId){
		
		
		return auditNodeDao.getLatestNodeIdx(formId);
	}
}