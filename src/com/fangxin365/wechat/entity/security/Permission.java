package com.fangxin365.wechat.entity.security;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fangxin365.wechat.entity.IdEntity;

/**
 * 权限.
 */
@Entity
@Table(name = "ss_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Permission extends IdEntity {

	private String name; // 名称

	private String descn; // 描述
	
	private String authorize; // 是否授权

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}
	
	@Transient
	public String getAuthorize() {
		return authorize;
	}

	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}

}
