package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmTopicLooksDao;
import cn.temobi.complex.entity.PmTopicLooks;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmTopicLooksService")
public class PmTopicLooksService extends ServiceBase{
	
	@Resource(name = "pmTopicLooksDao")
	private PmTopicLooksDao pmTopicLooksDao;
	
	public int update(PmTopicLooks entity){
		return pmTopicLooksDao.update(entity);
	}
	
	public Page<PmTopicLooks> findByPage(Page page,Object map){
		return pmTopicLooksDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmTopicLooksDao.getCount(map);
	}
	
	public PmTopicLooks getById(Long id){
		return pmTopicLooksDao.getById(id);
	}
	
	public int save(PmTopicLooks entity){
		return pmTopicLooksDao.save(entity);
	}
	
	public int delete(Object id){
		return pmTopicLooksDao.delete(id);
	}
	
	public int delete(PmTopicLooks entity){
		return pmTopicLooksDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmTopicLooksDao.findByMap(map);
	}
    
	public List<PmTopicLooks> findAll(){
		return pmTopicLooksDao.findAll();
	}
	
	public List<PmTopicLooks> findAll(PmTopicLooks entity){
		return pmTopicLooksDao.findAll(entity);
	}
}
