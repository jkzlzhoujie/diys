package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.ClassifyDao;
import cn.temobi.complex.dao.ThemeDao;
import cn.temobi.complex.dao.ThemeLoveDao;
import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.Theme;
import cn.temobi.complex.entity.ThemeLove;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 分类管理
 * @author hjf
 * 2014 三月 17 17:17:17
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("themeLoveService")
public class ThemeLoveService extends ServiceBase{
	
	@Resource(name = "themeLoveDao")
	private ThemeLoveDao themeLoveDao;
	
	@Resource(name = "themeDao")
	private ThemeDao themeDao;
	
	@Resource(name = "classifyDao")
	private ClassifyDao classifyDao;
	
	public int update(ThemeLove entity){
		return themeLoveDao.update(entity);
	}
	
	public Page<ThemeLove> findByPage(Page page,Object map){
		return themeLoveDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return themeLoveDao.getCount(map);
	}
	
	public ThemeLove getById(Long id){
		return themeLoveDao.getById(id);
	}
	
	public int save(ThemeLove entity){
		return themeLoveDao.save(entity);
	}
	
	public int delete(Object id){
		return themeLoveDao.delete(id);
	}
	
	public int delete(ThemeLove entity){
		return themeLoveDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return themeLoveDao.findByMap(map);
	}
    
	public List<ThemeLove> findAll(){
		return themeLoveDao.findAll();
	}
	
	public List<ThemeLove> findAll(ThemeLove entity){
		return themeLoveDao.findAll(entity);
	}
	
	public void saveAll(ThemeLove themeLove){
    	themeLoveDao.save(themeLove);
    	Theme theme = themeDao.getById(themeLove.getThemeId());
    	theme.setLoveNum(theme.getLoveNum()+1);
    	themeDao.update(theme);
    	Classify classify = classifyDao.getById(theme.getClassifyId());
    	classify.setLoveNum(classify.getLoveNum()+1);
    	classifyDao.update(classify);
	}
}
