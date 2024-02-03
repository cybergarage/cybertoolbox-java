/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleFilterScale.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleFilterScale extends Dialog {
	private DialogValueFieldComponent mTextFieldComponent = null;
	
	public DialogModuleFilterScale(Frame parentFrame, Module module) {
		super(parentFrame, "Module Filter::Scale");
		mTextFieldComponent = new DialogValueFieldComponent("Scaling value");
		mTextFieldComponent.setText(module.getValue());
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mTextFieldComponent;
		setComponents(dialogComponent);
	}
	
	public String getScaleValue() {
		return mTextFieldComponent.getText();
	}
}
		
