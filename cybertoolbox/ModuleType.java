/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	ModuleType.java
*
******************************************************************/

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.MediaTracker;

import vrml.*;
import vrml.field.*;
import vrml.util.*;

////////////////////////////////////////////////////////////
//	Module Type File Format
////////////////////////////////////////////////////////////

/**********************************************************

  =====================================

  #CTB MODULETYPE V1.0

  CLASSNAME		className
  MODULENAME	moduleName
  ATTRIBUTE		attributeType
  SCRIPTNAME	scriptFileName
  ICONNAME		iconFileName

  EVENTIN0		fieldType	filedName
  EVENTIN1		fieldType	filedName
  EVENTIN2		fieldType	filedName
  EVENTIN3		fieldType	filedName

  EVENTOUT0		fieldType	filedName
  EVENTOUTl		fieldType	filedName
  EVENTOUT2		fieldType	filedName
  EVENTOUT3		fieldType	filedName

  EXECUTIONNODE	(TURE | FALSE)

  TARGETNODE	thisEventOutFieldTypeName targetEventInFieldName

  =====================================

  attributeType	:	SYSTEM
				|	USER
				;

  fieldType		:	SFBool
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

public class ModuleType extends LinkedListNode implements WorldConstants, ModuleTypeConstants, vrml.Constants {

	private String	mClassName;
	private String	mName;
	private String	mIconName;
	private String	mScriptName;
	private Field	mEventInType[]	= new Field[MODULE_INOUTNODE_MAXNUM];
	private Field	mEventOutType[]	= new Field[MODULE_INOUTNODE_MAXNUM];
	private boolean	mExecutionNode;
	private int		mAttribure;
	private Image	mIconImage;
	
	public ModuleType() {
		setHeaderFlag(false);
		for (int n=0; n<MODULE_INOUTNODE_MAXNUM; n++) {
			mEventInType[n]		= new SFString();
			mEventOutType[n]	= new SFString();
		}
		for (int n=0; n<MODULE_INOUTNODE_MAXNUM; n++) {
			setEventInField(n, null, fieldTypeNone);
			setEventOutField(n, null, fieldTypeNone);
		}
		setExecutionNode(false);
		mIconImage = null;
	}
	
	////////////////////////////////////////
	//	Class Name
	////////////////////////////////////////

	public void setClassName(String className) {
		mClassName = className;
	}

	public String getClassName() {
		return mClassName;
	}

	////////////////////////////////////////
	//	 Name
	////////////////////////////////////////

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	////////////////////////////////////////
	//	 Attribure
	////////////////////////////////////////

	public void setAttribute(int attr) {
		mAttribure = attr;
	}

	public int getAttribute() {
		return mAttribure;
	}

	////////////////////////////////////////
	//	Icon Name
	////////////////////////////////////////

	public void setIconFileName(String iconName) {
		mIconName = iconName;

		if (mIconImage != null)
			mIconImage = null;
	}

	public String getIconFileName() {
		return mIconName;
	}

	public Image getIcon() {

		if (mIconImage == null) {
			Toolkit	toolkit = Toolkit.getDefaultToolkit();
			String		fileSep = System.getProperty("file.separator");
			
			int index = getIconFileName().lastIndexOf('.');
			String imageFilename = WORLD_MODULETYPE_ICON_DIRECTORY + fileSep + new String (getIconFileName().toCharArray(), 0, index) + ".gif";
			mIconImage = toolkit.getImage(imageFilename);
		}
		return mIconImage;
	}

	////////////////////////////////////////
	//	Script Name
	////////////////////////////////////////

	public void setScriptFileName(String scriptFileName) {
		mScriptName = scriptFileName;
	}

	public String getScriptFileName() {
		return mScriptName;
	}

	////////////////////////////////////////
	//	EventIn Type
	////////////////////////////////////////

	public void setEventInField(int n, String name, int type) {
		mEventInType[n].setName(name);
		mEventInType[n].setType(type);
	}

	public void setEventInField(int n, String name, String type) {
		mEventInType[n].setName(name);
		mEventInType[n].setType(type);
	}

	public void setEventInFieldName(int n, String name) {
		mEventInType[n].setName(name);
	}

	public void setEventInFieldType(int n, int type) {
		mEventInType[n].setType(type);
	}

	public void setEventInFieldType(int n, String type) {
		mEventInType[n].setType(type);
	}

	public String getEventInFieldName(int n) {
		return mEventInType[n].getName();
	}

	public int getEventInFieldType(int n) {
		return mEventInType[n].getType();
	}

	public String getEventInFieldTypeName(int n) {
		return mEventInType[n].getTypeName();
	}

	public Field getEventInField(int n) {
		return mEventInType[n];
	}

	public int getNEventInFields() {
		int nEventIn = 0;
		for (int n=0; n<MODULE_INOUTNODE_MAXNUM; n++) {
			if (getEventInFieldType(n) != fieldTypeNone)
				nEventIn++;
		}
		return nEventIn;
	}
	
	public boolean hasEventInFields() {
		return (0 < getNEventInFields()) ? true : false;
	}

	////////////////////////////////////////
	//	EventOut Type
	////////////////////////////////////////

	public void setEventOutField(int n, String name, int type) {
		mEventOutType[n].setName(name);
		mEventOutType[n].setType(type);
	}

	public void setEventOutField(int n, String name, String type) {
		mEventOutType[n].setName(name);
		mEventOutType[n].setType(type);
	}

	public void setEventOutFieldName(int n, String name) {
		mEventOutType[n].setName(name);
	}

	public void setEventOutFieldType(int n, String type) {
		mEventOutType[n].setType(type);
	}

	public void setEventOutFieldType(int n, int type) {
		mEventOutType[n].setType(type);
	}

	public String getEventOutFieldName(int n) {
		return mEventOutType[n].getName();
	}

	public int getEventOutFieldType(int n) {
		return mEventOutType[n].getType();
	}
	
	public String getEventOutFieldTypeName(int n) {
		return mEventOutType[n].getTypeName();
	}

	public Field getEventOutField(int n) {
		return mEventOutType[n];
	}

	public int getNEventOutFields() {
		int nEventOut = 0;
		for (int n=0; n<MODULE_INOUTNODE_MAXNUM; n++) {
			if (getEventOutFieldType(n) != fieldTypeNone)
				nEventOut++;
		}
		return nEventOut;
	}

	public boolean hasEventOutNode() {
		return (0 < getNEventOutFields()) ? true : false;
	}

	////////////////////////////////////////
	//	Execution Node
	////////////////////////////////////////

	public void setExecutionNode(boolean bvalue) {
		mExecutionNode = bvalue;
	}

	public boolean hasExecutionNode() {
		return mExecutionNode;
	}

	////////////////////////////////////////
	//	File
	////////////////////////////////////////

	public boolean load(String filename) {

		try {
			ModuleTypeFile moduleFile = new ModuleTypeFile(filename);

			moduleFile.findString(MODULETYPEFILE_CLASSNAME_STRING);		String className	= moduleFile.nextString();
			moduleFile.findString(MODULETYPEFILE_MODULENAME_STRING);	String moduleName	= moduleFile.nextString();
			moduleFile.findString(MODULETYPEFILE_ATTRIBUTE_STRING);		String attribute	= moduleFile.nextString();
			moduleFile.findString(MODULETYPEFILE_SCRIPTNAME_STRING);	String scriptName	= moduleFile.nextString();
			moduleFile.findString(MODULETYPEFILE_ICONNAME_STRING);		String iconName		= moduleFile.nextString();

			setClassName(className);
			setName(moduleName);
			setAttribute(attribute.equals(MODULETYPE_ATTRIBUTE_SYSTEM_STRING) ? MODULETYPE_ATTRIBUTE_SYSTEM : MODULETYPE_ATTRIBUTE_USER);
			setScriptFileName(scriptName);
			setIconFileName(iconName);

			for (int n=0; n<MODULE_INOUTNODE_MAXNUM; n++) {
				moduleFile.findString(new String(MODULETYPEFILE_EVENTIN_STRING + n));
				String fieldType = moduleFile.nextString();
				String fieldName = moduleFile.nextString();
				if (fieldType.equals("NULL") == false) {
					mEventInType[n].setType(fieldType);
					mEventInType[n].setName(fieldName);
				}
				else {
					mEventInType[n].setType(fieldTypeNone);
					mEventInType[n].setName(null);
				}
			}


			for (int n=0; n<MODULE_INOUTNODE_MAXNUM; n++) {
				moduleFile.findString(new String(MODULETYPEFILE_EVENTOUT_STRING + n));
				String fieldType = moduleFile.nextString();
				String fieldName = moduleFile.nextString();
				if (fieldType.equals("NULL") == false) {
					mEventOutType[n].setType("Const" + fieldType);
					mEventOutType[n].setName(fieldName);
				}
				else {
					mEventOutType[n].setType(fieldTypeNone);
					mEventOutType[n].setName(null);
				}
			}

			moduleFile.findString(MODULETYPEFILE_EXECUTIONNODE_STRING); String executionNode = moduleFile.nextString();
			setExecutionNode(executionNode.equals("TRUE"));
			
			check();
			compact();
		}
		catch (Exception e) {
			Debug.warning(e.toString() + " in ModuleType::load");
			return false;
		}
		
		return true;
	}

	////////////////////////////////////////
	//	Check
	////////////////////////////////////////

	public void check() {
		for (int n=0; n<MODULE_INOUTNODE_MAXNUM; n++) {
			if (getEventInFieldType(n) != fieldTypeNone && getEventInFieldName(n).length() == 0)
				setEventInFieldType(n, fieldTypeNone);
			if (getEventOutFieldType(n) != fieldTypeNone && getEventOutFieldName(n).length() == 0)
				setEventOutFieldType(n, fieldTypeNone);
		}
	}

	////////////////////////////////////////
	//	Compact
	////////////////////////////////////////

	public void compact(Field field[]) {
		for (int n=0; n<MODULE_INOUTNODE_MAXNUM-1; n++) {
			if (field[n].getType() == fieldTypeNone && field[n+1].getType() != fieldTypeNone) {
				for (int i=n; i<MODULE_INOUTNODE_MAXNUM-1; i++) {
					field[i].setType(field[i+1].getType());
					field[i].setName(field[i+1].getName());
				}
				field[(MODULE_INOUTNODE_MAXNUM-1)-n].setType(fieldTypeNone);
				field[(MODULE_INOUTNODE_MAXNUM-1)-n].setName(null);
			}
		}
	}
	
	public void compact() {
		compact(mEventInType);
		compact(mEventOutType);
	}
	
	////////////////////////////////////////
	//	List
	////////////////////////////////////////

	public ModuleType next(String className) {
		for (ModuleType cmType=next(); cmType != null; cmType=cmType.next()) {
			if (className.equalsIgnoreCase(cmType.getClassName()) == true) 
				return cmType;
		}
		return null;
	}
	
	public ModuleType next()	{
		return (ModuleType)getNextNode();
	}

	////////////////////////////////////////
	//	toString
	////////////////////////////////////////
	
	public String endl() {
		return "\n";
	}
	
	public String toString() {
		StringBuffer info = new StringBuffer();
		info.append(MODULETYPEFILE_CLASSNAME_STRING		+ " " + getClassName() + endl());		
		info.append(MODULETYPEFILE_MODULENAME_STRING	+ " " + getName() + endl());	
	
		if (getAttribute() == MODULETYPE_ATTRIBUTE_SYSTEM) 	
			info.append(MODULETYPEFILE_ATTRIBUTE_STRING + " " + MODULETYPE_ATTRIBUTE_SYSTEM_STRING + endl());
		else
			info.append(MODULETYPEFILE_ATTRIBUTE_STRING + " " + MODULETYPE_ATTRIBUTE_USER_STRING + endl());
					
		info.append(MODULETYPEFILE_SCRIPTNAME_STRING	+ " " + getScriptFileName() + endl());		
		info.append(MODULETYPEFILE_ICONNAME_STRING		+ " " + getIconFileName() + endl());

		for (int n=0; n<MODULE_INOUTNODE_MAXNUM; n++) {
			String fieldTypeName	= mEventInType[n].getTypeName();
			String fieldName		= mEventInType[n].getName();
			info.append(MODULETYPEFILE_EVENTIN_STRING + n + " ");
			info.append((fieldTypeName != null ? fieldTypeName : "NULL") + " ");
			info.append((fieldName != null ? fieldName : "NULL") + endl());
		}

		for (int n=0; n<MODULE_INOUTNODE_MAXNUM; n++) {
			String fieldTypeName	= mEventOutType[n].getTypeName();
			String fieldName		= mEventOutType[n].getName();
			info.append(MODULETYPEFILE_EVENTOUT_STRING + n + " ");
			info.append((fieldTypeName != null ? fieldTypeName : "NULL") + " ");
			info.append((fieldName != null ? fieldName : "NULL") + endl());
		}

		info.append(MODULETYPEFILE_EXECUTIONNODE_STRING + " " + (hasExecutionNode() ? "TRUE" : "FALSE") + endl()); 

		return info.toString();		
	}
};

