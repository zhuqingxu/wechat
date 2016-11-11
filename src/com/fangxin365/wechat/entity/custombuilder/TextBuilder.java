package com.fangxin365.wechat.entity.custombuilder;

import com.fangxin365.core.WxConsts;
import com.fangxin365.wechat.entity.WxMpCustomMessage;

/**
 * 文本消息builder
 * 
 * <pre>
 * 用法: WxMpCustomMessage m = WxMpCustomMessage.TEXT().content(...).toUser(...).build();
 * </pre>
 */
public final class TextBuilder extends BaseBuilder<TextBuilder> {
	
	private String content;

	public TextBuilder() {
		this.msgType = WxConsts.CUSTOM_MSG_TEXT;
	}

	public TextBuilder content(String content) {
		this.content = content;
		return this;
	}

	public WxMpCustomMessage build() {
		WxMpCustomMessage m = super.build();
		m.setContent(this.content);
		return m;
	}
	
}
