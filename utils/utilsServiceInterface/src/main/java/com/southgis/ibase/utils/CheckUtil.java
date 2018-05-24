package com.southgis.ibase.utils;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * 值格式检查，以及合法性检查
 * */
public final class CheckUtil
{
	/**
	 * 检查字符串是否为邮箱。
	 * 
	 * @author frj
	 * @param sAddr
	 * @return
	 */
	public static boolean isEmail(String sAddr)
	{
		Pattern pattern = Pattern
				.compile("^([a-z0-9A-Z]+[-|_\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		if (sAddr != null)
		{
			return pattern.matcher(sAddr).matches();
		}
		return false;
	}

	/**
	 * 判断可遍历集合是否包含指定元素
	 * @param list
	 * @param item
	 * @return
	 */
	public static <T> boolean contains(Enumeration<T> list, T item)
	{
		if(list==null) return false;
		while(list.hasMoreElements()){
			Object oVal=list.nextElement();
			if(oVal==item){//对象引用相同，或同为null
				return true;
			}else if(oVal!=null && oVal.equals(item)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断数组是否包含指定元素
	 * @param arr
	 * @param item
	 * @return
	 */
	public static <T> boolean contains(T[] arr, T item)
	{
		if(arr==null) return false;
		for(T oVal : arr){
			if(oVal==item){//对象引用相同，或同为null
				return true;
			}else if(oVal!=null && oVal.equals(item)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 找出字符串数组内的指定元素所在索引位置
	 * @param list
	 * @param item
	 * @return
	 */
	public static int indexOf(String[] list, String item)
	{
		if(list==null || list.length==0) return -1;
		for(int ix=0;ix<list.length;++ix){
			if(list[ix]==null){
				if(item==null) return ix;
			}
			else if((item==list[ix]) || list[ix].equals(item))
				return ix;
		}
		return -1;
	}
	
	/**
	 * 检果是否为空或""字符串
	 * 
	 * @param sValue
	 * @return
	 */
	public static boolean isNullorEmpty(String sValue)
	{
		return sValue == null || sValue.isEmpty();
	}
	
	/**
	 * 判断是否有效ID串。不为空，且不以#开头。
	 * @param id 要判断的标识值。
	 * @return
	 */
	public static boolean isValidId(String id)
	{
		if(id==null || id.isEmpty()) return false;
		if(id.charAt(0)=='#') return false;
		return true;
	}

	/**
	 * 判断是否为中文字符
	 * 
	 * @param chv
	 * @return
	 */
	public static boolean isCNumber(char chv)
	{
		// 由于汉字0可能会用到两个编码中的一个，需要全部判断
		return (chv == '○' || chv == '一' || chv == '二' || chv == '三'
				|| chv == '四' || chv == '五' || chv == '六' || chv == '七'
				|| chv == '八' || chv == '九' || chv == '〇');
	}
	
	/**
	 * 判断指定字符串是否为全数字
	 * @param val
	 * @return
	 */
	public static boolean isDigit(String val)
	{
		if(val==null || val.isEmpty()) return false;
		for(char chx:val.toCharArray()){
			if(!Character.isDigit(chx)) return false;
		}
		return true;
	}
	
	/**
	 * 收集时间差，向控件台输出时长
	 * @param start 开始时间点（等于0表示第一次调用，不输出）。
	 * @param title 要显示时间的标题文本（start=0时，忽略）。
	 * 控件台输出内容为："XXXX用时：yyyms"。XXXX为title内容，yyy为当前时间点与start时间点的差值
	 * @return 下一次计算的开始时间点（做为下一次调用的start参数值）
	 */
	public static long checkTime(long start,String title)
	{
		//invokeName 所调用方法，用于层次输出(同一个方法内名称相同，最好是当前方法名)
		long next=System.currentTimeMillis();
		if(start>0){
			String invokeName="?";
			StackTraceElement stack[] = Thread.currentThread().getStackTrace();
			//0=getStackTrace,1=当前方法,2=调用当前方法的方法
			invokeName=stack[2].getMethodName();
			System.err.println(String.format("[%s]%s用时：%d ms", invokeName, title,(next-start)));
		}
		return next;
	}
	
	/** 
   * 判断某个类里是否有某个方法 
   * @param clazz 类
   * @param methodName 方法名
   * @author wushanghai
   * @return
   */
  public static boolean isHaveSuchMethod(Class<?> clazz, String methodName){
      Method[] methodArray = clazz.getMethods();
      boolean result = false;
      if(null != methodArray){
          for(int i = 0;i < methodArray.length;i++){
              if(methodArray[i].getName().equals(methodName)){
                  result = true;
                  break;
              }
          }
      }
      return result;
  }
	
}
