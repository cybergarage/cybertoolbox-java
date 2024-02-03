/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleFilterHigh.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleFilterHigh extends Dialog {
	private DialogValueFieldComponent mTextFieldComponent = null;
	
	public DialogModuleFilterHigh(Frame parentFrame, Module module) {
		super(parentFrame, "Module Filter::High");
		mTextFieldComponent = new DialogValueFieldComponent("High value");
		mTextFieldComponent.setText(module.getValue());
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mTextFieldComponent;
		setComponents(dialogComponent);
	}
	
	public String getHighValue() {
		return mTextFieldComponent.getText();
	}
}
		
