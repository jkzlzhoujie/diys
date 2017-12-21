package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysColumnDao;
import cn.temobi.complex.entity.SysColumn;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysColumnService")
public class SysColumnService extends ServiceBase{
	
	@Resource(name = "sysColumnDao")
	private SysColumnDao sysColumnDao;
	
	public int update(SysColumn entity){
		return sysColumnDao.update(entity);
	}
	
	public Page<SysColumn> findByPage(Page page,Object map){
		return sysColumnDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return sysColumnDao.getCount(map);
	}
	
	public SysColumn getById(Long id){
		return sysColumnDao.getById(id);
	}
	
	public int save(SysColumn entity){
		return sysColumnDao.save(entity);
	}
	
	public int delete(Object id){
		return sysColumnDao.delete(id);
	}
	
	public int delete(SysColumn entity){
		return sysColumnDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return sysColumnDao.findByMap(map);
	}
    
	public List<SysColumn> findAll(){
		return sysColumnDao.findAll();
	}
	
	public List<SysColumn> findAll(SysColumn entity){
		return sysColumnDao.findAll(entity);
	}
}
