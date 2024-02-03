/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogSFRotation.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class DialogSFRotation extends DialogSField {
	private DialogValueFieldComponent mXTextFieldComponent = null;
	private DialogValueFieldComponent mYTextFieldComponent = null;
	private DialogValueFieldComponent mZTextFieldComponent = null;
	private DialogValueFieldComponent mATextFieldComponent = null;
	
	public DialogSFRotation(Frame parentFrame, SFRotation field) {
		super(parentFrame, field);
		
		mXTextFieldComponent = new DialogValueFieldComponent("X");
		mYTextFieldComponent = new DialogValueFieldComponent("Y");
		mZTextFieldComponent = new DialogValueFieldComponent("Z");
		mATextFieldComponent = new DialogValueFieldComponent("Angle");
		
		mXTextFieldComponent.setText(Float.toString(field.getX()));
		mYTextFieldComponent.setText(Float.toString(field.getY()));
		mZTextFieldComponent.setText(Float.toString(field.getZ()));
		mATextFieldComponent.setText(Float.toString(field.getAngle()));
												
		JComponent dialogComponent[] = new JComponent[4];
		dialogComponent[0] = mXTextFieldComponent;
		dialogComponent[1] = mYTextFieldComponent;
		dialogComponent[2] = mZTextFieldComponent;
		dialogComponent[3] = mATextFieldComponent;
		
		setComponents(dialogComponent);
	}
	
	public String getXValue() {
		return mXTextFieldComponent.getText();
	}

	public String getYValue() {
		return mYTextFieldComponent.getText();
	}

	public String getZValue() {
		return mZTextFieldComponent.getText();
	}

	public String getAngleValue() {
		return mATextFieldComponent.getText();
	}

	public String getStringValue() {
		return getXValue() + " " + getYValue() + " " + getZValue() + " " + getAngleValue();
	}
}
		
