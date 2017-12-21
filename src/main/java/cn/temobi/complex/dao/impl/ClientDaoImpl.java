package cn.temobi.complex.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import cn.temobi.complex.dao.ClientDao;
import cn.temobi.complex.entity.Client;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("clientDao")
public class ClientDaoImpl extends SimpleMybatisSupport<Client, Long> implements ClientDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Client";
	}

	@Override
	public Client getBySequence(Map<String, String> map) {
		return (Client) getSqlSession().selectOne(("getBySequence"), map);
	}

	@Override
	public Client getByUser(Map<String, Object> map) {
		return (Client) getSqlSession().selectOne(("getByUser"), map);
	}
    
}
