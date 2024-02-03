/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogEventClockPanel.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.field.*;

public class DialogEventClockPanel extends DialogEventPanel {
	
	private DialogValueFieldComponent mValueFieldComponent = null;
	
	public DialogEventClockPanel(World world) {
		setLayout(new BorderLayout());
		mValueFieldComponent = new DialogValueFieldComponent("Interval time (sec)");
		mValueFieldComponent.setText("1");
		add(mValueFieldComponent, BorderLayout.NORTH);
	}

	public DialogEventClockPanel(World world, Event event) {
		this(world);
		setIntervalTime((int)event.getOptionValue());
	}
	
	public void setIntervalTime(int time) {
		mValueFieldComponent.setValue(time);
	}
		
	public int getIntervalTime() {
		try {
			Double time = new Double(mValueFieldComponent.getText());
			return time.intValue();
		}
		catch (NumberFormatException e) {
			return -1;
		}
	}

	public String getOptionString() {
		int time = getIntervalTime();
		if (time < 0) 
			return null;
		return Integer.toString(time);
	}
}
		
