package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmDesignerInfoPicDao;
import cn.temobi.complex.entity.CmDesignerInfoPic;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmDesignerInfoPicService")
public class CmDesignerInfoPicService extends ServiceBase{

	@Resource(name = "cmDesignerInfoPicDao")
	private CmDesignerInfoPicDao cmDesignerInfoPicDao;
	
	
	public int update(CmDesignerInfoPic cmDesignerInfoPic){
		return cmDesignerInfoPicDao.update(cmDesignerInfoPic);
	}
	
	public Page<CmDesignerInfoPic> findByPage(Page page,Object map){
		return cmDesignerInfoPicDao.findByPage(page, map);
	}
	
	public List<CmDesignerInfoPic> findAll(){
		return cmDesignerInfoPicDao.findAll();
	}
	
	public List<CmDesignerInfoPic> findByMap(Map param){
		return cmDesignerInfoPicDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return cmDesignerInfoPicDao.getCount(map);
	}
	
	public CmDesignerInfoPic getById(Long id){
		return cmDesignerInfoPicDao.getById(id);
	}
	
	public int save(CmDesignerInfoPic cmDesignerInfoPic){
		return cmDesignerInfoPicDao.save(cmDesignerInfoPic);
	}
	
	public int delete(long id){
		return cmDesignerInfoPicDao.delete(id);
	}

	public void saveBatch(List<CmDesignerInfoPic> cmDesignerInfoPicList) {
		cmDesignerInfoPicDao.saveBatch(cmDesignerInfoPicList);
	}

	

	
	
	
	
	
}
