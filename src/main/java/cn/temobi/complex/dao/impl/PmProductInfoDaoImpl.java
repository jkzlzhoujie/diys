package cn.temobi.complex.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.PmProductInfoDao;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.dto.ThemeUsedByPdtDto;
import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("pmProductInfoDao")
public class PmProductInfoDaoImpl extends SimpleMybatisSupport<PmProductInfo, Long> implements PmProductInfoDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.PmProductInfo";
	}

	@Override
	public List<Long> findIdList(Map<String, Object> map) {
		return  this.getSqlSession().selectList(toMybatisStatement("findIdList"), map);
	}

	@Override
	public Page<PmProductInfoDto> findByPageDtoTo(Page<PmProductInfoDto> page,
			Object parameter) {
        Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) parameter;
		Number totalItems = 0;
		if(map!=null && map.size() ==3){//只有3个参数时 ，直接查总表数量 
			Long tempCount = (Long) CacheHelper.getInstance().get("productList@checkCount");
			if(tempCount == null){
				totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("count"), toParameterMap(parameter));
				CacheHelper.getInstance().set(60*60*24,"productList@checkCount", totalItems);
			}else{
				totalItems = tempCount;
			}
		}else{
			totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("count"), toParameterMap(parameter));
		}
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement("findByPageDto"), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
        
	}
	
	@Override
	public Page<ThemeUsedByPdtDto> findByPageDtoTo2(Page<ThemeUsedByPdtDto> page,
			Object parameter) {
        
        Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) parameter;
		Number totalItems = 0;
		if(map!=null && map.size() ==1){//只有一个参数时 ，直接查总表数量 
			Long tempCount = (Long) CacheHelper.getInstance().get("producJointList@checkCount");
			if(tempCount == null){
				totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("countDto2"), toParameterMap(parameter));
				CacheHelper.getInstance().set(60*60*24,"producJointList@checkCount", totalItems);
			}else{
				totalItems = tempCount;
			}
		}else{
			totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("countDto2"), toParameterMap(parameter));
		}
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement("findByPageDto2"), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
	}
	
	@Override
	public List<PmProductInfoDto> findDtoMap(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findByPageDto"), map);
	}

	@Override
	public List<PmProductInfoDto> findNew(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findNew"), map);
	}

	@Override
	public List<PmProductInfo> findNotPraises(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findNotPraises"), map);
	}

	@Override
	public List<Long> findProductList(Map<String, Object> map) {
		return  this.getSqlSession().selectList(toMybatisStatement("findProductList"), map);
	}
	
	@Override
	public List<Long> findCircleId(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findCircleId"), map);
	}

	@Override
	public List<Long> findProductIdList(Map<String, Object> map) {
		return  this.getSqlSession().selectList(toMybatisStatement("findProductIdList"), map);
	}

	@Override
	public List<Long> findNewProductId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(toMybatisStatement("findNewProductId"), map);
	}

	@Override
	public void updateAll(Map<String,Object> map) {
		this.getSqlSession().selectList(toMybatisStatement("updateAll"), map);
	}

	@Override
	public void saveBatch(List<PmProductInfo> pmProductInfoList) {
		getSqlSession().insert("insertBatch", pmProductInfoList);
	}
	
	
	
}
