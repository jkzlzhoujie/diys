package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.entity.Laber;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 17 17:15:32
 */
public interface LaberDao extends SimpleDao<Laber, Long> {

	public List<String> findRand(Map<String, Object> map);
}
