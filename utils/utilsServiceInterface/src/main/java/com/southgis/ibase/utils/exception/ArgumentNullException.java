package com.southgis.ibase.utils.exception;

/**
 * 当参数为{@code null}时抛出的异常.
 */
public class ArgumentNullException extends IllegalArgumentException
{
  /**
   * 用于序列化时判断类型版本的类型序列化版本UID.
   */
  private static final long serialVersionUID = 6641544969325359857L;

  /**
   * 参数名称.
   */
  private final String mName;

  /**
   * 构造函数.
   * 
   * 根据参数名称构造{@code ArgumentNullException}对象。
   * 
   * @param name
   *          值为{@code null}的参数名称。
   * @throws ArgumentNullException
   *           当参数{@code name}是{@code null}时抛出。
   */
  public ArgumentNullException(String name)
  {
  	//添加err标签，让客户端更准确找到错误信息
    super("参数 "+(name == null ? "" : name)+" 不能为null（或空字符串）");

    this.mName = name;
  }

  /**
   * 获取参数名称.
   * 
   * @return 参数名称。
   */
  public String getName()
  {
    return this.mName;
  }
}
