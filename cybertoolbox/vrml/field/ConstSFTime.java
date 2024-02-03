/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstSFTime.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class ConstSFTime extends ConstField {

	private DoubleValue mValue = new DoubleValue(0); 
	
	public ConstSFTime() {
		setType(fieldTypeConstSFTime);
		setValue(-1);
	}

	public ConstSFTime(SFTime time) {
		setType(fieldTypeConstSFTime);
		setValue(time);
	}

	public ConstSFTime(ConstSFTime time) {
		setType(fieldTypeConstSFTime);
		setValue(time);
	}

	public ConstSFTime(double value) {
		setType(fieldTypeConstSFTime);
		setValue(value);
	}

	public ConstSFTime(String value) {
		setType(fieldTypeConstSFTime);
		setValue(value);
	}

	public void setValue(double value) {
		synchronized (mValue) {
			mValue.setValue(value);
		}
	}

	public void setValue(SFTime value) {
		setValue(value.getValue());
	}

	public void setValue(ConstSFTime value) {
		setValue(value.getValue());
	}

	public void setValue(String value) {
		try {
			Double dvalue = new Double(value);
			setValue(dvalue.doubleValue());
		}
		catch (NumberFormatException e) {}
	}

	public double getValue() {
		double value;
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
			mValue = (DoubleValue)object;
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
		Double value = new Double(getValue());
		return value.toString();
	}

	////////////////////////////////////////////////
	//	Referrence Field Object 
	////////////////////////////////////////////////

	public Field createReferenceFieldObject() {
		SFTime field = new SFTime();
		field.setName(getName());
		field.setObject(getObject());
		return field;
	}
}