package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.LaberSearchDao;
import cn.temobi.complex.entity.LaberSearch;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 分类管理
 * @author hjf
 * 2014 三月 17 17:17:17
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("laberSearchService")
public class LaberSearchService extends ServiceBase{
	
	@Resource(name = "laberSearchDao")
	private LaberSearchDao laberSearchDao;
	
	public int update(LaberSearch entity){
		return laberSearchDao.update(entity);
	}
	
	public Page<LaberSearch> findByPage(Page page,Object map){
		return laberSearchDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return laberSearchDao.getCount(map);
	}
	
	public LaberSearch getById(Long id){
		return laberSearchDao.getById(id);
	}
	
	public int save(LaberSearch entity){
		return laberSearchDao.save(entity);
	}
	
	public int delete(Object id){
		return laberSearchDao.delete(id);
	}
	
	public int delete(LaberSearch entity){
		return laberSearchDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return laberSearchDao.findByMap(map);
	}
    
	public List<LaberSearch> findAll(){
		return laberSearchDao.findAll();
	}
	
	public List<LaberSearch> findAll(LaberSearch entity){
		return laberSearchDao.findAll(entity);
	}
}
