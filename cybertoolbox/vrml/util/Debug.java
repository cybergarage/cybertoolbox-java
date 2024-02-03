/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File:	Debug.java
*
******************************************************************/

package vrml.util;

public final class Debug {
	public static final boolean enabled = false;
	public static final void assert(boolean b, String s) {
		if (enabled && !b)
			throw new AssertionException("CyberVRML97 assertion failed : " + s);
	}
	public static final void message(String s) {
		if (enabled == true)
			System.out.println("CyberVRML97 message : " + s);
	}
	public static final void warning(String s) {
		if (enabled == true)
			System.out.println("CyberVRML97 warning : " + s);
	}
}