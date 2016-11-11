package com.fangxin365.wechat.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fangxin365.core.commons.BaseController;
import com.fangxin365.core.utils.PageInfo;
import com.fangxin365.wechat.entity.WxAccessToken;
import com.fangxin365.wechat.entity.security.WeixinUser;
import com.fangxin365.wechat.service.security.WeixinGroupService;
import com.fangxin365.wechat.service.security.WeixinUserService;
import com.google.gson.Gson;

@Controller
public class WeixinController extends BaseController {

	@Autowired
	private WeixinUserService weixinUserService;

	@Autowired
	private WeixinGroupService weixinGroupService;

	/**
	 * 获取access_token
	 */
	public String getAccessToken(String appId, String appSecret) throws Exception {
		HttpClient client = new HttpClient();
		String url = "https://api.weixin.qq.com/cgi-bin/token?appid=" + appId + "&secret=" + appSecret;
		logger.info("获取access_token url:" + url);

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
			String result = postMethod.getResponseBodyAsString();
			logger.info("获取access_token result:" + result);
			return result;
		} catch (IOException e) {
			throw e;
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 跳转到同步微信用户页面
	 */
	@RequestMapping(value = "/weixin/user", method = RequestMethod.GET)
	public String list() {
		return "weixin/synchrUser";
	}

	@RequestMapping(value = "/weixin/synchr/userlist", method = RequestMethod.POST)
	public String synchrUserlist(HttpServletRequest request, Model model) {

		String access_token = request.getParameter("access_token");
		String synchr_user_list_url = request
				.getParameter("synchr_user_list_url");
		String next_openid = request.getParameter("next_openid");

		String synchr_url = synchr_user_list_url + access_token;
		if (StringUtils.isNotBlank(next_openid)) {
			synchr_url = synchr_url + "&next_openid=" + next_openid;
		}

		logger.info("synchr_url=" + synchr_url);

		model.addAttribute("access_token", access_token);

		synchrUserlist(synchr_url, model);

		return "weixin/synchrUser";
	}

	public void synchrUserlist(String synchr_url, Model model) {
		try {
			URL url = new URL(synchr_url);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			connection.setConnectTimeout(3000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);

			int code = connection.getResponseCode();
			if (code == 200) {
				String jsonString = "";

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				int len = 0;
				byte[] data = new byte[1024];
				try {
					while ((len = connection.getInputStream().read(data)) != -1) {
						outputStream.write(data, 0, len);
					}
					jsonString = new String(outputStream.toByteArray(), "UTF-8");// 微信接口是UTF-8格式的。
				} catch (IOException e) {
					e.printStackTrace();
				}

				JSONObject jsonObject;

				try {
					jsonObject = new JSONObject(jsonString);
					String count = jsonObject.getString("count"); // 拉取的OPENID个数，最大值为10000
					String next_openid = jsonObject.getString("next_openid"); // 拉取列表的最后一个用户的OPENID
					String content = jsonObject.getString("data");

					model.addAttribute("count", count);
					model.addAttribute("next_openid", next_openid);

					JSONObject json = new JSONObject(content);
					JSONArray results = json.getJSONArray("openid");

					if (results.length() > 0) {
						WeixinUser weixinUser = null;
						logger.info("开始同步");
						for (int i = 0; i < results.length(); i++) {
							logger.info("序号：" + i);
							weixinUser = weixinUserService
									.getWeixinUser(results.getString(i));
							if (weixinUser == null) {
								weixinUser = new WeixinUser();
								weixinUser.setOpenid(results.getString(i));
							}
							weixinUserService.saveWeixinUser(weixinUser);
						}
						logger.info("结束同步");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/weixin/synchr/userInfo", method = RequestMethod.POST)
	public String synchrUserInfo(HttpServletRequest request, Model model) {

		String access_token = request.getParameter("access_token");
		String synchr_user_list_url = request
				.getParameter("synchr_user_list_url");

		model.addAttribute("access_token", access_token);

		// 用户列表
		PageInfo page = new PageInfo();
		page.setPageNo(1);
		page.setPageSize(10000);

		int currentPageNo = 1;
		logger.info("同步开始");
		Page<WeixinUser> users = null;
		do {
			users = weixinUserService.getWeixinUser(page);
			List<WeixinUser> userList = users.getContent();

			logger.info("正在处理第" + currentPageNo + "页，共" + users.getTotalPages()
					+ "页");

			for (WeixinUser user : userList) {
				if (StringUtils.isNotBlank(user.getOpenid())) {
					String synchr_url = synchr_user_list_url + access_token
							+ "&openid=" + user.getOpenid();
					synchrUserInfo(synchr_url);
				}
			}

			currentPageNo++;
			page.setPageNo(currentPageNo);
		} while (currentPageNo <= 1);

		logger.info("同步结束");

		return "weixin/synchrUser";
	}

	public void synchrUserInfo(String synchr_url) {
		try {
			URL url = new URL(synchr_url);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			connection.setConnectTimeout(3000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);

			int code = connection.getResponseCode();
			if (code == 200) {
				String jsonString = "";

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				int len = 0;
				byte[] data = new byte[1024];
				try {
					while ((len = connection.getInputStream().read(data)) != -1) {
						outputStream.write(data, 0, len);
					}
					jsonString = new String(outputStream.toByteArray(), "UTF-8");// 微信接口是UTF-8格式的。
				} catch (IOException e) {
					e.printStackTrace();
				}

				JSONObject jsonObject;

				try {
					jsonObject = new JSONObject(jsonString);

					String subscribe = jsonObject.getString("subscribe");
					if ("1".equals(subscribe)) {
						String openid = jsonObject.getString("openid");
						String nickname = jsonObject.getString("nickname");
						String sex = jsonObject.getString("sex");
						String city = jsonObject.getString("city");
						String country = jsonObject.getString("country");
						String province = jsonObject.getString("province");
						String language = jsonObject.getString("language");
						String headimgurl = jsonObject.getString("headimgurl");
						String subscribe_time = jsonObject
								.getString("subscribe_time");
						String remark = jsonObject.getString("remark");
						String groupid = jsonObject.getString("groupid");

						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Long time = new Long(
								Long.parseLong(subscribe_time) * 1000);
						String date = format.format(time);

						WeixinUser weixinUser = null;
						if (StringUtils.isNotBlank(openid)) {
							weixinUser = weixinUserService
									.getWeixinUser(openid);

							weixinUser.setSubscribe(subscribe);
							weixinUser.setNickname(filterEmoji(nickname));
							weixinUser.setSex(sex);
							weixinUser.setCity(city);
							weixinUser.setCountry(country);
							weixinUser.setProvince(province);
							weixinUser.setLanguage(language);
							weixinUser.setHeadimgurl(headimgurl);
							weixinUser.setSubscribeTime(date);
							weixinUser.setRemark(remark);
							weixinUser.setGroupid(groupid);
							weixinUser.setCreator("zhuqingxu");

							weixinUserService.saveWeixinUser(weixinUser);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将emoji表情替换成*
	 * 
	 * @param source
	 * @return 过滤后的字符串
	 */
	public static String filterEmoji(String source) {
		if (StringUtils.isNotBlank(source)) {
			return source.replaceAll(
					"[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
		} else {
			return source;
		}
	}

	/**
	 * 转码
	 * 
	 * @param ascii
	 * @return
	 */
	public static String ascii2native(String ascii) {
		int n = ascii.length() / 6;
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0, j = 2; i < n; i++, j += 6) {
			String code = ascii.substring(j, j + 4);
			char ch = (char) Integer.parseInt(code, 16);
			sb.append(ch);
		}
		return sb.toString();
	}

	@RequestMapping(value = "/weixin/synchr/group", method = RequestMethod.POST)
	public String synchrGroup(HttpServletRequest request, Model model) throws Exception {
		
		String appId = "wx21591686670283ec";
		String appSecret = "97dcebce81143555e5555081cfd388a0";
		String access_token = getAccessToken(appId, appSecret);
		
		//String access_token = "{\"access_token\":\"4r1PZgEn3kuIW17cTyhfy11THnoVzBP_Tf8IN0FUBPEbDBrhk6jQyRKP-jxUlwtyH7Bjq1sQ07esMJ7w5vBdFsgGC_Wbu2W81sNQa2yQ2-sJGWwgfCqDdwRK7WX2P8_9MSUdAGAVBD\",\"expires_in\":7200}";
		
		System.out.println("access_token=" + access_token);
		
		//WxAccessToken token = gson.fromJson(access_token, WxAccessToken.class);
		
		WxAccessToken token = WxAccessToken.fromJson(access_token);
		
		System.out.println("token=" + token);
		
		String accessToken = token.getAccessToken();
		
		System.out.println("accessToken=" + accessToken);

		//String access_token = request.getParameter("access_token");
		//String synchr_group_url = request.getParameter("synchr_group_url");
		//String synchr_group_url = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=";
		//String synchr_url = synchr_group_url + access_token;

		//logger.info("synchr_url=" + synchr_url);
		model.addAttribute("access_token", access_token);
		//synchrGroup(synchr_url, model);
		postGroup(null, accessToken, "get");

		return "weixin/synchrUser";
	}
	
	public void postGroup(String json, String token, String method) throws Exception{
		HttpClient client = new HttpClient();
		String url =null;
		url = "https://api.weixin.qq.com/cgi-bin/groups/"+method+"?access_token=";
		PostMethod postMethod = new PostMethod(url+token);

		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		// 构造键值对参数
		// NameValuePair[] data = { new NameValuePair("body", json),
		// new NameValuePair("access_token", token) };
		// 把参数值放入postMethod中
		if (!StringUtils.isEmpty(json)) {
			postMethod.setRequestBody(json);
		}
		
		// postMethod.setRequestBody(data);
		try {
			client.executeMethod(postMethod);
			String result = postMethod.getResponseBodyAsString();
			logger.info("result=" + result);
		} catch (IOException e) {
			throw e;
		} finally {
			postMethod.releaseConnection();
		}
	}

	/*public void synchrGroup(String synchr_url, Model model) {
		try {
			URL url = new URL(synchr_url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setConnectTimeout(3000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);

			int code = connection.getResponseCode();
			if (code == 200) {
				String jsonString = "";

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				int len = 0;
				byte[] data = new byte[1024];
				try {
					while ((len = connection.getInputStream().read(data)) != -1) {
						outputStream.write(data, 0, len);
					}
					jsonString = new String(outputStream.toByteArray(), "UTF-8");// 微信接口是UTF-8格式的。
				} catch (IOException e) {
					e.printStackTrace();
				}

				JSONObject jsonObject;

				try {
					logger.info("jsonString=" + jsonString);
					jsonObject = new JSONObject(jsonString);
					JSONArray ja = jsonObject.getJSONArray("groups");

					if (ja.length() > 0) {
						WeixinGroup weixinGroup = null;
						logger.info("开始同步");
						for (int i = 0; i < ja.length(); i++) {
							logger.info("序号：" + i);
							weixinGroup = weixinGroupService.getWeixinGroup(ja
									.getJSONObject(i).getString("id"));
							if (weixinGroup == null) {
								weixinGroup = new WeixinGroup();
								weixinGroup.setGroupId(ja.getJSONObject(i)
										.getString("id"));
								weixinGroup.setGroupName(ja.getJSONObject(i)
										.getString("name"));
								weixinGroup.setCount(ja.getJSONObject(i)
										.getString("count"));
								weixinGroup.setCreator("zhuqingxu");
							}
							weixinGroupService.saveWeixinGroup(weixinGroup);
						}
						logger.info("结束同步");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public static void main(String[] args) {
		
		String access_token = "{\"access_token\":\"4r1PZgEn3kuIW17cTyhfy11THnoVzBP_Tf8IN0FUBPEbDBrhk6jQyRKP-jxUlwtyH7Bjq1sQ07esMJ7w5vBdFsgGC_Wbu2W81sNQa2yQ2-sJGWwgfCqDdwRK7WX2P8_9MSUdAGAVBD\",\"expires_in\":7200}";
		
		System.out.println("access_token=" + access_token);
		
		Gson gson = new Gson();
		
		//WxAccessToken token = gson.fromJson(access_token, WxAccessToken.class);
		
		WxAccessToken token = WxAccessToken.fromJson(access_token);
		
		System.out.println("token=" + token);
		
		String accessToken = token.getAccessToken();
		
		System.out.println("accessToken=" + accessToken);
	}

}
