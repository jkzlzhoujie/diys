package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.LaberDao;
import cn.temobi.complex.entity.Client;
import cn.temobi.complex.entity.Laber;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("laberDao")
public class LaberDaoImpl extends SimpleMybatisSupport<Laber, Long> implements LaberDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Laber";
	}

	@Override
	public List<String> findRand(Map<String, Object> map) {
		return  getSqlSession().selectList(toMybatisStatement("findRand"), map);
	}
}
