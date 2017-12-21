package cn.temobi.complex.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.AccountUserBuyDao;
import cn.temobi.complex.dao.AccountUserDao;
import cn.temobi.complex.dao.AccountUserLogDao;
import cn.temobi.complex.dao.AccountWalletLogDao;
import cn.temobi.complex.dao.CmTalenInfoDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.CmUserPrivilegeDao;
import cn.temobi.complex.dao.OrderDao;
import cn.temobi.complex.dao.SysPackageDao;
import cn.temobi.complex.dao.SysPrivilegeDao;
import cn.temobi.complex.entity.AccountRedPacket;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.CmTalenInfo;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserPrivilege;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.SysPackage;
import cn.temobi.complex.entity.SysPrivilege;
import cn.temobi.complex.entity.enums.ApiNameEnum;
import cn.temobi.complex.schedule.PJob;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.BigDecimalUtil;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.OrderUtil;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.QiangHongBaoUtil;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;


@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("sysPrivilegeService")
public class SysPrivilegeService extends ServiceBase{
	
	private static Logger log = LoggerFactory.getLogger(SysPrivilegeService.class);

	@Resource(name = "sysPrivilegeDao")
	private SysPrivilegeDao sysPrivilegeDao;
	
	@Resource(name = "sysPackageDao")
	private SysPackageDao sysPackageDao;
	
	@Resource(name = "accountUserBuyDao")
	private AccountUserBuyDao accountUserBuyDao;
	
	@Resource(name = "accountUserDao")
	private AccountUserDao accountUserDao;
	
	@Resource(name = "orderDao")
	private OrderDao orderDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	@Resource(name = "accountUserLogDao")
	private AccountUserLogDao accountUserLogDao;
	
	@Resource(name = "accountWalletLogDao")
	private AccountWalletLogDao accountWalletLogDao;
	
	@Resource(name = "cmUserPrivilegeDao")
	private CmUserPrivilegeDao cmUserPrivilegeDao;

	@Resource(name = "cmTalenInfoDao")
	private CmTalenInfoDao cmTalenInfoDao;
	
	
	public int update(SysPrivilege sysPrivilege){
		return sysPrivilegeDao.update(sysPrivilege);
	}
	
	public Page<SysPrivilege> findByPage(Page page,Object map){
		return sysPrivilegeDao.findByPage(page, map);
	}
	
	public List<SysPrivilege> findAll(){
		return sysPrivilegeDao.findAll();
	}
	
	public List<SysPrivilege> findByMap(Map param){
		return sysPrivilegeDao.findByMap(param);
	}
	
	public Number getCount(Map map){
		return sysPrivilegeDao.getCount(map);
	}
	
	public SysPrivilege getById(Long id){
		return sysPrivilegeDao.getById(id);
	}
	
	public int save(SysPrivilege sysPrivilege){
		return sysPrivilegeDao.save(sysPrivilege);
	}
	
	public int delete(Object id){
		return sysPrivilegeDao.delete(id);
	}

