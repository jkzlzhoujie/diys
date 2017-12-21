package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.MaterialUseLogDao;
import cn.temobi.complex.entity.MaterialUseLog;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("materialUseLogService")
public class MaterialUseLogService {

	@Resource(name = "materialUseLogDao")
	private MaterialUseLogDao materialUseLogDao;

	public Number getCount(Map map){
		return materialUseLogDao.getCount(map);
	}
	
	public MaterialUseLog getById(Long id){
		return materialUseLogDao.getById(id);
	}
	
	public int save(MaterialUseLog entity){
		return materialUseLogDao.save(entity);
	}
	
	public int delete(Object id){
		return materialUseLogDao.delete(id);
	}

	public int delete(MaterialUseLog entity){
		return materialUseLogDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return materialUseLogDao.findByMap(map);
	}

	public List<MaterialUseLog> findAll(){
		return materialUseLogDao.findAll();
	}

	public int update(MaterialUseLog entity){
		return materialUseLogDao.update(entity);
	}
	
	public void insertList(List<MaterialUseLog> list){
		materialUseLogDao.insertList(list);
	}
}
