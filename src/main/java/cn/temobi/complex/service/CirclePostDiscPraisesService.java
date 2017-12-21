package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CirclePostDiscDao;
import cn.temobi.complex.dao.CirclePostDiscPraisesDao;
import cn.temobi.complex.dao.CmUserMessageDao;
import cn.temobi.complex.entity.CirclePostDisc;
import cn.temobi.complex.entity.CirclePostDiscPraises;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.DateUtils;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("circlePostDiscPraisesService")
public class CirclePostDiscPraisesService extends ServiceBase{
	
	@Resource(name = "circlePostDiscPraisesDao")
	private CirclePostDiscPraisesDao circlePostDiscPraisesDao;
	
	@Resource(name = "circlePostDiscDao")
	private CirclePostDiscDao circlePostDiscDao;
	
	@Resource(name = "cmUserMessageDao")
	private CmUserMessageDao cmUserMessageDao;
	
	
	public int update(CirclePostDiscPraises entity){
		return circlePostDiscPraisesDao.update(entity);
	}
	
	public Page<CirclePostDiscPraises> findByPage(Page page,Object map){
		return circlePostDiscPraisesDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return circlePostDiscPraisesDao.getCount(map);
	}
	
	public CirclePostDiscPraises getById(Long id){
		return circlePostDiscPraisesDao.getById(id);
	}
	
	public int save(CirclePostDiscPraises entity){
		return circlePostDiscPraisesDao.save(entity);
	}
	
	public int delete(Object id){
		return circlePostDiscPraisesDao.delete(id);
	}
	
	public int delete(CirclePostDiscPraises entity){
		return circlePostDiscPraisesDao.delete(entity);
	}
	
	public List<CirclePostDiscPraises> findByMap(Map map){
		return circlePostDiscPraisesDao.findByMap(map);
	}
    
	public List<CirclePostDiscPraises> findAll(){
		return circlePostDiscPraisesDao.findAll();
	}
	
	public List<CirclePostDiscPraises> findAll(CirclePostDiscPraises entity){
		return circlePostDiscPraisesDao.findAll(entity);
	}

	public int praiseCirclePost(CirclePostDisc circlePostDisc, CmUserInfo cmUserInfo) {
		CirclePostDiscPraises circlePostDiscPraises = new CirclePostDiscPraises();
		circlePostDiscPraises.setUserId( cmUserInfo.getId()  );
		circlePostDiscPraises.setCirclePostDiscId( circlePostDisc.getId() );
		circlePostDiscPraises.setCreatedWhen(DateUtils.getCurrDateStr());
		circlePostDiscPraises.setType(0);
		circlePostDiscPraisesDao.save(circlePostDiscPraises);
		
		circlePostDisc.setZanNum(circlePostDisc.getZanNum()+1);
		return circlePostDiscDao.update(circlePostDisc);	
	}

	public int circlePostDiscZ(CirclePostDisc circlePostDisc,
			CmUserInfo cmUserInfo) {
		
		circlePostDiscDao.update(circlePostDisc);
		
		CmUserMessage cmUserMessage = new CmUserMessage();
    	cmUserMessage.setContent("赞了");
		cmUserMessage.setType(8);
		cmUserMessage.setProductId(circlePostDisc.getCirclePostId());
    	cmUserMessage.setUserId(circlePostDisc.getDiscUserId());
    	cmUserMessage.setSendUserId(cmUserInfo.getId());
    	cmUserMessageDao.save(cmUserMessage);
		
		CirclePostDiscPraises circlePostDiscPraises = new CirclePostDiscPraises();
		circlePostDiscPraises.setUserId( cmUserInfo.getId()  );
		circlePostDiscPraises.setCirclePostDiscId( circlePostDisc.getId() );
		circlePostDiscPraises.setCreatedWhen(DateUtils.getCurrDateStr());
		circlePostDiscPraises.setType(0);
	   int saveNum = circlePostDiscPraisesDao.save(circlePostDiscPraises);
	   return	saveNum;
	}
	
}
