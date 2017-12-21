package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.ApplicationDao;
import cn.temobi.complex.entity.Application;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("applicationDao")
public class ApplicationDaoImpl extends SimpleMybatisSupport<Application, Long> implements ApplicationDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Application";
	}

}
