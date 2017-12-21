package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SystemSetingDao;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 分类管理
 * @author hjf
 * 2014 三月 17 17:17:17
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("systemSetingService")
public class SystemSetingService extends ServiceBase{
	
	@Resource(name = "systemSetingDao")
	private SystemSetingDao systemSetingDao;
	
	public int update(SystemSeting entity){
		return systemSetingDao.update(entity);
	}
	
	public Page<SystemSeting> findByPage(Page page,Object map){
		return systemSetingDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return systemSetingDao.getCount(map);
	}
	
	public SystemSeting getById(Long id){
		return systemSetingDao.getById(id);
	}
	
	public int save(SystemSeting entity){
		return systemSetingDao.save(entity);
	}
	
	public int delete(Object id){
		return systemSetingDao.delete(id);
	}
	
	public int delete(System entity){
		return systemSetingDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return systemSetingDao.findByMap(map);
	}
    
	public List<SystemSeting> findAll(){
		return systemSetingDao.findAll();
	}
	
	public List<SystemSeting> findAll(SystemSeting entity){
		return systemSetingDao.findAll(entity);
	}
}
