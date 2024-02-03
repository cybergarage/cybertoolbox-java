/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MiscPlaySound.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class MiscPlaySound extends Script {

	Node		node;
	SFTime		startTime;
	SFTime		stopTime;
	SFBool		loop;

    public void initialize () {
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_TARGET_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null) {
				startTime = (SFTime)node.getExposedField("startTime");
				stopTime = (SFTime)node.getExposedField("stopTime");
			}
			else {
				startTime = null;
				stopTime = null;
			}
		}
		else {
			node = null;
			startTime = null;
		}
    }

    public void processEvent (Event e){

		if (startTime == null || stopTime == null)
			return;

		if (e.getName().equals("SYSTEM_EVENTIN_MODULE_EXECUTION") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue().toUpperCase();
			if (value.compareTo("TRUE") == 0) {
				double	time = e.getTimeStamp();
				startTime.setValue(time);
				stopTime.setValue(time + 3600);
			}
		}

    }

    public void shutdown(){

		if (startTime == null || stopTime == null)
			return;

		startTime.setValue(-1.0);
		stopTime.setValue(0.0);
    }
}
