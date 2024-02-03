/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogSFColor.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class DialogSFColor extends DialogSField {
	
	private Component			mParentComponent	= null;
	private Color				mInitialColor 		= new Color(1, 1, 1);
	private Color				mSelectedColor		= new Color(1, 1, 1);
	private JColorChooser	mJColorChooser		= new JColorChooser();
	private SFColor			mColorField			= null;
	
	public DialogSFColor(Frame parentFrame, SFColor field) {
		super(parentFrame, field);
		
		mParentComponent = parentFrame;
		mColorField = field;
		
		mInitialColor = new Color(field.getRed(), field.getGreen(), field.getBlue());
	}

	public int doModal() {
		Color color = mJColorChooser.showDialog(mParentComponent, getTitle(), mInitialColor);
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

	public String getStringValue() {
		return getRed() + " " + getGreen() + " " + getBlue();
	}
}
		
