package cn.temobi.complex.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.PmProductDiscussService;
import cn.temobi.complex.service.PmProductPraisesService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.complex.service.PmTopicPsProductsService;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.SpringContextUtils;
import cn.temobi.core.util.StringUtil;

public class PJob {

	private static Logger log = LoggerFactory.getLogger(PJob.class);
	
	PmTopicJoinProductsService pmTopicJoinProductsService = (PmTopicJoinProductsService) SpringContextUtils.getBean("pmTopicJoinProductsService");
	PmTopicPsProductsService pmTopicPsProductsService = (PmTopicPsProductsService) SpringContextUtils.getBean("pmTopicPsProductsService");
	PmProductDiscussService pmProductDiscussService = (PmProductDiscussService) SpringContextUtils.getBean("pmProductDiscussService");
	PmProductPraisesService pmProductPraisesService = (PmProductPraisesService) SpringContextUtils.getBean("pmProductPraisesService");
	CmUserInfoService cmUserInfoService = (CmUserInfoService) SpringContextUtils.getBean("cmUserInfoService");
	
	
	public void execute() {
		log.error("P图悬赏结算开始执行");
			try {
				Thread.sleep(1*1000);
				Map<String,Object> searchMap = new HashMap<String, Object>();
				searchMap.put("pJob23", "pJob23");
				List<PmTopicJoinProducts> list23 = pmTopicJoinProductsService.findByMap(searchMap);
				if(list23 != null && list23.size() > 0){
					for(PmTopicJoinProducts pmTopicJoinProducts:list23){
						String userId = pmTopicJoinProducts.getUserId()+"";
						Object object = CacheHelper.getInstance().get("PJobUserSend"+userId);
						if(StringUtil.isEmpty(object)){
							log.error("P图悬赏23提醒   用户为：" + userId);
							String month = pmTopicJoinProducts.getJoinTime().substring(5, 7);
							String day = pmTopicJoinProducts.getJoinTime().substring(8, 10);
							PushUtil.pullOneMessage(pmTopicJoinProducts.getUserId()+"", "亲，你"+month+"月"+day+"的悬赏仍未采纳，需尽快发赏，X粉已为你消得人憔悴", "21", pmTopicJoinProducts.getId()+"", null);
							CacheHelper.getInstance().set(60*60*24, "PJobUserSend"+userId, "isSend");
						}
					}
				}
				searchMap.remove("pJob23");
				searchMap.put("pJob", "pJob");
				List<PmTopicJoinProducts> list = pmTopicJoinProductsService.findByMap(searchMap);
				Map<String,Object> passMap = new HashMap<String, Object>();
				if(list != null && list.size() > 0){
					for(PmTopicJoinProducts pmTopicJoinProducts:list){
						log.error("P图悬赏24提醒  作品ID为：" + pmTopicJoinProducts.getId());
						CmUserInfo cmUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getUserId());
						searchMap.put("joinId", pmTopicJoinProducts.getId());
						searchMap.put("pJobUserId", pmTopicJoinProducts.getUserId());
						if(cmUserInfo!=null && cmUserInfo.getClientId()!=null){
							searchMap.put("pJobClientId", cmUserInfo.getClientId());
						}
						if(cmUserInfo!=null && cmUserInfo.getImei()!=null){
							searchMap.put("pJobImei", cmUserInfo.getImei());
						}
						//查询出不是自己的所有PS的作品,且不是自己小号的作品
						List<PmTopicPsProducts> pslist = pmTopicPsProductsService.findByMap(searchMap);
						if(pslist != null && pslist.size() > 0){
							Map<String,Object> psMap = new HashMap<String, Object>();
							int tempNum = -1;
							PmTopicPsProducts tempPs = null;
							for(PmTopicPsProducts pmTopicPsProducts:pslist){
								psMap.put("disUserId", pmTopicPsProducts.getPsUserId());
								psMap.put("productId", pmTopicPsProducts.getProductId());
								psMap.put("type", "0");
//								Number disCussNum =  pmProductDiscussService.getCount(psMap);
//								Number praisesNum =  pmProductPraisesService.getCount(psMap);
//								
//								if(StringUtil.isEmpty(disCussNum)){
//									disCussNum = 0;
//								}
//								if(StringUtil.isEmpty(praisesNum)){
//									praisesNum = 0;
//								}
								
								int hotScore = pmTopicPsProducts.getHotScore();
								
								//评论重复Imei
								Map<String,String> imeiMap = new HashMap<String, String>();
								int repeatCount = 0;
								if( pmTopicPsProducts.getDiscussCount() > 1){
									List<Long> discUserList = new ArrayList<Long>();
									discUserList = pmProductDiscussService.findIdList(psMap);
					    			if(discUserList != null && discUserList.size() > 0){
					    				Map<String,Object> discMap = new HashMap<String, Object>();
					    				discMap.put("list", discUserList);
					    				discMap.put("imei", "is not null");
					    				List<CmUserInfoListDto> cmUserlist = cmUserInfoService.findByList(discMap);
					    				if(cmUserlist != null && cmUserlist.size() > 0){
					    					for (CmUserInfoListDto cmUserInfoListDto : cmUserlist) {
					    						if(imeiMap.containsKey(cmUserInfoListDto.getImei())){
					    							repeatCount = repeatCount + 1;
						    					}else{
						    						imeiMap.put(cmUserInfoListDto.getImei(), cmUserInfoListDto.getImei());
						    					}
											}
					    					
					    				}
					    			}
								}
								
								//点赞重复Imei
								imeiMap.clear();
								if( pmTopicPsProducts.getPraiseCount() > 1){
									List<Long> praiseUserList = new ArrayList<Long>();
					    			praiseUserList = pmProductPraisesService.findIdList(psMap);
					    			if(praiseUserList != null && praiseUserList.size() > 0){
					    				Map<String,Object> praiseMap = new HashMap<String, Object>();
					    				praiseMap.put("list", praiseUserList);
					    				praiseMap.put("imei", "is not null");
					    				List<CmUserInfoListDto> cmUserlist = cmUserInfoService.findByList(praiseMap);
					    				if(cmUserlist != null && cmUserlist.size() > 0){
					    					for (CmUserInfoListDto cmUserInfoListDto : cmUserlist) {
					    						if(imeiMap.containsKey(cmUserInfoListDto.getImei())){
					    							repeatCount = repeatCount + 1;
						    					}else{
						    						imeiMap.put(cmUserInfoListDto.getImei(), cmUserInfoListDto.getImei());
						    					}
											}
					    					
					    				}
					    			}
								}
								
								//点赞和评论重复的imei号，总热度分值要扣除
								int totalNum = hotScore - repeatCount*2;
								if(totalNum > tempNum){
									tempNum = totalNum;
									tempPs = pmTopicPsProducts;
								}
							}
							
							passMap.put("joinId", pmTopicJoinProducts.getId());
							passMap.put("psId", tempPs.getId());
							pmTopicJoinProductsService.sysConfirmP(passMap);
							
							String month = pmTopicJoinProducts.getJoinTime().substring(5, 7);
							String day = pmTopicJoinProducts.getJoinTime().substring(8, 10);
							PushUtil.pullOneMessage(pmTopicJoinProducts.getUserId()+"", "亲，你"+month+"月"+day+"日的悬赏超过24小时未采纳，系统已推荐发赏至"+tempPs.getNickName()+"，TA热泪盈眶", "22", pmTopicJoinProducts.getId()+"", null);
							String monthTo = tempPs.getPsTime().substring(5, 7);
							String dayTo =  tempPs.getPsTime().substring(8, 10);
							PushUtil.pullOneMessage(tempPs.getPsUserId()+"", "亲，你于"+monthTo+"月"+dayTo+"日的图因为太帅，已经被采纳，奖金已充值至钱包账户", "23", pmTopicJoinProducts.getId()+"", null);
							log.error("P图悬赏  作品ID为：" + pmTopicJoinProducts.getId() + "成功！" + "采纳用户为：" + tempPs.getPsUserId());
						}else{
							passMap.put("joinId", pmTopicJoinProducts.getId());
							pmTopicJoinProductsService.backP(passMap);
							
							String month = pmTopicJoinProducts.getJoinTime().substring(5, 7);
							String day = pmTopicJoinProducts.getJoinTime().substring(8, 10);
							PushUtil.pullOneMessage(pmTopicJoinProducts.getUserId()+"", "亲，你"+month+"月"+day+"日的悬赏超过24小时暂无人参与，奖金已退还至钱包账户", "19", null, null);
						}
					}
				}
				
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			log.error("P图悬赏结算结束");
	}
	
	
	public static void main(String[] args) {
		
	}
}
