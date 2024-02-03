/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogVec3f.java
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

public class DialogSFVec3f extends DialogSField {

	private DialogValueFieldComponent mXTextFieldComponent = null;
	private DialogValueFieldComponent mYTextFieldComponent = null;
	private DialogValueFieldComponent mZTextFieldComponent = null;
	
	public DialogSFVec3f(Frame parentFrame, SFVec3f field) {
		super(parentFrame, field);
		
		mXTextFieldComponent = new DialogValueFieldComponent("X");
		mYTextFieldComponent = new DialogValueFieldComponent("Y");
		mZTextFieldComponent = new DialogValueFieldComponent("Z");
		
		mXTextFieldComponent.setText(Float.toString(field.getX()));
		mYTextFieldComponent.setText(Float.toString(field.getY()));
		mZTextFieldComponent.setText(Float.toString(field.getZ()));
		
		JComponent dialogComponent[] = new JComponent[3];
		dialogComponent[0] = mXTextFieldComponent;
		dialogComponent[1] = mYTextFieldComponent;
		dialogComponent[2] = mZTextFieldComponent;
		
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

	public String getStringValue() {
		return getXValue() + " " + getYValue() + " " + getZValue();
	}
}
		
