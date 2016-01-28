package com.fangxin365.wechat.entity.security;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fangxin365.wechat.entity.IdEntity;
import com.fangxin365.wechat.entity.security.Role;
import com.google.common.collect.Lists;

/**
 * 菜单.
 */
@Entity
@Table(name = "ss_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menu extends IdEntity {

	private Long parentId; // 上级菜单

	private String title; // 标题

	private String tip; // 提示

	private Integer seq; // 排序

	private String image; // 图片

	private String forward; // 跳转路径

	private String target; // 跳转目标

	private Integer isleaf; // 是否末级

	private Integer status; // 状态

	private String descn; // 描述

	private List<Role> roleList = Lists.newArrayList(); // 有序的关联对象集合，一个角色可以查看多个菜单

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Integer isleaf) {
		this.isleaf = isleaf;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@ManyToMany
	@JoinTable(name = "ss_menu_role", joinColumns = { @JoinColumn(name = "menu_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id ASC")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
