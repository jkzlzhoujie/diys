package cn.temobi.complex.dao;

import cn.temobi.complex.entity.ClientUser;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 17 17:28:30
 */
public interface ClientUserDao extends SimpleDao<ClientUser, Long> {
	
	/**
	 * 根据用户名查找
	 * @param username
	 * @return
	 */
	public ClientUser getByUsername(String username);
}
