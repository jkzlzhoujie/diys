package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysConfigParamDao;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("sysConfigParamDao")
public class SysConfigParamDaoImpl extends SimpleMybatisSupport<SysConfigParam, Long> implements SysConfigParamDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysConfigParam";
	}

}
