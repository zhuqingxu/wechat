package com.fangxin365.wechat.repository.jpa.user;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fangxin365.wechat.entity.user.WechatTag;

public interface WechatTagDao extends PagingAndSortingRepository<WechatTag, Long>, JpaSpecificationExecutor<WechatTag> {
	
	WechatTag findByTagId(long tagId);
	
}
