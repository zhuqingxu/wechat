package com.fangxin365.wechat.service.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.javasimon.aop.Monitored;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.fangxin365.core.commons.BaseService;
import com.fangxin365.wechat.FxConstants;
import com.fangxin365.wechat.entity.WxAccessToken;
import com.fangxin365.wechat.entity.config.WechatConfig;
import com.fangxin365.wechat.repository.jpa.config.WechatConfigDao;

/**
 * 微信公众账号配置业务类.
 */
@Component
@Transactional
@Monitored
public class WechatConfigService extends BaseService {

	private WechatConfigDao wechatConfigDao;
	
	/**
	 * 按页面传来的查询条件查询.（分页）
	 */
	public Page<WechatConfig> getWechatConfig(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<WechatConfig> spec = buildSpecification(searchParams);
		return wechatConfigDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<WechatConfig> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<WechatConfig> spec = DynamicSpecifications.bySearchFilter(filters.values(), WechatConfig.class);
		return spec;
	}
	
	/**
	 * 保存
	 */
	public String saveWechatConfig(Map<String, String> bindingMap) {
		StringBuffer msg = new StringBuffer();
		
		String option = bindingMap.get("option");
		String id = bindingMap.get("id");
		String appId = bindingMap.get("appId");
		boolean isExist = isExist(option, id, StringUtils.trim(appId));
		if (isExist) {
			msg.append("appId不能重复！");
		} else {
			if(FxConstants.OPER_CREATE.equals(option)) {
				addWechatConfig(bindingMap);
			} else if(FxConstants.OPER_UPDATE.equals(option)) {
				editWechatConfig(bindingMap);
			}
		}
		
		return msg.toString();
	}
	
	/**
	 * 判断是否已存在
	 */
	public boolean isExist(String option, String id, String appId) {
		boolean isExist = false;
		WechatConfig wechatConfig = null;
		if (FxConstants.OPER_CREATE.equals(option)) {
			wechatConfig = wechatConfigDao.findByAppId(appId);
		} else if (FxConstants.OPER_UPDATE.equals(option)) {
			wechatConfig = wechatConfigDao.findByIdNotAndAppId(Long.parseLong(id), appId);
		}
		
		if(wechatConfig != null) {
			isExist = true;
		}
		return isExist;
	}
	
	/**
	 * 添加
	 */
	public void addWechatConfig(Map<String, String> bindingMap) {
		try {
			WechatConfig wechatConfig = new WechatConfig();
			bindingParam(wechatConfig, bindingMap);
			wechatConfigDao.save(wechatConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改
	 */
	public void editWechatConfig(Map<String, String> bindingMap) {
		try {
			WechatConfig wechatConfig = wechatConfigDao.findOne(Long.parseLong(bindingMap.get("id")));
			bindingParam(wechatConfig, bindingMap);
			wechatConfigDao.save(wechatConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 绑定表单参数
	 */
	private void bindingParam(WechatConfig wechatConfig, Map<String, String> bindingMap) {
		wechatConfig.setAppId(StringUtils.trim(bindingMap.get("appId")));
		wechatConfig.setAppSecret(StringUtils.trim(bindingMap.get("appSecret")));
	}
	
	/**
	 * 删除
	 */
	public void deleteWechatConfig(Long id) {
		wechatConfigDao.delete(id);
	}
	
	/**
	 * 获取AccessToken
	 */
	public void getAccessToken(Long id) {
		WechatConfig wechatConfig = wechatConfigDao.findOne(id);
		
		String appId = wechatConfig.getAppId();
		String appSecret = wechatConfig.getAppSecret();
		try {
			String access_token = getAccessToken(appId, appSecret);
			
			WxAccessToken token = WxAccessToken.fromJson(access_token);
			String accessToken = token.getAccessToken();
			long expiresInSeconds = token.getExpiresIn();
			
			long expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Long time = new Long(expiresTime);
			String date = format.format(time);
			
			wechatConfig.setAccessToken(access_token);
			wechatConfig.setToken(accessToken);
			wechatConfig.setExpiresTime(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		wechatConfigDao.save(wechatConfig);
	}
	
	/**
	 * 获取access_token
	 */
	public String getAccessToken(String appId, String appSecret) throws Exception {
		HttpClient client = new HttpClient();
		String url = "https://api.weixin.qq.com/cgi-bin/token?appid=" + appId + "&secret=" + appSecret;

		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		// 构造键值对参数
		NameValuePair[] data = {
				new NameValuePair("grant_type", "client_credential"),
				new NameValuePair("appid", appId),
				new NameValuePair("secret", appSecret) };
		// 把参数值放入postMethod中
		postMethod.setRequestBody(data);

		try {
			client.executeMethod(postMethod);
			return postMethod.getResponseBodyAsString();
		} catch (IOException e) {
			throw e;
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	// -----------------//
	// Setter methods   //
	// -----------------//

	@Autowired
	public void setWechatConfigDao(WechatConfigDao wechatConfigDao) {
		this.wechatConfigDao = wechatConfigDao;
	}

}
