package com.fangxin365.wechat.wechat.entity.resp;

/**
 * 音乐消息
 */
public class Music {

	private String Title; // 音乐名称

	private String Description; // 音乐描述

	private String MusicUrl; // 音乐链接

	private String HQMusicUrl; // 高质量音乐链接，WIFI环境优先使用该链接播放音乐

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMusicUrl() {
		return MusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

}
