package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysBackupLabelsDao;
import cn.temobi.complex.entity.SysBackupLabels;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysBackupLabelsService")
public class SysBackupLabelsService extends ServiceBase{
	
	@Resource(name = "sysBackupLabelsDao")
	private SysBackupLabelsDao sysBackupLabelsDao;
	
	public int update(SysBackupLabels entity){
		return sysBackupLabelsDao.update(entity);
	}
	
	public Page<SysBackupLabels> findByPage(Page page,Object map){
		return sysBackupLabelsDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return sysBackupLabelsDao.getCount(map);
	}
	
	public SysBackupLabels getById(Long id){
		return sysBackupLabelsDao.getById(id);
	}
	
	public int save(SysBackupLabels entity){
		return sysBackupLabelsDao.save(entity);
	}
	
	public int delete(Object id){
		return sysBackupLabelsDao.delete(id);
	}
	
	public int delete(SysBackupLabels entity){
		return sysBackupLabelsDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return sysBackupLabelsDao.findByMap(map);
	}
    
	public List<SysBackupLabels> findAll(){
		return sysBackupLabelsDao.findAll();
	}
	
	public List<SysBackupLabels> findAll(SysBackupLabels entity){
		return sysBackupLabelsDao.findAll(entity);
	}
}
