package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.AccountRedPacketLogDao;
import cn.temobi.complex.entity.AccountRedPacketLog;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("accountRedPacketLogDao")
public class AccountRedPacketLogDaoImpl extends SimpleMybatisSupport<AccountRedPacketLog, Long> implements AccountRedPacketLogDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.AccountRedPacketLog";
	}

	@Override
	public Number sumPrice(Object parameter) {
		 return (Number) getSqlSession().selectOne(toMybatisStatement("sumPrice"), toParameterMap(parameter));
	}
}
