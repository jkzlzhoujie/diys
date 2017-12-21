package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmDesignerProductFormatDao;
import cn.temobi.complex.entity.CmDesignerInfoPic;
import cn.temobi.complex.entity.CmDesignerProductFormat;
import cn.temobi.complex.entity.CmDesignerProductInfo;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmDesignerProductFormatService")
public class CmDesignerProductFormatService extends ServiceBase{

	@Resource(name = "cmDesignerProductFormatDao")
	private CmDesignerProductFormatDao cmDesignerProductFormatDao;
	
	
	public int update(CmDesignerProductFormat cmDesignerProductFormat){
		return cmDesignerProductFormatDao.update(cmDesignerProductFormat);
	}
	
	public Page<CmDesignerProductFormat> findByPage(Page page,Object map){
		return cmDesignerProductFormatDao.findByPage(page, map);
	}
	
	public List<CmDesignerProductFormat> findAll(){
		return cmDesignerProductFormatDao.findAll();
	}
	
	public List<CmDesignerProductFormat> findByMap(Map param){
		return cmDesignerProductFormatDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return cmDesignerProductFormatDao.getCount(map);
	}
	
	public CmDesignerProductFormat getById(Long id){
		return cmDesignerProductFormatDao.getById(id);
	}
	
	public int save(CmDesignerProductFormat cmDesignerProductFormat){
		return cmDesignerProductFormatDao.save(cmDesignerProductFormat);
	}
	
	public int delete(long id){
		return cmDesignerProductFormatDao.delete(id);
	}

	public void saveBatch(List<CmDesignerProductFormat> cmDesignerProductFormatList) {
		cmDesignerProductFormatDao.saveBatch(cmDesignerProductFormatList);
	}

	
	
	
	
	
}
