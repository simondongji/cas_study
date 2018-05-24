package com.southgis.ibase.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;

/**
 * 提供各种不同类型间的转换，一般是字符串到指定类型
 * @author dennis
 *
 */
public final class ConvertUtil
{
	/**
	 * 将字节数组转换为十六进制表示的字符串(不够两位会补0)。
	 * @param byBuf 要转换的字节数组。
	 * @return
	 */
	public static String byteToString(byte[] byBuf)
	{
		return byteToString(byBuf,true);
	}
	/**
	 * 将字节数组转换为十六进制表示的字符串。
	 * @param byBuf 要转换的字节数组。
	 * @param bPrefixZero 是否带前缀0（一个字节两个字符，如果小于16时，指出是否补0对齐）。
	 * 如：值为0xA时，当此参数为true，返回"0A"；为false，返回"A"
	 * @return 转换后的字符串，如果出错，返回null
	 */
	public static String byteToString(byte[] byBuf, boolean bPrefixZero)
	{
		if(byBuf==null) return null;
		StringBuffer sb=new StringBuffer();
		String sNum;
		for (int i = 0; i < byBuf.length; i++)
		{
			sNum=Integer.toHexString(byBuf[i]&0xFF);
			if(sNum.length()==1 && bPrefixZero)
				sb.append("0");
			sb.append(sNum);
		}
		return sb.toString();
	}
	
	/**
	 * 将指定字符串重复多次，生成新字符串返回
	 * @param chr 要重复的字符（或字符串）
	 * @param iCount 重复记数（如果小于等于0，则返回""）
	 * @param split 重复间的分隔符（如果不需要，则可以设为null或""）
	 * @return
	 */
	public static String RepeatrChar(String chr, int iCount, String split)
	{
		if(iCount<=0) return "";
		if(iCount==1) return chr;
		StringBuilder sb=new StringBuilder();
		if(split==null) split="";
		for(int ix=0;ix<iCount;++ix){
			sb.append(split);
			sb.append(chr);
		}
		if(!split.isEmpty()){
			sb=sb.delete(0, split.length());
		}
		return sb.toString();
	}
//	public static String RepeatrChar(char chr, int iCount, char split)
//	{
//		if(iCount<=0) return "";
//		if(iCount==1) return Character.toString(chr);
//		StringBuilder sb=new StringBuilder();
//		for(int ix=0;ix<iCount;++ix){
//			if(split!=(char)0)
//				sb.append(split);
//			sb.append(chr);
//		}
//		if(split!=(char)0)
//			sb=sb.deleteCharAt(0);
//		return sb.toString();
//	}

	/**
	 * 将字符串内容进行CSV内容编码（类似HTML编码风格）：
	 * 回车换行符转换为&#13;&#10;，双引号转换为&quot;，逗号转换为&#44;，&符转换为&amp;
	 * @param sOrgVal 要编码的字符串
	 * @return 返回编码后的字符串，如果为空，返回Empty。
	 */
	public static String toCsvEncode(String sOrgVal)
	{
		if(CheckUtil.isNullorEmpty(sOrgVal))return "";
		return sOrgVal.replaceAll("&", "&amp;")
				.replaceAll("\\r","&#13;").replaceAll("\\n","&#10;")
				.replaceAll(",","&#44;").replaceAll("\"","&quot;");
	}
	
	/**
	 * 将以CSV编码（类似HTML编码风格）的字符串内容解码，
	 * 要解码的字符见{@code toCsvEncode}描述。
	 * @param sCsvVal 要解码的字符串
	 * @return 返回解码后的字符串，如果为空，返回Empty。
	 */
	public static String fromCsvEncode(String sCsvVal)
	{
		if(CheckUtil.isNullorEmpty(sCsvVal))return "";
		
		return sCsvVal.replaceAll("&#13;","\r").replaceAll("&#10;","\n")
				.replaceAll("&#44;",",").replaceAll("&quot;","\"")
				.replaceAll("&amp;", "&");
	}
	
