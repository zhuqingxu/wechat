package com.fangxin365.wechat.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fangxin365.wechat.service.security.RoleService;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;

	/*@RequestMapping(value = "/account/role")
	public String list(Model model, ServletRequest request) {
		String name = request.getParameter("name");

		List<Role> roles = roleService.searchRole(name);
		model.addAttribute("roles", roles);
		return "account/roleList";
	}*/

}
