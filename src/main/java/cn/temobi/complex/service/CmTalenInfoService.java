package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmTalenInfoDao;
import cn.temobi.complex.dto.CmTalenInfoDto;
import cn.temobi.complex.entity.CmTalenInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmTalenInfoService")
public class CmTalenInfoService extends ServiceBase{
	
	@Resource(name = "cmTalenInfoDao")
	private CmTalenInfoDao cmTalenInfoDao;
	
	public int update(CmTalenInfo entity){
		return cmTalenInfoDao.update(entity);
	}
	
	public Page<CmTalenInfo> findByPage(Page page,Object map){
		return cmTalenInfoDao.findByPage(page, map);
	}
	
	public Page<CmTalenInfoDto> findDtoByPage(Page page,Object map){
		return cmTalenInfoDao.findByPage(page,"findDtoByPage", map);
	}
	
	public Number getCount(Map map){
		return cmTalenInfoDao.getCount(map);
	}
	
	public CmTalenInfo getById(Long id){
		return cmTalenInfoDao.getById(id);
	}
	
	public int save(CmTalenInfo entity){
		return cmTalenInfoDao.save(entity);
	}
	
	public int delete(Object id){
		return cmTalenInfoDao.delete(id);
	}
	
	public int delete(CmTalenInfo entity){
		return cmTalenInfoDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmTalenInfoDao.findByMap(map);
	}
    
	public List<CmTalenInfo> findAll(){
		return cmTalenInfoDao.findAll();
	}
	
	public List<CmTalenInfo> findAll(CmTalenInfo entity){
		return cmTalenInfoDao.findAll(entity);
	}
}