	/**
	 * 将字符串转换成可做为ID使用的字符串（只包含下划线、字母、数字、或中文）<br>
	 * 原则：数字上的符号，用数字转意；纯符号键，从左到右，从上到下依次按键盘字母从左到右上下两排<br>
	 * 最后p与z结对，而多出一对符号再用最后一排字母键从左到右结对（比如：xc vb nm）。对应关系如下：<br>
	 * !@#$%^&*()   ~`   _-   +=   {[   }]   |\   :;   "'   &lt;,   &gt;.   ?/<br>
	 * 1234567890   qa   ws   ed   rf   tg   yh   uj   ik   ol   pz   xc<br>
	 * @param text 要转换的字符串
	 * @return
	 */
	public static String toIdEncode(String text)
	{
		if(CheckUtil.isNullorEmpty(text))return "";
		
		return text.replace("_", "_w")
				.replace("~", "_q")
				.replace("!", "_1").replace("@", "_2").replace("#", "_3").replace("$", "_4")
				.replace("-", "_s").replace("\\", "_h").replace(":", "_u").replace("<", "_o")
				.replace(">", "_p").replace(".", "_z")
				.replace("?", "_x").replace("/", "_c");
	}

	/**
	 * 转换为HTML编码格式常量字符串值，转换&、"、'、＜、＞、回车换行符
	 * @param text
	 * @return
	 */
	public static String toHtmlEncode(String text)
	{
		if(CheckUtil.isNullorEmpty(text))return "";
		
		return text.replace("&", "&amp;")
			.replace("\"", "&quot;").replace("'", "&apos;")
			.replace("<", "&lt;").replace(">", "&gt;")
			.replace("\r","&#13;").replace("\n","&#10;");
	}

	/**
	 * 转换为js常量字符串值（\、"、'、回车换行符）
	 * @param text
	 * @return
	 */
	public static String toJs(String text){
		if(CheckUtil.isNullorEmpty(text))return "";
		text = text.replace("\\", "\\\\")
			 .replace("\"", "\\\"").replace("'", "\\'")
			 .replace("\r", "\\r").replace("\n", "\\n");
		return text;
	}
	
