package com.fangxin365.wechat.service.security;

import java.util.Map;

import org.javasimon.aop.Monitored;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.fangxin365.wechat.entity.security.Role;
import com.fangxin365.wechat.repository.jpa.security.RoleDao;

/**
 * 角色管理业务类.
 */
@Component
@Transactional
@Monitored
public class RoleService {
	
	private RoleDao roleDao;
	
	/*@Autowired
	private RoleMybatisDao roleDao;
	
	public Role getRole(Long id) {
		return roleDao.get(id);
	}
	
	public List<Role> searchRole(String name) {
		Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("name", name);
		return roleDao.search(parameters);
	}
	
	public void saveRole(Role role) {
		roleDao.save(role);
	}

	public void deleteRole(Long id) {
		roleDao.delete(id);
	}*/
	
	/**
	 * 按页面传来的查询条件查询角色.（分页）
	 */
	public Page<Role> getRole(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Role> spec = buildSpecification(searchParams);
		return roleDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Role> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Role> spec = DynamicSpecifications.bySearchFilter(filters.values(), Role.class);
		return spec;
	}
	
	/**
	 * 保存角色
	 */
	public void saveRole(Role role) {
		roleDao.save(role);
	}
	
	/**
	 * 删除角色
	 */
	public void deleteRole(Long id) {
		roleDao.delete(id);
	}
	
	/**
	 * 按Id获得角色.
	 */
	public Role getRole(Long id) {
		return roleDao.findOne(id);
	}
	
	// -----------------//
	// Setter methods //
	// -----------------//
	
	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
