/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	SystemArea.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class SystemArea extends Script {

	SFString	inRect;

    public void initialize () {
		inRect = (SFString)getEventOut("outValue"); 
    }

    public void processEvent (Event e){
		if (e.getName().equals("SYSTEM_EVENTIN_Area_ProximitySensor_isActive")) {
			ConstSFBool active = (ConstSFBool)e.getValue();
			if (active.getValue())
				inRect.setValue("true");
			else
				inRect.setValue("false");
		}
    }

}
