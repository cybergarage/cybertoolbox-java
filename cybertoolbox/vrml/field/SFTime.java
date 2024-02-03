/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SFTime.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class SFTime extends Field {

	private DoubleValue mValue = new DoubleValue(0); 
	
	public SFTime() {
		setType(fieldTypeSFTime);
		setValue(-1);
	}

	public SFTime(SFTime time) {
		setType(fieldTypeSFTime);
		setValue(time);
	}

	public SFTime(ConstSFTime time) {
		setType(fieldTypeSFTime);
		setValue(time);
	}

	public SFTime(double value) {
		setType(fieldTypeSFTime);
		setValue(value);
	}

	public SFTime(String value) {
		setType(fieldTypeSFTime);
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
}