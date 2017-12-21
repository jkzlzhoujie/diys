package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.LaberConfigureDao;
import cn.temobi.complex.entity.LaberConfigure;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("laberConfigureDao")
public class LaberConfigureDaoImpl extends SimpleMybatisSupport<LaberConfigure, Long> implements LaberConfigureDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.LaberConfigure";
	}

}
