/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogEventAreaPanel.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.field.*;

public class DialogEventAreaPanel extends DialogEventPanel {
	
	private DialogVectorFieldComponent mCenterFieldComponent = null;
	private DialogVectorFieldComponent mSizeFieldComponent = null;
	
	public DialogEventAreaPanel(World world) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		mCenterFieldComponent = new DialogVectorFieldComponent("Center");
		mSizeFieldComponent = new DialogVectorFieldComponent("Size");
		add(mCenterFieldComponent);
		add(mSizeFieldComponent);
	}

	public DialogEventAreaPanel(World world, Event event) {
		this(world);
		float value[] = event.getOptionValues();
		setAreaCenter((int)value[0], (int)value[1], (int)value[2]);
		setAreaSize((int)value[3], (int)value[4], (int)value[5]);
	}

	public void setAreaCenter(int x, int y, int z) {
		mCenterFieldComponent.setXValue(x);
		mCenterFieldComponent.setYValue(y);
		mCenterFieldComponent.setZValue(z);
	}
	
	public void setAreaCenter(int center[]) {
		setAreaCenter(center[0], center[1], center[2]);
	}
	
	public void setAreaSize(int x, int y, int z) {
		mSizeFieldComponent.setXValue(x);
		mSizeFieldComponent.setYValue(y);
		mSizeFieldComponent.setZValue(z);
	}
	
	public void setAreaSize(int center[]) {
		setAreaSize(center[0], center[1], center[2]);
	}

	public int[] getAreaCenter() {
		int center[] = new int[3];
		try {
			Double x = new Double(mCenterFieldComponent.getXValue());
			Double y = new Double(mCenterFieldComponent.getYValue());
			Double z = new Double(mCenterFieldComponent.getZValue());
			center[0] = x.intValue();
			center[1] = y.intValue();
			center[2] = z.intValue();
		}
		catch (NumberFormatException e) {
			return null;
		}
		return center;
	}

	public int[] getAreaSize() {
		int size[] = new int[3];
		try {
			Double x = new Double(mSizeFieldComponent.getXValue());
			Double y = new Double(mSizeFieldComponent.getYValue());
			Double z = new Double(mSizeFieldComponent.getZValue());
			size[0] = x.intValue();
			size[1] = y.intValue();
			size[2] = z.intValue();
		}
		catch (NumberFormatException e) {
			return null;
		}
		return size;
	}

	public String getOptionString() {
		int center[] = getAreaCenter();
		int size[]   = getAreaSize();
		if (center == null || size == null)
			return null;
		if (size[0] == 0 || size[1] == 0 || size[2] == 0)
			return null;
		return center[0] + ":" + center[1] + ":" + center[2] + ":" + size[0] + ":" + size[1] + ":" + size[2];
	}
	
}
		
