package cn.temobi.complex.schedule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserMessageService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmProductPraisesService;
import cn.temobi.core.common.Constant;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.SpringContextUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.salim.cache.CacheHelper;

public class ZJob {

	private static Logger log = LoggerFactory.getLogger(ZJob.class);
	PmProductInfoService pmProductInfoService = (PmProductInfoService) SpringContextUtils.getBean("pmProductInfoService");
	CmUserMessageService cmUserMessageService = (CmUserMessageService) SpringContextUtils.getBean("cmUserMessageService");
	PmProductPraisesService pmProductPraisesService = (PmProductPraisesService) SpringContextUtils.getBean("pmProductPraisesService");
	CmUserInfoService cmUserInfoService = (CmUserInfoService) SpringContextUtils.getBean("cmUserInfoService");
	
	public void execute() {
		//自动点赞
		log.error("自动点赞开始执行");
		List<String> newList;
		try {
			Thread.sleep(1*1000);
			List<String> list = (List<String>) CacheHelper.getInstance().get("allPruductKey");
			long time = System.currentTimeMillis();
			if(list != null && list.size() > 0)
			{
				List<Long> userList = (List<Long>)CacheHelper.getInstance().get("diyuserLisr");
				if(userList == null || userList.size() <= 0)
				{
					Map<String,String> map = new HashMap<String, String>();
					map.put("startTime", DateUtils.getNextDate(new Date(), -7));
					map.put("endTime", DateUtils.getCurrDateStr());
					userList = cmUserInfoService.findLoginUser(map);
					CacheHelper.getInstance().set(60*60*24, "diyuserLisr", userList);
				}
				
				newList = new ArrayList<String>();
				if(list != null && list.size() > 0)
				{
					for(int i=0;i<list.size();i++){
						String[] arr =  list.get(i).split("\\|");
						long tempTime = Long.parseLong(arr[1]);
						String status = arr[2];
						long bifTime = time - tempTime;
						if(bifTime - 5*60*1000 > 0 && status.equals("0"))
						{
							newList.add(arr[0]+"|"+arr[1]+"|1");
							zProduct(arr[0],userList);
							continue;
						}
						if(bifTime - 100*60*1000 > 0 && status.equals("1"))
						{
							newList.add(arr[0]+"|"+arr[1]+"|2");
							zProduct(arr[0],userList);
							continue;
						}
						if(bifTime - 500*60*1000 > 0)
						{
							zProduct(arr[0],userList);
							continue;
						}
						newList.add(arr[0]+"|"+arr[1]+"|"+arr[2]);
					}
				}
				if(newList.size() == 0)
				{
					CacheHelper.getInstance().remove("allPruductKey");
				}else{
					CacheHelper.getInstance().set(60*60*24, "allPruductKey", newList);
				}
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.error("自动点赞结束");
	}
	
	
	public void zProduct(final String productId,final List<Long> userList){
		PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(productId));
		if(pmProductInfo.getAudit() == 1 && pmProductInfo.getIsPublic() == 1)
		{
			userList.remove(pmProductInfo.getUserId());
			Map<String,String> map = new HashMap<String, String>();
			map.put("productId", productId);
			List<Long> newList = null;
			List<PmProductPraises> list = pmProductPraisesService.findByMap(map);
			if(list != null && list.size() > 0)
			{
				newList = new ArrayList<Long>();
				for(PmProductPraises pmProductPraises:list){
					newList.add(pmProductPraises.getPraiseUserId());
				}
				userList.removeAll(newList);
			}
	    	
			int b = (int) (Math.random()*userList.size());
			
			if(userList.size() >0 ){
				CmUserInfo cmUserInfo = cmUserInfoService.getById(userList.get(b));
				PmProductPraises pmProductPraises = new PmProductPraises();
				pmProductPraises.setPraiseUserId(cmUserInfo.getId());
				pmProductPraises.setClientId(cmUserInfo.getClientId());
				pmProductPraises.setProductId(Long.parseLong(productId));
				pmProductPraises.setType(0);
				pmProductPraisesService.save(pmProductPraises);
				
				int num = 5;
				pmProductInfo.setSearchCount(pmProductInfo.getSearchCount()+num);
				pmProductInfo.setPraiseCount(pmProductInfo.getPraiseCount()+num);
				pmProductInfo.setHotScore(pmProductInfo.getHotScore()+3*num);
				pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+2*num);
				
				
				CmUserInfo oldCmUserInfo = cmUserInfoService.getById(pmProductInfo.getUserId());
				oldCmUserInfo.setPraisesCount(oldCmUserInfo.getPraisesCount()+1*num);
				cmUserInfoService.update(oldCmUserInfo);
				pmProductInfoService.update(pmProductInfo);
				
				//添加消息
				CmUserMessage cmUserMessage = new CmUserMessage();
				cmUserMessage.setContent("赞了你的作品");
				cmUserMessage.setType(1);
				
//    		PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.Z_STRING, "11", pmProductInfo.getId()+"", "");
				cmUserMessage.setProductId(pmProductInfo.getId());
				cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
				cmUserMessage.setUserId(pmProductInfo.getUserId());
				cmUserMessage.setSendUserId(cmUserInfo.getId());
				cmUserMessageService.save(cmUserMessage);
			}
			
		}
	}
	
	public static void main(String[] args) {
        try {
        	ComboPooledDataSource ds = new ComboPooledDataSource();
            System.out.println(ds.getMaxPoolSize());// 最大连接数
            System.out.println(ds.getMinPoolSize());// 最小连接数
			System.out.println(ds.getNumBusyConnections());// 正在使用连接数
			System.out.println(ds.getNumIdleConnections());// 空闲连接数
		    System.out.println(ds.getNumConnections());// 总连接数
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
