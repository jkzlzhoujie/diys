package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.ThemeLoveDao;
import cn.temobi.complex.entity.ThemeLove;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("themeLoveDao")
public class ThemeLoveDaoImpl extends SimpleMybatisSupport<ThemeLove, Long> implements ThemeLoveDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.ThemeLove";
	}

}
