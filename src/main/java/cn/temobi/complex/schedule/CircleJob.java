package cn.temobi.complex.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.SetUniqueList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.entity.CmCircle;
import cn.temobi.complex.entity.CmUserExtendInfo;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.service.CmCircleProductService;
import cn.temobi.complex.service.CmCircleServive;
import cn.temobi.complex.service.CmCircleUserService;
import cn.temobi.complex.service.CmUserExtendInfoService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.core.util.IKAnalzyerUtil;
import cn.temobi.core.util.SpringContextUtils;

public class CircleJob {

	private static Logger log = LoggerFactory.getLogger(CircleJob.class);
	CmCircleServive cmCircleServive = (CmCircleServive) SpringContextUtils.getBean("cmCircleServive");
	CmUserInfoService cmUserInfoService = (CmUserInfoService) SpringContextUtils.getBean("cmUserInfoService");
	CmUserExtendInfoService cmUserExtendInfoService = (CmUserExtendInfoService) SpringContextUtils.getBean("cmUserExtendInfoService");
	PmProductInfoService pmProductInfoService = (PmProductInfoService) SpringContextUtils.getBean("pmProductInfoService");
	CmCircleProductService cmCircleProductService = (CmCircleProductService) SpringContextUtils.getBean("cmCircleProductService");
	CmCircleUserService cmCircleUserService = (CmCircleUserService) SpringContextUtils.getBean("cmCircleUserService");
	
//	public void execute() {
//		log.error("圈子作品数据更新");
//		long start = System.currentTimeMillis();
//		Map<String,Object> map = new HashMap<String, Object>();
//		List<CmCircle> list = cmCircleServive.findByMap(map);
//		//分出所有的词
//		List<String> keyList = SetUniqueList.decorate(new ArrayList<String>());
//		if(list != null && list.size() > 0)
//		{
//			for(CmCircle cmCircle:list)
//			{
//				keyList.addAll(IKAnalzyerUtil.split(cmCircle.getName()));
//		    	keyList.addAll(IKAnalzyerUtil.split(cmCircle.getDepict()));
//			}
//			
//		}
//		//查询出所有的用户和作品
//		map.put("audit", "1");
//		map.put("isPublic", "1");
//		map.put("isLaberNoNull", "isLaberNoNull");
//		List<PmProductInfo> productlist = pmProductInfoService.findByMap(map);
//		List<CmUserExtendInfo> userlist = cmUserExtendInfoService.findByMap(map);
//		//查询出所有分词后的词关联的图片
//		List<Long> newList = null;
//		if(productlist.size() > 0)
//		{
//			for(String temp:keyList)
//			{
//				newList = SetUniqueList.decorate(new ArrayList<String>());
//				for(PmProductInfo obj:productlist)
//				{
//					if(obj.getProductLabel().indexOf(temp) != -1)
//					{
//						newList.add(obj.getId());
//					}
//				}
//				CacheHelper.getInstance().set(60*60*24,temp+"product", newList);
//			}
//		}
//		if(userlist.size() > 0)
//		{
//			for(String temp:keyList)
//			{
//				newList = SetUniqueList.decorate(new ArrayList<String>());
//				for(CmUserExtendInfo obj:userlist)
//				{
//					if(obj.getAttentionLabel().indexOf(temp) != -1)
//					{
//						newList.add(obj.getUserId());
//					}
//				}
//				CacheHelper.getInstance().set(60*60*24,temp+"user", newList);
//			}
//		}
//		
//		if(list != null && list.size() > 0)
//		{
////			Map<String,Object> deletMap = new HashMap<String, Object>();
////			deletMap.put("flag", "0");
////			cmCircleProductService.delBycircleId(deletMap);
////			cmCircleUserService.delBycircleId(deletMap);
//			for(CmCircle cmCircle:list)
//			{
//				try {
//					cmCircleServive.excuteProductJob(cmCircle);
//				} catch (Exception e) {
//					log.error("圈子数据出错"+cmCircle.getId());
//					log.error(e.getMessage());
//				}
//			}
//			
//		}
//		long end = System.currentTimeMillis();
//		log.error("圈子数据更新结束"+(end-start)/1000);
//	}
	
	public void execute() {
		log.error("圈子数据更新");
		long start = System.currentTimeMillis();
		Map<String,Object> map = new HashMap<String, Object>();
		List<CmCircle> list = cmCircleServive.findByMap(map);
		//分出所有的词
		List<String> keyList = SetUniqueList.decorate(new ArrayList<String>());
		if(list != null && list.size() > 0)
		{
			for(CmCircle cmCircle:list)
			{
				keyList.addAll(IKAnalzyerUtil.split(cmCircle.getName()));
		    	keyList.addAll(IKAnalzyerUtil.split(cmCircle.getDepict()));
			}
			
		}
		//查询出所有的用户和作品
		map.put("audit", "1");
		map.put("isPublic", "1");
		map.put("isLaberNoNull", "isLaberNoNull");
		List<PmProductInfo> productlist = pmProductInfoService.findByMap(map);
		//查询出所有分词后的词关联的图片
		List<Long> newList = null;
		if(productlist.size() > 0)
		{
			for(String temp:keyList)
			{
				newList = SetUniqueList.decorate(new ArrayList<String>());
				for(PmProductInfo obj:productlist)
				{
					if(obj.getProductLabel().indexOf(temp) != -1)
					{
						newList.add(obj.getId());
					}
				}
				CacheHelper.getInstance().set(60*60*24,temp+"product", newList);
			}
		}
		
		if(list != null && list.size() > 0)
		{
//			Map<String,Object> deletMap = new HashMap<String, Object>();
//			deletMap.put("flag", "0");
//			cmCircleProductService.delBycircleId(deletMap);
//			cmCircleUserService.delBycircleId(deletMap);
			for(CmCircle cmCircle:list)
			{
				try {
					cmCircleServive.excuteProductJob(cmCircle);
				} catch (Exception e) {
					log.error("圈子数据出错"+cmCircle.getId());
					log.error(e.getMessage());
				}
			}
			
		}
		long end = System.currentTimeMillis();
		log.error("圈子数据更新结束"+(end-start)/1000);
	}
}
