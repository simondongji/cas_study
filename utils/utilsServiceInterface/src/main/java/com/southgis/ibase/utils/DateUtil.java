package com.southgis.ibase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 * @author HuangLeibing
 *
 */
public final class DateUtil {

	/**
	 * 长日期格式（日期+时间）
	 */
	public static final String FULL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 短日期格式（无时间）
	 */
	public static final String ONLY_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 仅时间格式
	 */
	public static final String ONLY_TIME_FORMAT = "HH:mm:ss";
	
	/**
	 * 转换成标准日期格式字符串
	 * @param dtv
	 * @return
	 */
	public static String dateToString(Date dtv)
	{
		return dateToString(dtv,false);
	}
	
	public static String dateToString(Date dtv, String sFmt)
	{
		if(!sFmt.equals("needed"))
		{
		SimpleDateFormat sdf=new SimpleDateFormat(sFmt);
		return sdf.format(dtv);
		}
		else {
			return dtv.toString();
		}
		
	}

	/**
	 * 转换成标准日期格式字符串
	 * @param dtv
	 * @param bOnlyDate 是否仅转换日期部分（不会返回时间部分）。
	 * @return
	 */
	public static String dateToString(Date dtv, boolean bOnlyDate)
	{
		if(dtv==null) return "";
		
		SimpleDateFormat sdf=new SimpleDateFormat(bOnlyDate?ONLY_DATE_FORMAT:FULL_DATETIME_FORMAT);
		return sdf.format(dtv);
	}
	
	/**
	 * 将字符串形式的日期转换成日期。如果不能转换，返回null，转换出错抛出ParseException。<br/>
	 * 注：如果参数是全数字的字符串，内部将会进行简单判断：<br/>
	 * 如果长度8位或14位，且月/日/时/分/秒在有效范围内，将视为yyyyMMdd或yyyyMMddHHmmss格式；<br/>
	 * 否则，将视为时间戳（从1970-1-1以来的毫秒）<br/>
	 * @param sDate 字符串形式日期
	 * @return Date类型日期
	 */
	public static Date stringToDate(String sDate)
		throws ParseException
	{
		Date date=null;
		if(CheckUtil.isNullorEmpty(sDate)) return date;

		if(CheckUtil.isDigit(sDate) && !isDateDigit(sDate)){
			return new Date(Long.parseLong(sDate));
		}


		if(sDate.charAt(0)>='A' && sDate.charAt(0)<='Z') //如果以字母开头，假定为国际格式
		{
			try{
				sDate=sDate.trim().replace("CST", "GMT+0800");//为了防止混乱，将CST替换成GMT+8
		  	@SuppressWarnings("deprecation")
				long lr=Date.parse(sDate);
		  	return new Date(lr);
			}catch(Exception ex){
				//如果出错，再进行后继处理
			}
		}
			
		sDate=formatDateString(sDate);
		
		SimpleDateFormat df;
		if(sDate.indexOf(':')>0) {
			if(sDate.indexOf('-')>0)
				df = new SimpleDateFormat(FULL_DATETIME_FORMAT);
			else
				df=new SimpleDateFormat(ONLY_TIME_FORMAT);
		} else {
			df = new SimpleDateFormat(ONLY_DATE_FORMAT);
		}
		
		date = df.parse(sDate);

		return date;
	}
	/**
	 * 全为数字的字符串是否为日期格式：
	 * 长度等于8（视为yyyyMMdd），或等于14（视为yyyyMMddHHmmss），转为时间戳
	 * @param val
	 * @return
	 */
	private static boolean isDateDigit(String val)
	{
		if(val.length()==8 || val.length()==14){
			//月份值
			int iv=Integer.parseInt(val.substring(4, 6));
			if(iv==0 || iv>12) return false;
			//月内的日期（并不考虑是否合法值，比如2月没有31号）
			iv=Integer.parseInt(val.substring(6, 8));
			if(iv==0 || iv>31) return false;
			
			if(val.length()==14){
				//时
				iv=Integer.parseInt(val.substring(8, 10));
				if(iv>24) return false;
				//分
				iv=Integer.parseInt(val.substring(10, 12));
				if(iv>59) return false;
				//秒
				iv=Integer.parseInt(val.substring(12, 14));
				if(iv>59) return false;
			}
			return true;
		}
		return false;
	}
	
