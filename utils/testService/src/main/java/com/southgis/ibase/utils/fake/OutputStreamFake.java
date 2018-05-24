package com.southgis.ibase.utils.fake;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * {@code OutputStream}的模拟对象.
 * 
 * 用于在单元测试中模拟{@code OutputStream}对象。
 * 
 * @author 黄科天
 */
public class OutputStreamFake extends OutputStream
{
  /**
   * 构造函数.
   * 
   * 调用{@code Integer.MAX_VALUE}此{@code write(byte buf[], int off, int len)}
   * 方法后，第 {@code Integer.MAX_VALUE + 1}此调用
   * {@code write(byte buf[], int off, int len)} 方法时将抛出异常。并且最终被测方法需要关闭此输入流。
   */
  public OutputStreamFake()
  {
    this.mMaxTimes = Integer.MAX_VALUE;
    this.mIsClosedAction = IsClosedAction.Close;
  }

  /**
   * 构造函数.
   * 
   * 调用{@code Integer.MAX_VALUE}此{@code write(byte buf[], int off, int len)}
   * 方法后，第 {@code Integer.MAX_VALUE + 1}此调用
   * {@code write(byte buf[], int off, int len)} 方法时将抛出异常。
   * 
   * @param isClosedAction
   *          被测方法在{@code write(byte buf[], int off, int len)}方法后是否关闭此对象。
   */
  public OutputStreamFake(IsClosedAction isClosedAction)
  {
    this.mMaxTimes = Integer.MAX_VALUE;
    this.mIsClosedAction = isClosedAction;
  }

  /**
   * 构造函数.
   * 
   * @param maxTimes
   *          调用{@code write(byte buf[], int off, int len)}方法的次数，请参考
   *          {@code getMaxTimes()}注释。
   * @param isClosedAction
   *          被测方法在{@code write(byte buf[], int off, int len)}方法后是否关闭此对象。
   */
  public OutputStreamFake(int maxTimes, IsClosedAction isClosedAction)
  {
    this.mMaxTimes = maxTimes;
    this.mIsClosedAction = isClosedAction;
  }

  /**
   * 用于保存调用{@code write(byte buf[], int off, int len)}方法的次数.
   */
  private final int mMaxTimes;

  /**
   * 需要调用{@code write(byte buf[], int off, int len)}方法的次数.
   * 
   * 如果{@code getMaxTimes() == 2}，则第三次调用
   * {@code write(byte buf[], int off, int len)}方法时，
   * {@code write(byte buf[], int off, int len)}会抛出 {@code IOException}异常。
   * 
   * 注意，实际调用{@code write(byte buf[], int off, int len)}方法的次数是
   * {@code getMaxTimes() + 1}，因为 还需要多调用一次以确定最终结果。
   * 
   * 注意，此方法并不包括{@code write(int b)}方法的调用次数。
   * 
   * @return 期望调用{@code write(byte buf[], int off, int len)}方法的次数。
   */
  public int getMaxTimes()
  {
    return this.mMaxTimes;
  }

  /**
   * 表示所模拟的真实环境中，是否需要关闭{@code OutputStream}.
   */
  private final IsClosedAction mIsClosedAction;

  /**
   * 获取所模拟的真实环境中，是否需要关闭{@code OutputStream}.
   * 
   * @return 表示是否需要关闭{@code OutputStream}的{@code IsClosedAction}枚举值。
   */
  public IsClosedAction getIsClosedAction()
  {
    return this.mIsClosedAction;
  }

  /**
   * 写入输出流的内容.
   */
  private final ArrayList<Byte> mContent = new ArrayList<Byte>();

  /**
   * 获取写入输出流的内容.
   * 
   * @return 表示写入输出流内容的字节数组。
   */
  public byte[] getContent()
  {
    int size = this.mContent.size();
    byte[] result = new byte[size];
    for (int i = 0; i < size; ++i) {
      result[i] = this.mContent.get(i).byteValue();
    }
    return result;
  }

  /**
   * 当前已经调用{@code write(byte buf[], int off, int len)}方法的次数.
   */
  private int mCurrentTimes = 0;

  /**
   * 当前已经调用{@code write(byte buf[], int off, int len)}方法的次数.
   * 
   * @return 表示当前已经调用{@code write(byte buf[], int off, int len)}方法次数的整数。
   */
  public int getCurrentTimes()
  {
    return this.mCurrentTimes;
  }

  /**
   * 此输出流在测试中是否已经关闭.
   */
  private boolean mIsClose = false;

  /**
   * 此输出流在测试中是否已经关闭.
   * 
   * @return 表示此输出流是否已经关闭的boolean值。
   */
  public boolean isClose()
  {
    return this.mIsClose;
  }

  /**
   * {@code write(byte buf[], int off, int len)}最后一次调用中抛出异常。
   */
  private final IOException mIOException = new IOException();

  /**
   * 获取{@code write(byte buf[], int off, int len)}最后一次调用中抛出的异常.
   * 
   * @return 抛出的异常对象，此方法不可能返回{@code null}。
   */
  public IOException getIOException()
  {
    return this.mIOException;
  }

  /**
   * 验证此输出流.
   * 
   * 如果此输出流模拟对象期望被测方法最终关闭此输出流，那么如果测试中被测方法确实调用了{@code close()}方法，此方法返回
   * {@code true}，否则返回{@code false}。
   * 
   * 相反的，如果此输出流模拟对象并不期望被测方法最终关闭此输出流，那么如果测试中被测方法确实没有调用{@code close()}方法，此方法返回
   * {@code true}，否则，返回{@code false}。
   * 
   * @return 验证成功与否的boolean值。
   */
  public boolean verify()
  {
    if (this.mIsClosedAction == IsClosedAction.Close) {
      return this.mIsClose;
    }
    else {
      return !this.mIsClose;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(byte buf[], int off, int len) throws IOException
  {
    if (this.mCurrentTimes == this.mMaxTimes) {
      ++this.mCurrentTimes;
      throw this.mIOException;
    }
    ++this.mCurrentTimes;
    for (int i = off; i < off + len; ++i) {
      mContent.add(buf[i]);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException
  {
    this.mIsClose = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(int b) throws IOException
  {
    byte bt = (byte)b;
    this.mContent.add(bt);
  }
}
