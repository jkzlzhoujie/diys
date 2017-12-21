package cn.temobi.core.util;

import java.math.*;  
import java.text.DecimalFormat;

public class BigDecimalUtil {  
	 private static DecimalFormat df = new DecimalFormat("0.00");
	 private static final int DEF_DIV_SCALE=2;  
	 private static final int roundingMode=BigDecimal.ROUND_HALF_UP;  
	
	 public static void main(String[] args){  
	        System.out.println(sum(0.05, 0.05));  
	        System.out.println(sub(1.0, 0.42));  
	        System.out.println(mul(4.015, 100));  
	        System.out.println(div(123.3, 100));  
	  }  
	
	/** 
     * 对double数据进行取精度. 
     * @param value  double数据. 
     * @param scale  精度位数(保留的小数位数). 
     * @param roundingMode  精度取值方式. 
     * @return 精度计算后的数据. 
     */ 
    public static double round(double value) {  
    	df.setRoundingMode(RoundingMode.DOWN);    
        BigDecimal bd = new BigDecimal(df.format(value));  
        bd = bd.setScale(DEF_DIV_SCALE, roundingMode);  
        double d = bd.doubleValue();  
        bd = null;  
        return d;  
    }  


     /**
     * double 相加
     * @param d1
     * @param d2
     * @return
     */
    public static double sum(double d1,double d2){
    	df.setRoundingMode(RoundingMode.DOWN); 
        BigDecimal bd1 = new BigDecimal(df.format(d1));
        BigDecimal bd2 = new BigDecimal(df.format(d2));
        return bd1.add(bd2).doubleValue();
    }


    /**
     * double 相减
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1,double d2){
    	df.setRoundingMode(RoundingMode.DOWN);  
        BigDecimal bd1 = new BigDecimal(df.format(d1));
        BigDecimal bd2 = new BigDecimal(df.format(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1,double d2){
    	df.setRoundingMode(RoundingMode.DOWN); 
        BigDecimal bd1 = new BigDecimal(df.format(d1));
        BigDecimal bd2 = new BigDecimal(df.format(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * double 除法
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static double div(double d1,double d2){
    	df.setRoundingMode(RoundingMode.DOWN);  
        BigDecimal bd1 = new BigDecimal(df.format(d1));
        BigDecimal bd2 = new BigDecimal(df.format(d2));
        return bd1.divide(bd2,DEF_DIV_SCALE,roundingMode).doubleValue();
    } 
      
}  
