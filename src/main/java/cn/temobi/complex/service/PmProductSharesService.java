package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmProductSharesDao;
import cn.temobi.complex.entity.PmProductShares;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmProductSharesService")
public class PmProductSharesService extends ServiceBase{
	
	@Resource(name = "pmProductSharesDao")
	private PmProductSharesDao pmProductSharesDao;
	
	public int update(PmProductShares entity){
		return pmProductSharesDao.update(entity);
	}
	
	public Page<PmProductShares> findByPage(Page page,Object map){
		return pmProductSharesDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmProductSharesDao.getCount(map);
	}
	
	public PmProductShares getById(Long id){
		return pmProductSharesDao.getById(id);
	}
	
	public int save(PmProductShares entity){
		return pmProductSharesDao.save(entity);
	}
	
	public int delete(Object id){
		return pmProductSharesDao.delete(id);
	}
	
	public int delete(PmProductShares entity){
		return pmProductSharesDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmProductSharesDao.findByMap(map);
	}
    
	public List<PmProductShares> findAll(){
		return pmProductSharesDao.findAll();
	}
	
	public List<PmProductShares> findAll(PmProductShares entity){
		return pmProductSharesDao.findAll(entity);
	}
}
