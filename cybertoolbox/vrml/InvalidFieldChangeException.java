/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : InvalidFieldChangeException.java
*
******************************************************************/

package vrml;

public class InvalidFieldChangeException extends IllegalArgumentException {
	public InvalidFieldChangeException(){
		super();
	}
	public InvalidFieldChangeException(String s){
		super(s);
	}
}