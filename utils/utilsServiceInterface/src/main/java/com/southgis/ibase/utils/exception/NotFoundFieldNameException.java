package com.southgis.ibase.utils.exception;

/**
 * 设置数据库中属性时，发现没有找到对应的属性信息时抛出.
 * 
 * @author 黄科天
 *
 */
public class NotFoundFieldNameException extends RuntimeException
{
  // region 静态字段
  
  /**
   * 用于序列化的UID数值.
   */
  private static final long serialVersionUID = -2388263019649687091L;
  
  // endregion
  
  // region 构造函数
  
  /**
   * 构造函数.
   * 
   * @param msg 异常消息
   */
  public NotFoundFieldNameException(String msg)
  {
  	//添加err标签，让客户端更准确找到错误信息
    super(msg);
  }

  // endregion
}
