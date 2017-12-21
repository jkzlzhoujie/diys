package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmCommodityDao;
import cn.temobi.complex.entity.PmCommodity;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmCommodityService")
public class PmCommodityService extends ServiceBase{
	
	@Resource(name = "pmCommodityDao")
	private PmCommodityDao pmCommodityDao;
	
	public int update(PmCommodity entity){
		return pmCommodityDao.update(entity);
	}
	
	public Page<PmCommodity> findByPage(Page page,Object map){
		return pmCommodityDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmCommodityDao.getCount(map);
	}
	
	public PmCommodity getById(Long id){
		return pmCommodityDao.getById(id);
	}
	
	public int save(PmCommodity entity){
		return pmCommodityDao.save(entity);
	}
	
	public int delete(Object id){
		return pmCommodityDao.delete(id);
	}
	
	public int delete(PmCommodity entity){
		return pmCommodityDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmCommodityDao.findByMap(map);
	}
    
	public List<PmCommodity> findAll(){
		return pmCommodityDao.findAll();
	}
	
	public List<PmCommodity> findAll(PmCommodity entity){
		return pmCommodityDao.findAll(entity);
	}
}
