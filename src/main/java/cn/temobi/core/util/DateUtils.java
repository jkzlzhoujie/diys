package cn.temobi.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * @author salim
 * @created 2012-3-22
 * @package com.salim.util
 */
public class DateUtils {
	private static final TimeZone zone = TimeZone.getTimeZone("GMT+8");
    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String YYYYMMDD = "yyyyMMdd";
    public final static String HHMMSS = "HHmmss";
    public final static String YYYY = "yyyy";
    public final static String MM = "MM";
    public final static String DD = "dd";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_MM = "yyyy-MM";
    public final static String HH_MM_SS = "HH:mm:ss";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    public final static String format_YYYY_MM_DD_HH_MM_SS = "yyyy.MM.dd HH:mm:ss";

    public static String formatStr_yyyyMMddHHmmssS = "yyyy-MM-dd HH:mm:ss.S";
    public static String formatStr_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static String formatStr_yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static String formatStr_yyyyMMddHH = "yyyy-MM-dd HH";
    public static String formatStr_yyyyMMdd = "yyyy-MM-dd";
    public static String[] formatStr = { formatStr_yyyyMMddHHmmss,
            formatStr_yyyyMMddHHmm, formatStr_yyyyMMddHH, formatStr_yyyyMMdd };

    public static String getCurrentDateTime(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        String time = df.format(date);
        return time;
    }
    
    /**
     * 构造函数
     */
    public DateUtils() {
    }

    /**
     * 日期格式化－将<code>Date</code>类型的日期格式化为<code>String</code>型
     * 
     * @param date
     *            待格式化的日期
     *@param pattern
     *            时间样式
     *@return 一个被格式化了的<code>String</code>日期
     */
    public static String format(Date date, String pattern) {
        if (date == null)
            return "";
        else
            return getFormatter(pattern).format(date);
    }

    /**
     * 默认把日期格式化成yyyy-mm-dd格式
     * 
     * @param date
     * @return
     */
    public static String format(Date date) {
        if (date == null)
            return "";
        else
            return getFormatter(YYYY_MM_DD).format(date);
    }

    /**
     * 把字符串日期默认转换为yyyy-mm-dd格式的Data对象
     * 
     * @param strDate
     * @return
     */
    public static Date format(String strDate) {
        Date d = null;
        if (strDate == "")
            return null;
        else
            try {
                d = getFormatter(YYYY_MM_DD).parse(strDate);
            } catch (ParseException pex) {
                return null;
            }
        return d;
    }

    /**
     * 把字符串日期转换为f指定格式的Data对象
     * 
     * @param strDate
     *            ,f
     * @return
     */
    public static Date format(String strDate, String f) {
        Date d = null;
        if (strDate == "")
            return null;
        else
            try {
                d = getFormatter(f).parse(strDate);
            } catch (ParseException pex) {
                return null;
            }
        return d;
    }

    /**
     * 日期解析－将<code>String</code>类型的日期解析为<code>Date</code>型
     * 
     * @param date
     *            待格式化的日期
     *@param pattern
     *            日期样式
     *@exception ParseException
     *                如果所给的字符串不能被解析成一个日期
     *@return 一个被格式化了的<code>Date</code>日期
     */
    public static Date parse(String strDate, String pattern)
            throws ParseException {
        try {
            return getFormatter(pattern).parse(strDate);
        } catch (ParseException pe) {
            throw new ParseException(
                    "Method parse in Class DateUtil err: parse strDate fail.",
                    pe.getErrorOffset());
        }
    }

    /**
     * 获取当前日期
     * 
     * @return 一个包含年月日的<code>Date</code>型日期
     */
    public static synchronized Date getCurrDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 获取当前日期
     * 
     * @return 一个包含年月日的<code>String</code>型日期，但不包含时分秒。yyyy-mm-dd
     */
    public static String getCurrDateStr() {
        return format(getCurrDate(), YYYY_MM_DD);
    }

    /**
     * 获取当前时间
     * 
     * @return 一个包含年月日时分秒的<code>String</code>型日期。hh:mm:ss
     */
    public static String getCurrTimeStr() {
        return format(getCurrDate(), HH_MM_SS);
    }

