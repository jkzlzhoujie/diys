package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CirclePostPicDao;
import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("circlePostPicService")
public class CirclePostPicService extends ServiceBase{

	@Resource(name = "circlePostPicDao")
	private CirclePostPicDao circlePostPicDao;
	
	public int update(CirclePostPic CirclePostPic){
		return circlePostPicDao.update(CirclePostPic);
	}
	
	public Page<CirclePostPic> findByPage(Page page,Object map){
		return circlePostPicDao.findByPage(page, map);
	}
	
	public List<CirclePostPic> findAll(){
		return circlePostPicDao.findAll();
	}
	
	public List<CirclePostPic> findByMap(Map param){
		return circlePostPicDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return circlePostPicDao.getCount(map);
	}
	
	public CirclePostPic getById(Long id){
		return circlePostPicDao.getById(id);
	}
	
	public int save(CirclePostPic CirclePostPic){
		return circlePostPicDao.save(CirclePostPic);
	}
	
	//删除帖子 对应内容
	public int delete(Object circlepostid){
		return circlePostPicDao.delete(circlepostid);
	}

	public void saveBatch(List<CirclePostPic> circlePostPicList) {
		circlePostPicDao.saveBatch(circlePostPicList);		
	}
}
