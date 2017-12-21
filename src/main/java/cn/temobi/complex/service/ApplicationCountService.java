package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.ApplicationCountDao;
import cn.temobi.complex.dao.ApplicationDao;
import cn.temobi.complex.entity.Application;
import cn.temobi.complex.entity.ApplicationCount;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("applicationCountService")
public class ApplicationCountService extends ServiceBase{
	
	@Resource(name = "applicationCountDao")
	private ApplicationCountDao applicationCountDao;
	
	@Resource(name = "applicationDao")
	private ApplicationDao applicationDao;
	
	public int update(ApplicationCount entity){
		return applicationCountDao.update(entity);
	}
	
	public Page<ApplicationCount> findByPage(Page page,Object map){
		return applicationCountDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return applicationCountDao.getCount(map);
	}
	
	public ApplicationCount getById(Long id){
		return applicationCountDao.getById(id);
	}
	
	public int save(ApplicationCount entity){
		return applicationCountDao.save(entity);
	}
	
	public int delete(Object id){
		return applicationCountDao.delete(id);
	}
	
	public int delete(ApplicationCount entity){
		return applicationCountDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return applicationCountDao.findByMap(map);
	}
    
	public List<ApplicationCount> findAll(){
		return applicationCountDao.findAll();
	}
	
	public List<ApplicationCount> findAll(ApplicationCount entity){
		return applicationCountDao.findAll(entity);
	}
	
	public void saveAll(ApplicationCount applicationCount,Application application){
		applicationCount.setType("1");
		applicationCountDao.save(applicationCount);
		applicationDao.update(application);
	}
}
