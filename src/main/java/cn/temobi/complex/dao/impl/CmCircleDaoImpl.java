package cn.temobi.complex.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.CmCircleDao;
import cn.temobi.complex.entity.CmCircle;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmCircleDao")
public class CmCircleDaoImpl extends SimpleMybatisSupport<CmCircle, Long> implements CmCircleDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmCircle";
	}
	
	@Override
	public Long check(Object parameter){
		return (Long)getSqlSession().selectOne(toMybatisStatement("check"), toParameterMap(parameter));
	}

	@Override
	public List<CmCircle> findMyFollow(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findMyFollow"), map);
	}

	@Override
	public Page<CmCircle> findByPageTwo(Page page, Object parameter) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) parameter;
		Number totalItems = 0;
		if(map ==null || map.size() ==0){//没有参数时 ，直接查总表数量 
			
			Long tempCount = (Long) CacheHelper.getInstance().get("circle@checkCount");
			if(tempCount == null){
				totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("singleCount"), toParameterMap(parameter));
				CacheHelper.getInstance().set(60*60*24,"circle@checkCount", totalItems);
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

	@Override
	public List<CmCircle> getByCircleIdAboutMe(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("getByCircleIdAboutMe"), map);
	}

}
