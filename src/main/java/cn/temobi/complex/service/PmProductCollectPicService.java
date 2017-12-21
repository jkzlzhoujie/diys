package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.PmProductCollectPicDao;
import cn.temobi.complex.entity.PmProductCollectPic;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmProductCollectPicService")
public class PmProductCollectPicService extends ServiceBase{

	@Resource(name = "pmProductCollectPicDao")
	private PmProductCollectPicDao pmProductCollectPicDao;
	
	public int update(PmProductCollectPic PmProductCollectPic){
		return pmProductCollectPicDao.update(PmProductCollectPic);
	}
	
	public Page<PmProductCollectPic> findByPage(Page page,Object map){
		return pmProductCollectPicDao.findByPage(page, map);
	}
	
	public List<PmProductCollectPic> findAll(){
		return pmProductCollectPicDao.findAll();
	}
	
	public List<PmProductCollectPic> findByMap(Map param){
		return pmProductCollectPicDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return pmProductCollectPicDao.getCount(map);
	}
	
	public PmProductCollectPic getById(Long id){
		return pmProductCollectPicDao.getById(id);
	}
	
	public int save(PmProductCollectPic PmProductCollectPic){
		return pmProductCollectPicDao.save(PmProductCollectPic);
	}
	
	//删除帖子 对应内容
	public int delete(Object circlepostid){
		return pmProductCollectPicDao.delete(circlepostid);
	}

	public void saveBatch(List<PmProductCollectPic> pmProductCollectPicList) {
		pmProductCollectPicDao.saveBatch(pmProductCollectPicList);		
	}
}
