package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserSignDao;
import cn.temobi.complex.entity.CmUserSign;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserSignService")
public class CmUserSignService extends ServiceBase{
	
	@Resource(name = "cmUserSignDao")
	private CmUserSignDao cmUserSignDao;
	
	public int update(CmUserSign entity){
		return cmUserSignDao.update(entity);
	}
	
	public Page<CmUserSign> findByPage(Page page,Object map){
		return cmUserSignDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserSignDao.getCount(map);
	}
	
	public CmUserSign getById(Long id){
		return cmUserSignDao.getById(id);
	}
	
	public int save(CmUserSign entity){
		return cmUserSignDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserSignDao.delete(id);
	}
	
	public int delete(CmUserSign entity){
		return cmUserSignDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserSignDao.findByMap(map);
	}
    
	public List<CmUserSign> findAll(){
		return cmUserSignDao.findAll();
	}
	
	public List<CmUserSign> findAll(CmUserSign entity){
		return cmUserSignDao.findAll(entity);
	}
}