	/** 将标准时间转换成本地时间（中国时区+8小时）
	 * @param dtcur
	 * @return
	 */
	public static Date dateIso2Local(Date dtcur)
	{
		if(dtcur==null) return null;
		Date dtLocal=new Date(dtcur.getTime()+8*60*60*1000);
		return dtLocal;
	}
	/**
	 * 将本地时间转换成标准时间（中国时区-8小时）
	 * @param dtcur
	 * @return
	 */
	public static Date dateLocal2Iso(Date dtcur)
	{
		if(dtcur==null) return null;
		Date dtIso=new Date(dtcur.getTime()-8*60*60*1000);
		return dtIso;
	}

	/**
	 * 将Date对象转换成Calendar对象。如果date为null，则返回当前时间
	 * @param date
	 * @return
	 */
	public static Calendar toCalendar(Date date)
	{
		Calendar cal=Calendar.getInstance();
		if(date!=null)
			cal.setTime(date);
		return cal;
	}
	
	/** 将日期串转换成标准的格式。如果转换失败，返回Empty
	 * 不处理长整型的时间戳、
	 * 以及形如：Mon May 22 21:58:42 CST 2017 或 Mon May 22 2017 21:58:42 GMT+0800 的国标格式的日期。
	 * 
	 * @param strDate 日期字符串支持格式：
	 * yyyy/MM/dd HH:mm:ss、yyyy-MM-dd HH:mm:ss，
	 * HH.mm.ss，
	 * yyyyMMddHHmmss、yyyyMMddTHHmmss，
	 * yyyy年MM月dd日HH时mm分ss秒、HH点mm分ss秒。
	 * 其中yyyy、MM、dd、HH、mm、ss也可以是全角数字，或汉字小写数字（如：二〇〇六）；
	 * HH也可以是12小时制数据，此时日期串需要包含AM/PM或上午/下午（如果不包含默认为上午日期）
	 * 
	 * @return 标准的日期格式字符串：yyyy-MM-dd HH:mm:ss
	 */
	@SuppressWarnings("deprecation")
	public static String formatDateString(String sDateVal)
	{
		if (sDateVal == null)
			return "";
		String sR = sDateVal.trim();
		if (sR.length() == 0)
			return "";
		try
		{
			sR = sR.toUpperCase().replace('\t', ' ');
			int iTemp = 0;
			//清除末尾的时区信息
			if ((iTemp = sR.indexOf('Z')) >= 0)
			{//ZE8 或 Z+08:00
				sR = sR.substring(0, iTemp);
			}
			else if ((iTemp = sR.indexOf('G')) >= 0)
			{//GMT +08:00
				sR = sR.substring(0, iTemp);
			}
			else if ((iTemp = sR.indexOf('+')) >= 0)
			{//+08:00
				sR = sR.substring(0, iTemp);
			}
			//上午/下午的自定义名字
			SimpleDateFormat sdf=new SimpleDateFormat("a");
			Calendar dtTest = Calendar.getInstance();
			dtTest.set(Calendar.HOUR_OF_DAY, 8);
			String sAMName = sdf.format(dtTest.getTime());
			dtTest.set(Calendar.HOUR_OF_DAY, 22);
			String sPMName = sdf.format(dtTest.getTime());
			//格式化日期部分
			if (sR.indexOf('／') >= 0)
			{
				sR = sR.replace('／', '-');
			}
			else if (sR.indexOf('－') >= 0)
			{
				sR = sR.replace('－', '-');
			}
			else if (sR.indexOf('/') >= 0)
			{
				sR = sR.replace('/', '-');
			}
			else if (sR.indexOf('年') >= 0)
			{
				sR = sR.replace('年', '-').replace('月', '-').replace('日', ' ');
			}
			//格式化时间部分
			if (sR.indexOf('：') >= 0)
			{
				sR = sR.replace('：', ':');
			}
			else if (sR.indexOf('.') >= 0)
			{
				sR = sR.replace('.', ':');
			}
			else if (sR.indexOf('分') >= 0)
			{
				sR = sR.replace('时', ':').replace('点', ':').replace('分', ':').replace('秒', ' ');
			}
			//替换“上午”字符串
			if (sR.indexOf("上午") >= 0)
			{
				sR = sR.replace("上午", " ");
			}
			else if (sR.indexOf("AM") >= 0)
			{
				sR = sR.replace("AM", " ");
			}
			else if (sR.indexOf(sAMName) >= 0)
			{
				sR = sR.replace(sAMName, " ");
			}
			if (sR.indexOf('T') >= 0)
			{
				sR = sR.replace('T', ' ');
			}
			//替换“下午”字符串
			boolean bPM = (sR.indexOf("下午") >= 0);
			if (bPM)
			{
				sR = sR.replace("下午", " ");
			}
			if (!bPM)
			{
				bPM = (sR.indexOf("PM") >= 0);
				if (bPM)
				{
					sR = sR.replace("PM", " ");
				}
			}
			if (!bPM)
			{
				bPM = (sR.indexOf(sPMName) >= 0);
				if (bPM)
				{
					sR = sR.replace(sPMName, " ");
				}
			}

			sR = sR.replace('０', '0').replace('１', '1').replace('２', '2').replace('３', '3')
				.replace('４', '4').replace('５', '5').replace('６', '6').replace('７', '7')
				.replace('８', '8').replace('９', '9');
			//〇一二三四五六七八九
			//十 一十 二十 三十 四十 五十 六十 十一 一十一 二十一 ...
			//-->十前面不是一至九，加"一"；十后面不是〇至九，加"〇"；最后将十替换成""
			iTemp = 0;
			sR = sR.replace('○', '〇');//将\xA1F0替换成\xA996
			while ((iTemp = sR.indexOf('十', iTemp)) >= 0)
			{
				if (iTemp + 1 == sR.length())
				{
					sR += "〇";
					break;
				}
				if (!CheckUtil.isCNumber(sR.charAt(iTemp + 1)))
				{
					sR = sR.substring(0, iTemp + 1)+"〇"+sR.substring(iTemp + 1);
				}

				if (iTemp == 0)
				{
					sR = "一"+sR;
					++iTemp;
				}
				else if (sR.charAt(iTemp - 1) == '〇')
				{
					sR = sR.substring(0, iTemp - 1)+"一"+sR.substring(iTemp);
				}
				else if (!CheckUtil.isCNumber(sR.charAt(iTemp - 1)))
				{
					sR = sR.substring(0, iTemp)+"一"+sR.substring(iTemp);
				}
				++iTemp;
			}
			sR = sR.replace("十", "");
			//廿 廿一 ...
			//-->廿后面不是〇至九，加"〇"；最后将廿替换成"二"
			iTemp = 0;
			while ((iTemp = sR.indexOf('廿', iTemp)) >= 0)
			{
				if (iTemp + 1 == sR.length())
				{
					sR += "〇";
					break;
				}
				if (!CheckUtil.isCNumber(sR.charAt(iTemp + 1)))
				{
					sR = (sR.substring(0, iTemp + 1)+"〇"+sR.substring(iTemp + 1));
				}
				++iTemp;
			}
			sR = sR.replace('廿', '二');
			//卅 卅一
			//-->卅后面不是〇至九，加"〇"；最后将卅替换成"三"
			iTemp = 0;
			while ((iTemp = sR.indexOf('卅', iTemp)) >= 0)
			{
				if (iTemp + 1 == sR.length())
				{
					sR += "〇";
					break;
				}
				if (!CheckUtil.isCNumber(sR.charAt(iTemp + 1)))
				{
					sR = (sR.substring(0, iTemp + 1)+"〇"+sR.substring(iTemp + 1));
				}
				++iTemp;
			}
			sR = sR.replace('卅', '三');

			sR = sR.replace('〇', '0').replace('一', '1').replace('二', '2')
					.replace('三', '3').replace('四', '4').replace('五', '5')
					.replace('六', '6').replace('七', '7').replace('八', '8')
					.replace('九', '9');
			//去除结尾可能出现的‘-’ 或 ‘:’
			iTemp=sR.length()-1;
			while(iTemp>=0){
				char chx=sR.charAt(iTemp);
				if(!Character.isSpace(chx) && chx!='-' && chx!=':')
					break;
				--iTemp;
			}
			++iTemp;
			sR=sR.substring(0, iTemp);

			int iHr = sR.indexOf(':');//按小时分隔符切分
			String sPrefix = "", sSuffix = "";
			if (sR.indexOf('-') < 0 && iHr < 0)
			{
				//是yyyymmddHHMISS格式
				sR = sR.replace(" ", "");
				if (sR.length() >= 8)//yyyymmdd 或 yyyymmddHHMISS
				{
					sPrefix = (sR.substring(0, 4)+"-"+sR.substring(4, 6)+"-"+sR.substring(6, 8));
				}
				if (sR.length() == 6 || sR.length() >= 14)//6=HHMISS 14=yyyymmddHHMISS
				{
					int iStart = (sR.length() == 6) ? 0 : 8;
					if (bPM)
					{
						iTemp = ConvertUtil.getValue(sR.substring(iStart, iStart+2), 0);
						if (iTemp < 12)
							iTemp += 12;
						sSuffix = (Integer.toString(iTemp)+":"
							+sR.substring(iStart+2, iStart+4)+":"+sR.substring(iStart + 4, iStart+6));
					}
					else
					{
						sSuffix = (sR.substring(iStart, iStart+2)+":"+sR.substring(iStart + 2, iStart+4)
								+":"+sR.substring(iStart + 4, iStart+6));
					}
				}
				if (sR.length() == 4)
				{
					sPrefix = sR + "-01-01";
				}
				sR = sPrefix;
				if (CheckUtil.isNullorEmpty(sR))
					sR = sSuffix;
				else if (!CheckUtil.isNullorEmpty(sSuffix))
				{
					sR = (sR+" "+sSuffix);
				}
				return sR;
			}else if (sR.indexOf('-') >=0) {
				String[] sPart = sR.split("-");
				if (sPart.length == 2) {//yyyy-MM
					sR = sPart[0].replace(" ", "")+"-"+sPart[1].replace(" ", "") + "-01";
					return sR;
				}
			}

			if (iHr >= 0)
			{//有时间部分或包含日期部分
				sPrefix = sR.substring(0, iHr).trim();
				iTemp = sPrefix.lastIndexOf(' ');
				if (iTemp >= 0)
				{//包含日期部分
					sSuffix = sPrefix.substring(iTemp);
					sPrefix = sPrefix.substring(0, iTemp);
				}
				else
				{//只有时间部分
					sSuffix = sPrefix;
					sPrefix = "";
				}
				sSuffix += sR.substring(iHr);
				sSuffix = sSuffix.replace(" ", "");
				String[] saTime = sSuffix.split(":");
				if (bPM)
				{//要调整下午时间，小时加12
					iHr = ConvertUtil.getValue(saTime[0], 0);
					if (iHr < 12)
						iHr += 12;
					saTime[0] = Integer.toString(iHr);
				}
				if (saTime.length > 2)
				{
					sSuffix = (saTime[0]+":"+(saTime[1] == "" ? "00" : saTime[1])
							+":"+(saTime[2] == "" ? "00" : saTime[2]));
					//添加毫秒值
					//if (saTime.length() > 3)
					//  sSuffix = sSuffix + "." + saTime[3];
				}
				else if (saTime.length == 2)
				{
					sSuffix = (saTime[0]+":"+(saTime[1] == "" ? "00" : saTime[1])
							+":00");
				}
				else
				{//saTime.length()==1
					sSuffix = (saTime[0]+":00:00");
				}
			}
			else
			{//只有日期部分
				sPrefix = sR;
				sSuffix = "";
			}

			sR = sPrefix.replace(" ", "");
			if (sR.isEmpty())
				sR = sSuffix;
			else if (!CheckUtil.isNullorEmpty(sSuffix))
			{
				sR = (sR+" "+sSuffix);
			}
			//删除时间后面的无效内容
			iTemp = sR.lastIndexOf(':') + 1;
			if (iTemp > 0)
			{
				//如果有毫秒值
				//int iMs = sR.lastIndexOf('.') + 1;
				//if (iMs > 0)
				//  iTemp = iMs;
				while (iTemp < sR.length() && Character.isDigit(sR.charAt(iTemp)))
					++iTemp;
				if (iTemp < sR.length())
					sR = sR.substring(0, iTemp);
			}
		}
		catch(Exception ex)
		{
		    System.out.println(ex.getMessage());
			sR = "";
		}
		return sR;
	}
}
