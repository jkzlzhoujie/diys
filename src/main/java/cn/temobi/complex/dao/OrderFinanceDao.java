package cn.temobi.complex.dao;

import cn.temobi.complex.entity.OrderFinance;
import cn.temobi.core.dao.SimpleDao;

public interface OrderFinanceDao extends SimpleDao<OrderFinance,Long>{

	public Number getSum(Object parameter);
}
