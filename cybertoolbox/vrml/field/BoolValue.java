/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : BoolValue.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class BoolValue extends Object {
	
	private boolean mValue; 

	public BoolValue(boolean value) {
		setValue(value);
	}
	
	public void setValue(boolean value) {
		mValue = value;
	}
	
	public boolean getValue() {
		return mValue;
	}	
}
