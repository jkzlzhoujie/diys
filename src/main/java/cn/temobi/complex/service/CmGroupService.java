package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmGroupDao;
import cn.temobi.complex.entity.CmGroup;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmGroupService")
public class CmGroupService extends ServiceBase{

	
	@Resource(name = "cmGroupDao")
	private CmGroupDao cmGroupDao;
	
	public Page<CmGroup> findByPage(Page page,Object map){
		return cmGroupDao.findByPage(page, map);
	}

	public Number getCount(Map map){
		return cmGroupDao.getCount(map);
	}
	
	public CmGroup getById(Long id){
		return cmGroupDao.getById(id);
	}
	
	public int save(CmGroup entity){
		return cmGroupDao.save(entity);
	}
	
	public int delete(Object id){
		return cmGroupDao.delete(id);
	}
	
	public int delete(CmGroup entity){
		return cmGroupDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmGroupDao.findByMap(map);
	}

	public List<CmGroup> findAll(){
		return cmGroupDao.findAll();
	}
	
	public List<CmGroup> findAll(CmGroup entity){
		return cmGroupDao.findAll(entity);
	}
	
	public int update(CmGroup entity){
		return cmGroupDao.update(entity);
	}
}
