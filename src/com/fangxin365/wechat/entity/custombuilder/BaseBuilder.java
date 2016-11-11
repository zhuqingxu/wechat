package com.fangxin365.wechat.entity.custombuilder;

import com.fangxin365.wechat.entity.WxMpCustomMessage;

public class BaseBuilder<T> {

	protected String msgType;
	protected String toUser;

	@SuppressWarnings("unchecked")
	public T toUser(String toUser) {
		this.toUser = toUser;
		return (T) this;
	}

	public WxMpCustomMessage build() {
		WxMpCustomMessage m = new WxMpCustomMessage();
		m.setMsgType(this.msgType);
		m.setToUser(this.toUser);
		return m;
	}

}
