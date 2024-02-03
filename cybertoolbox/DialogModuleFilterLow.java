/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleFilterLow.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleFilterLow extends Dialog {
	private DialogValueFieldComponent mTextFieldComponent = null;
	
	public DialogModuleFilterLow(Frame parentFrame, Module module) {
		super(parentFrame, "Module Filter::Low");
		mTextFieldComponent = new DialogValueFieldComponent("Low value");
		mTextFieldComponent.setText(module.getValue());
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mTextFieldComponent;
		setComponents(dialogComponent);
	}
	
	public String getLowValue() {
		return mTextFieldComponent.getText();
	}
}
		
