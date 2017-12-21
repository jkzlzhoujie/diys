package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 17 17:15:32
 */
public interface PmProductPraisesDao extends SimpleDao<PmProductPraises, Long> {

	public List<Long> findIdList(Map<String, Object> map);
}
