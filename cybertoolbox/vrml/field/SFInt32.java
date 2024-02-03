/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFInt32.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class SFInt32 extends Field {

	private IntValue mValue = new IntValue(0); 

	public SFInt32() {
		setType(fieldTypeSFInt32);
		setValue(0);
	}

	public SFInt32(SFInt32 value) {
		setType(fieldTypeSFInt32);
		setValue(value);
	}

	public SFInt32(ConstSFInt32 value) {
		setType(fieldTypeSFInt32);
		setValue(value);
	}

	public SFInt32(int value) {
		setType(fieldTypeSFInt32);
		setValue(value);
	}

	public SFInt32(String value) {
		setType(fieldTypeSFInt32);
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
			Float ivalue = new Float(value);
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
}