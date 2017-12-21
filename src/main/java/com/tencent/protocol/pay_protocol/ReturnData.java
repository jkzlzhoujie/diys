package com.tencent.protocol.pay_protocol;

/**
 * User: rizenguo
 * Date: 2014/10/22
 * Time: 21:29
 */

import com.tencent.common.Configure;
import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Signature;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求被扫支付API需要提交的数据
 */
public class ReturnData {

    //每个字段具体的意思请查看API文档
    private String appid = "";
    private String mch_id = "";
    private String device_info = "";
    private String nonce_str = "";
    private String sign = "";
    private String result_code  = "";
    private String err_code  = "";
    private String err_code_des = "";
    private String is_subscribe = "";
    private int total_fee = 0;
    private String cash_fee = "";
    private String transaction_id = "";
    private String out_trade_no = "";
    private String time_end = "";
    private String attach = "";
    private String trade_type  = "";
    private String bank_type  = "";
    private String fee_type = "";
    private String openid = "";

	public String getAppid() {
		return appid;
	}



	public void setAppid(String appid) {
		this.appid = appid;
	}



	public String getMch_id() {
		return mch_id;
	}



	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}



	public String getDevice_info() {
		return device_info;
	}



	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}



	public String getNonce_str() {
		return nonce_str;
	}



	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}



	public String getSign() {
		return sign;
	}



	public void setSign(String sign) {
		this.sign = sign;
	}



	public String getResult_code() {
		return result_code;
	}



	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}



	public String getErr_code() {
		return err_code;
	}



	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}



	public String getErr_code_des() {
		return err_code_des;
	}



	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}



	public String getIs_subscribe() {
		return is_subscribe;
	}



	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}



	public int getTotal_fee() {
		return total_fee;
	}



	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}



	public String getCash_fee() {
		return cash_fee;
	}



	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}



	public String getTransaction_id() {
		return transaction_id;
	}



	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}



	public String getOut_trade_no() {
		return out_trade_no;
	}



	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}



	public String getTime_end() {
		return time_end;
	}



	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}



	public String getAttach() {
		return attach;
	}



	public void setAttach(String attach) {
		this.attach = attach;
	}



	public String getTrade_type() {
		return trade_type;
	}



	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}



	public String getBank_type() {
		return bank_type;
	}



	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}



	public String getFee_type() {
		return fee_type;
	}



	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}



	public String getOpenid() {
		return openid;
	}



	public void setOpenid(String openid) {
		this.openid = openid;
	}



	public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
