package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.BlackListDao;
import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("blackListService")
public class BlackListService extends ServiceBase{

	@Resource(name = "blackListDao")
	private BlackListDao blackListDao;
	
	public Number checkContent(Map map){
		return blackListDao.checkContent(map);
	}
	
	public int update(BlackListWord blackList){
		return blackListDao.update(blackList);
	}
	
	public Page<BlackListWord> findByPage(Page page,Object map){
		return blackListDao.findByPage(page, map);
	}
	
	public List<BlackListWord> findAll(){
		return blackListDao.findAll();
	}
	
	public List<BlackListWord> findByMap(Map param){
		return blackListDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return blackListDao.getCount(map);
	}
	
	public BlackListWord getById(Long id){
		return blackListDao.getById(id);
	}
	
	public int save(BlackListWord blackList){
		return blackListDao.save(blackList);
	}
	
	public int delete(Object id){
		return blackListDao.delete(id);
	}
}
