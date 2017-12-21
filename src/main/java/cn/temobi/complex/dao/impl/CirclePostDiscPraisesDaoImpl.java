package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CirclePostDiscPraisesDao;
import cn.temobi.complex.entity.CirclePostDiscPraises;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("circlePostDiscPraisesDao")
public class CirclePostDiscPraisesDaoImpl extends SimpleMybatisSupport<CirclePostDiscPraises, Long> implements CirclePostDiscPraisesDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CirclePostDiscPraises";
	}

}
