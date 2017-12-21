package cn.temobi.core.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author salim
 * @created 2012-2-22
 * @package com.plaminfo.core.util
 */
public class JsonUtils {
    /** * logger. */
    private static Log logger = LogFactory.getLog(JsonUtils.class);

    /** * 工具类需要的保护构造方法. */
    protected JsonUtils() {
    }
    
    /***
     * 将List对象序列化为JSON文本
     */
    public static <T> String toJSONString(List<T> list)
    {
        JSONArray jsonArray = JSONArray.fromObject(list);

        return jsonArray.toString();
    }
    
    /***
     * 将对象序列化为JSON文本
     * @param object
     * @return
     */
    public static String toJSONString(Object object)
    {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return jsonArray.toString();
    }
    

    /***
     * 将JSON对象数组序列化为JSON文本
     * @param jsonArray
     * @return
     */
    public static String toJSONString(JSONArray jsonArray)
    {
        return jsonArray.toString();
    }

    /***
     * 将JSON对象序列化为JSON文本
     * @param jsonObject
     * @return
     */
    public static String toJSONString(JSONObject jsonObject)
    {
        return jsonObject.toString();
    }   
    
    /***
     * 将对象转换为List对象
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
	public static List toArrayList(Object object)
    {
        List arrayList = new ArrayList();

        JSONArray jsonArray = JSONArray.fromObject(object);

        Iterator it = jsonArray.iterator();
        while (it.hasNext())
        {
            JSONObject jsonObject = (JSONObject) it.next();

            Iterator keys = jsonObject.keys();
            while (keys.hasNext())
            {
                Object key = keys.next();
                Object value = jsonObject.get(key);
                arrayList.add(value);
            }
        }

        return arrayList;
    }

    /***
     * 将对象转换为Collection对象
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Collection toCollection(Object object)
    {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return JSONArray.toCollection(jsonArray);
    }

    /***
     * 将对象转换为JSON对象数组
     * @param object
     * @return
     */
    public static JSONArray toJSONArray(Object object)
    {
        return JSONArray.fromObject(object);
    }

    /***
     * 将对象转换为JSON对象
     * @param object
     * @return
     */
    public static JSONObject toJSONObject(Object object)
    {
        return JSONObject.fromObject(object);
    }

