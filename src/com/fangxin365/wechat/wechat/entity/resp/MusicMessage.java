package com.fangxin365.wechat.wechat.entity.resp;

/**
 * 响应的音乐消息
 */
public class MusicMessage extends BaseMessage {

	private Music Music; // 音乐

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}

}
