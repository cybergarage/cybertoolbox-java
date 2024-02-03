/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : InvalidExposedFieldException.java
*
******************************************************************/

package vrml;

public class InvalidExposedFieldException extends IllegalArgumentException {
	public InvalidExposedFieldException(){
		super();
	}
	public InvalidExposedFieldException(String s){
		super(s);
	}
}