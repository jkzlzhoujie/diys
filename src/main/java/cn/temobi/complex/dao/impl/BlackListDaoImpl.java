package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.BlackListDao;
import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("blackListDao")
public class BlackListDaoImpl extends SimpleMybatisSupport<BlackListWord, Long> implements BlackListDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.BlackListWord";
	}
	
	@Override
	public Number checkContent(Object parameter){
		return (Number) getSqlSession().selectOne(toMybatisStatement("checkContent"), toParameterMap(parameter));
	}
}
