package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserGroupDao;
import cn.temobi.complex.dto.CmUserGroupDto;
import cn.temobi.complex.entity.CmUserGroup;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserGroupService")
public class CmUserGroupService extends ServiceBase{

	
	@Resource(name = "cmUserGroupDao")
	private CmUserGroupDao cmUserGroupDao;
	
	public Page<CmUserGroup> findByPage(Page page,Object map){
		return cmUserGroupDao.findByPage(page, map);
	}
	
	public Page<CmUserGroupDto> findByPageDto(Page page,Object map){
		return cmUserGroupDao.findByPage(page, "findByPageDto", map);
	}
	
	public Number getCount(Map map){
		return cmUserGroupDao.getCount(map);
	}
	
	public CmUserGroup getById(Long id){
		return cmUserGroupDao.getById(id);
	}
	
	public int save(CmUserGroup entity){
		return cmUserGroupDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserGroupDao.delete(id);
	}
	
	public int deleteByGroupId(Object param){
		return cmUserGroupDao.deleteByGroupId(param);
	}
	
	public int delete(CmUserGroup entity){
		return cmUserGroupDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserGroupDao.findByMap(map);
	}

	public List<CmUserGroup> findAll(){
		return cmUserGroupDao.findAll();
	}
	
	public List<CmUserGroup> findAll(CmUserGroup entity){
		return cmUserGroupDao.findAll(entity);
	}
	
	public int update(CmUserGroup entity){
		return cmUserGroupDao.update(entity);
	}
	
	public int updateByGroupId(Map map){
		return cmUserGroupDao.updateByGroupId(map);
	}
}
