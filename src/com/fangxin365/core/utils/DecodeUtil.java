package com.fangxin365.core.utils;

import org.apache.commons.lang.StringUtils;

public class DecodeUtil {

	/**
	 * 解密参数，处理中文乱码
	 */
	public static String decodeParam(String param) {
		try {
			if (StringUtils.isNotBlank(param)) {
				param = java.net.URLDecoder.decode(param.trim(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return param;
	}

}
