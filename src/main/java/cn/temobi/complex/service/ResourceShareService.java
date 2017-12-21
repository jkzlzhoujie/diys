package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.CmUserMessageDao;
import cn.temobi.complex.dao.MaterialUseLogDao;
import cn.temobi.complex.dao.PmProductDiscussDao;
import cn.temobi.complex.dao.PmProductInfoDao;
import cn.temobi.complex.dao.PmTopicJoinProductsDao;
import cn.temobi.complex.dao.PmTopicPsProductsDao;
import cn.temobi.complex.dao.ResourceShareDao;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.complex.entity.MaterialUseLog;
import cn.temobi.complex.entity.PmProductDiscuss;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.entity.ResourceShare;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;

/**
 * 单个表情分享记录
 * @author hjf
 * 2014 六月 9 16:39:05
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("resourceShareService")
public class ResourceShareService extends ServiceBase{
	
	@Resource(name = "resourceShareDao")
	private ResourceShareDao resourceShareDao;
	
	@Resource(name = "pmTopicPsProductsDao")
	private PmTopicPsProductsDao pmTopicPsProductsDao;
	
	@Resource(name = "pmTopicJoinProductsDao")
	private PmTopicJoinProductsDao pmTopicJoinProductsDao;
	
	@Resource(name = "pmProductInfoDao")
	private PmProductInfoDao pmProductInfoDao;
	
	@Resource(name = "cmUserMessageDao")
	private CmUserMessageDao cmUserMessageDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	@Resource(name = "pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name = "materialUseLogDao")
	private MaterialUseLogDao materialUseLogDao;
	
	@Resource(name = "pmProductDiscussDao")
	private PmProductDiscussDao pmProductDiscussDao;
	
	
	
	public void update(Long packetId){
	}
	
	public Page<ResourceShare> findByPage(Page page,Object map){
		return resourceShareDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return resourceShareDao.getCount(map);
	}
	
	public ResourceShare getById(Long id){
		return resourceShareDao.getById(id);
	}
	
	public int save(ResourceShare entity){
		return resourceShareDao.save(entity);
	}
	
	public int delete(Object id){
		return resourceShareDao.delete(id);
	}
	
	public int delete(ResourceShare entity){
		return resourceShareDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return resourceShareDao.findByMap(map);
	}
    
	public List<ResourceShare> findAll(){
		return resourceShareDao.findAll();
	}
	
	public List<ResourceShare> findAll(ResourceShare entity){
		return resourceShareDao.findAll(entity);
	}
	
	
	public ResponseObject saveAll(Map<String,String> map,ResponseObject object){
		
		String resourceId = map.get("resourceId");
		String userId = map.get("userId");
		String productId = map.get("productId");
		String themeId = map.get("themeId");
    	String imei = map.get("imei");
    	String version = map.get("osVersion");
    	String type = map.get("type");
    	String clientId = map.get("clientId");
    	String shareType = map.get("shareType");
    	String shareStyle = map.get("shareStyle");
    	String expressIds = map.get("expressIds");
    	String chartletIds = map.get("chartletIds");
    	String yanWritings = map.get("yanWritings");
    	String writings = map.get("writings");
    	String useType = map.get("useType");
    	
    	log.error("分享开始！");
    	
		if(StringUtil.isNotEmpty(clientId) && StringUtil.isNotEmpty(type)) {
			ResourceShare resourceShare = new ResourceShare();
			resourceShare.setClientId(Long.parseLong(clientId));
			if(StringUtil.isNotEmpty(themeId))
			{
				resourceShare.setThemeId(Long.parseLong(themeId));
			}
			if(StringUtil.isNotEmpty(userId))
			{
				resourceShare.setUserId(Long.parseLong(userId));
			}
			if(StringUtil.isNotEmpty(productId))
			{
				resourceShare.setProductId(Long.parseLong(productId));
			}
			resourceShare.setImei(imei);
			resourceShare.setVersion(version);
			resourceShare.setShareType(shareType);
			resourceShare.setShareStyle(shareStyle);
			resourceShare.setType(type);
			resourceShare.setUseType(useType);
			resourceShare.setExpressIds(expressIds);
			resourceShare.setChartletIds(chartletIds);
			resourceShare.setYanWritings(yanWritings);
			resourceShare.setWritings(writings);
			
			if("5".equals(type))
			{
				if(StringUtil.isEmpty(productId))
				{
					return object;
				}
				if(StringUtil.isEmpty(userId))
				{
					return object;
				}
				PmProductInfo pmProductInfo = pmProductInfoDao.getById(Long.parseLong(productId));
		    	if(pmProductInfo == null)
		    	{
		    		return object;
		    	}
		    	
		    	CmUserInfo cmUserInfo = cmUserInfoDao.getById(Long.parseLong(userId));
		    	if(cmUserInfo == null)
		    	{
		    		return object;
		    	}
		    	
		    	resourceShare.setResourceId(pmProductInfo.getResourceId());
		    	
		    	pmProductInfo.setShareCount(pmProductInfo.getShareCount()+1);
		    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+5);
		    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+5);
		    	pmProductInfoDao.update(pmProductInfo);
		    	
		    	pmScoreLogService.addScore("21", cmUserInfo, clientId, pmProductInfo.getId()+"","");
		    	cmUserInfoDao.update(cmUserInfo);
		    	
		    	CmUserInfo cmUserInfoTo = cmUserInfoDao.getById(pmProductInfo.getUserId());
		    	pmScoreLogService.addScore("16", cmUserInfoTo, cmUserInfoTo.getClientId()+"", pmProductInfo.getId()+"","");
		    	cmUserInfoDao.update(cmUserInfoTo);
		    	
		    	if(pmProductInfo.getIsPublic() == 2){
	    			Map<String,Object> joinMap = new HashMap<String, Object>();
	    			if(pmProductInfo.getCreateType() == 1){
	    				
	    				joinMap.put("productId", pmProductInfo.getId());
	    				List<PmTopicPsProducts> psList = pmTopicPsProductsDao.findByMap(joinMap);
	    				PmTopicPsProducts pmTopicPsProducts = psList.get(0);
	    				
	    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.S_STRING, "27", pmTopicPsProducts.getJoinId()+"", "");
	    			}else if(pmProductInfo.getCreateType() == 0){
	    				joinMap.put("productId", pmProductInfo.getId());
	    				List<PmTopicJoinProducts> joinList = pmTopicJoinProductsDao.findByMap(joinMap);
	    				PmTopicJoinProducts pmTopicJoinProducts = joinList.get(0);
	    				
	    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.S_STRING, "27", pmTopicJoinProducts.getId()+"", "");
	    			}
	    		}else{
			    	CmUserMessage cmUserMessage = new CmUserMessage();
			    	cmUserMessage.setContent("分享了你的作品");
		    		cmUserMessage.setType(3);
			    	cmUserMessage.setProductId(pmProductInfo.getId());
			    	cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
			    	cmUserMessage.setUserId(pmProductInfo.getUserId());
			    	cmUserMessage.setSendUserId(Long.parseLong(userId));
			    	cmUserMessageDao.save(cmUserMessage);
			    	PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.S_STRING, "7", pmProductInfo.getId()+"", "");
	    		}
	    	}else{
				resourceShare.setResourceId(resourceId);
				
			}
			resourceShareDao.save(resourceShare);
			
//			if("6".equals(type))
//			{
//				Map<String,Object> param = new HashMap<String, Object>();
//				param.put("resourceId", resourceId);
//		    	List<PmProductInfo> list = pmProductInfoDao.findByMap(param);
//		    	if(list == null || list.size() <= 0){
//		    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
//					object.setDesc("作品还未上传");
//					return object;
//		    	}
//		    	PmProductInfo pmProductInfo = list.get(0);
//		    	
//				PmProductDiscuss pmProductDiscuss = new PmProductDiscuss();
//		    	pmProductDiscuss.setProductId(pmProductInfo.getId());
//		    	pmProductDiscuss.setUserId(pmProductInfo.getUserId());
//		    	pmProductDiscuss.setDiscussUserId(pmProductInfo.getUserId());
//		    	pmProductDiscuss.setParentDiscussId(0L);
//		    	pmProductDiscuss.setType(2);
//		    	pmProductDiscuss.setImageUrl(pmProductInfo.getThumbnail());
//		    	pmProductDiscussDao.save(pmProductDiscuss);
//				
//			}
			
			List<MaterialUseLog> list = new ArrayList<MaterialUseLog>();
		  	if(StringUtil.isNotEmpty(expressIds))
	    	{
	    		String materialResIds[] = expressIds.split(",");
    			for(String materialResId:materialResIds)
    			{
    				if(!"0".equals(materialResId))
    				{
    					MaterialUseLog materialUseLog = new MaterialUseLog();
	    				materialUseLog.setMaterialResId(Long.parseLong(materialResId));
	    				materialUseLog.setType("2");
	    				materialUseLog.setMaterialType("3");
	    				materialUseLog.setOtherId(resourceShare.getResourceId());
	    				list.add(materialUseLog);
    				}
    			}
	    	}
		  	if(StringUtil.isNotEmpty(chartletIds))
	    	{
	    		String materialResIds[] = chartletIds.split(",");
    			for(String materialResId:materialResIds)
    			{
    				if(!"0".equals(materialResId))
    				{
	    				MaterialUseLog materialUseLog = new MaterialUseLog();
	    				materialUseLog.setMaterialResId(Long.parseLong(materialResId));
	    				materialUseLog.setType("2");
	    				materialUseLog.setMaterialType("1");
	    				materialUseLog.setOtherId(resourceShare.getResourceId());
	    				list.add(materialUseLog);
    				}
    			}
	    	}
		  	if(StringUtil.isNotEmpty(yanWritings))
	    	{
	    		String materialResIds[] = yanWritings.split(",");
    			for(String materialResId:materialResIds)
    			{
    				if(!"0".equals(materialResId))
    				{
	    				MaterialUseLog materialUseLog = new MaterialUseLog();
	    				materialUseLog.setMaterialResId(Long.parseLong(materialResId));
	    				materialUseLog.setType("2");
	    				materialUseLog.setMaterialType("2");
	    				materialUseLog.setOtherId(resourceShare.getResourceId());
	    				list.add(materialUseLog);
    				}
    			}
	    	}
		  	if(list.size() > 0)
		  	{
		  		materialUseLogDao.insertList(list);
		  	}
			log.error("分享成功！");
			object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		}
		return object;
	}
}
