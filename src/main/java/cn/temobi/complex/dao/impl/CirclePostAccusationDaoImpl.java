package cn.temobi.complex.dao.impl;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CirclePostAccusationDao;
import cn.temobi.complex.entity.CirclePostAccusation;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("circlePostAccusationDao")
public class CirclePostAccusationDaoImpl extends SimpleMybatisSupport<CirclePostAccusation, Long> implements CirclePostAccusationDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CirclePostAccusation";
	}

}
