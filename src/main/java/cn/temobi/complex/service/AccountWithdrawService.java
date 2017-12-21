package cn.temobi.complex.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.AccountUserDao;
import cn.temobi.complex.dao.AccountUserLogDao;
import cn.temobi.complex.dao.AccountWalletLogDao;
import cn.temobi.complex.dao.AccountWithdrawDao;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.AccountWithdraw;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.BigDecimalUtil;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.StringUtil;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("accountWithdrawService")
public class AccountWithdrawService extends ServiceBase{
	
	@Resource(name = "accountUserLogDao")
	private AccountUserLogDao accountUserLogDao;
	
	@Resource(name = "accountWalletLogDao")
	private AccountWalletLogDao accountWalletLogDao;
	
	@Resource(name = "accountWithdrawDao")
	private AccountWithdrawDao accountWithdrawDao;
	
	@Resource(name = "accountUserDao")
	private AccountUserDao accountUserDao;
	
	public Number sumPrice(Map map){
		return accountWithdrawDao.sumPrice(map);
	} 
	
	public int update(AccountWithdraw entity){
		return accountWithdrawDao.update(entity);
	}
	
	public Page<AccountWithdraw> findByPage(Page page,Object map){
		return accountWithdrawDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return accountWithdrawDao.getCount(map);
	}
	
	public AccountWithdraw getById(Long id){
		return accountWithdrawDao.getById(id);
	}
	
	public int save(AccountWithdraw entity){
		return accountWithdrawDao.save(entity);
	}
	
	public int delete(Object id){
		return accountWithdrawDao.delete(id);
	}
	
	public int delete(AccountWithdraw entity){
		return accountWithdrawDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return accountWithdrawDao.findByMap(map);
	}
    
	public List<AccountWithdraw> findAll(){
		return accountWithdrawDao.findAll();
	}
	
