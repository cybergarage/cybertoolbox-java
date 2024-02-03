/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : IntValue.java
*
******************************************************************/

package vrml.field;

import vrml.*;

public class IntValue extends Object {
	
	private int mValue; 

	public IntValue(int value) {
		setValue(value);
	}

	public void setValue(int value) {
		mValue = value;
	}
	
	public int getValue() {
		return mValue;
	}	
}
