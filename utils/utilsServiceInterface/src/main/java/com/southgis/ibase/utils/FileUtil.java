package com.southgis.ibase.utils;

import java.io.File;

public final class FileUtil {
	/**
	 * 协议名与服务器地址的分隔符(://)最大可能位置，查找://的位置如果超过此常量值，
	 * 则表示是查询参数中的内容，不算是URL地址中的协议名分隔符。
	 */
	public static final int SCHEME_SPLIT_POS=8;
	/**
	 * 获取文件名的扩展名部分，包含点(.)。
	 * 如传入文件名：“test.jpg”，返回“.jpg”。
	 * @param sFileName
	 * @return
	 */
	public static String getExtend(String sFileName){
		if(CheckUtil.isNullorEmpty(sFileName)) return "";
		int iPos=sFileName.lastIndexOf('.');
		if(iPos<0) return "";
		return sFileName.substring(iPos);
	}

	/**
	 * 获取文件名的名称部分。
	 * 如传入文件名：“test.jpg”，返回“test”。
	 * @param sFileName
	 * @return
	 */
	public static String getFileName(String sFileName)
	{
		if(CheckUtil.isNullorEmpty(sFileName)) return "";
		int iPos=sFileName.lastIndexOf('.');
		if(iPos<0) return sFileName;
		return sFileName.substring(0,iPos);
	}
	/**
	 * 创建目录
	 * LXY
	 * @param destDirName
	 * @return
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {// 判断目录是否存在
			System.out.println("创建目录失败，目标目录已存在！");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
			destDirName = destDirName + File.separator;
		}
		if (dir.mkdirs()) {// 创建目标目录
			System.out.println("创建目录成功！" + destDirName);
			return true;
		} else {
			System.out.println("创建目录失败！");
			return false;
		}
	}
	
	/**
	 * 基于扩展名获得mime类型串（如：image/jpeg）
	 * @param sExt
	 * @return mime类型串，如果sExt为空，返回“application/octet-stream”；如果无法识别，返回application/扩展名；
	 */
	public static String GetMimeFromExt(String sExt)
	{
		String sMime = "application/octet-stream";
		if(CheckUtil.isNullorEmpty(sExt)){
			return sMime;
		}
		
		sExt = sExt.toLowerCase();
		if(sExt.charAt(0)=='.') sExt=sExt.substring(1);
		
		switch(sExt)
		{
		case "jpg":
		case "jpe":
			sMime = "image/jpeg";
			break;
		case "png":
		case "gif":
			sMime="image/"+sExt;
			break;
		case "txt":
			sMime = "text/plain";
			break;
		case "mp3":
			sMime="audio/mpeg";
			break;
		case "ogg":
		case "wav":
			sMime="audio/"+sExt;
			break;
		case "mp4":
		case "webm":
			sMime="video/"+sExt;
			break;
		case "ogv":
			sMime="video/ogg";
			break;
		case "doc":
		case "docx":
			sMime="application/msword";
			break;
		case "xls":
		case "xlsx":
			sMime="application/msexcel";
			break;
		case "ppt":
		case "pptx":
			sMime="application/mspowerpoint";
			break;
		default://pdf、rtf
			if(!sExt.isEmpty())
				sMime="application/"+sExt;
			break;
		}
		return sMime;
	}
  
  /**
   * 判断地址是否为网络地址。以ftp://、http://或https://开头则返回非0值
   * @param path
   * @return path为空返回-1。
   * 以http://开头返回1，以https://开头返回2。
   * 以ftp:// 开头返回10。
   * 其它值返回0
   */
  public static int IsNetPath(String path)
  {
  	if(CheckUtil.isNullorEmpty(path)) return -1;
  	int iPos=path.indexOf("://");
  	if(iPos<0 || iPos>FileUtil.SCHEME_SPLIT_POS) return 0;
  	
  	String sPrefix=path.substring(0,iPos).toLowerCase();
  	if(sPrefix.equals("ftp")) return 10;
  	if(sPrefix.equals("http")) return 1;
  	if(sPrefix.equals("https")) return 2;
  	
  	return 0;
  }
  
  /**
   * 查找URL地址的路径开始位置。如果为null或Empty，返回-1；如果不包含服务器地址，返回0；
   * 否则返回除服务器地址后的第一个路径位置(/)，如果仅服务器地址且无查询参数(?)，返回字符串长度，如果有?则返回?位置。<br/>
   * 比如以下情况都返回23（即服务器地址后的第一个字符位置）：<br/>
   * http://192.168.1.1:8080/mainWeb/work<br/>
   * http://192.168.1.1:8080<br/>
   * http://192.168.1.1:8080?name=test
   * @param url
   * @return
   */
  public static int indexOfPath(String url)
  {
  	if(CheckUtil.isNullorEmpty(url)) return -1;
  	int iPos=url.indexOf("://");
  	if(iPos<0 || iPos>FileUtil.SCHEME_SPLIT_POS) return 0;
  	int iquery=url.indexOf('?');
  	int inx=url.indexOf("/",iPos+3);
  	if(inx<0) inx=iquery;
  	if(inx<0) inx=url.length();
  	return inx;
  }

}
