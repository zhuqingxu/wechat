package com.fangxin365.wechat.service.security;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.javasimon.aop.Monitored;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.fangxin365.wechat.entity.TreeNode;
import com.fangxin365.wechat.entity.security.Menu;
import com.fangxin365.wechat.repository.jpa.security.MenuDao;
import com.fangxin365.wechat.service.ShiroDbRealm.ShiroUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 菜单管理业务类.
 */
@Component
@Transactional
@Monitored
public class MenuService {
	
	private MenuDao menuDao;

	/**
	 * 获取当前用户已授权上级菜单
	 */
	public List<TreeNode> loadTopMenus() {
		List<TreeNode> treeNodes = Lists.newArrayList();

		try {
			List<Menu> menus = loadMenus(1l, getCurrentUserId());

			for (int i = 0; i < menus.size(); i++) {
				Menu menu = menus.get(i);

				TreeNode treeNode = new TreeNode();
				treeNode.setId(menu.getId());
				treeNode.setText(menu.getTitle());
				if(0 == menu.getIsleaf()){
					treeNode.setState("closed");
				}

				/*
				 * 添加Tree自定义属性
				 */
				Map<String, String> attributes = Maps.newHashMap();
				attributes.put("name", menu.getTitle());
				attributes.put("forward", menu.getForward());
				treeNode.setAttributes(attributes);

				treeNodes.add(treeNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return treeNodes;
	}
	
	/**
	 * 获取所有上级菜单,已授权的为选中状态
	 */
	public List<TreeNode> loadAllTopMenus(String roleId) {
		List<TreeNode> treeNodes = Lists.newArrayList();
		
		try {
			List<Menu> menus = loadAllMenus(1l);
			

			for (int i = 0; i < menus.size(); i++) {
				Menu menu = menus.get(i);
				
				TreeNode treeNode = new TreeNode();
				treeNode.setId(menu.getId());
				treeNode.setText(menu.getTitle());
				if(0 == menu.getIsleaf()){
					treeNode.setState("closed");
				}
				
				int count = menuDao.findMenuRole(menu.getId(), Long.parseLong(roleId));
				if (count > 0) {
					treeNode.setChecked("true");
				}

				/*
				 * 添加Tree自定义属性
				 */
				Map<String, String> attributes = Maps.newHashMap();
				attributes.put("name", menu.getTitle());
				attributes.put("forward", menu.getForward());
				treeNode.setAttributes(attributes);

				treeNodes.add(treeNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return treeNodes;
	}

	/**
	 * 获取当前用户已授权子菜单
	 */
	public List<TreeNode> loadChildMenus(Long pid) {
		List<TreeNode> treeNodes = Lists.newArrayList();

		try {
			List<Menu> menus = loadMenus(pid, getCurrentUserId());

			for (int i = 0; i < menus.size(); i++) {
				Menu menu = menus.get(i);

				TreeNode treeNode = new TreeNode();
				treeNode.setId(menu.getId());
				treeNode.setText(menu.getTitle());
				if(0 == menu.getIsleaf()){
					treeNode.setState("closed");
				}

				/*
				 * 添加Tree自定义属性
				 */
				Map<String, String> attributes = Maps.newHashMap();
				attributes.put("name", menu.getTitle());
				attributes.put("forward", menu.getForward());
				treeNode.setAttributes(attributes);

				treeNodes.add(treeNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return treeNodes;
	}
	
	/**
	 * 获取所有子菜单,已授权的为选中状态
	 */
	public List<TreeNode> loadAllChildMenus(Long pid, String roleId) {
		List<TreeNode> treeNodes = Lists.newArrayList();

		try {
			List<Menu> menus = loadAllMenus(pid);

			if(menus != null && menus.size() > 0) {
				for (int i = 0; i < menus.size(); i++) {
					Menu menu = menus.get(i);

					TreeNode treeNode = new TreeNode();
					treeNode.setId(menu.getId());
					treeNode.setText(menu.getTitle());
					if(0 == menu.getIsleaf()){
						treeNode.setState("closed");
					}
					
					int count = menuDao.findMenuRole(menu.getId(), Long.parseLong(roleId));
					if (count > 0) {
						treeNode.setChecked("true");
					}

					/*
					 * 添加Tree自定义属性
					 */
					Map<String, String> attributes = Maps.newHashMap();
					attributes.put("name", menu.getTitle());
					attributes.put("forward", menu.getForward());
					treeNode.setAttributes(attributes);

					treeNodes.add(treeNode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return treeNodes;
	}
	
	/**
	 * 获取当前用户具有权限的菜单
	 */
	@SuppressWarnings("rawtypes")
	public List<Menu> loadMenus(Long pid, Long userId) {
		List<Menu> menuList = Lists.newArrayList();
		List<Object[]> list = menuDao.findMenuByUser(userId, pid);
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Object[] result = (Object[])it.next();
			Menu menu = new Menu();
			menu.setId(((BigInteger)result[0]).longValue());
			menu.setTitle((String)result[1]);
			menu.setForward((String)result[2]);
			menu.setIsleaf((Integer)result[3]);
			
			menuList.add(menu);
		}
		return menuList;
	}
	
	/**
	 * 获取当前角色对应的菜单
	 */
	@SuppressWarnings("rawtypes")
	public List<Menu> loadMenus(Long roleId) {
		List<Menu> menuList = Lists.newArrayList();
		List<Object[]> list = menuDao.findMenuByRole(roleId);
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Object[] result = (Object[])it.next();
			Menu menu = new Menu();
			menu.setId(((BigInteger)result[0]).longValue());
			menu.setTitle((String)result[1]);
			menu.setForward((String)result[2]);
			menu.setIsleaf((Integer)result[3]);
			
			menuList.add(menu);
		}
		return menuList;
	}
	
	/**
	 * 获取当前用户具有权限的菜单
	 */
	public List<Menu> loadAllMenus(Long pid) {
		Map<String, SearchFilter> filters = Maps.newHashMap();
		filters.put("parentId", new SearchFilter("parentId", Operator.EQ, pid));
		Specification<Menu> spec = DynamicSpecifications.bySearchFilter(filters.values(), Menu.class);
		List<Menu> menuList = menuDao.findAll(spec);

		return menuList;
	}
	
	/**
	 * 取出Shiro中的当前用户ID.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	
	/**
	 * 获取所有上级菜单
	 */
	public List<TreeNode> loadAllMenusByRole(String roleId) {
		List<TreeNode> treeNodes = Lists.newArrayList();
		
		try {
			List<Menu> menus = loadAllMenus(1l);
			

			for (int i = 0; i < menus.size(); i++) {
				Menu menu = menus.get(i);
				
				TreeNode treeNode = new TreeNode();
				treeNode.setId(menu.getId());
				treeNode.setText(menu.getTitle());
				if(0 == menu.getIsleaf()){
					treeNode.setState("closed");
				} else {
					int count = menuDao.findMenuRole(menu.getId(), Long.parseLong(roleId));
					if (count > 0) {
						treeNode.setChecked("true");
					}
				}
				
				List<TreeNode> childMenus = loadAllChildMenus(menu.getId(), roleId);
				treeNode.setChildren(childMenus);

				/*
				 * 添加Tree自定义属性
				 */
				Map<String, String> attributes = Maps.newHashMap();
				attributes.put("name", menu.getTitle());
				attributes.put("forward", menu.getForward());
				treeNode.setAttributes(attributes);

				treeNodes.add(treeNode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return treeNodes;
	}
	
	/**
	 * 按Id获得菜单.
	 */
	public Menu getMenu(Long id) {
		return menuDao.findOne(id);
	}

	// -----------------//
	// Setter methods //
	// -----------------//
	
	@Autowired
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

}
