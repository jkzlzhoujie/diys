package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmTopicPsProductsDao;
import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.dto.PmTopicPsProductsDto;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmTopicPsProductsService")
public class PmTopicPsProductsService extends ServiceBase{
	
	@Resource(name = "pmTopicPsProductsDao")
	private PmTopicPsProductsDao pmTopicPsProductsDao;
	
	public int update(PmTopicPsProducts entity){
		return pmTopicPsProductsDao.update(entity);
	}
	
	public Page<PmTopicPsProducts> findByPage(Page page,Object map){
		return pmTopicPsProductsDao.findByPage(page, map);
	}
	
	public Page<PmTopicPsProducts> findByPageTwo(Page page,Object map){
		return pmTopicPsProductsDao.findByPageTwo(page, map);
	}
	
	public Page<PmTopicPsProducts> findIndex(Page page,Object map){
		return pmTopicPsProductsDao.findIndex(page, map);
	}
	
	public List<PmTopicPsProducts> findMapIndex(Object map){
		return pmTopicPsProductsDao.findMapIndex(map);
	}
	
	public Number getCount(Map map){
		return pmTopicPsProductsDao.getCount(map);
	}
	
	public PmTopicPsProducts getById(Long id){
		return pmTopicPsProductsDao.getById(id);
	}
	
	public int save(PmTopicPsProducts entity){
		return pmTopicPsProductsDao.save(entity);
	}
	
	public int delete(Object id){
		return pmTopicPsProductsDao.delete(id);
	}
	
	public int delete(PmTopicPsProducts entity){
		return pmTopicPsProductsDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmTopicPsProductsDao.findByMap(map);
	}
    
	public List<PmTopicPsProducts> findAll(){
		return pmTopicPsProductsDao.findAll();
	}
	
	public List<PmTopicPsProducts> findAll(PmTopicPsProducts entity){
		return pmTopicPsProductsDao.findAll(entity);
	}
	
	public PIndexDto countPs(Map map){
		return pmTopicPsProductsDao.countPs(map);
	}
}
