package cn.temobi.complex.dao;

import java.util.Map;

import cn.temobi.complex.entity.ClientVersion;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 17 17:30:41
 */
public interface ClientVersionDao extends SimpleDao<ClientVersion, Long> {
	
	public ClientVersion getNewVersion(Map<String, String> map);
}
