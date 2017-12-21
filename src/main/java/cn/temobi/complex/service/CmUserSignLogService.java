package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserSignLogDao;
import cn.temobi.complex.entity.CmUserSignLog;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserSignLogService")
public class CmUserSignLogService extends ServiceBase{
	
	@Resource(name = "cmUserSignLogDao")
	private CmUserSignLogDao cmUserSignLogDao;
	
	public int update(CmUserSignLog entity){
		return cmUserSignLogDao.update(entity);
	}
	
	public Page<CmUserSignLog> findByPage(Page page,Object map){
		return cmUserSignLogDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserSignLogDao.getCount(map);
	}
	
	public CmUserSignLog getById(Long id){
		return cmUserSignLogDao.getById(id);
	}
	
	public int save(CmUserSignLog entity){
		return cmUserSignLogDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserSignLogDao.delete(id);
	}
	
	public int delete(CmUserSignLog entity){
		return cmUserSignLogDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserSignLogDao.findByMap(map);
	}
    
	public List<CmUserSignLog> findAll(){
		return cmUserSignLogDao.findAll();
	}
	
	public List<CmUserSignLog> findAll(CmUserSignLog entity){
		return cmUserSignLogDao.findAll(entity);
	}
}
