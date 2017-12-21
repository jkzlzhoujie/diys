package cn.temobi.complex.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.temobi.complex.entity.CmUserPrivilege;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserPrivilegeService;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.SpringContextUtils;


public class UserPriRemindJob {

	private static Logger log = LoggerFactory.getLogger(ZJob.class);
	CmUserInfoService cmUserInfoService = (CmUserInfoService) SpringContextUtils.getBean("cmUserInfoService");
	CmUserPrivilegeService cmUserPrivilegeService = (CmUserPrivilegeService) SpringContextUtils.getBean("cmUserPrivilegeService");
	
	public void execute() {
		
		log.error("用户特权还有七天到期 提醒开始执行");
		Map<String,String> map = new HashMap<String, String>();
		map.put("remindTime", "7");
		List<CmUserPrivilege> priList = cmUserPrivilegeService.findByMap(map);
		if(priList != null && priList.size() > 0 ){
			for (CmUserPrivilege cmUserPrivilege : priList) {
				String content = "您办理的X秀会员"+ cmUserPrivilege.getPrivilegeName()+cmUserPrivilege.getPackageName()+"包即将到期！";
				PushUtil.pullOneMessage(cmUserPrivilege.getUserId()+"", content, "28", "" + "", "");
			}
		}
		log.error("用户特权还有七天到期 提醒 结束");
	}
	
	
	
	
	public static void main(String[] args) {
		UserPriJob userPriJob = new UserPriJob();
		userPriJob.execute();
	} 
}
