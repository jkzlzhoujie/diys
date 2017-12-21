package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.AccountUserLogDao;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("accountUserLogDao")
public class AccountUserLogDaoImpl extends SimpleMybatisSupport<AccountUserLog, Long> implements AccountUserLogDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.AccountUserLog";
	}

}
