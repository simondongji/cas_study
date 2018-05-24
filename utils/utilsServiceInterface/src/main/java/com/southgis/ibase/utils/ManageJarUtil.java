package com.southgis.ibase.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;


import com.southgis.ibase.utils.exception.ServiceException;

/**
 * 
 *管理jar
 */
public class ManageJarUtil
{
	
	/**
	 * 保存文件到本地
	 * @param filePath 路径
	 * @param inputStream 流
	 * @param fileName 文件名
	 */
	public static void saveJar(String filePath,InputStream inputStream,String fileName){
		OutputStream os = null; 
		try {
       // 2、保存到临时文件
       // 1K的数据缓冲
       byte[] bs = new byte[1024];
       // 读取到的数据长度
       int len;
       // 输出的文件流保存到本地文件
       File tempFile = new File(filePath);
       if (!tempFile.exists()) {
           tempFile.mkdirs();
       }
       os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
       // 开始读取
       while ((len = inputStream.read(bs)) != -1) {
           os.write(bs, 0, len);
       }

   } catch (IOException e) {
       e.printStackTrace();
   } catch (Exception e) {
       e.printStackTrace();
   } finally {
       // 完毕，关闭所有链接
       try {
           os.close();
           inputStream.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
	}
	
	public static List<String> getAllJarName(List<String> result,String path,String sub){
		//List<String> result=new ArrayList<>();
		
		File subFile=new File(sub);
		sub=subFile.getAbsolutePath();
		File dir = new File(path);
		File[] files = dir.listFiles();
		if (files == null){
			return result;
		}
		
		for (int i = 0; i < files.length; i++) {
		if(files[i].isDirectory()){
			getAllJarName(result,files[i].getAbsolutePath(),sub);
		}else{
			String strFileName = files[i].getAbsolutePath();
			//strFileName=strFileName.substring(strFileName.lastIndexOf("\\")+1);
			strFileName=strFileName.substring(sub.length()+1);
			strFileName=strFileName.replaceAll("\\\\", "/");
			result.add(strFileName);
		}
			
		}
		return result;
	}	
	
	@SuppressWarnings("resource")
	public static List<String> getPackNameAndClassName(String path) throws Exception{
		List<String> result=new ArrayList<>();
		JarFile jarFile = new JarFile(path);
		 Enumeration<JarEntry> entries = jarFile.entries();
		 Pattern unpackRegex=Pattern.compile(".*");
		 while (entries.hasMoreElements()) {
       final JarEntry entry = entries.nextElement();
       if (!entry.isDirectory() && unpackRegex.matcher(entry.getName()).matches() && entry.getName().endsWith(".class")) {
      	 String pcNmae=entry.getName().substring(0,entry.getName().lastIndexOf("."));
      	 pcNmae= pcNmae.replaceAll("/", ".");
      	 result.add(pcNmae);
       }
   }
		return result;
	}
	
	@SuppressWarnings("resource")
	public static List<String> getMethod(String path ,String className) {
		List<String> result=new ArrayList<String>();
		
		if(!path.startsWith("file:")){
			path="file:"+path;
		}
		
		
		Class<?> extendClass=null;
		try
		{
			extendClass = loadClass(path,className);
		}
		catch(Throwable  e)
		{
			e.printStackTrace();
			throw new ServiceException("该类下面没有，对应的可执行方法！");
		}
		Method[] methods=extendClass.getMethods();
		for (Method method : methods) {
			String methodName=method.getName(); 
			if(!methodName.equals("wait") 
					&& !methodName.equals("equals")
					&& !methodName.equals("hashCode")
					&& !methodName.equals("toString")
					&& !methodName.equals("getClass")
					&& !methodName.equals("notify")
					&& !methodName.equals("notifyAll")
					){
				  methodName=methodName+"(";
						Parameter[] ps=method.getParameters();
						int pflag=0;
						for(Parameter p:ps){
							String type=p.getType().toString();
							String[] types=type.split("\\.");
							type=types[types.length-1];
							if(pflag==0){
								methodName=methodName+type+" "+p.getName();
								pflag++;
							}else{
								methodName=methodName+","+type+" "+p.getName();
							}
						}
						methodName=methodName+")";
						result.add(methodName);
			}
		}
		return result;
	}
	
	
	public static Class<?> loadClass(String path,String classnamme) throws Exception{
		
		
		Class<?> extendClass=null;
			URL url1 = new URL(path);
			URLClassLoader classLoader = new URLClassLoader(new URL[] { url1 },
			    Thread.currentThread().getContextClassLoader());
		/*	URLClassLoader classLoader = new URLClassLoader(new URL[] { url1 },
					ContextLoader.getCurrentWebApplicationContext().getClassLoader());*/
			 extendClass = classLoader.loadClass(classnamme);
			classLoader.close();
		return extendClass;
		}
	
}
