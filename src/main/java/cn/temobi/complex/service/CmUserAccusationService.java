package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserAccusationDao;
import cn.temobi.complex.entity.CmUserAccusation;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserAccusationService")
public class CmUserAccusationService extends ServiceBase{
	
	@Resource(name = "cmUserAccusationDao")
	private CmUserAccusationDao cmUserAccusationDao;
	
	public int update(CmUserAccusation entity){
		return cmUserAccusationDao.update(entity);
	}
	
	public Page<CmUserAccusation> findByPage(Page page,Object map){
		return cmUserAccusationDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserAccusationDao.getCount(map);
	}
	
	public CmUserAccusation getById(Long id){
		return cmUserAccusationDao.getById(id);
	}
	
	public int save(CmUserAccusation entity){
		return cmUserAccusationDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserAccusationDao.delete(id);
	}
	
	public int delete(CmUserAccusation entity){
		return cmUserAccusationDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserAccusationDao.findByMap(map);
	}
    
	public List<CmUserAccusation> findAll(){
		return cmUserAccusationDao.findAll();
	}
	
	public List<CmUserAccusation> findAll(CmUserAccusation entity){
		return cmUserAccusationDao.findAll(entity);
	}
}
