package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.MessageDao;
import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.Message;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 分类管理
 * @author hjf
 * 2014 三月 17 17:17:17
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("messageService")
public class MessageService extends ServiceBase{
	
	@Resource(name = "messageDao")
	private MessageDao messageDao;
	
	public int update(Message entity){
		return messageDao.update(entity);
	}
	
	public Page<Message> findByPage(Page page,Object map){
		return messageDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return messageDao.getCount(map);
	}
	
	public Message getById(Long id){
		return messageDao.getById(id);
	}
	
	public int save(Message entity){
		return messageDao.save(entity);
	}
	
	public int delete(Object id){
		return messageDao.delete(id);
	}
	
	public int delete(Message entity){
		return messageDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return messageDao.findByMap(map);
	}
    
	public List<Message> findAll(){
		return messageDao.findAll();
	}
	
	public List<Message> findAll(Message entity){
		return messageDao.findAll(entity);
	}
}
