/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleFilterRange.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleFilterRange extends Dialog {
	private DialogValueFieldComponent mHighTextFieldComponent = null;
	private DialogValueFieldComponent mLowTextFieldComponent = null;
	
	public DialogModuleFilterRange(Frame parentFrame, Module module) {
		super(parentFrame, "Module Filter::Range");
		
		mHighTextFieldComponent = new DialogValueFieldComponent("High value");
		mLowTextFieldComponent = new DialogValueFieldComponent("Low value");
		
		String moduleValue = module.getValue();
		int index = moduleValue.indexOf(',');
		if (index != -1) {
			mHighTextFieldComponent.setText(new String(moduleValue.toCharArray(), 0, (index-1)+1));
			mLowTextFieldComponent.setText(new String(moduleValue.toCharArray(), index+1, (moduleValue.length()-1) - index));
		}
				
		JComponent dialogComponent[] = new JComponent[2];
		dialogComponent[0] = mHighTextFieldComponent;
		dialogComponent[1] = mLowTextFieldComponent;
		
		setComponents(dialogComponent);
	}
	
	public String getHighValue() {
		return mHighTextFieldComponent.getText();
	}

	public String getLowValue() {
		return mLowTextFieldComponent.getText();
	}
}
		
