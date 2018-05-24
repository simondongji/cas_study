package com.southgis.ibase.utils.fake;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * {@code InputStream}的模拟对象.
 * 
 * 用于在单元测试中模拟{@code InputStream}对象。
 * 
 * @author 黄科天
 */
public class InputStreamFake extends InputStream
{
  /**
   * 构造函数.
   * 
   * 调用{@code maxTimes}此{@code read(byte[] buf)}方法后，第{@code maxTimes + 1}此调用
   * {@code read(byte[] buf)}方法时采取{@code action}行为。并且最终被测方法需要关闭此输入流。
   * 
   * @param maxTimes
   *          调用{@code read(byte[] buf)}方法的次数，请参考{@code getMaxTimes()}注释。
   * @param action
   *          最后调用{@code read(byte[] buf)}方法时的动作。
   */
  public InputStreamFake(int maxTimes, StreamFakeLastTimeAction action)
  {
    this.mMaxTimes = maxTimes;
    this.mAction = action;
    this.mIsClosedAction = IsClosedAction.Close;
  }

  /**
   * 构造函数.
   * 
   * @param maxTimes
   *          调用{@code read(byte[] buf)}方法的次数,请参考{@code getMaxTimes()}的注释。
   * @param action
   *          最后调用{@code read(byte[] buf)}方法时的动作。
   * @param isClosedAction
   *          被测方法执行完毕后，被测方法是否应该关闭此输入流。
   */
  public InputStreamFake(int maxTimes, StreamFakeLastTimeAction action, IsClosedAction isClosedAction)
  {
    this.mMaxTimes = maxTimes;
    this.mAction = action;
    this.mIsClosedAction = isClosedAction;
  }

  /**
   * 用于保存调用{@code read(byte[] buf)}方法的次数.
   */
  private final int mMaxTimes;

  /**
   * 需要调用{@code read(byte[] buf)}方法的次数.
   * 
   * 如果{@code getMaxTimes() == 2}，则第三次调用{@code read(byte[] buf)}方法时，
   * {@code read(byte[] buf)}会返回 -1（如果
   * {@code action == StreamFakeLastTimeAction.ReturnNegativeOne}，或者抛出
   * {@code IOException}异常（如果
   * {@code action == StreamFakeLastTimeAction.ThrowsException}）。
   * 
   * 注意，实际调用{@code read(byte[] buf)}方法的次数是{@code getMaxTimes() + 1}，因为
   * 还需要多调用一次以确定最终结果。
   * 
   * 注意，此方法并不包括{@code read()}方法的调用次数。
   * 
   * @return 期望调用{@code read(byte[] buf)}方法的次数。
   */
  public int getMaxTimes()
  {
    return this.mMaxTimes;
  }

  /**
   * 用于保存最后一次调用{@code read(byte[] buf)}方法时的动作.
   */
  private final StreamFakeLastTimeAction mAction;

  /**
   * 获取最后一次调用{@code read(byte[])}方法时的动作.
   * 
   * @return 表示最后一次调用{@code read(byte[])}方法时动作的{@code StreamFakeTimeAction}枚举值。
   */
  public StreamFakeLastTimeAction getAction()
  {
    return this.mAction;
  }

  /**
   * 表示所模拟的真实环境中，是否需要关闭{@code InputStream}.
   */
  private final IsClosedAction mIsClosedAction;

  /**
   * 获取所模拟的真实环境中，是否需要关闭{@code InputStream}.
   * 
   * @return 表示是否需要关闭{@code InputStream}的{@code IsClosedAction}枚举值。
   */
  public IsClosedAction getIsClosedAction()
  {
    return this.mIsClosedAction;
  }

  /**
   * 输入流的内容.
   */
  private final ArrayList<Byte> mContent = new ArrayList<Byte>();

  /**
   * 获取输入流的内容.
   * 
   * @return 表示输入流内容的字节数组。
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
   * 当前已经调用{@code read(byte[] buf)}方法的次数.
   */
  private int mCurrentTimes = 0;

  /**
   * 当前已经调用{@code read(byte[] buf)}方法的次数.
   * 
   * @return 表示当前已经调用{@code read(byte[] buf)}方法次数的整数。
   */
  public int getCurrentTimes()
  {
    return this.mCurrentTimes;
  }

  /**
   * 此输入流在测试中是否已经关闭.
   */
  private boolean mIsClose = false;

  /**
   * 此输入流在测试中是否已经关闭.
   * 
   * @return 表示此输入流是否已经关闭的boolean值。
   */
  public boolean isClose()
  {
    return this.mIsClose;
  }

  /**
   * 如果{@code getAction() == StreamFakeLastTimeAction.ThrowsException}，则
   * {@code read(byte[])}最后一次调用中抛出异常。
   */
  private final IOException mIOException = new IOException();

  /**
   * 如果{@code getAction() == StreamFakeLastTimeAction.ThrowsException}， 获取
   * {@code read(byte[])}最后一次调用中抛出的异常.
   * 
   * @return 抛出的异常对象，此方法不可能返回{@code null}。
   */
  public IOException getIOException()
  {
    return this.mIOException;
  }

  /**
   * 验证此输入流.
   * 
   * 如果此输入流模拟对象期望被测方法最终关闭此输入流，那么如果测试中被测方法确实调用了{@code close()}方法，此方法返回
   * {@code true}，否则返回{@code false}。
   * 
   * 相反的，如果此输入流模拟对象并不期望被测方法最终关闭此输入流，那么如果测试中被测方法确实没有调用{@code close()}方法，此方法返回
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
  public int read(byte[] buf) throws IOException
  {
    if (this.mCurrentTimes == this.mMaxTimes) {
      ++this.mCurrentTimes;
      if (this.mAction == StreamFakeLastTimeAction.ThrowsException) {
        throw this.mIOException;
      }
      return -1;
    }
    ++this.mCurrentTimes;
    int result = (int) (buf.length * Math.random());
    for (int i = 0; i < result; ++i) {
      byte b = (byte) (Byte.MAX_VALUE * Math.random());
      this.mContent.add(b);
      buf[i] = b;
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close()
  {
    this.mIsClose = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException
  {
    byte b = (byte) (Byte.MAX_VALUE * Math.random());
    this.mContent.add(b);
    return b;
  }
}
