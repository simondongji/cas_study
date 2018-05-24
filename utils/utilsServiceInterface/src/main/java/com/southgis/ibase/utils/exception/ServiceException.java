package com.southgis.ibase.utils.exception;

/**
 * Service层公用的Exception.
 * 
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * 
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 3583566093089790852L;

	public ServiceException(String message, Throwable cause) {
  	//添加err标签，让客户端更准确找到错误信息
		super((message!=null && message.indexOf("[errs]")<0)?("[errs]"+message+"[erre]"):message, cause);
	}

	public ServiceException() {
		this("系统内部异常",(Throwable)null);
	}

	public ServiceException(String message) {
		this(message,(Throwable)null);
	}

	public ServiceException(Throwable cause) {
		this(cause.getLocalizedMessage(),cause);
	}
	
	/**
	 * 获取异常错误信息。去除系统约定的分隔符（[errs],[erre]）
	 * @param errorMsg
	 * @return
	 */
	public static String getMessage(String errorMsg){
		if(errorMsg==null) return "";
		if(errorMsg.startsWith("[errs]")){
			return errorMsg.substring(6, errorMsg.length()-6);
		}
		return errorMsg;
	}
}