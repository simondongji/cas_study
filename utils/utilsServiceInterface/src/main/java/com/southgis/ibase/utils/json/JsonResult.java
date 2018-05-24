package com.southgis.ibase.utils.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.southgis.ibase.utils.CheckUtil;

/**
 * 标准的JSON返回结果格式
 * 
 * @author dennis
 *
 */
@JsonInclude(Include.NON_NULL)
public class JsonResult
{
	public JsonResult()
	{
		this(0, null, "");
	}

	/**
	 * 用处理结果构造，内部会设置code为0。因此，不能用此构造方法返回一些错误结果。
	 * 
	 * @param orest
	 *          正确处理的结果
	 */
	public JsonResult(Object orest)
	{
		this(0, orest, "");
	}

	/**
	 * 用代码和消息构造
	 * 
	 * @param icode
	 *          作为规范，用0表示成功，其他值表示具体错误
	 * @param msg 需要显示的消息（可以正确的提示消息，也可以是错误消息）
	 */
	public JsonResult(int icode, String msg)
	{
		this(icode, null, msg);
	}

	/**
	 * 用代码、处理结果和消息构造
	 * @param icode 作为规范，用0表示成功，其他值表示具体错误
	 * @param orest
	 * @param msg
	 */
	public JsonResult(int icode, Object orest, String msg)
	{
		setCode(icode);
		setResult(orest);
		setMsg(msg);
	}

	private String msg;
	/**
	 * 获取提示消息（可以正确的提示消息，也可以是错误消息）
	 * @return
	 */
	public String getMsg()
	{
		return msg;
	}
	/**
	 * 设置提示消息（可以正确的提示消息，也可以是错误消息）
	 * @param msg
	 */
	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	private int code = 0;
	/**
	 * 获取处理结果代码。作为规范，用0表示成功，其他值表示具体错误
	 *
	 * @return 处理结果的代码。
	 */
	public int getCode()
	{
		return this.code;
	}
	/**
	 * 设置处理结果的代码。作为规范，用0表示成功，其他值表示具体错误
	 *
	 * @param iCode
	 *          要设置的代码（0表示成功）。
	 */
	public void setCode(int iCode)
	{
		this.code = iCode;
	}

	private Object result;
	/**
	 * 处理结果
	 *
	 * @return 如果没有，返回{@code null}。
	 */
	public Object getResult()
	{
		return this.result;
	}
	/**
	 * 设置处理结果
	 *
	 * @param oResult
	 *          要设置的处理结果
	 */
	public void setResult(Object oResult)
	{
		this.result = oResult;
	}
	
	private String sResultAlias=null;
	/**
	 * 为了特殊需要，将result属性名改为其它名。
	 * @param alias 要为新的属性名（不能包含双引号，内部会替换掉）
	 * @return
	 */
	@JsonIgnore
	public JsonResult setResultAlias(String alias)
	{
		sResultAlias=alias.replace("\"", "");
		return this;
	}

	/**
	 * 转换成Json字符串
	 *
	 * @return
	 */
	public String toJsonString()
	{
		String sRet=JsonUtil.toJsonString(this);
		if(!CheckUtil.isNullorEmpty(sResultAlias)){
			sRet=sRet.replace("\"result\":", "\""+sResultAlias+"\":");
		}
		return sRet;
	}
	
	public Map<String, Object> toMap()
	{
		Map<String, Object> mapRet=new HashMap<>();
		mapRet.put("code", this.getCode());
		mapRet.put("msg", this.getMsg());
		if(!CheckUtil.isNullorEmpty(sResultAlias))
			mapRet.put(sResultAlias, this.getResult());
		else
			mapRet.put("result", this.getResult());
		
		return mapRet;
	}
}
