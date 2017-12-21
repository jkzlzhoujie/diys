package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CircleTypeDao;
import cn.temobi.complex.entity.CircleType;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("circleTypeDao")
public class CircleTypeDaoImpl extends SimpleMybatisSupport<CircleType, Long> implements CircleTypeDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CircleType";
	}


	
}
