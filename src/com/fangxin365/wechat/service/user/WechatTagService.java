package com.fangxin365.wechat.service.user;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
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
import com.fangxin365.core.utils.json.WxMpGsonBuilder;
import com.fangxin365.wechat.entity.WxMpTag;
import com.fangxin365.wechat.entity.config.WechatConfig;
import com.fangxin365.wechat.entity.user.WechatTag;
import com.fangxin365.wechat.repository.jpa.config.WechatConfigDao;
import com.fangxin365.wechat.repository.jpa.user.WechatTagDao;
import com.google.gson.JsonElement;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

@Component
@Transactional
@Monitored
public class WechatTagService extends BaseService {

	private WechatTagDao wechatTagDao;

	private WechatConfigDao wechatConfigDao;

	/**
	 * 按页面传来的查询条件查询.（分页）
	 */
	public Page<WechatTag> getWechatTag(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<WechatTag> spec = buildSpecification(searchParams);
		return wechatTagDao.findAll(spec, pageRequest);
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
	private Specification<WechatTag> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<WechatTag> spec = DynamicSpecifications.bySearchFilter(filters.values(), WechatTag.class);
		return spec;
	}

	public void syncTag() {
		WechatConfig config = wechatConfigDao.findByAppId("wx21591686670283ec");

		try {
			String responseContent = postTag(null, config.getToken(), "get");
			//logger.info("responseContent=" + responseContent);

			JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));

			List<WxMpTag> list = WxMpGsonBuilder.INSTANCE.create().fromJson(
					tmpJsonElement.getAsJsonObject().get("tags"), new TypeToken<List<WxMpTag>>() {}.getType());

			//logger.info("size=" + list != null ? list.size() : 0);
			
			if(list != null && list.size() > 0) {
				for (WxMpTag tag : list) {
					WechatTag wechatTag = wechatTagDao.findByTagId(tag.getId());
					if(wechatTag == null) {
						wechatTag = new WechatTag();
						wechatTag.setTagId(tag.getId());
					}
					wechatTag.setTagName(tag.getName());
					wechatTag.setCount(tag.getCount());
					wechatTagDao.save(wechatTag);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册分组
	 * 
	 * @param json
	 * @param token
	 * @param create添加，update修改，get查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public String postTag(String json, String token, String method) throws Exception {
		HttpClient client = new HttpClient();
		String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=";
		PostMethod postMethod = new PostMethod(url + token);

		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		// 把参数值放入postMethod中
		if (!StringUtils.isEmpty(json)) {
			postMethod.setRequestBody(json);
		}

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
	// Setter methods //
	// -----------------//

	@Autowired
	public void setWechatTagDao(WechatTagDao wechatTagDao) {
		this.wechatTagDao = wechatTagDao;
	}

	@Autowired
	public void setWechatConfigDao(WechatConfigDao wechatConfigDao) {
		this.wechatConfigDao = wechatConfigDao;
	}

}
