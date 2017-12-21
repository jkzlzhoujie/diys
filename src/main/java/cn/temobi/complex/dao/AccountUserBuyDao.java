package cn.temobi.complex.dao;

import java.util.Map;

import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface AccountUserBuyDao extends SimpleDao<AccountUserBuy, Long> {

	public void deleteByAccountBuyId(Map<String, Object> map);
	
	public Number sumPrice(Object parameter);
}
