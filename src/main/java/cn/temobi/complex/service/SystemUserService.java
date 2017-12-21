package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SystemUserDao;
import cn.temobi.complex.entity.SystemUser;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 后台用户
 * @author hjf
 * 2014-3-5 下午03:27:32
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("systemUserService")
public class SystemUserService extends ServiceBase{

	@Resource(name = "systemUserDao")
	private SystemUserDao systemUserDao;
	
	/**
	 * 通过账号和密码取用户
	 * @param map
	 * @return
	 */
	public SystemUser findUser(Map map){
		return systemUserDao.findUser(map);
	}
	
	public int update(SystemUser systemUser){
		return systemUserDao.update(systemUser);
	}
	
	public Page<SystemUser> findByPage(Page page,Object map){
		return systemUserDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return systemUserDao.getCount(map);
	}
	
	public SystemUser getById(Long id){
		return systemUserDao.getById(id);
	}
	
	public int save(SystemUser systemUser){
		return systemUserDao.save(systemUser);
	}
	
	public int delete(Long id){
		return systemUserDao.delete(id);
	}
	
	public int delete(SystemUser systemUser){
		return systemUserDao.delete(systemUser);
	}
	
	public List findByMap(Map map){
		return systemUserDao.findByMap(map);
	}
    
	public List<SystemUser> findAll(){
		return systemUserDao.findAll();
	}
	
	public List<SystemUser> findAll(SystemUser systemUser){
		return systemUserDao.findAll(systemUser);
	}
}
