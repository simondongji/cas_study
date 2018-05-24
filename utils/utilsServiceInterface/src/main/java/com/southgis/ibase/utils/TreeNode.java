package com.southgis.ibase.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形节点对象，一般用于数据处理
 * @author dennis
 *
 * @param <T>
 */
public class TreeNode<T>
{
	TreeNode<T> parent=null;
	List<TreeNode<T>> children=new ArrayList<>();
	T prop;
	
	public TreeNode(T prop)
	{
		this.prop=prop;
	}
	/**
	 * 获取当前节点的所有子节点
	 * @return
	 */
	public List<TreeNode<T>> getChildren(){
		return children;
	}

	/**
	 * 获取当前节点的父节点
	 * @return
	 */
	public TreeNode<T> getParent()
	{
		return parent;
	}

	/**
	 * 设置当前节点的父节点
	 * @param parent
	 */
	public void setParent(TreeNode<T> parent)
	{
		this.parent = parent;
	}

	/**
	 * 获取当前节点所包含的属性对象
	 * @return
	 */
	public T getProp()
	{
		return prop;
	}

	/**
	 * 设置当前节点所包含的属性对象
	 * @param prop
	 */
	public void setProp(T prop)
	{
		this.prop = prop;
	}
	
	/**
	 * 判断所包含的属性是否相等，使用属性对象的equals方法比较
	 * @param prop
	 * @return
	 */
	public boolean propEquals(T prop)
	{
		if(prop==null){
			if(this.prop==null) return true;
		}else{
			if(prop.equals(this.prop)) return true;
		}
		return false;
	}
	/**
	 * 判断是否包含指定子节点，使用对象引用的相等（==）比较。只有是相同对象才会认为相等
	 * @param node
	 * @return
	 */
	public boolean existChild(TreeNode<T> node,boolean bRecursive){
		for(TreeNode<T> n : children){
			if(n==node) return true;
			if(bRecursive && n.existChild(node, true)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否包含有指定属性相等的直接子节点，使用propEquals比较。
	 * 如果传入null，且子节点包含有空属性，会返回找到的节点
	 * @param nodeProp
	 * @param bRecursive 是否递归向下查找
	 * @return
	 */
	public TreeNode<T> findChild(T nodeProp,boolean bRecursive)
	{
		for(TreeNode<T> n : children){
			if(n.propEquals(nodeProp)) return n;
			if(bRecursive){
				TreeNode<T> temp=n.findChild(nodeProp,true);
				if(temp!=null)
					return temp;
			}
		}
		return null;
	}
	
	/**
	 * 判断是否存在有指定属性相等的父节点(或当前节点)，使用propEquals比较。
	 * 如果传入null，且父节点包含有空属性，会返回找到的节点
	 * @param nodeProp
	 */
	public TreeNode<T> findSelfOrParent(T nodeProp)
	{
		if(propEquals(nodeProp)) return this;
		if(parent==null) return null;
		
		return parent.findSelfOrParent(nodeProp);
	}
	
	/**
	 * 判断是否存在有指定属性相等的父节点，使用propEquals比较。
	 * 如果传入null，且父节点包含有空属性，会返回找到的节点
	 * @param nodeProp
	 */
	public TreeNode<T> findParent(T nodeProp)
	{
		if(parent==null) return null;
		
		return parent.findSelfOrParent(nodeProp);
	}
}
