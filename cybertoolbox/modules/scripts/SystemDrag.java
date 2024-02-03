/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	System_Pickup.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class SystemDrag extends Script {

	SFString	value;

    public void initialize () {
		value = (SFString)getEventOut("value"); 
    }

    public void processEvent (Event e){
		if (e.getName().equals("SYSTEM_EVENTIN_Drag_PlaneSensor_translation_changed")) {
			ConstSFVec3f translation = (ConstSFVec3f)e.getValue();
			value.setValue(translation.getX() + "," + translation.getY() + "," + translation.getZ());
		}
    }
}
