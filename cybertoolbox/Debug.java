/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	Debug.java
*
******************************************************************/

public final class Debug {
	public static final boolean enabled = false;
	public static final void assert(boolean b, String s) {
		if (enabled && !b)
			throw new AssertionException("CyberToolbox assertion failed : " + s);
	}
	public static final void message(String s) {
		if (enabled == true)
			System.out.println("CyberToolbox message : " + s);
	}
	public static final void warning(String s) {
		if (enabled == true)
			System.out.println("CyberToolbox warning : " + s);
	}
}