package cn.temobi.complex.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.FeedbackDao;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Feedback;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.StringUtil;

/**
 * 单个表情分享记录
 * @author hjf
 * 2014 六月 9 16:39:05
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("feedbackService")
public class FeedbackService extends ServiceBase{
	
	@Resource(name = "feedbackDao")
	private FeedbackDao feedbackDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	@Resource(name = "pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	public int update(Feedback entity){
		return feedbackDao.update(entity);
	}
	
	public Page<Feedback> findByPage(Page page,Object map){
		return feedbackDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return feedbackDao.getCount(map);
	}
	
	public Feedback getById(Long id){
		return feedbackDao.getById(id);
	}
	
	public int save(Feedback entity){
		return feedbackDao.save(entity);
	}
	
	public int delete(Object id){
		return feedbackDao.delete(id);
	}
	
	public int delete(Feedback entity){
		return feedbackDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return feedbackDao.findByMap(map);
	}
    
	public List<Feedback> findAll(){
		return feedbackDao.findAll();
	}
	
	public List<Feedback> findAll(Feedback entity){
		return feedbackDao.findAll(entity);
	}
	
	
	public void saveAll(Feedback feedback,Map<String,String> map){
		String clientId = map.get("clientId");
    	String userId = map.get("userId");
		feedbackDao.save(feedback);
		if(StringUtil.isNotEmpty(userId))
		{
			CmUserInfo cmUserInfo = cmUserInfoDao.getById(Long.parseLong(userId));
	    	if(StringUtil.isNotEmpty(cmUserInfo))
	    	{
	    		pmScoreLogService.addScore("26", cmUserInfo, clientId, "","");
		    	cmUserInfoDao.update(cmUserInfo);
	    	}
		}
	}

	public Number findFeedbackCount(Map<String, Object> mapOneDay) {
		return feedbackDao.findFeedbackCount(mapOneDay);
	}
}
