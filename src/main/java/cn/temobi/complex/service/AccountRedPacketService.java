package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.AccountRedPacketDao;
import cn.temobi.complex.dao.AccountRedPacketLogDao;
import cn.temobi.complex.dao.AccountUserDao;
import cn.temobi.complex.dao.AccountUserLogDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.CmUserSignDao;
import cn.temobi.complex.dao.CmUserSignLogDao;
import cn.temobi.complex.dao.SysScoreDao;
import cn.temobi.complex.entity.AccountRedPacket;
import cn.temobi.complex.entity.AccountRedPacketLog;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserSign;
import cn.temobi.complex.entity.CmUserSignLog;
import cn.temobi.complex.entity.SysScore;
import cn.temobi.complex.entity.enums.ApiNameEnum;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.BigDecimalUtil;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.StringUtil;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("accountRedPacketService")
public class AccountRedPacketService extends ServiceBase{
	
	
	@Resource(name = "accountRedPacketDao")
	private AccountRedPacketDao accountRedPacketDao;
	
	@Resource(name = "accountUserDao")
	private AccountUserDao accountUserDao;
	
	@Resource(name = "accountUserLogDao")
	private AccountUserLogDao accountUserLogDao;
	
	@Resource(name = "accountRedPacketLogDao")
	private AccountRedPacketLogDao accountRedPacketLogDao;
	
	@Resource(name = "cmUserSignLogDao")
	private CmUserSignLogDao cmUserSignLogDao;
	
	@Resource(name = "cmUserSignDao")
	private CmUserSignDao cmUserSignDao;
	
	@Resource(name = "sysScoreDao")
	private SysScoreDao sysScoreDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	public int update(AccountRedPacket entity){
		return accountRedPacketDao.update(entity);
	}
	
	public Page<AccountRedPacket> findByPage(Page page,Object map){
		return accountRedPacketDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return accountRedPacketDao.getCount(map);
	}
	
	public AccountRedPacket getById(Long id){
		return accountRedPacketDao.getById(id);
	}
	
	public int save(AccountRedPacket entity){
		return accountRedPacketDao.save(entity);
	}
	
	public int delete(Object id){
		return accountRedPacketDao.delete(id);
	}
	
	public int delete(AccountRedPacket entity){
		return accountRedPacketDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return accountRedPacketDao.findByMap(map);
	}
    
	public List<AccountRedPacket> findAll(){
		return accountRedPacketDao.findAll();
	}
	
