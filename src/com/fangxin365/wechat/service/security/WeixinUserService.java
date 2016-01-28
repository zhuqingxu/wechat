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

import com.fangxin365.core.utils.PageInfo;
import com.fangxin365.wechat.entity.security.WeixinUser;
import com.fangxin365.wechat.repository.jpa.security.WeixinUserDao;

@Component
@Transactional
@Monitored
public class WeixinUserService {

	private WeixinUserDao weixinUserDao;
	
	// -----------------//
	// Query methods
	// -----------------//
	
	/**
	 * Page
	 */
	public Page<WeixinUser> getWeixinUser(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<WeixinUser> spec = buildSpecification(searchParams);
		return weixinUserDao.findAll(spec, pageRequest);
	}
	
	/**
	 * Page
	 */
	public Page<WeixinUser> getWeixinUser(PageInfo page) {
		PageRequest pageRequest = buildPageRequest(page.getPageNo(), page.getPageSize(), "auto");
		return weixinUserDao.findWeixinUser(pageRequest);
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "id");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<WeixinUser> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<WeixinUser> spec = DynamicSpecifications.bySearchFilter(filters.values(), WeixinUser.class);
		return spec;
	}
	
	/**
	 * Entity
	 */
	public WeixinUser getWeixinUser(Long id) {
		return weixinUserDao.findOne(id);
	}

	/**
	 * Entity
	 */
	public WeixinUser getWeixinUser(String openid) {
		return weixinUserDao.findByOpenid(openid);
	}
	
	// -----------------//
	// Save methods
	// -----------------//
	
	public void saveWeixinUser(WeixinUser weixinUser) {
		weixinUserDao.save(weixinUser);
	}

	// -----------------//
	// Setter methods
	// -----------------//

	@Autowired
	public void setWeixinUserDao(WeixinUserDao weixinUserDao) {
		this.weixinUserDao = weixinUserDao;
	}

}
