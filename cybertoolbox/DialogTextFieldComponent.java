/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogTextFieldComponent.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class DialogTextFieldComponent extends JPanel {
	
	private JTextField mValueField = null;
		
	public DialogTextFieldComponent(String title) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(title));
		mValueField = new JTextField();
		add(mValueField, BorderLayout.NORTH);
	}
		
	public void setText(String text) {
		mValueField.setText(text);
	}

	public String getText() {
		return mValueField.getText();
	}
}
