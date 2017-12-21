package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmCircleUserDao;
import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.entity.CmCircleUser;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmCircleUserService")
public class CmCircleUserService extends ServiceBase{

	@Resource(name = "cmCircleUserDao")
	private CmCircleUserDao cmCircleUserDao;
	
	public void upByCircleId(Map map){
		cmCircleUserDao.upByCircleId(map);
	}
	
	public Long delBycircleId(Map map){
		return cmCircleUserDao.delBycircleId(map);
	}
	
	public Long check(Map map){
		return cmCircleUserDao.check(map);
	} 
	
	public int update(CmCircleUser entity){
		return cmCircleUserDao.update(entity);
	}
	
	public Page<CmCircleUser> findByPage(Page page,Object map){
		return cmCircleUserDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmCircleUserDao.getCount(map);
	}
	
	public CmCircleUser getById(Long id){
		return cmCircleUserDao.getById(id);
	}
	
	public int save(CmCircleUser entity){
		return cmCircleUserDao.save(entity);
	}
	
	public void insertList(Map<String,Object> map){
		cmCircleUserDao.insertList(map);
	}
	
	public int delete(Object id){
		return cmCircleUserDao.delete(id);
	}
	
	public int delete(CmCircleUser entity){
		return cmCircleUserDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmCircleUserDao.findByMap(map);
	}
    
	public List<CmCircleUser> findAll(){
		return cmCircleUserDao.findAll();
	}
	
	public Page<CmUserInfoListDto> findUser(Page page,Object map){
		return cmCircleUserDao.findUser(page, map);
	}
	
	public List<Long> findUserId(Object map){
		return cmCircleUserDao.findUserId(map);
	}

	public Page findByPageTwo(Page page, Map<String, String> map) {
		return cmCircleUserDao.findByPageTwo(page, map);
	}
}
