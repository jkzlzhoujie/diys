package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.LaberConfigureDao;
import cn.temobi.complex.entity.LaberConfigure;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 分类管理
 * @author hjf
 * 2014 三月 17 17:17:17
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("laberConfigureService")
public class LaberConfigureService extends ServiceBase{
	
	@Resource(name = "laberConfigureDao")
	private LaberConfigureDao laberConfigureDao;
	
	public int update(LaberConfigure entity){
		return laberConfigureDao.update(entity);
	}
	
	public Page<LaberConfigure> findByPage(Page page,Object map){
		return laberConfigureDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return laberConfigureDao.getCount(map);
	}
	public LaberConfigure getById(Long id){
		return laberConfigureDao.getById(id);
	}
	
	public int save(LaberConfigure entity){
		return laberConfigureDao.save(entity);
	}
	
	public int delete(Object id){
		return laberConfigureDao.delete(id);
	}
	
	public int delete(LaberConfigure entity){
		return laberConfigureDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return laberConfigureDao.findByMap(map);
	}
    
	public List<LaberConfigure> findAll(){
		return laberConfigureDao.findAll();
	}
	
	public List<LaberConfigure> findAll(LaberConfigure entity){
		return laberConfigureDao.findAll(entity);
	}
}
