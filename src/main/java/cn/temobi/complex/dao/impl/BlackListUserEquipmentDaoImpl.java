package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.BlackListUserEquipmentDao;
import cn.temobi.complex.entity.BlackListUserEquipment;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("blackListUserEquipmentDao")
public class BlackListUserEquipmentDaoImpl extends SimpleMybatisSupport<BlackListUserEquipment, Long> implements BlackListUserEquipmentDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.BlackListUserEquipment";
	}
	
	@Override
	public Number checkContent(Object parameter){
		return (Number) getSqlSession().selectOne(toMybatisStatement("checkContent"), toParameterMap(parameter));
	}
}
