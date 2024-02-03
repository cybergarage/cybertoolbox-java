/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : DoubleValue.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class DoubleValue extends Object {
	
	private double mValue; 

	public DoubleValue(double value) {
		setValue(value);
	}
	
	public void setValue(double value) {
		mValue = value;
	}
	
	public double getValue() {
		return mValue;
	}	
}
