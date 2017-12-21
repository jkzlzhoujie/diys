package cn.temobi.complex.dao.impl;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmGroupDao;
import cn.temobi.complex.entity.CmGroup;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmGroupDao")
public class CmGroupDaoImpl extends SimpleMybatisSupport<CmGroup, Long> implements CmGroupDao{
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmGroup";
	}

}
