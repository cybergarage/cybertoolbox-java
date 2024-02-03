/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstSFString.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class ConstSFString extends ConstField {

	private StringValue mValue = new StringValue(null); 

	public ConstSFString() {
		setType(fieldTypeConstSFString);
	}

	public ConstSFString(SFString string) {
		setType(fieldTypeConstSFString);
		setValue(string);
	}

	public ConstSFString(ConstSFString string) {
		setType(fieldTypeConstSFString);
		setValue(string);
	}

	public ConstSFString(String value) {
		setType(fieldTypeConstSFString);
		setValue(value);
	}

	public void setValue(String value) {
		synchronized (mValue) {
			mValue.setValue(value);
		}
	}

	public void setValue(SFString value) {
		setValue(value.getValue());
	}

	public void setValue(ConstSFString value) {
		setValue(value.getValue());
	}

	public String getValue() {
		String value;
		synchronized (mValue) {
			value = mValue.getValue();
		}
		return value;
	}

	////////////////////////////////////////////////
	//	Object
	////////////////////////////////////////////////

	public void setObject(Object object) {
		synchronized (mValue) {
			mValue = (StringValue)object;
		}
	}

	public Object getObject() {
		Object object;
		synchronized (mValue) {
			object = mValue;
		}
		return object;
	}
	
	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return "\"" + getValue() + "\"";
	}

	////////////////////////////////////////////////
	//	Referrence Field Object 
	////////////////////////////////////////////////

	public Field createReferenceFieldObject() {
		SFString field = new SFString();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}