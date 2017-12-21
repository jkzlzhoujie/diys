package cn.temobi.complex.dao;

import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface AccountWalletLogDao extends SimpleDao<AccountWalletLog, Long> {

	public Number sumPrice(Object parameter);
}
