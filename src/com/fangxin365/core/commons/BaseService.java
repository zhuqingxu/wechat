package com.fangxin365.core.commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;

import com.fangxin365.wechat.service.ShiroDbRealm.ShiroUser;

public class BaseService {
	
	protected Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	public String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

}
