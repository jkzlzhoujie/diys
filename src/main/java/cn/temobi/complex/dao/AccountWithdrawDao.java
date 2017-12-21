package cn.temobi.complex.dao;

import cn.temobi.complex.entity.AccountWithdraw;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface AccountWithdrawDao extends SimpleDao<AccountWithdraw, Long> {

	public Number sumPrice(Object parameter);
}
