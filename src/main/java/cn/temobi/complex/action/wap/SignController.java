package cn.temobi.complex.action.wap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.action.def.IndexController;
import cn.temobi.complex.entity.WxDiy;
import cn.temobi.complex.service.CmUserRankingService;
import cn.temobi.complex.service.WxDiyService;
import cn.temobi.core.common.Constant;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.HttpClientUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.UUIDGenerator;

//import com.he.monitor.*;
@Controller
public class SignController {//extends CommonAction {
	
	private static Logger log = LoggerFactory.getLogger(SignController.class);
	
	String strUrlPath=PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url");
	String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
	String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
	
	@Resource(name="wxDiyService")
	private WxDiyService wxDiyService;
	
	public static long lastGetTicketTime=0;
	public static String appid="wxc35fdf1a57e1e34d";
	public static String secret="c3178386a37fb9ab1e6b70f00c0b0a67";
	public static String strTicket = "jsapi_ticket";
	public static String strTocken;
	public static String nonce_str = create_nonce_str();
	public static String timestamp = create_timestamp();
	@RequestMapping(value = "/sign", method = {RequestMethod.GET,RequestMethod.POST})
    public void getAuthInfo(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
    	String strAuthInfo="";
		// 注意 URL 一定要动态获取，不能 hardcode
		//String url =ServletActionContext.getRequest().getRequestURL().toString();// "http://www.weiju360.cn";
		String url=request.getParameter("url");
		String isappinstalled=request.getParameter("isappinstalled");
		String from=request.getParameter("from");
		String jsonpcallback=request.getParameter("jsonpcallback");
		if(url==null)
		{
			url="http://www.weiju360.cn";
		}
		if(StringUtil.isNotEmpty(from))
		{
			url += "&from=" + from;
		}
		if(StringUtil.isNotEmpty(isappinstalled))
		{
			url += "&isappinstalled=" + isappinstalled;
		}
		if(jsonpcallback==null)
		{
			jsonpcallback="jsonpcallback";
		}
		//System.out.println(url);
		long lCurTime=System.currentTimeMillis() / 1000;
		if(lastGetTicketTime ==0 || (lCurTime-lastGetTicketTime>=3600))
		{
			try 
			{
				strTocken=getAccessToken(appid, secret);
		   	 	strTicket=getJsApiTicket(strTocken);
		   	 	nonce_str = create_nonce_str();
		   	 	timestamp = create_timestamp();
			} catch (Exception e) {
				// TODO: handle exception
			}
			lastGetTicketTime=lCurTime;
		}
		int i=0;
		Map<String, String> ret = sign(strTicket, url);
		strAuthInfo=jsonpcallback+"({";

		for (Map.Entry entry : ret.entrySet()) 
		{
			if(entry.getKey().toString().compareTo("timestamp")==0)
			{
				if(i>0)
				{
					strAuthInfo=strAuthInfo+",\""+entry.getKey() + "\":" + entry.getValue()+"";
				}
				else 
				{
					strAuthInfo=strAuthInfo+"\""+entry.getKey() + "\":" + entry.getValue()+"";
				}
			}
			else 
			{			
				if(i>0)
				{
					strAuthInfo=strAuthInfo+",\""+entry.getKey() + "\":\"" + entry.getValue()+"\"";
				}
				else 
				{
					strAuthInfo=strAuthInfo+"\""+entry.getKey() + "\":\"" + entry.getValue()+"\"";
				}
			}
		    
		    i++;
		}
		strAuthInfo=strAuthInfo+"})";
		//return strAuthInfo;
		
		response.getWriter().println(strAuthInfo);
		response.getWriter().flush();
		response.getWriter().close();
    }
	
