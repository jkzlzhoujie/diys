package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.action.def.FileOperationController;
import cn.temobi.complex.dao.CirclePostDao;
import cn.temobi.complex.dao.CirclePostPicDao;
import cn.temobi.complex.dao.CmTalenInfoDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.PmProductCollectPicDao;
import cn.temobi.complex.dao.PmProductInfoDao;
import cn.temobi.complex.dao.ProductRecommendDao;
import cn.temobi.complex.dao.SysProductTypeInfoDao;
import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.dto.ThemeUsedByPdtDto;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.complex.entity.CmTalenInfo;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.PmProductCollectPic;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.entity.ProductRecommend;
import cn.temobi.complex.entity.SysProductTypeInfo;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.PageModel;
import cn.temobi.core.util.StringUtil;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pmProductInfoService")
public class PmProductInfoService extends ServiceBase{
	
	@Resource(name = "pmProductInfoDao")
	private PmProductInfoDao pmProductInfoDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	@Resource(name = "productRecommendDao")
	private ProductRecommendDao productRecommendDao;
	
	@Resource(name = "cmTalenInfoDao")
	private CmTalenInfoDao cmTalenInfoDao;
	
	@Resource(name = "sysProductTypeInfoDao")
	private SysProductTypeInfoDao sysProductTypeInfoDao;
	
	@Resource(name = "pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name = "circlePostDao")
	private CirclePostDao circlePostDao;
	
	@Resource(name = "circlePostPicDao")
	private CirclePostPicDao circlePostPicDao;
	
	@Resource(name = "pmProductCollectPicDao")
	private PmProductCollectPicDao pmProductCollectPicDao;
	
	
	
	public Page<PmProductInfo> findByPage(Page page,Object map){
		return pmProductInfoDao.findByPage(page, map);
	}
	
	public Page<PmProductInfoDto> findByPageDto(Page page,Object map){
		return pmProductInfoDao.findByPage(page, "findByPageDto", map);
	}
	
	public List<PmProductInfoDto> findDtoMap(Map<String,Object> map){
		return pmProductInfoDao.findDtoMap(map);
	}
	
	public Page<PmProductInfoDto> findByPageDtoTo(Page page,Object map){
		return pmProductInfoDao.findByPageDtoTo(page, map);
	}
	
	public Page<ThemeUsedByPdtDto> findByPageDtoTo2(Page page,Object map){
		return pmProductInfoDao.findByPageDtoTo2(page, map);
	}
	
	public Number getCount(Map map){
		return pmProductInfoDao.getCount(map);
	}
	
	public PmProductInfo getById(Long id){
		return pmProductInfoDao.getById(id);
	}
	
	public int save(PmProductInfo entity){
		return pmProductInfoDao.save(entity);
	}
	
	public int delete(Object id){
		return pmProductInfoDao.delete(id);
	}
	
	public int delete(PmProductInfo entity){
		return pmProductInfoDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return pmProductInfoDao.findByMap(map);
	}
    
	public List<Long> findIdList(Map<String,Object> map){
		return pmProductInfoDao.findIdList(map);
	}
	
	public List<Long> findProductList(Map<String,Object> map){
		return pmProductInfoDao.findProductList(map);
	}
	
	public List findNew(Map map){
		return pmProductInfoDao.findNew(map);
	}
	
	public List findNotPraises(Map map){
		return pmProductInfoDao.findNotPraises(map);
	}
	
	public List<PmProductInfo> findAll(){
		return pmProductInfoDao.findAll();
	}
	
	public List<PmProductInfo> findAll(PmProductInfo entity){
		return pmProductInfoDao.findAll(entity);
	}
	
	public int update(PmProductInfo entity){
		return pmProductInfoDao.update(entity);
	}
	
	public void updateAll(Map<String,Object> map){
		pmProductInfoDao.updateAll(map);
	}
	
	public List<Long> findCircleId(Map<String,Object> map){
		return pmProductInfoDao.findCircleId(map);
	}
	
	public List<Long> findProductIdList(Map<String,Object> map){
		return pmProductInfoDao.findProductIdList(map);
	}
	
	public List<Long> findNewProductId(Map<String,Object> map){
		return pmProductInfoDao.findNewProductId(map);
	}
	
	public void zProduct(PmProductInfo pmProductInfo){
		pmProductInfo.setSearchCount(pmProductInfo.getSearchCount()+1);
		pmProductInfo.setPraiseCount(pmProductInfo.getPraiseCount()+1);
    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+2);
    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+2);
    	pmProductInfoDao.update(pmProductInfo);
		
