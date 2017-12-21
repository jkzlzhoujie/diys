package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserAttentionsDao;
import cn.temobi.complex.entity.CmUserAttentions;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserAttentionsService")
public class CmUserAttentionsService extends ServiceBase{
	
	@Resource(name = "cmUserAttentionsDao")
	private CmUserAttentionsDao cmUserAttentionsDao;
	
	public int update(CmUserAttentions entity){
		return cmUserAttentionsDao.update(entity);
	}
	
	public Page<CmUserAttentions> findByPage(Page page,Object map){
		return cmUserAttentionsDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserAttentionsDao.getCount(map);
	}
	
	public CmUserAttentions getById(Long id){
		return cmUserAttentionsDao.getById(id);
	}
	
	public int save(CmUserAttentions entity){
		return cmUserAttentionsDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserAttentionsDao.delete(id);
	}
	
	public int delete(CmUserAttentions entity){
		return cmUserAttentionsDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserAttentionsDao.findByMap(map);
	}
    
	public List<CmUserAttentions> findAll(){
		return cmUserAttentionsDao.findAll();
	}
	
	public List<CmUserAttentions> findAll(CmUserAttentions entity){
		return cmUserAttentionsDao.findAll(entity);
	}
}
