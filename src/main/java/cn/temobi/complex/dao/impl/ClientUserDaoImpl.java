package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import cn.temobi.complex.dao.ClientUserDao;
import cn.temobi.complex.entity.ClientUser;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("clientUserDao")
public class ClientUserDaoImpl extends SimpleMybatisSupport<ClientUser, Long> implements ClientUserDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.ClientUser";
	}

	@Override
	public ClientUser getByUsername(String username) {
		return (ClientUser) getSqlSession().selectOne(toMybatisStatement("getByUsername"), username);
	}
    
}
