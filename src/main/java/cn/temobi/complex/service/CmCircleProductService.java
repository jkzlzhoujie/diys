package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmCircleProductDao;
import cn.temobi.complex.entity.CmCircleProduct;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmCircleProductService")
public class CmCircleProductService extends ServiceBase{

	@Resource(name = "cmCircleProductDao")
	private CmCircleProductDao cmCircleProductDao;
	
	public void upByCircleId(Map map){
		cmCircleProductDao.upByCircleId(map);
	}
	
	public Long delBycircleId(Map map){
		return cmCircleProductDao.delBycircleId(map);
	}
	
	public Long check(Map map){
		return cmCircleProductDao.check(map);
	} 
	
	public int update(CmCircleProduct entity){
		return cmCircleProductDao.update(entity);
	}
	
	public Page<CmCircleProduct> findByPage(Page page,Object map){
		return cmCircleProductDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmCircleProductDao.getCount(map);
	}
	
	public CmCircleProduct getById(Long id){
		return cmCircleProductDao.getById(id);
	}
	
	public int save(CmCircleProduct entity){
		return cmCircleProductDao.save(entity);
	}
	
	public void insertList(Map<String,Object> map){
		 cmCircleProductDao.insertList(map);
	}
	
	public int delete(Object id){
		return cmCircleProductDao.delete(id);
	}
	
	public int delete(CmCircleProduct entity){
		return cmCircleProductDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmCircleProductDao.findByMap(map);
	}
	
	public List<CmCircleProduct> findAll(){
		return cmCircleProductDao.findAll();
	}
	
	public Page<PmProductInfo> findPruduct(Page page,Object map){
		return cmCircleProductDao.findPruduct(page, map);
	}
	
	public Number countPruduct(Map map){
		return cmCircleProductDao.countPruduct(map);
	}
	
	public List<Long> findProductId(Object map){
		return cmCircleProductDao.findProductId(map);
	}

	public Page findByPageTwo(Page page, Map<String, String> map) {
		return cmCircleProductDao.findByPageTwo(page, map);
	}
	
}
