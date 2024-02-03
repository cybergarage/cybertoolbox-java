/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	BooleanIf.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class BooleanIf extends Script {

	SFString	outValue1Field;
	SFString	outValue2Field;
	String		inValueString;

    public void initialize () {
		outValue1Field	= (SFString)getEventOut("outValue1"); 
		outValue2Field	= (SFString)getEventOut("outValue2"); 
		inValueString = new String();
    }

    public void processEvent (Event e){
		if (e.getName().equals("inValue") == true) 
			inValueString = ((ConstSFString)e.getValue()).getValue();

		if (inValueString.equalsIgnoreCase("true") == true) {
			outValue1Field.setValue("true");
			outValue2Field.setValue("false");
		}
		else {
			outValue1Field.setValue("false");
			outValue2Field.setValue("true");
		}
    }

}
