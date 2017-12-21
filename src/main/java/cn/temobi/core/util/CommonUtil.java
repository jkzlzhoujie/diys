package cn.temobi.core.util;


import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import cn.temobi.core.common.Constant;

/**
 * Created by linch on 2014/6/12.
 */
public class CommonUtil {
	public static Logger logger = Logger.getLogger(CommonUtil.class);
	/**
	 * NULL字符串转换，参数对象为null时，返回空字符串
	 * @param o 转换原对象
	 * @return 字符串
	 */
	public static String nvl(Object o) {
		if (o == null) {
			return "";
		}
		return o.toString().trim();
	}

	/**
	 * NULL字符串转换，参数对象为null时，返回NULL
	 * @param o 转换原对象
	 * @return 字符串
	 */
	public static Integer nvlInteger(Object o){
		if (o == null || "".equals(String.valueOf(o))) {
			return null;
		}else{
			if(o.toString().indexOf(".") == -1){
				return Integer.valueOf(o.toString());
			}else{
				return nvlDouble(o).intValue();
			}

		}
	}

	public static Double nvlDouble(Object o){
		if (o == null || "".equals(String.valueOf(o))) {
			return new Double(0);
		}else{
			return Double.valueOf(o.toString());
		}

	}
	
	public static Long nvlLong(Object o){
		if (o == null || "".equals(String.valueOf(o))) {
			return new Long(0);
		}else{
			return Long.valueOf(o.toString());
		}

	}

	/**
	 * double值保留一位小数
	 * @param d
	 * @return 字符串
	 */
	public static String double1(double d){
		DecimalFormat df =new DecimalFormat("#####0.0");
		return df.format(d);
	}

	/**
	 * double值保留两位小数
	 * @param o
	 * @return 字符串
	 */
	public static double double2(Object o){
		if (o == null || "".equals(String.valueOf(o))) {
			return new Double(0);
		}else{
			DecimalFormat df =new DecimalFormat("#####0.00");
			return Double.valueOf(df.format(o));
		}

	}

	/**
	 * 获取日期字段时间
	 * @param date
	 * @return 字符串
	 */
	public static String getTimeFromDate(Timestamp date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String sTime = sdf.format(date);
		return sTime;
	}

	/**
	 * 获取当前日期
	 * @return 字符串
	 */
	public static String getNowDate(){
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
		return matter.format(dt);
	}



	// 产生一个由UUID生成的文件名
	private static String getNewFileName() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toUpperCase();
	}

	/**
	 * 验证字符串是不是数字
	 */
	public static boolean checkNum(String str){
		return str.matches("[0-9]{0}([0-9]|[.])+");
	}

	public static String getYearSelect(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		StringBuffer buf = new StringBuffer("<select id='year_sel'>");
		for(int i=9; i>=0; i--){
			buf.append("<option value='"+(year-i)+"'>"+(year-i)+"</option>");
		}
		buf.append("</select>");
		return buf.toString();
	}
	
	/**
	 * 比较版本号大小
	 * @param version
	 * @return
	 */
	public static boolean checkVersion(String version){
		if(StringUtil.isNotEmpty(version)){
			if(Integer.valueOf(version)> Constant.CURRENT_VERSION){
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args){
		boolean s = checkVersion("32");
		System.out.println();
	}
}

