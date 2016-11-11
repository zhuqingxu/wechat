package com.fangxin365.wechat.repository.jpa.config;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fangxin365.wechat.entity.config.WechatConfig;

public interface WechatConfigDao extends PagingAndSortingRepository<WechatConfig, Long>, JpaSpecificationExecutor<WechatConfig> {

	/**
	 * 根据唯一标识获取对象
	 */
	WechatConfig findByAppId(String appId);
	
	/**
	 * 根据主键和唯一标识获取对象
	 */
	WechatConfig findByIdNotAndAppId(long id, String appId);
	
}
