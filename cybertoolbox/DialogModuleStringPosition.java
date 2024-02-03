/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleStringPosition.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleStringPosition extends Dialog {
	private DialogValueFieldComponent mXTextFieldComponent = null;
	private DialogValueFieldComponent mYTextFieldComponent = null;
	private DialogValueFieldComponent mZTextFieldComponent = null;
	
	public DialogModuleStringPosition(Frame parentFrame, Module module) {
		super(parentFrame, "Module String::Position");
		
		mXTextFieldComponent = new DialogValueFieldComponent("X");
		mYTextFieldComponent = new DialogValueFieldComponent("Y");
		mZTextFieldComponent = new DialogValueFieldComponent("Z");
		
		String moduleValue = module.getValue();
		int index1 = moduleValue.indexOf(',');
		int index2 = moduleValue.indexOf(',', index1+1);
		if (index1 != -1 && index2 != -1 && index1 < index2) {
			mXTextFieldComponent.setText(new String(moduleValue.toCharArray(), 0, (index1-1)+1));
			mYTextFieldComponent.setText(new String(moduleValue.toCharArray(), index1+1, (index2-1)-(index1)));
			mZTextFieldComponent.setText(new String(moduleValue.toCharArray(), index2+1, (moduleValue.length()-1) - index2));
		}
		else {
			mXTextFieldComponent.setText("0");
			mYTextFieldComponent.setText("0");
			mZTextFieldComponent.setText("0");
		}
		
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
}
		
