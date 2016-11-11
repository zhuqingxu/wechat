package com.fangxin365.wechat.entity.config;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fangxin365.wechat.entity.IdEntity;

/**
 * 微信公众账号配置.
 */
@Entity
@Table(name = "wechat_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WechatConfig extends IdEntity {

	private String appId; // appId用于获取access_token；唯一标识

	private String appSecret; // appSecret用于获取access_token

	private String accessToken;

	private String token;

	private String expiresTime;

	public int isExpired; // 是否过期（1：过期；0：未过期）
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(String expiresTime) {
		this.expiresTime = expiresTime;
	}

	public int getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(int isExpired) {
		this.isExpired = isExpired;
	}

}
