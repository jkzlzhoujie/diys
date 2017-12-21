package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmDesignerProductInfoDao;
import cn.temobi.complex.entity.CmDesignerProductInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmDesignerProductInfoService")
public class CmDesignerProductInfoService extends ServiceBase{

	@Resource(name = "cmDesignerProductInfoDao")
	private CmDesignerProductInfoDao cmDesignerProductInfoDao;
	
	
	public int update(CmDesignerProductInfo cmDesignerProductInfo){
		return cmDesignerProductInfoDao.update(cmDesignerProductInfo);
	}
	
	public Page<CmDesignerProductInfo> findByPage(Page page,Object map){
		return cmDesignerProductInfoDao.findByPage(page, map);
	}
	
	public List<CmDesignerProductInfo> findAll(){
		return cmDesignerProductInfoDao.findAll();
	}
	
	public List<CmDesignerProductInfo> findByMap(Map param){
		return cmDesignerProductInfoDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return cmDesignerProductInfoDao.getCount(map);
	}
	
	public CmDesignerProductInfo getById(Long id){
		return cmDesignerProductInfoDao.getById(id);
	}
	
	public int save(CmDesignerProductInfo cmDesignerProductInfo){
		return cmDesignerProductInfoDao.save(cmDesignerProductInfo);
	}
	
	public int delete(long id){
		return cmDesignerProductInfoDao.delete(id);
	}

	

	
	
	
	
	
}
