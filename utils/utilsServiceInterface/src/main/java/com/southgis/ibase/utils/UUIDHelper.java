package com.southgis.ibase.utils;

import java.util.Base64;
import java.util.UUID;

/**
 * 全局唯一标识值的额外处理，比如获得标准格式的字符串
 * @author dennis
 *
 */
public final class UUIDHelper
{
  /**
   * 获得惟一标识串的字符串表示
   * 
   * @return 标准格式。如：12345678-1234-1234-1234-123456789ABC
   */
  public static String getString()
  {
    return getString(UUID.randomUUID());
  }
  /**
   * 获得惟一标识串的字符串表示，以Base64表示，压缩了长度（去除末尾对齐的两个=）
   * 
   * @return Base64串。如：1sm5ij85SKGN5P743DniMg
   */
  public static String getZipString()
  {
  	return getZipString(UUID.randomUUID());
  }
  
  /**
   * 将UUID转换成字节数组：[0]=msb高8位，...[7]=msb低8位，[8]=lsb高8位，...[15]=lsb低8位
   * @param uuid
   * @return
   */
  public static byte[] toBytes(UUID uuid)
  {
    long  msb = uuid.getMostSignificantBits();
    long  lsb = uuid.getLeastSignificantBits();
    byte[] buffer =  new byte[16];
    for(int i=0; i<8; i++) {
      buffer[i] = (byte)(msb >>> (8*(7-i)));
    }
    for(int i=0; i<8; i++) {
      buffer[i+8] = (byte)(lsb >>> (8*(7-i)));
    }
    return buffer;
  }
  /**
   * 将字节数组转换成UUID
   * @param byBuf 通过toBytes转换的数组格式
   * @return
   */
  public static UUID toUUID(byte[] byBuf)
  {
     long msb = 0;
     long lsb = 0;
     for(int i=0; i<8; i++)
       msb = (msb<<8) | (byBuf[i]&0xff);
     for(int i=8; i<16; i++)
       lsb = (lsb<<8) | (byBuf[i]&0xff);
     return new  UUID(msb, lsb);
  }
  /**
   * 获得惟一标识串的字符串表示
   * @param uuid
   * @return 标准格式。如：12345678-1234-1234-1234-123456789ABC
   */
  public static String getString(UUID uuid)
  {
    return uuid.toString();//.replace("-", "");
  }
  /**
   * 获得惟一标识串的字符串表示，以Base64表示，压缩了长度（去除末尾对齐的两个=）
   * 
   * @return Base64串。如：1sm5ij85SKGN5P743DniMg
   */
  public static String getZipString(UUID uuid)
  {
  	byte[] byBuf=toBytes(uuid);
  	byBuf=Base64.getEncoder().encode(byBuf);
  	return new String(byBuf,0,byBuf.length-2);
  }
}
