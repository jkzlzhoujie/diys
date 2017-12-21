package cn.temobi.core.easemob.api;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.temobi.core.easemob.utils.HTTPClientUtils;
import cn.temobi.core.easemob.vo.ClientSecretCredential;
import cn.temobi.core.easemob.vo.Constants;
import cn.temobi.core.easemob.vo.Credential;
import cn.temobi.core.easemob.vo.EndPoints;
import cn.temobi.core.easemob.vo.HTTPMethod;
import cn.temobi.core.easemob.vo.Roles;
import cn.temobi.core.util.StringUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.salim.encrypt.MD5;

/**
 * REST API Demo :用户体系集成 REST API HttpClient4.3实现
 * 
 * Doc URL: http://www.easemob.com/docs/rest/userapi
 * 
 * @author Lynch 2014-09-15
 * 
 */
public class EasemobIUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(EasemobIUtil.class);
	private static final JsonNodeFactory factory = new JsonNodeFactory(false);

    // 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
            Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);

    public static void main(String[] args) {
       
//    	/**
//         * 注册IM用户[单个]
//         */
//        ObjectNode createNewIMUserSingleNode = createNewIMUserSingle("hehh13");
//        if (null != createNewIMUserSingleNode) {
//            LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
//        }
        
//        /**
//         * 注册IM用户[批量生成用户然后注册]
//         */
//        String usernamePrefix = "heh";
//        Long perNumber = 50l;
//        Long totalNumber = 23631l;
//        ObjectNode createNewIMUserBatchGenNode = createNewIMUserBatchGen(usernamePrefix, perNumber, totalNumber);
//        if (null != createNewIMUserBatchGenNode) {
//            LOGGER.info("注册IM用户[批量]: " + createNewIMUserBatchGenNode.toString());
//        }
    	
    	 /**
         * IM用户登录
         */
        ObjectNode imUserLoginNode = imUserLogin("heh15306","A518AB643150EBED965AC9C5FA251A2F".toLowerCase());
        System.out.println(imUserLoginNode.toString());
        if (null != imUserLoginNode) {
            LOGGER.info("IM用户登录: " + imUserLoginNode.toString());
        }
    	
 //   	System.out.println(MD5.getMD5( "heh23631").toLowerCase());
//    	/** 创建群组 
//		 * curl示例
//		 * curl -X POST 'https://a1.easemob.com/easemob-playground/test1/chatgroups' -H 'Authorization: Bearer {token}'
//         * -d '{"groupname":"测试群组","desc":"测试群组","public":true,"approval":true,"owner":"xiaojianguo001","maxusers":333,"members":["xiaojianguo002","xiaojianguo003"]}'
//		 */
//		ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
//		dataObjectNode.put("groupname", "测试群组");
//		dataObjectNode.put("desc", "测试群组");
//		dataObjectNode.put("approval", false);
//		dataObjectNode.put("public", true);
//		dataObjectNode.put("maxusers", 333);
//		dataObjectNode.put("owner", "ken101");
//		ObjectNode creatChatGroupNode = creatChatGroups(dataObjectNode);
//		System.out.println(creatChatGroupNode.toString());
    	
//    	/**
//		 * 在群组中添加一个人
//		 * curl示例
//		 * curl -X POST 'https://a1.easemob.com/easemob-playground/test1/chatgroups/1405735927133519/users/xiaojianguo002'
//         * -H 'Authorization: Bearer {token}'
//		 */
//		String addToChatgroupid = "1433397230599403";
//		String toAddUsername = "kenshinnuser102";
//		ObjectNode addUserToGroupNode = addUserToGroup(addToChatgroupid, toAddUsername);
//		System.out.println(addUserToGroupNode.toString());

//		/**
//		 * 在群组中减少一个人
//		 * curl示例
//		 * curl -X DELETE 'https://a1.easemob.com/easemob-playground/test1/chatgroups/1405735927133519/users/xiaojianguo002'
//         * -H 'Authorization: Bearer {token}'
//		 */
//		String delFromChatgroupid = "1433397230599403";
//		String toRemoveUsername = "kenshinnuser102";
//		ObjectNode deleteUserFromGroupNode = deleteUserFromGroup(delFromChatgroupid, toRemoveUsername);
//		System.out.println(deleteUserFromGroupNode.asText());
//    	
        
        //给一个群组发文本消息
//        String targetTypegr = "chatgroups";
//        ArrayNode targetgroup = factory.arrayNode();
//        targetgroup.add("1433397230599403");
//        ObjectNode sendTxtMessagegroupnode = sendMessages(targetTypegr, targetgroup, datanode, "ken101", null);
//        System.out.println(sendTxtMessagegroupnode.asText());
    }
    
    

    /**
	 * 注册IM用户[单个]
	 * 
	 * 给指定Constants.APPKEY创建一个新的用户
	 * 
	 * @param dataNode
	 * @return
	 */
	public static ObjectNode createNewIMUserSingle(String userName) {

	    ObjectNode dataNode = JsonNodeFactory.instance.objectNode();
	    dataNode.put("username",userName);
	    dataNode.put("password",MD5.getMD5(userName).toLowerCase());
		ObjectNode objectNode = factory.objectNode();

		try {

		    objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataNode,
					HTTPMethod.METHOD_POST);
		    String statusCode = objectNode.get("statusCode").asText();
		    if(!"200".equals(statusCode))
		    {
		    	 LOGGER.error("用户名为"+userName+"|"+objectNode.toString());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
	
	
	/**
	 * 发送消息
	 * 
	 * @param targetType
	 *            消息投递者类型：users 用户, chatgroups 群组
	 * @param target
	 *            接收者ID 必须是数组,数组元素为用户ID或者群组ID
	 * @param msg
	 *            消息内容
	 * @param from
	 *            发送者
	 * @param ext
	 *            扩展字段
	 * 
	 * @return 请求响应
	 */
	public static ObjectNode sendMessages(String targetType, ArrayNode target, ObjectNode msg, String from,
			ObjectNode ext) {

		ObjectNode objectNode = factory.objectNode();

		ObjectNode dataNode = factory.objectNode();

		try {
			// 构造消息体
			dataNode.put("target_type", targetType);
			dataNode.put("target", target);
			dataNode.put("msg", msg);
			dataNode.put("from", from);
			dataNode.put("ext", ext);

			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.MESSAGES_URL, credential, dataNode,
					HTTPMethod.METHOD_POST);

			objectNode = (ObjectNode) objectNode.get("data");
			for (int i = 0; i < target.size(); i++) {
				String resultStr = objectNode.path(target.path(i).asText()).asText();
				if ("success".equals(resultStr)) {
					LOGGER.error(String.format("Message has been send to user[%s] successfully .", target.path(i)
							.asText()));
				} else if (!"success".equals(resultStr)) {
					LOGGER.error(String.format("Message has been send to user[%s] failed .", target.path(i).asText()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
	

	/**
	 * 创建群组
	 * 
	 */
	public static ObjectNode creatChatGroups(String groupname,String desc,String owner) {
		ObjectNode objectNode = factory.objectNode();

		ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
		dataObjectNode.put("groupname", groupname);
		dataObjectNode.put("desc", desc);
		dataObjectNode.put("approval", false);
		dataObjectNode.put("public", true);
		dataObjectNode.put("maxusers", 333);
		dataObjectNode.put("owner", owner);
		try {
			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.CHATGROUPS_URL, credential, dataObjectNode,
					HTTPMethod.METHOD_POST);
			LOGGER.info(objectNode.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 在群组中添加一个人
	 * 
	 */
	public static ObjectNode addUserToGroup(String chatgroupid, String userName) {
		ObjectNode objectNode = factory.objectNode();

		try {
			URL allMemberssByGroupIdUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/"
					+ chatgroupid + "/users/" + userName);
			ObjectNode dataobjectNode = factory.objectNode();
			objectNode = HTTPClientUtils.sendHTTPRequest(allMemberssByGroupIdUrl, credential, dataobjectNode,
					HTTPMethod.METHOD_POST);
			LOGGER.info(objectNode.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 在群组中减少一个人
	 * 
	 */
	public static ObjectNode deleteUserFromGroup(String chatgroupid, String userName) {
		ObjectNode objectNode = factory.objectNode();

		try {
			URL allMemberssByGroupIdUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/"
					+ chatgroupid + "/users/" + userName);
			objectNode = HTTPClientUtils.sendHTTPRequest(allMemberssByGroupIdUrl, credential, null,
					HTTPMethod.METHOD_DELETE);
			LOGGER.info(objectNode.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
	
	/**
	 * 注册IM用户[批量]
	 * 
	 * 给指定Constants.APPKEY创建一批用户
	 * 
	 * @param dataArrayNode
	 * @return
	 */
	public static ObjectNode createNewIMUserBatch(ArrayNode dataArrayNode) {

		ObjectNode objectNode = factory.objectNode();

		// check Constants.APPKEY format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

			objectNode.put("message", "Bad format of Constants.APPKEY");

			return objectNode;
		}

		// check properties that must be provided
		if (dataArrayNode.isArray()) {
			for (JsonNode jsonNode : dataArrayNode) {
				if (null != jsonNode && !jsonNode.has("username")) {
					LOGGER.error("Property that named username must be provided .");

					objectNode.put("message", "Property that named username must be provided .");

					return objectNode;
				}
				if (null != jsonNode && !jsonNode.has("password")) {
					LOGGER.error("Property that named password must be provided .");

					objectNode.put("message", "Property that named password must be provided .");

					return objectNode;
				}
			}
		}

		try {

			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataArrayNode,
					HTTPMethod.METHOD_POST);
			if(objectNode.get("statusCode").asInt() != 200){
				System.out.println(objectNode);
				System.out.println(dataArrayNode.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
	
	/**
	 * 注册IM用户[批量生成用户然后注册]
	 * 
	 * 给指定Constants.APPKEY创建一批用户
	 * 
	 * @param usernamePrefix
	 *            生成用户名的前缀
	 * @param perNumber
	 *            批量注册时一次注册的数量
	 * @param totalNumber
	 *            生成用户注册的用户总数
	 * @return
	 */
	public static ObjectNode createNewIMUserBatchGen(String usernamePrefix, Long perNumber, Long totalNumber) {
		ObjectNode objectNode = factory.objectNode();

		if (totalNumber == 0 || perNumber == 0) {
			return objectNode;
		}
		System.out.println("你即将注册" + totalNumber + "个用户，如果大于" + perNumber + "了,会分批注册,每次注册" + perNumber + "个");
		ArrayNode genericArrayNode = genericArrayNode(usernamePrefix, totalNumber);
		if (totalNumber <= perNumber) {
			objectNode = createNewIMUserBatch(genericArrayNode);
		} else {

			ArrayNode tmpArrayNode = factory.arrayNode();
			
			for (int i = 0; i < genericArrayNode.size(); i++) {
				tmpArrayNode.add(genericArrayNode.get(i));
				// 300 records on one migration
				if ((i + 1) % perNumber == 0) {
					objectNode = createNewIMUserBatch(tmpArrayNode);
					if(objectNode!=null){
						LOGGER.info("注册IM用户[批量]: " + objectNode.toString());
					}
					tmpArrayNode.removeAll();
					continue;
				}

				// the rest records that less than the times of 300
				if (i > (genericArrayNode.size() / perNumber * perNumber - 1)) {
					objectNode = createNewIMUserBatch(tmpArrayNode);
					if(objectNode!=null){
						LOGGER.info("注册IM用户[批量]: " + objectNode.toString());
					}
					tmpArrayNode.removeAll();
				}
			}
		}

		return objectNode;
	}
	
	/*************************************************************************************************************************/
	/**
	 * 指定前缀和数量生成用户基本数据
	 * 
	 * @param usernamePrefix
	 * @param number
	 * @return
	 */
	public static ArrayNode genericArrayNode(String usernamePrefix, Long number) {
		ArrayNode arrayNode = factory.arrayNode();
		for (int i = 0; i < number; i++) {
			ObjectNode userNode = factory.objectNode();
			userNode.put("username", usernamePrefix + i);
			userNode.put("password",MD5.getMD5( usernamePrefix + i).toLowerCase());

			arrayNode.add(userNode);
		}

		return arrayNode;
	}
	
	/**
	 * IM用户登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static ObjectNode imUserLogin(String username, String password) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + Constants.APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}
		if (StringUtil.isEmpty(username)) {
			LOGGER.error("Your username must be provided，the value is username of imuser.");

			objectNode.put("message", "Your username must be provided，the value is username of imuser.");

			return objectNode;
		}
		if (StringUtil.isEmpty(password)) {
			LOGGER.error("Your password must be provided，the value is username of imuser.");

			objectNode.put("message", "Your password must be provided，the value is username of imuser.");

			return objectNode;
		}

		try {
			ObjectNode dataNode = factory.objectNode();
			dataNode.put("grant_type", "password");
			dataNode.put("username", username);
			dataNode.put("password", password);

			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.TOKEN_APP_URL, null, dataNode,
					HTTPMethod.METHOD_POST);

		} catch (Exception e) {
			throw new RuntimeException("Some errors occurred while fetching a token by username and password .");
		}

		return objectNode;
	}
	
}
