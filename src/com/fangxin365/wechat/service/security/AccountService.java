package com.fangxin365.wechat.service.security;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.javasimon.aop.Monitored;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.Hibernates;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.fangxin365.wechat.demos.jms.simple.NotifyMessageProducer;
import com.fangxin365.wechat.demos.jmx.ApplicationStatistics;
import com.fangxin365.wechat.entity.security.Role;
import com.fangxin365.wechat.entity.security.User;
import com.fangxin365.wechat.repository.jpa.security.RoleDao;
import com.fangxin365.wechat.repository.jpa.security.UserDao;
import com.fangxin365.wechat.service.BusinessLogger;
import com.fangxin365.wechat.service.ServiceException;
import com.fangxin365.wechat.service.ShiroDbRealm.ShiroUser;
import com.google.common.collect.Maps;

/**
 * 用户管理业务类.
 */
@Component
@Transactional
@Monitored
public class AccountService{

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	private UserDao userDao;

	private RoleDao roleDao;

	private NotifyMessageProducer notifyProducer;

	private ApplicationStatistics applicationStatistics;

	private BusinessLogger businessLogger;

	/**
	 * 按页面传来的查询条件查询用户.（分页）
	 */
	public Page<User> getUser(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<User> spec = buildSpecification(searchParams);
		return userDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("loginName".equals(sortType)) {
			sort = new Sort(Direction.ASC, "loginName");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<User> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
		return spec;
	}

	/**
	 * 在保存用户时,发送用户修改通知消息, 由消息接收者异步进行较为耗时的通知邮件发送.
	 * 
	 * 如果企图修改超级用户,取出当前操作员用户,打印其信息然后抛出异常.
	 */
	public void saveUser(User user) {

		if (isSupervisor(user)) {
			logger.warn("操作员{}尝试修改超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能修改超级管理员用户");
		}

		// 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}

		userDao.save(user);

		// 发送JMS消息
		if (notifyProducer != null) {
			sendNotifyMessage(user);
		}
		
		// 运行统计演示
		if (applicationStatistics != null) {
			applicationStatistics.incrUpdateUserTimes();
		}

		// 业务日志演示
		Map<String, Object> logData = Maps.newHashMap();
		logData.put("userId", user.getId());
		businessLogger.log("USER", "UPDATE", getCurrentUserName(), logData);
	}

	/**
	 * 按Id获得用户.
	 */
	public User getUser(Long id) {
		return userDao.findOne(id);
	}
	
	/**
	 * 按登录名查询用户.
	 */
	public User findByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	/**
	 * 获取全部用户，并在返回前对用户的延迟加载关联角色进行初始化.
	 */
	public List<User> getAllUserInitialized() {
		List<User> users = (List<User>) userDao.findAll();
		for (User user : users) {
			Hibernates.initLazyProperty(user.getRoleList());
		}
		return users;
	}

	/**
	 * 按名称查询用户, 并在返回前对用户的延迟加载关联角色进行初始化.
	 */
	public User findUserByNameInitialized(String name) {
		User user = userDao.findByName(name);
		if (user != null) {
			Hibernates.initLazyProperty(user.getRoleList());
		}
		return user;
	}

	/**
	 * 按页面传来的查询条件查询用户.（不分页）
	 */
	public List<User> searchUser(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
		List<User> users = userDao.findAll(spec);

		// 运行统计演示
		if (applicationStatistics != null) {
			applicationStatistics.incrListUserTimes();
		}
		
		// 业务日志演示
		if (businessLogger != null) {
			businessLogger.log("USER", "LIST", getCurrentUserName(), null);
		}
		
		return users;
	}

	/**
	 * 获取当前用户数量.
	 */
	public Long getUserCount() {
		return userDao.count();
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(User user) {
		return ((user.getId() != null) && (user.getId() == 1L));
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	/**
	 * 发送用户变更消息.
	 * 
	 * 同时发送只有一个消费者的Queue消息与发布订阅模式有多个消费者的Topic消息.
	 */
	private void sendNotifyMessage(User user) {
		try {
			notifyProducer.sendQueue(user);
			notifyProducer.sendTopic(user);
		} catch (Exception e) {
			logger.error("消息发送失败", e);
		}
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	public void deleteUser(Long id) {
		userDao.delete(id);
	}

	// --------------------//
	// Role Management     //
	// --------------------//

	public List<Role> getAllRole() {
		return (List<Role>) roleDao.findAll();
	}

	// -----------------//
	// Setter methods //
	// -----------------//

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Autowired
	public void setBusinessLogger(BusinessLogger businessLogger) {
		this.businessLogger = businessLogger;
	}

	@Autowired(required = false)
	public void setNotifyProducer(NotifyMessageProducer notifyProducer) {
		this.notifyProducer = notifyProducer;
	}

	@Autowired(required = false)
	public void setApplicationStatistics(ApplicationStatistics applicationStatistics) {
		this.applicationStatistics = applicationStatistics;
	}
	
}
