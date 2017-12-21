package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CirclePostAccusationDao;
import cn.temobi.complex.entity.CirclePostAccusation;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("circlePostAccusationService")
public class CirclePostAccusationService extends ServiceBase{
	
	@Resource(name = "circlePostAccusationDao")
	private CirclePostAccusationDao circlePostAccusationDao;
	
	public int update(CirclePostAccusation entity){
		return circlePostAccusationDao.update(entity);
	}
	
	public Page<CirclePostAccusation> findByPage(Page page,Object map){
		return circlePostAccusationDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return circlePostAccusationDao.getCount(map);
	}
	
	public CirclePostAccusation getById(Long id){
		return circlePostAccusationDao.getById(id);
	}
	
	public int save(CirclePostAccusation entity){
		return circlePostAccusationDao.save(entity);
	}
	
	public int delete(Object id){
		return circlePostAccusationDao.delete(id);
	}
	
	public int delete(CirclePostAccusation entity){
		return circlePostAccusationDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return circlePostAccusationDao.findByMap(map);
	}
    
	public List<CirclePostAccusation> findAll(){
		return circlePostAccusationDao.findAll();
	}
	
	public List<CirclePostAccusation> findAll(CirclePostAccusation entity){
		return circlePostAccusationDao.findAll(entity);
	}
	
//	public Page<CirclePostAccusationDto> findDtoByPage(Page page,Object map){
//		return circlePostAccusationDao.findDtoByPage(page, map);
//	}
}
