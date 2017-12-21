package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmBusiScopeDao;
import cn.temobi.complex.entity.CmBusiScope;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmBusiScopeService")
public class CmBusiScopeService extends ServiceBase{
	
	@Resource(name = "cmBusiScopeDao")
	private CmBusiScopeDao cmBusiScopeDao;
	
	public int update(CmBusiScope entity){
		return cmBusiScopeDao.update(entity);
	}
	
	public Page<CmBusiScope> findByPage(Page page,Object map){
		return cmBusiScopeDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmBusiScopeDao.getCount(map);
	}
	
	public CmBusiScope getById(Long id){
		return cmBusiScopeDao.getById(id);
	}
	
	public int save(CmBusiScope entity){
		return cmBusiScopeDao.save(entity);
	}
	
	public int delete(Object id){
		return cmBusiScopeDao.delete(id);
	}
	
	public int delete(CmBusiScope entity){
		return cmBusiScopeDao.delete(entity);
	}
	

	public void deleteByUserId(Map map){
		cmBusiScopeDao.deleteByUserId(map);
	}
	
	public List findByMap(Map map){
		return cmBusiScopeDao.findByMap(map);
	}
    
	public List<CmBusiScope> findAll(){
		return cmBusiScopeDao.findAll();
	}
	
	public List<CmBusiScope> findAll(CmBusiScope entity){
		return cmBusiScopeDao.findAll(entity);
	}
}
