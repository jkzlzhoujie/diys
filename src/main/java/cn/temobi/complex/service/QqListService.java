package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.QqListDao;
import cn.temobi.complex.entity.QqList;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("qqListService")
public class QqListService extends ServiceBase{

	@Resource(name = "qqListDao")
	private QqListDao qqListDao;
	

	public int update(QqList qqList){
		return qqListDao.update(qqList);
	}
	
	public Page<QqList> findByPage(Page page,Object map){
		return qqListDao.findByPage(page, map);
	}
	
	public List<QqList> findAll(){
		return qqListDao.findAll();
	}
	
	public List<QqList> findByMap(Map param){
		return qqListDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return qqListDao.getCount(map);
	}
	
	public QqList getById(Long id){
		return qqListDao.getById(id);
	}
	
	public int save(QqList qqList){
		return qqListDao.save(qqList);
	}
	
	public int delete(Object id){
		return qqListDao.delete(id);
	}
}
