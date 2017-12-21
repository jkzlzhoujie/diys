package cn.temobi.complex.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.ClientVersionDao;
import cn.temobi.complex.entity.ClientVersion;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("clientVersionDao")
public class ClientVersionDaoImpl extends SimpleMybatisSupport<ClientVersion, Long> implements ClientVersionDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.ClientVersion";
	}

	@Override
	public ClientVersion getNewVersion(Map<String, String> map) {
		return (ClientVersion) getSqlSession().selectOne(("getNewVersion"), map);
	}
    
}
