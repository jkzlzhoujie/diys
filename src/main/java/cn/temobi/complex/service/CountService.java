package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.CountDao;
import cn.temobi.complex.dto.CountDto;
import cn.temobi.complex.dto.PUserDto;
import cn.temobi.complex.dto.ShareCountDto;
import cn.temobi.complex.entity.Client;
import cn.temobi.complex.entity.Count;
import cn.temobi.complex.entity.MaterialCount;
import cn.temobi.complex.entity.StartStatistics;
import cn.temobi.complex.entity.ThemeUseCount;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("countService")
public class CountService extends ServiceBase{

	@Resource(name = "countDao")
	private CountDao countDao;
	//每日用户行为统计
	public List<Count> findCount(Object map){
		return countDao.findCount(map);
	}
	//点赞数
	public List<Count> getZCount(Map map){
		return countDao.getZCount(map);
	}
	//被赞数
	public List<Count> getisZCount(Map map){
		return countDao.getisZCount(map);
	}
	//评论数
	public List<Count> getPCount(Map map){
		return countDao.getPCount(map);
	}
	//被评论数
	public List<Count> getisPCount(Map map){
		return countDao.getisPCount(map);
	}
	
	//主题使用排行	
	public List<ThemeUseCount> themeUse(Map map){
		return countDao.themeUse(map);
	}
	//主题分类使用排行
	public List<ThemeUseCount> classifyUse(Map map){
		return countDao.classifyUse(map);
	}
	
	//每日统计
	public List countUser(Map map){
		return countDao.countUser(map);
	}
	//每日统计-总计
	public CountDto countAll(Map map){
		return countDao.countAll(map);
	}
	//分页
	public Page<CountDto> findByPageTo(Page page,Object map){
		return countDao.findByPageTo(page, map);
	}
	
	
	//分享统计
	public Number countNum(Map map){
		return countDao.countNum(map);
	}
	
	public Number sumShare(Map map){
		return countDao.sumShare(map);
	}
	
	public Page<ShareCountDto> findByPageTo1(Page page,Object map){
		return countDao.findByPageTo1(page, map);
	}

	//使用率前50的热门素材
	public List<MaterialCount> materialCount(Map map){
		return countDao.materialCount(map);
	}
	
	//每日用户活跃
	public List<PUserDto> userCount(Object map){
		return countDao.userCount(map);
	}
	
	//每日P图
	public List<PUserDto> pDayCount(Object map){
		return countDao.pDayCount(map);
	}
	
	//每日彩绘
	public List<PUserDto> cDayCount(Object map){
		return countDao.cDayCount(map);
	}
	
	public List<PUserDto> countUserP(Object map){
		return countDao.countUserP(map);
	}
}
