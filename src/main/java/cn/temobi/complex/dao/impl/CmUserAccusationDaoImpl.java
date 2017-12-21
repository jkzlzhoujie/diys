package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserAccusationDao;
import cn.temobi.complex.entity.CmUserAccusation;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserAccusationDao")
public class CmUserAccusationDaoImpl extends SimpleMybatisSupport<CmUserAccusation, Long> implements CmUserAccusationDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserAccusation";
	}

}
