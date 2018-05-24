package com.southgis.ibase.utils.exception;

/**
 * 当设置数据库属性时，发现类型不匹配时抛出的异常.
 * 
 * @author 黄科天
 *
 */
public class NotMatchedFieldTypeException extends RuntimeException
{
  // region 静态字段
  
  /**
   * 用于序列化的UID数值.
   */
  private static final long serialVersionUID = 5788738142442564818L;

  // endregion
  
  // region 构造函数
  
  /**
   * 构造函数.
   * 
   * @param msg 异常消息
   */
  public NotMatchedFieldTypeException(String msg)
  {
  	//添加err标签，让客户端更准确找到错误信息
    super(msg);
  }
  
  // endregion
}
