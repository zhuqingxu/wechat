package com.fangxin365.wechat.repository.jpa.security;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fangxin365.wechat.entity.security.Permission;

public interface PermissionDao extends PagingAndSortingRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

	Permission findByName(String name);

}
