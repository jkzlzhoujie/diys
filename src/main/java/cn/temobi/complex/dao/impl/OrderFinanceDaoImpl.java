package cn.temobi.complex.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.OrderFinanceDao;
import cn.temobi.complex.entity.OrderFinance;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("orderFinanceDao")
public class OrderFinanceDaoImpl extends SimpleMybatisSupport<OrderFinance,Long> implements OrderFinanceDao{
	
	@Override
	public String getMybatisMapperNamesapce(){
		return "cn.temobi.complex.entity.OrderFinance";
	}
	
	@Override
    public Number getSum(Object parameter) {
        return (Number) getSqlSession().selectOne(toMybatisStatement("sum"), toParameterMap(parameter));
    }
	
}
