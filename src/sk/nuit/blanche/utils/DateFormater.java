package sk.nuit.blanche.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat") public class DateFormater {
	public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";
	public static final String FORMAT_YYYYMMDD_HHMMSS = "yyyyMMdd HH:mm:ss";
	public static final String FORMAT_YYYY_MM_DD_HHMMSS = "yyyy/MM/dd HH:mm:ss";
	
	public static Date getDateFromString(String date, String format){
		if(date == null)
			return null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String getStringfromDate(Date date, String format){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}
}
