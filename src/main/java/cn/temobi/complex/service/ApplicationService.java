package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.ApplicationDao;
import cn.temobi.complex.entity.Application;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("applicationService")
public class ApplicationService extends ServiceBase{
	
	@Resource(name = "applicationDao")
	private ApplicationDao applicationDao;
	
	public int update(Application entity){
		return applicationDao.update(entity);
	}
	
	public Page<Application> findByPage(Page page,Object map){
		return applicationDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return applicationDao.getCount(map);
	}
	
	public Application getById(Long id){
		return applicationDao.getById(id);
	}
	
	public int save(Application entity){
		return applicationDao.save(entity);
	}
	
	public int delete(Object id){
		return applicationDao.delete(id);
	}
	
	public int delete(Application entity){
		return applicationDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return applicationDao.findByMap(map);
	}
    
	public List<Application> findAll(){
		return applicationDao.findAll();
	}
	
	public List<Application> findAll(Application entity){
		return applicationDao.findAll(entity);
	}
}
