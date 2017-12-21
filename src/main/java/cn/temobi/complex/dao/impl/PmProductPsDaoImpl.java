package cn.temobi.complex.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.PmProductPsDao;
import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.entity.PmProductPs;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmProductPsDao")
public class PmProductPsDaoImpl extends SimpleMybatisSupport<PmProductPs, Long> implements PmProductPsDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmProductPs";
	}

}
