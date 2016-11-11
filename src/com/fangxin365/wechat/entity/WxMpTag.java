package com.fangxin365.wechat.entity;

import java.io.Serializable;

import com.fangxin365.core.utils.json.WxMpGsonBuilder;

/**
 * 微信用户标签
 */
@SuppressWarnings("serial")
public class WxMpTag implements Serializable {
	
	private long id = -1;
	private String name;
	private long count;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public static WxMpTag fromJson(String json) {
		return WxMpGsonBuilder.create().fromJson(json, WxMpTag.class);
	}

	public String toJson() {
		return WxMpGsonBuilder.create().toJson(this);
	}

	@Override
	public String toString() {
		return "WxMpTag [id=" + id + ", name=" + name + ", count=" + count + "]";
	}

}
