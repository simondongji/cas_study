package com.southgis.ibase.utils;

import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

/**
 * 树实体,此树结构符合easyui 的tree结构
 * 
 * @author TP
 *
 */
@Data
public class TreeEntity implements Comparable<TreeEntity>
{
	/**
	 * 节点收缩状态标识
	 */
	public static final String STATE_CLOSED = "closed";
	/**
	 * 节点展开状态标识
	 */
	public static final String STATE_OPEN = "open";
	
	/**
	 * 空图标的样式
	 */
	public static final String NULL_ICON="icon-blank";
	/**
	 * 文件夹图标标式
	 */
	public static final String FOLDER_ICON="tree-folder";
	/**
	 * 节点id
	 */
	private String id;
	/**
	 * 节点显示文本
	 */
	private String text;
	
	/**
	 * 节点显示的图标
	 */
	private String iconCls;
	
	/**
	 * 是否勾选
	 */
	private boolean checked;
	
	/**
	 * 节点默认状态（open、closed），
	 * 如果为open，且没有children属性，则展示为叶子节点；
	 * 如果为closed，且没有children属性，则在展开时会发送加载节点的请求。
	 */
	private String state;
	
	/*
	 * 父节点标识值
	 */
	private String parentId;

	/**
	 * 排序值
	 */
	private int sort;
	/*
	 * 是否已加载（用于子节点的延时加载）
	 */
	@JsonIgnore
	private boolean isLoad;
	
	/**
	 * 节点自定义属性
	 */
	private Object attributes;
	
	/**
	 * 子节点集
	 */
	@Setter(AccessLevel.PROTECTED)
	private TreeSet<TreeEntity> children;

	public TreeEntity()
	{
		children=new TreeSet<TreeEntity>();
	}
	
	/**
	 * 构造节点
	 * @param sId
	 * @param sText
	 * @param sParentId
	 * @param iSort
	 * @param sIcon 节点图标样式定义（null表示默认图标，""表示使用空图标）
	 */
	public TreeEntity(String sId,String sText,String sParentId,int iSort,String sIcon)
	{
		this();
		id=sId;
		text=sText;
		parentId=sParentId;
		sort=iSort;
		if(sIcon!=null){
			if(sIcon.isEmpty())
				sIcon=NULL_ICON;
			iconCls=sIcon;
		}
	}

	@Override
	public int compareTo(TreeEntity o)
	{
		if(o==null) return 1;
		if(this==o) return 0;//如果是同一对象，返回0（用于移除）
  	//注意：不能返回0，因为在TreeSet排序时，如果比较是0值时，会认为是在同一个位置放置元素（将会覆盖原来的元素）
		if(this.sort==o.sort){//如果排序值相同，再比较标题
			String stext=this.text;
			if(stext==null)stext="";
			int ir=stext.compareTo(o.text);
			if(ir==0){
				stext=this.id;
				if(stext==null)stext="";
				ir=stext.compareTo(o.id);
			}
			return ir==0?1:ir;//仍相等，返回大于
		}
		return (this.sort<o.sort)?-1:1;
	}
	
}
