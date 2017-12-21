package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmProductDiscussDao;
import cn.temobi.complex.dto.PmProductDiscussDto;
import cn.temobi.complex.entity.PmProductDiscuss;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmProductDiscussService")
public class PmProductDiscussService extends ServiceBase{
	
	@Resource(name = "pmProductDiscussDao")
	private PmProductDiscussDao pmProductDiscussDao;
	
	public int update(PmProductDiscuss entity){
		return pmProductDiscussDao.update(entity);
	}
	
	public Page<PmProductDiscuss> findByPage(Page page,Object map){
		return pmProductDiscussDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmProductDiscussDao.getCount(map);
	}
	
	public PmProductDiscuss getById(Long id){
		return pmProductDiscussDao.getById(id);
	}
	
	public int save(PmProductDiscuss entity){
		return pmProductDiscussDao.save(entity);
	}
	
	public int delete(Object id){
		return pmProductDiscussDao.delete(id);
	}
	
	public int delete(PmProductDiscuss entity){
		return pmProductDiscussDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmProductDiscussDao.findByMap(map);
	}
	
	public List findDto(Map map){
		return pmProductDiscussDao.findDto(map);
	}

	public List findDtoTo(Map map){
		return pmProductDiscussDao.findDtoTo(map);
	}
    
	public List findMyDiscuss(Map map){
		return pmProductDiscussDao.findMyDiscuss(map);
	}
	
	public List<PmProductDiscuss> findAll(){
		return pmProductDiscussDao.findAll();
	}
	
	public List<PmProductDiscuss> findAll(PmProductDiscuss entity){
		return pmProductDiscussDao.findAll(entity);
	}
	
	public Page<PmProductDiscussDto> findByPageTo(Page page,Object map){
		return pmProductDiscussDao.findByPage(page, "findDto", map);
	}

	public Number findDiscussCount(Map<String, Object> map) {
		return pmProductDiscussDao.findDiscussCount(map);
	}

	public List<Long> findIdList(Map<String, Object> map) {
		return pmProductDiscussDao.findIdList(map);
	}
}
