package com.fangxin365.wechat.service.security;

import org.javasimon.aop.Monitored;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fangxin365.wechat.entity.security.WeixinGroup;
import com.fangxin365.wechat.repository.jpa.security.WeixinGroupDao;

@Component
@Transactional
@Monitored
public class WeixinGroupService {
	
	private WeixinGroupDao weixinGroupDao;
	
	// -----------------//
	// Query methods
	// -----------------//
	
	/**
	 * Entity
	 */
	public WeixinGroup getWeixinGroup(Long id) {
		return weixinGroupDao.findOne(id);
	}

	/**
	 * Entity
	 */
	public WeixinGroup getWeixinGroup(String groupId) {
		return weixinGroupDao.findByGroupId(groupId);
	}

	// -----------------//
	// Save methods
	// -----------------//
	
	public void saveWeixinGroup(WeixinGroup weixinGroup) {
		weixinGroupDao.save(weixinGroup);
	}
	
	// -----------------//
	// Setter methods
	// -----------------//
	
	@Autowired
	public void setWeixinGroupDao(WeixinGroupDao weixinGroupDao) {
		this.weixinGroupDao = weixinGroupDao;
	}
}
