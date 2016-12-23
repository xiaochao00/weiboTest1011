package edu.shmtu.nlap.weibo.catagory.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 规格化时间类
 * @author Administrator
 *
 */
public class DateUtils {
	//没实现
	public static Date formatDate(Date date){
		if(date==null)
			return null;
		Date fdate = null;
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = matter.format(date);
		try {
			fdate = matter.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fdate;
	}
	public static String formatDateToString(Date date){
		if(date==null)
			return null;
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = matter.format(date);
		return dateStr;
	}
	public static String formatDateToString2(Date date){
		if(date==null)
			return null;
		SimpleDateFormat matter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String dateStr = matter.format(date);
		return dateStr;
	}
	/**
	 * 获取两个时间内的差值 拼接字符串
	 * @param date1
	 * @param date2
	 * date2>=date1
	 * @return
	 */
	public static String subDate(Date date1,Date date2){
		long sub = date2.getTime()-date1.getTime();
		long day = sub/(1000*60*60*24);
		long hour = sub/(1000*60*60)-day*24;
		long min = sub/(1000*60)-hour*60-day*24*60;
		long second = sub/(1000)-min*60-hour*60*60-day*24*60*60;
		return day+"天"+hour+"时"+min+"分"+second+"秒";
	}
}
