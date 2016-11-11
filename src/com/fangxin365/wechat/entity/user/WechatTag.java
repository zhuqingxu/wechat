package com.fangxin365.wechat.entity.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fangxin365.wechat.entity.IdEntity;

/**
 * 用户标签.
 */
@Entity
@Table(name = "wechat_tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WechatTag extends IdEntity {

	private long tagId; // 标签id，由微信分配

	private String tagName; // 标签名，UTF8编码

	private long count; // 此标签下粉丝数

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
