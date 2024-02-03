/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	WarningMessageDialog.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;

public class WarningMessageDialog extends Object {
	public WarningMessageDialog(Component parentComponent, String message) {
		new MessageBeep();
		JOptionPane.showMessageDialog(parentComponent, message, "Warning", JOptionPane.WARNING_MESSAGE);
	}
}
