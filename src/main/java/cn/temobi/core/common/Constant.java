package cn.temobi.core.common;


public class Constant {
	
	public static final String DEFAULT_PWD = "123456";//IM默认密码
	public static final String RESPONSE_SUCCESS_CODE = "00000";//成功	
	public static final String RESPONSE_ERROR_CODE = "10000";//系统异常失败
	public static final String RESPONSE_PARAMS_ERROR = "10001";//参数错误
	public static final String RESPONSE_DEFAULT_ERROR = "10002";//失败
	public static final String RESPONSE_ERROR_10003 = "10003";//失败
	public static final String RESPONSE_ERROR_10004 = "10004";//失败
	public static final String RESPONSE_ERROR_10005 = "10005";//需要登录
	public static final String RESPONSE_ERROR_10006 = "10006";//被举报不能使用
	
	public static final String RESPONSE_ERROR_10007 = "10007";//相同的评论或反馈
	public static final String RESPONSE_ERROR_10008 = "10008";//黑名单用户
	public static final String RESPONSE_ERROR_10009 = "10009";//发表过于频繁
	public static final String RESPONSE_ERROR_10010 = "10010";//内容中含有禁词
	public static final String RESPONSE_ERROR_10011 = "10011";//已举报、点赞
	public static final String RESPONSE_ERROR_10012 = "10012";//图片上传失败
	public static final String RESPONSE_ERROR_10013 = "10013";//暂无新版本
	public static final String RESPONSE_ERROR_10014 = "10014";//不要重复提交
	
	
	public static final String SESSION_USER_INFO = "session_user_info";
	public static final int COOKIE_OUTTIME_LONG = 60*60*24*30;     //cookie时效30天
	public static final String MAX_SIZE = "1073741824";//上传限制 1024*1024*1024 1G
	public static final String UPLOAD_URL = "http://127.0.0.1";//上传的地址
	
	public static final int CURRENT_VERSION = 32;   //当前版本 5.0.2
	
	public static final String ENCRYPT_KEY = "net_salim";   //加密私密串
	
	/**
	 * 132 IDC http://180.97.250.132:8091
	 * 测试http://192.168.0.242:8080
	 * 正式 http://121.40.107.240:8091
	 */
	//public static final String HOST_URL = "http://120.36.139.162:8091";
	//public static final String HOST_URL = "http://110.84.28.127:9000";
//	public static final String HOST_URL = "http://180.97.250.132:8091";
	public static final String HOST_URL = "http://121.40.107.240:8091";
	
	
	// 服务器外网IP地址配置文件
	public static final String SERVER_INFORMATION = "properties/server_information.properties";
	
	public static final String Z_STRING = "赞了你的作品";
	public static final String C_STRING = "踩了你的作品";
	public static final String S_STRING = "分享了你的作品";
	public static final String X_STRING = "下载了你的作品";
	public static final String P_STRING = "评论了你的作品";
	public static final String J_STRING = "举报了你的作品";
	public static final String H_STRING = "回复了你的评论";
	
	
	public static final String JB_DISC = "你的评论被举报，暂时先下线，审核通过后恢复";
	public static final String JB_POST = "你的帖子被举报，暂时先下线，审核通过后恢复";
	
	//支付宝回调接口
	public static String alipay_notify_url = HOST_URL+"/diys/clientNew/orderNew/aliNotify";
	//微信回调接口
	public static String wx_notify_url = HOST_URL+"/diys/clientNew/orderNew/weixinNotify";
	
	public static final String PUSH_TYPE_1 = "1";		//表情 
	public static final String PUSH_TYPE_2 = "2";		//贴图
	public static final String PUSH_TYPE_3 = "3";		//颜文字
	public static final String PUSH_TYPE_4 = "4";		//主题
	public static final String PUSH_TYPE_5 = "5";		//外链
	public static final String PUSH_TYPE_6 = "6";		//被踩
	public static final String PUSH_TYPE_7 = "7";		//被分享
	public static final String PUSH_TYPE_8 = "8";		//被下载
	public static final String PUSH_TYPE_9 = "9";		//被举报
	public static final String PUSH_TYPE_10 = "10";		//审核重新上线
	public static final String PUSH_TYPE_11 = "11";		//被赞
	public static final String PUSH_TYPE_12 = "12";		//被评论
	public static final String PUSH_TYPE_13 = "13";		//其他
	public static final String PUSH_TYPE_14 = "14";		//订单成功对运营人员推送 
	public static final String PUSH_TYPE_15 = "15";		//单个推送 
	public static final String PUSH_TYPE_16 = "16";		//原图被p 	
	public static final String PUSH_TYPE_17 = "17";		//评论被回复 
	public static final String PUSH_TYPE_18 = "18";		//作品被举报对运营人员的推送 	
	public static final String PUSH_TYPE_19 = "19";		//悬赏超时无人参与 	
	public static final String PUSH_TYPE_20 = "20";		//悬赏后赏主采纳某张图
	public static final String PUSH_TYPE_21 = "21";		//悬赏23小时仍未采纳 
	public static final String PUSH_TYPE_22 = "22";		//悬赏24小时为采纳，系统发赏，发赏着
	public static final String PUSH_TYPE_23 = "23";		//悬赏24小时为采纳，系统发赏，被采纳者	
	public static final String PUSH_TYPE_24 = "24";		//悬赏金额大于0
	public static final String PUSH_TYPE_25 = "25";		//P图评论被回复
	public static final String PUSH_TYPE_26 = "26";		//悬赏求P被赞
	public static final String PUSH_TYPE_27 = "27";		//悬赏求P被分享
	public static final String PUSH_TYPE_28 = "28";		//悬赏求P被评论
	public static final String PUSH_TYPE_29 = "29";		//悬赏求P被下载
	public static final String PUSH_TYPE_30 = "30";		//悬赏求P被举报
	public static final String PUSH_TYPE_31 = "31";		//帖子被举报
	public static final String PUSH_TYPE_32 = "32";		//评论被举报
	public static final String PUSH_TYPE_33 = "33";		//
	public static final String PUSH_TYPE_34 = "34";		//
	
	
	
	
}