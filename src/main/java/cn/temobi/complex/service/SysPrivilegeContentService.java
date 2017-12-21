package cn.temobi.complex.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.SysPrivilegeContentDao;
import cn.temobi.complex.entity.SysPrivilegeContent;
import cn.temobi.complex.entity.SysPrivilegePackage;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysPrivilegeContentService")
public class SysPrivilegeContentService extends ServiceBase{

	@Resource(name = "sysPrivilegeContentDao")
	private SysPrivilegeContentDao sysPrivilegeContentDao;
	
	public int update(SysPrivilegeContent sysPrivilegeContent){
		return sysPrivilegeContentDao.update(sysPrivilegeContent);
	}
	
	public Page<SysPrivilegeContent> findByPage(Page page,Object map){
		return sysPrivilegeContentDao.findByPage(page, map);
	}
	
	public List<SysPrivilegeContent> findAll(){
		return sysPrivilegeContentDao.findAll();
	}
	
	public List<SysPrivilegeContent> findByMap(Map param){
		return sysPrivilegeContentDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return sysPrivilegeContentDao.getCount(map);
	}
	
	public SysPrivilegeContent getById(Long id){
		return sysPrivilegeContentDao.getById(id);
	}
	
	public int save(SysPrivilegeContent sysPrivilegeContent){
		return sysPrivilegeContentDao.save(sysPrivilegeContent);
	}
	
	public int delete(Object id){
		return sysPrivilegeContentDao.delete(id);
	}

	public void setPrivilegeContent(String privilegeId, String [] contentId) {
		sysPrivilegeContentDao.deleteByPrivilegeId(privilegeId);
		for (int i = 0; i < contentId.length; i++) {
			SysPrivilegeContent sysPrivilegeContent = new SysPrivilegeContent();
			sysPrivilegeContent.setContentTypeId(contentId[i]);
			sysPrivilegeContent.setPrivilegeId(privilegeId);
			sysPrivilegeContent.setUpdateWhen(new Date());
			sysPrivilegeContentDao.save(sysPrivilegeContent);
		}
	}
}
