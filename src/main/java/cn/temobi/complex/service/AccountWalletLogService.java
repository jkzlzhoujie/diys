package cn.temobi.complex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.AccountUserBuyDao;
import cn.temobi.complex.dao.AccountUserDao;
import cn.temobi.complex.dao.AccountUserLogDao;
import cn.temobi.complex.dao.AccountWalletLogDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.OrderDao;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Order;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.BigDecimalUtil;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.OrderUtil;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;

/**
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("accountWalletLogService")
public class AccountWalletLogService extends ServiceBase{
	
	@Resource(name = "accountWalletLogDao")
	private AccountWalletLogDao accountWalletLogDao;
	
	@Resource(name = "accountUserBuyDao")
	private AccountUserBuyDao accountUserBuyDao;
	
	@Resource(name = "accountUserLogDao")
	private AccountUserLogDao accountUserLogDao;
	
	@Resource(name = "accountUserDao")
	private AccountUserDao accountUserDao;
	
	@Resource(name = "orderDao")
	private OrderDao orderDao;
	
	@Resource(name = "cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	public Number sumPrice(Map map){
		return accountWalletLogDao.sumPrice(map);
	} 
	
	public int update(AccountWalletLog entity){
		return accountWalletLogDao.update(entity);
	}
	
	public Page<AccountWalletLog> findByPage(Page page,Object map){
		return accountWalletLogDao.findByPage(page, map);
	}
	
	public Number getCount(Map map){
		return accountWalletLogDao.getCount(map);
	}
	
	public AccountWalletLog getById(Long id){
		return accountWalletLogDao.getById(id);
	}
	
	public int save(AccountWalletLog entity){
		return accountWalletLogDao.save(entity);
	}
	
	public int delete(Object id){
		return accountWalletLogDao.delete(id);
	}
	
	public int delete(AccountWalletLog entity){
		return accountWalletLogDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return accountWalletLogDao.findByMap(map);
	}
    
	public List<AccountWalletLog> findAll(){
		return accountWalletLogDao.findAll();
	}
	
	public List<AccountWalletLog> findAll(AccountWalletLog entity){
		return accountWalletLogDao.findAll(entity);
	}
	
	public ResponseObject myWalletLog(Map<String,Object> passMap,ResponseObject object){
		String pageNo =  passMap.get("pageNo").toString();
		String pageSize =  passMap.get("pageSize").toString();
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		Map<String,Object> searchMap = new HashMap<String, Object>();
    	searchMap.put("userId", cmUserInfo.getId());
    	searchMap.put("myWalletLog","1");
    	Page<AccountWalletLog> pageResult = accountWalletLogDao.findByPage(page, searchMap);
		List<AccountWalletLog> walletLogList = pageResult.getResult();
		Map<String,Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("walletLogList", walletLogList);
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	/**
	 * 账户充值
	 * @param passMap
	 * @param object
	 * @return
	 */
	public ResponseObject recharge(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
    	AccountUserLog accountUserLog = (AccountUserLog) passMap.get("accountUserLog");
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());
    	if(StringUtil.isEmpty(accountUser))
    	{
    		return object;
    	}
    	Long userId = cmUserInfo.getId();
    	Long clientId =  CommonUtil.nvlLong(passMap.get("clientId"));
    	String payType = passMap.get("payType").toString();
    	String imsi = passMap.get("imsi").toString();
    	String imei = passMap.get("imei").toString();
    	double price =  CommonUtil.nvlDouble(passMap.get("price"));
    	String orderNo = RandomUtils.getRandomStr(1, 32, 1)[0];
    	Map<String, Object> returnMap = null;
    	passMap.put("productDes", "账户充值");
		passMap.put("productDetail", "账户充值");
		passMap.put("orderNo", orderNo);
    	if("1".equals(payType))
    	{
    		returnMap = OrderUtil.getWeixinInfo(passMap,price);
    	}else if("2".equals(payType))
    	{
    		returnMap = OrderUtil.getAliInfo(passMap,price);
    	}
    	String code = CommonUtil.nvl(returnMap.get("code"));
		if(StringUtil.isNotEmpty(code) && "1".equals(code))
		{
			return object;
		}
    	
		AccountUserBuy accountUserBuy = new AccountUserBuy();
		accountUserBuy.setPrice(price);
		accountUserBuy.setUserId(userId);
		accountUserBuy.setType("3");
		accountUserBuy.setPayType(payType);
		accountUserBuy.setOrderNo(orderNo);
		accountUserBuyDao.save(accountUserBuy);
		
    	Order order = new Order();
    	order.setClientId(clientId);
    	order.setUserId(userId);
    	order.setPayType(payType);
    	order.setAmount(price+"");
    	order.setOrderNo(orderNo);
    	order.setType("3");
    	order.setAccountBuyId(accountUserBuy.getId());
    	orderDao.save(order);
    	
    	AccountWalletLog accountWalletLog = new AccountWalletLog();
    	accountWalletLog.setUserId(userId);
    	accountWalletLog.setOrderNo(orderNo);
    	accountWalletLog.setType("1");
    	accountWalletLog.setHavaType("1");
    	accountWalletLog.setPrice(price);
    	accountWalletLog.setClientId(clientId);
		accountWalletLog.setImei(imei);
		accountWalletLog.setImsi(imsi);
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(BigDecimalUtil.sum(accountUser.getWallet(), price));
		accountWalletLog.setPayType(payType);
		accountWalletLog.setAccountBuyId(accountUserBuy.getId());
		accountWalletLogDao.save(accountWalletLog);
		accountUserLog.setOtherId(accountWalletLog.getId());
		accountUserLogDao.save(accountUserLog);
    	
    	object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	
	/**
	 * 支付
	 * orderType 2 支付宝或者 1微信支付
	 * orderPrice 订单价格
	 */
	public Map<String, Object> moneyPay(AccountUserBuy accountUserBuy,String orderType,double orderPrice,Map<String,Object> passMap){
		
		String useType =  CommonUtil.nvl(passMap.get("useType"));
		String payType =  CommonUtil.nvl(passMap.get("payType"));
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		Long userId = cmUserInfo.getId();
		
		String orderNo =  accountUserBuy.getOrderNo();
		passMap.put("orderNo", orderNo);
		AccountUser accountUser = accountUserDao.getById(cmUserInfo.getId());	
		
    	if(StringUtil.isEmpty(accountUser)){
			accountUser = createAccount(passMap);
    	}
		
    	Map<String, Object> returnMap = null;
    	if("1".equals(orderType)){
    		returnMap = OrderUtil.getWeixinInfo(passMap,orderPrice);
    	}else if("2".equals(orderType)){
    		returnMap = OrderUtil.getAliInfo(passMap,orderPrice);
    	}
    	String code = CommonUtil.nvl(returnMap.get("code"));
		if(StringUtil.isNotEmpty(code) && "1".equals(code)){
			try {
				throw new Exception("订单获取失败");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		Long accountBuyId = accountUserBuy.getId();
    	Order order = new Order();
    	order.setClientId(cmUserInfo.getClientId());
    	order.setUserId(userId);
    	order.setPayType(orderType);
    	order.setAmount(orderPrice+"");
    	order.setOrderNo(orderNo);
    	order.setType(useType);
    	order.setAccountBuyId(accountBuyId);
    	order.setCommodityId(accountUserBuy.getCommodityId());
    	orderDao.save(order);
    	
    	double tempPrice = BigDecimalUtil.sum(accountUser.getWallet(), orderPrice);
    	AccountWalletLog accountWalletLog = new AccountWalletLog();
    	accountWalletLog.setUserId(userId);
    	accountWalletLog.setOrderNo(orderNo);
    	accountWalletLog.setType("1");		//充值
    	accountWalletLog.setHavaType("1");
    	accountWalletLog.setPrice(orderPrice);
    	accountWalletLog.setClientId(cmUserInfo.getClientId());
		if(StringUtil.isNotEmpty(cmUserInfo.getImei())){
			accountWalletLog.setImei(cmUserInfo.getImei());
		}
		accountWalletLog.setStartAccountPrice(accountUser.getWallet());
		accountWalletLog.setEndAccountPrice(tempPrice);
		accountWalletLog.setAccountBuyId(accountBuyId);
		accountWalletLog.setPayType(payType);
		accountWalletLogDao.save(accountWalletLog);
		
		AccountWalletLog consumeWalletLog = new AccountWalletLog();
		consumeWalletLog.setUserId(userId);
		consumeWalletLog.setOrderNo(orderNo);
		consumeWalletLog.setType("2");			//消费
		consumeWalletLog.setUseType(useType);
		consumeWalletLog.setPrice(orderPrice);
		consumeWalletLog.setClientId(cmUserInfo.getClientId());
		if(StringUtil.isNotEmpty(cmUserInfo.getImei())){
			consumeWalletLog.setImei(cmUserInfo.getImei());
		}
		consumeWalletLog.setStartAccountPrice(tempPrice);
		consumeWalletLog.setEndAccountPrice(BigDecimalUtil.sub(tempPrice,orderPrice));
		consumeWalletLog.setAccountBuyId(accountUserBuy.getId());
		consumeWalletLog.setPayType(payType);
		accountWalletLogDao.save(consumeWalletLog);
		
		return returnMap;
		
	}
	
	
	
}
