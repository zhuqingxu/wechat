package com.fangxin365.wechat.web.security;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangxin365.core.commons.BaseController;
import com.fangxin365.core.utils.DecodeUtil;
import com.fangxin365.wechat.entity.security.User;
import com.fangxin365.wechat.service.security.AccountService;
import com.google.common.collect.Maps;

@Controller
public class UserAdminController extends BaseController {

	@Autowired
	private AccountService accountService;

	/**
	 * 模块：后台 >> 权限管理 >> 用户管理 功能：跳转到用户管理页面
	 */
	@RequestMapping(value = "/admin/user/manage", method = RequestMethod.GET)
	public String manageUser(Model model) {
		return "admin/security/manageUser";
	}

	/**
	 * 模块：后台 >> 权限管理 >> 用户管理 功能：获取用户列表（需要查看用户权限）
	 */
	@RequiresPermissions("user:view")
	@RequestMapping(value = "/admin/user", method = RequestMethod.POST)
	@ResponseBody
	public Object listUser(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE_FIFTEEN) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "loginName", defaultValue = "") String loginName,
			@RequestParam(value = "status", defaultValue = "") String status,
			Model model, ServletRequest request) {
		
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("EQ_status", status);
		searchParams.put("LIKE_loginName", DecodeUtil.decodeParam(loginName));
		Page<User> page = accountService.getUser(searchParams, pageNumber, pageSize, sortType);

		JsonConfig config = JsonConfig.getInstance();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(new String[] { "roleList", "team" });

		Map<String, Object> jsonMap = Maps.newHashMap();
		jsonMap.put("total", page.getTotalElements());
		jsonMap.put("rows", page.getContent());

		JSONObject object = JSONObject.fromObject(jsonMap);

		return object;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 用户管理 功能：保存添加或修改的用户（需要添加用户或修改用户权限）
	 */
	@RequiresPermissions(value = { "user:add", "user:edit" }, logical = Logical.OR)
	@RequestMapping(value = "/admin/user/save", method = RequestMethod.POST)
	@ResponseBody
	public String saveUser(@Valid @ModelAttribute("user") User user) {
		accountService.saveUser(user);
		return "{success:true,msg:'操作成功'}";
	}

	/**
	 * 模块：后台 >> 权限管理 >> 用户管理 功能：删除用户（需要删除用户权限）
	 */
	@RequiresPermissions("user:delete")
	@RequestMapping(value = "/admin/user/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUser(@PathVariable("id") Long id) {
		// 删除用户角色关系
		User user = accountService.getUser(id);
		user.getRoleList().clear();
		// 删除用户
		accountService.deleteUser(id);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "操作成功");

		return map;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 用户管理 功能：选择角色（需要为用户分配角色权限）
	 */
	@RequiresPermissions("user:authRole")
	@RequestMapping(value = "/admin/user/selectRole/{id}", method = RequestMethod.GET)
	public String selectRole(@PathVariable("id") Long userId, Model model) {
		model.addAttribute("userId", userId);
		return "admin/security/selectRole";
	}

	/**
	 * 不自动绑定对象中的属性，另行处理。
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("roleList");
	}

}
