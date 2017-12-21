package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysIndustryInfoDao;
import cn.temobi.complex.entity.SysIndustryInfo;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("sysIndustryInfoDao")
public class SysIndustryInfoDaoImpl extends SimpleMybatisSupport<SysIndustryInfo, Long> implements SysIndustryInfoDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysIndustryInfo";
	}

}
