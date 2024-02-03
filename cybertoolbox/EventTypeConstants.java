/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	EventTypeConstants.java
*
******************************************************************/

interface EventTypeConstants {
	public static final String EVENTTYPE_DATA[][] = {
		{"Start",	"System",	"TimeSensor",			"SFTime",	"time"},
		{"Frame",	"System",	"TimeSensor",			"SFTime",	"cycleTime"},
		{"Clock",	"User",		"TimeSensor",			"SFTime",	"cycleTime"},
		{"Pickup",	"User",		"TouchSensor",			"SFBool",	"isActive"},
		{"Area",		"User",		"ProximitySensor",	"SFBool",	"isActive"},
		{"Drag",		"User",		"PlaneSensor",			"SFVec3f",	"translation_changed"},
		{"Timer",	"User",		"TimeSensor",			"SFBool",	"isActive"},
	};

	public static final int EVENTTYPE_START		= 0;
	public static final int EVENTTYPE_FRAME		= 1;
	public static final int EVENTTYPE_CLOCK		= 2;
	public static final int EVENTTYPE_PICKUP	= 3;
	public static final int EVENTTYPE_AREA		= 4;
	public static final int EVENTTYPE_DRAG		= 5;
	public static final int EVENTTYPE_TIMER		= 6;

	public static final String	EVENTTYPE_ATTRIBUTE_SYSTEM_STRING	= "SYSTEM";
	public static final String	EVENTTYPE_ATTRIBUTE_USER_STRING		= "USER";
	public static final int	EVENTTYPE_ATTRIBUTE_SYSTEM					= 0;
	public static final int	EVENTTYPE_ATTRIBUTE_USER					= 1;

	public static final String EVENTTYPE_CLASSNAME_SYSTEM_STRING	= "System";

	/* Start Event (System Event)*/
	public static final String EVENTTYPE_START_SCRIPT_NAME				= "SystemStart.class";
	public static final String EVENTTYPE_START_SCRIPT_EVENTIN_NAME		= "EVENT_START_TIME";
	public static final String EVENTTYPE_START_SCRIPT_EVENTOUT_NAME	= "EVENT_START_ENABLED";

	/* Timer Event (User Event)*/
	public static final String EVENTTYPE_TIMER_SCRIPT_NAME				= "SystemSetTimer.class";
	public static final String EVENTTYPE_TIMER_SCRIPT_VALUE_NAME		= "EVENT_TIMER_VALUE";
	public static final String EVENTTYPE_TIMER_SCRIPT_EVENTIN_NAME		= "EVENT_TIMER_EVENTIN";
	public static final String EVENTTYPE_TIMER_SCRIPT_STARTTIME_NAME	= "EVENT_TIMER_STARTTIME";
	public static final String EVENTTYPE_TIMER_SCRIPT_STOPTIME_NAME	= "EVENT_TIMER_STOPTTIME";
}
