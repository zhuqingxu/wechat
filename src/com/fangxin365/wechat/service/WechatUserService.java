package com.fangxin365.wechat.service;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.javasimon.aop.Monitored;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fangxin365.core.utils.http.RequestExecutor;
import com.fangxin365.core.utils.http.SimpleGetRequestExecutor;
import com.fangxin365.wechat.entity.WxAccessToken;
import com.fangxin365.wechat.entity.result.WxError;
import com.fangxin365.wechat.entity.result.WxMpUserList;
import com.fangxin365.wechat.service.api.WxMpConfigStorage;

@Component
@Transactional
@Monitored
public class WechatUserService {

	protected final Logger log = LoggerFactory.getLogger(WechatUserService.class);

	/**
	 * 全局的是否正在刷新access token的锁
	 */
	protected final Object globalAccessTokenRefreshLock = new Object();

	protected CloseableHttpClient httpClient;

	protected HttpHost httpProxy;

	private int retrySleepMillis = 1000;

	private int maxRetryTimes = 5;

	protected WxMpConfigStorage wxMpConfigStorage;
	
	public String getAccessToken() throws WxErrorException {
		return getAccessToken(false);
	}
	
	public String getAccessToken(boolean forceRefresh) throws WxErrorException {
		if (forceRefresh) {
			wxMpConfigStorage.expireAccessToken();
		}
		if (wxMpConfigStorage.isAccessTokenExpired()) {
			synchronized (globalAccessTokenRefreshLock) {
				if (wxMpConfigStorage.isAccessTokenExpired()) {
					String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" + "&appid=" + wxMpConfigStorage.getAppId() + "&secret=" + wxMpConfigStorage.getSecret();
					// https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
					try {
						HttpGet httpGet = new HttpGet(url);
						if (httpProxy != null) {
							RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
							httpGet.setConfig(config);
						}
						CloseableHttpResponse response = getHttpclient().execute(httpGet);
						String resultContent = new BasicResponseHandler().handleResponse(response);
						WxError error = WxError.fromJson(resultContent);
						if (error.getErrorCode() != 0) {
							throw new WxErrorException(error);
						}
						WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
						wxMpConfigStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
					} catch (ClientProtocolException e) {
						throw new RuntimeException(e);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return wxMpConfigStorage.getAccessToken();
	}

	public WxMpUserList userList(String next_openid) throws WxErrorException {
		String url = "https://api.weixin.qq.com/cgi-bin/user/get";
		String responseContent = execute(new SimpleGetRequestExecutor(), url, next_openid == null ? null : "next_openid=" + next_openid);
		return WxMpUserList.fromJson(responseContent);
	}

	/**
	 * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
	 *
	 * @param executor
	 * @param uri
	 * @param data
	 * @return
	 * @throws WxErrorException
	 */
	public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
		int retryTimes = 0;
		do {
			try {
				return executeInternal(executor, uri, data);
			} catch (WxErrorException e) {
				WxError error = e.getError();
				/**
				 * -1 系统繁忙, 1000ms后重试
				 */
				if (error.getErrorCode() == -1) {
					int sleepMillis = retrySleepMillis * (1 << retryTimes);
					try {
						log.debug("微信系统繁忙，{}ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
						Thread.sleep(sleepMillis);
					} catch (InterruptedException e1) {
						throw new RuntimeException(e1);
					}
				} else {
					throw e;
				}
			}
		} while (++retryTimes < maxRetryTimes);

		throw new RuntimeException("微信服务端异常，超出重试次数");
	}

	protected synchronized <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
		if (uri.indexOf("access_token=") != -1) {
			throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
		}
		String accessToken = getAccessToken(false);

		String uriWithAccessToken = uri;
		uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token=" + accessToken : "&access_token=" + accessToken;

		try {
			return executor.execute(getHttpclient(), httpProxy, uriWithAccessToken, data);
		} catch (WxErrorException e) {
			WxError error = e.getError();
			/*
			 * 发生以下情况时尝试刷新access_token 40001
			 * 获取access_token时AppSecret错误，或者access_token无效 42001 access_token超时
			 */
			if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001) {
				// 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
				wxMpConfigStorage.expireAccessToken();
				return execute(executor, uri, data);
			}
			if (error.getErrorCode() != 0) {
				throw new WxErrorException(error);
			}
			return null;
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected CloseableHttpClient getHttpclient() {
		return httpClient;
	}

}
