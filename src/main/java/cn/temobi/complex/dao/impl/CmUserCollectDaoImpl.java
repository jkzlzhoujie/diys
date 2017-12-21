package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserCollectDao;
import cn.temobi.complex.entity.CmUserCollect;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("cmUserCollectDao")
public class CmUserCollectDaoImpl extends SimpleMybatisSupport<CmUserCollect, Long> implements CmUserCollectDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserCollect";
	}


	
}
