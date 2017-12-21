package cn.temobi.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ValidateUtil {

	public static boolean validateData(HttpServletRequest request){
		String imei = request.getParameter("imei");
        String imsi = request.getParameter("imsi");
        String clientId = request.getParameter("clientId");
		List<String> imeiList = new ArrayList<String>();
		imeiList.add("866978029659216");
		imeiList.add("867622021036051");
		if(StringUtil.isNotEmpty(imei) && imeiList.contains(imei))
		{
			return false;
		}
		
		List<String> imsiList = new ArrayList<String>();
		imsiList.add("460026912730549");
		imsiList.add("460023955766006");
		imsiList.add("460075531174010");
		if(StringUtil.isNotEmpty(imsi) && imsiList.contains(imsi))
		{
			return false;
		}
		
		List<String> clientIdList = new ArrayList<String>();
		clientIdList.add("1067235");
		clientIdList.add("865497");
		clientIdList.add("977610");
		if(StringUtil.isNotEmpty(clientId) && clientIdList.contains(clientId))
		{
			return false;
		}

		return  true;
	}
	
}
