package com.southgis.ibase.utils;

import java.util.List;

/**
 * 
 * @author wujian
 *
 * @param 接收返回数据的实体类
 * 为了能够兼容easyUI 的 page ， rows 以及total参数
 */
public class PageEntity<T> {	

	private int rows = Constant.DEFAULT_PAGE_SIZE;//每页显示的条数 
	private int page = 1;//当前页面 ，基于1开始
	private int total;//总共的条数 
	private List<T> queryList; //查询后返回的结果集

	/**
	 * 获取每页记录数
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**设置每页记录数（-1表示查询所有记录）
	 * @param rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**获取总记录数
	 * @return
	 */
	public int getTotal() {
		if(total<=0)
		{
			if(queryList!=null)
				total=queryList.size();
		}
		return total;
	}

	/**
	 * 设置总记录数
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * 获取当前页号（基于1开始）
	 * @return
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 设置当前页号（基于1开始）
	 * @param page
	 */
	public void setPage(int page)
	{
		if(page<=0) throw new IllegalArgumentException("页号从1开始计数");
		this.page = page;
	}

	/**
	 * 获取查询结果列表
	 * @return
	 */
	public List<T> getQueryList() {
		return queryList;
	}

	/**
	 * 设置查询结果列表
	 * @param queryList
	 */
	public void setQueryList(List<T> queryList) {
		this.queryList = queryList;
	}
}
