package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmFriendsRequestDao;
import cn.temobi.complex.entity.CmFriendsRequest;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmFriendsRequestService")
public class CmFriendsRequestService extends ServiceBase{
	
	@Resource(name = "cmFriendsRequestDao")
	private CmFriendsRequestDao cmFriendsRequestDao;
	
	public int update(CmFriendsRequest entity){
		return cmFriendsRequestDao.update(entity);
	}
	
	public Page<CmFriendsRequest> findByPage(Page page,Object map){
		return cmFriendsRequestDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmFriendsRequestDao.getCount(map);
	}
	
	public CmFriendsRequest getById(Long id){
		return cmFriendsRequestDao.getById(id);
	}
	
	public int save(CmFriendsRequest entity){
		return cmFriendsRequestDao.save(entity);
	}
	
	public int delete(Object id){
		return cmFriendsRequestDao.delete(id);
	}
	
	public int delete(CmFriendsRequest entity){
		return cmFriendsRequestDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmFriendsRequestDao.findByMap(map);
	}
    
	public List<CmFriendsRequest> findAll(){
		return cmFriendsRequestDao.findAll();
	}
	
	public List<CmFriendsRequest> findAll(CmFriendsRequest entity){
		return cmFriendsRequestDao.findAll(entity);
	}
}
