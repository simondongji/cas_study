package com.southgis.ibase.utils.fake;

/**
 * 在{@code InputStreamFake}和{@code OutputStreamFake}伪对象中，
 * 期望被测方法是否会关闭对象的枚举类。
 * 
 * @author 黄科天
 */
public enum IsClosedAction
{
  /**
   * 期待被测方法关闭伪对象.
   */
  Close,
  
  /**
   * 期待被测方法不会关闭伪对象.
   */
  NotClose
}
