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

public class DialogSFVec2f extends DialogSField {

	private DialogValueFieldComponent mXTextFieldComponent = null;
	private DialogValueFieldComponent mYTextFieldComponent = null;
	
	public DialogSFVec2f(Frame parentFrame, SFVec2f field) {
		super(parentFrame, field);
		
		mXTextFieldComponent = new DialogValueFieldComponent("X");
		mYTextFieldComponent = new DialogValueFieldComponent("Y");
		
		mXTextFieldComponent.setText(Float.toString(field.getX()));
		mYTextFieldComponent.setText(Float.toString(field.getY()));
		
		JComponent dialogComponent[] = new JComponent[2];
		dialogComponent[0] = mXTextFieldComponent;
		dialogComponent[1] = mYTextFieldComponent;
		
		setComponents(dialogComponent);
	}
	
	public String getXValue() {
		return mXTextFieldComponent.getText();
	}

	public String getYValue() {
		return mYTextFieldComponent.getText();
	}

	public String getStringValue() {
		return getXValue() + " " + getYValue();
	}
}
		