	@RequestMapping(value = "/wxUpload", method = {RequestMethod.GET,RequestMethod.POST})
    public void wxUpload(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
		String serverId = request.getParameter("serverId");
		String imgVal = request.getParameter("imgVal");
		String bgSrc = request.getParameter("bgSrc");
		log.error("微信上传参数serverId="+serverId+"&imgVal="+imgVal+"&bgSrc="+bgSrc);
		String saveUrl = strUrlPath+ resourcePath + "weixin/"+DateUtils.getCurrentDateTime("yyyyMMdd")+"/";
		String savePath = homePath+resourcePath + "weixin/"+DateUtils.getCurrentDateTime("yyyyMMdd")+"/";
		String ySrc = "";
		if(StringUtil.isNotEmpty(serverId) && serverId.indexOf("http://") == -1)
		{
			String strRemoteFileString="http://file.api.weixin.qq.com/cgi-bin/media/get?";//access_token=OaqLPjdlcovGOdFpl85uw46veX_Iq11klnexPyV3d70vWUPopZoiJby9ezhBppJ8FchBdBB8Um2NAUMx_aaH0rOrqnfXcu1bO-NAIRdFlp4&media_id=FVHkqRQvYgUSKbQqMsrXCVy4XuOCIJ5PoucHjcWCnaBGrwGuy0i_6DEM3FPSm4J"
			try {
				strTocken=getAccessToken(appid, secret);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			strRemoteFileString=strRemoteFileString+"access_token="+strTocken;//request.getParameter("access_token");
			strRemoteFileString=strRemoteFileString+"&media_id="+serverId;
			File filePath =new File(savePath);
			if  (!filePath.exists()  && !filePath.isDirectory())      
			{       
			    //System.out.println("//不存在");  
			    filePath .mkdir();    
			} 
			HttpClient client = new HttpClient();  
		    GetMethod httpGet = new GetMethod(strRemoteFileString);  
		    try 
		    {  
		    	int iRet=client.executeMethod(httpGet);  
		        if(iRet == HttpStatus.SC_OK)
		        {
		            InputStream in = httpGet.getResponseBodyAsStream();  	             
		            FileOutputStream out = new FileOutputStream(new File(savePath+serverId+".jpg"));  	             
		            byte[] b = new byte[1024];  
		            int len = 0;  
		            while((len=in.read(b))!= -1)
		            {  
		                out.write(b,0,len);  
		            }  
		            in.close();  
		            out.close();  
		         }
		              
		    }catch (HttpException e){  
		            e.printStackTrace();  
		    } catch (IOException e) {  
		            e.printStackTrace();  
		    }finally{  
		            httpGet.releaseConnection();  
		    }  
		    
		    ySrc = saveUrl+serverId+".jpg";
		}else{
			ySrc = serverId;
		}
	    String resourceId = "*wx*"+UUIDGenerator.getUUID();
	    WxDiy wxDiy = new WxDiy();
	    wxDiy.setBgSrc(bgSrc);
	    wxDiy.setySrc(ySrc);
	    wxDiy.setImgVal(imgVal);
	    wxDiy.setServerId(serverId);
	    wxDiy.setResourceId(resourceId);
	    wxDiyService.save(wxDiy);
	    String jsonpcallback =request.getParameter("jsonpcallback");//得到js函数名称  
	    response.getWriter().println(jsonpcallback + "(\""+resourceId+"\")");
		response.getWriter().flush();
		response.getWriter().close();
    }
	
	@RequestMapping(value = "/getwximage", method = {RequestMethod.GET,RequestMethod.POST})
    public void getImageFromWX(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
		String saveUrl = strUrlPath+ resourcePath + "weixin/"+DateUtils.getCurrentDateTime("yyyyMMdd")+"/";
		String savePath = homePath+resourcePath + "weixin/"+DateUtils.getCurrentDateTime("yyyyMMdd")+"/";
		String strRemoteFileString="http://file.api.weixin.qq.com/cgi-bin/media/get?";//access_token=OaqLPjdlcovGOdFpl85uw46veX_Iq11klnexPyV3d70vWUPopZoiJby9ezhBppJ8FchBdBB8Um2NAUMx_aaH0rOrqnfXcu1bO-NAIRdFlp4&media_id=FVHkqRQvYgUSKbQqMsrXCVy4XuOCIJ5PoucHjcWCnaBGrwGuy0i_6DEM3FPSm4J"
		try {
			strTocken=getAccessToken(appid, secret);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		strRemoteFileString=strRemoteFileString+"access_token="+strTocken;//request.getParameter("access_token");
		strRemoteFileString=strRemoteFileString+"&media_id="+request.getParameter("media_id");
		File filePath =new File(savePath);
		if  (!filePath.exists()  && !filePath.isDirectory())      
		{       
		    //System.out.println("//不存在");  
		    filePath .mkdir();    
		} 
		HttpClient client = new HttpClient();  
	    GetMethod httpGet = new GetMethod(strRemoteFileString);  
	    try 
	    {  
	    	int iRet=client.executeMethod(httpGet);  
	        if(iRet == HttpStatus.SC_OK)
	        {
	            InputStream in = httpGet.getResponseBodyAsStream();  	             
	            FileOutputStream out = new FileOutputStream(new File(savePath+request.getParameter("media_id")+".jpg"));  	             
	            byte[] b = new byte[1024];  
	            int len = 0;  
	            while((len=in.read(b))!= -1)
	            {  
	                out.write(b,0,len);  
	            }  
	            in.close();  
	            out.close();  
	         }
	              
	    }catch (HttpException e){  
	            e.printStackTrace();  
	    } catch (IOException e) {  
	            e.printStackTrace();  
	    }finally{  
	            httpGet.releaseConnection();  
	    }  
	    response.getWriter().println(saveUrl+request.getParameter("media_id")+".jpg");
		response.getWriter().flush();
		response.getWriter().close();
    }
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        ret.put("appid", appid);
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("access_token", strTocken);
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    /**
     * 获取jsapi_ticket
     * 
     * @return
     * @throws Exception
     */
    public static String getJsApiTicket(String accessToken) throws Exception {
    	String str = HttpClientUtil.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi");
    	JSONObject jSONObject = JSONObject.fromObject(str);
        return jSONObject.get("ticket").toString();
    }
 
    /**
     * 获取access_token
     * 
     * @return
     * @throws Exception
     */
    public static String getAccessToken(String appid, String secret) throws Exception {
    	String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";       
    	String str = HttpClientUtil.get(ACCESSTOKEN_URL.concat("&appid=") + appid + "&secret=" + secret);
    	JSONObject jSONObject = JSONObject.fromObject(str);
        return jSONObject.get("access_token").toString();
    }
    
    public static void main(String[] args) {
    	String media_id = "jTaeewT-3m5vBx5O85mfkTBDDLQMHJdSrhpzOo5MBQAeYQRtCYq7zidAFPaNgCT3";
    	String strUrlPath=PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url");
		String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
		String saveUrl = strUrlPath+ resourcePath + "weixin/"+DateUtils.getCurrentDateTime("yyyyMMdd")+"/";
		String savePath = homePath+resourcePath + "weixin/"+DateUtils.getCurrentDateTime("yyyyMMdd")+"/";
		String strRemoteFileString="http://file.api.weixin.qq.com/cgi-bin/media/get?";//access_token=OaqLPjdlcovGOdFpl85uw46veX_Iq11klnexPyV3d70vWUPopZoiJby9ezhBppJ8FchBdBB8Um2NAUMx_aaH0rOrqnfXcu1bO-NAIRdFlp4&media_id=FVHkqRQvYgUSKbQqMsrXCVy4XuOCIJ5PoucHjcWCnaBGrwGuy0i_6DEM3FPSm4J"
		try {
			strTocken=getAccessToken(appid, secret);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		strRemoteFileString=strRemoteFileString+"access_token="+strTocken;//request.getParameter("access_token");
		strRemoteFileString=strRemoteFileString+"&media_id="+media_id;
		File filePath =new File(savePath);
		if  (!filePath.exists()  && !filePath.isDirectory())      
		{       
		    //System.out.println("//不存在");  
		    filePath .mkdir();    
		} 
		HttpClient client = new HttpClient();  
	    GetMethod httpGet = new GetMethod(strRemoteFileString);  
	    try 
	    {  
	    	int iRet=client.executeMethod(httpGet);  
	        if(iRet == HttpStatus.SC_OK)
	        {
	            InputStream in = httpGet.getResponseBodyAsStream();  	             
	            FileOutputStream out = new FileOutputStream(new File(savePath+media_id+".jpg"));  	             
	            byte[] b = new byte[1024];  
	            int len = 0;  
	            while((len=in.read(b))!= -1)
	            {  
	                out.write(b,0,len);  
	            }  
	            in.close();  
	            out.close();  
	         }
	              
	    }catch (HttpException e){  
	            e.printStackTrace();  
	    } catch (IOException e) {  
	            e.printStackTrace();  
	    }finally{  
	            httpGet.releaseConnection();  
	    }  
	}
    
    /**
	 * 图片上传
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadImage", method = {RequestMethod.GET,RequestMethod.POST})
	public void uploadImage(HttpServletRequest request,HttpServletResponse response) {
		try {

			String url = FileUtil.uploadLogo(request, response,"weixin");
			response.getWriter().println(strUrlPath+url);
			response.getWriter().flush();
			response.getWriter().close();
		}catch(Exception e) {
		}
	}
}