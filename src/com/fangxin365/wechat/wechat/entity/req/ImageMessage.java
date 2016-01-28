package com.fangxin365.wechat.wechat.entity.req;

/**
 * 接收的图片消息
 */
public class ImageMessage extends BaseMessage {

	private String picUrl;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
}
