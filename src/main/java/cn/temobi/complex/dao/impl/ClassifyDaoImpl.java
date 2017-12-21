package cn.temobi.complex.dao.impl;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.ClassifyDao;
import cn.temobi.complex.entity.Classify;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("classifyDao")
public class ClassifyDaoImpl extends SimpleMybatisSupport<Classify, Long> implements ClassifyDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Classify";
	}

}
