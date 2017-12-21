package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserMessageDao;
import cn.temobi.complex.dto.CmUserMessageDto;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserMessageService")
public class CmUserMessageService extends ServiceBase{
	
	@Resource(name = "cmUserMessageDao")
	private CmUserMessageDao cmUserMessageDao;
	
	public int update(CmUserMessage entity){
		return cmUserMessageDao.update(entity);
	}
	
	public Page<CmUserMessage> findByPage(Page page,Object map){
		return cmUserMessageDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserMessageDao.getCount(map);
	}
	
	public CmUserMessage getById(Long id){
		return cmUserMessageDao.getById(id);
	}
	
	public int save(CmUserMessage entity){
		return cmUserMessageDao.save(entity);
	}
	
	public int delete(Object id){
		return cmUserMessageDao.delete(id);
	}
	
	public int delete(CmUserMessage entity){
		return cmUserMessageDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserMessageDao.findByMap(map);
	}
    
	public List<CmUserMessage> findAll(){
		return cmUserMessageDao.findAll();
	}
	
	public List<CmUserMessage> findAll(CmUserMessage entity){
		return cmUserMessageDao.findAll(entity);
	}
	
	public Page<CmUserMessageDto> findDtoByMap(Page page,Object map){
		return cmUserMessageDao.findByPage(page,"findDtoByMap", map);
	}
}
