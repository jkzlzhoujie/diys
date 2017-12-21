package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.MaterialResourceDao;
import cn.temobi.complex.entity.MaterialResource;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 客户端下载统计管理
 * @author hjf
 * 2014 三月 26 09:40:28
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("materialResourceService")
public class MaterialResourceService extends ServiceBase{
	
	@Resource(name = "materialResourceDao")
	private MaterialResourceDao materialResourceDao;
	
	public int update(MaterialResource entity){
		return materialResourceDao.update(entity);
	}
	
	public int save(MaterialResource entity){
		return materialResourceDao.save(entity);
	}
	
	public Page<MaterialResource> findByPageTo(Page page,Object map){
		return materialResourceDao.findByPageTo(page, map);
	}
	
	public Page<MaterialResource> findByPage(Page page,Object map){
		return materialResourceDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return materialResourceDao.getCount(map);
	}
	
	public MaterialResource getById(Long id){
		return materialResourceDao.getById(id);
	}
	
	
	public int delete(Object id){
		return materialResourceDao.delete(id);
	}
	
	public int delete(MaterialResource entity){
		return materialResourceDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return materialResourceDao.findByMap(map);
	}
    
	public List<MaterialResource> findAll(){
		return materialResourceDao.findAll();
	}
	
	public List<MaterialResource> findAll(MaterialResource entity){
		return materialResourceDao.findAll(entity);
	}
	
	public Number maxId(Map map){
		return materialResourceDao.maxId(map);
	}
}
