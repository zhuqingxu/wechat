package com.fangxin365.wechat.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fangxin365.wechat.entity.result.WxMpUserList;
import com.fangxin365.wechat.service.WechatUserService;
import com.fangxin365.wechat.service.WxErrorException;

/**
 * 微信接口 用户管理
 */
@Controller
public class WechatUserController {
	
	@Autowired
	private WechatUserService wechatUserService;
	
	/**
	 * 获取用户列表
	 */
	@RequestMapping(value = "/user/get", method = RequestMethod.GET)
	public void getUser(Model model) {
		String next_openid = "";
		try {
			WxMpUserList wxUserList = wechatUserService.userList(next_openid);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}
	

}
