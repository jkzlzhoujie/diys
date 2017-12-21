package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserPrivilegeDao;
import cn.temobi.complex.entity.CmUserPrivilege;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserPrivilegeService")
public class CmUserPrivilegeService extends ServiceBase{

	@Resource(name = "cmUserPrivilegeDao")
	private CmUserPrivilegeDao cmUserPrivilegeDao;
	
	public int update(CmUserPrivilege cmUserPrivilege){
		return cmUserPrivilegeDao.update(cmUserPrivilege);
	}
	
	public Page<CmUserPrivilege> findByPage(Page page,Object map){
		return cmUserPrivilegeDao.findByPage(page, map);
	}
	
	public List<CmUserPrivilege> findAll(){
		return cmUserPrivilegeDao.findAll();
	}
	
	public List<CmUserPrivilege> findByMap(Map param){
		return cmUserPrivilegeDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return cmUserPrivilegeDao.getCount(map);
	}
	
	public CmUserPrivilege getById(Long id){
		return cmUserPrivilegeDao.getById(id);
	}
	
	public int save(CmUserPrivilege cmUserPrivilege){
		return cmUserPrivilegeDao.save(cmUserPrivilege);
	}
	
	public int delete(Object id){
		return cmUserPrivilegeDao.delete(id);
	}
}
