/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleFilterOffset.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleFilterOffset extends Dialog {
	private DialogValueFieldComponent mTextFieldComponent = null;
	
	public DialogModuleFilterOffset(Frame parentFrame, Module module) {
		super(parentFrame, "Module Filter::Offset");
		mTextFieldComponent = new DialogValueFieldComponent("Offset value");
		mTextFieldComponent.setText(module.getValue());
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mTextFieldComponent;
		setComponents(dialogComponent);
	}
	
	public String getOffsetValue() {
		return mTextFieldComponent.getText();
	}
}
		
