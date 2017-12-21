package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.Order;
import cn.temobi.core.dao.SimpleDao;
import cn.temobi.core.util.MybatisStatementUtils;

public interface OrderDao extends SimpleDao<Order,Long>{
	
	public Number getSum(Object parameter);
	
	public List findByMap2(Map map);
}
