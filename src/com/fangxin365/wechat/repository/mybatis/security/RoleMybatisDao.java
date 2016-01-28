package com.fangxin365.wechat.repository.mybatis.security;

import java.util.List;
import java.util.Map;

import com.fangxin365.wechat.entity.security.Role;
import com.fangxin365.wechat.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface RoleMybatisDao {

	Role get(Long id);

	List<Role> search(Map<String, Object> parameters);

	void save(Role role);

	void delete(Long id);

}
