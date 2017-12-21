package cn.temobi.complex.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.dto.CmUserInfoUpdateDto;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("cmUserInfoDao")
public class CmUserInfoDaoImpl extends SimpleMybatisSupport<CmUserInfo, Long> implements CmUserInfoDao {
	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.CmUserInfo";
	}
	
	@Override
	public List<CmUserInfoListDto> findByList(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findByList"), map);
	}

	@Override
	public List<CmUserInfo> findLoginUser(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findLoginUser"), map);
	}
	
	@Override
	public List<CmUserInfo> findRand(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findRand"), map);
	}

	@Override
	public List<Long> findCircleId(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findCircleId"), map);
	}

	@Override
	public List<CmUserInfo> findByPage(Map<String, Object> map) {
		 return getSqlSession().selectList(toMybatisStatement("findByPage"), map);
	}

	@Override
	public List<CmUserInfo> findPriCmUser(Map<String, String> map) {
		return this.getSqlSession().selectList(toMybatisStatement("findPriCmUser"), map);
	}

	@Override
	public void updatePartCmUser(CmUserInfoUpdateDto updateUser) {
		 this.getSqlSession().update(toMybatisStatement("updatePartCmUser"), updateUser);
	}

	@Override
	public Page<CmUserInfo> findByPageTwo(Page page, Object parameter) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) parameter;
		Number totalItems = 0;
		if(map!=null && map.size() ==3){//只有一个参数时 ，直接查总表数量 
			Long tempCount = (Long) CacheHelper.getInstance().get("cmUserInfo@checkCount");
			if(tempCount == null){
				totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("singleCount"), toParameterMap(parameter));
				CacheHelper.getInstance().set(60*60*24,"cmUserInfo@checkCount", totalItems);
			}else{
				totalItems = tempCount;
			}
		}else{
			totalItems = (Number) getSqlSession().selectOne(toMybatisStatement("singleCount"), toParameterMap(parameter));
		}
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = getSqlSession().selectList(toMybatisStatement("findByPage"), toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
	}
	
}
