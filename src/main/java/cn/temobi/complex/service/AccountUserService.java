package cn.temobi.complex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.AccountRedPacketLogDao;
import cn.temobi.complex.dao.AccountUserDao;
import cn.temobi.complex.dao.AccountUserLogDao;
import cn.temobi.complex.dao.AccountWalletLogDao;
import cn.temobi.complex.dao.CmUserSignDao;
import cn.temobi.complex.dto.AccountUserBoDto;
import cn.temobi.complex.entity.AccountRedPacketLog;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserSign;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.StringUtil;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("accountUserService")
public class AccountUserService extends ServiceBase{
	
	@Resource(name = "accountUserDao")
	private AccountUserDao accountUserDao;
	
	@Resource(name = "cmUserSignDao")
	private CmUserSignDao cmUserSignDao;
	
	@Resource(name = "accountUserLogDao")
	private AccountUserLogDao accountUserLogDao;
	
	@Resource(name = "accountWalletLogDao")
	private AccountWalletLogDao accountWalletLogDao;
	
	@Resource(name = "accountRedPacketLogDao")
	private AccountRedPacketLogDao accountRedPacketLogDao;
	
	public int update(AccountUser entity){
		return accountUserDao.update(entity);
	}
	
	public Page<AccountUser> findByPage(Page page,Object map){
		return accountUserDao.findByPage(page, map);
	}
	
	public Page<AccountUserBoDto> findByPageBo(Page page,Object map){
		return accountUserDao.findByPageBo(page, map);
	}
	
	public Number getCount(Map map){
		return accountUserDao.getCount(map);
	}
	
	public AccountUser getById(Long id){
		return accountUserDao.getById(id);
	}
	
	public int save(AccountUser entity){
		return accountUserDao.save(entity);
	}
	
	public int delete(Object id){
		return accountUserDao.delete(id);
	}
	
	public int delete(AccountUser entity){
		return accountUserDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return accountUserDao.findByMap(map);
	}
    
	public List<AccountUser> findAll(){
		return accountUserDao.findAll();
	}
	
	public List<AccountUser> findAll(AccountUser entity){
		return accountUserDao.findAll(entity);
	}
	
	
	public ResponseObject myWallet(Map<String,Object> passMap,ResponseObject object){
		
    	CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
    	if(StringUtil.isEmpty(accountUser))
    	{
    		accountUser = createAccount(passMap);
    	}
		Page page =  new Page(1, 10);
		Map<String,Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("userId", cmUserInfo.getId());
    	searchMap.put("myWalletLog","1");
    	Page<AccountWalletLog> pageResult = accountWalletLogDao.findByPage(page, searchMap);
		List<AccountWalletLog> walletLogList = pageResult.getResult();
		
    	Map<String,Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("walletPrice", accountUser.getWallet());
    	returnMap.put("walletLogList", walletLogList);
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	

	public ResponseObject queryBalance(Map<String,Object> passMap,ResponseObject object){
		
    	CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
    	if(StringUtil.isEmpty(accountUser))
    	{
    		accountUser = createAccount(passMap);
    	}
		CmUserSign cmUserSign = cmUserSignDao.getById(cmUserInfo.getId());
    	Map<String,Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("walletPrice", accountUser.getWallet());
    	returnMap.put("redPrice", accountUser.getRedPacket());
    	returnMap.put("score", cmUserInfo.getScore());
    	returnMap.put("monthNum", cmUserSign.getMonthNum());
    	returnMap.put("monthIntegral", cmUserSign.getMonthIntegral());
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	public ResponseObject myRed(Map<String,Object> passMap,ResponseObject object){
		
    	CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
    	AccountUserLog accountUserLog = (AccountUserLog) passMap.get("accountUserLog");
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
    	if(StringUtil.isEmpty(accountUser))
    	{
    		accountUser = createAccount(passMap);
    	}
		Page page =  new Page(1, 10);
		Map<String,Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("userId", cmUserInfo.getId());
    	Page<AccountRedPacketLog> pageResult = accountRedPacketLogDao.findByPage(page, searchMap);
		List<AccountRedPacketLog> redLogList = pageResult.getResult();
		
    	Map<String,Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("redPrice", accountUser.getRedPacket());
    	returnMap.put("redLogList", redLogList);
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
}
