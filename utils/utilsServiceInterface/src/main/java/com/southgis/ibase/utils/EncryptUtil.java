package com.southgis.ibase.utils;

import java.security.MessageDigest;

/**
 * 加解密工具类：摘要加密、对称加解密、非对称加解密
 * 
 * @author HuangLeibing
 *
 */
public final class EncryptUtil
{

	/**
	 * MD5加密
	 * 
	 * @param input 要加密的字符串
	 * @return 如果成功，返回加密串；否则返回null
	 */
	public final static byte[] MD5(String input)
	{
		try
		{
			//MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			return mdInst.digest(input.getBytes("utf-8"));
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * sha1加密
	 * @param input 要加密的字符串
	 * @return 如果成功，返回加密串；否则返回null
	 */
	public final static byte[] Sha1(String input)
	{
		try
		{
			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			return mDigest.digest(input.getBytes("utf-8"));
		}
		catch(Exception e)
		{
			return null;
		}
	}

//	/**
//	 * 对称加密数据
//	 * 
//	 * @param input
//	 * @param sPwd
//	 * @return
//	 */
	/*
	public static byte[] AesEncrypt(byte[] input, String sPwd)
	{
	}
	public static byte[] AesDecrypt(byte[] input, String sPwd)
	{
	}
	*/
	

//	/**
//	 * 非对称加密数据
//	 * 
//	 * @param input
//	 * @param sKey
//	 * @return
//	 */
	/*
	public static byte[] RsaEncrypt(byte[] input, String sKey)
	{
	}
	public static byte[] RsaDecrypt(byte[] input, String sKey)
	{
	}
	*/
}
