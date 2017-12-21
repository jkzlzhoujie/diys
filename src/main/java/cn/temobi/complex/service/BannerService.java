package cn.temobi.complex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.ApplicationCountDao;
import cn.temobi.complex.dao.ApplicationDao;
import cn.temobi.complex.dao.BannerDao;
import cn.temobi.complex.entity.Application;
import cn.temobi.complex.entity.ApplicationCount;
import cn.temobi.complex.entity.Banner;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.StringUtil;

/**
 * 横幅广告
 * @author hjf
 * 2014 三月 17 17:17:17
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("bannerService")
public class BannerService extends ServiceBase{
	
	@Resource(name = "bannerDao")
	private BannerDao bannerDao;
	
	@Resource(name = "applicationCountDao")
	private ApplicationCountDao applicationCountDao;
	
	public int update(Banner entity){
		return bannerDao.update(entity);
	}
	
	public Page<Banner> findByPage(Page page,Object map){
		return bannerDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return bannerDao.getCount(map);
	}
	
	public Banner getById(Long id){
		return bannerDao.getById(id);
	}
	
	public int save(Banner entity){
		return bannerDao.save(entity);
	}
	
	public int delete(Object id){
		return bannerDao.delete(id);
	}
	
	public int delete(Banner entity){
		return bannerDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return bannerDao.findByMap(map);
	}
    
	public List<Banner> findAll(){
		return bannerDao.findAll();
	}
	
	public List<Banner> findAll(Banner entity){
		return bannerDao.findAll(entity);
	}
	
	public ResponseObject bannerIndex(Map<String,Object> passMap,ResponseObject object){
		Map<String,Object> searchMap = new HashMap<String, Object>();
		String type = CommonUtil.nvl(passMap.get("type"));
		String columnType = CommonUtil.nvl(passMap.get("columnType"));
		String osVersion = CommonUtil.nvl(passMap.get("osVersion"));
		String system = CommonUtil.nvl(passMap.get("system"));
		Map<String,Object> returnMap = new HashMap<String, Object>();
		searchMap.put("status", "1");
		searchMap.put("currentDate", DateUtils.getCurrDateStr());
		if(StringUtil.isNotEmpty(system)){
			searchMap.put("system", system);
		}else{
			searchMap.put("system", 1);
		}
		
		if("show".equals(type))
		{
			searchMap.put("type", type+"_banner5"+"&"+columnType);
			List<Banner> topBanner = bannerDao.findByMap(searchMap);
			if(topBanner != null && topBanner.size() > 0)
			{
				for(Banner banner:topBanner){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			
			searchMap.put("type", type+"_banner7");
			List<Banner> bottomWord = bannerDao.findByMap(searchMap);
			if(bottomWord != null && bottomWord.size() > 0)
			{
				for(Banner banner:bottomWord){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			
			
			returnMap.put("topBanner", topBanner);
			returnMap.put("bottomWord", bottomWord);
		}else if("play".equals(type)){
			searchMap.put("type", type+"_banner8");
			List<Banner> topBanner = bannerDao.findByMap(searchMap);
			if(topBanner != null && topBanner.size() > 0)
			{
				for(Banner banner:topBanner){
					banner.setPicUrl(host+banner.getPicUrl());
					if(StringUtil.isNotEmpty(osVersion)){//玩顶部广告，6.0之后用新的广告
						if( Integer.valueOf(osVersion) > 36 ){
							banner.setPicUrl(host+banner.getPicUrl2());
						}
					}
				}
			}
			
			searchMap.put("type", type+"_banner13");
			List<Banner> bottomBanner = bannerDao.findByMap(searchMap);
			if(bottomBanner != null && bottomBanner.size() > 0)
			{
				for(Banner banner:bottomBanner){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			
			searchMap.put("type", type+"_banner14");
			List<Banner> bottomWord = bannerDao.findByMap(searchMap);
			if(bottomWord != null && bottomWord.size() > 0)
			{
				for(Banner banner:bottomWord){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			
			searchMap.put("type", type+"_banner9");
			List<Banner> oneBanner = bannerDao.findByMap(searchMap);
			if(oneBanner != null && oneBanner.size() > 0)
			{
				for(Banner banner:oneBanner){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			
			searchMap.put("type", type+"_banner10");
			List<Banner> twoBanner = bannerDao.findByMap(searchMap);
			if(twoBanner != null && twoBanner.size() > 0)
			{
				for(Banner banner:twoBanner){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			
			searchMap.put("type", type+"_banner11");
			List<Banner> threeBanner = bannerDao.findByMap(searchMap);
			if(threeBanner != null && threeBanner.size() > 0)
			{
				for(Banner banner:threeBanner){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			
			searchMap.put("type", type+"_banner12");
			List<Banner> fourBanner = bannerDao.findByMap(searchMap);
			if(fourBanner != null && fourBanner.size() > 0)
			{
				for(Banner banner:fourBanner){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			
			searchMap.put("type", type+"_banner15");
			List<Banner> fiveforThreeBanner = bannerDao.findByMap(searchMap);
			if(fiveforThreeBanner != null && fiveforThreeBanner.size() > 0)
			{
				for(Banner banner:fiveforThreeBanner){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			searchMap.put("type", type+"_banner16");
			List<Banner> fiveforTwoBanner = bannerDao.findByMap(searchMap);
			if(fiveforTwoBanner != null && fiveforTwoBanner.size() > 0)
			{
				for(Banner banner:fiveforTwoBanner){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			
			returnMap.put("topBanner", topBanner);
			returnMap.put("bottomBanner", bottomBanner);
			returnMap.put("bottomWord", bottomWord);
			returnMap.put("oneBanner", oneBanner);
			returnMap.put("twoBanner", twoBanner);
			returnMap.put("threeBanner", threeBanner);
			returnMap.put("fourBanner", fourBanner);
			
			returnMap.put("fiveforThreeBanner", fiveforThreeBanner);
			returnMap.put("fiveforTwoBanner", fiveforTwoBanner);
		}else{
			searchMap.put("liketype", type);
			List<Banner> bannerList = bannerDao.findByMap(searchMap);
			if(bannerList != null && bannerList.size() > 0)
			{
				for(Banner banner:bannerList){
					banner.setPicUrl(host+banner.getPicUrl());
				}
			}
			
			returnMap.put("bannerList", bannerList);
		}
			
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	public ResponseObject bannerClick(Map<String,Object> passMap,ResponseObject object){
		Long clientId = CommonUtil.nvlLong(passMap.get("clientId"));
		Long applicationId = CommonUtil.nvlLong(passMap.get("applicationId"));
		
    	ApplicationCount applicationCount = new ApplicationCount();
    	applicationCount.setClientId(clientId);
    	applicationCount.setApplicationId(applicationId);
    	applicationCount.setType("2");
    	applicationCountDao.save(applicationCount);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
}
