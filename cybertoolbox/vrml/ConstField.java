/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ConstField.java
*
******************************************************************/

package vrml;

public abstract class ConstField extends Field {
	public ConstField() {
	}
	abstract public Field createReferenceFieldObject();
};