	/**
	 * 转换为sql常量字符串值（替换'为''）。
	 * @param text
	 * @return 如果为空（“”）或null，返回“”
	 */
	public static String toSql(String text){
		if(CheckUtil.isNullorEmpty(text))
			return "";
		return text.replace("'", "''");
	}
	/**
	 * 转换为字符串值。
	 * 如果是Date/Calendar/UUID类型，将返回标准格式的字符串；
	 * 如果是byte[]，将返回Base64编码串。
	 * 其他类型，返回toString的值
	 * @param ov
	 * @param sDefault
	 * @return
	 */
	public static String getValue(Object ov, String sDefault)
	{
		if (ov == null)
			return sDefault;
		if (ov instanceof String)
			return (String)ov;
		if (ov instanceof Date)
			return DateUtil.dateToString((Date)ov);
		if(ov instanceof Calendar)
			return DateUtil.dateToString(((Calendar)ov).getTime());
		if (ov instanceof UUID)
			return UUIDHelper.getString((UUID)ov);
		if (ov instanceof byte[])
			return Base64.getEncoder().encodeToString((byte[])ov);
		
		return ov.toString();
	}
	/**
	 * 尝试转换成整数。如果出错，返回指定默认值。
	 * Double/Float值，将截取整数部分；
	 * 
	 * @param ov
	 * @param iDefault
	 * @return
	 */
	public static int getValue(Object ov, int iDefault)
	{
		if (ov == null)
			return iDefault;
		if (ov instanceof Integer)
			return (int)ov;
		if(ov instanceof Short)
			return (short)ov;
		if(ov instanceof Long)
			return (int)(long)ov;//FIXME 可能会溢出
		if(ov instanceof BigInteger)
			return ((BigInteger)ov).intValue();
		if(ov instanceof BigDecimal)
			return (int)((BigDecimal)ov).doubleValue();
		if(ov instanceof Double)
			return (int)(double)ov;
		if(ov instanceof Float)
			return (int)(float)ov;
		if(ov instanceof Byte)
			return (byte)ov;
		if(ov instanceof Boolean)
			return ((boolean)ov)?1:0;
		
		try
		{
			String sVal=getValue(ov, "").replace(",","");
			if (sVal.indexOf(".") >= 0)
			{
				//截断取整（不四舍五入）
				return (int)Double.parseDouble(sVal);
			}
			else
			{
				return Integer.parseInt(sVal);
			}
		}
		catch(Exception ex)
		{
			return iDefault;
		}
	}
	/**
	 * 尝试转换成长整数。如果出错，返回指定默认值。
	 * Double/Float值，将截取整数部分；
	 * 
	 * @param ov
	 * @param lDefault
	 * @return
	 */
	public static long getValue(Object ov, long lDefault)
	{
		if (ov == null)
			return lDefault;
		if (ov instanceof Integer)
			return (int)ov;
		if(ov instanceof Short)
			return (short)ov;
		if(ov instanceof Long)
			return (long)ov;
		if(ov instanceof BigInteger)
			return ((BigInteger)ov).longValue();
		if(ov instanceof BigDecimal)
			return (long)((BigDecimal)ov).doubleValue();
		if(ov instanceof Double)
			return (long)(double)ov;
		if(ov instanceof Float)
			return (long)(float)ov;
		if(ov instanceof Byte)
			return (byte)ov;
		if(ov instanceof Boolean)
			return ((boolean)ov)?1:0;
		
		try
		{
			String sVal=getValue(ov, "").replace(",","");
			if (sVal.indexOf(".") >= 0)
			{
				//截断取整（不四舍五入）
				return (long)Double.parseDouble(sVal);
			}
			else
			{
				return Long.parseLong(sVal);
			}
		}
		catch(Exception ex)
		{
			return lDefault;
		}
	}
	/**
	 * 转换为无限大的整数。注：对于byte[]值，将调用new BigInteger(byte[])
	 * @param ov
	 * @param biDefautl
	 * @return
	 */
	public static BigInteger getValue(Object ov, BigInteger biDefautl)
	{
		if (ov == null)
			return biDefautl;
		if (ov instanceof Integer)
			return BigInteger.valueOf((int)ov);
		if(ov instanceof Short)
			return BigInteger.valueOf((short)ov);
		if(ov instanceof Long)
			return BigInteger.valueOf((long)ov);
		if(ov instanceof BigInteger)
			return (BigInteger)ov;
		if(ov instanceof BigDecimal)
			return ((BigDecimal)ov).toBigInteger();
		if(ov instanceof Double)
			return BigDecimal.valueOf((double)ov).toBigInteger();
		if(ov instanceof Float)
			return BigInteger.valueOf((long)(float)ov);
		if(ov instanceof Byte)
			return BigInteger.valueOf((byte)ov);
		if(ov instanceof Boolean)
			return BigInteger.valueOf(((boolean)ov)?1:0);
		if(ov instanceof byte[])
			return new BigInteger((byte[])ov);
		
		try
		{
			String sVal=getValue(ov, "").replace(",","");
			int iPos=sVal.indexOf(".");
			if (iPos >= 0)
			{
				//截断取整（不四舍五入）
				sVal=sVal.substring(0,iPos);
				if(sVal.isEmpty()) sVal="0";
			}
			return new BigInteger(sVal);
		}
		catch(Exception ex)
		{
			return biDefautl;
		}
	}
	/**
	 * 尝试转换成Double。如果出错，返回指定默认值。
	 * 
	 * @param ov
	 * @param dDefault
	 * @return
	 */
	public static double getValue(Object ov, double dDefault)
	{
		if (ov == null)
			return dDefault;
		if (ov instanceof Integer)
			return (int)ov;
		if(ov instanceof Short)
			return (short)ov;
		if(ov instanceof Long)
			return (long)ov;
		if(ov instanceof BigInteger)
			return ((BigInteger)ov).doubleValue();
		if(ov instanceof BigDecimal)
			return ((BigDecimal)ov).doubleValue();
		if(ov instanceof Double)
			return (double)ov;
		if(ov instanceof Float)
			return (float)ov;
		if(ov instanceof Byte)
			return (byte)ov;
		if(ov instanceof Boolean)
			return ((boolean)ov)?1.0:0.0;
		
		try
		{
			String sVal=getValue(ov, "");
			return Double.parseDouble(sVal);
		}
		catch(Exception ex)
		{
			return dDefault;
		}
	}
	
