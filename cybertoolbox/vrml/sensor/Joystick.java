/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Joystick.java
*
******************************************************************/

package vrml.sensor;

public class Joystick {

	static {
		System.loadLibrary("joystick");
	}		
	
	public Joystick() {
	}

	public native void displayHelloWorld();
	
	////////////////////////////////////////////////
	//	Name / Type
	////////////////////////////////////////////////

	protected String	mName;
	protected String	mType;

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public void setType(String name) {
		mType = name;
	}

	public String getType() {
		return mType;
	}

	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		return "Joystick";
	}
}
