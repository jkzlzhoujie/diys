package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.Material;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 17 17:46:15
 */
public interface MaterialDao extends SimpleDao<Material, Long> {
	
	public List<Material> findUseByMap(Map<String, Object> map);
}
