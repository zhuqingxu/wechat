package com.fangxin365.wechat.service.security;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;

import com.fangxin365.wechat.FxConstants;


/**
 * 授权管理类
 */
public class AuthHelper {

	/**
	 * 判断是否有权限
	 * 
	 * @param list 所有权限
	 * @param filterlist 已授权权限
	 * @param authorize 是否授权标识
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void judgeAuth(Collection list, Collection filterlist) throws Exception {

		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Object resource = iter.next();

			if (filterlist.contains(resource)) {
				BeanUtils.setProperty(resource, "authorize", FxConstants.STATUS_AUTH);
			} else {
				BeanUtils.setProperty(resource, "authorize", FxConstants.STATUS_UNAUTH);
			}
		}
	}
}
