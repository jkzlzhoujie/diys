package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.BusinessCountDao;
import cn.temobi.complex.entity.BusinessCount;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("businessCountDao")
public class BusinessCountDaoImpl extends SimpleMybatisSupport<BusinessCount, Long> implements BusinessCountDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.BusinessCount";
	}

}
