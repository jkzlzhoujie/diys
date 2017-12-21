package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.ClientUserDao;
import cn.temobi.complex.entity.ClientUser;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 漫赏表情注册用户
 * @author hjf
 * 2014 三月 17 17:29:50
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("clientUserService")
public class ClientUserService extends ServiceBase{
	
	@Resource(name = "clientUserDao")
	private ClientUserDao clientUserDao;
	
	public int update(ClientUser entity){
		return clientUserDao.update(entity);
	}
	
	public Page<ClientUser> findByPage(Page page,Object map){
		return clientUserDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return clientUserDao.getCount(map);
	}
	
	public ClientUser getById(Long id){
		return clientUserDao.getById(id);
	}
	
	public int save(ClientUser entity){
		return clientUserDao.save(entity);
	}
	
	public int delete(Object id){
		return clientUserDao.delete(id);
	}
	
	public int delete(ClientUser entity){
		return clientUserDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return clientUserDao.findByMap(map);
	}
    
	public List<ClientUser> findAll(){
		return clientUserDao.findAll();
	}
	
	public List<ClientUser> findAll(ClientUser entity){
		return clientUserDao.findAll(entity);
	}
	
	public ClientUser getByUsername(String username) { 
		return clientUserDao.getByUsername(username);
	}
}
