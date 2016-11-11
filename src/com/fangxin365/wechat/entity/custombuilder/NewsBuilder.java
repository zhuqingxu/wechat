package com.fangxin365.wechat.entity.custombuilder;

import java.util.ArrayList;
import java.util.List;

import com.fangxin365.core.WxConsts;
import com.fangxin365.wechat.entity.WxMpCustomMessage;

/**
 * 图文消息builder
 * 
 * <pre>
 * 用法:
 * WxMpCustomMessage m = WxMpCustomMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

	private List<WxMpCustomMessage.WxArticle> articles = new ArrayList<WxMpCustomMessage.WxArticle>();

	public NewsBuilder() {
		this.msgType = WxConsts.CUSTOM_MSG_NEWS;
	}

	public NewsBuilder addArticle(WxMpCustomMessage.WxArticle article) {
		this.articles.add(article);
		return this;
	}

	public WxMpCustomMessage build() {
		WxMpCustomMessage m = super.build();
		m.setArticles(this.articles);
		return m;
	}
	
}
