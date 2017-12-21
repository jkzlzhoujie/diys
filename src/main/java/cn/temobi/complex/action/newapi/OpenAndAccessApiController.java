package cn.temobi.complex.action.newapi;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.commons.codec.binary.Base64;
import org.springframework.util.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tencent.common.MD5;

import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.service.AccountUserBuyService;
import cn.temobi.complex.service.AccountUserService;
import cn.temobi.complex.service.AccountWalletLogService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.SysParameterService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.IpUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;

/**
 * 对外及外部项目接入  接口
 * @author zhouj
 * 
 * 2016年5月18日10:57:55
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/open")
public class OpenAndAccessApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="sysParameterService")
	private SysParameterService sysParameterService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="accountWalletLogService")
	private AccountWalletLogService accountWalletLogService;
	
	@Resource(name="accountUserService")
	private AccountUserService accountUserService;
	
	@Resource(name="accountUserBuyService")
	private AccountUserBuyService accountUserBuyService;
	
	/**
	 * 元元夺宝项目 
	 * 获取请求地址
	 * （客户 支持15分钟 有效时间，如果要时间长点 需动态再次生成 ） 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDuoBaoUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getDuoBaoUrl(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId)){
	    		return object;
	    	}
	    	Map<String,String> param = new HashMap<String, String>();
	    	param.put("code",SysParamConstant.YUANYUAN_DUOBAO);
			List<SysParameter> sysParameterList = sysParameterService.findByMap(param);
	    	String duobaoUrl = "";
			if(sysParameterList != null && sysParameterList.size()==1){
				duobaoUrl = sysParameterList.get(0).getValue();
				
				CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.valueOf(userId ));
				String nickName = "";
				String headUrl = "";
				String phone = "";
				if(StringUtil.isNotEmpty(cmUserInfo.getNickName())){
					nickName = java.net.URLEncoder.encode(cmUserInfo.getNickName(),"utf-8");
				}
				if(StringUtil.isNotEmpty(cmUserInfo.getHeadImageUrl() )){
					headUrl = java.net.URLEncoder.encode(cmUserInfo.getHeadImageUrl(),"utf-8");
				}
				if(StringUtil.isNotEmpty(cmUserInfo.getMobile())){
					phone = cmUserInfo.getMobile();
				}
				
				String sign = sign("2016001", userId,  phone);
				
				duobaoUrl = duobaoUrl + "?"+"uid="+userId+"&phone="+phone+"&headUrl="+headUrl+"&nickName="+nickName+"&sign="+sign+"&channelId=2016001";
				
				object.setResponse(duobaoUrl);
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		object.setDesc("成功");
	    	}else{
	    		object.setCode(Constant.RESPONSE_ERROR_10003);
	    		object.setDesc("系统参数未配置，请联系客服！");
	    	}
    		
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	 public static String sign(String cid, String uid, String mobile) throws UnsupportedEncodingException {

	        String key = cid + uid + mobile + "XShow" + new SimpleDateFormat("MMdd-HH").format(new Date());

	        return org.apache.commons.codec.binary.Base64.encodeBase64String(DigestUtils.md5Digest(key.getBytes("utf-8")));

	 }
	 
	 
	     /**
		 * 元元夺宝项目 
		 * 微信支付接口
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/orderPayWeixin", method = {RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody ResponseObject orderPayWeixin(HttpServletRequest request) {
			ResponseObject object = initResponseObject();
			object.setCode(Constant.RESPONSE_PARAMS_ERROR);
			object.setDesc("参数错误");
		    try{
		    	String userId = request.getParameter("userId");
		    	if(StringUtil.isEmpty(userId)){
		    		object.setCode(Constant.RESPONSE_ERROR_CODE);
		    		object.setDesc("用户ID不能为空！");
		    		return object;
		    	}
		    	String price = request.getParameter("price");
		    	if(StringUtil.isEmpty(price)){
		    		object.setCode(Constant.RESPONSE_ERROR_CODE);
		    		object.setDesc("价格不能为空！");
		    		return object;
		    	}
		    	String commodityId = request.getParameter("commodityId");
		    	if(StringUtil.isEmpty(commodityId)){
		    		object.setCode(Constant.RESPONSE_ERROR_CODE);
		    		object.setDesc("商品ID不能为空！");
		    		return object;
		    	}
		    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.valueOf(userId));
		    	if(cmUserInfo == null){
		    		object.setCode(Constant.RESPONSE_ERROR_10003);
		    		object.setDesc("用户不存在！");
		    		return object;
		    	}
		    	
		    	Map<String,Object> map = new HashMap<String, Object>();	
		    	getDefaultPara(request, map, null);
		    	map.put("userId", userId);
		    	map.put("payType", 1);//支付方式 微信
		    	map.put("useType", 5);//订单种类 5 参与夺宝
		    	map.put("productDes", "参与夺宝活动");
		    	map.put("productDetail", "参与夺宝活动");
		    	map.put("cmUserInfo", cmUserInfo);
		      	
				double orderPrice = CommonUtil.nvlDouble(price);
				
				String orderNo = RandomUtils.getRandomStr(1, 32, 1)[0];
				AccountUserBuy accountUserBuy = new AccountUserBuy();
				accountUserBuy.setPayType("1");
				accountUserBuy.setPrice(orderPrice);
				accountUserBuy.setUserId(Long.valueOf(userId));
				accountUserBuy.setOrderNo(orderNo);
				accountUserBuy.setStatus("0");
				accountUserBuy.setRemark("参与夺宝");
				
				if(commodityId.equals("Recharge")){
					accountUserBuy.setType("3");
				}else{
					accountUserBuy.setType("5");
					accountUserBuy.setCommodityId(Long.valueOf(commodityId));
					accountUserBuy.setCommodityInfoId(Long.valueOf(commodityId));
				}
				accountUserBuyService.save(accountUserBuy);
				
				Map<String, Object> returnMap = accountWalletLogService.moneyPay(accountUserBuy, "1", orderPrice, map);
		    	
				object.setResponse(returnMap);
				object.setDesc("成功");
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
				return object;
		    }catch(Exception e) {
		    	e.printStackTrace();
				log.error(e.getMessage());
				object.setCode(Constant.RESPONSE_ERROR_CODE);
				object.setDesc("服务器有点忙，请稍后再试");
			}
		    return object;
		}
		
		
		/**
		 * 元元夺宝项目 
		 * 查询支付结果
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/checkOrderPayResult", method = {RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody ResponseObject checkOrderPayResult(HttpServletRequest request) {
			ResponseObject object = initResponseObject();
			object.setCode(Constant.RESPONSE_PARAMS_ERROR);
			object.setDesc("参数错误");
		    try{
		    	
		    	String orderNo = request.getParameter("orderNo");
		    	if(StringUtil.isEmpty(orderNo)){
		    		object.setCode(Constant.RESPONSE_ERROR_CODE);
		    		object.setDesc("orderNo不能为空！");
		    		return object;
		    	}
		    	Map<String,Object> map = new HashMap<String, Object>();	
		    	map.put("orderNo", orderNo);
		    	map.put("status", "1");
		    	int count = accountUserBuyService.getCount(map).intValue();
		    	if(count == 1){
		    		object.setResponse("SUCCESS");
					object.setDesc("付款成功");
					object.setCode(Constant.RESPONSE_SUCCESS_CODE);
					return object;
		    	}else{
		    		object.setResponse("FAILURE");
					object.setDesc("付款失败");
					object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
					return object;
		    	}

		    }catch(Exception e) {
		    	e.printStackTrace();
				log.error(e.getMessage());
				object.setCode(Constant.RESPONSE_ERROR_CODE);
				object.setDesc("服务器有点忙，请稍后再试");
			}
		    return object;
		}
		
		
		/**
		 * 二月兰项目 
		 * 获取请求地址
		 * @param request
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/getErYueLanUrl", method = {RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody ResponseObject getErYueLanUrl(HttpServletRequest request) {
			ResponseObject object = initResponseObject();
			object.setCode(Constant.RESPONSE_PARAMS_ERROR);
			object.setDesc("参数错误");
		    try{
		    	String userId = request.getParameter("userId");
		    	if(StringUtil.isEmpty(userId)){
		    		return object;
		    	}
		    	Map<String,String> param = new HashMap<String, String>();
		    	param.put("code",SysParamConstant.ER_YUE_LAN);
				List<SysParameter> sysParameterList = sysParameterService.findByMap(param);
		    	String erYueLanUrl = "";
				if(sysParameterList != null && sysParameterList.size()==1){
					erYueLanUrl = sysParameterList.get(0).getValue();
					
					CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.valueOf(userId ));
					
					String cid = "7";//X秀渠道号7
					String verify = userId;//用户ID
					String code = "";//签名
					String key = "f5226bb1aabe9b380c7db4fe8c84eb08";
					
					//签名规则为： md5("xshow" + verify + key) (key为通信秘钥)
					String signStr = "xshow" + verify + key;
					code = MD5.MD5Encode(signStr);
					
					erYueLanUrl = erYueLanUrl + "?"+"verify=xshow"+userId+"&code="+code+"&cid=7";
					
					object.setResponse(erYueLanUrl);
		    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		    		object.setDesc("成功");
		    	}else{
		    		object.setCode(Constant.RESPONSE_ERROR_10003);
		    		object.setDesc("系统参数未配置，请联系客服！");
		    	}
	    		
		    }catch(Exception e) {
		    	e.printStackTrace();
				log.error(e.getMessage());
				object.setCode(Constant.RESPONSE_ERROR_CODE);
				object.setDesc("服务器有点忙，请稍后再试");
			}
		    return object;
		}
		
		
	    private static boolean verify(String sign, String key) {
	        try {
	            byte[] signData = Base64.decodeBase64(sign);
	            byte[] md5Digest = DigestUtils.md5Digest(key.getBytes("utf-8"));
	            return Arrays.equals(signData, md5Digest);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	    public static void main(String[] args) throws Exception {

	        String cid = "2016001";
	        String uid = "15321";
	        String mobile = "18206060967";

	        String sign =  sign(cid, uid, mobile);

	        String key = cid + uid + mobile + "XShow" + new SimpleDateFormat("MMdd-HH").format(new Date());

	        System.out.println(sign);
	        System.out.println(verify(sign,key));

	        final String currentUsername = "heh565737";
			final String currentPassword = MD5.MD5Encode(currentUsername).toLowerCase();

			 System.out.println(currentPassword);//3ea01fd8e2470b264d03a6112454d86b
			

	    }
	
	
}
