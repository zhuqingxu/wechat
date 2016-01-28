package com.fangxin365.wechat.web.security;

import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.util.WebUtils;

import com.fangxin365.core.commons.BaseController;
import com.fangxin365.core.utils.DecodeUtil;
import com.fangxin365.wechat.entity.security.Role;
import com.fangxin365.wechat.entity.security.User;
import com.fangxin365.wechat.service.security.AccountService;
import com.fangxin365.wechat.service.security.AuthHelper;
import com.fangxin365.wechat.service.security.RoleService;
import com.google.common.collect.Maps;

@Controller
public class RoleAdminController extends BaseController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private AccountService accountService;

	/**
	 * 模块：后台 >> 权限管理 >> 角色管理 功能：跳转到角色管理页面
	 */
	@RequestMapping(value = "/admin/role/manage", method = RequestMethod.GET)
	public String manageRole(Model model) {
		return "admin/security/manageRole";
	}

	/**
	 * 模块：后台 >> 权限管理 >> 角色管理 功能：获取角色列表（需要查看角色权限）
	 */
	@RequiresPermissions("role:view")
	@RequestMapping(value = "/admin/role", method = RequestMethod.POST)
	@ResponseBody
	public Object listRole(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE_FIFTEEN) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "name", defaultValue = "") String name,
			Model model, ServletRequest request) {

		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("LIKE_name", DecodeUtil.decodeParam(name));
		Page<Role> page = roleService.getRole(searchParams, pageNumber, pageSize, sortType);

		JsonConfig config = JsonConfig.getInstance();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(new String[] { "permissionList", "menuList" });

		Map<String, Object> jsonMap = Maps.newHashMap();
		jsonMap.put("total", page.getTotalElements());
		jsonMap.put("rows", page.getContent());

		JSONObject object = JSONObject.fromObject(jsonMap);

		return object;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 角色管理 功能：保存添加或修改的角色（需要添加角色或修改角色权限）
	 */
	@RequiresPermissions(value = { "role:add", "role:edit" }, logical = Logical.OR)
	@RequestMapping(value = "/admin/role/save", method = RequestMethod.POST)
	@ResponseBody
	public String saveRole(@Valid @ModelAttribute("role") Role role) {
		roleService.saveRole(role);
		return "{success:true,msg:'操作成功'}";
	}

	/**
	 * 模块：后台 >> 权限管理 >> 角色管理 功能：删除角色（需要删除角色权限）
	 */
	@RequiresPermissions("role:delete")
	@RequestMapping(value = "/admin/role/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRole(@PathVariable("id") Long id) {
		// 删除角色权限关系
		Role role = roleService.getRole(id);
		role.getPermissionList().clear();
		// 删除角色
		roleService.deleteRole(id);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "操作成功");

		return map;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 角色管理 功能：角色授权列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/admin/role/authorize", method = RequestMethod.POST)
	@ResponseBody
	public Object authorize(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE_FIFTEEN) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "userId", defaultValue = "") String userId,
			Model model, HttpServletRequest request) throws Exception {

		if (StringUtils.isNotBlank(userId)) {
			WebUtils.setSessionAttribute(request, "userId", userId);
		}

		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("LIKE_name", DecodeUtil.decodeParam(name));
		Page<Role> page = roleService.getRole(searchParams, pageNumber, pageSize, sortType);

		JsonConfig config = JsonConfig.getInstance();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(new String[] { "permissionList", "menuList" });

		User user = accountService.getUser(Long.parseLong(userId));

		Collection roles = page.getContent();
		AuthHelper.judgeAuth(roles, user.getRoleList());

		Map<String, Object> jsonMap = Maps.newHashMap();
		jsonMap.put("total", page.getTotalElements());
		jsonMap.put("rows", page.getContent());

		JSONObject object = JSONObject.fromObject(jsonMap);

		return object;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 角色管理 功能：授权用户角色
	 */
	@RequestMapping(value = "/admin/role/authRole/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> authRole(@PathVariable("id") Long id,
			HttpServletRequest request) {

		String userId = (String) WebUtils.getRequiredSessionAttribute(request, "userId");
		User user = accountService.getUser(Long.parseLong(userId));
		Role role = roleService.getRole(id);
		user.getRoleList().add(role);
		accountService.saveUser(user);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "操作成功");

		return map;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 角色管理 功能：撤销授权用户角色
	 */
	@RequestMapping(value = "/admin/role/unauthRole/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unauthRole(@PathVariable("id") Long id,
			HttpServletRequest request) {

		String userId = (String) WebUtils.getRequiredSessionAttribute(request, "userId");
		User user = accountService.getUser(Long.parseLong(userId));
		Role role = roleService.getRole(id);
		user.getRoleList().remove(role);
		accountService.saveUser(user);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "操作成功");

		return map;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 角色管理 功能：选择权限（需要为角色分配权限的权限）
	 */
	@RequiresPermissions("role:authPermission")
	@RequestMapping(value = "/admin/role/selectPermission/{id}", method = RequestMethod.GET)
	public String selectPermission(@PathVariable("id") Long roleId, Model model) {
		model.addAttribute("roleId", roleId);
		return "admin/security/selectPermission";
	}
	
	/**
	 * 模块：后台 >> 权限管理 >> 角色管理 功能：选择菜单
	 */
	@RequestMapping(value = "/admin/role/selectMenu/{id}", method = RequestMethod.GET)
	public String selectMenu(@PathVariable("id") Long roleId, Model model) {
		model.addAttribute("roleId", roleId);
		return "admin/security/selectMenu";
	}

	/**
	 * 不自动绑定对象中的属性，另行处理。
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("permissionList");
	}

}
