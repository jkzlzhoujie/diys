package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysPrivilegeContentTypeDao;
import cn.temobi.complex.entity.SysPrivilegeContentType;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysPrivilegeContentTypeService")
public class SysPrivilegeContentTypeService extends ServiceBase{

	@Resource(name = "sysPrivilegeContentTypeDao")
	private SysPrivilegeContentTypeDao sysPrivilegeContentTypeDao;
	
	public int update(SysPrivilegeContentType sysPrivilegeContentType){
		return sysPrivilegeContentTypeDao.update(sysPrivilegeContentType);
	}
	
	public Page<SysPrivilegeContentType> findByPage(Page page,Object map){
		return sysPrivilegeContentTypeDao.findByPage(page, map);
	}
	
	public List<SysPrivilegeContentType> findAll(){
		return sysPrivilegeContentTypeDao.findAll();
	}
	
	public List<SysPrivilegeContentType> findByMap(Map param){
		return sysPrivilegeContentTypeDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return sysPrivilegeContentTypeDao.getCount(map);
	}
	
	public SysPrivilegeContentType getById(Long id){
		return sysPrivilegeContentTypeDao.getById(id);
	}
	
	public int save(SysPrivilegeContentType sysPrivilegeContentType){
		return sysPrivilegeContentTypeDao.save(sysPrivilegeContentType);
	}
	
	public int delete(Object id){
		return sysPrivilegeContentTypeDao.delete(id);
	}
}
