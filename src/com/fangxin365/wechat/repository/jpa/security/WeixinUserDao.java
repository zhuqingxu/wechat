package com.fangxin365.wechat.repository.jpa.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fangxin365.wechat.entity.security.WeixinUser;

public interface WeixinUserDao extends PagingAndSortingRepository<WeixinUser, Long>, JpaSpecificationExecutor<WeixinUser> {
	
	WeixinUser findByOpenid(String openid);
	
	@Query("select user from WeixinUser user where user.creator is null ")
	Page<WeixinUser> findWeixinUser(Pageable pageable);

}
