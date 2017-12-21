package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.OrderFinanceDao;
import cn.temobi.complex.dto.OrderFinanceDto;
import cn.temobi.complex.entity.OrderFinance;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({"serial","unchecked"})
@Transactional
@Service("orderFinanceService")
public class OrderFinanceService extends ServiceBase{
	
	@Resource(name="orderFinanceDao")
	private OrderFinanceDao orderFinanceDao;
	
	public Number getSum(Map map){
		return orderFinanceDao.getSum(map);
	} 
	
	public Number getCount(Map map){
		return orderFinanceDao.getCount(map);
	}
	
	public int update(OrderFinance entity){
		return orderFinanceDao.update(entity);
	}
	
	public Page<OrderFinanceDto> findByPage(Page page,Object map){
		return orderFinanceDao.findByPage(page, map);
	}
	
	public OrderFinance getById(Long id){
		return orderFinanceDao.getById(id);
	}
	
	public int save(OrderFinance entity){
		return orderFinanceDao.save(entity);
	}
	
	public int delete(Object id){
		return orderFinanceDao.delete(id);
	}
	
	public int delete(OrderFinance entity){
		return orderFinanceDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return orderFinanceDao.findByMap(map);
	}
	
	
	public List<OrderFinance> findAll(){
		return orderFinanceDao.findAll();
	}
	
	public List<OrderFinance> findAll(OrderFinance entity){
		return orderFinanceDao.findAll(entity);
	}
}
