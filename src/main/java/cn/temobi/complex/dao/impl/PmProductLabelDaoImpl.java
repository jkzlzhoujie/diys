package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmProductLabelDao;
import cn.temobi.complex.entity.PmProductLabel;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("pmProductLabelDao")
public class PmProductLabelDaoImpl extends SimpleMybatisSupport<PmProductLabel, Long> implements PmProductLabelDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmProductLabel";
	}


	
}
