package com.aqap.matrix.faurecia.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aqap.matrix.faurecia.dao.AttachmentDao;
import com.aqap.matrix.faurecia.entity.Attachment;

/**
 * 附件管理
 * 
 * @author:Kirk Zhou
 * @date:2013-6-29下午04:40:58
 */
@Component
@Transactional(readOnly = true)
public class AttachmentService {
	@Autowired
	private AttachmentDao attachmentDao;

	public List<Attachment> getAttachmentByIds(String ids) {
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
				return attachmentDao.findByIdIn(idList);
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public Attachment getAttachment(String id) {
		return attachmentDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public Attachment addAttachment(Attachment entity) {
		return attachmentDao.save(entity);
	}

	@Transactional(readOnly = false)
	public Attachment save(Attachment entity) {
		Attachment attachment = attachmentDao.findOne(entity.getId());
		// 备注
		attachment.setDescn(entity.getDescn());
		// 名称
		attachment.setOriginalName(entity.getOriginalName());
		attachmentDao.save(attachment);

		return attachmentDao.save(attachment);
	}

	@Transactional(readOnly = false)
	public Attachment saves(Attachment entity) {

		return attachmentDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void delAttachment(String id) {
		attachmentDao.delete(id);
	}

	/**
	 * 获取所有的附件
	 */
	public List<Attachment> getAttachmentList() {
		return (List<Attachment>) attachmentDao.findAll();
	}

	

	public Attachment findByOne(String id) {
		return attachmentDao.findOne(id);
	}

}
