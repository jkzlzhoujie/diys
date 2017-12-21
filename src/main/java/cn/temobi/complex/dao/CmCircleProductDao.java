package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.temobi.complex.entity.CmCircleProduct;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

public interface CmCircleProductDao extends SimpleDao<CmCircleProduct, Long>{

	public Page<PmProductInfo> findPruduct(Page<PmProductInfo> page, Object parameter);
	public Long check(Object parameter);
	
	public Long delBycircleId(Object id) throws DataAccessException;
	
	public void insertList(Map<String,Object> map);
	
	public void upByCircleId(Map map);
	
	public Number countPruduct(Object parameter);
	
	public List<Long> findProductId(Object parameter);
	
	public Page findByPageTwo(Page page, Map<String, String> map);
}
