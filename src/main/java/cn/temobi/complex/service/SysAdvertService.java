package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.SysAdvertDao;
import cn.temobi.complex.entity.SysAdvert;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysAdvertService")
public class SysAdvertService extends ServiceBase{


	@Resource(name = "sysAdvertDao")
	private SysAdvertDao sysAdvertDao;
	
	public int update(SysAdvert entity){
		return sysAdvertDao.update(entity);
	}
	
	public Page<SysAdvert> findByPage(Page page,Object map){
		return sysAdvertDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return sysAdvertDao.getCount(map);
	}
	
	public SysAdvert getById(Long id){
		return sysAdvertDao.getById(id);
	}
	
	public int save(SysAdvert entity){
		return sysAdvertDao.save(entity);
	}
	
	public int delete(Object id){
		return sysAdvertDao.delete(id);
	}
	
	public int delete(SysAdvert entity){
		return sysAdvertDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return sysAdvertDao.findByMap(map);
	}
    
	public List<SysAdvert> findAll(){
		return sysAdvertDao.findAll();
	}
	
	public List<SysAdvert> findAll(SysAdvert entity){
		return sysAdvertDao.findAll(entity);
	}
}
