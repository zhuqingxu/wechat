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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.fangxin365.core.commons.BaseController;
import com.fangxin365.core.utils.DecodeUtil;
import com.fangxin365.wechat.entity.security.Permission;
import com.fangxin365.wechat.entity.security.Role;
import com.fangxin365.wechat.service.security.AuthHelper;
import com.fangxin365.wechat.service.security.PermissionService;
import com.fangxin365.wechat.service.security.RoleService;
import com.google.common.collect.Maps;

@Controller
public class PermissionAdminController  extends BaseController {

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RoleService roleService;

	/**
	 * 模块：后台 >> 权限管理 >> 权限管理 功能：跳转到权限管理页面
	 */
	@RequestMapping(value = "/admin/permission/manage", method = RequestMethod.GET)
	public String managePermission(Model model) {
		return "admin/security/managePermission";
	}

	/**
	 * 模块：后台 >> 权限管理 >> 权限管理 功能：获取权限列表（需要查看权限的权限）
	 */
	@RequiresPermissions("permission:view")
	@RequestMapping(value = "/admin/permission", method = RequestMethod.POST)
	@ResponseBody
	public Object listPermission(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE_FIFTEEN) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "name", defaultValue = "") String name,
			Model model, ServletRequest request) {

		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("LIKE_name", DecodeUtil.decodeParam(name));
		Page<Permission> page = permissionService.getPermission(searchParams, pageNumber, pageSize, sortType);

		JsonConfig config = JsonConfig.getInstance();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(new String[] { "permissionList" });

		Map<String, Object> jsonMap = Maps.newHashMap();
		jsonMap.put("total", page.getTotalElements());
		jsonMap.put("rows", page.getContent());

		JSONObject object = JSONObject.fromObject(jsonMap);

		return object;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 权限管理 功能：保存添加或修改的权限
	 */
	@RequiresPermissions(value = { "permission:add", "permission:edit" }, logical = Logical.OR)
	@RequestMapping(value = "/admin/permission/save", method = RequestMethod.POST)
	@ResponseBody
	public String savePermission(
			@Valid @ModelAttribute("permission") Permission permission) {
		permissionService.savePermission(permission);
		return "{success:true,msg:'操作成功'}";
	}

	/**
	 * 模块：后台 >> 权限管理 >> 权限管理 功能：删除权限
	 */
	@RequiresPermissions("permission:delete")
	@RequestMapping(value = "/admin/permission/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePermission(@PathVariable("id") Long id) {
		// 删除权限
		permissionService.deletePermission(id);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "操作成功");

		return map;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 权限管理 功能：权限授权列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/admin/permission/authorize", method = RequestMethod.POST)
	@ResponseBody
	public Object authorize(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = PAGE_SIZE_FIFTEEN) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "roleId", defaultValue = "") String roleId,
			Model model, HttpServletRequest request) throws Exception {

		if (StringUtils.isNotBlank(roleId)) {
			WebUtils.setSessionAttribute(request, "roleId", roleId);
		}

		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("LIKE_name", DecodeUtil.decodeParam(name));
		Page<Permission> page = permissionService.getPermission(searchParams, pageNumber, pageSize, sortType);

		Role role = roleService.getRole(Long.parseLong(roleId));

		Collection permissions = page.getContent();
		AuthHelper.judgeAuth(permissions, role.getPermissionList());

		Map<String, Object> jsonMap = Maps.newHashMap();
		jsonMap.put("total", page.getTotalElements());
		jsonMap.put("rows", page.getContent());

		JSONObject object = JSONObject.fromObject(jsonMap);

		return object;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 权限管理 功能：授权角色权限
	 */
	@RequestMapping(value = "/admin/permission/authPermission/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> authPermission(@PathVariable("id") Long id,
			HttpServletRequest request) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "操作成功");

		String roleId = (String) WebUtils.getRequiredSessionAttribute(request, "roleId");
		Role role = roleService.getRole(Long.parseLong(roleId));
		Permission permission = permissionService.getPermission(id);
		role.getPermissionList().add(permission);
		roleService.saveRole(role);

		return map;
	}

	/**
	 * 模块：后台 >> 权限管理 >> 权限管理 功能：撤销授权角色权限
	 */
	@RequestMapping(value = "/admin/permission/unauthPermission/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unauthPermission(@PathVariable("id") Long id,
			HttpServletRequest request) {
		
		String roleId = (String) WebUtils.getRequiredSessionAttribute(request, "roleId");
		Role role = roleService.getRole(Long.parseLong(roleId));
		Permission permission = permissionService.getPermission(id);
		role.getPermissionList().remove(permission);
		roleService.saveRole(role);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "操作成功");

		return map;
	}

}
