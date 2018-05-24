package com.southgis.ibase.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限缓存数据
 * @author dennis
 *
 */
public class RightData implements Serializable
{
	private static final long serialVersionUID = -6359112698268419040L;
	
	//模块id
	private String moduleId;
	//模块权限
	private Integer moduleRight;
	//模块对应的功能权限
	private List<String> funcList;
	//模块对应的URL地址
	private String pageUrl;
	
	public RightData() {
		funcList=new ArrayList<>();
	}
	
	public RightData(String moduleId, Integer moduleRight, List<String> funcList, String pageUrl) {
		super();
		this.moduleId = moduleId;
		this.moduleRight = moduleRight;
		if(funcList==null)
			this.funcList=new ArrayList<>();
		else
			this.funcList = funcList;
		this.pageUrl = pageUrl;
	}

	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	/**
	 * 权限值：0无权限，1只读，3修改，7创建，15删除，127管理，255授权
	 * -1 未定义
	 * @return
	 */
	public Integer getModuleRight() {
		return moduleRight;
	}
	public void setModuleRight(Integer moduleRight) {
		this.moduleRight = moduleRight==null?-1:moduleRight;
	}
	
	public List<String> getFuncList() {
		return funcList;
	}
	public void setFuncList(List<String> funcList) {
		if(funcList==null)
			this.funcList=new ArrayList<>();
		else
			this.funcList = funcList;
	}

}
