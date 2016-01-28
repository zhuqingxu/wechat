package com.fangxin365.wechat.repository.jpa.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fangxin365.wechat.entity.security.Menu;

/**
 * 菜单.
 */
public interface MenuDao extends PagingAndSortingRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {
	
	@Query(value="SELECT DISTINCT mr.MENU_ID, m.TITLE, m.FORWARD, m.ISLEAF "
			+ "FROM ss_menu_role mr, ss_role r, ss_user_role ur, ss_menu m "
			+ "WHERE mr.ROLE_ID = r.ID "
			+ "AND r.ID = ur.ROLE_ID "
			+ "AND ur.USER_ID = ?1 "
			+ "AND mr.MENU_ID = m.ID "
			+ "AND m.PARENT_ID = ?2", nativeQuery = true)
	public List<Object[]> findMenuByUser(Long userId, Long parentId);
	
	@Query(value="SELECT DISTINCT mr.MENU_ID, m.TITLE, m.FORWARD, m.ISLEAF "
			+ "FROM ss_menu_role mr, ss_role r, ss_menu m "
			+ "WHERE mr.ROLE_ID = r.ID "
			+ "AND mr.MENU_ID = m.ID "
			+ "AND r.ID = ?1", nativeQuery = true)
	public List<Object[]> findMenuByRole(Long roleId);
	
	@Query(value="SELECT COUNT(menu_ID) "
			+ "FROM ss_menu_role mr "
			+ "WHERE mr.menu_ID = ?1 "
			+ "AND mr.ROLE_ID = ?2", nativeQuery = true)
	public int findMenuRole(Long menuId, Long roleId);

}
