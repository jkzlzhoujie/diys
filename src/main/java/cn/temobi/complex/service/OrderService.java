package cn.temobi.complex.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salim.cache.CacheHelper;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

import cn.temobi.complex.dao.AccountRedPacketDao;
import cn.temobi.complex.dao.AccountUserBuyDao;
import cn.temobi.complex.dao.AccountUserDao;
import cn.temobi.complex.dao.AccountWalletLogDao;
import cn.temobi.complex.dao.CmTalenInfoDao;
import cn.temobi.complex.dao.CmUserGroupDao;
import cn.temobi.complex.dao.CmUserInfoDao;
import cn.temobi.complex.dao.CmUserPrivilegeDao;
import cn.temobi.complex.dao.OrderDao;
import cn.temobi.complex.dao.PmCommodityInfoDao;
import cn.temobi.complex.dao.PmTopicJoinProductsDao;
import cn.temobi.complex.dao.SysPackageDao;
import cn.temobi.complex.dao.SysParameterDao;
import cn.temobi.complex.dao.SysPrivilegeDao;
import cn.temobi.complex.dto.OrderDto;
import cn.temobi.complex.entity.AccountRedPacket;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.CmCircle;
import cn.temobi.complex.entity.CmTalenInfo;
import cn.temobi.complex.entity.CmUserGroup;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserPrivilege;
import cn.temobi.complex.entity.Material;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.SysPackage;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.entity.SysPrivilege;
import cn.temobi.complex.schedule.CircleJob;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.BigDecimalUtil;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.QiangHongBaoUtil;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings({"serial","unchecked"})
@Transactional
@Service("orderService")
public class OrderService extends ServiceBase{
	
	private static Logger log = LoggerFactory.getLogger(OrderService.class);
	
	@Resource(name="orderDao")
	private OrderDao orderDao;
	
	@Resource(name="cmUserGroupDao")
	private CmUserGroupDao cmUserGroupDao;
	
	@Resource(name="accountUserBuyDao")
	private AccountUserBuyDao accountUserBuyDao;
	
	@Resource(name="pmCommodityInfoDao")
	private PmCommodityInfoDao pmCommodityInfoDao;
	
	@Resource(name="accountWalletLogDao")
	private AccountWalletLogDao accountWalletLogDao;
	
	@Resource(name="accountUserDao")
	private AccountUserDao accountUserDao;
	
