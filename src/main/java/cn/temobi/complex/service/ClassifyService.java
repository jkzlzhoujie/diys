package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.temobi.complex.dao.ClassifyDao;
import cn.temobi.complex.entity.Classify;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;

/**
 * 分类管理
 * @author hjf
 * 2014 三月 17 17:17:17
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("classifyService")
public class ClassifyService extends ServiceBase{
	
	@Resource(name = "classifyDao")
	private ClassifyDao classifyDao;

	public int update(Classify entity){
		return classifyDao.update(entity);
	}
	
	public Page<Classify> findByPage(Page page,Object map){
		return classifyDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return classifyDao.getCount(map);
	}
	
	public Classify getById(Long id){
		return classifyDao.getById(id);
	}
	
	public int save(Classify entity){
		return classifyDao.save(entity);
	}
	
	public int delete(Object id){
		return classifyDao.delete(id);
	}
	
	public int delete(Classify entity){
		return classifyDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return classifyDao.findByMap(map);
	}
    
	public List<Classify> findAll(){
		return classifyDao.findAll();
	}
	
	public List<Classify> findAll(Classify entity){
		return classifyDao.findAll(entity);
	}
}
