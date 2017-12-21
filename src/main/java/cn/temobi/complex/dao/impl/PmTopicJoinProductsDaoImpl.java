package cn.temobi.complex.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.PmTopicJoinProductsDao;
import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmTopicJoinProductsDao")
public class PmTopicJoinProductsDaoImpl extends SimpleMybatisSupport<PmTopicJoinProducts, Long> implements PmTopicJoinProductsDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmTopicJoinProducts";
	}
	
	@Override
	public Page<PmTopicJoinProducts> findByPageDtoTo(Page<PmTopicJoinProducts> page,
			Object parameter) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) parameter;
		Number totalItems = 0;
		if(map!=null && map.size() ==1){//只有一个参数时 ，直接查总表数量 
			if(map.get("topicId").equals("3")){//悬赏求P
				Long tempCount = (Long) CacheHelper.getInstance().get("pmTopicPs@count");
				if(tempCount == null){
					totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("singleCount"), toParameterMap(parameter));
					CacheHelper.getInstance().set(60*60*24,"pmTopicPs@count", totalItems);
				}else{
					totalItems = tempCount;
				}
			}
			if(map.get("topicId").equals("1")){//彩绘订单
				Long tempCount = (Long) CacheHelper.getInstance().get("pmTopicColour@count");
				if(tempCount == null){
					totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("singleCount"), toParameterMap(parameter));
					CacheHelper.getInstance().set(60*60*24,"pmTopicColour@count", totalItems);
				}else{
					totalItems = tempCount;
				}
			}
		}else{
			totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("countDto"), toParameterMap(parameter));
		}
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement("findByPageDto"), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
	}

	@Override
	public Page<PmTopicJoinProducts> findByPageAppCheck(Page page,Map<String, Object> map) {
		Number totalItems = 0;
		if(map.get("appCheck") != null){//悬赏求P
			Long tempCount = (Long) CacheHelper.getInstance().get("pmTopicProduct@appCheckCount");
			if(tempCount == null){
				totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("singleCount"), toParameterMap(map));
				CacheHelper.getInstance().set(60*60*24,"pmTopicProduct@appCheckCount", totalItems);
			}else{
				totalItems = tempCount;
			}
		}
		 if (totalItems != null && totalItems.longValue() > 0) {
	            List<PmTopicJoinProducts> list = getSqlSession().selectList(toMybatisStatement("findByPage"), toParameterMap(map, page));
	            page.setResult(list);
	            page.setTotalItems(totalItems.longValue());
	        }
		return page;
	}
}
