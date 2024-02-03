/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleStringConstant.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleStringValue extends Dialog {
	private DialogTextFieldComponent mNameTextFieldComponent = null;
	
	public DialogModuleStringValue(Frame parentFrame, Module module) {
		super(parentFrame, "Module String::Value");
		mNameTextFieldComponent = new DialogTextFieldComponent("Value");
		mNameTextFieldComponent.setText(module.getValue());
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mNameTextFieldComponent;
		setComponents(dialogComponent);
	}
	
	public String getConstantValue() {
		return mNameTextFieldComponent.getText();
	}
}
		
