package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysTypeTimeDao;
import cn.temobi.complex.entity.SysTypeTime;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysTypeTimeService")
public class SysTypeTimeService extends ServiceBase{
	
	@Resource(name = "sysTypeTimeDao")
	private SysTypeTimeDao sysTypeTimeDao;
	
	public int update(SysTypeTime entity){
		return sysTypeTimeDao.update(entity);
	}
	
	public Page<SysTypeTime> findByPage(Page page,Object map){
		return sysTypeTimeDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return sysTypeTimeDao.getCount(map);
	}
	
	public SysTypeTime getById(Long id){
		return sysTypeTimeDao.getById(id);
	}
	
	public int save(SysTypeTime entity){
		return sysTypeTimeDao.save(entity);
	}
	
	public int delete(Object id){
		return sysTypeTimeDao.delete(id);
	}
	
	public int delete(SysTypeTime entity){
		return sysTypeTimeDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return sysTypeTimeDao.findByMap(map);
	}
    
	public List<SysTypeTime> findAll(){
		return sysTypeTimeDao.findAll();
	}
	
	public List<SysTypeTime> findAll(SysTypeTime entity){
		return sysTypeTimeDao.findAll(entity);
	}
}
