package cn.temobi.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import org.springframework.core.io.ClassPathResource;

import cn.temobi.complex.dto.ChangeDto;

/**
 * @TODO get .properties file value
 * @author chenjy
 * @since cs
 * @create 2013-5-29
 */
public class PropertiesHelper {
	public static String getProperty(String propertyFile, String key) {
		ClassPathResource resouce = new ClassPathResource(propertyFile);
		InputStream is = null;
		Properties properties = new Properties();
		String value = null;
		try {
			is = resouce.getInputStream();
			properties.load(is);
			value = properties.getProperty(key);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return value;
	}
	
	public static void putProperty(String propertyFile, String key, String value) {
		ClassPathResource resouce = new ClassPathResource(propertyFile);
		PropertiesHelper.class.getResource("/");
		InputStream is = null;
		Properties properties = new Properties();
		try {
			is = resouce.getInputStream();
			properties.load(is);
			properties.setProperty(key, value);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
    /** 
     * @param properties 
     */  
    private static void showKeys(Properties properties) {  
        Enumeration<?> enu = properties.propertyNames();  
        while (enu.hasMoreElements()) {  
            Object key = enu.nextElement();  
            System.out.println(key);  
        }  
    }  
  
    /** 
     * @param properties 
     */  
    private static void showValues(Properties properties) {  
        Enumeration<Object> enu = properties.elements();  
        while (enu.hasMoreElements()) {  
            Object value = enu.nextElement();  
            System.out.println(value);  
        }  
    }  
  
    /** 
     * @param properties 
     */  
    public static void showKeysAndValues(String propertyFile,List<ChangeDto> list) {  
    	ClassPathResource resouce = new ClassPathResource(propertyFile);
		InputStream is = null;
		Properties properties = new Properties();
		try {
			is = resouce.getInputStream();
			properties.load(is);
			Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();  
	        ChangeDto changeDto;
	        while (it.hasNext()) {  
	            Entry<Object, Object> entry = it.next();  
	            Object key = entry.getKey();  
	            Object value = entry.getValue();  
	            changeDto = new ChangeDto();
	            changeDto.setKey(key.toString());
	            changeDto.setName(value.toString());
	            list.add(changeDto);
	        }  
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
    } 
    
    public static void main(String[] args) {
		putProperty("properties/chang.properties", "12", "ces");
	}
}
