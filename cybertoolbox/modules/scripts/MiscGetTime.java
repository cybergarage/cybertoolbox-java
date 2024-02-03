/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MiscGetTime.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;
/************ JDK v1.1 *********************** 
import java.util.Calendar;
import java.util.GregorianCalendar;
************* JDK v1.1 ***********************/
import java.util.Date;
import java.util.TimeZone;

public class MiscGetTime extends Script {

	SFString			hoursValue;
	SFString			minutesValue;
	SFString			secondsValue;
/************ JDK v1.02 ***********************/
	long				offset;
/**********************************************/
/************ JDK v1.1 *********************** 
	GregorianCalendar	calendar; 
**********************************************/
	Integer				hours;
	Integer				minutes;
	Integer				seconds;

    public void initialize () {
		hoursValue		= (SFString)getEventOut("hoursValue"); 
		minutesValue	= (SFString)getEventOut("minutesValue"); 
		secondsValue	= (SFString)getEventOut("secondsValue"); 
/************ JDK v1.02 ***********************/
		offset			= (TimeZone.getDefault()).getRawOffset();
/**********************************************/
/************ JDK v1.1 *********************** 
		calendar		= new GregorianCalendar(TimeZone.getDefault());
**********************************************/
    }
    public void processEvent (Event e){
		double	time = e.getTimeStamp();
/************ JDK v1.02 ***********************/
		Date date = new Date((long)(time*1000.0)); 
		hours	= new Integer(date.getHours());
		minutes	= new Integer(date.getMinutes());
		seconds	= new Integer(date.getSeconds());
/**********************************************/
/************ JDK v1.1 *********************** 
		calendar.setTime(new Date((long)(time * 1000.0))); 
		hours	= new Integer(calendar.get(Calendar.HOUR_OF_DAY));
		minutes	= new Integer(calendar.get(Calendar.MINUTE));
		seconds	= new Integer(calendar.get(Calendar.SECOND));
**********************************************/
		hoursValue.setValue(hours.toString());
		minutesValue.setValue(minutes.toString());
		secondsValue.setValue(seconds.toString());
    }

}