	public ResponseObject openPrivilegeSubmit(Map<String,Object> passMap,ResponseObject object) {
		SysPrivilege sysPrivilege = sysPrivilegeDao.getById(Long.valueOf(passMap.get("privilegeId").toString() ));
		SysPackage sysPackage = sysPackageDao.getById(Long.valueOf(passMap.get("packaegeId").toString() ));
		if(sysPackage != null &&  sysPrivilege != null){
			
			CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
			AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
	    	if(StringUtil.isEmpty(accountUser))
	    	{
	    		accountUser = createAccount(passMap);
	    	}
	    
			String orderNo = RandomUtils.getRandomStr(1, 32, 1)[0];
			AccountUserBuy accountUserBuy = new AccountUserBuy();
			accountUserBuy.setPrice(sysPackage.getPrice());
			accountUserBuy.setUserId(cmUserInfo.getId());
			accountUserBuy.setType("4");
			accountUserBuy.setStatus("0");
			accountUserBuy.setCommodityId(sysPrivilege.getId());
			accountUserBuy.setCommodityInfoId(sysPackage.getId());
			accountUserBuy.setOrderNo(orderNo);
			accountUserBuy.setRemark("开通特权");
			accountUserBuyDao.save(accountUserBuy);
			
			Map<String,Object> returnMap = new HashMap<String, Object>();
			returnMap.put("price", sysPackage.getPrice());
			returnMap.put("wallet", accountUser.getWallet());
			returnMap.put("accountUserBuyId", accountUserBuy.getId());
			object.setResponse(returnMap);
			object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
		}else{
			object.setDesc("参数错误");
			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
			return object;
		}
	}

	
	public ResponseObject getPayParameter(Map<String, Object> passMap,
			ResponseObject object) {
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		String payType =   passMap.get("payType").toString();
		Long accountBuyId = CommonUtil.nvlLong(passMap.get("accountBuyId"));
		
		AccountUserBuy accountUserBuy = accountUserBuyDao.getById(accountBuyId);
		if(StringUtil.isEmpty(accountUserBuy)){
			return object;
		}
		if(accountUserBuy.getUserId()-cmUserInfo.getId() != 0){
			return object;
		}
		if(!"0".equals(accountUserBuy.getStatus())){
			return object;
		}
		
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
		if(StringUtil.isEmpty(accountUser)){
			accountUser = createAccount(passMap);
    	}
		
		double price = accountUserBuy.getPrice();
		double userPrice = accountUser.getWallet();
		
		if("3".equals(payType)){
			if(price == 0){
				return object;
			}
			if(BigDecimalUtil.sub(userPrice, price) < 0){
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("余额不足");
				return object;
			}
			
			
		}else if("4".equals(payType) || "5".equals(payType)){
			if(price == 0){
				return object;
			}
			if(userPrice <= 0){
				log.error("钱包余额小于0！");
				return object;
			}
		}else if("1".equals(payType) || "2".equals(payType)){
			if(price == 0){
				return object;
			}
		}else if("0".equals(payType)){
			if(price != 0){
				return object;
			}
		}
		
		String orderNo = accountUserBuy.getOrderNo();
		
		passMap.put("accountUser", accountUser);
		passMap.put("price", price);
		passMap.put("useType", accountUserBuy.getType());// 4 开通特权
		passMap.put("apiName", ApiNameEnum.payPrivilege.getCode());
		passMap.put("productDes", "特权开通支付");
		passMap.put("productDetail", "特权开通支付");
		passMap.put("orderNo", orderNo);
		
    	Map<String,Object> returnMap = pay(accountUserBuy, passMap);
		
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}

