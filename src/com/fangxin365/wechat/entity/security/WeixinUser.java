package com.fangxin365.wechat.entity.security;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fangxin365.wechat.entity.IdEntity;

/**
 * 微信用户.
 */
@Entity
@Table(name = "weixin_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) // 默认的缓存策略.
public class WeixinUser extends IdEntity {

	private String subscribe; // 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	private String openid; // 用户的标识，对当前公众号唯一 
	private String nickname; // 用户的昵称
	private String sex; // 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 
	private String city; // 用户所在城市
	private String country; // 用户所在国家
	private String province; // 用户所在省份
	private String language; // 用户的语言，简体中文为zh_CN
	private String headimgurl; // 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。 
	private String subscribeTime; // 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 
	private String unionid; // 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	private String remark; // 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	private String groupid; // 用户所在的分组ID
	private String creator;
	private String createTime;
	private String editor;
	private String editTime;
	
	private String extInfo1;
	private String extInfo2;
	private String extInfo3;
	private String extInfo4;
	private String extInfo5;

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
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

	public String getExtInfo1() {
		return extInfo1;
	}

	public void setExtInfo1(String extInfo1) {
		this.extInfo1 = extInfo1;
	}

	public String getExtInfo2() {
		return extInfo2;
	}

	public void setExtInfo2(String extInfo2) {
		this.extInfo2 = extInfo2;
	}

	public String getExtInfo3() {
		return extInfo3;
	}

	public void setExtInfo3(String extInfo3) {
		this.extInfo3 = extInfo3;
	}

	public String getExtInfo4() {
		return extInfo4;
	}

	public void setExtInfo4(String extInfo4) {
		this.extInfo4 = extInfo4;
	}

	public String getExtInfo5() {
		return extInfo5;
	}

	public void setExtInfo5(String extInfo5) {
		this.extInfo5 = extInfo5;
	}

}