	public List<AccountWithdraw> findAll(AccountWithdraw entity){
		return accountWithdrawDao.findAll(entity);
	}
	
	
	public ResponseObject withdraw(Map<String,Object> passMap,ResponseObject object){
		
    	CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
    	AccountUserLog accountUserLog = (AccountUserLog) passMap.get("accountUserLog");
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
    	if(StringUtil.isEmpty(accountUser))
    	{
    		return object;
    	}
    	
    	String payType = CommonUtil.nvl(passMap.get("payType"));
    	String msgCode = CommonUtil.nvl(passMap.get("msgCode"));
    	double price = CommonUtil.nvlDouble(passMap.get("price"));
    	Long clientId =  CommonUtil.nvlLong(passMap.get("clientId"));
    	String imei =  passMap.get("imei").toString();
    	String imsi =  passMap.get("imsi").toString();
    	String account = CommonUtil.nvl(passMap.get("account"));
    	
    	if("6".equals(payType))
    	{
    		if(price%10 != 0){
    			object.setCode(Constant.RESPONSE_ERROR_10004);
				object.setDesc("话费提现只能提10的整数");
				return object;
    		}
    		
    		Map<String,Object> searchMap = new HashMap<String, Object>();
        	searchMap.put("userId", accountUser.getUserId());
        	searchMap.put("status","2");
        	searchMap.put("payType",payType);
        	List<AccountWithdraw> list = accountWithdrawDao.findByMap(searchMap);
    		if(list == null || list.size() <= 0 || !account.equals(list.get(0).getAccount()))
    		{
    			if(StringUtil.isEmpty(msgCode))
        	    {
        	    	return object;
        	    }
    	    	String oldCode = (String) CacheHelper.getInstance().get(account);
    	    	if(StringUtil.isEmpty(oldCode))
    	    	{
    	    		object.setCode(Constant.RESPONSE_ERROR_10004);
    				object.setDesc("验证码过期");
    				return object;
    	    	}
    	    	
    	    	if(oldCode.split("\\|")[0].equals(msgCode))
    	    	{
    	    		
    	    	}else{
    	    		object.setCode(Constant.RESPONSE_ERROR_10003);
    				object.setDesc("验证码错误");
    				return object;
    	    	}
    		}
    	}
    	
    	
    	double surplus = BigDecimalUtil.sub(accountUser.getWallet(), price);
    	if(surplus < 0)
    	{
    		return object;
    	}
    	if(BigDecimalUtil.sub(price, 10) < 0)
    	{
    		object.setDesc("提现金额必须大于10元");
    		return object;
    	}
    	if(BigDecimalUtil.sub(accountUser.getWallet(), 10) < 0)
    	{
    		object.setDesc("账户余额必须大于10元");
    		return object;
    	}
    	AccountWithdraw accountWithdraw = new AccountWithdraw();
    	accountWithdraw.setUserId(cmUserInfo.getId());
    	accountWithdraw.setPrice(price);
    	accountWithdraw.setPayType(payType);
    	accountWithdraw.setAccount(account);
    	accountWithdraw.setBalance(accountUser.getWallet());
		accountWithdraw.setClientId(clientId);
		accountWithdraw.setImei(imei);
		accountWithdraw.setImsi(imsi);
    	accountWithdraw.setNickName(cmUserInfo.getNickName());
    	accountWithdrawDao.save(accountWithdraw);
    	
    	accountUserLog.setOtherId(accountWithdraw.getId());
		accountUserLogDao.save(accountUserLog);
		
		
		AccountWalletLog accountWalletLog = new AccountWalletLog();
    	accountWalletLog.setUserId(cmUserInfo.getId());
    	accountWalletLog.setWithdrawId(accountWithdraw.getId());
    	accountWalletLog.setType("2");
    	accountWalletLog.setUseType("3");
    	accountWalletLog.setPrice(price);
    	accountWalletLog.setClientId(clientId);
		accountWalletLog.setImei(imei);
		accountWalletLog.setImsi(imsi);
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(surplus);
		accountWalletLog.setPayType(payType);
		accountWalletLogDao.save(accountWalletLog);
    	
		accountUser.setUpdateTime(DateUtils.getCurrDateTimeStr());
		accountUser.setWallet(surplus);
		accountUserDao.update(accountUser);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	public ResponseObject lastAccount(Map<String,Object> passMap,ResponseObject object){
		
    	CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
    	if(StringUtil.isEmpty(accountUser))
    	{
    		return object;
    	}
    	Map<String,Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("userId", accountUser.getUserId());
    	searchMap.put("status","2");
    	List<AccountWithdraw> list = accountWithdrawDao.findByMap(searchMap);
    	
    	
    	String mobile = "";
    	String aliAccount = "";
    	if(list != null && list.size() > 0)
    	{
    		Collections.sort(list, new Comparator<AccountWithdraw>() {
                public int compare(AccountWithdraw arg0, AccountWithdraw arg1) {
                    return arg1.getAddTime().compareTo(arg0.getAddTime());
                }
            });
        
        	for (AccountWithdraw accountWithdraw : list) {
                if(accountWithdraw.getPayType().equals("6") && StringUtil.isEmpty(mobile))
                {
                	mobile = accountWithdraw.getAccount();
                }else if(accountWithdraw.getPayType().equals("2") && StringUtil.isEmpty(aliAccount)){
                	aliAccount = accountWithdraw.getAccount();
                }
            }
    	}
    	
    	Map<String,Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("mobile", mobile);
    	returnMap.put("aliAccount", aliAccount);
    	object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	public void examine(Map<String,Object> passMap){
		
		Long id = CommonUtil.nvlLong(passMap.get("id"));
		String remark = CommonUtil.nvl(passMap.get("remark"));
		String financeStatus = CommonUtil.nvl(passMap.get("financeStatus"));
    	AccountWithdraw accountWithdraw = accountWithdrawDao.getById(id);
    	if(StringUtil.isEmpty(accountWithdraw.getFinanceStatus()) || "1".equals(accountWithdraw.getFinanceStatus()))
    	{
    		accountWithdraw.setRemark(remark);
    		accountWithdraw.setFinanceStatus(financeStatus);
    		accountWithdraw.setExamineTime(DateUtils.getCurrDateTimeStr());
    		Map<String,Object> searchMap = new HashMap<String, Object>();
    		searchMap.put("withdrawId", id);
    		List<AccountWalletLog> list = accountWalletLogDao.findByMap(searchMap);
    		AccountWalletLog accountWalletLog = list.get(0);
    		AccountUser accountUser = accountUserDao.getById(accountWalletLog.getUserId());
    		
    		if("2".equals(financeStatus))
    		{
    			accountWalletLog.setStatus("1");
    			accountWithdraw.setStatus("2");
    		}else if("3".equals(financeStatus))
    		{
    			accountWalletLog.setStatus("2");
    			accountWithdraw.setStatus("3");
    			accountWalletLog.setEndAccountPrice(accountWalletLog.getEndAccountPrice()+accountWalletLog.getPrice());
    			accountUser.setWallet(BigDecimalUtil.sum(accountUser.getWallet(), accountWalletLog.getPrice()));
    			accountUserDao.update(accountUser);
    		}else if("4".equals(financeStatus))
    		{
    			accountWalletLog.setStatus("2");
    			accountWithdraw.setStatus("4");
    		}
    		
    		accountWalletLogDao.update(accountWalletLog);
    		accountWithdrawDao.update(accountWithdraw);
    	}
	}
	
	public static void main(String[] args) {
		double a = 10.01;
		System.out.println(a%10);
		if(a%10 == 0)
		{
			System.out.println("nnn");
		}
	}
}
