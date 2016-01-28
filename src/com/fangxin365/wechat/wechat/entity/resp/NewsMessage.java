package com.fangxin365.wechat.wechat.entity.resp;

import java.util.List;

/**
 * 响应的图文消息
 * 
 * 多图文消息 单图文的时候 Articles 只放一个
 */
public class NewsMessage extends BaseMessage {

	private int ArticleCount; // 图文消息个数，限制为10条以内

	private List<Article> Articles; // 多条图文消息信息，默认第一个item为大图

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

}
