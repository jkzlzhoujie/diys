package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.PmProductAccusationDao;
import cn.temobi.complex.dao.PmScoreDisposableDao;
import cn.temobi.complex.dao.PmScoreLogDao;
import cn.temobi.complex.dao.PmScoreLogSeeDao;
import cn.temobi.complex.dao.PmScoreLogSeeUserDao;
import cn.temobi.complex.dao.SysScoreDao;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.PmProductAccusation;
import cn.temobi.complex.entity.PmScoreDisposable;
import cn.temobi.complex.entity.PmScoreLog;
import cn.temobi.complex.entity.PmScoreLogSee;
import cn.temobi.complex.entity.PmScoreLogSeeUser;
import cn.temobi.complex.entity.SysScore;
import cn.temobi.core.common.Page;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.LevelUtil;
import cn.temobi.core.util.StringUtil;

import com.salim.cache.CacheHelper;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmScoreLogService")
public class PmScoreLogService extends ServiceBase{
	
	@Resource(name = "pmScoreLogDao")
	private PmScoreLogDao pmScoreLogDao;
	
	@Resource(name = "pmScoreDisposableDao")
	private PmScoreDisposableDao pmScoreDisposableDao;
	
	@Resource(name = "pmScoreLogSeeDao")
	private PmScoreLogSeeDao pmScoreLogSeeDao;
	
	@Resource(name = "pmScoreLogSeeUserDao")
	private PmScoreLogSeeUserDao pmScoreLogSeeUserDao;
	
	@Resource(name = "pmProductAccusationDao")
	private PmProductAccusationDao pmProductAccusationDao;
	
	@Resource(name = "sysScoreDao")
	private SysScoreDao sysScoreDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	public int update(PmScoreLog pmScoreLog){
		return pmScoreLogDao.update(pmScoreLog);
	}
	
	public Page<PmScoreLog> findByPage(Page page,Object map){
		return pmScoreLogDao.findByPage(page, map);
	}
	
	public List<PmScoreLog> findAll(){
		return pmScoreLogDao.findAll();
	}
	
	public List<PmScoreLog> findByMap(Map map){
		return pmScoreLogDao.findByMap(map);
	}
	
	public Number getCount(Map map){
		return pmScoreLogDao.getCount(map);
	}
	
	public PmScoreLog getById(Long id){
		return pmScoreLogDao.getById(id);
	}
	
	public int save(PmScoreLog pmScoreLog){
		return pmScoreLogDao.save(pmScoreLog);
	}
	
	public int delete(Object id){
		return pmScoreLogDao.delete(id);
	}
	
