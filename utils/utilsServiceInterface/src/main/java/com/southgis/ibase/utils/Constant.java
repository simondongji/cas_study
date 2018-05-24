package com.southgis.ibase.utils;


/**
 * 系统需要的一些常量定义
 * @author dennis
 *
 */
public final class Constant {
	/**
	 * FreeMarker 配置对象注入名
	 */
	public static final String FREEMARKER_CONFIG_BEAN_NAME="freemarkerConfig";
	
	public static final String ERROR_OF_NONE = "未知错误";
	public static final String UNKNOWN = "(未知)";
	
	/**
	 * 错误标准提示信息。第一个位置为服务名，第二个位置为错误信息。
	 */
	public static final String ERROR_PROMPT="%s 服务拒绝了本次调用：%s。%n如此问题一直出现，请联系管理员。";
	/**
	 * 系统内的默认编码方式
	 */
	public static final String DEFAULT_ENCODE = "UTF-8";

	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	
	public static final String CONTENT_TYPE_HTML = "text/html";
	public static final String CONTENT_TYPE_JSON = "text/json";
	public static final String CONTENT_TYPE_XML = "text/xml";
	public static final String CONTENT_TYPE_PLAIN = "text/plain";

	/**
	 * 系统默认分页记录数
	 */
	public static final int DEFAULT_PAGE_SIZE = 20; //默认每页显示的条数 
	
	public static final String DEFAULT_CALLBACK = "ibase2-xjp-cb"; //跨域调用的客户端回调方法前缀
}