    /***
     * 将对象转换为HashMap
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
	public static HashMap toHashMap(Object object)
    {
        HashMap<String, Object> data = new HashMap<String, Object>();
        JSONObject jsonObject = JsonUtils.toJSONObject(object);
        Iterator it = jsonObject.keys();
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            data.put(key, value);
        }

        return data;
    }

    /***
     * 将对象转换为List<Map<String,Object>>
     * @param object
     * @return
     */
    // 返回非实体类型(Map<String,Object>)的List
    @SuppressWarnings("unchecked")
	public static List<Map<String, Object>> toList(Object object)
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray jsonArray = JSONArray.fromObject(object);
        for (Object obj : jsonArray)
        {
            JSONObject jsonObject = (JSONObject) obj;
            Map<String, Object> map = new HashMap<String, Object>();
            Iterator it = jsonObject.keys();
            while (it.hasNext())
            {
                String key = (String) it.next();
                Object value = jsonObject.get(key);
                map.put((String) key, value);
            }
            list.add(map);
        }
        return list;
    }

    /***
     * 将JSON对象数组转换为传入类型的List
     * @param <T>
     * @param jsonArray
     * @param objectClass
     * @return
     */
    public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass)
    {
        return JSONArray.toList(jsonArray, objectClass);
    }

    /***
     * 将对象转换为传入类型的List
     * @param <T>
     * @param jsonArray
     * @param objectClass
     * @return
     */
    public static <T> List<T> toList(Object object, Class<T> objectClass)
    {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return JSONArray.toList(jsonArray, objectClass);
    }

    /***
     * 将JSON对象转换为传入类型的对象
     * @param <T>
     * @param jsonObject
     * @param beanClass
     * @return
     */
    public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass)
    {
        return (T) JSONObject.toBean(jsonObject, beanClass);
    }

    /***
     * 将将对象转换为传入类型的对象
     * @param <T>
     * @param object
     * @param beanClass
     * @return
     */
    public static <T> T toBean(Object object, Class<T> beanClass)
    {
        JSONObject jsonObject = JSONObject.fromObject(object);

        return (T) JSONObject.toBean(jsonObject, beanClass);
    }

    /**
     * write.
     *
     * @param bean obj
     * @param writer 输出流
     * @param excludes 不转换的属性数组
     * @param datePattern date到string转换的模式
     * @throws Exception 写入数据可能出现异常
     */
    public static void write(Object bean, Writer writer,
        String[] excludes, String datePattern) throws Exception {
        JsonConfig jsonConfig = configJson(excludes, datePattern);

        JSON json = JSONSerializer.toJSON(bean, jsonConfig);

        json.write(writer);
    }

    /**
     * 配置json-lib需要的excludes和datePattern.
     *
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return JsonConfig 根据excludes和dataPattern生成的jsonConfig，用于write
     */
    public static JsonConfig configJson(String[] excludes,
        String datePattern) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class,
            new DateJsonValueProcessor(datePattern));

        return jsonConfig;
    }

    /**
     * data={"id":"1"}用json的数据创建指定的pojo.
     *
     * @param <T> Object
     * @param data json字符串
     * @param clazz 需要转换成bean的具体类型
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return T
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> T json2Bean(String data,
        Class<T> clazz, String[] excludes, String datePattern)
        throws Exception {
        // JsonUtils.configJson(excludes, datePattern);
        T entity = clazz.newInstance();

        return json2Bean(data, entity, excludes, datePattern);
    }

    /**
     * data={"id":"1"}用json里的数据，填充指定的pojo.
     *
     * @param <T> Object
     * @param data json字符串
     * @param entity 需要填充数据的bean
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return T
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> T json2Bean(String data, T entity,
        String[] excludes, String datePattern) throws Exception {
        // JsonUtils.configJson(excludes, datePattern);
        JSONObject jsonObject = JSONObject.fromObject(data);

        return json2Bean(jsonObject, entity, excludes, datePattern);
    }

    /**
     * 根据Class生成entity，再把JSONObject中的数据填充进去.
     *
     * @param <T> Object
     * @param jsonObject json对象
     * @param clazz 需要转换成bean的具体类型
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return T
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> T json2Bean(JSONObject jsonObject,
        Class<T> clazz, String[] excludes, String datePattern)
        throws Exception {
        // JsonUtils.configJson(excludes, datePattern);
        T entity = clazz.newInstance();

        return json2Bean(jsonObject, entity, excludes, datePattern);
    }

    /**
     * 把JSONObject中的数据填充到entity中.
     *
     * @param <T> Object
     * @param jsonObject json对象
     * @param entity 需要填充数据的node
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return T
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> T json2Bean(JSONObject jsonObject,
        T entity, String[] excludes, String datePattern)
        throws Exception {
        // JsonUtils.configJson(excludes, datePattern);
        Set<String> excludeSet = new HashSet<String>();

        for (String exclude : excludes) {
            excludeSet.add(exclude);
        }

        for (Object object : jsonObject.entrySet()) {
            Map.Entry entry = (Map.Entry) object;
            String propertyName = entry.getKey().toString();

            if (excludeSet.contains(propertyName)) {
                continue;
            }

            String propertyValue = entry.getValue().toString();

            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName,
                        entity.getClass());
                Class propertyType = propertyDescriptor.getPropertyType();

                Method writeMethod = propertyDescriptor.getWriteMethod();

                if (propertyType == String.class) {
                    writeMethod.invoke(entity, propertyValue);
                } else if ((propertyType == Byte.class)
                        || (propertyType == byte.class)) {
                    writeMethod.invoke(entity,
                        Byte.parseByte(propertyValue));
                } else if ((propertyType == Short.class)
                        || (propertyType == short.class)) {
                    writeMethod.invoke(entity,
                        Short.parseShort(propertyValue));
                } else if ((propertyType == Integer.class)
                        || (propertyType == int.class)) {
                    writeMethod.invoke(entity,
                        Integer.parseInt(propertyValue));
                } else if ((propertyType == Long.class)
                        || (propertyType == long.class)) {
                    writeMethod.invoke(entity,
                        Long.parseLong(propertyValue));
                } else if ((propertyType == Float.class)
                        || (propertyType == float.class)) {
                    writeMethod.invoke(entity,
                        Float.parseFloat(propertyValue));
                } else if ((propertyType == Double.class)
                        || (propertyType == double.class)) {
                    writeMethod.invoke(entity,
                        Double.parseDouble(propertyValue));
                } else if ((propertyType == Boolean.class)
                        || (propertyType == boolean.class)) {
                    writeMethod.invoke(entity,
                        Boolean.parseBoolean(propertyValue));
                } else if ((propertyType == Character.class)
                        || (propertyType == char.class)) {
                    writeMethod.invoke(entity, propertyValue.charAt(0));
                } else if (propertyType == Date.class) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
                    writeMethod.invoke(entity,
                        dateFormat.parse(propertyValue));
                }
            } catch (IntrospectionException ex) {
                logger.info(ex);

                continue;
            }
        }

        return entity;
    }

    /**
     * data=[{"id":"1"},{"id":2}]用json里的数据，创建pojo队列.
     *
     * @param <T> Object
     * @param data json字符串
     * @param clazz 需要转换成node的具体类型
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return List
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> List<T> json2List(String data,
        Class<T> clazz, String[] excludes, String datePattern)
        throws Exception {
        JSONArray jsonArray = JSONArray.fromObject(data);

        return json2List(jsonArray, clazz, excludes, datePattern);
    }

    /**
     * data=[{"id":"1"},{"id":2}]用json里的数据，创建pojo队列.
     *
     * @param <T> Object
     * @param jsonArray JSONArray
     * @param clazz 需要转换成node的具体类型
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return List
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> List<T> json2List(
        JSONArray jsonArray, Class<T> clazz, String[] excludes,
        String datePattern) throws Exception {
        List<T> list = new ArrayList<T>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            T node = json2Bean(jsonObject, clazz, excludes, datePattern);
            list.add(node);
        }

        return list;
    }
    
    /**   
     * 把对象封装为JSON格式   
     *    
     * @param o   
     *            对象   
     * @return JSON格式   
     */    
    @SuppressWarnings("unchecked")     
    public static String toJson(final Object o)     
    {     
        if (o == null)     
        {     
            return "null";     
        }     
        if (o instanceof String) //String     
        {     
            return string2Json((String) o);     
        }     
        if (o instanceof Boolean) //Boolean     
        {     
            return boolean2Json((Boolean) o);     
        }     
        if (o instanceof Number) //Number     
        {     
            return number2Json((Number) o);     
        }     
        if (o instanceof Map) //Map     
        {     
            return map2Json((Map<String, Object>) o);     
        }     
        if (o instanceof Collection) //List  Set     
        {     
            return collection2Json((Collection) o);     
        }     
        if (o instanceof Object[]) //对象数组     
        {     
            return array2Json((Object[]) o);     
        }     
    
        if (o instanceof int[])//基本类型数组     
        {     
            return intArray2Json((int[]) o);     
        }     
        if (o instanceof boolean[])//基本类型数组     
        {     
            return booleanArray2Json((boolean[]) o);     
        }     
        if (o instanceof long[])//基本类型数组     
        {     
            return longArray2Json((long[]) o);     
        }     
        if (o instanceof float[])//基本类型数组     
        {     
            return floatArray2Json((float[]) o);     
        }     
        if (o instanceof double[])//基本类型数组     
        {     
            return doubleArray2Json((double[]) o);     
        }     
        if (o instanceof short[])//基本类型数组     
        {     
            return shortArray2Json((short[]) o);     
        }     
        if (o instanceof byte[])//基本类型数组     
        {     
            return byteArray2Json((byte[]) o);     
        }     
        if (o instanceof Object) //保底收尾对象     
        {     
            return object2Json(o);     
        }     
    
        throw new RuntimeException("不支持的类型: " + o.getClass().getName());     
    }     
    
    /**   
     * 将 String 对象编码为 JSON格式，只需处理好特殊字符   
     *    
     * @param s   
     *            String 对象   
     * @return JSON格式   
     */    
    public static String string2Json(final String s)     
    {     
        final StringBuilder sb = new StringBuilder(s.length() + 20);     
        sb.append('\"');     
        for (int i = 0; i < s.length(); i++)     
        {     
            final char c = s.charAt(i);     
            switch (c)     
            {     
            case '\"':     
                sb.append("\\\"");     
                break;     
            case '\\':     
                sb.append("\\\\");     
                break;     
            case '/':     
                sb.append("\\/");     
                break;     
            case '\b':     
                sb.append("\\b");     
                break;     
            case '\f':     
                sb.append("\\f");     
                break;     
            case '\n':     
                sb.append("\\n");     
                break;     
            case '\r':     
                sb.append("\\r");     
                break;     
            case '\t':     
                sb.append("\\t");     
                break;     
            default:     
                sb.append(c);     
            }     
        }     
        sb.append('\"');     
        return sb.toString();     
    }     
    
    /**   
     * 将 Number 表示为 JSON格式   
     *    
     * @param number   
     *            Number   
     * @return JSON格式   
     */    
    public static String number2Json(final Number number)     
    {     
        return number.toString();     
    }     
    
    /**   
     * 将 Boolean 表示为 JSON格式   
     *    
     * @param bool   
     *            Boolean   
     * @return JSON格式   
     */    
    public static String boolean2Json(final Boolean bool)     
    {     
        return bool.toString();     
    }     
    
    /**   
     * 将 Collection 编码为 JSON 格式 (List,Set)   
     *    
     * @param c   
     * @return   
     */    
    public static String collection2Json(final Collection<Object> c)     
    {     
        final Object[] arrObj = c.toArray();     
        return toJson(arrObj);     
    }     
    
    /**   
     * 将 Map<String, Object> 编码为 JSON 格式   
     *    
     * @param map   
     * @return   
     */    
    public static String map2Json(final Map<String, Object> map)     
    {     
        if (map.isEmpty())     
        {     
            return "{}";     
        }     
        final StringBuilder sb = new StringBuilder(map.size() << 4); //4次方     
        sb.append('{');     
        final Set<String> keys = map.keySet();     
        for (final String key : keys)     
        {     
            final Object value = map.get(key);     
            sb.append('\"');     
            sb.append(key); //不能包含特殊字符     
            sb.append('\"');     
            sb.append(':');     
            sb.append(toJson(value)); //循环引用的对象会引发无限递归     
            sb.append(',');     
        }     
        // 将最后的 ',' 变为 '}':      
        sb.setCharAt(sb.length() - 1, '}');     
        return sb.toString();     
    }     
    
    /**   
     * 将数组编码为 JSON 格式   
     *    
     * @param array   
     *            数组   
     * @return JSON 格式   
     */    
    public static String array2Json(final Object[] array)     
    {     
        if (array.length == 0)     
        {     
            return "[]";     
        }     
        final StringBuilder sb = new StringBuilder(array.length << 4); //4次方     
        sb.append('[');     
        for (final Object o : array)     
        {     
            sb.append(toJson(o));     
            sb.append(',');     
        }     
        // 将最后添加的 ',' 变为 ']':      
        sb.setCharAt(sb.length() - 1, ']');     
        return sb.toString();     
    }     
    
    public static String intArray2Json(final int[] array)     
    {     
        if (array.length == 0)     
        {     
            return "[]";     
        }     
        final StringBuilder sb = new StringBuilder(array.length << 4);     
        sb.append('[');     
        for (final int o : array)     
        {     
            sb.append(Integer.toString(o));     
            sb.append(',');     
        }     
        // set last ',' to ']':     
        sb.setCharAt(sb.length() - 1, ']');     
        return sb.toString();     
    }     
    
    public static String longArray2Json(final long[] array)     
    {     
        if (array.length == 0)     
        {     
            return "[]";     
        }     
        final StringBuilder sb = new StringBuilder(array.length << 4);     
        sb.append('[');     
        for (final long o : array)     
        {     
            sb.append(Long.toString(o));     
            sb.append(',');     
        }     
        // set last ',' to ']':     
        sb.setCharAt(sb.length() - 1, ']');     
        return sb.toString();     
    }     
    
    public static String booleanArray2Json(final boolean[] array)     
    {     
        if (array.length == 0)     
        {     
            return "[]";     
        }     
        final StringBuilder sb = new StringBuilder(array.length << 4);     
        sb.append('[');     
        for (final boolean o : array)     
        {     
            sb.append(Boolean.toString(o));     
            sb.append(',');     
        }     
        // set last ',' to ']':     
        sb.setCharAt(sb.length() - 1, ']');     
        return sb.toString();     
    }     
    
    public static String floatArray2Json(final float[] array)     
    {     
        if (array.length == 0)     
        {     
            return "[]";     
        }     
        final StringBuilder sb = new StringBuilder(array.length << 4);     
        sb.append('[');     
        for (final float o : array)     
        {     
            sb.append(Float.toString(o));     
            sb.append(',');     
        }     
        // set last ',' to ']':     
        sb.setCharAt(sb.length() - 1, ']');     
        return sb.toString();     
    }     
    
    public static String doubleArray2Json(final double[] array)     
    {     
        if (array.length == 0)     
        {     
            return "[]";     
        }     
        final StringBuilder sb = new StringBuilder(array.length << 4);     
        sb.append('[');     
        for (final double o : array)     
        {     
            sb.append(Double.toString(o));     
            sb.append(',');     
        }     
        // set last ',' to ']':     
        sb.setCharAt(sb.length() - 1, ']');     
        return sb.toString();     
    }     
    
    public static String shortArray2Json(final short[] array)     
    {     
        if (array.length == 0)     
        {     
            return "[]";     
        }     
        final StringBuilder sb = new StringBuilder(array.length << 4);     
        sb.append('[');     
        for (final short o : array)     
        {     
            sb.append(Short.toString(o));     
            sb.append(',');     
        }     
        // set last ',' to ']':     
        sb.setCharAt(sb.length() - 1, ']');     
        return sb.toString();     
    }     
    
    static String byteArray2Json(final byte[] array)     
    {     
        if (array.length == 0)     
        {     
            return "[]";     
        }     
        final StringBuilder sb = new StringBuilder(array.length << 4);     
        sb.append('[');     
        for (final byte o : array)     
        {     
            sb.append(Byte.toString(o));     
            sb.append(',');     
        }     
        // set last ',' to ']':     
        sb.setCharAt(sb.length() - 1, ']');     
        return sb.toString();     
    }     
    
    public static String object2Json(final Object bean)     
    {     
        //数据检查     
        if (bean == null)     
        {     
            return "{}";     
        }     
        final Method[] methods = bean.getClass().getMethods(); //方法数组     
        final StringBuilder sb = new StringBuilder(methods.length << 4); //4次方     
        sb.append('{');     
    
        for (final Method method : methods)     
        {     
            try    
            {     
                final String name = method.getName();     
                String key = "";     
                if (name.startsWith("get"))     
                {     
                    key = name.substring(3);     
    
                    //防死循环     
                    final String[] arrs =     
                    { "Class" };     
                    boolean bl = false;     
                    for (final String s : arrs)     
                    {     
                        if (s.equals(key))     
                        {     
                            bl = true;     
                            continue;     
                        }     
                    }     
                    if (bl)     
                    {     
                        continue; //防死循环     
                    }     
                }     
                else if (name.startsWith("is"))     
                {     
                    key = name.substring(2);     
                }     
                if (key.length() > 0 && Character.isUpperCase(key.charAt(0)) && method.getParameterTypes().length == 0)     
                {     
                    if (key.length() == 1)     
                    {     
                        key = key.toLowerCase();     
                    }     
                    else if (!Character.isUpperCase(key.charAt(1)))     
                    {     
                        key = key.substring(0, 1).toLowerCase() + key.substring(1);     
                    }     
                    final Object elementObj = method.invoke(bean);     
    
                    //System.out.println("###" + key + ":" + elementObj.toString());     
    
                    sb.append('\"');     
                    sb.append(key); //不能包含特殊字符     
                    sb.append('\"');     
                    sb.append(':');     
                    sb.append(toJson(elementObj)); //循环引用的对象会引发无限递归     
                    sb.append(',');     
                }     
            }     
            catch (final Exception e)     
            {     
                //e.getMessage();     
                throw new RuntimeException("在将bean封装成JSON格式时异常：" + e.getMessage(), e);     
            }     
        }     
        if (sb.length() == 1)     
        {     
            return bean.toString();     
        }     
        else    
        {     
            sb.setCharAt(sb.length() - 1, '}');     
            return sb.toString();     
        }     
    }
    
    /***
    public static ArrayList<Object> JSON2JavaObj(JSONArray jsonArr) {
		ArrayList<Object> arrL = new ArrayList<Object>();
		for (int i = 0, l = jsonArr.size(); i < l; i++) {
			Object objTemp = jsonArr.get(i);
			if (objTemp instanceof JSONNull) {
				arrL.add(null);
			}else if (objTemp instanceof JSONObject) {
				arrL.add(JSON2JavaObj((JSONObject) objTemp));
			} else if (objTemp instanceof JSONArray) {
				arrL.add(JSON2JavaObj((JSONArray) objTemp));
			} else {
				// date
				if (objTemp != null && objTemp instanceof String) {
					String str = objTemp.toString();
					Matcher m = pattern.matcher(str);
					if (m.matches()) {
						try {
							objTemp = sdf.parse(str);
						} catch (Exception e) {
						}
					}
				}
				arrL.add(objTemp);
			}
		}
		return arrL;
	}

    private static String timeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	private static String datePattern = "^((-\\d+|\\d{4,})(-(\\d{2})(-(\\d{2}))?)?)?T((\\d{2})(:(\\d{2})(:(\\d{2})(\\.(\\d{1,3})(\\d)?\\d*)?)?)?)?(([+-])(\\d{2})((\\d{2}))?)?$";
	private static SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
	private static Pattern pattern = Pattern.compile(datePattern);
	
	@SuppressWarnings(value = { "unchecked" })
	public static Map<String, Object> JSON2JavaObj(JSONObject jsonObject) {
		Map<String, Object> tempObjectMap = new HashMap<String, Object>();
		Iterator ite = jsonObject.entrySet().iterator();
		while (ite.hasNext()) {
			Map.Entry paraMap = (Map.Entry) ite.next();
			String strKey = (String) paraMap.getKey();
			Object objTemp = paraMap.getValue();
			if (objTemp instanceof JSONNull) {
				tempObjectMap.put(strKey, null);
			} else if (objTemp instanceof JSONObject) {
				tempObjectMap.put(strKey, JSON2JavaObj((JSONObject) objTemp));
			} else if (objTemp instanceof JSONArray) {
				tempObjectMap.put(strKey, JSON2JavaObj((JSONArray) objTemp));
			} else {
				// date
				if (objTemp != null && objTemp instanceof String) {
					String str = objTemp.toString();
					Matcher m = pattern.matcher(str);
					if (m.matches()) {
						try {
							objTemp = sdf.parse(str);
						} catch (Exception e) {
						}
					}
				}
				tempObjectMap.put(strKey, objTemp);
			}
		}
		return tempObjectMap;
	}
    
	***/
    
  
}
