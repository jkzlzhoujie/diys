package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.WxDiyDao;
import cn.temobi.complex.entity.WxDiy;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("wxDiyService")
public class WxDiyService extends ServiceBase{
	
	@Resource(name = "wxDiyDao")
	private WxDiyDao wxDiyDao;
	
	public int update(WxDiy entity){
		return wxDiyDao.update(entity);
	}
	
	public Page<WxDiy> findByPage(Page page,Object map){
		return wxDiyDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return wxDiyDao.getCount(map);
	}
	
	public WxDiy getById(Long id){
		return wxDiyDao.getById(id);
	}
	
	public int save(WxDiy entity){
		return wxDiyDao.save(entity);
	}
	
	public int delete(Object id){
		return wxDiyDao.delete(id);
	}
	
	public int delete(WxDiy entity){
		return wxDiyDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return wxDiyDao.findByMap(map);
	}
    
	public List<WxDiy> findAll(){
		return wxDiyDao.findAll();
	}
	
	public List<WxDiy> findAll(WxDiy entity){
		return wxDiyDao.findAll(entity);
	}
}
