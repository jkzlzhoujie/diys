package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.CmCircleUserLogDao;
import cn.temobi.complex.entity.CmCircleUserLog;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmCircleUserLogService")
public class CmCircleUserLogService extends ServiceBase{

	@Resource(name = "cmCircleUserLogDao")
	private CmCircleUserLogDao cmCircleUserLogDao;
	
	public Long delBycircleId(Object id){
		return cmCircleUserLogDao.delBycircleId(id);
	}
	
	public Number check(Map map){
		return cmCircleUserLogDao.check(map);
	} 
	
	public int update(CmCircleUserLog entity){
		return cmCircleUserLogDao.update(entity);
	}
	
	public Page<CmCircleUserLog> findByPage(Page page,Object map){
		return cmCircleUserLogDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmCircleUserLogDao.getCount(map);
	}
	
	public CmCircleUserLog getById(Long id){
		return cmCircleUserLogDao.getById(id);
	}
	
	public int save(CmCircleUserLog entity){
		return cmCircleUserLogDao.save(entity);
	}
	
	public int delete(Object id){
		return cmCircleUserLogDao.delete(id);
	}
	
	public int delete(CmCircleUserLog entity){
		return cmCircleUserLogDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmCircleUserLogDao.findByMap(map);
	}
    
	public List<CmCircleUserLog> findAll(){
		return cmCircleUserLogDao.findAll();
	}
}
