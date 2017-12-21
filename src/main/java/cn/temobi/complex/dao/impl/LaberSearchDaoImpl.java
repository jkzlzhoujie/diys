package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.LaberSearchDao;
import cn.temobi.complex.entity.LaberSearch;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("laberSearchDao")
public class LaberSearchDaoImpl extends SimpleMybatisSupport<LaberSearch, Long> implements LaberSearchDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.LaberSearch";
	}

}
