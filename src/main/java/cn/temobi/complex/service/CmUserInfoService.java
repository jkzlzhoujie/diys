package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.salim.cache.CacheHelper;
import com.salim.encrypt.MD5;

import cn.temobi.complex.dao.ClientDao;
import cn.temobi.complex.dao.CmUserExtendInfoDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.LaberDao;
import cn.temobi.complex.dto.CmUserInfoDto;
import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.dto.CmUserInfoUpdateDto;
import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.Client;
import cn.temobi.complex.entity.CmCircle;
import cn.temobi.complex.entity.CmUserExtendInfo;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.ThreadPoolManager;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmUserInfoService")
public class CmUserInfoService extends ServiceBase{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	@Resource(name = "cmUserExtendInfoDao")
	private CmUserExtendInfoDao cmUserExtendInfoDao;
	
	@Resource(name = "pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name = "laberDao")
	private LaberDao laberDao;
	
	public int update(CmUserInfo entity){
		int num =  cmUserInfoDao.update(entity);
		String key = "cmUserInfo@userId"+entity.getId();
		CacheHelper.getInstance().set(60*60*6,key, entity);
		return num;
	}
	
	public Page<CmUserInfo> findByPage(Page page,Object map){
		return cmUserInfoDao.findByPage(page, map);
	}
	
	public Page<CmUserInfo> findByPageTwo(Page page,Object map){
		return cmUserInfoDao.findByPageTwo(page, map);
	}
	
	public Number getCount(Map map){
		return cmUserInfoDao.getCount(map);
	}
	
	public CmUserInfo getById(Long id){
		String key = "cmUserInfo@userId"+id;
		CmUserInfo tempCmUserInfo = (CmUserInfo) CacheHelper.getInstance().get(key);
    	if(tempCmUserInfo != null){
    		return tempCmUserInfo ; 
    	}else{
    		CmUserInfo cmUserInfo =  cmUserInfoDao.getById(id);
    		CacheHelper.getInstance().set(60*60*6,key, cmUserInfo);
    		return cmUserInfo;
    	}
	}
	
	public int save(CmUserInfo entity){
		int num =  cmUserInfoDao.save(entity);
		String key = "cmUserInfo@userId"+entity.getId();
		CacheHelper.getInstance().set(60*60*6,key, entity);
		return num;
	}
	
	public int delete(Object id){
		String key = "cmUserInfo@userId"+id;
		CacheHelper.getInstance().remove(key);
		return cmUserInfoDao.delete(id);
	}
	
	public int delete(CmUserInfo entity){
		String key = "cmUserInfo@userId"+entity.getId();
		CacheHelper.getInstance().remove(key);
		return cmUserInfoDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmUserInfoDao.findByMap(map);
	}
	
	public List findRand(Map map){
		return cmUserInfoDao.findRand(map);
	}
	
	
	public List findLoginUser(Map map){
		return cmUserInfoDao.findLoginUser(map);
	}
	
	public List<CmUserInfoListDto> findByList(Map<String,Object> map){
		return cmUserInfoDao.findByList(map);
	}
    
	public List<CmUserInfo> findAll(){
		return cmUserInfoDao.findAll();
	}
	
	public List<CmUserInfo> findAll(CmUserInfo entity){
		return cmUserInfoDao.findAll(entity);
	}
	
	public List<Long> findCircleId(Map<String,Object> map){
		return cmUserInfoDao.findCircleId(map);
	}
	
	public Page<CmUserInfoListDto> findDtoByMap(Page page,Object map){
		return cmUserInfoDao.findByPage(page,"findDtoByMap", map);
	}
	
	
	public void saveAll(CmUserInfo cmUserInfo,Map<String,Object> passMap){
		
		CmUserExtendInfo cmUserExtendInfo = new CmUserExtendInfo();
		cmUserExtendInfo.setUserId(cmUserInfo.getId());
		int b = (int) (Math.random()*73)+1;
		cmUserExtendInfo.setCoverThumbnail(host+"/diys/rs/default/cover/"+b+".jpg");
		Map<String,Object> laberMap = new HashMap<String, Object>();
		laberMap.put("type", "4");
		laberMap.put("limit", 3);
		List<String> laberList = laberDao.findRand(laberMap);
		cmUserExtendInfo.setAttentionLabel(StringUtils.join(laberList.toArray(), ","));
		cmUserInfoDao.save(cmUserInfo);
		cmUserExtendInfo.setUserId(cmUserInfo.getId());
		cmUserExtendInfoDao.save(cmUserExtendInfo);
		passMap.put("cmUserInfo", cmUserInfo);
		AccountUserLog accountUserLog = (AccountUserLog) passMap.get("accountUserLog");
		accountUserLog.setUserId(cmUserInfo.getId());
		passMap.put("accountUserLog",accountUserLog);
		createAccount(passMap);
		
	}
	
	public void saveAllScore(CmUserInfo cmUserInfo,Map<String,Object> passMap){
			String clientId = passMap.get("clientId").toString();
			long t1= System.currentTimeMillis();
		
			cmUserInfoDao.save(cmUserInfo);
			
			long t2= System.currentTimeMillis();
			cmUserInfo = pmScoreLogService.addScoreRegister(cmUserInfo,clientId);
			long t3= System.currentTimeMillis();
			
			CmUserExtendInfo cmUserExtendInfo = new CmUserExtendInfo();
			cmUserExtendInfo.setUserId(cmUserInfo.getId());
			int b = (int) (Math.random()*73)+1;
			cmUserExtendInfo.setCoverThumbnail(host+"/diys/rs/default/cover/"+b+".jpg");
			Map<String,Object> laberMap = new HashMap<String, Object>();
			laberMap.put("type", "4");
			laberMap.put("limit", 3);
			List<String> laberList = laberDao.findRand(laberMap);
			cmUserExtendInfo.setAttentionLabel(StringUtils.join(laberList.toArray(), ","));
			cmUserExtendInfoDao.save(cmUserExtendInfo);
			long t4= System.currentTimeMillis();
			passMap.put("cmUserInfo", cmUserInfo);
			AccountUserLog accountUserLog = (AccountUserLog) passMap.get("accountUserLog");
			accountUserLog.setUserId(cmUserInfo.getId());
			passMap.put("accountUserLog",accountUserLog);
			createAccount(passMap);
			long t5= System.currentTimeMillis();
			
//			log.error("t2-t1="+(t2-t1)+",t3-t2="+(t3-t2)+",t4-t3="+(t4-t3)+",t5-t4="+(t5-t4));
		
			
		
	}
	
	
	public void updateExtendScore(CmUserInfo cmUserInfo,String clientId,CmUserExtendInfo cmUserExtendInfo){
		
		pmScoreLogService.addScore("1002", cmUserInfo,clientId,"","");
		cmUserExtendInfoDao.update(cmUserExtendInfo);
	}
	
	public void userBinding(CmUserInfo cmUserInfo,String clientId,String type){
		
		pmScoreLogService.addScore(type, cmUserInfo,clientId,"","");
		cmUserInfoDao.update(cmUserInfo);
	}
	
	
	public void updateAll(CmUserInfo cmUserInfo,String clientId,Map<String,String> map){
		String sex = map.get("sex");
    	String nickName = map.get("nickName");
    	String signature = map.get("signature");
    	String education = map.get("education");
    	String career = map.get("career");
    	String qq = map.get("qq");
    	String birth = map.get("birth");
    	String headImageUrl = map.get("headImageUrl");
		if(StringUtil.isNotEmpty(birth))
    	{
    		cmUserInfo.setBirth(birth);
    		pmScoreLogService.addScore("1007", cmUserInfo,clientId,"","");
    	}
    	if(StringUtil.isNotEmpty(sex))
    	{
    		cmUserInfo.setSex(Integer.parseInt(sex));
    		pmScoreLogService.addScore("1005", cmUserInfo,clientId,"","");
    	}
    	if(StringUtil.isNotEmpty(nickName))
    	{
    		cmUserInfo.setNickName(nickName);
    		pmScoreLogService.addScore("1004", cmUserInfo,clientId,"","");
    	}
    	if(StringUtil.isNotEmpty(headImageUrl))
    	{
    		cmUserInfo.setHeadImageUrl(host+headImageUrl);
    		pmScoreLogService.addScore("1001", cmUserInfo,clientId,"","");
    	}
    	CmUserExtendInfo cmUserExtendInfo = cmUserExtendInfoDao.getById(cmUserInfo.getId());
    	if(cmUserExtendInfo == null)
    	{
    		cmUserExtendInfo = new CmUserExtendInfo();
    		cmUserExtendInfo.setUserId(cmUserInfo.getId());
    	}
    	if(StringUtil.isNotEmpty(qq))
    	{
    		cmUserExtendInfo.setQq(qq);
    		
    		pmScoreLogService.addScore("1010", cmUserInfo,clientId,"","");
    	}
    	if(StringUtil.isNotEmpty(signature))
    	{
    		cmUserExtendInfo.setSignature(HtmlUtils.htmlEscape(signature));
    		
    		pmScoreLogService.addScore("1006", cmUserInfo,clientId,"","");
    	}
    	if(StringUtil.isNotEmpty(education))
    	{
    		cmUserExtendInfo.setEducation(education);
    		pmScoreLogService.addScore("1008", cmUserInfo,clientId,"","");
    	}
    	if(StringUtil.isNotEmpty(career))
    	{
    		cmUserExtendInfo.setCareer(career);
    		
    		pmScoreLogService.addScore("1009", cmUserInfo,clientId,"","");
    	}
    	cmUserInfoDao.update(cmUserInfo);
    	cmUserExtendInfoDao.update(cmUserExtendInfo);
	}
	
	
	public void zXiu(Map<String,String> map){
		String clientId = map.get("clientId");
    	String userId = map.get("userId");
		CmUserInfo cmUserInfo = cmUserInfoDao.getById(Long.parseLong(userId));
    	if(StringUtil.isNotEmpty(cmUserInfo))
    	{
    		pmScoreLogService.addScore("27", cmUserInfo, clientId, "","");
    		cmUserInfoDao.update(cmUserInfo);
    	}
    	
	}

	public List<CmUserInfo> findPriCmUser(Map<String, String> map) {
		return cmUserInfoDao.findPriCmUser(map);
	}

	public void updatePartCmUser(final CmUserInfoUpdateDto updateUser) {
		ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance( "threadPoolManager");
		threadPoolManager.addTask(
			new Runnable() {
				@Override
				public void run() {
					try {
						cmUserInfoDao.updatePartCmUser(updateUser);
					}catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
	   );
		
	}
}
