package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysHotLabelsDao;
import cn.temobi.complex.entity.Application;
import cn.temobi.complex.entity.SysHotLabels;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysHotLabelsService")
public class SysHotLabelsService extends ServiceBase{
	
	@Resource(name = "sysHotLabelsDao")
	private SysHotLabelsDao sysHotLabelsDao;
	
	public int update(SysHotLabels entity){
		return sysHotLabelsDao.update(entity);
	}
	
	public Page<SysHotLabels> findByPage(Page page,Object map){
		return sysHotLabelsDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return sysHotLabelsDao.getCount(map);
	}
	
	public SysHotLabels getById(Long id){
		return sysHotLabelsDao.getById(id);
	}
	
	public int save(SysHotLabels entity){
		return sysHotLabelsDao.save(entity);
	}
	
	public int delete(Object id){
		return sysHotLabelsDao.delete(id);
	}
	
	public int delete(SysHotLabels entity){
		return sysHotLabelsDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return sysHotLabelsDao.findByMap(map);
	}
    
	public List<SysHotLabels> findAll(){
		return sysHotLabelsDao.findAll();
	}
	
	public List<SysHotLabels> findAll(SysHotLabels entity){
		return sysHotLabelsDao.findAll(entity);
	}
}
