package cn.temobi.complex.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.CirclePostDao;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;


@Component
@Repository("circlePostDao")
public class CirclePostDaoImpl extends SimpleMybatisSupport<CirclePost, Long> implements CirclePostDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CirclePost";
	}

	@Override
	public Page findByPageTwo(Page page, Map<String, String> parameter) {
		Map<String, String> map = new HashMap<String, String>();
		map = (Map<String, String>) parameter;
		Number totalItems = 0;
		if(map !=null && map.size() ==1){//没有参数时 ，直接查总表数量 
			Long tempCount = (Long) CacheHelper.getInstance().get("circlePost@checkCount");
			if(tempCount == null){
				
				totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("count"), toParameterMap(parameter));
				CacheHelper.getInstance().set(60*60*24,"circlePost@checkCount", totalItems);
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