	/**
	 * 尝试转换成无限大的小数。如果出错，返回指定默认值。注：对于byte[]值，将调用new BigDecimal(new BigInteger(byte[]))
	 * 
	 * @param ov
	 * @param dDefault
	 * @return
	 */
	public static BigDecimal getValue(Object ov, BigDecimal bdDefault)
	{
		if (ov == null)
			return bdDefault;
		if (ov instanceof Integer)
			return BigDecimal.valueOf((int)ov);
		if(ov instanceof Short)
			return BigDecimal.valueOf((short)ov);
		if(ov instanceof Long)
			return BigDecimal.valueOf((long)ov);
		if(ov instanceof BigInteger)
			return new BigDecimal((BigInteger)ov);
		if(ov instanceof BigDecimal)
			return (BigDecimal)ov;
		if(ov instanceof Double)
			return BigDecimal.valueOf((double)ov);
		if(ov instanceof Float)
			return BigDecimal.valueOf((float)ov);
		if(ov instanceof Byte)
			return BigDecimal.valueOf((byte)ov);
		if(ov instanceof Boolean)
			return BigDecimal.valueOf(((boolean)ov)?1.0:0.0);
		if(ov instanceof byte[])
			return new BigDecimal(new BigInteger((byte[])ov));
		
		try
		{
			String sVal=getValue(ov, "");
			return new BigDecimal(sVal);
		}
		catch(Exception ex)
		{
			return bdDefault;
		}
	}
	/**
	 * 尝试转换成Boolean。如果出错，返回指定默认值。
	 * @param ov
	 * @param bDefault
	 * @return
	 */
	public static boolean getValue(Object ov, boolean bDefault)
	{
		if (ov == null)
			return bDefault;
		if(ov instanceof Boolean)
			return (boolean)ov;
		if (ov instanceof Integer)
			return ((int)ov)!=0;
		if(ov instanceof Short)
			return ((short)ov)!=0;
		if(ov instanceof Long)
			return ((long)ov)!=0;
		if(ov instanceof BigInteger)
			return ((BigInteger)ov).compareTo(BigInteger.ZERO)==0;
		if(ov instanceof BigDecimal)
			return ((BigDecimal)ov).compareTo(BigDecimal.ZERO)==0;
		if(ov instanceof Double)
			return ((double)ov)!=0.0;
		if(ov instanceof Float)
			return ((float)ov)!=0.0;
		if(ov instanceof Byte)
			return ((byte)ov)!=0;

		try
		{
			String sVal=getValue(ov, "");
			if (sVal.isEmpty())return bDefault;
			
			return Boolean.parseBoolean(sVal);
		}
		catch(Exception ex)
		{
			return bDefault;
		}
	}
	
	/**
	 * 转换成日期值
	 * @param ov 可以是Date类型，Calendar类型，长整型（时间戳，从1970-1-1以来的毫秒，如果是GMT时间，需要加8小时(28800000ms)），
	 * 其它应为 可格式化日期的字符串类型（包括形如：Mon May 22 2017 21:58:42 GMT+0800 国际标准格式）。<br/>
	 * 注：如果是全数字的字符串，内部将会进行简单判断：<br/>
	 * 如果长度8位或14位，且月/日/时/分/秒的值在有效范围内，将视为yyyyMMdd或yyyyMMddHHmmss格式；<br/>
	 * 否则，将视为时间戳（从1970-1-1以来的毫秒）。<br/>
	 * 如：20170101 视为yyyyMMdd格式的串；<br/>
	 * 20171509 因月份值15不是有效值，则视为时间戳。<br/>
	 * @param dtDefault
	 * @return
	 */
	public static Date getValue(Object ov, Date dtDefault)
	{
		if (ov == null)
			return dtDefault;

		if(ov instanceof Date)
			return (Date)ov;
		if(ov instanceof Calendar)
			return ((Calendar)ov).getTime();
		if(ov instanceof Long)
			return new Date((long)ov);
		if(ov instanceof BigInteger)
			return new Date(((BigInteger)ov).longValue());
		if(ov instanceof BigDecimal)
			return new Date(((BigDecimal)ov).longValue());
		try{
			Date dt = DateUtil.stringToDate(ov.toString());
			if(dt==null)dt=dtDefault;
			return dt;
		}
		catch(Exception ex)
		{
			return dtDefault;
		}
	}

