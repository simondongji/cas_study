package com.southgis.ibase.utils;

import java.io.Serializable;

/**
 * 用于保存标识-文本对的类，也包含扩展属性
 * 
 * @author wujian
 *
 */
public class IdText implements Serializable
{
	private static final long serialVersionUID = -7115379138755043229L;

	//(dennis)为了实现oracle查询结果的映射，字段名定义为大写
	private String ID;
	private String TEXT;
	private Object ATTRIBUTES;

	public IdText()
	{
		this(null,"",null);
	}

	public IdText(String id, String text)
	{
		this(id,text,null);
	}

	public IdText(String id, String text, Object attributes)
	{
		this.ID = id;
		this.TEXT = text;
		this.ATTRIBUTES = attributes;
	}

	public String getId()
	{
		return ID;
	}

	public void setId(String id)
	{
		this.ID = id;
	}

	public String getText()
	{
		return TEXT;
	}

	public void setText(String text)
	{
		this.TEXT = text;
	}

	public Object getAttributes()
	{
		return ATTRIBUTES;
	}

	public void setAttributes(Object attributes)
	{
		this.ATTRIBUTES = attributes;
	}

	/**
	 * 将Attributes值转换成需要的类型（如果无法转换，会报java.lang.ClassCastException异常）
	 * 
	 * @return
	 */
	public <T> T ofAttributes()
	{
		return ofAttributes(null);
	}

	/**
	 * 将Attributes值转换成指定的类型（如果无法转换，会返回空；如果clazz参数为null，则无法转换时会报java.lang.ClassCastException异常。）
	 * 
	 * @param clazz
	 *          需要转换的类型，如果指定null，则无法转换时会报异常。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T ofAttributes(Class<?> clazz)
	{
		T ret = null;
		if (ATTRIBUTES == null)
			return ret;

		try
		{
			if (clazz == null || clazz.isAssignableFrom(ATTRIBUTES.getClass()))
				ret = (T)(ATTRIBUTES);
		}
		catch(Exception ex)
		{
			ret = null;
		}
		return ret;
	}

	@Override
	public String toString()
	{
		return ID + "::" + TEXT + "::" + (ATTRIBUTES == null ? "null" : ATTRIBUTES.toString());
	}

	@Override
	public int hashCode()
	{
		return ID.hashCode() * 11 + (TEXT == null ? 0 : TEXT.hashCode())+(ATTRIBUTES==null?0:ATTRIBUTES.hashCode());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o instanceof IdText)
		{
			IdText pair = (IdText)o;
			if (ID != null ? !ID.equals(pair.ID) : pair.ID != null)
				return false;
			if (TEXT != null ? !TEXT.equals(pair.TEXT) : pair.TEXT != null)
				return false;
			if (ATTRIBUTES != null ? !ATTRIBUTES.equals(pair.ATTRIBUTES) : pair.ATTRIBUTES != null)
				return false;
			return true;
		}
		return false;
	}
}
