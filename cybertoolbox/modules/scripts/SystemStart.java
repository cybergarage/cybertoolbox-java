/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	System_Frame.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class SystemStart extends Script {

	SFBool		enabled;

    public void initialize () {
		enabled = (SFBool)getEventOut("EVENT_START_ENABLED"); 
    }

    public void processEvent (Event e){
		enabled.setValue(false);
    }

}
