package cn.temobi.complex.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.ProductRecommendDao;
import cn.temobi.complex.entity.ProductRecommend;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("productRecommendDao")
public class ProductRecommendDaoImpl extends SimpleMybatisSupport<ProductRecommend, Long> implements ProductRecommendDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.ProductRecommend";
	}

	@Override
	public Page<ProductRecommend> findByPageTwo(Page page,Map<String, String> parameter) {
		 Map<String, String> map = new HashMap<String, String>();
			map = (Map<String, String>) parameter;
			Number totalItems = 0;
			if(map!=null && map.size() ==0){//只有0个参数时 ，直接查总表数量 
				Long tempCount = (Long) CacheHelper.getInstance().get("productListRecommend@checkCount");
				if(tempCount == null){
					totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("count"), toParameterMap(parameter));
					CacheHelper.getInstance().set(60*60*24,"productListRecommend@checkCount", totalItems);
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
