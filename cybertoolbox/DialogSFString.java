/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogSFString.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.field.*;

public class DialogSFString extends DialogSField {
	
	private DialogTextFieldComponent mValueFieldComponent = null;
	
	public DialogSFString(Frame parentFrame, SFString field) {
		super(parentFrame, field);
		
		mValueFieldComponent = new DialogTextFieldComponent(field.getName());
		mValueFieldComponent.setText(field.getValue());
	
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mValueFieldComponent;
		setComponents(dialogComponent);
	}
	
	public String getStringValue() {
		return mValueFieldComponent.getText();
	}
}
		
