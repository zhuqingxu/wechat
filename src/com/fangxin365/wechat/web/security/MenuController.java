package com.fangxin365.wechat.web.security;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.fangxin365.wechat.entity.TreeNode;
import com.fangxin365.wechat.entity.security.Menu;
import com.fangxin365.wechat.entity.security.Role;
import com.fangxin365.wechat.service.security.MenuService;
import com.fangxin365.wechat.service.security.RoleService;
import com.google.common.collect.Maps;

@Controller
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleService roleService;

	/**
	 * 获取当前用户已授权上级菜单
	 */
	@RequestMapping(value = "/loadTopMenus", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> loadTopMenus() {
		List<TreeNode> trees = menuService.loadTopMenus();
		return trees;
	}
	
	/**
	 * 获取当前用户已授权子菜单
	 */
	@RequestMapping(value = "/loadChildMenus/{pid}", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> loadChildMenus(@PathVariable("pid") Long pid) {
		List<TreeNode> trees = menuService.loadChildMenus(pid);
		return trees;
	}
	
	/**
	 * 获取所有上级菜单
	 */
	@RequestMapping(value = "/loadAllTopMenus", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> loadAllTopMenus(
			@RequestParam(value = "roleId", defaultValue = "") String roleId,
			HttpServletRequest request) {
		if (StringUtils.isNotBlank(roleId)) {
			WebUtils.setSessionAttribute(request, "roleId", roleId);
		}
		List<TreeNode> trees = menuService.loadAllTopMenus(roleId);
		return trees;
	}
	
	/**
	 * 获取所有子菜单
	 */
	@RequestMapping(value = "/loadAllChildMenus/{pid}", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> loadAllChildMenus(@PathVariable("pid") Long pid, HttpServletRequest request) {
		String roleId = (String) WebUtils.getRequiredSessionAttribute(request,
				"roleId");
		List<TreeNode> trees = menuService.loadAllChildMenus(pid, roleId);
		return trees;
	}
	
	/**
	 * 获取所有菜单
	 */
	@RequestMapping(value = "/loadAllMenus", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> loadAllMenus(
			@RequestParam(value = "roleId", defaultValue = "") String roleId,
			HttpServletRequest request) {
		if (StringUtils.isNotBlank(roleId)) {
			WebUtils.setSessionAttribute(request, "roleId", roleId);
		}
		List<TreeNode> trees = menuService.loadAllMenusByRole(roleId);
		return trees;
	}
	
	/**
	 * 授权角色权限
	 */
	@RequestMapping(value = "/admin/menu/authMenu/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> authMenu(@PathVariable("id") String id,
			HttpServletRequest request) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		map.put("msg", "操作成功");

		String roleId = (String) WebUtils.getRequiredSessionAttribute(request, "roleId");
		Role role = roleService.getRole(Long.parseLong(roleId));
		
		role.getMenuList().clear();
		
		String[] ids = id.split(",");
		for(String menuId: ids) {
			if(StringUtils.isNotBlank(menuId)) {
				Menu menu = menuService.getMenu(Long.parseLong(menuId));
				Menu parentMenu = menuService.getMenu(menu.getParentId());
				role.getMenuList().add(menu);
				if(!role.getMenuList().contains(parentMenu)) {
					role.getMenuList().add(parentMenu);
				}
				
				roleService.saveRole(role);
			}
		}

		return map;
	}

}
