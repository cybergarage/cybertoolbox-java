/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleStringOrientation.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleStringRotation extends Dialog {
	private DialogValueFieldComponent mXTextFieldComponent = null;
	private DialogValueFieldComponent mYTextFieldComponent = null;
	private DialogValueFieldComponent mZTextFieldComponent = null;
	private DialogValueFieldComponent mATextFieldComponent = null;
	
	public DialogModuleStringRotation(Frame parentFrame, Module module) {
		super(parentFrame, "Module String::Rotation");
		
		mXTextFieldComponent = new DialogValueFieldComponent("X");
		mYTextFieldComponent = new DialogValueFieldComponent("Y");
		mZTextFieldComponent = new DialogValueFieldComponent("Z");
		mATextFieldComponent = new DialogValueFieldComponent("Angle");
		
		String moduleValue = module.getValue();
		int index1 = moduleValue.indexOf(',');
		int index2 = moduleValue.indexOf(',', index1+1);
		int index3 = moduleValue.indexOf(',', index2+1);
		if (index1 != -1 && index2 != -1 && index2 != -1 && index1 < index2 && index2 < index3) {
			mXTextFieldComponent.setText(new String(moduleValue.toCharArray(), 0, (index1-1)+1));
			mYTextFieldComponent.setText(new String(moduleValue.toCharArray(), index1+1, (index2-1)- index1));
			mZTextFieldComponent.setText(new String(moduleValue.toCharArray(), index2+1, (index3-1)- index2));
			mATextFieldComponent.setText(new String(moduleValue.toCharArray(), index3+1, (moduleValue.length()-1) - index3));
		}
		else {
			mXTextFieldComponent.setText("0");
			mYTextFieldComponent.setText("0");
			mZTextFieldComponent.setText("1");
			mATextFieldComponent.setText("0");
		}
												
		JComponent dialogComponent[] = new JComponent[4];
		dialogComponent[0] = mXTextFieldComponent;
		dialogComponent[1] = mYTextFieldComponent;
		dialogComponent[2] = mZTextFieldComponent;
		dialogComponent[3] = mATextFieldComponent;
		
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

	public String getAngleValue() {
		return mATextFieldComponent.getText();
	}
}
		
