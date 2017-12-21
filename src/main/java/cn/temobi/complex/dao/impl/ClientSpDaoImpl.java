package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.ClientSpDao;
import cn.temobi.complex.entity.ClientSp;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("clientSpDao")
public class ClientSpDaoImpl extends SimpleMybatisSupport<ClientSp, Long> implements ClientSpDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.ClientSp";
	}

    
}
