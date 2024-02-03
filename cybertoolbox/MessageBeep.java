/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	MessageBeep.java
*
******************************************************************/

import java.awt.Toolkit;

public class MessageBeep extends Object {
	public MessageBeep() {
		Toolkit.getDefaultToolkit().beep();
	}
}
