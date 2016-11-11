package com.fangxin365.wechat.web.user;

import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangxin365.core.commons.BaseController;
import com.fangxin365.core.utils.DecodeUtil;
import com.fangxin365.wechat.entity.user.WechatTag;
import com.fangxin365.wechat.service.user.WechatTagService;
import com.google.common.collect.Maps;

@Controller
public class WechatTagController extends BaseController {
	
	@Autowired
	private WechatTagService wechatTagService;

	/**
	 * 模块：微信接口配置 功能：跳转到维护页面
	 */
	@RequestMapping(value = "/wechat/tag/manage", method = RequestMethod.GET)
	public String manageTag() {
		return "wechat/manageTag";
	}
	
	/**
	 * 模块：微信接口配置 功能：获取列表数据
	 */
	@RequestMapping(value = "/wechat/tag/list", method = RequestMethod.POST)
	@ResponseBody
	public Object listTag(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE_TWENTY) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "name", defaultValue = "") String name,
			Model model) {
		
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("LIKE_name", DecodeUtil.decodeParam(name));
		Page<WechatTag> page = wechatTagService.getWechatTag(searchParams, pageNumber, pageSize, sortType);
		
		Map<String, Object> jsonMap = Maps.newHashMap();
		jsonMap.put("total", page.getTotalElements());
		jsonMap.put("rows", page.getContent());

		JSONObject object = JSONObject.fromObject(jsonMap);
		
		return object;
	}
	
	/**
	 * 模块：甄选标准 >> 分级指标维护 功能：获取AccessToken
	 */
	@RequestMapping(value = "/wechat/tag/sync", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncTag() {
		wechatTagService.syncTag();
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "操作成功");

		return map;
	}

}