		CmUserInfo oldCmUserInfo = cmUserInfoDao.getById(pmProductInfo.getUserId());
		oldCmUserInfo.setPraisesCount(oldCmUserInfo.getPraisesCount()+1);
		cmUserInfoDao.update(oldCmUserInfo);
	}
	
	
	public ResponseObject colouredIndex(Map<String,Object> passMap,ResponseObject object,String version){
		String type = "6";
		List<PmProductInfoDto> tempList = (List<PmProductInfoDto>) CacheHelper.getInstance().get("index@"+type);
		if(tempList == null || tempList.size() <= 0)
		{
    		Map<String,Object> index4Map = new HashMap<String, Object>();
    		index4Map.put("type",type);
    		List<ProductRecommend> reList = productRecommendDao.findByMap(index4Map);
    		List<Long> templist = new ArrayList<Long>();
			if(reList != null && reList.size() > 0)
			{
				for(ProductRecommend productRecommend:reList)
				{
					templist.add(productRecommend.getProductId());
				}
			}
			if(templist != null && templist.size() > 0)
			{
				Map<String, Object> map = new HashMap<String, Object>();
    			map.put("productList", templist);
    			map.put("ids",StringUtils.join(templist.toArray(), ","));
    			
				tempList = pmProductInfoDao.findDtoMap(map);
				
			}
		}
		seachList(type,null,tempList,"index@"+type);
		int rows = 3;
		if( CommonUtil.checkVersion(version) ){
			rows = 6;
    	}
		PageModel pageModel = new PageModel(tempList, rows);
		List<PmProductInfoDto> list = pageModel.getObjects(1);
		
		List<CmUserInfoListDto> drList = (List<CmUserInfoListDto>) CacheHelper.getInstance().get("colouredIndex@drList");
		if(drList == null || drList.size() <= 0)
		{
			List<Long> seaid = new ArrayList<Long>();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", "2");
			List<CmTalenInfo> cmTalenInfoList = cmTalenInfoDao.findByMap(map);
			if(cmTalenInfoList.size() > 0)
			{
				for(CmTalenInfo cmTalenInfo:cmTalenInfoList)
				{
					seaid.add(cmTalenInfo.getUserId());
				}
				if(seaid != null && seaid.size() > 0)
				{
					Map<String, Object> searchMap = new HashMap<String, Object>();
					searchMap.put("list", seaid);
					searchMap.put("ids", StringUtils.join(seaid.toArray(), ","));
					drList = cmUserInfoDao.findByList(searchMap);
					CacheHelper.getInstance().set(60*60*24, "colouredIndex@drList", drList);
				}
			}
		}
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("productList", list);
		returnMap.put("drList", drList);
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	public ResponseObject drawIndex(Map<String,Object> passMap,ResponseObject object){
     	Map<String, Object> searchMap = new HashMap<String, Object>();
     	searchMap.put("orderFried", "createdwhen");
    	String pageNo =  passMap.get("pageNo").toString();
		String pageSize =  passMap.get("pageSize").toString();
		Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		searchMap.put("isPublic", "4");
		
		List<PmProductInfoDto> list = (List<PmProductInfoDto>) CacheHelper.getInstance().get("drawIndex");
		if(list == null || list.size() <= 0)
		{
			Page<PmProductInfoDto> pageResult = pmProductInfoDao.findByPage(page, "findByPageDto", searchMap);
			list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				Map<String, Object> typeMap = new HashMap<String, Object>();
				List<SysProductTypeInfo> typeList = sysProductTypeInfoDao.findAll();
				if(typeList != null && typeList.size() > 0)
				{
					for(SysProductTypeInfo sysProductTypeInfo:typeList)
					{
						typeMap.put(sysProductTypeInfo.getId()+"", sysProductTypeInfo);
					}
				}
				for(PmProductInfoDto pmProductInfoDto:list)
				{
					if(pmProductInfoDto.getThumbnail().indexOf(host) == -1)
					{
						pmProductInfoDto.setThumbnail(host+pmProductInfoDto.getThumbnail());
						pmProductInfoDto.setUrl(host+pmProductInfoDto.getUrl());
						pmProductInfoDto.setDepict(HtmlUtils.htmlUnescape(pmProductInfoDto.getDescription()));
						SysProductTypeInfo sysProductTypeInfo = sysProductTypeInfoDao.getById(pmProductInfoDto.getTypeId());
						String path = sysProductTypeInfo.getPath();
						String typeName = "";
						if(StringUtil.isNotEmpty(path))
						{
							String arr[] = path.split(",");
							for(String a:arr)
							{
								typeName += ((SysProductTypeInfo) typeMap.get(a)).getName() +"-";
							}
							pmProductInfoDto.setTypeName(typeName.substring(0,typeName.length()-1)+","+ ((SysProductTypeInfo) typeMap.get(arr[0])).getTypeColor());
						}
					}
				}
				
				CacheHelper.getInstance().set(60*60*24,"drawIndex", list);
			}
		}
		object.setResponse(list);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	private void seachList(String type,List<PmTopicPsProducts> pslist,List<PmProductInfoDto> list,String key){
		Map<Long,Integer> updateMap = (Map<Long, Integer>) CacheHelper.getInstance().get("updateMap");
		if(StringUtil.isEmpty(updateMap))
		{
			updateMap = new ConcurrentHashMap<Long, Integer>();
		}
		if("5".equals(type))
		{
			if(pslist != null && pslist.size() > 0)
			{
				for(PmTopicPsProducts pmTopicPsProducts:pslist)
				{
					if(pmTopicPsProducts.getThumbnail().indexOf(host) == -1)
					{
						pmTopicPsProducts.setThumbnail(host+pmTopicPsProducts.getThumbnail());
						pmTopicPsProducts.setUrl(host+pmTopicPsProducts.getUrl());
						pmTopicPsProducts.setPsDescription(HtmlUtils.htmlUnescape(pmTopicPsProducts.getPsDescription()));
					}
					
					Integer tempNum = updateMap.get(pmTopicPsProducts.getProductId());
					if(StringUtil.isEmpty(tempNum))
					{
						tempNum = 0;
					}
					updateMap.put(pmTopicPsProducts.getProductId(),tempNum+1);
					pmTopicPsProducts.setSearchCount(pmTopicPsProducts.getSearchCount()+1);
				}
				
				if(StringUtil.isNotEmpty(key))
				{
					CacheHelper.getInstance().set(60*60*24, key, pslist);
				}
			}
			
		}else{
			if(list != null && list.size() > 0)
			{
				for(PmProductInfoDto pmProductInfoDto:list)
				{
					if(pmProductInfoDto.getThumbnail().indexOf(host) == -1)
					{
						pmProductInfoDto.setThumbnail(host+pmProductInfoDto.getThumbnail());
						pmProductInfoDto.setUrl(host+pmProductInfoDto.getUrl());
						pmProductInfoDto.setDescription(HtmlUtils.htmlUnescape(pmProductInfoDto.getDescription()));
					}
					
					pmProductInfoDto.setDepict(HtmlUtils.htmlUnescape(pmProductInfoDto.getDescription()));
					
					Integer tempNum = updateMap.get(pmProductInfoDto.getProductId());
					if(StringUtil.isEmpty(tempNum))
					{
						tempNum = 0;
					}
					updateMap.put(pmProductInfoDto.getProductId(),tempNum+1);
					pmProductInfoDto.setSearchCount(pmProductInfoDto.getSearchCount()+1);
				}
				
				if(StringUtil.isNotEmpty(key))
				{
					CacheHelper.getInstance().set(60*60*24, key, list);
				}
			}
		}
		
		CacheHelper.getInstance().set(60*60*24, "updateMap", updateMap);
	}

	public ResponseObject releaseProduct(ResponseObject object,Map<String, String> paramMap) {
		
		String userId = paramMap.get("userId");
		String resourceId = paramMap.get("resourceId");
		String type = paramMap.get("type");
		String clientId = paramMap.get("clientId");
		String circleId = paramMap.get("circleId");
		
		
		CmUserInfo cmUserInfo = cmUserInfoDao.getById(Long.parseLong(userId));
    	if(cmUserInfo == null){
    		return object;
    	}
    	if(cmUserInfo.getIsAccusation() == 1){
    		object.setCode(Constant.RESPONSE_ERROR_10006);
			object.setDesc("被举报不能使用");
			return object;
    	}
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("resourceId", resourceId);
    	List<PmProductInfo> list = pmProductInfoDao.findByMap(map);
    	if(list == null || list.size() <= 0){
    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
			object.setDesc("作品还未上传");
			return object;
    	}
    	
    	PmProductInfo pmProductInfo = list.get(0);
    	if(pmProductInfo.getIsPublic() != 0){
    		object.setCode(Constant.RESPONSE_ERROR_10003);
			object.setDesc("已发布");
			return object;
    	}
    	pmProductInfo.setUserId(Long.parseLong(userId));
    	if(StringUtil.isNotEmpty(type) && "2".equals(type))
    	{
    		pmProductInfo.setIsPublic(98);
    		
    		//积分增加
    		pmScoreLogService.addScore("3", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    		pmScoreLogService.addScore("1016", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    	}else{
    		pmProductInfo.setIsPublic(1);
    		
    		if("1".equals(cmUserInfo.getUserType()))//设计师
    		{
    			pmScoreLogService.addScore("5", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    		}else{
    			pmScoreLogService.addScore("1", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    		}
    		pmScoreLogService.addScore("1014", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    	
    		//放入缓存中
			List<String> allPruductKey = (List<String>) CacheHelper.getInstance().get("allPruductKey");
			if(allPruductKey != null && allPruductKey.size() > 0)
			{
				allPruductKey.add(pmProductInfo.getId()+"|"+System.currentTimeMillis()+"|0");
			}else{
				allPruductKey = new ArrayList<String>();
				allPruductKey.add(pmProductInfo.getId()+"|"+System.currentTimeMillis()+"|0");
			}
			CacheHelper.getInstance().set(60*60*24, "allPruductKey", allPruductKey);
    	
    	}
		
		cmUserInfo.setProductCount(cmUserInfo.getProductCount()+1);
		cmUserInfoDao.update(cmUserInfo);
		pmProductInfoDao.update(pmProductInfo);
		
		if(StringUtil.isNotEmpty(circleId)){
			CirclePost circlePost = new  CirclePost();
			circlePost.setDiscussNum(0);
			circlePost.setShareNum(0);
			circlePost.setZanNum(0);
			circlePost.setStatus(1);
			circlePost.setIsAccusation(0);
			circlePost.setUserId(cmUserInfo.getId());
			circlePost.setCreatedWhen(DateUtils.getCurrDateTimeStr());
			circlePost.setCircleId( Long.valueOf(circleId) ) ;
			circlePost.setText(pmProductInfo.getDescription());
			circlePost.setTitle("");
			circlePostDao.save(circlePost);
			
			CirclePostPic circlePostPic = new CirclePostPic();
			circlePostPic.setCirclePostId(circlePost.getId());
			circlePostPic.setPicUrl(pmProductInfo.getThumbnail());
			circlePostPic.setThumbnail(pmProductInfo.getUrl());
			circlePostPicDao.save(circlePostPic);
    	}
		
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		object.setDesc("成功");
		return object;
	}
	
public ResponseObject releaseProductList(ResponseObject object,Map<String, String> paramMap) {
		
		String userId = paramMap.get("userId");
		String resourceId = paramMap.get("resourceId");
		String type = paramMap.get("type");
		String clientId = paramMap.get("clientId");
		
		log.error("发布图片");
		Map<String,Object> map = new HashMap<String, Object>();
    	map.put("resourceId", resourceId);
    	List<PmProductInfo> list = pmProductInfoDao.findByMap(map);
    	PmProductInfo pmProductInfo = list.get(0);
    	
    	CmUserInfo cmUserInfo = cmUserInfoDao.getById(Long.parseLong(userId));
    	pmProductInfo.setUserId(Long.parseLong(userId));
    	if(StringUtil.isNotEmpty(type) && "2".equals(type)){
    		pmProductInfo.setIsPublic(98);
    		//积分增加
    		pmScoreLogService.addScore("3", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    		pmScoreLogService.addScore("1016", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    	}else{
    		pmProductInfo.setIsPublic(1);
    		if("1".equals(cmUserInfo.getUserType()))//设计师
    		{
    			pmScoreLogService.addScore("5", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    		}else{
    			pmScoreLogService.addScore("1", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    		}
    		pmScoreLogService.addScore("1014", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    	
    		//放入缓存中
			List<String> allPruductKey = (List<String>) CacheHelper.getInstance().get("allPruductKey");
			if(allPruductKey != null && allPruductKey.size() > 0)
			{
				allPruductKey.add(pmProductInfo.getId()+"|"+System.currentTimeMillis()+"|0");
			}else{
				allPruductKey = new ArrayList<String>();
				allPruductKey.add(pmProductInfo.getId()+"|"+System.currentTimeMillis()+"|0");
			}
			CacheHelper.getInstance().set(60*60*24, "allPruductKey", allPruductKey);
    	
    	}
    	
    	pmProductInfoDao.update(pmProductInfo);
		cmUserInfo.setProductCount(cmUserInfo.getProductCount()+1);
		cmUserInfoDao.update(cmUserInfo);
		
		object.setResponse(pmProductInfo);
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		object.setDesc("成功");
		return object;
	}
	

	public ResponseObject uploadResource(Map<String, String> paramMap ,HttpServletRequest request,
			HttpServletResponse response,ResponseObject object) {
		
		String userId = paramMap.get("userId");
		String resourceId = paramMap.get("resourceId");
		String clientId = paramMap.get("clientId");
		String circleId = paramMap.get("circleId");
		String description = paramMap.get("description");
		String laber = paramMap.get("laber");
		
		log.error("上传 相册");
		Map<String, String> map = new HashMap<String, String>();
		map.put("resourceId", resourceId);
    	List<PmProductInfo> list = pmProductInfoDao.findByMap(map);
    	if(list != null && list.size() > 0){
    		object.setCode(Constant.RESPONSE_ERROR_10003);
			object.setDesc("作品已上传");
			return object;
    	}
    	CmUserInfo cmUserInfo = cmUserInfoDao.getById(Long.parseLong(userId));
    	if(cmUserInfo == null){
    		return object;
    	}
    	if(cmUserInfo.getIsAccusation() == 1){
    		object.setCode(Constant.RESPONSE_ERROR_10006);
			object.setDesc("被举报不能使用");
			return object;
    	}
    	
		List<Map<String,String>>  returnMapList = new ArrayList<Map<String,String>>();
		
		try {
			//上传图片
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        Map fileMap =multipartRequest.getFileMap();
	        if(fileMap != null && fileMap.size()>0){
	        	FileOperationController fileOperationController = new FileOperationController();
	        	returnMapList = fileOperationController.uploadAppImage(request);
	        	if(returnMapList!= null && returnMapList.size()>0){
	        		for (Map<String, String> returnMap : returnMapList) {
	        			if(returnMap.get("error")!=null){
	        				object.setCode(Constant.RESPONSE_ERROR_10012);
	        				object.setDesc("图片上传失败");
	        				return object;
	        			}
	        		}
	        	}else{
	        		object.setCode(Constant.RESPONSE_ERROR_10012);
	        		object.setDesc("图片上传失败");
	        		return object;
	        	}
	        }else{
	        	object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
        		object.setDesc("请选择图片");
        		return object;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.error("相册上传 成功");
		if( returnMapList != null && returnMapList.size() > 0){
			PmProductInfo resource = new PmProductInfo();
			resource.setClientId(Long.parseLong(clientId));
			resource.setResourceId(resourceId);
			resource.setUrl(returnMapList.get(0).get("picUrl"));
			resource.setThumbnail(returnMapList.get(0).get("thumbnail"));
			resource.setAudit(1);
			if(StringUtil.isNotEmpty(description)){
				resource.setDescription(HtmlUtils.htmlEscape(description));
			}
			resource.setProductLabel(laber);
			resource.setCreateFrom("1");
			resource.setPicCollectFlag(1);
			resource.setPicCollectCount(returnMapList.size());
			pmProductInfoDao.save(resource);
			
			List<PmProductCollectPic> pmProductCollectPicList = new ArrayList<PmProductCollectPic>();
			List<CirclePostPic> circlePostPicList = new ArrayList<CirclePostPic>();
			for (int i = 0; i < returnMapList.size(); i++) {
		    	PmProductCollectPic pmProductCollectPic = new PmProductCollectPic();
		    	pmProductCollectPic.setProductId(resource.getId());
		    	pmProductCollectPic.setThumbnail(returnMapList.get(i).get("thumbnail"));
		    	pmProductCollectPic.setUrl(returnMapList.get(i).get("picUrl"));
		    	pmProductCollectPicList.add(pmProductCollectPic);
		    	
		    	if(StringUtil.isNotEmpty(circleId)){
		    		CirclePostPic circlePostPic = new CirclePostPic();
		    		circlePostPic.setPicUrl(returnMapList.get(i).get("picUrl"));
		    		circlePostPic.setThumbnail(returnMapList.get(i).get("thumbnail"));
		    		circlePostPicList.add(circlePostPic);
		    	}
			}
			if(pmProductCollectPicList!=null && pmProductCollectPicList.size()>0){
				pmProductCollectPicDao.saveBatch(pmProductCollectPicList);
			}
			if(circlePostPicList!=null && circlePostPicList.size() > 0){
				CirclePost circlePost = new  CirclePost();
				circlePost.setDiscussNum(0);
				circlePost.setShareNum(0);
				circlePost.setZanNum(0);
				circlePost.setStatus(1);
				circlePost.setIsAccusation(0);
				circlePost.setUserId(cmUserInfo.getId());
				circlePost.setCreatedWhen(DateUtils.getCurrDateTimeStr());
				circlePost.setCircleId( Long.valueOf(circleId) ) ;
				circlePost.setText(description);
				circlePost.setTitle("");
				circlePostDao.save(circlePost);
				for( CirclePostPic cpp:circlePostPicList){
					cpp.setCirclePostId(circlePost.getId());
				}
				circlePostPicDao.saveBatch(circlePostPicList);
	    	}
		}
		return null;
    	
	}
}
