package cn.temobi.complex.service;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.temobi.complex.dao.AccountWalletLogDao;
import cn.temobi.complex.dao.WeixinUserInfoDao;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.WeixinUserInfo;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.service.ServiceBase;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.OrderUtil;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("weixinClientApiService")
public class WeixinClientApiService extends ServiceBase{

	@Resource(name = "weixinUserInfoDao")
	private WeixinUserInfoDao weixinUserInfoDao;

	@Resource(name = "accountWalletLogDao")
	private AccountWalletLogDao accountWalletLogDao;
	
	
	/**
	 * 充值
	 * @param passMap
	 * @param object
	 * @return
	 */
	public ResponseObject reCharge(HttpServletRequest request,ResponseObject object,double price ,String userId) {
	    	int payType = Integer.valueOf(1);
	    	WeixinUserInfo weixinUserInfo = weixinUserInfoDao.getById(Long.valueOf(userId));
	    	if(weixinUserInfo == null){
	    		log.error("用户有误！");
	    		return object;
	    	}
	    	Date date = new Date();
	    	String orderNo = date.getTime()+userId ;
	    	AccountWalletLog accountWalletLog = new AccountWalletLog();
	    	accountWalletLog.setUserId(Long.valueOf(userId) );
	    	accountWalletLog.setOrderNo(orderNo);
	    	accountWalletLog.setUseType("6");
	    	accountWalletLog.setType("1");//充值
	    	accountWalletLog.setPayType("1");
	    	accountWalletLog.setPrice(price);
	    	accountWalletLog.setStatus("0");
	    	accountWalletLog.setStartAccountPrice(Double.valueOf("0"));
	    	accountWalletLog.setEndAccountPrice( Double.valueOf("0"));
	    	accountWalletLogDao.save(accountWalletLog);
			
			Map<String, Object> passMap = new HashMap<String, Object>();
	    	passMap.clear();
	    	passMap.put("productDes", "打call付款");
			passMap.put("productDetail", "打call付款");
			passMap.put("orderNo", orderNo);
			passMap.put("ip", request.getRemoteAddr());
//			passMap.put("ip", "127.0.0.1");
			log.error("userId= " + weixinUserInfo.getId() + ",openId" +weixinUserInfo.getOpenId());
			if(weixinUserInfo.getOpenId()!=null){
				passMap.put("openId", weixinUserInfo.getOpenId());
			}
			Map<String, Object> returnMap = null;
			if(1 == payType){
	    		returnMap = OrderUtil.getWeixinInfo(passMap,price);
	    	}else if(2 == payType){
	    		returnMap = OrderUtil.getAliInfo(passMap,price);
	    	}
	    	String code = CommonUtil.nvl(returnMap.get("code"));
			if(StringUtil.isNotEmpty(code) && "1".equals(code)){
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				return object;
			}
	    	object.setResponse(returnMap);
			object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
	}

}
