/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MiscBeep.java
*
----------------------------------------------------------------*/

import java.awt.Toolkit;

import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class MiscBeep extends Script {

    public void initialize () {
    }

    public void processEvent (Event e){
		if (e.getName().equals("SYSTEM_EVENTIN_MODULE_EXECUTION") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue().toUpperCase();
			if (value.compareTo("TRUE") == 0) 
				Toolkit.getDefaultToolkit().beep();
		}
    }

}
