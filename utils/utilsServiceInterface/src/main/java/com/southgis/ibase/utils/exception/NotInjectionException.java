package com.southgis.ibase.utils.exception;

/**
 * 代码中许多地方要求注入对象。当代码中没有对象注入时抛出此异常
 * 
 * @author 黄科天
 *
 */
public class NotInjectionException extends RuntimeException
{
  // region 静态字段
  
  /**
   * 用于序列化的UID数值.
   */
  private static final long serialVersionUID = 7134736936245812714L;
  
  // endregion
  
  // region 私有成员
  
  /**
   * 保存未注入对象的名称
   */
  private final String mName;
  
  // endregion
  
  // region 构造函数
  
  /**
   * 构造函数.
   * 
   * @param name 未注入对象的名称
   * @throws ArgumentNullException 当{@code name}为{@code null}时抛出。
   */
  public NotInjectionException(String name)
  {
  	//添加err标签，让客户端更准确找到错误信息
    super("未注入对象：" + (name == null ? "" : name));
    this.mName = name;
  }
  
  // endregion
  
  // region 成员方法
  
  /**
   * 获取未注入的对象的名称。
   * 
   * @return 未注入对象的名称。
   */
  public String getName()
  {
    return this.mName;
  }
  
  // endregion
}
