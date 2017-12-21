package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.SystemRightsDao;
import cn.temobi.complex.entity.SystemRights;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 权限管理
 * @author hjf
 * 2014-3-5 下午03:45:21
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("systemRightsService")
public class SystemRightsService extends ServiceBase{
	
	@Resource(name = "systemRightsDao")
	private SystemRightsDao systemRightsDao;
	
	public int update(SystemRights systemRight){
		return systemRightsDao.update(systemRight);
	}
	
	public Page<SystemRights> findByPage(Page page,Object map){
		return systemRightsDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return systemRightsDao.getCount(map);
	}
	
	public SystemRights getById(Long id){
		return systemRightsDao.getById(id);
	}
	
	public int save(SystemRights systemRight){
		return systemRightsDao.save(systemRight);
	}
	
	public int delete(Object id){
		return systemRightsDao.delete(id);
	}
	
	public int delete(SystemRights systemRight){
		return systemRightsDao.delete(systemRight);
	}
	
	public List findByMap(Map map){
		return systemRightsDao.findByMap(map);
	}
    
	public List<SystemRights> findAll(){
		return systemRightsDao.findAll();
	}
	
	public List<SystemRights> findAll(SystemRights systemRight){
		return systemRightsDao.findAll(systemRight);
	}
}
