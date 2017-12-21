package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.AccountUserBuyDao;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("accountUserBuyService")
public class AccountUserBuyService extends ServiceBase{
	
	@Resource(name = "accountUserBuyDao")
	private AccountUserBuyDao accountUserBuyDao;
	
	public Number sumPrice(Map map){
		return accountUserBuyDao.sumPrice(map);
	} 
	
	public int update(AccountUserBuy entity){
		return accountUserBuyDao.update(entity);
	}
	
	public Page<AccountUserBuy> findByPage(Page page,Object map){
		return accountUserBuyDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return accountUserBuyDao.getCount(map);
	}
	
	public AccountUserBuy getById(Long id){
		return accountUserBuyDao.getById(id);
	}
	
	public int save(AccountUserBuy entity){
		return accountUserBuyDao.save(entity);
	}
	
	public int delete(Object id){
		return accountUserBuyDao.delete(id);
	}
	
	public int delete(AccountUserBuy entity){
		return accountUserBuyDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return accountUserBuyDao.findByMap(map);
	}
    
	public List<AccountUserBuy> findAll(){
		return accountUserBuyDao.findAll();
	}
	
	public List<AccountUserBuy> findAll(AccountUserBuy entity){
		return accountUserBuyDao.findAll(entity);
	}
	
	
}