	/**
	 * 对URL格式字符串内容进行UTF-8的URL编码（即/符号不会编码）
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String UriPartEncode(String url) throws Exception
	{
		String[] sPart=url.split("/");
		for(int ix=0;ix<sPart.length;++ix){
			sPart[ix]=URLEncoder.encode(sPart[ix], "utf-8");
		}
		
		return String.join("/", sPart);
	}
	
	/** 
   * 把Map<String,Object>处理成实体类
   * @param clazz     想要的实体类 
   * @param map       包含信息的Map对象 
   * @author wushanghai
   * @return
   */
  @SuppressWarnings("unchecked")
  public static Object mapToObject(Class<?> clazz, Map<String,Object> map){
      if(null == map){
          return null;
      }
      Object entity  = null;
      try {
      	entity = clazz.newInstance();
      } catch (InstantiationException e1) {
          e1.printStackTrace();
      } catch (IllegalAccessException e1) {
          e1.printStackTrace();
      } 
      Method[] methods = clazz.getMethods();
      //存放methods和column对应关系，该关系来自于实体类的 @Column配置
      Map<String, Object> fieldHasColumnAnnoMap = new LinkedHashMap<String, Object>();
      //获取 @Column注解字段
	    Annotation[] annotations = null;
	    for(Method method:methods){
	    	annotations = method.getAnnotations();
	    	for(Annotation an : annotations){
          if(an instanceof Column){
              Column column = (Column)an;
              fieldHasColumnAnnoMap.put(column.name(),method);
          }
	    	}
	    }
	    for (Map.Entry<String, Object> fieldHasColumnAnno : fieldHasColumnAnnoMap.entrySet()){
	    	String dbName = fieldHasColumnAnno.getKey();
	    	Method method = (Method)fieldHasColumnAnno.getValue();
	    	if(method == null){
	    		continue;
	    	}
	    	String getterName = method.getName();
	    	//获取setter方法名称
	    	String setterName = getterName.replaceFirst("g", "s");
	    	Class<?> methodClass = method.getReturnType();
	    	//真正取得set方法
        Method setMethod = null;
	    	try {
          if(CheckUtil.isHaveSuchMethod(clazz, setterName)){
              if(methodClass == String.class){
                  setMethod = clazz.getMethod(setterName, methodClass);
                  setMethod.invoke(entity, map.get(dbName)!=null?String.valueOf(map.get(dbName)):"");//为其赋值
              }else if(methodClass == Integer.class || methodClass == int.class){
                  setMethod = clazz.getMethod(setterName, methodClass);
                  setMethod.invoke(entity, Integer.parseInt(String.valueOf(map.get(dbName))));//为其赋值
              }else if(methodClass == Boolean.class || methodClass == boolean.class){
                  setMethod = clazz.getMethod(setterName, methodClass);
                  setMethod.invoke(entity, Boolean.getBoolean(String.valueOf(map.get(dbName))));//为其赋值
              }else if(methodClass == Short.class || methodClass == short.class){
                  setMethod = clazz.getMethod(setterName, methodClass);
                  setMethod.invoke(entity, Short.parseShort(String.valueOf(map.get(dbName))));//为其赋值
              }else if(methodClass == Long.class || methodClass == long.class){
                  setMethod = clazz.getMethod(setterName, methodClass);
                  setMethod.invoke(entity, Long.parseLong(String.valueOf(map.get(dbName))));//为其赋值
              }else if(methodClass == Double.class || methodClass == double.class){
                  setMethod = clazz.getMethod(setterName, methodClass);
                  setMethod.invoke(entity, Double.parseDouble(String.valueOf(map.get(dbName))));//为其赋值
              }else if(methodClass == Float.class || methodClass == float.class){
                  setMethod = clazz.getMethod(setterName, methodClass);
                  setMethod.invoke(entity, Float.parseFloat(String.valueOf(map.get(dbName))));//为其赋值
              }else if(methodClass == BigInteger.class ){
                  setMethod = clazz.getMethod(setterName, methodClass);
                  setMethod.invoke(entity, BigInteger.valueOf(Long.parseLong(String.valueOf(map.get(dbName)))));//为其赋值
              }else if(methodClass == BigDecimal.class){
                  setMethod = clazz.getMethod(setterName, methodClass);
                  setMethod.invoke(entity, BigDecimal.valueOf(Long.parseLong(String.valueOf(map.get(dbName)))));//为其赋值
              }else if(methodClass == Date.class){
                  setMethod = clazz.getMethod(setterName, methodClass);
                  if(map.get(dbName).getClass() == java.sql.Date.class){
                      setMethod.invoke(entity, new Date(((java.sql.Date)map.get(dbName)).getTime()));//为其赋值
                  }else if(map.get(dbName).getClass() == java.sql.Time.class){
                      setMethod.invoke(entity, new Date(((java.sql.Time)map.get(dbName)).getTime()));//为其赋值
                  }else if(map.get(dbName).getClass() == java.sql.Timestamp.class){
                      setMethod.invoke(entity, new Date(((java.sql.Timestamp)map.get(dbName)).getTime()));//为其赋值
                  }
              }
          }  
	      } catch (SecurityException e) {
	          e.printStackTrace();
	      } catch (NoSuchMethodException e) {
	          e.printStackTrace();
	      }   catch (IllegalArgumentException e) {
	          e.printStackTrace();
	      } catch (IllegalAccessException e) {
	          e.printStackTrace();  
	      } catch (InvocationTargetException e) {
	          e.printStackTrace();
	      }
	    }
      return entity;
  }  
  
}