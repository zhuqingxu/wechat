package com.fangxin365.wechat.web.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangxin365.core.commons.BaseController;
import com.fangxin365.wechat.entity.config.WechatConfig;
import com.fangxin365.wechat.service.config.WechatConfigService;
import com.google.common.collect.Maps;

@Controller
public class WechatConfigController extends BaseController {
	
	@Autowired
	private WechatConfigService wechatConfigService;

	/**
	 * 模块：微信接口配置 功能：跳转到维护页面
	 */
	@RequestMapping(value = "/wechat/config/manage", method = RequestMethod.GET)
	public String manageConfig() {
		return "wechat/manageConfig";
	}
	
	/**
	 * 模块：微信接口配置 功能：获取列表数据
	 */
	@RequestMapping(value = "/wechat/config/list", method = RequestMethod.POST)
	@ResponseBody
	public Object listConfig(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE_TWENTY) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model) {
		
		Map<String, Object> searchParams = Maps.newHashMap();
		Page<WechatConfig> page = wechatConfigService.getWechatConfig(searchParams, pageNumber, pageSize, sortType);
		
		Map<String, Object> jsonMap = Maps.newHashMap();
		jsonMap.put("total", page.getTotalElements());
		jsonMap.put("rows", page.getContent());

		JSONObject object = JSONObject.fromObject(jsonMap);
		
		return object;
	}
	
	/**
	 * 模块：微信接口配置 功能：保存
	 */
	@RequestMapping(value = "/wechat/config/save", method = RequestMethod.POST)
	@ResponseBody
	public String saveConfig(HttpServletRequest request) {
		String msg = wechatConfigService.saveWechatConfig(bindingParams(request));
		String result = "{success:true,msg:'保存成功'}";
		if (StringUtils.isNotBlank(msg)) {
			result = "{success:false,msg:'" + msg + "'}";
		}
		return result;
	}
	
	/**
	 * 模块：微信接口配置 功能：保存 >> 绑定表单参数
	 */
	private Map<String, String> bindingParams(HttpServletRequest request) {
		Map<String, String> saveParams = Maps.newHashMap();
		saveParams.put("option", request.getParameter("option")); // 操作标识（新增或更新）
		saveParams.put("id", request.getParameter("id")); // 主键
		saveParams.put("appId", request.getParameter("appId"));
		saveParams.put("appSecret", request.getParameter("appSecret"));
		return saveParams;
	}
	
	/**
	 * 模块：甄选标准 >> 分级指标维护 功能：删除
	 */
	@RequestMapping(value = "/wechat/config/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteConfig(@RequestParam("id") long id) {
		wechatConfigService.deleteWechatConfig(id);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "删除成功");

		return map;
	}
	
	/**
	 * 模块：甄选标准 >> 分级指标维护 功能：获取AccessToken
	 */
	@RequestMapping(value = "/wechat/config/getAccessToken", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAccessToken(@RequestParam("id") long id) {
		wechatConfigService.getAccessToken(id);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "操作成功");

		return map;
	}

}
