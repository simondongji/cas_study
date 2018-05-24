package com.southgis.ibase.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public final class PropertiesUtil
{
	/**
	 * 获取主应用“mainWeb”的部署物理路径，
	 * 依赖mainWeb/WEB-INF/web.xml（或pubWeb，如果两者都有配置，优先使用mainWeb内容）中配置的webAppRootKey初始化参数。
	 * 返回格式(没有后缀/)：
   *  Windows系统：E:/tomcat/webapps/mainWeb
   *  Linux系统：/usr/tomcat/webapps/mainWeb
	 * @return
	 */
	public static String getMainWebRoot()
	{
		String appMainRoot=System.getProperty("appMain.root");
		if(CheckUtil.isNullorEmpty(appMainRoot))
			appMainRoot=System.getProperty("appPub.root");
		
		if(!CheckUtil.isNullorEmpty(appMainRoot))
			return appMainRoot.replace('\\', '/');
		return "";
	}
	
	//public static String getWorkflowWebRoot()
	//{
	//	return System.getProperty("appWfs.root").replace('\\', '/');
	//}
	
	//public static String getFormWebRoot()
	//{
	//	return System.getProperty("appForm.root").replace('\\', '/');
	//}
	
	/**
	 * 从当前应用目录下的config.properties文件中读取配置项
	 * @param key
	 * @return
	 */
	public static String getValue(String key)
	{
		try{
			Properties prop = PropertiesUtil.getProperties("/config.properties", null);
			return prop.getProperty(key);
		}catch(Exception ex){
			return null;
		}
	}
	
  /**从指定位置读取配置文件
   * @author frj
   * @throws IOException
   * @param path 文件绝对路径。
   * */
  public static Properties getProperties(String path) throws IOException
  {
    Properties pps = new Properties();
    InputStream in = new BufferedInputStream(new FileInputStream(path));
    pps.load(in);
    in.close();
    return pps;
  }
  
  /**从当前Web目录或jar包中读取配置文件，文件不存在会抛出异常。
   * @author dennis
   * @throws IOException
   * @param path 配置文件的相对路径。以/开头时，表示基于classpath，否则表示基于clsObj类所在目录。
   * @param clsObj 要加载配置文件所参照的类对象。配置文件查找方式有以下组合情况：
   * path以/开头，clsObj==null：优先基于web目录下的classpath查找
   * path以/开头，clsObj!=null：优先基于web目录下的classpath查找，
   *       不存在则基于clsObj所在包的classPath
   * path不以/开头，clsObj==null：[无意义]
   * path不以/开头，clsObj!=null：相对于clsObj所在目录
   * */
  public static Properties getProperties(String path, Class<?> clsObj) throws IOException
  {
    Properties pps = new Properties();
    InputStream stm=null;
    try{
  	if(clsObj==null)
  		stm=PropertiesUtil.class.getResourceAsStream(path);
  	else
  		stm=clsObj.getResourceAsStream(path);
    if(stm==null)
    	throw new IOException(String.format("%s:file not exists for class",path));
    pps.load(stm);
    }finally{
    	if(stm!=null)
    		stm.close();
    }
    return pps;
  }

  /**
   * 将配置文件中所有配置项的名称复制到Set集合中
   * @author frj
   * @param properties
   */
  public static Set<String> getKeys(Properties properties)
  {
    Enumeration<?> enu = properties.propertyNames();
    Set<String> keySet = new HashSet<String>();
    while (enu.hasMoreElements()) {
      String key = (String) enu.nextElement();
      keySet.add(key);
    }
    return keySet;
  }

  /**将配置文件中所有配置项的值复制到Set集合中
   * @author frj
   * @param properties
   */
  public static Set<String> getValues(Properties properties)
  {
    Enumeration<Object> enu = properties.elements();
    Set<String> valueSet = new HashSet<String>();
    while (enu.hasMoreElements()) {
      String value = enu.nextElement().toString();
      valueSet.add(value);
    }
    return valueSet;
  }

  /**将配置文件中所有配置项复制到Set集合中
   * @author frj
   * @param properties
   */
  public static Set<Entry<Object, Object>> getKeysAndValues(
      Properties properties)
  {
    Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
    Set<Entry<Object, Object>> keyValueSet = new HashSet<Entry<Object, Object>>();
    while (it.hasNext()) {
      Entry<Object, Object> entry = it.next();
      keyValueSet.add(entry);
    }
    return keyValueSet;
  }

}
