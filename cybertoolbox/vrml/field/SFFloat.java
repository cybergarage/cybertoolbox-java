/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFFloat.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class SFFloat extends Field {

	private FloatValue mValue = new FloatValue(0.0f); 

	public SFFloat() {
		setType(fieldTypeSFFloat);
		setValue(0.0f);
	}

	public SFFloat(SFFloat value) {
		setType(fieldTypeSFFloat);
		setValue(value);
	}

	public SFFloat(ConstSFFloat value) {
		setType(fieldTypeSFFloat);
		setValue(value);
	}

	public SFFloat(float value) {
		setType(fieldTypeSFFloat);
		setValue(value);
	}

	public SFFloat(String value) {
		setType(fieldTypeSFFloat);
		setValue(value);
	}

	public void setValue(float value) {
		synchronized (mValue) {
			mValue.setValue(value);
		}
	}

	public void setValue(SFFloat fvalue) {
		setValue(fvalue.getValue());
	}

	public void setValue(ConstSFFloat fvalue) {
		setValue(fvalue.getValue());
	}

	public void setValue(String value) {
		try {
			Float fvalue = new Float(value);
			setValue(fvalue.floatValue());
		}
		catch (NumberFormatException e) {}
	}

	public float getValue() {
		float value;
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
			mValue = (FloatValue)object;
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
		Float value = new Float(getValue());
		return value.toString();
	}
}