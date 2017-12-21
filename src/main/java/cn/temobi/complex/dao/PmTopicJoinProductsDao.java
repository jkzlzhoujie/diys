package cn.temobi.complex.dao;

import java.util.Map;

import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface PmTopicJoinProductsDao extends SimpleDao<PmTopicJoinProducts, Long> {
	
	public Page<PmTopicJoinProducts> findByPageDtoTo(Page<PmTopicJoinProducts> page, Object parameter);

	public Page<PmTopicJoinProducts> findByPageAppCheck(Page page,Map<String, Object> map);
}
