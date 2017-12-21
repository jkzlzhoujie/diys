package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmProductAccusationDao;
import cn.temobi.complex.dto.PmProductAccusationDto;
import cn.temobi.complex.entity.PmProductAccusation;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmProductAccusationService")
public class PmProductAccusationService extends ServiceBase{
	
	@Resource(name = "pmProductAccusationDao")
	private PmProductAccusationDao pmProductAccusationDao;
	
	public int update(PmProductAccusation entity){
		return pmProductAccusationDao.update(entity);
	}
	
	public Page<PmProductAccusation> findByPage(Page page,Object map){
		return pmProductAccusationDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmProductAccusationDao.getCount(map);
	}
	
	public PmProductAccusation getById(Long id){
		return pmProductAccusationDao.getById(id);
	}
	
	public int save(PmProductAccusation entity){
		return pmProductAccusationDao.save(entity);
	}
	
	public int delete(Object id){
		return pmProductAccusationDao.delete(id);
	}
	
	public int delete(PmProductAccusation entity){
		return pmProductAccusationDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmProductAccusationDao.findByMap(map);
	}
    
	public List<PmProductAccusation> findAll(){
		return pmProductAccusationDao.findAll();
	}
	
	public List<PmProductAccusation> findAll(PmProductAccusation entity){
		return pmProductAccusationDao.findAll(entity);
	}
	
	public Page<PmProductAccusationDto> findDtoByPage(Page page,Object map){
		return pmProductAccusationDao.findDtoByPage(page, map);
	}
}
