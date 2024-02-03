/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogWorldNewEvent.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogWorldNewEvent extends Dialog implements EventTypeConstants {

	private	JTabbedPane	mTabbedPane;
	private	JPanel		mEventPanel[]	= new JPanel[4];
	
	public DialogWorldNewEvent(Frame parentFrame, World world) {
		super(parentFrame, "New Event");
	
		mTabbedPane = new JTabbedPane();
		
		mEventPanel[0] = new DialogEventTimerPanel(world);
		mEventPanel[1] = new DialogEventClockPanel(world);
		mEventPanel[2] = new DialogEventPickupPanel(world);
		mEventPanel[3] = new DialogEventAreaPanel(world);

		mTabbedPane.addTab("Timer",	mEventPanel[0]);
		mTabbedPane.addTab("Clock",	mEventPanel[1]);
		mTabbedPane.addTab("Pickup",	mEventPanel[2]);
		mTabbedPane.addTab("Area",		mEventPanel[3]);

		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mTabbedPane;
		setComponents(dialogComponent);
		
		setPreferredSize(new Dimension(300, 260));
	}
	
	public int getSelectedEventType() {
		int index = mTabbedPane.getSelectedIndex();
		switch (index) {
		case 0: return EVENTTYPE_TIMER;
		case 1: return EVENTTYPE_CLOCK;
		case 2: return EVENTTYPE_PICKUP;
		case 3: return EVENTTYPE_AREA;
		}
		return -1;
	}
	
	public DialogEventPanel getSelectedEventPanel() {
		return (DialogEventPanel)mTabbedPane.getSelectedComponent();
	}

	public DialogEventPanel getEventPanel(int eventType) {
		switch (eventType) {
		case EVENTTYPE_TIMER:	return (DialogEventPanel)mEventPanel[0];
		case EVENTTYPE_CLOCK:	return (DialogEventPanel)mEventPanel[1];
		case EVENTTYPE_PICKUP:	return (DialogEventPanel)mEventPanel[2];
		case EVENTTYPE_AREA:		return (DialogEventPanel)mEventPanel[3];
		}
		return null;
	}
}
