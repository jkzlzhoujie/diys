package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.CmCircleUserFollowDao;
import cn.temobi.complex.entity.CmCircleUserFollow;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmCircleUserFollowService")
public class CmCircleUserFollowService extends ServiceBase{

	@Resource(name = "cmCircleUserFollowDao")
	private CmCircleUserFollowDao cmCircleUserFollowDao;
	
	public int update(CmCircleUserFollow entity){
		return cmCircleUserFollowDao.update(entity);
	}
	
	public Page<CmCircleUserFollow> findByPage(Page page,Object map){
		return cmCircleUserFollowDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmCircleUserFollowDao.getCount(map);
	}
	
	public CmCircleUserFollow getById(Long id){
		return cmCircleUserFollowDao.getById(id);
	}
	
	public int save(CmCircleUserFollow entity){
		return cmCircleUserFollowDao.save(entity);
	}
	
	public int delete(Object id){
		return cmCircleUserFollowDao.delete(id);
	}
	
	public int delete(CmCircleUserFollow entity){
		return cmCircleUserFollowDao.delete(entity);
	}
	
	public List<CmCircleUserFollow> findByMap(Map map){
		return cmCircleUserFollowDao.findByMap(map);
	}
    
	public List<CmCircleUserFollow> findAll(){
		return cmCircleUserFollowDao.findAll();
	}

	public List<Long> findUserId(Map<String, Object> map) {
		return cmCircleUserFollowDao.findUserId(map);
		
	}
}
