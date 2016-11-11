package com.fangxin365.wechat;

public class FxConstants {
	
	/*
	 * 可用状态
	 */
	public static final int STATUS_VALID = 1; // 有效
	public static final int STATUS_INVALID = 0; // 无效
	
	/*
	 * 授权状态
	 */
	public static final String STATUS_AUTH = "1"; // 已授权
	public static final String STATUS_UNAUTH = "0"; // 未授权
	
	/*
	 * FORM表单操作
	 */
	public static final String OPER_CREATE = "create"; // 添加操作
	public static final String OPER_UPDATE = "update"; // 修改操作
	public static final String OPER_COPY = "copy"; // 复制操作
	public static final String OPER_CREATECHILD = "createChild"; // 添加下级操作

}
