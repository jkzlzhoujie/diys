package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.ApplicationCountDao;
import cn.temobi.complex.entity.ApplicationCount;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("applicationCountDao")
public class ApplicationCountDaoImpl extends SimpleMybatisSupport<ApplicationCount, Long> implements ApplicationCountDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.ApplicationCount";
	}

}
