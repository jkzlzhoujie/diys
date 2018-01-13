package com.tencent;

import com.tencent.common.Util;
import com.tencent.protocol.pay_protocol.ReqData;

public class Main {

    public static void main(String[] args) {

        try {
        	ReqData reqData = new ReqData("olEg40bOOrqKQkEKy5ni6pV0AVrE","recharge", "", "1217752501201407033233368018", 1, "http://localhost:8080", "127.0.0.1", "JSAPI","");
        	System.out.println(WXPay.requestUnifiedorderService(reqData));
        	
        } catch (Exception e){
        	e.printStackTrace();
            Util.log(e.getMessage());
        }

    }

}
