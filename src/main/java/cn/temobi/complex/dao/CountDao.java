package cn.temobi.complex.dao;

import java.util.List;
import java.util.Map;

import cn.temobi.complex.dto.CountDto;
import cn.temobi.complex.dto.PUserDto;
import cn.temobi.complex.dto.ShareCountDto;
import cn.temobi.complex.entity.Count;
import cn.temobi.complex.entity.MaterialCount;
import cn.temobi.complex.entity.ThemeUseCount;
import cn.temobi.core.common.Page;
import cn.temobi.core.dao.SimpleDao;

public interface CountDao extends SimpleDao<Object, Long>{
	//点赞数
	public List<Count> getZCount(Object parameter);
	//被赞数
	public List<Count> getisZCount(Object parameter);
	//评论数
	public List<Count> getPCount(Object parameter);
	//被评论数
	public List<Count> getisPCount(Object parameter);
	//每日用户行为统计
	public List<Count> findCount(Object parameter);
	
	//主题使用排行
	public List<ThemeUseCount> themeUse(Map map);
	//主题分类使用排行
	public List<ThemeUseCount> classifyUse(Map map);

	//每日统计
	public List<CountDto> countUser(Map<String, Object> map);
	//每日统计-总计
	public CountDto countAll(Map<String, Object> map);
	//每日统计-总条数
	public Number countUserNum(Map<String, Object> map);
	//分页
	public Page<CountDto> findByPageTo(Page<CountDto> page,Object parameter) ;
	
	//分享统计
	public Page<ShareCountDto> findByPageTo1(Page<ShareCountDto> page, Object parameter);
	
	public Number countNum(Object parameter);
	
	public Number sumShare(Object parameter);
	
	//使用率前50的热门素材
	public List<MaterialCount> materialCount(Map map);
	
	 public List<PUserDto> userCount(Object parameter);
	 
	 public List<PUserDto> pDayCount(Object parameter);
	 
	 public List<PUserDto> cDayCount(Object parameter);
	 
	 public List<PUserDto> countUserP(Object parameter);
}
