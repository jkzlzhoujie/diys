package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmTopicInfoDao;
import cn.temobi.complex.entity.PmTopicInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmTopicInfoService")
public class PmTopicInfoService extends ServiceBase{
	
	@Resource(name = "pmTopicInfoDao")
	private PmTopicInfoDao pmTopicInfoDao;
	
	public int update(PmTopicInfo entity){
		return pmTopicInfoDao.update(entity);
	}
	
	public Page<PmTopicInfo> findByPage(Page page,Object map){
		return pmTopicInfoDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return pmTopicInfoDao.getCount(map);
	}
	
	public PmTopicInfo getById(Long id){
		return pmTopicInfoDao.getById(id);
	}
	
	public int save(PmTopicInfo entity){
		return pmTopicInfoDao.save(entity);
	}
	
	public int delete(Object id){
		return pmTopicInfoDao.delete(id);
	}
	
	public int delete(PmTopicInfo entity){
		return pmTopicInfoDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmTopicInfoDao.findByMap(map);
	}
    
	public List<PmTopicInfo> findAll(){
		return pmTopicInfoDao.findAll();
	}
	
	public List<PmTopicInfo> findAll(PmTopicInfo entity){
		return pmTopicInfoDao.findAll(entity);
	}
}
