package com.southgis.ibase.utils.json;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author frj
 *
 */
public class JsonUtil
{
	/**
	 * 将结果转成Json串输出
	 *
	 * @param value 转换对象
	 * @return json字符串
	 */
	public static String toJsonString(Object value)
	{
		if(value==null) return "null";
		
		String result;
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(value);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = "";
		}
		return result;
	}
	/**
	 * 将json串反序列化成对象，json串需要标准格式（即属性名及字符串值要以双引号包含）
	 * @param json
	 * @param classType
	 * @return
	 */
	public static Object jsonStringToObject(String json,Class<?> classType)
	{
		return jsonStringToObject(json,classType,false);
	}
	
	/**
	 * 将json串反序列化成对象，json串需要标准格式（即属性名及字符串值要以双引号包含）
	 * @param json
	 * @param classType
	 * @param ignoreUnknown 忽略该目标对象不存在的属性
	 * @return
	 */
	public static Object jsonStringToObject(String json,Class<?> classType,boolean ignoreUnknown)
	{
		Object result;
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			if (ignoreUnknown)
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			result = mapper.readValue(json, classType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	/**
	 * 将字符串转换成泛型对象，比如List对象（可以用接口类型，对于List会使用ArrayList实例化，对于Map会使用LinkedHashMap实例化），
	 * json串需要标准格式（即属性名及字符串值要以双引号包含）
	 * 如：:
	 * jsonStringToObject("[{\"age\":1,\"name\":\"aa\"},{\"age\":2,\"name\":\"12\"}]",
	 *  new TypeReference＜List＜Person＞＞(){});
	 *  或
	 * jsonStringToObject("[{\"age\":1,\"name\":\"aa\"},{\"age\":2,\"name\":\"12\"}]",
	 *  new TypeReference＜List＜Map＜?,?＞＞＞(){});
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T jsonStringToObject(String json,TypeReference<T> type)
	{
		T result;
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(json, type);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	/**
	 * 将json字符串转换为Map对象
	 * @param json
	 * @return
	 */
	public static HashMap<String, Object> jsonStringToMap(String json)
	{
		return jsonStringToObject(json,new TypeReference<HashMap<String, Object>>(){});
	}
	/**
	 * 将json字符串转换为List＜Map＞对象
	 * @param json
	 * @return
	 */
	public static ArrayList<HashMap<String, Object>> jsonStringToList(String json)
	{
		return jsonStringToObject(json,new TypeReference<ArrayList<HashMap<String, Object>>>(){});
	}

}
