package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CmUserSignDao;
import cn.temobi.complex.entity.CmUserSign;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserSignDao")
public class CmUserSignDaoImpl extends SimpleMybatisSupport<CmUserSign, Long> implements CmUserSignDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserSign";
	}

}
