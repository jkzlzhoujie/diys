package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.MaterialDao;
import cn.temobi.complex.entity.Material;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 资源包管理
 * @author hjf
 * 2014 三月 17 17:27:15
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("materialService")
public class MaterialService extends ServiceBase{
	
	@Resource(name = "materialDao")
	private MaterialDao materialDao;
	
	public int update(Material entity){
		return materialDao.update(entity);
	}
	
	public Page<Material> findByPage(Page page,Object map){
		return materialDao.findByPage(page, map);
	}
	
	public Page<Material> findByPage2(Page page,Object map){
		return materialDao.findByPage(page, "findByPage2", map);
	}
	
	public Number getCount(Map map){
		return materialDao.getCount(map);
	}
	
	public Material getById(Long id){
		return materialDao.getById(id);
	}
	
	public int save(Material entity){
		return materialDao.save(entity);
	}
	
	public int delete(Object id){
		return materialDao.delete(id);
	}
	
	public int delete(Material entity){
		return materialDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return materialDao.findByMap(map);
	}
	
	public List findUseByMap(Map map){
		return materialDao.findUseByMap(map);
	}
    
	public List<Material> findAll(){
		return materialDao.findAll();
	}
	
	public List<Material> findAll(Material entity){
		return materialDao.findAll(entity);
	}
	
}
