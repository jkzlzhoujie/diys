package cn.temobi.complex.dao;

import cn.temobi.complex.entity.AccountRedPacketLog;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface AccountRedPacketLogDao extends SimpleDao<AccountRedPacketLog, Long> {

	public Number sumPrice(Object parameter);
}
