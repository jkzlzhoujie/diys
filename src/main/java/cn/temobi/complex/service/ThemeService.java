package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.ThemeDao;
import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.Theme;
import cn.temobi.complex.entity.ThemeUseCount;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 分类管理
 * @author hjf
 * 2014 三月 17 17:17:17
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("themeService")
public class ThemeService extends ServiceBase{
	
	@Resource(name = "themeDao")
	private ThemeDao themeDao;

	public int update(Theme entity){
		return themeDao.update(entity);
	}
	
	public Page<Theme> findByPage(Page page,Object map){
		return themeDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return themeDao.getCount(map);
	}
	
	public Number sumLove(Map map){
		return themeDao.sumLove(map);
	}
	
	public Theme getById(Long id){
		return themeDao.getById(id);
	}
	
	public int save(Theme entity){
		return themeDao.save(entity);
	}
	
	public int delete(Object id){
		return themeDao.delete(id);
	}
	
	public int delete(Theme entity){
		return themeDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return themeDao.findByMap(map);
	}
    
	public List<Theme> findAll(){
		return themeDao.findAll();
	}
	
	public List<Theme> findAll(Theme entity){
		return themeDao.findAll(entity);
	}
}
