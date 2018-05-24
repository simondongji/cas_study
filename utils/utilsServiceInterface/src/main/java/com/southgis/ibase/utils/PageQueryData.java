package com.southgis.ibase.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageQueryData<T> extends PageEntity<T>
{
	private String userId;

	private Map<String, Boolean> sort = new LinkedHashMap<>();

	private String searchTxt;

	private Map<String, String> searchTexts;

	/**
	 * 返回组装的结果，兼容easyUI的分页功能, 可增加需要扩展返回值
	 * 
	 * @return
	 */
	public Map<String, Object> getResult()
	{
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("total", this.getTotal());
		List<T> lsTemp = this.getQueryList();
		if (lsTemp == null)
		{
			lsTemp = new ArrayList<>(1);
		}
		result.put("rows", lsTemp);
		return result;
	}

	/**
	 * 获取已设置的排序字段（有先后顺序）
	 * 
	 * @return
	 */
	public Map<String, Boolean> getSort()
	{
		return sort;
	}

	/**
	 * 添加一个排序字段
	 * 
	 * @param field
	 *          字段名
	 * @param asc
	 *          是否升序
	 */
	public void addSortField(String field, boolean asc)
	{
		sort.put(field, asc);
	}

	/**
	 * 获取关联操作用户ID
	 * 
	 * @return
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * 设置关联操作用户ID
	 * 
	 * @param userId
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/**
	 * 获取通用字段模糊查询内容，空表示没有通用查询
	 * 
	 * @return
	 */
	public String getSearchTxt()
	{
		return searchTxt;
	}

	/**
	 * 设置通用字段模糊查询内容，空表示不使用通用查询
	 * 
	 * @param searchTxt
	 */
	public void setSearchTxt(String searchTxt)
	{
		this.searchTxt = searchTxt;
	}

	public Map<String, String> getSearchTexts()
	{
		return searchTexts;
	}

	public void setSearchTexts(Map<String, String> searchTexts)
	{
		this.searchTexts = searchTexts;
	}
}
