package cn.temobi.complex.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dto.OrderDto;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.AccountUserBuyService;
import cn.temobi.complex.service.PmCommodityInfoService;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.SpringContextUtils;
import cn.temobi.core.util.StringUtil;


public class AccountUserBuyRemindJob {

	private static Logger log = LoggerFactory.getLogger(ZJob.class);
	CmUserInfoService cmUserInfoService = (CmUserInfoService) SpringContextUtils.getBean("cmUserInfoService");
	AccountUserBuyService accountUserBuyService = (AccountUserBuyService) SpringContextUtils.getBean("accountUserBuyService");
	PmCommodityInfoService pmCommodityInfoService = (PmCommodityInfoService) SpringContextUtils.getBean("pmCommodityInfoService");
	
	public void execute() {
		
		log.error("订单未付款提醒开始执行");
		Map<String,String> map = new HashMap<String, String>();
		map.put("remindTime", "30");
		List<AccountUserBuy> accountUserBuyList = accountUserBuyService.findByMap(map);
		if(accountUserBuyList != null && accountUserBuyList.size() > 0 ){
			for (AccountUserBuy accountUserBuy : accountUserBuyList) {
				log.error("未付款订单号为：" + accountUserBuy.getOrderNo());
				String userId = accountUserBuy.getUserId()+"";
				Object object = CacheHelper.getInstance().get("UserBuyRemindJobUserSend"+userId);
				if(StringUtil.isEmpty(object)){
					OrderDto commodityDto = pmCommodityInfoService.getDtoById(accountUserBuy.getCommodityInfoId());
					if(commodityDto != null){
						String content = "您的订单号：" + accountUserBuy.getOrderNo()+ " " +commodityDto.getCommodityName()+ "还未付款，一个小时未付款将自动失效！";
						PushUtil.pullOneMessage(accountUserBuy.getUserId()+"", content, "28", "" + "", "");
					}
					CacheHelper.getInstance().set(60*60*24, "UserBuyRemindJobUserSend"+userId, "isSend");
				}
			}
		}
		
		map.clear();
		map.put("remindTime", "60");
		List<AccountUserBuy> timeOutList = accountUserBuyService.findByMap(map);
		if(timeOutList != null && timeOutList.size() > 0 ){
			for (AccountUserBuy accountUserBuy : timeOutList) {
				accountUserBuy.setStatus("4");
				accountUserBuyService.update(accountUserBuy);
				log.error("未付款订单号为：" + accountUserBuy.getOrderNo()+"已置为失效！");
			}
		}
		
		log.error("订单未付款 结束");
	}
	
	
}
