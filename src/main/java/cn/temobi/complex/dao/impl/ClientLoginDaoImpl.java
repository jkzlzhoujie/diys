package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import cn.temobi.complex.dao.ClientLoginDao;
import cn.temobi.complex.entity.ClientLogin;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("clientLoginDao")
public class ClientLoginDaoImpl extends SimpleMybatisSupport<ClientLogin, Long> implements ClientLoginDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.ClientLogin";
	}
    
}
