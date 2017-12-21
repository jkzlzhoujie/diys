package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.AccountWalletLogDao;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("accountWalletLogDao")
public class AccountWalletLogDaoImpl extends SimpleMybatisSupport<AccountWalletLog, Long> implements AccountWalletLogDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.AccountWalletLog";
	}

	@Override
	public Number sumPrice(Object parameter) {
		 return (Number) getSqlSession().selectOne(toMybatisStatement("sumPrice"), toParameterMap(parameter));
	}

}
