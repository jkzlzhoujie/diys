package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserExtendInfoDao;
import cn.temobi.complex.entity.CmUserExtendInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserExtendInfoService")
public class CmUserExtendInfoService extends ServiceBase{
	
	@Resource(name = "cmUserExtendInfoDao")
	private CmUserExtendInfoDao cmUserExtendInfoDao;
	
	public int update(CmUserExtendInfo entity){
		return cmUserExtendInfoDao.update(entity);
	}
	
	public Page<CmUserExtendInfo> findByPage(Page page,Object map){
		return cmUserExtendInfoDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserExtendInfoDao.getCount(map);
	}
	
	public CmUserExtendInfo getById(Long id){
		return cmUserExtendInfoDao.getById(id);
	}
	
	public int save(CmUserExtendInfo entity){
		return cmUserExtendInfoDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserExtendInfoDao.delete(id);
	}
	
	public int delete(CmUserExtendInfo entity){
		return cmUserExtendInfoDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserExtendInfoDao.findByMap(map);
	}
    
	public List<CmUserExtendInfo> findAll(){
		return cmUserExtendInfoDao.findAll();
	}
	
	public List<CmUserExtendInfo> findAll(CmUserExtendInfo entity){
		return cmUserExtendInfoDao.findAll(entity);
	}
}
