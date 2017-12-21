package cn.temobi.core.action;

import java.beans.PropertyEditorSupport;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.temobi.complex.entity.Client;
import cn.temobi.core.util.StringUtil;

import com.salim.cache.CacheHelper;
import com.salim.cache.MemoryCacheHelper;
import com.salim.util.NullCheck;


/**
 * @author salim
 * @created 2012-7-18
 * @package com.palminfo.core.action
 */
@SuppressWarnings("serial")
public class SimpleController implements Serializable {
    protected Logger log = LoggerFactory.getLogger(getClass());
    protected LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String,Object>();

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
        public void setAsText(String value) {
            try {
                setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
            } catch(ParseException e) {
                setValue(null);
            }
        }

        public String getAsText() {
            return new SimpleDateFormat("yyyy-MM-dd").format((Date) getValue());
        }

    });
    }
    
    public LinkedHashMap<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(LinkedHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
    
    public boolean isChinaTelPhone(String phoneNum){
		if(!StringUtil.isEmpty(phoneNum)){
			Pattern p = Pattern.compile("^(133|153|180|189|181)\\d{8}$");     
	        Matcher m = p.matcher(phoneNum);
	        if(m.matches()){
	        	return true;
	        }
		}
		return false;
	}
    
    public void CachedValue(String key, Object value, boolean usememory) {
		//MemoryCacheHelper.getInstance().setValue(key, value);
		if(usememory){ //使用物理内存
		 MemoryCacheHelper.getInstance().setValue(key,value); 
		}else{
		 //memcache 1小时 
		 CacheHelper.getInstance().set(60*60, key, value); 
		}
		
	}
    
    //传入缓存过期的时间,time单位为小时
    public void CachedValueAndTime(String key, Object value, int time) {
		 CacheHelper.getInstance().set(60*60*time, key, value); 
	}
    
    //memcache 缓存清除
    public void CachedRemove(String key) {
    	CacheHelper.getInstance().remove(key); 
    }
	
	public Object FetchFromCache(String key) {
		if (!NullCheck.isNull(MemoryCacheHelper.getInstance().getValue(key))) {
			return MemoryCacheHelper.getInstance().getValue(key);
		} else if (!NullCheck.isNull(CacheHelper.getInstance().get(key))) {
			return CacheHelper.getInstance().get(key);
		} else
			return null;
	}
	
	public void RemoveCachedValue(String key) {
		MemoryCacheHelper.getInstance().remove(key);
	}
	
}
