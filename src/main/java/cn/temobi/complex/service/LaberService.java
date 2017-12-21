package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.LaberDao;
import cn.temobi.complex.entity.Laber;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 分类管理
 * @author hjf
 * 2014 三月 17 17:17:17
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("laberService")
public class LaberService extends ServiceBase{
	
	@Resource(name = "laberDao")
	private LaberDao laberDao;
	
	public int update(Laber entity){
		return laberDao.update(entity);
	}
	
	public Page<Laber> findByPage(Page page,Object map){
		return laberDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return laberDao.getCount(map);
	}
	public Laber getById(Long id){
		return laberDao.getById(id);
	}
	
	public int save(Laber entity){
		return laberDao.save(entity);
	}
	
	public int delete(Object id){
		return laberDao.delete(id);
	}
	
	public int delete(Laber entity){
		return laberDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return laberDao.findByMap(map);
	}
	
	public List findRand(Map map){
		return laberDao.findRand(map);
	}
    
    
	public List<Laber> findAll(){
		return laberDao.findAll();
	}
	
	public List<Laber> findAll(Laber entity){
		return laberDao.findAll(entity);
	}
}
