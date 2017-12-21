package cn.temobi.complex.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cn.temobi.complex.dao.CountDao;
import cn.temobi.complex.dto.CountDto;
import cn.temobi.complex.dto.PUserDto;
import cn.temobi.complex.dto.ShareCountDto;
import cn.temobi.complex.entity.Count;
import cn.temobi.complex.entity.MaterialCount;
import cn.temobi.complex.entity.ThemeUseCount;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.SimpleMybatisSupport;

@Component
@Repository("countDao")
public class CountDaoImpl extends SimpleMybatisSupport<Object, Long> implements CountDao{

	@Override
	public String getMybatisMapperNamesapce() {
		return "cn.temobi.complex.entity.Count";
	}
	//每日用户行为统计
	@Override
    public List<Count> findCount(Object parameter) throws DataAccessException {
        return getSqlSession().selectList(toMybatisStatement("findCount"), toParameterMap(parameter));
    }
	//点赞数
	@Override
    public List<Count> getZCount(Object parameter) {
        return  getSqlSession().selectList(toMybatisStatement("zCount"), toParameterMap(parameter));
    }
	//被赞数
	@Override
    public List<Count> getisZCount(Object parameter) {
        return  getSqlSession().selectList(toMybatisStatement("isZCount"), toParameterMap(parameter));
    }
	//评论数
	@Override
    public List<Count> getPCount(Object parameter) {
        return  getSqlSession().selectList(toMybatisStatement("pCount"), toParameterMap(parameter));
    }
	//被评论数
	@Override
    public List<Count> getisPCount(Object parameter) {
        return  getSqlSession().selectList(toMybatisStatement("isPCount"), toParameterMap(parameter));
    }
	
	//主题使用排行
	@Override
	public List<ThemeUseCount> themeUse(Map map){
		return getSqlSession().selectList(toMybatisStatement("themeUse"),map);
	}
	//主题分类使用排行
	@Override
	public List<ThemeUseCount> classifyUse(Map map){
		return getSqlSession().selectList(toMybatisStatement("classifyUse"),map);
	}
	
	//每日统计
	@Override
	public List<CountDto> countUser(Map<String, Object> map) {
		return this.getSqlSession().selectList(toMybatisStatement("countUser"), map);
	}
	//每日统计-总条数
	@Override
	public Number countUserNum(Map<String, Object> map) {
		return (Number) this.getSqlSession().selectOne(toMybatisStatement("countUserNum"), map);
	}
	//每日统计-总计
	@Override
	public CountDto countAll(Map<String, Object> map) {
		return (CountDto) this.getSqlSession().selectOne(toMybatisStatement("countAll"), map);
	}
	
	
	//分享统计-分页
	@Override
	public Page<CountDto> findByPageTo(Page<CountDto> page,Object parameter) {
		Number totalItems = (Number) this.getSqlSession().selectOne(toMybatisStatement("countUserNum"), parameter);
        if (totalItems != null && totalItems.longValue() > 0) {
            List list = countUser(toParameterMap(parameter, page));
            page.setResult(list);
            page.setTotalItems(totalItems.longValue());
        }
        return page;
	}
	
	@Override
	 public Number countNum(Object parameter) {
	        return (Number) getSqlSession().selectOne(toMybatisStatement("countNum"), toParameterMap(parameter));
	   }
	
	@Override
	public Page<ShareCountDto> findByPageTo1(Page<ShareCountDto> page,
			Object parameter) {
		Number totalItems = getCountTo(parameter);
       if (totalItems != null && totalItems.longValue() > 0) {
           List list = getSqlSession().selectList(toMybatisStatement("findCountPage"), toParameterMap(parameter, page));
           page.setResult(list);
           page.setTotalItems(totalItems.longValue());
       }
       return page;
	}


	 public Number getCountTo(Object parameter) {
	        return (Number) getSqlSession().selectOne(toMybatisStatement("countShare"), toParameterMap(parameter));
	   }
	 
	@Override
	public Number sumShare(Object parameter) {
		return (Number) getSqlSession().selectOne(toMybatisStatement("sumShare"), toParameterMap(parameter));
	}
	
	//使用率前50的热门素材
	public List<MaterialCount> materialCount(Map map){
		return getSqlSession().selectList(toMybatisStatement("materialCount"),map);
	}
	
	//每日活跃用户
	@Override
    public List<PUserDto> userCount(Object parameter) throws DataAccessException {
        return getSqlSession().selectList(toMybatisStatement("userCount"), toParameterMap(parameter));
    }
	
	//每日p图用户
	@Override
	public List<PUserDto> pDayCount(Object parameter) throws DataAccessException {
		return getSqlSession().selectList(toMybatisStatement("pDayCount"), toParameterMap(parameter));
	}
	
	//每日彩绘用户
	@Override
	public List<PUserDto> cDayCount(Object parameter) throws DataAccessException {
		return getSqlSession().selectList(toMybatisStatement("cDayCount"), toParameterMap(parameter));
	}
	@Override
	public List<PUserDto> countUserP(Object parameter) {
		return getSqlSession().selectList(toMybatisStatement("countUserP"), toParameterMap(parameter));
	}
	
}
