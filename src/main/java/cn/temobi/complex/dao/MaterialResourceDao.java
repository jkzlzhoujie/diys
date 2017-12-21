package cn.temobi.complex.dao;

import java.util.Map;

import cn.temobi.complex.entity.MaterialResource;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 26 09:38:40
 */
public interface MaterialResourceDao extends SimpleDao<MaterialResource, Long> {
	
	public Number maxId(Map<String, String> map);

	public Page<MaterialResource> findByPageTo(Page<MaterialResource> page, Object parameter);
}
