package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CirclePostPraisesDao;
import cn.temobi.complex.entity.CirclePostPraises;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("circlePostPraisesDao")
public class CirclePostPraisesDaoImpl extends SimpleMybatisSupport<CirclePostPraises, Long> implements CirclePostPraisesDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CirclePostPraises";
	}

}
