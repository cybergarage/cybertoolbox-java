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

public class DialogValueFieldComponent extends JPanel {
	
	private JTextField mValueField = null;
		
	public DialogValueFieldComponent(String title) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(title));
		mValueField = new JTextField();
		mValueField.setHorizontalAlignment(JTextField.RIGHT);
		add(mValueField, BorderLayout.NORTH);
	}
		
	public void setText(String text) {
		mValueField.setText(text);
	}

	public String getText() {
		return mValueField.getText();
	}

	public void setValue(int value) {
		mValueField.setText(Integer.toString(value));
	}
	
	public void setValue(float value) {
		mValueField.setText(Float.toString(value));
	}
	
	public void setValue(double value) {
		mValueField.setText(Double.toString(value));
	}

	public double getValue() {
		Double doubleValue = new Double(getText());
		return doubleValue.doubleValue();
	}
}
