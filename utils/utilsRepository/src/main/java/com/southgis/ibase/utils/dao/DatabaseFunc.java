/**
 * 
 */
package com.southgis.ibase.utils.dao;

import com.southgis.ibase.utils.exception.ServiceException;

import javax.sql.DataSource;

/**
 * 根据配置的数据库类型，生成相应的sql函数调用语句
 * @author dennis
 *
 */
public class DatabaseFunc
{
	private static DatabaseFunc dfDefault;
	
	private String dbType;
	
	/**
	 * 获取默认数据库配置项的单例对象
	 * @return
	 */
	public static DatabaseFunc defaultDatabaseFunc()
	{
		if(dfDefault==null)
			dfDefault=new DatabaseFunc();
		return dfDefault;
	}
	/**
	 * 从config.properties获取默认数据驱动配置项“jdbc.driverClassName”得到数据库类型
	 */
	public DatabaseFunc()
	{
		this("jdbc.driverClassName");
	}
	/**
	 * 从config.properties获取指定数据驱动配置项得到数据库类型（一般在多数据源的情况下）
	 * @param cfgDriverName 数据驱动配置项
	 */
	public DatabaseFunc(String cfgDriverName)
	{
		dbType=DatabaseUtil.getDataSourceType(cfgDriverName);
	}
	
	/**
	 * 从数据源获取指定数据驱动配置项得到数据库类型（一般在多数据源的情况下）
	 * @param ds 数据源
	 */
	public DatabaseFunc(DataSource ds)
	{
		dbType=DatabaseUtil.getDataSourceType(ds);
	}

	
	/**
	 * 生成非空字符串判断条件
	 * @param fieldname （字符串类型的）字段名
	 * @return 如果是oralce库返回：(fieldname is not null)，
	 * 其它数据库返回：(fieldname is not null and fieldname<>'')
	 */
	public String notNullCharWhere(String fieldname)
	{
		return "("+fieldname+" is not null"
			 + (DatabaseUtil.DBTYPE_ORACLE.equals(dbType) ? ")" : (" and "+fieldname+"<>'')"));
	}
	
	/**
	 * 连接两个字符串值
	 * @param charVal1 （sql char类型）的标量值或字段名或SQL调用语句
	 * @param charVal2 （sql char类型）的标量值或字段名或SQL调用语句
	 * @return Oralce返回：(charVal1 || charVal2)，
	 * Mysql返回：CONCAT(charVal1, charVal2)，
	 * SqlServer返回：(charVal1 + charVal2)
	 */
	public String concatChar(String ...charVals)
	{
		if(DatabaseUtil.DBTYPE_ORACLE.equals(dbType)){
			return String.join(" || ", charVals);
		}else if(DatabaseUtil.DBTYPE_MYSQL.equals(dbType)){
			return "CONCAT("+String.join(", ", charVals)+")";
		}else if(DatabaseUtil.DBTYPE_SQLSERVER.equals(dbType)){
			return String.join(" + ", charVals);
		}else{
			throw new ServiceException("[sql函数调用]未支持的数据库类型："+dbType);
		}
	}
	
	/**
	 * 生成“转换为日期值”的SQL调用语句
	 * @param charVal （sql char类型）的标量值或字段名或SQL调用语句
	 * @param fmt 转换值格式（-1 默认格式，即不用格式串；
	 *   0 yyyy-MM-dd HH:mm:ss；1 yyyy年MM月dd日  HH:mm:ss；2 yyyyMMddHHmmss；
	 *   10 yyyy-MM-dd；11 yyyy年MM月dd日；12 yyyyMMdd）
	 * @return 调用语句的SQL片段
	 */
	public String getToDateInvoke(String charVal,int fmt)
	{
		throw new RuntimeException("方法还未实现");
		/*
		if(DatabaseUtil.DBTYPE_ORACLE.equals(dbType)){
			switch(fmt)
			{
			case 0:
				
				break;
			case 1:
				
				break;
			case 2:
				
				break;
			case 10:
				
				break;
			case 11:
				
				break;
			case 12:
				
				break;
			default://默认格式，即不用格式串
				break;
			}
			return "";
		}else if(DatabaseUtil.DBTYPE_MYSQL.equals(dbType)){
			switch(fmt)
			{
			case 0:
				
				break;
			case 1:
				
				break;
			case 2:
				
				break;
			case 10:
				
				break;
			case 11:
				
				break;
			case 12:
				
				break;
			default://默认格式，即不用格式串
				break;
			}
			return "";
		}else if(DatabaseUtil.DBTYPE_SQLSERVER.equals(dbType)){
			switch(fmt)
			{
			case 0:
				
				break;
			case 1:
				
				break;
			case 2:
				
				break;
			case 10:
				
				break;
			case 11:
				
				break;
			case 12:
				
				break;
			default://默认格式，即不用格式串
				break;
			}
			return "";
		}else{
			throw new ServiceException("[sql函数调用]未支持的数据库类型："+dbType);
		}
		*/
	}

	/**
	 * 生成“获取年份”的SQL调用语句
	 * @param dateVal （sql date类型）的标量值或字段名或SQL调用语句
	 * @return 调用语句的SQL片段
	 */
	public String getYearInvoke(String dateVal)
	{
		if(DatabaseUtil.DBTYPE_ORACLE.equals(dbType)){
			return "TO_NUMBER(TO_CHAR(" + dateVal + ",'yyyy'))";
		}else if(DatabaseUtil.DBTYPE_MYSQL.equals(dbType)){
			return "YEAR("+dateVal+")";
		}else if(DatabaseUtil.DBTYPE_SQLSERVER.equals(dbType)){
			return "DATEPART(yyyy," + dateVal + ")";
		}else{
			throw new ServiceException("[sql函数调用]未支持的数据库类型："+dbType);
		}
	}
}