	@Resource(name="pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name="cmUserInfoDao")
	private CmUserInfoDao cmUserInfoDao;
	
	@Resource(name="pmTopicJoinProductsDao")
	private PmTopicJoinProductsDao pmTopicJoinProductsDao;
	
	@Resource(name="accountRedPacketDao")
	private AccountRedPacketDao accountRedPacketDao;
	
	@Resource(name = "sysPrivilegeDao")
	private SysPrivilegeDao sysPrivilegeDao;
	
	@Resource(name = "sysPackageDao")
	private SysPackageDao sysPackageDao;
	
	@Resource(name="cmUserPrivilegeDao")
	private CmUserPrivilegeDao cmUserPrivilegeDao;
	
	@Resource(name="sysParameterDao")
	private SysParameterDao sysParameterDao;
	
	@Resource(name = "cmTalenInfoDao")
	private CmTalenInfoDao cmTalenInfoDao;
	
	
	public Number getSum(Map map){
		return orderDao.getSum(map);
	} 
	
	public Number getCount(Map map){
		return orderDao.getCount(map);
	}
	
	public int update(Order entity){
		return orderDao.update(entity);
	}
	
	public Page<Order> findByPage(Page page,Object map){
		return orderDao.findByPage(page, map);
	}
	
	public Order getById(Long id){
		return orderDao.getById(id);
	}
	
	public int save(Order entity){
		return orderDao.save(entity);
	}
	
	public int delete(Object id){
		return orderDao.delete(id);
	}
	
	public int delete(Order entity){
		return orderDao.delete(entity);
	}
	
	public List findByMap(Map map){
		return orderDao.findByMap(map);
	}
	
	public List findByMap2(Map map){
		return orderDao.findByMap2(map);
	}
	
	public List<Order> findAll(){
		return orderDao.findAll();
	}
	
	public List<Order> findAll(Order entity){
		return orderDao.findAll(entity);
	}
	
	
	public Order callBack(String orderNo,String code){
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNoTo", orderNo);
		map.put("status", "0");
		List<Order> list = orderDao.findByMap(map);
		if(list != null && list.size() > 0)
		{
			Order order = list.get(0);
			if (code.equals("000000")) {
				order.setStatus("1");
			} else {
				order.setStatus("2");
			}
			orderDao.update(order);
			
			return order;
		}
		
		return null;
	}
	
	
	public void executeFuc(Order order){
		AccountUserBuy accountUserBuy = accountUserBuyDao.getById(order.getAccountBuyId());
		if(StringUtil.isNotEmpty(accountUserBuy))
		{
			log.error("用户="+accountUserBuy.getUserId() + "订单号 orderId= " + order.getOrderNo()+"价格=" + order.getAmount());
			Map<String,Object> searchMap = new HashMap<String, Object>();
			searchMap.put("accountBuyId", accountUserBuy.getId());
			searchMap.put("order", order.getOrderNo());
			List<AccountWalletLog> list = accountWalletLogDao.findByMap(searchMap);
			AccountUser accountUser = accountUserDao.getById(accountUserBuy.getUserId());
			double price = accountUser.getWallet();
			if(list != null && list.size() > 0)
			{
				for(AccountWalletLog accountWalletLog:list){
					accountWalletLog.setStatus("1");
					accountWalletLogDao.update(accountWalletLog);
					
					if("1".equals(accountWalletLog.getType())){
						price = BigDecimalUtil.sum(price, accountWalletLog.getPrice());
					}else if("2".equals(accountWalletLog.getType())){
						price = BigDecimalUtil.sub(price, accountWalletLog.getPrice());
					}
				}
			}
			accountUserBuy.setStatus("1");
			accountUserBuy.setUpdateTime(DateUtils.getCurrDateTimeStr());
			accountUserBuyDao.update(accountUserBuy);
			
			accountUser.setUpdateTime(DateUtils.getCurrDateTimeStr());
			accountUser.setWallet(price);
			accountUserDao.update(accountUser);
			
			CmUserInfo cmUserInfo = cmUserInfoDao.getById(accountUserBuy.getUserId());
			
			PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsDao.getById(accountUserBuy.getRetionId());
			
			String type = accountUserBuy.getType();
			if("1".equals(type))
			{
				List<String> userlist  = new ArrayList<String>();
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("groupId", "6");
				List<CmUserGroup> grouplist = cmUserGroupDao.findByMap(map);
				if(grouplist != null && grouplist.size() > 0)
				{
					for(CmUserGroup cmUserGroup:grouplist)
					{
						userlist.add(cmUserGroup.getUserId() + "");
					}
				}
				PushUtil.pushAccountListMultiple(userlist,"亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
				
				accountUser.setType("1");
				double buyPrice = accountUserBuy.getPrice();
				
				pmTopicJoinProducts.setStatus("1");
				pmTopicJoinProductsDao.update(pmTopicJoinProducts);
				
				pmScoreLogService.addScore("10", cmUserInfo, cmUserInfo.getClientId()+"", pmTopicJoinProducts.getProductId()+"",buyPrice+"");
				cmUserInfoDao.update(cmUserInfo);
				
			//  zhouj 5.3 五一过后暂停
//				List<Double> redlist = QiangHongBaoUtil.createRed(buyPrice);
//				if(redlist != null && redlist.size() > 0)
//				{
//					for(int i=0;i<redlist.size();i++){
//						double a = redlist.get(i);
//						AccountRedPacket accountRedPacket = new AccountRedPacket();
//						accountRedPacket.setUserId(accountUserBuy.getUserId());
//						accountRedPacket.setPrice(a);
//						accountRedPacket.setJoinId(pmTopicJoinProducts.getId());
//						accountRedPacket.setNum(i);
//						accountRedPacket.setType("1");
//						accountRedPacketDao.save(accountRedPacket);
//					}
//				}
				
			}else if("2".equals(type)){
				
				List<String> userlist  = new ArrayList<String>();
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("groupId", "5");
				List<CmUserGroup> grouplist = cmUserGroupDao.findByMap(map);
				if(grouplist != null && grouplist.size() > 0)
				{
					for(CmUserGroup cmUserGroup:grouplist)
					{
						userlist.add(cmUserGroup.getUserId() + "");
					}
				}
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("code",SysParamConstant.BIG_MEMBER_AMOUNT);
				List<SysParameter> sysParameterList = sysParameterDao.findByMap(param);
				if(sysParameterList != null && sysParameterList.size() > 0){
					if( Double.valueOf(sysParameterList.get(0).getValue()) <= accountUserBuy.getPrice() ){
						map.clear();
						map.put("noPrivilege", SysParamConstant.PRI_LEVEL_GENERAL);
						List<CmUserInfo> cmuserInfolist = cmUserInfoDao.findByMap(map);//查找会员
						if(cmuserInfolist != null && cmuserInfolist.size() > 0)
						{
							for(CmUserInfo user:cmuserInfolist)
							{
								if(user.getId()!= cmUserInfo.getId()){//除自己外 推送消息 
									userlist.add(user.getId() + "");
								}
							}
						}
					}
				}
				
				PushUtil.pushAccountListMultiple(userlist, "亲，又有土豪付费悬赏求P，是否要爆发你的P图洪荒之力呢？","24",pmTopicJoinProducts.getId()+"", "");
				
				pmTopicJoinProducts.setStatus("1");
				pmTopicJoinProductsDao.update(pmTopicJoinProducts);
				
				pmScoreLogService.addScore("8", cmUserInfo, accountUserBuy.getId()+"", pmTopicJoinProducts.getProductId()+"","");
		    	pmScoreLogService.addScore("1019", cmUserInfo, accountUserBuy.getId()+"", pmTopicJoinProducts.getProductId()+"","");
		    	cmUserInfoDao.update(cmUserInfo);
			}else if("4".equals(type)){
				SysPrivilege sysPrivilege = sysPrivilegeDao.getById( accountUserBuy.getCommodityId() );
				SysPackage sysPackage = sysPackageDao.getById( accountUserBuy.getCommodityInfoId() );
				accountUser.setType("1");
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
		    	
				PushUtil.pullOneMessage(cmUserInfo.getId()+"", "你已经成功办理X秀会员特权包！", "28", "" + "", "");
				cmUserInfoDao.update(cmUserInfo);
				accountUserDao.update(accountUser);
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
			}
		}
		log.error("无购买记录");
	}
	
	
	public ResponseObject orderList(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		
		String pageNo = (String) passMap.get("pageNo");
		if(StringUtil.isEmpty(pageNo)) pageNo = "1";
		Page page =  new Page<Material>((Integer.parseInt(pageNo)-1)*10, Integer.parseInt(pageNo)*10);
		
		Map<String,Object> map = new HashMap<String, Object>();
    	map.put("userId", cmUserInfo.getId());
    	map.put("statusTo", "statusTo");
    	map.put("type", "1");
		
    	Page<AccountUserBuy> pageResult = accountUserBuyDao.findByPage(page, map);
		List<AccountUserBuy> list = pageResult.getResult();
		OrderDto commodityDto = new OrderDto();
		
    	List<OrderDto> relist = new ArrayList<OrderDto>();
    	if(list != null && list.size() > 0)
    	{
    		OrderDto orderDto;
    		for(AccountUserBuy accountUserBuy:list)
    		{
    			orderDto = new OrderDto();
    			orderDto.setNum(1);
    			orderDto.setFreight("0");
    			orderDto.setType(accountUserBuy.getType().replaceAll("1", "X秀专属私人订制"));
    			orderDto.setAddTime(accountUserBuy.getAddTime());
    			String commodityInfoId = accountUserBuy.getCommodityInfoId().toString();
    			OrderDto commodityDtoTemp = (OrderDto) CacheHelper.getInstance().get("orderDto"+commodityInfoId);
				if(commodityDtoTemp!=null){
					commodityDto = commodityDtoTemp;
				}else{
					commodityDto = pmCommodityInfoDao.getDtoById(accountUserBuy.getCommodityInfoId());
					CacheHelper.getInstance().set(60*60*24,"orderDto"+commodityInfoId, commodityDto);
				}
    			orderDto.setCommodityName(commodityDto.getCommodityName());
    			orderDto.setCommodityType(commodityDto.getCommodityType());
    			orderDto.setCommodityThumbnail(host+commodityDto.getCommodityThumbnail());
    			orderDto.setAccountBuyId(accountUserBuy.getId());
    			orderDto.setStatus(accountUserBuy.getStatus());
    			orderDto.setCommodityId(accountUserBuy.getCommodityId());
    			orderDto.setCommodityInfoId(accountUserBuy.getCommodityInfoId());
    			orderDto.setAmount(accountUserBuy.getPrice()+"");
    			orderDto.setOrderNo(accountUserBuy.getOrderNo());
    			PmTopicJoinProducts pmTopicJoinProduct = pmTopicJoinProductsDao.getById(accountUserBuy.getRetionId());
    			if(pmTopicJoinProduct != null){
    				orderDto.setPhoneShell(pmTopicJoinProduct.getPhoneShell()==null?"":pmTopicJoinProduct.getPhoneShell());
    				orderDto.setReceivePerson(pmTopicJoinProduct.getReceivePerson()==null?"":pmTopicJoinProduct.getReceivePerson());
    				orderDto.setReceiveAddress(pmTopicJoinProduct.getReceiveAddress()==null?"":pmTopicJoinProduct.getReceiveAddress());
    			}
    			relist.add(orderDto);
    		}
    	}
    	//旧的接口下的单，及微店下的单     - 补
    	map.clear();
    	map.put("userId", cmUserInfo.getId());
    	map.put("statusTo", "statusTo");
    	map.put("type", "1");
    	List<Order> orderList = orderDao.findByMap(map);
    	if(orderList != null && orderList.size() > 0){
    		OrderDto orderDto;
    		for(Order order:orderList){
    			orderDto = new OrderDto();
    			orderDto.setNum(1);
    			orderDto.setFreight("0");
    			orderDto.setType(order.getType().replaceAll("1", "X秀专属私人订制"));
    			orderDto.setAddTime(order.getAddTime());
    			
    			String commodityInfoId = order.getCommodityInfoId().toString();
    			OrderDto commodityDtoTemp = (OrderDto) CacheHelper.getInstance().get("orderDto"+commodityInfoId);
				if(commodityDtoTemp!=null){
					commodityDto = commodityDtoTemp;
				}else{
					commodityDto = pmCommodityInfoDao.getDtoById(order.getCommodityInfoId());
					CacheHelper.getInstance().set(60*60*24,"orderDto"+commodityInfoId, commodityDto);
				}
    			orderDto.setCommodityName(commodityDto.getCommodityName());
    			orderDto.setCommodityType(commodityDto.getCommodityType());
    			orderDto.setCommodityThumbnail(host+commodityDto.getCommodityThumbnail());
    			orderDto.setOrderNo(order.getOrderNo());
    			orderDto.setStatus(order.getStatus());
    			orderDto.setCommodityId(order.getCommodityId());
    			orderDto.setCommodityInfoId(order.getCommodityInfoId());
    			orderDto.setAmount((int)(Double.parseDouble(order.getAmount())*100)+"");
    			boolean isExist = false;
    			for (Iterator iterator = relist.iterator(); iterator.hasNext();) {
    				OrderDto orderDtoUserBuy = (OrderDto) iterator.next();
					if(orderDtoUserBuy.getOrderNo().trim().equals(order.getOrderNo() )){
						isExist = true;
					}
				}
    			if(!isExist){
    				relist.add(orderDto);
    			}
    		}
    	}
    	
    	
    	
    	object.setResponse(relist);
		
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	public ResponseObject cancelOrder(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		
		Long accountBuyId = CommonUtil.nvlLong(passMap.get("accountBuyId"));
		
		AccountUserBuy accountUserBuy = accountUserBuyDao.getById(accountBuyId);
		if(StringUtil.isEmpty(accountUserBuy))
		{
			return object;
		}
		
		if(accountUserBuy.getUserId() - cmUserInfo.getId() != 0)
		{
			return object;
		}
		
		if(accountUserBuy.getStatus().equals("1"))
		{
			return object;
		}
		
		accountUserBuy.setStatus("3");
		accountUserBuyDao.update(accountUserBuy);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	public ResponseObject delOrder(Map<String,Object> passMap,ResponseObject object){
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		
		Long accountBuyId = CommonUtil.nvlLong(passMap.get("accountBuyId"));
		
		AccountUserBuy accountUserBuy = accountUserBuyDao.getById(accountBuyId);
		if(StringUtil.isEmpty(accountUserBuy))
		{
			return object;
		}
		
		if(accountUserBuy.getUserId() - cmUserInfo.getId() != 0)
		{
			return object;
		}
		
		if(accountUserBuy.getStatus().equals("1"))
		{
			return object;
		}
		
		accountUserBuy.setStatus("4");
		accountUserBuyDao.update(accountUserBuy);
		
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
	
	public ResponseObject orderDetail(Map<String,Object> passMap,ResponseObject object){
		
		CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
		
		Long accountBuyId = CommonUtil.nvlLong(passMap.get("accountBuyId"));
		
		AccountUserBuy accountUserBuy = accountUserBuyDao.getById(accountBuyId);
		if(StringUtil.isEmpty(accountUserBuy))
		{
			return object;
		}
		
		if(accountUserBuy.getUserId() - cmUserInfo.getId() != 0)
		{
			return object;
		}
		OrderDto commodityDto = new OrderDto();
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("accountBuyId", accountUserBuy.getId());
		returnMap.put("type","X秀专属私人订制");
		
		String commodityInfoId = accountUserBuy.getCommodityInfoId().toString();
		OrderDto commodityDtoTemp = (OrderDto) CacheHelper.getInstance().get("orderDto"+commodityInfoId);
		if(commodityDtoTemp!=null){
			commodityDto = commodityDtoTemp;
		}else{
			commodityDto = pmCommodityInfoDao.getDtoById(accountUserBuy.getCommodityInfoId());
			CacheHelper.getInstance().set(60*60*24,"orderDto"+commodityInfoId, commodityDto);
		}
		
		
		returnMap.put("commodityName",commodityDto.getCommodityName());
		returnMap.put("commodityType",commodityDto.getCommodityType());
		returnMap.put("commodityThumbnail",host+commodityDto.getCommodityThumbnail());
		returnMap.put("orderNo",accountUserBuy.getOrderNo());
		returnMap.put("status","0");
		returnMap.put("commodityId",accountUserBuy.getCommodityId());
		returnMap.put("commodityInfoId",accountUserBuy.getCommodityInfoId());
		returnMap.put("amount",accountUserBuy.getPrice());
		returnMap.put("num",1);
		returnMap.put("freight","0");
		returnMap.put("addTime",accountUserBuy.getAddTime());
		
		PmTopicJoinProducts pmTopicJoinProduct = pmTopicJoinProductsDao.getById(accountUserBuy.getRetionId());
		if(pmTopicJoinProduct != null){
			returnMap.put("phoneShell",pmTopicJoinProduct.getPhoneShell()==null?"":pmTopicJoinProduct.getPhoneShell());
			returnMap.put("receivePerson",pmTopicJoinProduct.getReceivePerson()==null?"":pmTopicJoinProduct.getReceivePerson());
			returnMap.put("receiveAddress",pmTopicJoinProduct.getReceiveAddress()==null?"":pmTopicJoinProduct.getReceiveAddress());
		}
		
		object.setResponse(returnMap);
		object.setDesc("成功");
		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		return object;
	}
	
}
