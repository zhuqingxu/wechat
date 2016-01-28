package com.fangxin365.wechat.repository.jpa.security;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fangxin365.wechat.entity.security.WeixinGroup;

public interface WeixinGroupDao extends PagingAndSortingRepository<WeixinGroup, Long>, JpaSpecificationExecutor<WeixinGroup> {
	
	WeixinGroup findByGroupId(String groupId);

}
