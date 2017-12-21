package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmProductPsDao;
import cn.temobi.complex.entity.PmProductPs;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmProductPsService")
public class PmProductPsService extends ServiceBase{
	
	@Resource(name = "pmProductPsDao")
	private PmProductPsDao pmProductPsDao;
	
	public int update(PmProductPs entity){
		return pmProductPsDao.update(entity);
	}
	
	public Page<PmProductPs> findByPage(Page page,Object map){
		return pmProductPsDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmProductPsDao.getCount(map);
	}
	
	public PmProductPs getById(Long id){
		return pmProductPsDao.getById(id);
	}
	
	public int save(PmProductPs entity){
		return pmProductPsDao.save(entity);
	}
	
	public int delete(Object id){
		return pmProductPsDao.delete(id);
	}
	
	public int delete(PmProductPs entity){
		return pmProductPsDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmProductPsDao.findByMap(map);
	}
    
	public List<PmProductPs> findAll(){
		return pmProductPsDao.findAll();
	}
	
	public List<PmProductPs> findAll(PmProductPs entity){
		return pmProductPsDao.findAll(entity);
	}
}
