/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	EventType.java
*
******************************************************************/

import vrml.*;
import vrml.field.*;
import vrml.util.*;

////////////////////////////////////////////////////////////
//	Event Type File Format
////////////////////////////////////////////////////////////

/**********************************************************

  ==========================================================

  #CTB EVENTTYPE V1.0

  EVENTTYPENAME EVENTATTRIBUTE SENSORNODETYPE EVENTFIELDTYPE EVENTFIELDNAME
  ............. ............. .............. .............. ..............

  ==========================================================

  EVENTATTRIBUTE	:	"SYSTEM"
					|	"USER"
					;

  SENSORNODETYPE	:	CylinderSensor
					|	PlaneSesor
					|	ProximitySesor
					|	SphereSesor
					|	TimeSesor
					|	TouchSesor
					|	VisibilitySesor
					|	TimeSesor
					;

  EVENTFIELDTYPE	:	SFBool
					|	SFColor	
					|	SFFloat	
					|	SFImage	
					|	SFInt32	
					|	SFRotation	
					|	SFString	
					|	SFVec2f	
					|	SFVec3f	
					;

**********************************************************/

public class EventType extends LinkedListNode implements EventTypeConstants, ModuleConstants {

	private	String		mName				= null;
	private	String		mNodeTypeName		= null;
	private	int			mEventAttributeType;
	private	String		mEventFieldTypeName	= null;
	private	String		mEventFieldName		= null;

	public EventType(String name, String attributeTypeName, String nodeTypeName, String fieldTypeName, String fieldName) {
		setHeaderFlag(false);
		setName(name);
		setAttributeType((attributeTypeName.equalsIgnoreCase(EVENTTYPE_ATTRIBUTE_SYSTEM_STRING) == true) ? EVENTTYPE_ATTRIBUTE_SYSTEM : EVENTTYPE_ATTRIBUTE_USER);
		setNodeTypeName(nodeTypeName);
		setFieldTypeName(fieldTypeName);
		setFieldName(fieldName);
	}

	//////////////////////////////////////////////////
	// Name
	//////////////////////////////////////////////////

	public void	setName(String name) {
		mName = name;
	}
	
	public String getName() {
		return mName;
	}

	//////////////////////////////////////////////////
	// Attribute Type
	//////////////////////////////////////////////////

	public void	setAttributeType(int type) {
		mEventAttributeType = type;
	}
	
	public int getAttributeType() {
		return mEventAttributeType;
	}
	
	String getAttributeTypeString() {
		return (mEventAttributeType == EVENTTYPE_ATTRIBUTE_SYSTEM) ? 
			EVENTTYPE_ATTRIBUTE_SYSTEM_STRING : EVENTTYPE_ATTRIBUTE_USER_STRING;
	}

	//////////////////////////////////////////////////
	// Node Name
	//////////////////////////////////////////////////

	public void	setNodeTypeName(String name) {
		mNodeTypeName = name;
	}
	
	public String getNodeTypeName() {
		return mNodeTypeName;
	}

	//////////////////////////////////////////////////
	// Field Type
	//////////////////////////////////////////////////

	public void	setFieldTypeName(String typeName) {
		mEventFieldTypeName = typeName;
	}
	
	public String getFieldTypeName() {
		return mEventFieldTypeName;
	}

	//////////////////////////////////////////////////
	// Field Name
	//////////////////////////////////////////////////

	public void	setFieldName(String name) {
		mEventFieldName = name;
	}
	
	public String getFieldName() {
		return mEventFieldName;
	}

	//////////////////////////////////////////////////
	// Moudle Field Name
	//////////////////////////////////////////////////

	public String getModuleFieldName() {
		return (MODULE_SYSTEMEVENT_FIELDNAME_HEADSTRING + "_" + getName() + "_" + getNodeTypeName() + "_" + getFieldName());
	}

	//////////////////////////////////////////////////
	// next
	//////////////////////////////////////////////////

	public EventType next() {
		return (EventType)getNextNode();
	}

	//////////////////////////////////////////////////
	// toString
	//////////////////////////////////////////////////
	
	public String toString() {
		return getName() + " " 	
			+ (getAttributeType() == EVENTTYPE_ATTRIBUTE_SYSTEM ? EVENTTYPE_ATTRIBUTE_SYSTEM_STRING : EVENTTYPE_ATTRIBUTE_USER_STRING) + " "
			+ getNodeTypeName() + " " 
			+ getFieldTypeName() + " "
			+ getFieldName();
	}
}

