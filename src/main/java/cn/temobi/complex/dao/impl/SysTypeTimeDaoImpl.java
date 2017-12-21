package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysTypeTimeDao;
import cn.temobi.complex.entity.SysTypeTime;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("sysTypeTimeDao")
public class SysTypeTimeDaoImpl extends SimpleMybatisSupport<SysTypeTime, Long> implements SysTypeTimeDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysTypeTime";
	}

}