	public void accusationCancel(String id){
		PmProductAccusation pmProductAccusation = pmProductAccusationDao.getById(Long.parseLong(id));
		if(StringUtil.isNotEmpty(pmProductAccusation)) {
			pmProductAccusation.setIsDistort(0);
			pmProductAccusationDao.update(pmProductAccusation);
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("otherId",pmProductAccusation.getProductId());
			List<PmScoreLog> list = pmScoreLogDao.findByMap(map);
			if(list != null && list.size() > 0)
			{
				for(PmScoreLog pmScoreLog:list){
					if(pmScoreLog.getType() == 13){
						pmScoreLogDao.delete(pmScoreLog.getId());
						
						
						//被举报人
						CmUserInfo cmUserInfo = cmUserInfoDao.getById(pmProductAccusation.getUserId());
						
						cmUserInfo.setScore(cmUserInfo.getScore()-pmScoreLog.getIntegral());
						cmUserInfo.setExperience(cmUserInfo.getExperience()-pmScoreLog.getExperience());
						cmUserInfo.setCharm(cmUserInfo.getCharm()-pmScoreLog.getCharm());
						cmUserInfo.setOriginality(cmUserInfo.getOriginality()-pmScoreLog.getOriginality());
						cmUserInfo.setCredit(cmUserInfo.getCredit()-pmScoreLog.getCredit());
						cmUserInfo.setLevel(LevelUtil.getLevel(cmUserInfo.getExperience()));
						cmUserInfoDao.update(cmUserInfo);
						
						String time = DateUtils.getCurrDateStr();
						CacheHelper.getInstance().remove("score@"+13+"@"+cmUserInfo.getId()+"@"+time);
					}else if(pmScoreLog.getType() == 24){
						pmScoreLogDao.delete(pmScoreLog.getId());
						
						//举报人
						CmUserInfo cmUserInfoTo = cmUserInfoDao.getById(pmProductAccusation.getAccusationUserId());
						
						cmUserInfoTo.setScore(cmUserInfoTo.getScore()-pmScoreLog.getIntegral());
						cmUserInfoTo.setExperience(cmUserInfoTo.getExperience()-pmScoreLog.getExperience());
						cmUserInfoTo.setCharm(cmUserInfoTo.getCharm()-pmScoreLog.getCharm());
						cmUserInfoTo.setOriginality(cmUserInfoTo.getOriginality()-pmScoreLog.getOriginality());
						cmUserInfoTo.setCredit(cmUserInfoTo.getCredit()-pmScoreLog.getCredit());
						cmUserInfoTo.setLevel(LevelUtil.getLevel(cmUserInfoTo.getExperience()));
						
						cmUserInfoDao.update(cmUserInfoTo);
						
						String time = DateUtils.getCurrDateStr();
						CacheHelper.getInstance().remove("score@"+24+"@"+cmUserInfoTo.getId()+"@"+time);
					}
				}
			}
		}
	}

	
	public boolean addScore(String type,CmUserInfo cmUserInfo,String clientId,String otherId,String price){
		if(StringUtil.isNotEmpty(type) && StringUtil.isNotEmpty(cmUserInfo))
		{
			SysScore sysScore = sysScoreDao.getById(Long.parseLong(type));
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", type);
			map.put("userId", cmUserInfo.getId());
			if("2".equals(sysScore.getFlag()))//flag 是否一次性 1否 2是
			{
				List<PmScoreDisposable> list= (List<PmScoreDisposable>) CacheHelper.getInstance().get("score@"+type+"@"+cmUserInfo.getId());
				if(list == null || list.size() <= 0)
				{
					list = pmScoreDisposableDao.findByMap(map);
				}
				List<PmScoreDisposable> newlist = new ArrayList<PmScoreDisposable>();
				newlist.addAll(list);
				if(list == null || list.size() <= 0)
				{
					cmUserInfo.setScore(cmUserInfo.getScore()+sysScore.getIntegral());
					cmUserInfo.setExperience(cmUserInfo.getExperience()+sysScore.getExperience());
					cmUserInfo.setCharm(cmUserInfo.getCharm()+sysScore.getCharm());
					cmUserInfo.setCredit(cmUserInfo.getCredit()+sysScore.getCredit());
					cmUserInfo.setOriginality(cmUserInfo.getOriginality()+sysScore.getOriginality());
					int level = cmUserInfo.getLevel();
					int newLevel = level+1;
					if(cmUserInfo.getExperience() >= newLevel*newLevel*100)
					{
						cmUserInfo.setLevel(newLevel);
					}
					cmUserInfoDao.update(cmUserInfo);
					PmScoreDisposable pmScoreDisposable = new PmScoreDisposable();
					pmScoreDisposable.setType(Long.parseLong(type));
					pmScoreDisposable.setUserId(cmUserInfo.getId());
					if(StringUtil.isNotEmpty(clientId))
					{
						pmScoreDisposable.setClientId(Long.parseLong(clientId));
					}
					pmScoreDisposable.setExperience(sysScore.getExperience());
					pmScoreDisposable.setCharm(sysScore.getCharm());
					pmScoreDisposable.setCredit(sysScore.getCredit());
					pmScoreDisposable.setOriginality(sysScore.getOriginality());
					pmScoreDisposable.setIntegral(sysScore.getIntegral());
					if(StringUtil.isNotEmpty(otherId))
					{
						pmScoreDisposable.setOtherId(Long.parseLong(otherId));
					}
					pmScoreDisposable.setContent(sysScore.getName());
					pmScoreDisposableDao.save(pmScoreDisposable);
					newlist.add(pmScoreDisposable);
				}
				CacheHelper.getInstance().set(60*60*24, "score@"+type+"@"+cmUserInfo.getId(), newlist);
			}else if("1".equals(sysScore.getFlag())){
				String time = DateUtils.getCurrDateStr();
				map.put("time", DateUtils.getCurrDateStr());
				Long scoreId = sysScore.getId();
				if(scoreId - 12 == 0)
				{
					List<PmScoreLogSeeUser> list= (List<PmScoreLogSeeUser>) CacheHelper.getInstance().get("score@"+type+"@"+cmUserInfo.getId()+"@"+time);
					if(list == null || list.size() <= 0)
					{
						list = pmScoreLogSeeUserDao.findByMap(map);
					}
					if(list != null && list.size() > 0)
					{
						if(StringUtil.isNotEmpty(sysScore.getDayNum()))
						{
							if(list.size() >= sysScore.getDayNum())
							{
								List<PmScoreLogSeeUser> newlist = new ArrayList<PmScoreLogSeeUser>();
								newlist.addAll(list);
								CacheHelper.getInstance().set(60*60*24, "score@"+type+"@"+cmUserInfo.getId()+"@"+time, newlist);
								return true;
							}
						}
					}
					
					PmScoreLogSeeUser pmScoreLogSeeUser = new PmScoreLogSeeUser();
					pmScoreLogSeeUser.setType(Long.parseLong(type));
					pmScoreLogSeeUser.setUserId(cmUserInfo.getId());
					if(StringUtil.isNotEmpty(clientId))
					{
						pmScoreLogSeeUser.setClientId(Long.parseLong(clientId));
					}
					pmScoreLogSeeUser.setExperience(sysScore.getExperience());
					pmScoreLogSeeUser.setCharm(sysScore.getCharm());
					pmScoreLogSeeUser.setCredit(sysScore.getCredit());
					pmScoreLogSeeUser.setOriginality(sysScore.getOriginality());
					pmScoreLogSeeUser.setIntegral(sysScore.getIntegral());
					if(StringUtil.isNotEmpty(otherId))
					{
						pmScoreLogSeeUser.setOtherId(Long.parseLong(otherId));
					}
					pmScoreLogSeeUser.setContent(sysScore.getName());
					pmScoreLogSeeUserDao.save(pmScoreLogSeeUser);
					
					List<PmScoreLogSeeUser> newlist = new ArrayList<PmScoreLogSeeUser>();
					newlist.addAll(list);
					newlist.add(pmScoreLogSeeUser);
					CacheHelper.getInstance().set(60*60*24, "score@"+type+"@"+cmUserInfo.getId()+"@"+time, newlist);
				}else if(scoreId - 14 == 0){
					List<PmScoreLogSee> list= (List<PmScoreLogSee>) CacheHelper.getInstance().get("score@"+type+"@"+cmUserInfo.getId()+"@"+time);
					if(list == null || list.size() <= 0)
					{
						list = pmScoreLogSeeDao.findByMap(map);
					}
					if(list != null && list.size() > 0)
					{
						if(StringUtil.isNotEmpty(sysScore.getDayNum()))
						{
							if(list.size() >= sysScore.getDayNum())
							{
								List<PmScoreLogSee> newlist = new ArrayList<PmScoreLogSee>();
								newlist.addAll(list);
								CacheHelper.getInstance().set(60*60*24, "score@"+type+"@"+cmUserInfo.getId()+"@"+time, newlist);
								return true;
							}
						}
					}
					
					PmScoreLogSee pmScoreLogSee = new PmScoreLogSee();
					pmScoreLogSee.setType(Long.parseLong(type));
					pmScoreLogSee.setUserId(cmUserInfo.getId());
					if(StringUtil.isNotEmpty(clientId))
					{
						pmScoreLogSee.setClientId(Long.parseLong(clientId));
					}
					pmScoreLogSee.setExperience(sysScore.getExperience());
					pmScoreLogSee.setCharm(sysScore.getCharm());
					pmScoreLogSee.setCredit(sysScore.getCredit());
					pmScoreLogSee.setOriginality(sysScore.getOriginality());
					pmScoreLogSee.setIntegral(sysScore.getIntegral());
					if(StringUtil.isNotEmpty(otherId))
					{
						pmScoreLogSee.setOtherId(Long.parseLong(otherId));
					}
					pmScoreLogSee.setContent(sysScore.getName());
					pmScoreLogSeeDao.save(pmScoreLogSee);
					
					List<PmScoreLogSee> newlist = new ArrayList<PmScoreLogSee>();
					newlist.addAll(list);
					newlist.add(pmScoreLogSee);
					CacheHelper.getInstance().set(60*60*24, "score@"+type+"@"+cmUserInfo.getId()+"@"+time, newlist);
				}else{
					List<PmScoreLog> list= (List<PmScoreLog>) CacheHelper.getInstance().get("score@"+type+"@"+cmUserInfo.getId()+"@"+time);
					if(list == null || list.size() <= 0)
					{
						list = pmScoreLogDao.findByMap(map);
					}
					if(list != null && list.size() > 0)
					{
						if(StringUtil.isNotEmpty(sysScore.getDayNum()))
						{
							if(list.size() >= sysScore.getDayNum())
							{
								List<PmScoreLog> newlist = new ArrayList<PmScoreLog>();
								newlist.addAll(list);
								CacheHelper.getInstance().set(60*60*24, "score@"+type+"@"+cmUserInfo.getId()+"@"+time, newlist);
								return true;
							}
						}
					}
					
					PmScoreLog pmScoreLog = new PmScoreLog();
					pmScoreLog.setType(Long.parseLong(type));
					pmScoreLog.setUserId(cmUserInfo.getId());
					if(StringUtil.isNotEmpty(clientId))
					{
						pmScoreLog.setClientId(Long.parseLong(clientId));
					}
					pmScoreLog.setExperience(sysScore.getExperience());
					pmScoreLog.setCharm(sysScore.getCharm());
					pmScoreLog.setCredit(sysScore.getCredit());
					pmScoreLog.setOriginality(sysScore.getOriginality());
					pmScoreLog.setIntegral(sysScore.getIntegral());
					if(StringUtil.isNotEmpty(otherId))
					{
						pmScoreLog.setOtherId(Long.parseLong(otherId));
					}
					pmScoreLog.setContent(sysScore.getName());
					pmScoreLogDao.save(pmScoreLog);
					
					List<PmScoreLog> newlist = new ArrayList<PmScoreLog>();
					newlist.addAll(list);
					newlist.add(pmScoreLog);
					CacheHelper.getInstance().set(60*60*24, "score@"+type+"@"+cmUserInfo.getId()+"@"+time, newlist);
				}
				
				cmUserInfo.setScore(cmUserInfo.getScore()+sysScore.getIntegral());
				cmUserInfo.setExperience(cmUserInfo.getExperience()+sysScore.getExperience());
				cmUserInfo.setCharm(cmUserInfo.getCharm()+sysScore.getCharm());
				if(StringUtil.isNotEmpty(price))
				{
					cmUserInfo.setCredit(cmUserInfo.getCredit()+(int)Double.parseDouble(price));
				}else{
					cmUserInfo.setCredit(cmUserInfo.getCredit()+sysScore.getCredit());
				}
				cmUserInfo.setOriginality(cmUserInfo.getOriginality()+sysScore.getOriginality());
				int level = cmUserInfo.getLevel();
				int newLevel = level+1;
				if(cmUserInfo.getExperience() >= newLevel*newLevel*100)
				{
					cmUserInfo.setLevel(newLevel);
				}
			}
		}
		return true;
	}
	
	
//	public boolean addScore2(String type,CmUserInfo cmUserInfo,String clientId,String otherId,String price,String ranking){
//		if(StringUtil.isNotEmpty(type) && StringUtil.isNotEmpty(cmUserInfo))
//		{
//			SysScore sysScore = sysScoreDao.getById(Long.parseLong(type));
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("type", type);
//			map.put("userId", cmUserInfo.getId());
//			map.put("content", ranking);
//			if("2".equals(sysScore.getFlag()))
//			{
//				List<PmScoreLog> list = pmScoreLogDao.findByMap(map);
//				if(list == null || list.size() <= 0)
//				{
//					cmUserInfo.setScore(cmUserInfo.getScore()+sysScore.getIntegral());
//					cmUserInfo.setExperience(cmUserInfo.getExperience()+sysScore.getExperience());
//					cmUserInfo.setCharm(cmUserInfo.getCharm()+sysScore.getCharm());
//					cmUserInfo.setCredit(cmUserInfo.getCredit()+sysScore.getCredit());
//					cmUserInfo.setOriginality(cmUserInfo.getOriginality()+sysScore.getOriginality());
//					int level = cmUserInfo.getLevel();
//					int newLevel = level+1;
//					if(cmUserInfo.getExperience() >= newLevel*newLevel*100)
//					{
//						cmUserInfo.setLevel(newLevel);
//					}
//					cmUserInfoDao.update(cmUserInfo);
//					PmScoreLog pmScoreLog = new PmScoreLog();
//					pmScoreLog.setType(Long.parseLong(type));
//					pmScoreLog.setUserId(cmUserInfo.getId());
//					if(StringUtil.isNotEmpty(clientId))
//					{
//						pmScoreLog.setClientId(Long.parseLong(clientId));
//					}
//					pmScoreLog.setExperience(sysScore.getExperience());
//					pmScoreLog.setCharm(sysScore.getCharm());
//					pmScoreLog.setCredit(sysScore.getCredit());
//					pmScoreLog.setOriginality(sysScore.getOriginality());
//					pmScoreLog.setIntegral(sysScore.getIntegral());
//					if(StringUtil.isNotEmpty(otherId))
//					{
//						pmScoreLog.setOtherId(Long.parseLong(otherId));
//					}
//					pmScoreLog.setContent(ranking);
//					pmScoreLogDao.save(pmScoreLog);
//				}
//			}
//		}
//		return true;
//	}
	
	
	public static void main(String[] args) {
		int a = 99;
		double b = a/100.0;
		double n=Math.sqrt(b);
		System.out.println((int)Math.floor(n));
	}

