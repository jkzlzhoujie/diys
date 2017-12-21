package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysProductTypeInfoDao;
import cn.temobi.complex.dto.AllTypeDto;
import cn.temobi.complex.entity.SysProductTypeInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysProductTypeInfoService")
public class SysProductTypeInfoService extends ServiceBase{

	@Resource(name = "sysProductTypeInfoDao")
	private SysProductTypeInfoDao sysProductTypeInfoDao;
	
	public int update(SysProductTypeInfo entity){
		return sysProductTypeInfoDao.update(entity);
	}
	
	public Page<SysProductTypeInfo> findByPage(Page page,Object map){
		return sysProductTypeInfoDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return sysProductTypeInfoDao.getCount(map);
	}
	
	public SysProductTypeInfo getById(Long id){
		return sysProductTypeInfoDao.getById(id);
	}
	
	public int save(SysProductTypeInfo entity){
		return sysProductTypeInfoDao.save(entity);
	}
	
	public int delete(Object id){
		return sysProductTypeInfoDao.delete(id);
	}
	
	public int delete(SysProductTypeInfo entity){
		return sysProductTypeInfoDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return sysProductTypeInfoDao.findByMap(map);
	}
    
	public List<SysProductTypeInfo> findAll(){
		return sysProductTypeInfoDao.findAll();
	}
	
	public List<SysProductTypeInfo> findAll(SysProductTypeInfo entity){
		return sysProductTypeInfoDao.findAll(entity);
	}
	
	public List<SysProductTypeInfo> findByUserId(Map map){
		return sysProductTypeInfoDao.findByUserId(map);
	}
	
	public List<AllTypeDto> findAllType(Map map){
		return sysProductTypeInfoDao.findAllType(map);
	}
}
