/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleStringColor.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleStringColor extends JColorChooser {
	
	public static final int OK_OPTION = 0;
	public static final int CANCEL_OPTION = -1;

	private Component	mParentComponent	= null;
	private Color		mInitialColor 		= new Color(1, 1, 1);
	private Color		mSelectedColor		= new Color(1, 1, 1);
	
	public DialogModuleStringColor(Frame parentFrame, Module module) {
		mParentComponent = parentFrame;
		
		String moduleValue = module.getValue();
		int index1 = moduleValue.indexOf(',');
		int index2 = moduleValue.indexOf(',', index1+1);
		if (index1 != -1 && index2 != -1 && index1 < index2) {
			Float r = new Float(new String(moduleValue.toCharArray(), 0, (index1-1)+1));
			Float g = new Float(new String(moduleValue.toCharArray(), index1+1, (index2-1)-(index1)));
			Float b = new Float(new String(moduleValue.toCharArray(), index2+1, (moduleValue.length()-1) - index2));
			mInitialColor = new Color(r.floatValue(), g.floatValue(), b.floatValue());
		}
		
	}
	
	public int doModal() {
		Color color = showDialog(mParentComponent,"Module String::Color", mInitialColor);
		if (mSelectedColor == null)
			return CANCEL_OPTION;
		mSelectedColor = color;
		return OK_OPTION;
	}

	public String getColor(int n) {
		float color[] = new float[3];
		mSelectedColor.getColorComponents(color);
		return Float.toString(color[n]);
	}

	public String getRed() {
		return getColor(0);
	}

	public String getGreen() {
		return getColor(1);
	}

	public String getBlue() {
		return getColor(2);
	}

}
		
