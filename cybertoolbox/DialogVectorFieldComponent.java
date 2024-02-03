/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogVectorFieldComponent.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class DialogVectorFieldComponent extends JPanel {
	
	private DialogValueFieldComponent mXTextFieldComponent = null;
	private DialogValueFieldComponent mYTextFieldComponent = null;
	private DialogValueFieldComponent mZTextFieldComponent = null;
		
	public DialogVectorFieldComponent(String title) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder(title));
		
		mXTextFieldComponent = new DialogValueFieldComponent("X");
		mYTextFieldComponent = new DialogValueFieldComponent("Y");
		mZTextFieldComponent = new DialogValueFieldComponent("Z");

		mXTextFieldComponent.setText("0");
		mYTextFieldComponent.setText("0");
		mZTextFieldComponent.setText("0");
		
		add(mXTextFieldComponent);
		add(mYTextFieldComponent);
		add(mZTextFieldComponent);
	}

	public void setXString(String value) {
		mXTextFieldComponent.setText(value);
	}

	public void setYString(String value) {
		mYTextFieldComponent.setText(value);
	}

	public void setZString(String value) {
		mZTextFieldComponent.setText(value);
	}

	public String getXString() {
		return mXTextFieldComponent.getText();
	}

	public String getYString() {
		return mYTextFieldComponent.getText();
	}

	public String getZString() {
		return mZTextFieldComponent.getText();
	}

	public void setXValue(double value) {
		 mXTextFieldComponent.setValue(value);
	}

	public void setYValue(double value) {
		 mYTextFieldComponent.setValue(value);
	}

	public void setZValue(double value) {
		 mZTextFieldComponent.setValue(value);
	}

	public void setXValue(float value) {
		 mXTextFieldComponent.setValue(value);
	}

	public void setYValue(float value) {
		 mYTextFieldComponent.setValue(value);
	}

	public void setZValue(float value) {
		 mZTextFieldComponent.setValue(value);
	}

	public void setXValue(int value) {
		 mXTextFieldComponent.setValue(value);
	}

	public void setYValue(int value) {
		 mYTextFieldComponent.setValue(value);
	}

	public void setZValue(int value) {
		 mZTextFieldComponent.setValue(value);
	}
		
	public double getXValue() {
		return mXTextFieldComponent.getValue();
	}

	public double getYValue() {
		return mYTextFieldComponent.getValue();
	}

	public double getZValue() {
		return mZTextFieldComponent.getValue();
	}
}
