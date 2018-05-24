package com.southgis.ibase.utils.fake;

/**
 * 输入输出流伪对象在最后一次调用{@code read(byte[])}或者{@code write(byte[], int, int)}方法时的动作.
 * 
 * @author 黄科天
 *
 */
public enum StreamFakeLastTimeAction
{
  /**
   * 最后一次调用方法时抛出异常.
   */
  ThrowsException,

  /**
   * 最后一次调用方法时返回-1.
   */
  ReturnNegativeOne
}
