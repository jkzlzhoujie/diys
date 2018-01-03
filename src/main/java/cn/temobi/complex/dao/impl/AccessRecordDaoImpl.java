package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.AccessRecordDao;
import cn.temobi.complex.entity.AccessRecord;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("accessRecordDao")
public class AccessRecordDaoImpl extends SimpleMybatisSupport<AccessRecord, Long> implements AccessRecordDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.AccessRecord";
	}


	
}
