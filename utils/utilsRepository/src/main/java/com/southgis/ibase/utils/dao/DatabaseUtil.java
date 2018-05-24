package com.southgis.ibase.utils.dao;

import com.southgis.ibase.utils.CheckUtil;
import com.southgis.ibase.utils.PropertiesUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据库工具类
 * @author young
 *
 */
public final class DatabaseUtil
{
	/**
	 * 数据库类型：mysql
	 */
	public static final String DBTYPE_MYSQL="mysql";
	/**
	 * 数据库类型：oracle
	 */
	public static final String DBTYPE_ORACLE="oracle";
	/**
	 * 数据库类型：SQLServer
	 */
	public static final String DBTYPE_SQLSERVER="sqlserver";

	private static String getDataSourceTypeFromDriverName(String sName)
	{
		String dataType = "";
		if(sName==null)
			sName="";
		else
			sName=sName.toLowerCase();

		if (sName.indexOf(DBTYPE_MYSQL)>=0){//com.mysql.jdbc.driver
			dataType = DBTYPE_MYSQL;
		}else if (sName.indexOf(DBTYPE_ORACLE)>=0){//oracle.jdbc.oracledriver
			dataType = DBTYPE_ORACLE;
		}else if(sName.indexOf(DBTYPE_SQLSERVER)>=0){//com.microsoft.sqlserver.jdbc.SQLServerDriver
			dataType = DBTYPE_SQLSERVER;
		}else{
			dataType=sName;
		}
		return dataType;
	}
	/**
	 * 在config.properties中，根据驱动类型获取数据类型:mysql、oracle
	 * @param cfgDriverName 配置数据库驱动的配置名，在多数据源的项目中，可能需要连不同的数据库时。
	 * 比如有两个配置项：
	 * jdbc.driverClassName=com.mysql.jdbc.driver
	 * jdbc.bdcdjDriver=oracle.jdbc.oracledriver
	 * 当需要获取jdbc.bdcdjDriver配置类型时，调用：
	 * getDataSourceType("jdbc.bdcdjDriver")
	 */
	public static String getDataSourceType(String cfgDriverName){
		String dataDriver = PropertiesUtil.getValue(cfgDriverName);
		return getDataSourceTypeFromDriverName(dataDriver);
	}
	
	/**
	 * 在config.properties中，根据"jdbc.driverClassName"配置的驱动类型获取数据类型:mysql、oracle
	 * @return
	 */
	public static String getDataSourceType(){
		return getDataSourceType("jdbc.driverClassName");
	}
	
	/**
	 * 从数据源中获取数据库类型
	 * @param ds
	 * @return
	 */
	public static String getDataSourceType(DataSource ds)
	{
		try
		{
			return getDataSourceTypeFromDriverName(ds.getConnection().getMetaData().getDriverName());
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return "(unknow)";
	}
	

	/**
	 * 获得sql语句中的命令关键字（小写）。
	 * 如果sql是复合语句，仅返回开头的（主语句）关键字。
	 * @param sql 
	 * @return sql语句中的命令关键字（select、update、insert、delete等）
	 */
	@SuppressWarnings("deprecation")
	public static String getSqlCmd(String sql)
	{
		if (CheckUtil.isNullorEmpty(sql))
		{
			return "";
		}

		// 找到非空的第一个字符
		int is = 0;
		while(is < sql.length() && Character.isSpace(sql.charAt(is)))
			is++;
		// 找到下一个空白符
		int ie = is;
		while(ie < sql.length() && !Character.isSpace(sql.charAt(ie)))
			ie++;
		String sCmd = sql.substring(is, ie).toLowerCase();
		return sCmd;
	}
	
	/**
	 * 判断sql语句中是否包含关键字
	 * @param sSql
	 * @param sKey 要查找的关键（比如：select、order by、where等），不区分大小写
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean existKeyword(String sSql,String sKey)
	{
		if(CheckUtil.isNullorEmpty(sSql)) return false;
		String sTemp=" "+sSql.toLowerCase()+" ";
		sKey=sKey.toLowerCase();
		
		String sPart;
		int is=0;
		int ie=0;
		int iPos;
		while(is>=0){
			ie=sTemp.indexOf("'", is);
			if(ie<0)
				sPart=sTemp.substring(is);
			else
				sPart=sTemp.substring(is, ie);

			iPos=sPart.indexOf(sKey);
			if(iPos>0 && Character.isSpace(sPart.charAt(iPos-1)) && Character.isSpace(sPart.charAt(iPos+sKey.length())))
				return true;

			is=-1;
			if(ie>=0){
				is=sTemp.indexOf("'",ie+1);
			}
			if(is>=0) is++;
		}
		return false;
	}
}
