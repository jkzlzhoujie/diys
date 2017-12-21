package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.BlackListNickNameDao;
import cn.temobi.complex.entity.BlackListNickName;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("blackListNickNameDao")
public class BlackListNickNameDaoImpl extends SimpleMybatisSupport<BlackListNickName, Long> implements BlackListNickNameDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.BlackListNickName";
	}
	
	@Override
	public Number checkContent(Object parameter){
		return (Number) getSqlSession().selectOne(toMybatisStatement("checkContent"), toParameterMap(parameter));
	}
}
