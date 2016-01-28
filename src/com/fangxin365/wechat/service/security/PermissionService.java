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

import com.fangxin365.wechat.entity.security.Permission;
import com.fangxin365.wechat.repository.jpa.security.PermissionDao;

/**
 * 权限管理业务类.
 */
@Component
@Transactional
@Monitored
public class PermissionService {

	private PermissionDao permissionDao;
	
	/**
	 * 按页面传来的查询条件查询权限.（分页）
	 */
	public Page<Permission> getPermission(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Permission> spec = buildSpecification(searchParams);
		return permissionDao.findAll(spec, pageRequest);
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
	private Specification<Permission> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Permission> spec = DynamicSpecifications.bySearchFilter(filters.values(), Permission.class);
		return spec;  
	}
	
	/**
	 * 保存权限
	 */
	public void savePermission(Permission permission) {
		permissionDao.save(permission);
	}
	
	/**
	 * 删除权限
	 */
	public void deletePermission(Long id) {
		permissionDao.delete(id);
	}
	
	/**
	 * 按Id获得角色.
	 */
	public Permission getPermission(Long id) {
		return permissionDao.findOne(id);
	}

	// -----------------//
	// Setter methods //
	// -----------------//
	
	@Autowired
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

}
