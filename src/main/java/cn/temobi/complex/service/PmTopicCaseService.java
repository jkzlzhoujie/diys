package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmTopicCaseDao;
import cn.temobi.complex.entity.PmTopicCase;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmTopicCaseService")
public class PmTopicCaseService extends ServiceBase{
	
	@Resource(name = "pmTopicCaseDao")
	private PmTopicCaseDao pmTopicCaseDao;
	
	public int update(PmTopicCase entity){
		return pmTopicCaseDao.update(entity);
	}
	
	public Page<PmTopicCase> findByPage(Page page,Object map){
		return pmTopicCaseDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmTopicCaseDao.getCount(map);
	}
	
	public PmTopicCase getById(Long id){
		return pmTopicCaseDao.getById(id);
	}
	
	public int save(PmTopicCase entity){
		return pmTopicCaseDao.save(entity);
	}
	
	public int delete(Object id){
		return pmTopicCaseDao.delete(id);
	}
	
	public int delete(PmTopicCase entity){
		return pmTopicCaseDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmTopicCaseDao.findByMap(map);
	}
    
	public List<PmTopicCase> findAll(){
		return pmTopicCaseDao.findAll();
	}
	
	public List<PmTopicCase> findAll(PmTopicCase entity){
		return pmTopicCaseDao.findAll(entity);
	}
}