	public List<AccountRedPacket> findAll(AccountRedPacket entity){
		return accountRedPacketDao.findAll(entity);
	}
	
	
	public ResponseObject getRedorSign(Map<String,Object> passMap,ResponseObject object){
			
	    try {
			CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
			AccountUserLog accountUserLog = (AccountUserLog) passMap.get("accountUserLog");
			AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
			String imei =  passMap.get("imei").toString();
			String imsi =  passMap.get("imsi").toString();
			String clientId =  passMap.get("clientId").toString();
			Long userId =  cmUserInfo.getId();
			if(StringUtil.isEmpty(accountUser))
			{
				accountUser = createAccount(passMap);
			}
			
			Map<String,Object> searchMap = new HashMap<String, Object>();
			searchMap.put("userId", userId);
			
			String userType = accountUser.getType();
			
			Map<String,Object> search2Map = new HashMap<String, Object>();
			double totalRedPrice = 0;
			CmUserSign cmUserSign = null;
			if("0".equals(userType)){
				searchMap.put("time", DateUtils.getCurrDateStr());
				List<CmUserSignLog> list = cmUserSignLogDao.findByMap(searchMap);
				if(list == null || list.size() <= 0)
				{
					SysScore sysScore = sysScoreDao.getById(0l);
					int integral = sysScore.getIntegral();
					CmUserSignLog cmUserSignLog = new CmUserSignLog();
					cmUserSignLog.setUserId(userId);
					cmUserSignLog.setClientId(Long.parseLong(clientId));
					cmUserSignLog.setImei(imei);
					cmUserSignLog.setImsi(imsi);
					cmUserSignLog.setIntegral(integral);
					cmUserSignLogDao.save(cmUserSignLog);
					
					accountUserLog.setOtherId(cmUserSignLog.getId());
					accountUserLog.setApiName(ApiNameEnum.sign.getCode());
					accountUserLogDao.save(accountUserLog);
					
					cmUserSign = cmUserSignDao.getById(userId);
					if(StringUtil.isEmpty(cmUserSign)){
						cmUserSign = new CmUserSign();
						cmUserSign.setTotalIntegral(integral);
						cmUserSign.setMonthIntegral(integral);
						cmUserSign.setUserId(userId);
						cmUserSign.setContinuityNum(1);
						cmUserSign.setTotalIntegral(1);
						cmUserSign.setMonthNum(1);
						cmUserSignDao.save(cmUserSign);
					}else{
						
						searchMap.put("time", DateUtils.getLastOneDay(new Date()));
						List<CmUserSignLog> yesList = cmUserSignLogDao.findByMap(searchMap);
						if(yesList != null && yesList.size() > 0)
						{
							cmUserSign.setContinuityNum(cmUserSign.getContinuityNum()+1);
						}else{
							cmUserSign.setContinuityNum(1);
						}
						if("01".equals(DateUtils.getDay()))
						{
							cmUserSign.setMonthNum(1);
							cmUserSign.setMonthIntegral(integral);
						}else{
							cmUserSign.setMonthNum(cmUserSign.getMonthNum()+1);
			    			cmUserSign.setMonthIntegral(cmUserSign.getMonthIntegral()+integral);
						}
					
						cmUserSign.setTotalNum(cmUserSign.getTotalNum()+1);
						cmUserSign.setTotalIntegral(cmUserSign.getTotalIntegral()+integral);
						cmUserSignDao.update(cmUserSign);
					}
					
					cmUserInfo.setScore(cmUserInfo.getScore()+sysScore.getIntegral());
					cmUserInfo.setExperience(cmUserInfo.getExperience()+sysScore.getExperience());
					cmUserInfo.setCharm(cmUserInfo.getCharm()+sysScore.getCharm());
					cmUserInfo.setCredit(cmUserInfo.getCredit()+sysScore.getCredit());
					cmUserInfo.setOriginality(cmUserInfo.getOriginality()+sysScore.getOriginality());
					cmUserInfoDao.update(cmUserInfo);
				}
				
			}else if("1".equals(userType)){
				List<Long> list = accountRedPacketDao.findAllJoin(searchMap);
				if(list != null && list.size() > 0)
				{
					for(Long joinId:list)
					{
						searchMap.put("time", DateUtils.getCurrDateStr());
						searchMap.put("joinId", joinId);
						List<AccountRedPacket> redList = accountRedPacketDao.findByMap(searchMap);
						if(redList == null || redList.size() <= 0){
							search2Map.put("status", "0");
							search2Map.put("joinId", joinId);
							search2Map.put("userId", userId);
							//五一后暂停赠送红包
							search2Map.put("addTimeWuYi", "2016-05-01");
							List<AccountRedPacket> redList2 = accountRedPacketDao.findByMap(search2Map);
							if(redList2 != null && redList2.size() > 0)
							{
								AccountRedPacket accountRedPacket = redList2.get(0);
								double redPacketPrice = accountRedPacket.getPrice();
								totalRedPrice = BigDecimalUtil.sum(totalRedPrice, redPacketPrice);
							}
						}
					}
				}
				
				userSign(passMap, object);
			}
			
			Map<String,Object> returnmap = new HashMap<String, Object>();
			if(totalRedPrice != 0)
			{
				returnmap.put("redpacket", totalRedPrice);
			}else{
				returnmap.put("redpacket", null);
			}
			returnmap.put("cmUserSign", cmUserSign);
			object.setResponse(returnmap);
			object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	
	public ResponseObject userSign(Map<String,Object> passMap,ResponseObject object){
		
	    CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
	    AccountUserLog accountUserLog = (AccountUserLog) passMap.get("accountUserLog");
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
		String imei =  passMap.get("imei").toString();
		String imsi =  passMap.get("imsi").toString();
		String clientId =  passMap.get("clientId").toString();
		Long userId =  cmUserInfo.getId();
		if(StringUtil.isEmpty(accountUser))
    	{
			accountUser = createAccount(passMap);
    	}
    	
    	Map<String,Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("userId", userId);
    	
    	CmUserSign cmUserSign = null;
		searchMap.put("time", DateUtils.getCurrDateStr());
		List<CmUserSignLog> list = cmUserSignLogDao.findByMap(searchMap);
		if(list == null || list.size() <= 0)
		{
			SysScore sysScore = sysScoreDao.getById(0l);
			int integral = sysScore.getIntegral();
			CmUserSignLog cmUserSignLog = new CmUserSignLog();
			cmUserSignLog.setUserId(userId);
			cmUserSignLog.setClientId(Long.parseLong(clientId));
			cmUserSignLog.setImei(imei);
			cmUserSignLog.setImsi(imsi);
			cmUserSignLog.setIntegral(integral);
			cmUserSignLogDao.save(cmUserSignLog);
			
			accountUserLog.setOtherId(cmUserSignLog.getId());
    		accountUserLog.setApiName(ApiNameEnum.sign.getCode());
    		accountUserLogDao.save(accountUserLog);
    		
    		cmUserSign = cmUserSignDao.getById(userId);
    		if(StringUtil.isEmpty(cmUserSign)){
    			cmUserSign = new CmUserSign();
    			cmUserSign.setTotalIntegral(integral);
    			cmUserSign.setMonthIntegral(integral);
    			cmUserSign.setUserId(userId);
    			cmUserSign.setContinuityNum(1);
    			cmUserSign.setTotalIntegral(1);
    			cmUserSign.setMonthNum(1);
    			cmUserSignDao.save(cmUserSign);
    		}else{
    			
    			searchMap.put("time", DateUtils.getLastOneDay(new Date()));
    			List<CmUserSignLog> yesList = cmUserSignLogDao.findByMap(searchMap);
    			if(yesList != null && yesList.size() > 0)
    			{
    				cmUserSign.setContinuityNum(cmUserSign.getContinuityNum()+1);
    			}else{
    				cmUserSign.setContinuityNum(1);
    			}
    			if("01".equals(DateUtils.getDay()))
    			{
    				cmUserSign.setMonthNum(1);
    				cmUserSign.setMonthIntegral(integral);
    			}else{
    				cmUserSign.setMonthNum(cmUserSign.getMonthNum()+1);
	    			cmUserSign.setMonthIntegral(cmUserSign.getMonthIntegral()+integral);
    			}
    		
    			cmUserSign.setTotalNum(cmUserSign.getTotalNum()+1);
    			cmUserSign.setTotalIntegral(cmUserSign.getTotalIntegral()+integral);
    			cmUserSignDao.update(cmUserSign);
    		}
    		
    		cmUserInfo.setScore(cmUserInfo.getScore()+sysScore.getIntegral());
			cmUserInfo.setExperience(cmUserInfo.getExperience()+sysScore.getExperience());
			cmUserInfo.setCharm(cmUserInfo.getCharm()+sysScore.getCharm());
			cmUserInfo.setCredit(cmUserInfo.getCredit()+sysScore.getCredit());
			cmUserInfo.setOriginality(cmUserInfo.getOriginality()+sysScore.getOriginality());
    		cmUserInfoDao.update(cmUserInfo);
		}
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	public ResponseObject receiveRed(Map<String,Object> passMap,ResponseObject object){
		
	    CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
	    AccountUserLog accountUserLog = (AccountUserLog) passMap.get("accountUserLog");
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
		String imei =  passMap.get("imei").toString();
		String imsi =  passMap.get("imsi").toString();
		String clientId =  passMap.get("clientId").toString();
		Long userId =  cmUserInfo.getId();
		if(StringUtil.isEmpty(accountUser))
    	{
    		return object;
    	}
    	
    	Map<String,Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("userId", userId);
    	String userType = accountUser.getType();
    	
    	Map<String,Object> search2Map = new HashMap<String, Object>();
    	double totalRedPrice = 0;
    	if("0".equals(userType)){
    		return object;
    	}else if("1".equals(userType)){
    		List<Long> list = accountRedPacketDao.findAllJoin(searchMap);
    		if(list != null && list.size() > 0)
    		{
    			List<AccountRedPacket> alllist = new ArrayList<AccountRedPacket>();
    			for(Long joinId:list)
    			{
    				searchMap.put("time", DateUtils.getCurrDateStr());
    				searchMap.put("joinId", joinId);
    				List<AccountRedPacket> redList = accountRedPacketDao.findByMap(searchMap);
    				if(redList == null || redList.size() <= 0){
    					search2Map.put("status", "0");
    					search2Map.put("joinId", joinId);
    					search2Map.put("userId", userId);
    					//五一后暂停赠送红包
    					search2Map.put("addTimeWuYi", "2016-05-01");
    					List<AccountRedPacket> redList2 = accountRedPacketDao.findByMap(search2Map);
    					if(redList2 != null && redList2.size() > 0)
    					{
    						AccountRedPacket accountRedPacket = redList2.get(0);
    						double redPacketPrice = accountRedPacket.getPrice();
    						totalRedPrice = BigDecimalUtil.sum(totalRedPrice, redPacketPrice);
    						accountRedPacket.setUpdateTime(DateUtils.getCurrDateTimeStr());
    						accountRedPacket.setStatus("1");
    						accountRedPacket.setClientId(Long.parseLong(clientId));
    						accountRedPacket.setImei(imei);
    						accountRedPacket.setImsi(imsi);
    						alllist.add(accountRedPacket);
    					}
    				}
    			}
    			
    			if(alllist != null && alllist.size() > 0)
    			{
    				
    				AccountRedPacketLog accountRedPacketLog = new AccountRedPacketLog();
    				accountRedPacketLog.setUserId(userId);
    				accountRedPacketLog.setPrice(totalRedPrice);
    				accountRedPacketLog.setType("1");
    				accountRedPacketLog.setClientId(Long.parseLong(clientId));
    				accountRedPacketLog.setImei(imei);
    				accountRedPacketLog.setImsi(imsi);
    				accountRedPacketLog.setStartAccountPrice(accountUser.getRedPacket());
    				
    				double userRedPacket = BigDecimalUtil.sum(totalRedPrice,accountUser.getRedPacket());
    				accountRedPacketLog.setEndAccountPrice(userRedPacket);
    				accountRedPacketLog.setRedType("1");
    				accountRedPacketLogDao.save(accountRedPacketLog);
    				
    				accountUser.setRedPacket(userRedPacket);
    				accountUserDao.update(accountUser);
    				
    				accountUserLog.setOtherId(accountRedPacketLog.getId());
    	    		accountUserLog.setApiName(ApiNameEnum.getRed.getCode());
    	    		accountUserLogDao.save(accountUserLog);
    	    		
    	    		for(AccountRedPacket accountRedPacket:alllist)
    				{
    	    			accountRedPacket.setRedpacketLog(accountRedPacketLog.getId());
    	    			accountRedPacketDao.update(accountRedPacket);
    				}
    				
    			}
    		}
    	}
    	
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
}
