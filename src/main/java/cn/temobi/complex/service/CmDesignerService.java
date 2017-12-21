package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmDesignerDao;
import cn.temobi.complex.entity.CmDesignerInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.DateUtils;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmDesignerService")
public class CmDesignerService extends ServiceBase{

	@Resource(name = "cmDesignerDao")
	private CmDesignerDao cmDesignerDao;
	
	
	public int update(CmDesignerInfo cmDesignerInfo){
		cmDesignerInfo.setUpdateTime(DateUtils.getCurrDateTimeStr());
		return cmDesignerDao.update(cmDesignerInfo);
	}
	
	public Page<CmDesignerInfo> findByPage(Page page,Object map){
		return cmDesignerDao.findByPage(page, map);
	}
	
	public List<CmDesignerInfo> findAll(){
		return cmDesignerDao.findAll();
	}
	
	public List<CmDesignerInfo> findByMap(Map param){
		return cmDesignerDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return cmDesignerDao.getCount(map);
	}
	
	public CmDesignerInfo getById(Long id){
		return cmDesignerDao.getById(id);
	}
	
	public int save(CmDesignerInfo CmDesignerInfo){
		return cmDesignerDao.save(CmDesignerInfo);
	}
	
	public int delete(Object id){
		return cmDesignerDao.delete(id);
	}

	

	
	
	
	
	
}