	private Map<String, Object> pay(AccountUserBuy accountUserBuy,Map<String, Object> passMap) {
		String payType = CommonUtil.nvl(passMap.get("payType"));
		String apiName = CommonUtil.nvl(passMap.get("apiName"));
		double price = CommonUtil.nvlDouble(passMap.get("price"));
		AccountUserLog accountUserLog =  (AccountUserLog) passMap.get("accountUserLog");
		AccountUser accountUser =  (AccountUser) passMap.get("accountUser");
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
    	
		log.error("用户："+cmUserInfo.getId()+"，支付开通特权 获取支付参数");
		
		if("1".equals(payType)){//微信
			return moneyPay(accountUserBuy, "1", price, passMap);
    	}else if("2".equals(payType)){//支付宝
    		return moneyPay(accountUserBuy, "2", price, passMap);
    		
    	}else if("3".equals(payType)){//钱包余额
    		
    		accountUserBuy.setStatus("1");
			accountUserBuy.setUpdateTime(DateUtils.getCurrDateTimeStr());
			accountUserBuyDao.update(accountUserBuy);
			accountUserLog.setApiName(apiName);
			accountUserLog.setOtherId(accountUserBuy.getId());
			accountUserLogDao.save(accountUserLog);
			
			double surplusPrice = BigDecimalUtil.sub(accountUser.getWallet(), price);
			balancePay(accountUserBuy, surplusPrice,passMap);
			successUpdateData(accountUserBuy,cmUserInfo,accountUser);
    	}else if("4".equals(payType)){
			groupPay(accountUserBuy, accountUser.getWallet(),passMap);
			
			accountUserBuyDao.update(accountUserBuy);
			accountUserLog.setApiName(apiName);
			accountUserLog.setOtherId(accountUserBuy.getId());
			accountUserLogDao.save(accountUserLog);
			
			double surplusPrice = BigDecimalUtil.sub(price, accountUser.getWallet());
			return moneyPay(accountUserBuy,"2", surplusPrice,passMap);
		}else if("5".equals(payType)){
			groupPay(accountUserBuy, accountUser.getWallet(),passMap);
			
			accountUserBuyDao.update(accountUserBuy);
			accountUserLog.setApiName(apiName);
			accountUserLog.setOtherId(accountUserBuy.getId());
			accountUserLogDao.save(accountUserLog);
			
			double surplusPrice = BigDecimalUtil.sub(price, accountUser.getWallet());
			return moneyPay(accountUserBuy,"1", surplusPrice,passMap);
		}
		return null;
	}
	
	private void successUpdateData(AccountUserBuy accountUserBuy,CmUserInfo cmUserInfo,
			AccountUser accountUser) {
		SysPrivilege sysPrivilege = sysPrivilegeDao.getById( accountUserBuy.getCommodityId() );
		SysPackage sysPackage = sysPackageDao.getById( accountUserBuy.getCommodityInfoId() );
		accountUser.setType("1");
		accountUserDao.update(accountUser);
		cmUserInfo.setPrivilegeLevel(sysPrivilege.getLevel());
    	cmUserInfo.setScore(cmUserInfo.getScore()+sysPackage.getScore());
    	cmUserInfo.setExperience(cmUserInfo.getExperience()+sysPackage.getExperience());
    	cmUserInfo.setCharm(cmUserInfo.getCharm()+sysPackage.getCharm());
    	cmUserInfo.setOriginality(cmUserInfo.getOriginality()+sysPackage.getOriginality());
    	cmUserInfo.setCredit(cmUserInfo.getCredit()+sysPackage.getCredit());
    	log.error("用户：" + cmUserInfo.getId() + "特权支付开通成功！" + "特权ID：" + sysPrivilege.getName()+sysPrivilege.getId()+"套餐ID：" +sysPackage.getName() + sysPackage.getId() );
    	Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", cmUserInfo.getId());
		map.put("privilegeId", sysPrivilege.getId() );
		map.put("valid", 1);
		List<CmUserPrivilege> cmUserPrivilegeList = cmUserPrivilegeDao.findByMap(map);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(cmUserPrivilegeList!=null && cmUserPrivilegeList.size() > 0){
    		CmUserPrivilege cmUserPrivilege = cmUserPrivilegeList.get(0);
    		cmUserPrivilege.setPackageId(sysPackage.getId());
    		cmUserPrivilege.setUpdateWhen(DateUtils.getCurrDate());
    		cmUserPrivilege.setIsNew(2);//续费
    		cmUserPrivilege.setEffectTime(DateUtils.getCurrDateStr());
    		String effectTime = cmUserPrivilege.getFailureTime();
    		Date date;
			try {
				date = sdf.parse(effectTime);
				Calendar calendar = Calendar.getInstance();
	    	    calendar.setTime(date);
	    	    calendar.add(Calendar.MONTH, sysPackage.getValidDate());
	    		date = calendar.getTime();
	    		String failureTime = sdf.format(date);
	    		cmUserPrivilege.setFailureTime(failureTime);
			} catch (java.text.ParseException e) {
				e.getMessage();
			}
			cmUserPrivilegeDao.update(cmUserPrivilege);
    	}else{
    		CmUserPrivilege cmUserPrivilege = new CmUserPrivilege();
    		cmUserPrivilege.setUserId(cmUserInfo.getId());
    		cmUserPrivilege.setEffectTime(DateUtils.getCurrDateStr());
    		Calendar cal = Calendar.getInstance();
    		//下面的就是把当前日期加一个月
    		cal.add(Calendar.MONTH, sysPackage.getValidDate());
    		cmUserPrivilege.setFailureTime(sdf.format(cal.getTime()));
    		cmUserPrivilege.setIsNew(1);
    		cmUserPrivilege.setPrivilegeId(sysPrivilege.getId());
    		cmUserPrivilege.setPackageId(sysPackage.getId());
    		cmUserPrivilegeDao.save(cmUserPrivilege);
    	}
		cmUserInfoDao.update(cmUserInfo);
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userId", cmUserInfo.getId());
		List<CmTalenInfo> taList= cmTalenInfoDao.findByMap(param);
		if(taList==null || taList.size() <= 0){
			CmTalenInfo cmTalenInfo = new CmTalenInfo();
			cmTalenInfo.setType("1");
			cmTalenInfo.setTalenSeq(10);
			cmTalenInfo.setRemark("开通特权直接升级为达人");
			cmTalenInfo.setTalenScore(cmUserInfo.getScore());
			cmTalenInfo.setUserId(cmUserInfo.getId());
			cmTalenInfoDao.save(cmTalenInfo);
		}
		PushUtil.pullOneMessage(cmUserInfo.getId().toString(), "你已经成功办理X秀会员特权包！", "28", "", "");
		
	}

