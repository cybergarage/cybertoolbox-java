/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : InvalidFieldException.java
*
******************************************************************/

package vrml;

public class InvalidFieldException extends IllegalArgumentException { 
	public InvalidFieldException(){
		super();
	}
	public InvalidFieldException(String s){
		super(s);
	}
}
