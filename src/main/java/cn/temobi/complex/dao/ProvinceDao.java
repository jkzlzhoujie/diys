package cn.temobi.complex.dao;

import cn.temobi.complex.entity.Province;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 * @author hjf
 * 2014 三月 28 10:33:04
 */
public interface ProvinceDao extends SimpleDao<Province, Long> {
	
	/**
	 * 根据号码段查询
	 * @param segment
	 * @return
	 */
	public Province getBySegment(String segment);
}
