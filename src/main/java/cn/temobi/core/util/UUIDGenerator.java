package cn.temobi.core.util;

import java.util.UUID;

public class UUIDGenerator { 
    /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.replaceAll("-", "");
    } 
   
    public static void main(String[] args){ 
        for(int i=0;i<10;i++){ 
            System.out.println(getUUID()); 
        } 
    } 
}   
