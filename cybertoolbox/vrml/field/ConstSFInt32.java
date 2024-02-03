/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstSFInt32.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class ConstSFInt32 extends ConstField {

	private IntValue mValue = new IntValue(0); 

	public ConstSFInt32() {
		setType(fieldTypeConstSFInt32);
		setValue(0);
	}

	public ConstSFInt32(SFInt32 value) {
		setType(fieldTypeConstSFInt32);
		setValue(value);
	}

	public ConstSFInt32(ConstSFInt32 value) {
		setType(fieldTypeConstSFInt32);
		setValue(value);
	}

	public ConstSFInt32(int value) {
		setType(fieldTypeConstSFInt32);
		setValue(value);
	}

	public ConstSFInt32(String value) {
		setType(fieldTypeConstSFInt32);
		setValue(value);
	}

	public void setValue(int value) {
		synchronized (mValue) {
			mValue.setValue(value);
		}
	}

	public void setValue(SFInt32 value) {
		setValue(value.getValue());
	}

	public void setValue(ConstSFInt32 value) {
		setValue(value.getValue());
	}

	public void setValue(String value) {
		try {
			Integer ivalue = new Integer(value);
			setValue(ivalue.intValue());
		}
		catch (NumberFormatException e) {}
	}

	public int getValue() {
		int value;
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
			mValue = (IntValue)object;
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
		Integer value = new Integer(getValue());
		return value.toString();
	}

	////////////////////////////////////////////////
	//	Referrence Field Object 
	////////////////////////////////////////////////

	public Field createReferenceFieldObject() {
		SFInt32 field = new SFInt32();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}