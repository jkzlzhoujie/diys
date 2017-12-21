package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.dto.PmTopicPsProductsDto;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

/**
 * 
 */
public interface PmTopicPsProductsDao extends SimpleDao<PmTopicPsProducts, Long> {

	public Page<PmTopicPsProducts> findIndex(Page<PmTopicPsProducts> page, Object parameter);
	
	public PIndexDto countPs(Map<String, Object> map);
	
	public List<Long> findIds(Map<String, Object> map);
	
	public List<PmTopicPsProducts> findMapIndex(Object parameter);

	public Page<PmTopicPsProducts> findByPageTwo(Page page, Object map);
}
