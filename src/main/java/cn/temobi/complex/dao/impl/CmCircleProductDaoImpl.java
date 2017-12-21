package cn.temobi.complex.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.CmCircleProductDao;
import cn.temobi.complex.entity.CmCircleProduct;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmCircleProductDao")
public class CmCircleProductDaoImpl extends SimpleMybatisSupport<CmCircleProduct, Long> implements CmCircleProductDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmCircleProduct";
	}

	@Override
	public Page<cn.temobi.complex.entity.PmProductInfo> findPruduct(
			Page<cn.temobi.complex.entity.PmProductInfo> page, Object parameter) {
		Number totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("countPruduct"), toParameterMap(parameter));
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement("findPruduct"), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
	}
	
	@Override
	public Number countPruduct(Object parameter){
		return (Number) getSqlSession().selectOne(toMybatisStatement("countPruduct"), toParameterMap(parameter));
	}
	
	@Override
	public Long check(Object parameter){
		return (Long)getSqlSession().selectOne(toMybatisStatement("check"), toParameterMap(parameter));
	}
	
	@Override
    public Long delBycircleId(Object id) throws DataAccessException {
        return (Long)getSqlSession().selectOne(toMybatisStatement("delBycircleId"), id);
    }

	@Override
	public void insertList(Map<String, Object> map) {
		this.getSqlSession().insert(toMybatisStatement("insertList"), map);
	}
	
	@Override
	public void upByCircleId(Map map){
        getSqlSession().update(toMybatisStatement("upByCircleId"), map);
	}

	@Override
	public List<Long> findProductId(Object parameter) {
		 return getSqlSession().selectList(toMybatisStatement("findProductId"), parameter);
	}

	@Override
	public Page findByPageTwo(Page page, Map<String, String> parameter) {
		Map<String, String> map = new HashMap<String, String>();
		map = (Map<String, String>) parameter;
		Number totalItems = 0;
		if(map ==null || map.size() ==0){//没有参数时 ，直接查总表数量 
			
			Long tempCount = (Long) CacheHelper.getInstance().get("circleProduct@checkCount");
			if(tempCount == null){
				totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("count"), toParameterMap(parameter));
				CacheHelper.getInstance().set(60*60*24,"circleProduct@checkCount", totalItems);
			}else{
				totalItems = tempCount;
			}
		}else{
			totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("count"), toParameterMap(parameter));
		}
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement("findByPage"), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
	}
}
