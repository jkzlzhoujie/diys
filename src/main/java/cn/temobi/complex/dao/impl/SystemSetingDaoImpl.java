package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SystemSetingDao;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("systemSetingDao")
public class SystemSetingDaoImpl extends SimpleMybatisSupport<SystemSeting, Long> implements SystemSetingDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SystemSeting";
	}
    
}
