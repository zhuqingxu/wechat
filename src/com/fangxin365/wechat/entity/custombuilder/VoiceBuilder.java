package com.fangxin365.wechat.entity.custombuilder;

import com.fangxin365.core.WxConsts;
import com.fangxin365.wechat.entity.WxMpCustomMessage;

/**
 * 语音消息builder
 * 
 * <pre>
 * 用法: WxMpCustomMessage m = WxMpCustomMessage.VOICE().mediaId(...).toUser(...).build();
 * </pre>
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder> {
	
	private String mediaId;

	public VoiceBuilder() {
		this.msgType = WxConsts.CUSTOM_MSG_VOICE;
	}

	public VoiceBuilder mediaId(String media_id) {
		this.mediaId = media_id;
		return this;
	}

	public WxMpCustomMessage build() {
		WxMpCustomMessage m = super.build();
		m.setMediaId(this.mediaId);
		return m;
	}
	
}
