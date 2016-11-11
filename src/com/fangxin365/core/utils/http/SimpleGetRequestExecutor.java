package com.fangxin365.core.utils.http;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fangxin365.wechat.entity.result.WxError;
import com.fangxin365.wechat.service.WxErrorException;

/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 */
public class SimpleGetRequestExecutor implements RequestExecutor<String, String> {

	@Override
	public String execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, String queryParam) throws WxErrorException, ClientProtocolException, IOException {
		if (queryParam != null) {
			if (uri.indexOf('?') == -1) {
				uri += '?';
			}
			uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
		}
		HttpGet httpGet = new HttpGet(uri);
		if (httpProxy != null) {
			RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
			httpGet.setConfig(config);
		}

		try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
			String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
			WxError error = WxError.fromJson(responseContent);
			if (error.getErrorCode() != 0) {
				throw new WxErrorException(error);
			}
			return responseContent;
		}
	}

}
