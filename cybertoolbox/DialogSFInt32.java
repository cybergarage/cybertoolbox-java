/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogSFInt32.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.field.*;

public class DialogSFInt32 extends DialogSField {
	
	private DialogValueFieldComponent mValueFieldComponent = null;
	
	public DialogSFInt32(Frame parentFrame, SFInt32 field) {
		super(parentFrame, field);
		
		mValueFieldComponent = new DialogValueFieldComponent(field.getName());
		mValueFieldComponent.setText(Integer.toString(field.getValue()));
		
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mValueFieldComponent;
		setComponents(dialogComponent);
	}
	
	public String getStringValue() {
		return mValueFieldComponent.getText();
	}
}
		
