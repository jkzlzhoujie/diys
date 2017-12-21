package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.ClientSpDao;
import cn.temobi.complex.entity.ClientSp;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 漫赏表情用户设备
 * @author hjf
 * 2014 三月 17 17:27:15
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("clientSpService")
public class ClientSpService extends ServiceBase{
	
	@Resource(name = "clientSpDao")
	private ClientSpDao clientSpDao;
	
	public int update(ClientSp entity){
		return clientSpDao.update(entity);
	}
	
	public Page<ClientSp> findByPage(Page page,Object map){
		return clientSpDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return clientSpDao.getCount(map);
	}
	
	public ClientSp getById(Long id){
		return clientSpDao.getById(id);
	}
	
	public int save(ClientSp entity){
		return clientSpDao.save(entity);
	}
	
	public int delete(Object id){
		return clientSpDao.delete(id);
	}
	
	public int delete(ClientSp entity){
		return clientSpDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return clientSpDao.findByMap(map);
	}
    
	public List<ClientSp> findAll(){
		return clientSpDao.findAll();
	}
	
	public List<ClientSp> findAll(ClientSp entity){
		return clientSpDao.findAll(entity);
	}
}
