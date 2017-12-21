package cn.temobi.complex.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.AccountUserBuyDao;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("accountUserBuyDao")
public class AccountUserBuyDaoImpl extends SimpleMybatisSupport<AccountUserBuy, Long> implements AccountUserBuyDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.AccountUserBuy";
	}

	@Override
	public void deleteByAccountBuyId(Map<String, Object> map) {
		this.getSqlSession().delete(toMybatisStatement("deleteByAccountBuyId"), map);
	}

	@Override
	public Number sumPrice(Object parameter) {
		 return (Number) getSqlSession().selectOne(toMybatisStatement("sumPrice"), toParameterMap(parameter));
	}
}
