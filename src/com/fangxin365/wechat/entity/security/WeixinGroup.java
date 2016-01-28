package com.fangxin365.wechat.entity.security;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fangxin365.wechat.entity.IdEntity;

/**
 * 微信分组.
 */
@Entity
@Table(name = "weixin_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) // 默认的缓存策略.
public class WeixinGroup extends IdEntity {

	private String groupId;

	private String groupName;

	private String count;

	private String creator;
	private String createTime;
	private String editor;
	private String editTime;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getEditTime() {
		return editTime;
	}

	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}

}
