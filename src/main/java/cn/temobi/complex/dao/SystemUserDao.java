package cn.temobi.complex.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.temobi.complex.entity.SystemUser;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014-3-5 下午03:23:46
 */
public interface SystemUserDao extends SimpleDao<SystemUser, Long> {
	
	/**
	 * 通过账号和密码取用户
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SystemUser findUser(Map map)throws DataAccessException;
	
}
