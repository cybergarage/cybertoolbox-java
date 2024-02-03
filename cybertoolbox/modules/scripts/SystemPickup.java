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

public class SystemPickup extends Script {

	SFString	pickupButton;

    public void initialize () {
		pickupButton = (SFString)getEventOut("button"); 
    }

    public void processEvent (Event e){
		if (e.getName().equals("SYSTEM_EVENTIN_Pickup_TouchSensor_isActive")) {
			ConstSFBool button = (ConstSFBool)e.getValue();
			if (button.getValue())
				pickupButton.setValue("true");
			else
				pickupButton.setValue("false");
		}
    }

}
