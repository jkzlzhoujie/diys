package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.ClientDao;
import cn.temobi.complex.dao.StartStatisticsDao;
import cn.temobi.complex.entity.Client;
import cn.temobi.complex.entity.StartStatistics;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.IpUtil;

/**
 * 漫赏表情用户设备
 * @author hjf
 * 2014 三月 17 17:27:15
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("clientService")
public class ClientService extends ServiceBase{
	
	@Resource(name = "clientDao")
	private ClientDao clientDao;
	
	@Resource(name = "startStatisticsDao")
	private StartStatisticsDao startStatisticsDao;
	
	public int update(Client entity){
		int num = clientDao.update(entity);
		String key = "client@clientId"+entity.getId();
		CacheHelper.getInstance().set(60*60*6,key, entity);
		return num;
	}
	
	public Page<Client> findByPage(Page page,Object map){
		return clientDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return clientDao.getCount(map);
	}
	
	public Client getById(Long id){
		Client client = clientDao.getById(id);
		String key = "client@clientId"+id;
		CacheHelper.getInstance().set(60*60*6,key, client);
		return  client ;
	}
	
	public int save(Client entity){
		int num = clientDao.save(entity);
		String key = "client@clientId"+entity.getId();
		CacheHelper.getInstance().set(60*60*6,key, entity);
		return num;
	}
	
	public int delete(Object id){
		String key = "client@clientId"+id.toString();
		CacheHelper.getInstance().remove(key);
		return clientDao.delete(id);
	}
	
	public int delete(Client entity){
		String key = "client@clientId"+entity.getId();
		CacheHelper.getInstance().remove(key);
		return clientDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return clientDao.findByMap(map);
	}
    
	public List<Client> findAll(){
		return clientDao.findAll();
	}
	
	public List<Client> findAll(Client entity){
		return clientDao.findAll(entity);
	}
	
	public Client getBySequence(Map<String, String> map) {
		return clientDao.getBySequence(map);
	}
	
	public Client getByUser(Map<String, Object> map) {
		return clientDao.getByUser(map);
	}
	
	public void saveAll(List<Client> list,Client client){
		if(list.size() == 0) {
			clientDao.save(client);
			list.add(client);
		}
		StartStatistics startStatistics = new StartStatistics();
		startStatistics.setChannelId(client.getChannel());
		startStatistics.setClientId(client.getId());
		startStatistics.setStartIp(client.getIp());
		startStatistics.setVersion(client.getOsVersion());
		startStatisticsDao.save(startStatistics);
	}
}