	//钱包直接支付
	private void balancePay(AccountUserBuy accountUserBuy,double surplusPrice,Map<String,Object> passMap){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Long clientId = CommonUtil.nvlLong(passMap.get("clientId"));
		Long userId = cmUserInfo.getId();
		String imei =  passMap.get("imei").toString();
		String imsi =  passMap.get("imsi").toString();
		String useType =  CommonUtil.nvl(passMap.get("useType"));
		String payType =  CommonUtil.nvl(passMap.get("payType"));
		AccountUser accountUser =  (AccountUser) passMap.get("accountUser");
		double price = CommonUtil.nvlDouble(passMap.get("price"));
		
		AccountWalletLog accountWalletLog = new AccountWalletLog();
		accountWalletLog.setUserId(userId);
		accountWalletLog.setType("2");
		accountWalletLog.setUseType(useType);
		accountWalletLog.setPrice(price);
		accountWalletLog.setStatus("1");
		accountWalletLog.setClientId(clientId);
		accountWalletLog.setImei(imei);
		accountWalletLog.setImsi(imsi);
		accountWalletLog.setAccountBuyId(accountUserBuy.getId());
		accountWalletLog.setPayType(payType);
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(surplusPrice);
		
		accountUser.setUpdateTime(DateUtils.getCurrDateTimeStr());
		accountUser.setWallet(surplusPrice);
		accountUserDao.update(accountUser);
		
		accountWalletLogDao.save(accountWalletLog);
	}
	
	
	//2 支付宝或者 1微信支付/
	private Map<String, Object> moneyPay(AccountUserBuy accountUserBuy,String orderType,double orderPrice,Map<String,Object> passMap){
		
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Long clientId = CommonUtil.nvlLong(passMap.get("clientId"));
		Long userId = cmUserInfo.getId();
		String imei =  passMap.get("imei").toString();
		String imsi =  passMap.get("imsi").toString();
		String orderNo =  CommonUtil.nvl(passMap.get("orderNo"));
		String useType =  CommonUtil.nvl(passMap.get("useType"));
		String payType =  CommonUtil.nvl(passMap.get("payType"));
		AccountUser accountUser =  (AccountUser) passMap.get("accountUser");
		
		
    	Map<String, Object> returnMap = null;
    	if("1".equals(orderType))
    	{
    		returnMap = OrderUtil.getWeixinInfo(passMap,orderPrice);
    	}else if("2".equals(orderType))
    	{
    		returnMap = OrderUtil.getAliInfo(passMap,orderPrice);
    	}
    	String code = CommonUtil.nvl(returnMap.get("code"));
		if(StringUtil.isNotEmpty(code) && "1".equals(code))
		{
			try {
				throw new Exception("订单获取失败");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		Long accountBuyId = accountUserBuy.getId();
    	Order order = new Order();
    	order.setClientId(clientId);
    	order.setUserId(userId);
    	order.setPayType(orderType);
    	order.setAmount(orderPrice+"");
    	order.setOrderNo(orderNo);
    	order.setType(useType);
    	order.setAccountBuyId(accountBuyId);
    	orderDao.save(order);
    	
    	double tempPrice = BigDecimalUtil.sum(accountUser.getWallet(), orderPrice);
    	AccountWalletLog accountWalletLog = new AccountWalletLog();
    	accountWalletLog.setUserId(userId);
    	accountWalletLog.setOrderNo(orderNo);
    	accountWalletLog.setType("1");
    	accountWalletLog.setHavaType("1");
    	accountWalletLog.setPrice(orderPrice);
    	accountWalletLog.setClientId(clientId);
		accountWalletLog.setImei(imei);
		accountWalletLog.setImsi(imsi);
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(tempPrice);
		accountWalletLog.setAccountBuyId(accountBuyId);
		accountWalletLog.setPayType(payType);
		accountWalletLogDao.save(accountWalletLog);
		
		AccountWalletLog consumeWalletLog = new AccountWalletLog();
		consumeWalletLog.setUserId(userId);
		consumeWalletLog.setOrderNo(orderNo);
		consumeWalletLog.setType("2");
		consumeWalletLog.setUseType(useType);
		consumeWalletLog.setPrice(orderPrice);
		consumeWalletLog.setClientId(clientId);
		consumeWalletLog.setImei(imei);
		consumeWalletLog.setImsi(imsi);
		consumeWalletLog.setStartAccountPrice(tempPrice);
		consumeWalletLog.setEndAccountPrice(BigDecimalUtil.sub(tempPrice,orderPrice));
		consumeWalletLog.setAccountBuyId(accountUserBuy.getId());
		consumeWalletLog.setPayType(payType);
		accountWalletLogDao.save(consumeWalletLog);
		
		return returnMap;
		
	}
	
	//组合支付
	private void groupPay(AccountUserBuy accountUserBuy,double surplusPrice,Map<String,Object> passMap){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Long clientId = CommonUtil.nvlLong(passMap.get("clientId"));
		Long userId = cmUserInfo.getId();
		String imei =  passMap.get("imei").toString();
		String imsi =  passMap.get("imsi").toString();
		String orderNo =  CommonUtil.nvl(passMap.get("orderNo"));
		String useType =  CommonUtil.nvl(passMap.get("useType"));
		String payType =  CommonUtil.nvl(passMap.get("payType"));
		AccountUser accountUser =  (AccountUser) passMap.get("accountUser");
		
		AccountWalletLog accountWalletLog = new AccountWalletLog();
		accountWalletLog.setUserId(userId);
		accountWalletLog.setType("2");
		accountWalletLog.setUseType(useType);
		accountWalletLog.setPrice(surplusPrice);
		accountWalletLog.setStatus("0");
		accountWalletLog.setClientId(clientId);
		accountWalletLog.setImei(imei);
		accountWalletLog.setImsi(imsi);
		accountWalletLog.setAccountBuyId(accountUserBuy.getId());
		accountWalletLog.setPayType(payType);
		accountWalletLog.setOrderNo(orderNo);
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(0);
		
		accountWalletLogDao.save(accountWalletLog);
		
	}
	
}
