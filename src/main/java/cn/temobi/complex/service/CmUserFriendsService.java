package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserFriendsDao;
import cn.temobi.complex.entity.CmUserFriends;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserFriendsService")
public class CmUserFriendsService extends ServiceBase{
	
	@Resource(name = "cmUserFriendsDao")
	private CmUserFriendsDao cmUserFriendsDao;
	
	public int update(CmUserFriends entity){
		return cmUserFriendsDao.update(entity);
	}
	
	public Page<CmUserFriends> findByPage(Page page,Object map){
		return cmUserFriendsDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserFriendsDao.getCount(map);
	}
	
	public CmUserFriends getById(Long id){
		return cmUserFriendsDao.getById(id);
	}
	
	public int save(CmUserFriends entity){
		return cmUserFriendsDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserFriendsDao.delete(id);
	}
	
	public int delete(CmUserFriends entity){
		return cmUserFriendsDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserFriendsDao.findByMap(map);
	}
    
	public List<CmUserFriends> findAll(){
		return cmUserFriendsDao.findAll();
	}
	
	public List<CmUserFriends> findAll(CmUserFriends entity){
		return cmUserFriendsDao.findAll(entity);
	}
}
