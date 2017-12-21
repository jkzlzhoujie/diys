package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmProductLabelDao;
import cn.temobi.complex.entity.PmProductLabel;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmProductLabelService")
public class PmProductLabelService extends ServiceBase{

	@Resource(name = "pmProductLabelDao")
	private PmProductLabelDao pmProductLabelDao;
	
	public int update(PmProductLabel pmProductLabel){
		return pmProductLabelDao.update(pmProductLabel);
	}
	
	public Page<PmProductLabel> findByPage(Page page,Object map){
		return pmProductLabelDao.findByPage(page, map);
	}
	
	public List<PmProductLabel> findAll(){
		return pmProductLabelDao.findAll();
	}
	
	public List<PmProductLabel> findByMap(Map param){
		return pmProductLabelDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return pmProductLabelDao.getCount(map);
	}
	
	public PmProductLabel getById(Long id){
		return pmProductLabelDao.getById(id);
	}
	
	public int save(PmProductLabel pmProductLabel){
		return pmProductLabelDao.save(pmProductLabel);
	}
	
	public int delete(Object id){
		return pmProductLabelDao.delete(id);
	}
}
