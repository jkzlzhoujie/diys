package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.ClientVersionDao;
import cn.temobi.complex.entity.ClientVersion;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 客户端版本管理
 * @author hjf
 * 2014 三月 17 17:32:50
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("clientVersionService")
public class ClientVersionService extends ServiceBase{
	
	@Resource(name = "clientVersionDao")
	private ClientVersionDao clientVersionDao;
	
	public int update(ClientVersion entity){
		return clientVersionDao.update(entity);
	}
	
	public Page<ClientVersion> findByPage(Page page,Object map){
		return clientVersionDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return clientVersionDao.getCount(map);
	}
	
	public ClientVersion getById(Long id){
		return clientVersionDao.getById(id);
	}
	
	public int save(ClientVersion entity){
		return clientVersionDao.save(entity);
	}
	
	public int delete(Object id){
		return clientVersionDao.delete(id);
	}
	
	public int delete(ClientVersion entity){
		return clientVersionDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return clientVersionDao.findByMap(map);
	}
    
	public List<ClientVersion> findAll(){
		return clientVersionDao.findAll();
	}
	
	public List<ClientVersion> findAll(ClientVersion entity){
		return clientVersionDao.findAll(entity);
	}
	
	public ClientVersion getNewVersion(Map<String, String> map) {
		return clientVersionDao.getNewVersion(map);
	}
}
