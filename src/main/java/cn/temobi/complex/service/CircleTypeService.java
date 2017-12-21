package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CircleTypeDao;
import cn.temobi.complex.entity.CircleType;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("circleTypeService")
public class CircleTypeService extends ServiceBase{

	@Resource(name = "circleTypeDao")
	private CircleTypeDao circleTypeDao;
	
	public int update(CircleType circleType){
		return circleTypeDao.update(circleType);
	}
	
	public Page<CircleType> findByPage(Page page,Object map){
		return circleTypeDao.findByPage(page, map);
	}
	
	public List<CircleType> findAll(){
		return circleTypeDao.findAll();
	}
	
	public List<CircleType> findByMap(Map param){
		return circleTypeDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return circleTypeDao.getCount(map);
	}
	
	public CircleType getById(Long id){
		return circleTypeDao.getById(id);
	}
	
	public int save(CircleType circleType){
		return circleTypeDao.save(circleType);
	}
	
	public int delete(Object id){
		return circleTypeDao.delete(id);
	}
}
