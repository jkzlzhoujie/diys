package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CirclePostDao;
import cn.temobi.complex.dao.CirclePostPraisesDao;
import cn.temobi.complex.dao.CmUserMessageDao;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CirclePostPraises;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.DateUtils;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("circlePostPraisesService")
public class CirclePostPraisesService extends ServiceBase{
	
	@Resource(name = "circlePostPraisesDao")
	private CirclePostPraisesDao circlePostPraisesDao;
	
	@Resource(name = "circlePostDao")
	private CirclePostDao circlePostDao;
	
	@Resource(name = "cmUserMessageDao")
	private CmUserMessageDao cmUserMessageDao;
	
	public int update(CirclePostPraises entity){
		return circlePostPraisesDao.update(entity);
	}
	
	public Page<CirclePostPraises> findByPage(Page page,Object map){
		return circlePostPraisesDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return circlePostPraisesDao.getCount(map);
	}
	
	public CirclePostPraises getById(Long id){
		return circlePostPraisesDao.getById(id);
	}
	
	public int save(CirclePostPraises entity){
		return circlePostPraisesDao.save(entity);
	}
	
	public int delete(Object id){
		return circlePostPraisesDao.delete(id);
	}
	
	public int delete(CirclePostPraises entity){
		return circlePostPraisesDao.delete(entity);
	}
	
	public List<CirclePostPraises> findByMap(Map map){
		return circlePostPraisesDao.findByMap(map);
	}
    
	public List<CirclePostPraises> findAll(){
		return circlePostPraisesDao.findAll();
	}
	
	public List<CirclePostPraises> findAll(CirclePostPraises entity){
		return circlePostPraisesDao.findAll(entity);
	}

	public int circlePostZ(CirclePost circlePost, CmUserInfo cmUserInfo,String mesCont,String type) {
		
		circlePostDao.update(circlePost);
		CmUserMessage cmUserMessage = new CmUserMessage();
    	cmUserMessage.setContent(mesCont);
		cmUserMessage.setType(8);
    	cmUserMessage.setUserId(circlePost.getUserId());
    	cmUserMessage.setSendUserId(cmUserInfo.getId());
    	cmUserMessageDao.save(cmUserMessage);
		
		CirclePostPraises circlePostPraises = new CirclePostPraises();
		circlePostPraises.setUserId( cmUserInfo.getId()  );
		circlePostPraises.setCirclePostId( circlePost.getId() );
		circlePostPraises.setCreatedWhen(DateUtils.getCurrDateStr());
		circlePostPraises.setType(Integer.valueOf(type));
		int saveNum = circlePostPraisesDao.save(circlePostPraises);
		return saveNum;
	}

}
