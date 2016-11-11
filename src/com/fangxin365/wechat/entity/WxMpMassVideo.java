package com.fangxin365.wechat.entity;

import java.io.Serializable;

import com.fangxin365.core.utils.json.WxMpGsonBuilder;

/**
 * 群发时用到的视频素材
 */
@SuppressWarnings("serial")
public class WxMpMassVideo implements Serializable {

	private String mediaId;
	private String title;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String toJson() {
		return WxMpGsonBuilder.INSTANCE.create().toJson(this);
	}
	
}