    /**
     * 获取当前完整时间,样式: yyyy－MM－dd hh:mm:ss
     * 
     * @return 一个包含年月日时分秒的<code>String</code>型日期。yyyy-MM-dd hh:mm:ss
     */
    public static String getCurrDateTimeStr() {
        return format(getCurrDate(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前年分 样式：yyyy
     * 
     * @return 当前年分
     */
    public static String getYear() {
        return format(getCurrDate(), YYYY);
    }

    /**
     * 获取当前月分 样式：MM
     * 
     * @return 当前月分
     */
    public static String getMonth() {
        return format(getCurrDate(), MM);
    }

    /**
     * 获取当前日期号 样式：dd
     * 
     * @return 当前日期号
     */
    public static String getDay() {
        return format(getCurrDate(), DD);
    }

    /**
     * 按给定日期样式判断给定字符串是否为合法日期数据
     * 
     * @param strDate
     *            要判断的日期
     *@param pattern
     *            日期样式
     *@return true 如果是，否则返回false
     */
    public static boolean isDate(String strDate, String pattern) {
        try {
            parse(strDate, pattern);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式日期（包括：年月日yyyy-MM-dd）数据
     * 
     * @param strDate
     *            要判断的日期
     *@return true 如果是，否则返回false
     */
    // public static boolean isDate(String strDate) {
    // try {
    // parse(strDate, YYYY_MM_DD);
    // return true;
    // }
    // catch (ParseException pe) {
    // return false;
    // }
    // }

    /**
     * 判断给定字符串是否为特定格式年份（格式：yyyy）数据
     * 
     * @param strDate
     *            要判断的日期
     *@return true 如果是，否则返回false
     */
    public static boolean isYYYY(String strDate) {
        try {
            parse(strDate, YYYY);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    public static boolean isYYYY_MM(String strDate) {
        try {
            parse(strDate, YYYY_MM);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式的年月日（格式：yyyy-MM-dd）数据
     * 
     * @param strDate
     *            要判断的日期
     *@return true 如果是，否则返回false
     */
    public static boolean isYYYY_MM_DD(String strDate) {
        try {
            parse(strDate, YYYY_MM_DD);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式年月日时分秒（格式：yyyy-MM-dd HH:mm:ss）数据
     * 
     * @param strDate
     *            要判断的日期
     *@return true 如果是，否则返回false
     */
    public static boolean isYYYY_MM_DD_HH_MM_SS(String strDate) {
        try {
            parse(strDate, YYYY_MM_DD_HH_MM_SS);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式时分秒（格式：HH:mm:ss）数据
     * 
     * @param strDate
     *            要判断的日期
     *@return true 如果是，否则返回false
     */
    public static boolean isHH_MM_SS(String strDate) {
        try {
            parse(strDate, HH_MM_SS);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式时间（包括：时分秒hh:mm:ss）数据
     * 
     * @param strTime
     *            要判断的时间
     *@return true 如果是，否则返回false
     */
    // public static boolean isTime(String strTime) {
    // try {
    // parse(strTime, HH_MM_SS);
    // return true;
    // }
    // catch (ParseException pe) {
    // return false;
    // }
    // }

    /**
     * 判断给定字符串是否为特定格式日期时间（包括：年月日时分秒 yyyy-MM-dd hh:mm:ss）数据
     * 
     * @param strDateTime
     *            要判断的日期时间
     *@return true 如果是，否则返回false
     */
    // public static boolean isDateTime(String strDateTime) {
    // try {
    // parse(strDateTime, YYYY_MM_DD_HH_MM_SS);
    // return true;
    // }
    // catch (ParseException pe) {
    // return false;
    // }
    // }

    /**
     * 获取一个简单的日期格式化对象
     * 
     * @return 一个简单的日期格式化对象
     */
    private static SimpleDateFormat getFormatter(String parttern) {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 获取给定日前的后intevalDay天的日期
     * 
     * @param refenceDate
     *            给定日期（格式为：yyyy-MM-dd）
     * @param intevalDays
     *            间隔天数
     * @return 计算后的日期
     */
    public static String getNextDate(String refenceDate, int intevalDays) {
        try {
            return getNextDate(parse(refenceDate, YYYY_MM_DD), intevalDays);
        } catch (Exception ee) {
            return "";
        }
    }

    /**
     * 获取给定日前的后intevalDay天的日期
     * 
     * @param refenceDate
     *            Date 给定日期
     * @param intevalDays
     *            int 间隔天数
     * @return String 计算后的日期
     */
    public static String getNextDate(Date refenceDate, int intevalDays) {
        try {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(refenceDate);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)
                    + intevalDays);
            return format(calendar.getTime(), YYYY_MM_DD);
        } catch (Exception ee) {
            return "";
        }
    }
    
    /**
     * 前一天 23:59:59
     * @param refenceDate
     * @return
     */
    public static String getLastOneDay(Date refenceDate) {
        try {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(refenceDate);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return format(calendar.getTime(), YYYY_MM_DD_HH_MM_SS);
        } catch (Exception ee) {
            return "";
        }
    }
    
    
    
    /**
     * 当天  23:59:59
     * @param refenceDate
     * @return
     */
    public static String getCurrentDay(Date refenceDate) {
        try {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(refenceDate);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return format(calendar.getTime(), YYYY_MM_DD_HH_MM_SS);
        } catch (Exception ee) {
            return "";
        }
    }

    public static long getIntevalDays(String startDate, String endDate) {
        try {
            return getIntevalDays(parse(startDate, YYYY_MM_DD), parse(endDate,
                    YYYY_MM_DD));
        } catch (Exception ee) {
            return 0l;
        }
    }

    public static long getIntevalDays(Date startDate, Date endDate) {
        try {
            java.util.Calendar startCalendar = java.util.Calendar.getInstance();
            java.util.Calendar endCalendar = java.util.Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);
            long diff = endCalendar.getTimeInMillis()-startCalendar.getTimeInMillis();

            return (diff / (1000 * 60 * 60 * 24));
        } catch (Exception ee) {
            return 0l;
        }
    }

    /**
     *求当前日期和指定字符串日期的相差天数
     * 
     * @param startDate
     * @return
     */
    public static long getTodayIntevalDays(String startDate) {
        try {
            // 当前时间
            Date currentDate = new Date();

            // 指定日期
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date theDate = myFormatter.parse(startDate);

            // 两个时间之间的天数
            long days = (currentDate.getTime() - theDate.getTime())
                    / ( 24 * 60 * 60 * 1000 );

            return days;
        } catch (Exception ee) {
            return 0l;
        }
    }
    
    /**
     *求当前日期和指定字符串日期的相差小时数
     * 
     * @param startDate
     * @return
     */
    public static long getTodayIntevalMoney(String startDate) {
        try {
            // 当前时间
            Date currentDate = new Date();

            // 指定日期
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date theDate = myFormatter.parse(startDate);

            // 两个时间之间的天数
            long days = (currentDate.getTime() - theDate.getTime())
                    / (60 * 60 * 1000 );

            return days;
        } catch (Exception ee) {
            return 0l;
        }
    }

    public static Date parseToDate(String dateTimeStr) {
        if (dateTimeStr == null)
            return null;
        Date d = null;
        int formatStrLength = formatStr.length;
        for (int i = 0; i < formatStrLength; i++) {
            d = parseToDate(dateTimeStr, formatStr[i]);
            if (d != null) {
                break;
            }
        }
        return d;
    }

    private static Date parseToDate(String dateTimeStr, String formatString) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        try {
            d = sdf.parse(dateTimeStr);
        } catch (ParseException pe) {

        }
        return d;
    }

    public static String dateTimeToString(Date datetime) {
        // dateTime=dateTime.substring(,)+dateTime.substring(,)+dateTime.substring(,)+dateTime.substring(,)+dateTime.substring(,)+dateTime.substring(,);
        // return dateTime;

        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
        calendar.setTime(datetime);
        String dateTime = calendar.get(Calendar.YEAR) + ""
        + (calendar.get(Calendar.MONTH) + 1 > 9 ? "" : "0")
        + (calendar.get(Calendar.MONTH) + 1) + ""
        + (calendar.get(Calendar.DATE) > 9 ? "" : "0")
        + calendar.get(Calendar.DATE) + ""
        + (calendar.get(Calendar.HOUR_OF_DAY) > 9 ? "" : "0")
        + calendar.get(Calendar.HOUR_OF_DAY) + ""
        + (calendar.get(Calendar.MINUTE) > 9 ? "" : "0")
        + calendar.get(Calendar.MINUTE) + ""
        + (calendar.get(Calendar.SECOND) > 9 ? "" : "0")
        + calendar.get(Calendar.SECOND);
        return dateTime;
    }

    /**
     * 由年、月份，获得当前月的最后一天
     * 
     * @param year
     *            month 月份    
     * @return
     * @throws ParseException
     */
	public static String getLastDayOfMonth(String year, String month) throws ParseException {
		String LastDay = "";
		Calendar cal = Calendar.getInstance();
		Date date_;
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-"+"01");  //hk   报错修改过 20121122
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		date_ = cal.getTime();
		LastDay = new SimpleDateFormat("yyyy-MM-dd").format(date_);
		return LastDay;
	}
	
	//获取当前月的天数
	public static int getDayNumOfCurrentMonth() throws ParseException
	{
		String date= getLastDayOfMonth(getYear(), getMonth());
		int numOfCurrentMonth=Integer.valueOf(date.substring(date.length()-2));
		return numOfCurrentMonth;
	}

	/**
	 * 获取本月的第一天
	 * @return
	 */
	public static String getFirstDayOfThisMonth(){
		Calendar calendar=Calendar.getInstance();
		Date today=calendar.getTime();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMM");
		String todayDate=simpleDateFormat.format(today);
		String a= todayDate.concat("01");
		return a;
	}
	/**
	 * 格式化日期
	 * @return
	 */
	public static String getDateStr(String date, String pattern) {
		Date d = DateUtils.getDate(date);
		return getDate(d, pattern);
	}

	public static String getDate(Date date, String pattern) {
		SimpleDateFormat f = new SimpleDateFormat();
		f.setTimeZone(zone);
		f.applyPattern(pattern);
		return f.format(date);
	}
	
	public static Date getDate(String string) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern(YYYY_MM_DD_HH_MM_SS);
		dateFormat.setTimeZone(zone);
		try {
			Date date = dateFormat.parse(string);
			return date;
		} catch (ParseException e) {
			throw new RuntimeException("时间转换错误!", e);
		}
	}
	
	 /** 
	 * 取得当前日期所在周的第一天 
	 * 
	 * @param date 
	 * @return 
	 */ 
	 public static String getFirstDayOfWeek(String pattern) { 
	 Calendar c = new GregorianCalendar(); 
	 c.setFirstDayOfWeek(Calendar.MONDAY); 
	 c.setTime(new Date()); 
	 c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday 
	 return  format(c.getTime(),pattern);
	 }

	 /** 
	 * 取得当前日期所在周的最后一天 
	 * 
	 * @param date 
	 * @return 
	 */ 
	 public static String getLastDayOfWeek(String pattern) { 
	 Calendar c = new GregorianCalendar(); 
	 c.setFirstDayOfWeek(Calendar.MONDAY); 
	 c.setTime(new Date()); 
	 c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday 
	 return format(c.getTime(), pattern); 
	 } 
	 
	 /**
	     * 设定开始时间月份进行加减
	     * 
	     * @param date
	     *            开始时间
	     *@param month
	     *            加减
	     *@param pattern
	     *            时间样式
	     *@return 一个被格式化了的String日期
	     */
		public static String getDateStr(Date date ,int month,String pattern) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			ca.add(Calendar.MONTH, month);
			Date dateTo = ca.getTime();//上个月的时间
			return DateUtils.format(dateTo, pattern);
		}
		/**
	     * 当前时间月份进行加减
	     * 
	     *@param month
	     *            月份加减
	     *@param pattern
	     *            时间样式
	     *@return 一个被格式化了的String日期
	     */
		public static String getDateStr(int month,String pattern) {
			Calendar ca = Calendar.getInstance();
			Date date = ca.getTime();
			ca.setTime(date);
			ca.add(Calendar.MONTH, month);
			Date dateTo = ca.getTime();//上个月的时间
			return DateUtils.format(dateTo, pattern);
		}
		
		/**
	     * 当前时间小时进行加减
	     * 
	     *@param month
	     *            小时加减
	     *@param pattern
	     *            时间样式
	     *@return 一个被格式化了的String日期
	     */
		public static String getDateStrOfDay(int hours,String pattern) {
			Calendar ca = Calendar.getInstance();
			Date date = ca.getTime();
			ca.setTime(date);
			ca.add(Calendar.HOUR_OF_DAY, hours);
			Date dateTo = ca.getTime();//上个月的时间
			return DateUtils.format(dateTo, pattern);
		}
		
		/**
		 *  设定开始时间月份进行加减
		 * @param date  开始时间
		 * @param hours 小时
		 * @param pattern
		 * @return
		 */
		public static String getDateStrOfDay(String date ,int hours,String pattern) {
			try {
			Calendar ca = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date2 = sdf.parse(date);
			ca.setTime(date2);
			ca.add(Calendar.HOUR_OF_DAY, hours);
			Date dateTo = ca.getTime();//上个月的时间
			return DateUtils.format(dateTo, pattern);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return "";
		}
		
		
		/**
	     * 设定开始时间月份进行加减
	     * 
	     * @param string date
	     *            开始时间
	     *@param month
	     *           月份加减
	     *@param pattern
	     *            时间样式
	     *@return 一个被格式化了的String日期
	     */
		public static String getDateStr(String date ,int month,String pattern) {
			try {
			Calendar ca = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date2 = sdf.parse(date);
			ca.setTime(date2);
			ca.add(Calendar.MONTH, month);
			Date dateTo = ca.getTime();//上个月的时间
			return DateUtils.format(dateTo, pattern);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return "";
		}
		
		/**
	     * 设定开始时间对日进行加减
	     * 
	     * @param string date
	     *            开始时间
	     *@param month
	     *           日加减
	     *@param pattern
	     *            时间样式
	     *@return 一个被格式化了的String日期
	     */
		public static String getDateDay(String date ,int month,String pattern) {
			try {
			Calendar ca = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date2 = sdf.parse(date);
			ca.setTime(date2);
			ca.add(Calendar.DATE, month);
			Date dateTo = ca.getTime();
			return DateUtils.format(dateTo, pattern);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}
	
    /**
     * debug
     */
    public static void main(String[] args) {
        try {
        	/*System.out.println("当前月的第一天"+DateUtils.getFirstDayOfThisMonth());
            System.out.println( "当前日期：" + DateUtils.getCurrDateStr());
            System.out.println( "日期格式化：" + DateUtils.format(new Date(), DateUtils.formatStr_yyyyMMddHHmmssS));
            System.out.println( "短日期：" + DateUtils.format(new Date()));
            System.out.println( "长日期：" + DateUtils.getCurrDateTimeStr());
            System.out.println( "日：" + DateUtils.getDay());
            System.out.println( "月：" + DateUtils.getMonth());
            System.out.println( "年：" + DateUtils.getYear());
            System.out.println( "月未最后一天：" + DateUtils.getLastDayOfMonth("2010", "08"));
            System.out.println( "相差几天：" + DateUtils.getIntevalDays("2010-08-01", "2010-08-21"));
            
            System.out.println( "当前日期后的几天：" + DateUtils.getNextDate("2010-08-01", -3));
            System.out.println( "与今天相差几天：" + DateUtils.getTodayIntevalDays("2010-08-01"));
            System.out.println("获取当月的最后一天"+getDayNumOfCurrentMonth());
            System.out.println("取得当前日期所在周的第一天 "+getFirstDayOfWeek(DateUtils.YYYY_MM_DD));
            System.out.println("取得当前日期所在周的最后一天  "+getLastDayOfWeek(DateUtils.YYYY_MM_DD));*/
        	Calendar ca = Calendar.getInstance();
			Date date = ca.getTime();
			ca.setTime(date);
			ca.add(Calendar.HOUR_OF_DAY, 1);
			Date dateTo = ca.getTime();//上个月的时间
			System.out.println(DateUtils.format(dateTo, DateUtils.formatStr_yyyyMMddHHmmss));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static boolean compareDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
		    Date d1 = df.parse("2016-03-05 00:00:00");
		    Date d2 = df.parse("2016-03-09 00:00:00");
		    long temp = new Date().getTime();
		    if(temp -  d1.getTime() > 0 && d2.getTime() - temp > 0)
		    {
		    	return true;
		    }
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
    
    public static int getAge(String birth)
    {
    	String t1 = birth.replace('-','/'); 
		Date dt1= new Date(t1);
		long i= (System.currentTimeMillis() - dt1.getTime())/(1000*60*60*24); 
		return (int) (i/365);
    }
}
