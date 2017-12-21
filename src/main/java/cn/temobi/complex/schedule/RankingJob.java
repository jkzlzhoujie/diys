package cn.temobi.complex.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserRanking;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserRankingService;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.SpringContextUtils;
import cn.temobi.core.util.StringUtil;

public class RankingJob {

	private static Logger log = LoggerFactory.getLogger(RankingJob.class);
	CmUserRankingService cmUserRankingService = (CmUserRankingService) SpringContextUtils.getBean("cmUserRankingService");
	PmScoreLogService pmScoreLogService = (PmScoreLogService) SpringContextUtils.getBean("pmScoreLogService");
	CmUserInfoService cmUserInfoService = (CmUserInfoService) SpringContextUtils.getBean("cmUserInfoService");
	
	public void execute() {
		//自动点赞
		log.error("排名开始执行");
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("orderField", "totalScore");
//		map.put("limit",3);
//		map.put("offset", 0);
//		map.put("time", DateUtils.getNextDate(new Date(), -2));
//		List<CmUserRanking> list = cmUserRankingService.findByMap(map);
//		Map<String,Object> userMap = new HashMap<String, Object>();
//		if(list != null && list.size() > 0)
//		{
//			for(CmUserRanking cmUserRanking:list){
//				userMap.put("userId", cmUserRanking.getUserId());
//				userMap.put("id", cmUserRanking.getId());
//				Number maxNum = cmUserRankingService.maxNum(userMap);
//				if(StringUtil.isEmpty(maxNum) || cmUserRanking.getTotalScoreNum() < maxNum.intValue())
//				{
//					CmUserInfo cmUserInfo = cmUserInfoService.getById(cmUserRanking.getUserId());
//					pmScoreLogService.addScore2("1021", cmUserInfo, cmUserInfo.getClientId()+"","","",cmUserRanking.getTotalScoreNum()+"");
//				}
//			}
//		}
//		map.put("limit",7);
//		map.put("offset", 3);
//		list = cmUserRankingService.findByMap(map);
//		if(list != null && list.size() > 0)
//		{
//			for(CmUserRanking cmUserRanking:list){
//				userMap.put("userId", cmUserRanking.getUserId());
//				userMap.put("id", cmUserRanking.getId());
//				Number maxNum = cmUserRankingService.maxNum(userMap);
//				if(StringUtil.isEmpty(maxNum) || cmUserRanking.getTotalScoreNum() < maxNum.intValue())
//				{
//					CmUserInfo cmUserInfo = cmUserInfoService.getById(cmUserRanking.getUserId());
//					pmScoreLogService.addScore2("1022", cmUserInfo, cmUserInfo.getClientId()+"","","",cmUserRanking.getTotalScoreNum()+"");
//				}
//			}
//		}
//		map.put("limit",20);
//		map.put("offset", 10);
//		list = cmUserRankingService.findByMap(map);
//		if(list != null && list.size() > 0)
//		{
//			for(CmUserRanking cmUserRanking:list){
//				userMap.put("userId", cmUserRanking.getUserId());
//				userMap.put("id", cmUserRanking.getId());
//				Number maxNum = cmUserRankingService.maxNum(userMap);
//				if(StringUtil.isEmpty(maxNum) || cmUserRanking.getTotalScoreNum() < maxNum.intValue())
//				{
//					CmUserInfo cmUserInfo = cmUserInfoService.getById(cmUserRanking.getUserId());
//					pmScoreLogService.addScore2("1023", cmUserInfo, cmUserInfo.getClientId()+"","","",cmUserRanking.getTotalScoreNum()+"");
//				}
//			}
//		}
//		map.put("limit",70);
//		map.put("offset", 30);
//		list = cmUserRankingService.findByMap(map);
//		if(list != null && list.size() > 0)
//		{
//			for(CmUserRanking cmUserRanking:list){
//				userMap.put("userId", cmUserRanking.getUserId());
//				userMap.put("id", cmUserRanking.getId());
//				Number maxNum = cmUserRankingService.maxNum(userMap);
//				if(StringUtil.isEmpty(maxNum) || cmUserRanking.getTotalScoreNum() < maxNum.intValue())
//				{
//					CmUserInfo cmUserInfo = cmUserInfoService.getById(cmUserRanking.getUserId());
//					pmScoreLogService.addScore2("1024", cmUserInfo, cmUserInfo.getClientId()+"","","",cmUserRanking.getTotalScoreNum()+"");
//				}
//			}
//		}
//		map.put("limit",200);
//		map.put("offset", 100);
//		list = cmUserRankingService.findByMap(map);
//		if(list != null && list.size() > 0)
//		{
//			for(CmUserRanking cmUserRanking:list){
//				userMap.put("userId", cmUserRanking.getUserId());
//				userMap.put("id", cmUserRanking.getId());
//				Number maxNum = cmUserRankingService.maxNum(userMap);
//				if(StringUtil.isEmpty(maxNum) || cmUserRanking.getTotalScoreNum() < maxNum.intValue())
//				{
//					CmUserInfo cmUserInfo = cmUserInfoService.getById(cmUserRanking.getUserId());
//					pmScoreLogService.addScore2("1025", cmUserInfo, cmUserInfo.getClientId()+"","","",cmUserRanking.getTotalScoreNum()+"");
//				}
//			}
//		}
		cmUserRankingService.executeSql();
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtils.getNextDate(new Date(), -2));
	}
	
}
