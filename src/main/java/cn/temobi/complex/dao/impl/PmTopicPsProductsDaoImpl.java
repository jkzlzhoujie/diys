package cn.temobi.complex.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.PmTopicPsProductsDao;
import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.dto.PmTopicPsProductsDto;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmTopicPsProductsDao")
public class PmTopicPsProductsDaoImpl extends SimpleMybatisSupport<PmTopicPsProducts, Long> implements PmTopicPsProductsDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmTopicPsProducts";
	}

	@Override
	public Page<PmTopicPsProducts> findIndex(Page<PmTopicPsProducts> page,
			Object parameter) {
		Number totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("countIndex"), toParameterMap(parameter));
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement("findIndex"), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
	}
	
	@Override
	public List<PmTopicPsProducts> findMapIndex(Object parameter) {
        return getSqlSession().selectList(toMybatisStatement("findIndex"), toParameterMap(parameter));
	}

	
	@Override
	public PIndexDto countPs(Map<String, Object> map) {
		return (PIndexDto) this.getSqlSession().selectOne(toMybatisStatement("countPs"), map);
	}

	@Override
	public List<Long> findIds(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findIds"), map);
	}

	@Override
	public Page<PmTopicPsProducts> findByPageTwo(Page page, Object parameter) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) parameter;
		Number totalItems = 0;
		if(map!=null && map.size() ==1){//只有一个参数时 ，直接查总表数量 
			
			Long tempCount = (Long) CacheHelper.getInstance().get("pmPsProduct@count");
			if(tempCount == null){
				totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("singleCount"), toParameterMap(parameter));
				CacheHelper.getInstance().set(60*60*24,"pmPsProduct@count", totalItems);
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
