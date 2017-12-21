package cn.temobi.complex.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SystemUserDao;
import cn.temobi.complex.entity.SystemUser;
import cn.temobi.core.common.SimpleMybatisSupport;

/**
 * 客户端用户账户信息
 * @author hujf
 *
 */
@Component
@Repository("systemUserDao")
public class SystemUserDaoImpl extends SimpleMybatisSupport<SystemUser, Long> implements SystemUserDao{
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SystemUser";
	}
	
	/**
	 * 通过账号和密码取用户
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SystemUser findUser(Map map){
		return (SystemUser) getSqlSession().selectOne(toMybatisStatement("findAll"),map);
	}
	
}
