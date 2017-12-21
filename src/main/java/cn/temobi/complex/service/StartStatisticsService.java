package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.StartStatisticsDao;
import cn.temobi.complex.dto.CountDto;
import cn.temobi.complex.entity.StartStatistics;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 用户启动行为统计
 * @author hjf
 * 2014 三月 17 18:16:24
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("startStatisticsService")
public class StartStatisticsService extends ServiceBase{
	
	@Resource(name = "startStatisticsDao")
	private StartStatisticsDao startStatisticsDao;
	
	public int update(StartStatistics entity){
		return startStatisticsDao.update(entity);
	}
	
	public Page<StartStatistics> findByPage(Page page,Object map){
		return startStatisticsDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return startStatisticsDao.getCount(map);
	}
	
	public StartStatistics getById(Long id){
		return startStatisticsDao.getById(id);
	}
	
	public String findMaxTime(Map map){
		return startStatisticsDao.findMaxTime(map);
	}
	
	public int save(StartStatistics entity){
		return startStatisticsDao.save(entity);
	}
	
	public int delete(Object id){
		return startStatisticsDao.delete(id);
	}
	
	public int delete(StartStatistics entity){
		return startStatisticsDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return startStatisticsDao.findByMap(map);
	}
    
	public List<StartStatistics> findAll(){
		return startStatisticsDao.findAll();
	}
	
	public List<StartStatistics> findAll(StartStatistics entity){
		return startStatisticsDao.findAll(entity);
	}

}
