/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogEventTimerPanel.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.field.*;

public class DialogEventTimerPanel extends DialogEventPanel {
	
	private DialogValueFieldComponent mValueFieldComponent = null;
	
	public DialogEventTimerPanel(World world) {
		setLayout(new BorderLayout());
		mValueFieldComponent = new DialogValueFieldComponent("Alarm time (sec)");
		mValueFieldComponent.setText("1");
		add(mValueFieldComponent, BorderLayout.NORTH);
	}

	public DialogEventTimerPanel(World world, Event event) {
		this(world);
		setWakeUpTime((int)event.getOptionValue());
	}
	
	public void setWakeUpTime(int time) {
		mValueFieldComponent.setValue(time);
	}
		
	public int getWakeUpTime() {
		try {
			Double time = new Double(mValueFieldComponent.getText());
			return time.intValue();
		}
		catch (NumberFormatException e) {
			return -1;
		}
	}
	
	public String getOptionString() {
		int time = getWakeUpTime();
		if (time < 0) 
			return null;
		return Integer.toString(time);
	}
		
}
		
