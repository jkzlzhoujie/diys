package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.ClientLoginDao;
import cn.temobi.complex.entity.ClientLogin;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 客户端登陆
 * @author hjf
 * 2014 五月 6 17:33:15
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("clientLoginService")
public class ClientLoginService extends ServiceBase{
	
	@Resource(name = "clientLoginDao")
	private ClientLoginDao clientLoginDao;
	
	public int update(ClientLogin entity){
		return clientLoginDao.update(entity);
	}
	
	public Page<ClientLogin> findByPage(Page page,Object map){
		return clientLoginDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return clientLoginDao.getCount(map);
	}
	
	public ClientLogin getById(Long id){
		return clientLoginDao.getById(id);
	}
	
	public int save(ClientLogin entity){
		return clientLoginDao.save(entity);
	}
	
	public int delete(Object id){
		return clientLoginDao.delete(id);
	}
	
	public int delete(ClientLogin entity){
		return clientLoginDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return clientLoginDao.findByMap(map);
	}
    
	public List<ClientLogin> findAll(){
		return clientLoginDao.findAll();
	}
	
	public List<ClientLogin> findAll(ClientLogin entity){
		return clientLoginDao.findAll(entity);
	}
}
