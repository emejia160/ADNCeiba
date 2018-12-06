package com.ceiba.parking.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.ceiba.parking.domain.Constants;

public class DateUtil {

	public static String getCurrentDateAndTime() {
		
		Date date = new Date();
		String strDateFormat = Constants.DATE_FORMAT_ddMMyyy;
	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	    
	    return dateFormat.format(date);
	}
	
	public static int getCurrentDayOfWeek() {
		
		Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        
        return  localCalendar.get(Calendar.DAY_OF_WEEK);

	}
	
}
