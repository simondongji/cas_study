package com.southgis.ibase.utils.exception;

/**
 * 当无法找到指定记录时抛出的异常.
 */
public class NotFoundRidException extends RuntimeException
{
  /**
   * 用于序列化时判断类型版本的类型序列化版本UID.
   */
  private static final long serialVersionUID = 7794714969392533048L;

  /**
   * 表名.
   */
  private final String mTableName;

  /**
   * 记录RID值.
   */
  private final String mRid;

  /**
   * 构造函数.
   * 
   * @param rid
   *          记录RID的值。
   * @param tableName
   *          记录所在表名。
   * @throws ArgumentNullException
   *           当{@code rid}或者{@code tableName}为{@code null}时抛出。
   */
  public NotFoundRidException(String rid, String tableName)
  {
  	//添加err标签，让客户端更准确找到错误信息
    super("无法找到记录: 表名为" + (tableName == null ? "" : tableName)
    		+ "，记录RID为" + (rid == null ? "" : rid));
    this.mRid = rid;
    this.mTableName = tableName;
  }

  /**
   * 获取无法找到记录的表名.
   * 
   * @return 表名，这个方法不会返回{@code null}。
   */
  public String getTableName()
  {
    return this.mTableName;
  }

  /**
   * 获取无法找到记录的RID值.
   * 
   * @return RID值，这个方法不会返回{@code null}。
   */
  public String getRid()
  {
    return this.mRid;
  }
}
