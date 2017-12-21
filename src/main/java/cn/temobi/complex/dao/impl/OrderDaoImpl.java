package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.OrderDao;
import cn.temobi.complex.dto.PmProductDiscussDto;
import cn.temobi.complex.entity.Order;
import cn.temobi.core.common.SimpleMybatisSupport;
import cn.temobi.core.util.MybatisStatementUtils;

@Component
@Repository("orderDao")
public class OrderDaoImpl extends SimpleMybatisSupport<Order,Long> implements OrderDao{
	
	@Override
	public String getMybatisMapperNamesapce(){
		return "cn.temobi.complex.entity.Order";
	}
	
	@Override
    public Number getSum(Object parameter) {
        return (Number) getSqlSession().selectOne(toMybatisStatement("sum"), toParameterMap(parameter));
    }

	@Override
	public List findByMap2(Map map) {
		return this.getSqlSession().selectList(toMybatisStatement("findByMap2"), map);
	}
}
