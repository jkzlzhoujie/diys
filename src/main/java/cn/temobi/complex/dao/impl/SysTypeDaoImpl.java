package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysTypeDao;
import cn.temobi.complex.entity.SysType;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("sysTypeDao")
public class SysTypeDaoImpl extends SimpleMybatisSupport<SysType, Long> implements SysTypeDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysType";
	}

}
