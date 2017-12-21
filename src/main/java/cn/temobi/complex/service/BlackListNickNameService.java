package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.BlackListNickNameDao;
import cn.temobi.complex.entity.BlackListNickName;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("blackListNickNameService")
public class BlackListNickNameService extends ServiceBase{

	@Resource(name = "blackListNickNameDao")
	private BlackListNickNameDao blackListNickNameDao;
	
	public Number checkContent(Map map){
		return blackListNickNameDao.checkContent(map);
	}
	
	public int update(BlackListNickName blackListNickName){
		return blackListNickNameDao.update(blackListNickName);
	}
	
	public Page<BlackListNickName> findByPage(Page page,Object map){
		return blackListNickNameDao.findByPage(page, map);
	}
	
	public List<BlackListNickName> findAll(){
		return blackListNickNameDao.findAll();
	}
	
	public List<BlackListNickName> findByMap(Map param){
		return blackListNickNameDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return blackListNickNameDao.getCount(map);
	}
	
	public BlackListNickName getById(Long id){
		return blackListNickNameDao.getById(id);
	}
	
	public int save(BlackListNickName blackListNickName){
		return blackListNickNameDao.save(blackListNickName);
	}
	
	public int delete(Object id){
		return blackListNickNameDao.delete(id);
	}
}
