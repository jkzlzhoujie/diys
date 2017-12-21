package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysTypeDao;
import cn.temobi.complex.entity.SysType;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysTypeService")
public class SysTypeService extends ServiceBase{
	
	@Resource(name = "sysTypeDao")
	private SysTypeDao sysTypeDao;
	
	public int update(SysType entity){
		return sysTypeDao.update(entity);
	}
	
	public Page<SysType> findByPage(Page page,Object map){
		return sysTypeDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return sysTypeDao.getCount(map);
	}
	
	public SysType getById(Long id){
		return sysTypeDao.getById(id);
	}
	
	public int save(SysType entity){
		return sysTypeDao.save(entity);
	}
	
	public int delete(Object id){
		return sysTypeDao.delete(id);
	}
	
	public int delete(SysType entity){
		return sysTypeDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return sysTypeDao.findByMap(map);
	}
    
	public List<SysType> findAll(){
		return sysTypeDao.findAll();
	}
	
	public List<SysType> findAll(SysType entity){
		return sysTypeDao.findAll(entity);
	}
}
