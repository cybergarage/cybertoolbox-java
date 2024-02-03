/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstSFFloat.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class ConstSFFloat extends ConstField {

	private FloatValue mValue = new FloatValue(0.0f); 

	public ConstSFFloat() {
		setType(fieldTypeConstSFFloat);
		setValue(0.0f);
	}

	public ConstSFFloat(SFFloat value) {
		setType(fieldTypeConstSFFloat);
		setValue(value);
	}

	public ConstSFFloat(ConstSFFloat value) {
		setType(fieldTypeConstSFFloat);
		setValue(value);
	}

	public ConstSFFloat(float value) {
		setType(fieldTypeConstSFFloat);
		setValue(value);
	}

	public ConstSFFloat(String value) {
		setType(fieldTypeConstSFFloat);
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

	////////////////////////////////////////////////
	//	Referrence Field Object 
	////////////////////////////////////////////////

	public Field createReferenceFieldObject() {
		SFFloat field = new SFFloat();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}