	/**
	 * 第一次注册添加积分
	 * @param cmUserInfo
	 * @param clientId
	 * @return
	 */
	public CmUserInfo addScoreRegister(CmUserInfo cmUserInfo, String clientId) {
		
			
			//1001 设置头像,1004 填写昵称,1005  选择性别    都是一次性的  
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", "'1001','1004','1005'");
			List<SysScore> sysScoreList = sysScoreDao.findByMap(map);
			int score = 0;
			int experience = 0;
			int charm = 0;
			int credit = 0;
			int originality = 0;
			for (SysScore sysScore : sysScoreList) {
				List<PmScoreDisposable> list = new ArrayList<PmScoreDisposable>();
				score = score + sysScore.getIntegral();
				experience = experience + sysScore.getExperience();
				charm = charm + sysScore.getCharm();
				credit = credit + sysScore.getCredit();
				originality = originality + sysScore.getOriginality();
				
				PmScoreDisposable pmScoreDisposable = new PmScoreDisposable();
				pmScoreDisposable.setType(sysScore.getId());
				pmScoreDisposable.setUserId(cmUserInfo.getId());
				if(StringUtil.isNotEmpty(clientId))
				{
					pmScoreDisposable.setClientId(Long.parseLong(clientId));
				}
				pmScoreDisposable.setExperience(sysScore.getExperience());
				pmScoreDisposable.setCharm(sysScore.getCharm());
				pmScoreDisposable.setCredit(sysScore.getCredit());
				pmScoreDisposable.setOriginality(sysScore.getOriginality());
				pmScoreDisposable.setIntegral(sysScore.getIntegral());
				pmScoreDisposable.setContent(sysScore.getName());
				pmScoreDisposableDao.save(pmScoreDisposable);
				list.add(pmScoreDisposable);
				CacheHelper.getInstance().set(60*60*24, "score@"+sysScore.getId()+"@"+cmUserInfo.getId(), list);
			}
				
			cmUserInfo.setScore(score);
			cmUserInfo.setExperience(experience);
			cmUserInfo.setCharm(charm);
			cmUserInfo.setCredit(credit);
			cmUserInfo.setOriginality(originality);
			int level = cmUserInfo.getLevel();
			int newLevel = level+1;
			if(cmUserInfo.getExperience() >= newLevel*newLevel*100)
			{
				cmUserInfo.setLevel(newLevel);
			}
			cmUserInfoDao.update(cmUserInfo);
			return cmUserInfo;
	}
	
	
	
}
