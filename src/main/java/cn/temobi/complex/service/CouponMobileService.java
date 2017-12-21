package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.CouponMobileDao;
import cn.temobi.complex.entity.CouponMobile;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("couponMobileService")
public class CouponMobileService extends ServiceBase{
	
	@Resource(name = "couponMobileDao")
	private CouponMobileDao couponMobileDao;

	public int update(CouponMobile entity){
		return couponMobileDao.update(entity);
	}
	
	public Page<CouponMobile> findByPage(Page page,Object map){
		return couponMobileDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return couponMobileDao.getCount(map);
	}
	
	public CouponMobile getById(Long id){
		return couponMobileDao.getById(id);
	}
	
	public int save(CouponMobile entity){
		return couponMobileDao.save(entity);
	}
	
	public int delete(Object id){
		return couponMobileDao.delete(id);
	}
	
	public int delete(CouponMobile entity){
		return couponMobileDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return couponMobileDao.findByMap(map);
	}
    
	public List<CouponMobile> findAll(){
		return couponMobileDao.findAll();
	}
	
	public List<CouponMobile> findAll(CouponMobile entity){
		return couponMobileDao.findAll(entity);
	}
}
