package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.StatisticsDao;
import cn.temobi.complex.entity.Statistics;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 资源包管理
 * @author hjf
 * 2014 三月 17 17:27:15
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("statisticsService")
public class StatisticsService extends ServiceBase{
	
	@Resource(name = "statisticsDao")
	private StatisticsDao statisticsDao;
	
	public int update(Statistics entity){
		return statisticsDao.update(entity);
	}
	
	public Page<Statistics> findByPage(Page page,Object map){
		return statisticsDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return statisticsDao.getCount(map);
	}
	
	public Statistics getById(Long id){
		return statisticsDao.getById(id);
	}
	
	public int save(Statistics entity){
		return statisticsDao.save(entity);
	}
	
	public int delete(Object id){
		return statisticsDao.delete(id);
	}
	
	public int delete(Statistics entity){
		return statisticsDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return statisticsDao.findByMap(map);
	}
    
	public List<Statistics> findAll(){
		return statisticsDao.findAll();
	}
	
	public List<Statistics> findAll(Statistics entity){
		return statisticsDao.findAll(entity);
	}
}
