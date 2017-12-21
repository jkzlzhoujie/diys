package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.SysProductTypeInfoDao;
import cn.temobi.complex.dto.AllTypeDto;
import cn.temobi.complex.entity.SysProductTypeInfo;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("sysProductTypeInfoDao")
public class SysProductTypeInfoDaoImpl extends SimpleMybatisSupport<SysProductTypeInfo, Long> implements SysProductTypeInfoDao{
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.SysProductTypeInfo";
	}
	
	@Override
	public List<SysProductTypeInfo> findByUserId(Map<String,Object> map) {
		return getSqlSession().selectList(toMybatisStatement("findByUserId"), map);
	}

	@Override
	public List<AllTypeDto> findAllType(Map<String, Object> map) {
		return getSqlSession().selectList(toMybatisStatement("findAllType"), map);
	}
}
