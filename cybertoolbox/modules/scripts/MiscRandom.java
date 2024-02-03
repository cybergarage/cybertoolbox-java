/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MiscGetTime.java
*
----------------------------------------------------------------*/

import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class MiscRandom extends Script {

	SFString value;

    public void initialize () {
		value = (SFString)getEventOut("value"); 
    }
    public void processEvent (Event e){
		Double randomValue = new Double(Math.random());
		value.setValue(randomValue.toString());
    }

}
