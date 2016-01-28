package com.fangxin365.wechat.service;

import org.javasimon.aop.Monitored;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fangxin365.wechat.repository.mybatis.security.MenuMybatisDao;

@Component
@Transactional
@Monitored
public class MenuEffectiveService {
	
	@Autowired
	private MenuMybatisDao menuDao;

}
