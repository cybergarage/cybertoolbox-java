/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : PROTOParameter.java
*
******************************************************************/

package vrml.parser;

import vrml.util.*;

public class ProtoParameter extends LinkedListNode {

	public ProtoParameter(String type, String name, String value) {
		setType(type);
		setName(name);
		setValue(value);
	}
	
	////////////////////////////////////////////////
	//	Type
	////////////////////////////////////////////////

	protected String	mType;

	public void setType(String type) {
		mType = type;
	}

	public String getType() {
		return mType;
	}
	
	////////////////////////////////////////////////
	//	Name
	////////////////////////////////////////////////

	protected String	mName;

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	////////////////////////////////////////////////
	//	Defalut Value
	////////////////////////////////////////////////

	protected String	mDefalutValue;

	public void setValue(String value) {
		mDefalutValue = value;
	}

	public String getValue() {
		return mDefalutValue;
	}

	////////////////////////////////////////////////
	//	Next node 
	////////////////////////////////////////////////

	public ProtoParameter next() {
		return (ProtoParameter)getNextNode();
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return "field " + getType() + " " + getName() + " " + getValue();
	}
}
