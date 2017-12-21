package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.BackgroundPicDao;
import cn.temobi.complex.entity.BackgroundPic;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("backgroundPicService")
public class BackgroundPicService extends ServiceBase{

	@Resource(name = "backgroundPicDao")
	private BackgroundPicDao backgroundPicDao;
	
	public int update(BackgroundPic backgroundPic){
		return backgroundPicDao.update(backgroundPic);
	}
	
	public Page<BackgroundPic> findByPage(Page page,Object map){
		return backgroundPicDao.findByPage(page, map);
	}
	
	public List<BackgroundPic> findAll(){
		return backgroundPicDao.findAll();
	}
	
	public List<BackgroundPic> findByMap(Map param){
		return backgroundPicDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return backgroundPicDao.getCount(map);
	}
	
	public BackgroundPic getById(Long id){
		return backgroundPicDao.getById(id);
	}
	
	public int save(BackgroundPic backgroundPic){
		return backgroundPicDao.save(backgroundPic);
	}
}
