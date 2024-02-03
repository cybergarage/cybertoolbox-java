/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	StringPI.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class StringPI extends Script {

	SFString	eventOutValue;
	String		outValue;
    public void initialize () {
		eventOutValue = (SFString)getEventOut("value");
		outValue = Double.toString(Math.PI);
    }

    public void processEvent (Event e){
		eventOutValue.setValue(outValue);
    }

}
