package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserSignLogDao;
import cn.temobi.complex.entity.CmUserSignLog;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserSignLogDao")
public class CmUserSignLogDaoImpl extends SimpleMybatisSupport<CmUserSignLog, Long> implements CmUserSignLogDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserSignLog";
	}

}
