package cn.temobi.core.service;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.temobi.complex.dao.AccountUserDao;
import cn.temobi.complex.dao.AccountUserLogDao;
import cn.temobi.complex.dao.CmUserSignDao;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserSign;
import cn.temobi.complex.entity.enums.ApiNameEnum;
import cn.temobi.core.common.Constant;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.PropertiesHelper;

@SuppressWarnings("serial")
public abstract class ServiceBase<T, PK extends Serializable> implements Cloneable, Serializable {
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Resource(name = "accountUserDao")
	private AccountUserDao accountUserDao;
	
	@Resource(name = "accountUserLogDao")
	private AccountUserLogDao accountUserLogDao;
	
	@Resource(name = "cmUserSignDao")
	private CmUserSignDao cmUserSignDao;

	protected String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	
	public AccountUser createAccount(Map<String,Object> passMap){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
    	AccountUserLog accountUserLog = (AccountUserLog) passMap.get("accountUserLog");
		AccountUser accountUser = new AccountUser();
		accountUser.setClientId(CommonUtil.nvlLong( passMap.get("clientId")));
		accountUser.setImei(CommonUtil.nvl(passMap.get("imei")));
		accountUser.setImsi(CommonUtil.nvl(passMap.get("imsi")));
		accountUser.setUserId(cmUserInfo.getId());
		accountUser.setOs(CommonUtil.nvl(passMap.get("os")));
		accountUser.setChannel(CommonUtil.nvl(passMap.get("channel")));
		accountUserDao.save(accountUser);
		accountUserLog.setOtherId(accountUser.getId());
		accountUserLog.setApiName(ApiNameEnum.createAcount.getCode());
		accountUserLogDao.save(accountUserLog);
		
		CmUserSign cmUserSign = new CmUserSign();
		cmUserSign.setUserId(cmUserInfo.getId());
		
		cmUserSignDao.save(cmUserSign);
		
		return accountUser;
	}
	
	
	public AccountUser createOtherAccount(Long userId){
		AccountUser accountUser = new AccountUser();
		accountUser.setUserId(userId);
		accountUserDao.save(accountUser);
		AccountUserLog accountUserLog = new AccountUserLog();
		accountUserLog.setOtherId(accountUser.getId());
		accountUserLog.setUserId(userId);
		accountUserLog.setApiName(ApiNameEnum.createAcount.getCode());
		accountUserLogDao.save(accountUserLog);
		
		CmUserSign cmUserSign = new CmUserSign();
		cmUserSign.setUserId(userId);
		cmUserSignDao.save(cmUserSign);
		
		return accountUser;
	}
	
}