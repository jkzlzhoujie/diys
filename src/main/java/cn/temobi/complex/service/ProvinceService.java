package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.ProvinceDao;
import cn.temobi.complex.entity.Province;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 号码归属地管理
 * @author hjf
 * 2014 三月 28 10:34:48
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("provinceService")
public class ProvinceService extends ServiceBase{
	
	@Resource(name = "provinceDao")
	private ProvinceDao provinceDao;
	
	public Page<Province> findByPage(Page page,Object map){
		return provinceDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return provinceDao.getCount(map);
	}
	
	public Province getById(Long id){
		return provinceDao.getById(id);
	}
	
	public List findByMap(Map map){
		return provinceDao.findByMap(map);
	}
    
	public List<Province> findAll(){
		return provinceDao.findAll();
	}
	
	public List<Province> findAll(Province entity){
		return provinceDao.findAll(entity);
	}
	
	public Province getBySegment(String segment) {
		return provinceDao.getBySegment(segment);
	}
}
