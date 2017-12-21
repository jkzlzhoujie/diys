package cn.temobi.complex.schedule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.core.common.Constant;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.SpringContextUtils;
import cn.temobi.core.util.StringUtil;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.salim.cache.CacheHelper;

public class UpdateJob {

	private static Logger log = LoggerFactory.getLogger(UpdateJob.class);
	PmProductInfoService pmProductInfoService = (PmProductInfoService) SpringContextUtils.getBean("pmProductInfoService");
	
	public void execute() {
		//浏览数
		log.error("更新浏览数开始执行");
		try {
			Thread.sleep(1000);
			Map<Long,Integer> updateMap = (Map<Long, Integer>) CacheHelper.getInstance().get("updateMap");
			CacheHelper.getInstance().remove("updateMap");
			if(StringUtil.isNotEmpty(updateMap)){
				Map<String,Object> map = new HashMap<String, Object>();
				for (Entry<Long, Integer> entry : updateMap.entrySet()) {
					Long productId =  entry.getKey();
					Integer num =  entry.getValue();
					if(StringUtil.isNotEmpty(productId) && StringUtil.isNotEmpty(num)){
						map.put("productId", entry.getKey());
						map.put("num", entry.getValue());
						pmProductInfoService.updateAll(map);
					}
				 }
			}
			
		} catch (InterruptedException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	    log.error("更新浏览数end执行");
	}
	
}
