package com.fangxin365.core;

import com.fangxin365.core.commons.ConfigurableConstants;

/**
 * 系统级静态常量. 可通过cbms.properties初始化,同时保持常量static & final的特征.
 * 
 * @see ConfigurableConstants
 */
public class SysConstants extends ConfigurableConstants {

	// 静态初始化读入cbms.properties中的设置
	static {
		init("conf/wxapi.properties");
	}

	/**
	 * 从wxapi.properties中读取constant.message_bundle_key的值，如果配置文件不存在或配置文件中不存在该值时，默认取值"messages"
	 */
	
	/*
	 * Page
	 */
	public final static String DEFAULT_PAGE_NO = getProperty("constant.default_page_no", String.valueOf(1));
	public final static String DEFAULT_PAGE_SIZE = getProperty("constant.default_page_size", String.valueOf(15));
	
	/*
	 * Date/Time Pattern
	 */
	public final static String DEFAULT_DATE_PATTERN = getProperty("constant.default_date_pattern", "yyyy-MM-dd");
	public final static String DEFAULT_TIME_PATTERN = getProperty("constant.default_date_pattern", "yyyy-MM-dd HH:mm:ss");
	
	/*
	 * Upload/Download
	 */
	public final static String UPLOAD_ROOT = getProperty("upload.root", "/uploadfiles/");
	public static final String DOWNLOAD_ROOT = getProperty("download.root", "/downloadfiles/");
	
	/*
	 * 图片服务器
	 */
	public final static String IMAGE_SERVER = getProperty("image.server", "");
	public final static String IMAGE_ROOT = getProperty("image.root", "");
	public final static String REPORT_ROOT = getProperty("report.root", "");
	
	public final static String FILE_SERVER = getProperty("file.server", "");
	public final static String REPORT_SERVER = getProperty("report.server", "");
	
}
