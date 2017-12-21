package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserImageIntroduceDao;
import cn.temobi.complex.entity.CmUserImageIntroduce;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserImageIntroduceService")
public class CmUserImageIntroduceService extends ServiceBase{
	
	@Resource(name = "cmUserImageIntroduceDao")
	private CmUserImageIntroduceDao cmUserImageIntroduceDao;
	
	public int update(CmUserImageIntroduce entity){
		return cmUserImageIntroduceDao.update(entity);
	}
	
	public Page<CmUserImageIntroduce> findByPage(Page page,Object map){
		return cmUserImageIntroduceDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserImageIntroduceDao.getCount(map);
	}
	
	public CmUserImageIntroduce getById(Long id){
		return cmUserImageIntroduceDao.getById(id);
	}
	
	public int save(CmUserImageIntroduce entity){
		return cmUserImageIntroduceDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserImageIntroduceDao.delete(id);
	}
	
	public int delete(CmUserImageIntroduce entity){
		return cmUserImageIntroduceDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserImageIntroduceDao.findByMap(map);
	}
    
	public List<CmUserImageIntroduce> findAll(){
		return cmUserImageIntroduceDao.findAll();
	}
	
	public List<CmUserImageIntroduce> findAll(CmUserImageIntroduce entity){
		return cmUserImageIntroduceDao.findAll(entity);
	}
	
	public void deleteByUserId(Map map){
		cmUserImageIntroduceDao.deleteByUserId(map);
	}
}
