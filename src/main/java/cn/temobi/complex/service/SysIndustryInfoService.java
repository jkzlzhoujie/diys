package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysIndustryInfoDao;
import cn.temobi.complex.entity.SysIndustryInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysIndustryInfoService")
public class SysIndustryInfoService extends ServiceBase{
	
	@Resource(name = "sysIndustryInfoDao")
	private SysIndustryInfoDao sysIndustryInfoDao;
	
	public int update(SysIndustryInfo entity){
		return sysIndustryInfoDao.update(entity);
	}
	
	public Page<SysIndustryInfo> findByPage(Page page,Object map){
		return sysIndustryInfoDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return sysIndustryInfoDao.getCount(map);
	}
	
	public SysIndustryInfo getById(Long id){
		return sysIndustryInfoDao.getById(id);
	}
	
	public int save(SysIndustryInfo entity){
		return sysIndustryInfoDao.save(entity);
	}
	
	public int delete(Object id){
		return sysIndustryInfoDao.delete(id);
	}
	
	public int delete(SysIndustryInfo entity){
		return sysIndustryInfoDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return sysIndustryInfoDao.findByMap(map);
	}
    
	public List<SysIndustryInfo> findAll(){
		return sysIndustryInfoDao.findAll();
	}
	
	public List<SysIndustryInfo> findAll(SysIndustryInfo entity){
		return sysIndustryInfoDao.findAll(entity);
	}
}
