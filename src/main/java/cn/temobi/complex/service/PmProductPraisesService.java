package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmProductPraisesDao;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmProductPraisesService")
public class PmProductPraisesService extends ServiceBase{
	
	@Resource(name = "pmProductPraisesDao")
	private PmProductPraisesDao pmProductPraisesDao;
	
	public int update(PmProductPraises entity){
		return pmProductPraisesDao.update(entity);
	}
	
	public Page<PmProductPraises> findByPage(Page page,Object map){
		return pmProductPraisesDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmProductPraisesDao.getCount(map);
	}
	
	public PmProductPraises getById(Long id){
		return pmProductPraisesDao.getById(id);
	}
	
	public int save(PmProductPraises entity){
		return pmProductPraisesDao.save(entity);
	}
	
	public int delete(Object id){
		return pmProductPraisesDao.delete(id);
	}
	
	public int delete(PmProductPraises entity){
		return pmProductPraisesDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmProductPraisesDao.findByMap(map);
	}
    
	public List<PmProductPraises> findAll(){
		return pmProductPraisesDao.findAll();
	}
	
	public List<PmProductPraises> findAll(PmProductPraises entity){
		return pmProductPraisesDao.findAll(entity);
	}
	
	public List<Long> findIdList(Map<String,Object> map){
		return pmProductPraisesDao.findIdList(map);
	}
}
