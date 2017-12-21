package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.BusinessCountDao;
import cn.temobi.complex.entity.BusinessCount;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("businessCountService")
public class BusinessCountService extends ServiceBase{
	
	@Resource(name = "businessCountDao")
	private BusinessCountDao businessCountDao;
	
	public int update(BusinessCount entity){
		return businessCountDao.update(entity);
	}
	
	public Page<BusinessCount> findByPage(Page page,Object map){
		return businessCountDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return businessCountDao.getCount(map);
	}
	
	public BusinessCount getById(Long id){
		return businessCountDao.getById(id);
	}
	
	public int save(BusinessCount entity){
		return businessCountDao.save(entity);
	}
	
	public int delete(Object id){
		return businessCountDao.delete(id);
	}
	
	public int delete(BusinessCount entity){
		return businessCountDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return businessCountDao.findByMap(map);
	}
    
	public List<BusinessCount> findAll(){
		return businessCountDao.findAll();
	}
	
	public List<BusinessCount> findAll(BusinessCount entity){
		return businessCountDao.findAll(entity);
	}
}
