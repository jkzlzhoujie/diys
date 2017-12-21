package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserFansDao;
import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.entity.CmUserFans;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserFansService")
public class CmUserFansService extends ServiceBase{
	
	@Resource(name = "cmUserFansDao")
	private CmUserFansDao cmUserFansDao;
	
	public int update(CmUserFans entity){
		return cmUserFansDao.update(entity);
	}
	
	public Page<CmUserFans> findByPage(Page page,Object map){
		return cmUserFansDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserFansDao.getCount(map);
	}
	
	public CmUserFans getById(Long id){
		return cmUserFansDao.getById(id);
	}
	
	public int save(CmUserFans entity){
		return cmUserFansDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserFansDao.delete(id);
	}
	
	public int delete(CmUserFans entity){
		return cmUserFansDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserFansDao.findByMap(map);
	}
    
	public List<CmUserFans> findAll(){
		return cmUserFansDao.findAll();
	}
	
	public List<CmUserFans> findAll(CmUserFans entity){
		return cmUserFansDao.findAll(entity);
	}
	
	public List<CmUserInfoListDto> findDto(Map<String,Object> map){
		return cmUserFansDao.findDto(map);
	}
	
	public Page<CmUserInfoListDto> findDtoPage(Page page,Object map){
		return cmUserFansDao.findByPage(page, "findDto", map);
	}
	
	public Page<CmUserInfoListDto> findFollowPage(Page page,Object map){
		return cmUserFansDao.findByPage(page, "findFollowDto", map);
	}
}
