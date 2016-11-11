package com.fangxin365.wechat.entity.custombuilder;

import com.fangxin365.core.WxConsts;
import com.fangxin365.wechat.entity.WxMpCustomMessage;

/**
 * 视频消息builder
 * 
 * <pre>
 * 用法: WxMpCustomMessage m = WxMpCustomMessage.VOICE()
 *                              .mediaId(...)
 *                              .title(...)
 *                              .thumbMediaId(..)
 *                              .description(..)
 *                              .toUser(...)
 *                              .build();
 * </pre>
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder> {
	
	private String mediaId;
	private String title;
	private String description;
	private String thumbMediaId;

	public VideoBuilder() {
		this.msgType = WxConsts.CUSTOM_MSG_VIDEO;
	}

	public VideoBuilder mediaId(String mediaId) {
		this.mediaId = mediaId;
		return this;
	}

	public VideoBuilder title(String title) {
		this.title = title;
		return this;
	}

	public VideoBuilder description(String description) {
		this.description = description;
		return this;
	}

	public VideoBuilder thumbMediaId(String thumb_media_id) {
		this.thumbMediaId = thumb_media_id;
		return this;
	}

	public WxMpCustomMessage build() {
		WxMpCustomMessage m = super.build();
		m.setMediaId(this.mediaId);
		m.setTitle(title);
		m.setDescription(description);
		m.setThumbMediaId(thumbMediaId);
		return m;
	}
	
}
