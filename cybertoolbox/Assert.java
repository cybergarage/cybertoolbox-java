/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	Assert.java
*
******************************************************************/

public final class Assert {
	public static final boolean enabled = true;
	public static final void assert(boolean b, String s) {
		if (enabled && !b)
			throw new AssertionException("assertion failed in " + s);
	}
}