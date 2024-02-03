/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	ModuleConstants.java
*
******************************************************************/

public interface ModuleConstants {
	public static final int MODULE_SIZE						= 32;
	public static final int MODULE_NODE_SIZE				= 5;
	public static final int MODULE_NODE_SPACING				= ((MODULE_SIZE - MODULE_NODE_SIZE * ModuleTypeConstants.MODULE_INOUTNODE_MAXNUM) / (ModuleTypeConstants.MODULE_INOUTNODE_MAXNUM+1));

	public static final String MODULE_SYSTEMEVENT_FIELDNAME_HEADSTRING	= "SYSTEM_EVENTIN";
	
	public static final String MODULE_CLASSNAME_FIELDNAME		= "SYSTEM_FIELD_MODULE_CLASSNAME";
	public static final String MODULE_MODULENAME_FIELDNAME		= "SYSTEM_FIELD_MODULE_NAME";
	public static final String MODULE_XPOS_FIELDNAME			= "SYSTEM_FIELD_MODULE_XPOS";
	public static final String MODULE_YPOS_FIELDNAME			= "SYSTEM_FIELD_MODULE_YPOS";
	public static final String MODULE_VALUE_FIELDNAME			= "SYSTEM_FIELD_MODULE_VALUE";
	public static final String MODULE_EXECUTION_EVENTINNAME		= "SYSTEM_EVENTIN_MODULE_EXECUTION";

	public static final String MODULE_TARGET_NODENAME			= "SYSTEM_FIELD_MODULE_TARGET_NODE";
	public static final String MODULE_TARGET_FIELDNAME			= "SYSTEM_FIELD_MODULE_TARGET_FILED";
	public static final String MODULE_SOURCE_NODENAME			= "SYSTEM_FIELD_MODULE_SOURCE_NODE";

	public static final int MODULE_OUTSIDE					= 0x000;
	public static final int MODULE_INSIDE					= 0x100;
	public static final int MODULE_INSIDE_INNODE			= 0x200;	
	public static final int MODULE_INSIDE_OUTNODE			= 0x400;
	public static final int MODULE_INSIDE_EXECUTIONNODE		= 0x800;

	public static final String MODULE_NONE_VALUE			= "MODULE_NONE";
	public static final int MODULE_NODE_NUMBER_MASK			= 0xff;
}
