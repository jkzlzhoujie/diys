package cn.temobi.complex.dao;

import java.util.Map;
import cn.temobi.complex.entity.Client;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 17 17:25:44
 */
public interface ClientDao extends SimpleDao<Client, Long> {
	
	public Client getBySequence(Map<String, String> map);
	
	public Client getByUser(Map<String, Object> map);
}
