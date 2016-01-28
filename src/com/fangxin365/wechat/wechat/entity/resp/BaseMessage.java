package com.fangxin365.wechat.wechat.entity.resp;

/**
 * 响应消息的基类(公众帐号 >>>>> 用户) 公众帐号发送给用户
 */
public class BaseMessage {

	private String ToUserName; // 接收方帐号（收到的OpenID）

	private String FromUserName; // 开发者微信号

	private long CreateTime; // 消息创建时间（整型）

	private String MsgType; // 消息类型

	private int FuncFlag; // 位0x0001被标志时，星标刚收到的消息

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public int getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}

}
