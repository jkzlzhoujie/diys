package cn.temobi.complex.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysPrivilegePackageDao;
import cn.temobi.complex.entity.SysPrivilegePackage;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysPrivilegePackageService")
public class SysPrivilegePackageService extends ServiceBase{

	@Resource(name = "sysPrivilegePackageDao")
	private SysPrivilegePackageDao sysPrivilegePackageDao;
	
	public int update(SysPrivilegePackage sysPrivilegePackage){
		return sysPrivilegePackageDao.update(sysPrivilegePackage);
	}
	
	public Page<SysPrivilegePackage> findByPage(Page page,Object map){
		return sysPrivilegePackageDao.findByPage(page, map);
	}
	
	public List<SysPrivilegePackage> findAll(){
		return sysPrivilegePackageDao.findAll();
	}
	
	public List<SysPrivilegePackage> findByMap(Map param){
		return sysPrivilegePackageDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return sysPrivilegePackageDao.getCount(map);
	}
	
	public SysPrivilegePackage getById(Long id){
		return sysPrivilegePackageDao.getById(id);
	}
	
	public int save(SysPrivilegePackage sysPrivilegePackage){
		return sysPrivilegePackageDao.save(sysPrivilegePackage);
	}
	
	public int delete(Object id){
		return sysPrivilegePackageDao.delete(id);
	}
	
	public void setPrivilegePackage(String privilegeId, String [] packageId) {
		sysPrivilegePackageDao.deleteByPrivilegeId(privilegeId);
		for (int i = 0; i < packageId.length; i++) {
			SysPrivilegePackage sysPrivilegePackage = new SysPrivilegePackage();
			sysPrivilegePackage.setPackageId(packageId[i]);
			sysPrivilegePackage.setPrivilegeId(privilegeId);
			sysPrivilegePackage.setUpdateWhen(new Date());
			sysPrivilegePackageDao.save(sysPrivilegePackage);
		}
	}
	
}
