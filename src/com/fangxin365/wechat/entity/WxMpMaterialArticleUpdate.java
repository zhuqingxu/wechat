package com.fangxin365.wechat.entity;

import java.io.Serializable;

import com.fangxin365.core.utils.json.WxMpGsonBuilder;

@SuppressWarnings("serial")
public class WxMpMaterialArticleUpdate implements Serializable {

	private String mediaId;
	private int index;
	private WxMpMaterialNews.WxMpMaterialNewsArticle articles;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public WxMpMaterialNews.WxMpMaterialNewsArticle getArticles() {
		return articles;
	}

	public void setArticles(WxMpMaterialNews.WxMpMaterialNewsArticle articles) {
		this.articles = articles;
	}

	public String toJson() {
		return WxMpGsonBuilder.create().toJson(this);
	}

	@Override
	public String toString() {
		return "WxMpMaterialArticleUpdate [" + "mediaId=" + mediaId + ", index=" + index + ", articles=" + articles + "]";
	}
	
}
