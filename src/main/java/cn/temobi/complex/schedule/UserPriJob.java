package cn.temobi.complex.schedule;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserPrivilege;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserPrivilegeService;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.SpringContextUtils;


public class UserPriJob {

	private static Logger log = LoggerFactory.getLogger(UserPriJob.class);
	CmUserInfoService cmUserInfoService = (CmUserInfoService) SpringContextUtils.getBean("cmUserInfoService");
	CmUserPrivilegeService cmUserPrivilegeService = (CmUserPrivilegeService) SpringContextUtils.getBean("cmUserPrivilegeService");
	
	public void execute() {
		
		log.error("用户特权到期 检测 开始执行");
		Map<String,String> map = new HashMap<String, String>();
		map.put("noPrivilege", SysParamConstant.PRI_LEVEL_GENERAL);
		map.put("failureTime", DateUtils.getCurrDateStr());
		List<CmUserInfo> userList = cmUserInfoService.findPriCmUser(map);
		if(userList != null && userList.size() > 0 ){
			for (CmUserInfo cmUserInfo : userList) {
				map.clear();
				map.put("userId", cmUserInfo.getId().toString());
				map.put("valid", "1");
				 List<CmUserPrivilege> cmpList = cmUserPrivilegeService.findByMap(map);
				 if(cmpList.size()<=0){//没有其他特权的  修改会员状态
					 cmUserInfo.setPrivilegeLevel(SysParamConstant.PRI_LEVEL_GENERAL);
					 cmUserInfoService.update(cmUserInfo);
				 }
			}
		}
		
		log.error("用户特权到期 检测结束");
	}
	
	
	
	
	public static void main(String[] args) {
		UserPriJob userPriJob = new UserPriJob();
		userPriJob.execute();
	} 
}
