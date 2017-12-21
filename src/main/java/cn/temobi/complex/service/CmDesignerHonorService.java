package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmDesignerHonorDao;
import cn.temobi.complex.entity.CmDesignerHonor;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmDesignerHonorService")
public class CmDesignerHonorService extends ServiceBase{

	@Resource(name = "cmDesignerHonorDao")
	private CmDesignerHonorDao cmDesignerHonorDao;
	
	
	public int update(CmDesignerHonor cmDesignerHonor){
		return cmDesignerHonorDao.update(cmDesignerHonor);
	}
	
	public Page<CmDesignerHonor> findByPage(Page page,Object map){
		return cmDesignerHonorDao.findByPage(page, map);
	}
	
	public List<CmDesignerHonor> findAll(){
		return cmDesignerHonorDao.findAll();
	}
	
	public List<CmDesignerHonor> findByMap(Map param){
		return cmDesignerHonorDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return cmDesignerHonorDao.getCount(map);
	}
	
	public CmDesignerHonor getById(Long id){
		return cmDesignerHonorDao.getById(id);
	}
	
	public int save(CmDesignerHonor cmDesignerHonor){
		return cmDesignerHonorDao.save(cmDesignerHonor);
	}
	
	public int delete(long id){
		return cmDesignerHonorDao.delete(id);
	}

	public void saveBatch(List<CmDesignerHonor> cmDesignerHonorList) {
		cmDesignerHonorDao.saveBatch(cmDesignerHonorList);
	}
	
	
	
	
	
}
