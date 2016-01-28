package com.fangxin365.wechat.webservice.rest;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fangxin365.core.SysConstants;
import com.google.common.collect.Lists;

@RestController
public class IndexRestController {
	
	/**
	 * 首页 - 获取顶部展示图片
	 */
	@RequestMapping(value = "/api/index/banner", method = RequestMethod.GET)
	public String banner() {
		List<String> list = Lists.newArrayList();
		list.add(SysConstants.FILE_SERVER + "app/images/G01_home/banner.png");
		JSONArray array = JSONArray.fromObject(list);
		return array.toString();
	}
	
	/**
	 * 合作伙伴页 - 获取顶部展示图片
	 */
	@RequestMapping(value = "/api/hzhb/banner", method = RequestMethod.GET)
	public String banner_hzhb() {
		List<String> list = Lists.newArrayList();
		list.add(SysConstants.FILE_SERVER + "app/images/G10_cooperative_partner/banner.png");
		JSONArray array = JSONArray.fromObject(list);
		return array.toString();
	}

}
