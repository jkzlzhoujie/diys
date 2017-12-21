package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.list.SetUniqueList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.CmCircleDao;
import cn.temobi.complex.dao.CmCircleProductDao;
import cn.temobi.complex.dao.CmCircleUserDao;
import cn.temobi.complex.dao.CmCircleUserFollowDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.PmProductInfoDao;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.entity.CmCircle;
import cn.temobi.complex.entity.CmCircleProduct;
import cn.temobi.complex.entity.CmCircleUser;
import cn.temobi.complex.entity.CmCircleUserFollow;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.IKAnalzyerUtil;
import cn.temobi.core.util.PageModel;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.ThreadPoolManager;

import com.salim.cache.CacheHelper;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("cmCircleServive")
public class CmCircleServive extends ServiceBase{

	@Resource(name = "cmCircleDao")
	private CmCircleDao cmCircleDao;
	
	@Resource(name = "cmCircleUserFollowDao")
	private CmCircleUserFollowDao cmCircleUserFollowDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	@Resource(name = "pmProductInfoDao")
	private PmProductInfoDao pmProductInfoDao;
	
	@Resource(name = "pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name = "cmCircleProductDao")
	private CmCircleProductDao cmCircleProductDao;
	
	@Resource(name = "cmCircleUserDao")
	private CmCircleUserDao cmCircleUserDao;
	
	public Long check(Map map){
		return cmCircleDao.check(map);
	} 
	
	public int update(CmCircle entity){
		return cmCircleDao.update(entity);
	}
	
	public Page<CmCircle> findByPage(Page page,Object map){
		return cmCircleDao.findByPage(page, map);
	}
	

	public List<CmCircle> getByCircleIdAboutMe(Map<String, Object> map) {
		return cmCircleDao.getByCircleIdAboutMe(map);
		
	}
	
	public Page<CmCircle> findByPageTwo(Page page,Object map){
		return cmCircleDao.findByPageTwo(page, map);
	}
	
	public List findMyFollow(Map map){
		return cmCircleDao.findMyFollow(map);
	}
	
	public Number getCount(Map map){
		return cmCircleDao.getCount(map);
	}
	
	public CmCircle getById(Long id){
		return cmCircleDao.getById(id);
	}
	
	public int save(CmCircle entity){
    	return cmCircleDao.save(entity);
	}
	
	
	public int create(final CmCircle entity){
		cmCircleDao.save(entity);
		ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance( "threadPoolManager");
		threadPoolManager.addTask(new Runnable() {
			@Override
			public void run() {
				try {
					excute(entity);
				}catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		});
    	return 1;
	}
	
	public int updateCm(final CmCircle entity){
		cmCircleDao.update(entity);
		ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance( "threadPoolManager");
		threadPoolManager.addTask(new Runnable() {
			@Override
			public void run() {
				try {
					excute(entity);
				}catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		});
    	return 1;
	}
	
	public void excute(CmCircle entity){
		List<String> keyList = SetUniqueList.decorate(new ArrayList<String>());
    	keyList.addAll(IKAnalzyerUtil.split(entity.getName()));
    	keyList.addAll(IKAnalzyerUtil.split(entity.getDepict()));
		if(keyList != null && keyList.size() > 0)
    	{
    		Map<String,Object> insertMap = new HashMap<String, Object>();
    		List<Long> userTotal = SetUniqueList.decorate(new ArrayList<Long>());
    		List<Long> productTotal = SetUniqueList.decorate(new ArrayList<Long>());
    		Map<String,Object> map = new HashMap<String, Object>();
    		for(String temp:keyList)
    		{
    			List<Long> userIdList = null;
    			List<Long> productIdList = null;
    			map.put("keyword", temp);
    			Object userList = CacheHelper.getInstance().get(temp+"user");
    			if(StringUtil.isEmpty(userList))
    			{
    				userIdList = cmUserInfoDao.findCircleId(map);
    				CacheHelper.getInstance().set(60*60*24,temp+"user", userIdList);
    			}else{
    				userIdList = (List<Long>) userList;
    			}
    			userTotal.addAll(userIdList);
    			Object productList = CacheHelper.getInstance().get(temp+"product");
    			if(StringUtil.isEmpty(productList))
    			{
    				productIdList = pmProductInfoDao.findCircleId(map);
    				CacheHelper.getInstance().set(60*60*24,temp+"product", productIdList);
    			}else{
    				productIdList = (List<Long>) productList;
    			}
    			productTotal.addAll(productIdList);
    		}
    		insertMap.put("circleId", entity.getId());
    		insertMap.put("list", productTotal);
    		if(productTotal.size() > 0)
    		{
    			cmCircleProductDao.insertList(insertMap);
    		}
    		userTotal.remove(entity.getUserId());
    		insertMap.put("list", userTotal);
    		if(userTotal.size() > 0)
    		{
    			cmCircleUserDao.insertList(insertMap);
    		}
    	}
    	Map<String,Object> newMap = new HashMap<String, Object>();
    	newMap.put("flag","0");
    	newMap.put("circleId",entity.getId());
    	newMap.put("limit", 1);
    	List<CmCircleProduct> cmList = cmCircleProductDao.findByMap(newMap);
    	Number productNum = cmCircleProductDao.getCount(newMap);
    	Number userNum = cmCircleUserDao.getCount(newMap);
    	entity.setUserNum(userNum.intValue());
    	entity.setImageNum(productNum.intValue());
    	if(cmList != null && cmList.size() > 0)
    	{
    		CmCircleProduct cmCircleProduct = cmList.get(0);
    		entity.setLatestImage(cmCircleProduct.getThumbnail());
    		entity.setLatestProductId(cmCircleProduct.getProductId());
    		cmCircleDao.update(entity);
    	}else{
    		cmCircleDao.update(entity);
    	}
	}
	
	public int delete(Object id){
		return cmCircleDao.delete(id);
	}
	
	public int delete(CmCircle entity){
		return cmCircleDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return cmCircleDao.findByMap(map);
	}
    
	public List<CmCircle> findAll(){
		return cmCircleDao.findAll();
	}

	
	
//	public void excuteJob(CmCircle entity){
//		List<String> keyList = SetUniqueList.decorate(new ArrayList<String>());
//    	keyList.addAll(IKAnalzyerUtil.split(entity.getName()));
//    	keyList.addAll(IKAnalzyerUtil.split(entity.getDepict()));
//		if(keyList != null && keyList.size() > 0)
//    	{
//    		Map<String,Object> insertMap = new HashMap<String, Object>();
//    		List<Long> userTotal = SetUniqueList.decorate(new ArrayList<Long>());
//    		List<Long> productTotal = SetUniqueList.decorate(new ArrayList<Long>());
//    		Map<String,Object> map = new HashMap<String, Object>();
//    		for(String temp:keyList)
//    		{
//    			List<Long> userIdList = null;
//    			List<Long> productIdList = null;
//    			map.put("keyword", temp);
//    			Object userList = CacheHelper.getInstance().get(temp+"user");
//    			if(StringUtil.isNotEmpty(userList))
//    			{
//    				userIdList = (List<Long>) userList;
//    				userTotal.addAll(userIdList);
//    			}
//    			Object productList = CacheHelper.getInstance().get(temp+"product");
//    			if(StringUtil.isNotEmpty(productList))
//    			{
//    				productIdList = (List<Long>) productList;
//    				productTotal.addAll(productIdList);
//    			}
//    		}
//    		insertMap.put("circleId", entity.getId());
//    		insertMap.put("searchAll", "searchAll");
//    		if(productTotal.size() > 0)
//    		{
//    			List<Long> oldList = cmCircleProductDao.findProductId(insertMap);
//    			productTotal.removeAll(oldList);
//    			if(productTotal.size() > 0)
//        		{
//	    			insertMap.put("list", productTotal);
//	    			cmCircleProductDao.insertList(insertMap);
//        		}
//    		}
//    		if(userTotal.size() > 0)
//    		{
//    			List<Long> oldList = cmCircleUserDao.findUserId(insertMap);
//    			userTotal.removeAll(oldList);
//    			userTotal.remove(entity.getUserId());
//    			if(userTotal.size() > 0)
//        		{
//	        		insertMap.put("list", userTotal);
//	    			cmCircleUserDao.insertList(insertMap);
//        		}
//    		}
//    	}
//    	Map<String,Object> newMap = new HashMap<String, Object>();
//    	newMap.put("flag","0");
//    	newMap.put("circleId",entity.getId());
//    	newMap.put("limit", 1);
//    	List<CmCircleProduct> cmList = cmCircleProductDao.findByMap(newMap);
//    	Number productNum = cmCircleProductDao.getCount(newMap);
//    	Number userNum = cmCircleUserDao.getCount(newMap);
//    	entity.setUserNum(userNum.intValue());
//    	entity.setImageNum(productNum.intValue());
//    	if(cmList != null && cmList.size() > 0)
//    	{
//    		CmCircleProduct cmCircleProduct = cmList.get(0);
//    		entity.setLatestImage(cmCircleProduct.getThumbnail());
//    		entity.setLatestProductId(cmCircleProduct.getProductId());
//    		cmCircleDao.update(entity);
//    	}else{
//    		cmCircleDao.update(entity);
//    	}
//	}
	
	
	public void excuteUserJob(CmCircle entity){
		List<String> keyList = SetUniqueList.decorate(new ArrayList<String>());
    	keyList.addAll(IKAnalzyerUtil.split(entity.getName()));
    	keyList.addAll(IKAnalzyerUtil.split(entity.getDepict()));
		if(keyList != null && keyList.size() > 0)
    	{
    		Map<String,Object> insertMap = new HashMap<String, Object>();
    		List<Long> userTotal = SetUniqueList.decorate(new ArrayList<Long>());
    		Map<String,Object> map = new HashMap<String, Object>();
    		for(String temp:keyList)
    		{
    			List<Long> userIdList = null;
    			map.put("keyword", temp);
    			Object userList = CacheHelper.getInstance().get(temp+"user");
    			if(StringUtil.isNotEmpty(userList))
    			{
    				userIdList = (List<Long>) userList;
    				userTotal.addAll(userIdList);
    			}
    		}
    		insertMap.put("circleId", entity.getId());
    		insertMap.put("searchAll", "searchAll");
    		if(userTotal.size() > 0)
    		{
    			entity.setUserNum(userTotal.size());
    			List<Long> oldList = cmCircleUserDao.findUserId(insertMap);
    			userTotal.removeAll(oldList);
    			userTotal.remove(entity.getUserId());
    			if(userTotal.size() > 0)
        		{
    				PageModel pageModel = new PageModel(userTotal, 1000);
    				for(int i=1;i<=pageModel.getTotalPages();i++){
    					List sblist = pageModel.getObjects(i);
    					insertMap.put("list", sblist);
    	    			cmCircleUserDao.insertList(insertMap);
    				}
	        		
        		}
    			cmCircleDao.update(entity);
    		}
    	}
	}
	
	
	public void excuteProductJob(CmCircle entity){
		List<String> keyList = SetUniqueList.decorate(new ArrayList<String>());
    	keyList.addAll(IKAnalzyerUtil.split(entity.getName()));
    	keyList.addAll(IKAnalzyerUtil.split(entity.getDepict()));
		if(keyList != null && keyList.size() > 0)
    	{
    		Map<String,Object> insertMap = new HashMap<String, Object>();
    		List<Long> productTotal = SetUniqueList.decorate(new ArrayList<Long>());
    		Map<String,Object> map = new HashMap<String, Object>();
    		for(String temp:keyList)
    		{
    			List<Long> productIdList = null;
    			map.put("keyword", temp);
    			Object productList = CacheHelper.getInstance().get(temp+"product");
    			if(StringUtil.isNotEmpty(productList))
    			{
    				productIdList = (List<Long>) productList;
    				productTotal.addAll(productIdList);
    			}
    		}
    		insertMap.put("circleId", entity.getId());
    		insertMap.put("searchAll", "searchAll");
    		if(productTotal.size() > 0)
    		{
    			entity.setImageNum(productTotal.size());
    			List<Long> oldList = cmCircleProductDao.findProductId(insertMap);
    			productTotal.removeAll(oldList);
    			if(productTotal.size() > 0)
        		{
	    			PageModel pageModel = new PageModel(productTotal, 1000);
    				for(int i=1;i<=pageModel.getTotalPages();i++){
    					List sblist = pageModel.getObjects(i);
    					insertMap.put("list", sblist);
    	    			cmCircleProductDao.insertList(insertMap);
    				}
        		}
    			cmCircleDao.update(entity);
    		}
    	}
	}
	
	public void saveAll(CmCircle cmCircle,CmUserInfo cmUserInfo,Map<String,String> map){
		String clientId = map.get("clientId");
		this.create(cmCircle);
    	pmScoreLogService.addScore("7", cmUserInfo, clientId, cmCircle.getId()+"","");
    	pmScoreLogService.addScore("1018", cmUserInfo, clientId,  cmCircle.getId()+"","");
    	cmUserInfoDao.update(cmUserInfo);
	}
	
	public void cancelUser(CmCircle cmCircle,CmCircleUser cmCircleUser)
	{
		cmCircle.setUserNum(cmCircle.getUserNum()-1);
		cmCircleDao.update(cmCircle);
		cmCircleUser.setFlag("1");
		cmCircleUserDao.update(cmCircleUser);
	}
	
	public void cancelProduct(CmCircle cmCircle,Map<String,String> map)
	{
		String productIdS = map.get("productIdS");
		String arr[] = productIdS.split(",");
    	for(String productId:arr)
    	{
    		Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("productId", productId);
	    	searchMap.put("circleId", cmCircle.getId());
	    	List<CmCircleProduct> list = cmCircleProductDao.findByMap(searchMap);
	    	if(list != null && list.size() > 0)
	    	{
	    		cmCircle.setImageNum(cmCircle.getImageNum()-1);
	    		CmCircleProduct cmCircleProduct = list.get(0);
	    		cmCircleProduct.setFlag("1");
	    		cmCircleProductDao.update(cmCircleProduct);
	    	}
    	}
    	this.update(cmCircle);
	}
	
	public void cancelFollow(CmCircle cmCircle,CmCircleUserFollow cmCircleUserFollow)
	{
		cmCircle.setFollowNum(cmCircle.getFollowNum()-1);
		cmCircleDao.update(cmCircle);
		cmCircleUserFollowDao.delete(cmCircleUserFollow);
	}
	
	public void follow(CmCircle cmCircle,CmCircleUserFollow cmCircleUserFollow)
	{
		cmCircle.setFollowNum(cmCircle.getFollowNum()+1);
		cmCircleDao.update(cmCircle);
		cmCircleUserFollowDao.save(cmCircleUserFollow);
    	
	}
	
	
	public ResponseObject circleIndex(Map<String,Object> passMap,ResponseObject object){
		String pageNo =  passMap.get("pageNo").toString();
		String pageSize =  passMap.get("pageSize").toString();
		String type =  CommonUtil.nvl(passMap.get("type"));
		Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		Map<String,Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("flag", "0");
    	List<CmCircle> alllist = null;
    	if("index".equals(type))
    	{
    		alllist = (List<CmCircle>) CacheHelper.getInstance().get("circleIndex");
    		if(alllist == null || alllist.size() <= 0)
    		{
    			searchMap.put("imageNum", "0");
    			searchMap.put("userNum", "0");
    			Page<CmCircle> pageResult = cmCircleDao.findByPage(page, searchMap);
            	alllist = pageResult.getResult();
            	if(alllist != null && alllist.size() > 0)
            	{
            		for(CmCircle cmCircle:alllist)
            		{
            			if(cmCircle.getLogo().indexOf(host) == -1)
    					{
	            			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
	            			cmCircle.setLogo(host+cmCircle.getLogo());
	            			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
    					}
            		}
            	}
            	
            	CacheHelper.getInstance().set(60*30,"circleIndex", alllist);
    		}
    	}else{
    		Page<CmCircle> pageResult = cmCircleDao.findByPage(page, searchMap);
        	alllist = pageResult.getResult();
        	if(alllist != null && alllist.size() > 0)
        	{
        		for(CmCircle cmCircle:alllist)
        		{
        			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
        			cmCircle.setLogo(host+cmCircle.getLogo());
        			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
        		}
        	}
    	}
    	object.setResponse(alllist);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}

}
