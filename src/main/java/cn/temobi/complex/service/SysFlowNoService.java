package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysFlowNoDao;
import cn.temobi.complex.entity.SysFlowNo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysFlowNoService")
public class SysFlowNoService extends ServiceBase{
	
	@Resource(name = "sysFlowNoDao")
	private SysFlowNoDao sysFlowNoDao;
	
	public int update(SysFlowNo entity){
		return sysFlowNoDao.update(entity);
	}
	
	public Page<SysFlowNo> findByPage(Page page,Object map){
		return sysFlowNoDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return sysFlowNoDao.getCount(map);
	}
	
	public SysFlowNo getById(Long id){
		return sysFlowNoDao.getById(id);
	}
	
	public int save(SysFlowNo entity){
		return sysFlowNoDao.save(entity);
	}
	
	public int delete(Object id){
		return sysFlowNoDao.delete(id);
	}
	
	public int delete(SysFlowNo entity){
		return sysFlowNoDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return sysFlowNoDao.findByMap(map);
	}
    
	public List<SysFlowNo> findAll(){
		return sysFlowNoDao.findAll();
	}
	
	public List<SysFlowNo> findAll(SysFlowNo entity){
		return sysFlowNoDao.findAll(entity);
	}
}
