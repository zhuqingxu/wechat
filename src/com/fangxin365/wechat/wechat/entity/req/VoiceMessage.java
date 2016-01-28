package com.fangxin365.wechat.wechat.entity.req;

/**
 * 接收的语音消息
 */
public class VoiceMessage extends BaseMessage {

	private String MediaId; // 媒体ID

	private String Format; // 语音格式

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

}
