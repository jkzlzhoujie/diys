package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmProductSharesDao;
import cn.temobi.complex.entity.PmProductShares;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmProductSharesDao")
public class PmProductSharesDaoImpl extends SimpleMybatisSupport<PmProductShares, Long> implements PmProductSharesDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmProductShares";
	}

}
