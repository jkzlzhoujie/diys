package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmDesignerSaleCommentDao;
import cn.temobi.complex.entity.CmDesignerSaleComment;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmDesignerSaleCommentService")
public class CmDesignerSaleCommentService extends ServiceBase{

	@Resource(name = "cmDesignerSaleCommentDao")
	private CmDesignerSaleCommentDao cmDesignerSaleCommentDao;
	
	
	public int update(CmDesignerSaleComment cmDesignerSaleComment){
		return cmDesignerSaleCommentDao.update(cmDesignerSaleComment);
	}
	
	public Page<CmDesignerSaleComment> findByPage(Page page,Object map){
		return cmDesignerSaleCommentDao.findByPage(page, map);
	}
	
	public List<CmDesignerSaleComment> findAll(){
		return cmDesignerSaleCommentDao.findAll();
	}
	
	public List<CmDesignerSaleComment> findByMap(Map param){
		return cmDesignerSaleCommentDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return cmDesignerSaleCommentDao.getCount(map);
	}
	
	public CmDesignerSaleComment getById(Long id){
		return cmDesignerSaleCommentDao.getById(id);
	}
	
	public int save(CmDesignerSaleComment cmDesignerSaleComment){
		return cmDesignerSaleCommentDao.save(cmDesignerSaleComment);
	}
	
	public int delete(long id){
		return cmDesignerSaleCommentDao.delete(id);
	}

	public void saveBatch(List<CmDesignerSaleComment> cmDesignerSaleCommentList) {
		cmDesignerSaleCommentDao.saveBatch(cmDesignerSaleCommentList);
	}

	

	
	
	
	
	
}
