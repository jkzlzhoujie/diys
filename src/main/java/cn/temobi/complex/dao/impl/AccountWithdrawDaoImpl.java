package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.AccountWithdrawDao;
import cn.temobi.complex.entity.AccountWithdraw;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("accountWithdrawDao")
public class AccountWithdrawDaoImpl extends SimpleMybatisSupport<AccountWithdraw, Long> implements AccountWithdrawDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.AccountWithdraw";
	}

	@Override
	public Number sumPrice(Object parameter) {
		 return (Number) getSqlSession().selectOne(toMybatisStatement("sumPrice"), toParameterMap(parameter));
	}
}
