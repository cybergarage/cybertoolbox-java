/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	SystemSetTimer.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class SystemSetTimer extends Script {

	SFTime		startTime;
	SFTime		stopTime;
	SFTime		value;

    public void initialize () {
		value = (SFTime)getField("EVENT_TIMER_VALUE"); 
		startTime = (SFTime)getEventOut("EVENT_TIMER_STARTTIME"); 
		stopTime = (SFTime)getEventOut("EVENT_TIMER_STOPTTIME"); 
    }

    public void processEvent (Event e){
		if (e.getName().equals("EVENT_TIMER_EVENTIN") == true) { 
			double time = ((ConstSFTime)e.getValue()).getValue();
			startTime.setValue(time + value.getValue());
			stopTime.setValue(time + value.getValue());
		}
    }

}
