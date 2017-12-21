package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.AccountUserLogDao;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("accountUserLogService")
public class AccountUserLogService extends ServiceBase{
	
	@Resource(name = "accountUserLogDao")
	private AccountUserLogDao accountUserLogDao;
	
	public int update(AccountUserLog entity){
		return accountUserLogDao.update(entity);
	}
	
	public Page<AccountUserLog> findByPage(Page page,Object map){
		return accountUserLogDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return accountUserLogDao.getCount(map);
	}
	
	public AccountUserLog getById(Long id){
		return accountUserLogDao.getById(id);
	}
	
	public int save(AccountUserLog entity){
		return accountUserLogDao.save(entity);
	}
	
	public int delete(Object id){
		return accountUserLogDao.delete(id);
	}
	
	public int delete(AccountUserLog entity){
		return accountUserLogDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return accountUserLogDao.findByMap(map);
	}
    
	public List<AccountUserLog> findAll(){
		return accountUserLogDao.findAll();
	}
	
	public List<AccountUserLog> findAll(AccountUserLog entity){
		return accountUserLogDao.findAll(entity);
	}
	
	
	
}
