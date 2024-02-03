/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	BooleanNot.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class BooleanNot extends Script {

	SFString	outValueField;
	String		inValueString;
	String		oldInValueString;
	boolean		bExecution;

    public void initialize () {
		outValueField	= (SFString)getEventOut("outValue"); 
		inValueString	= null;
   		bExecution		= true;
    }

    public void processEvent (Event e){

		if (e.getName().equals("SYSTEM_EVENTIN_MODULE_EXECUTION") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue().toUpperCase();
			if (value.compareTo("TRUE") == 0)
				bExecution = true;
			else if (value.compareTo("FALSE") == 0)
				bExecution = false;
		}

		if (e.getName().equals("inValue") == true) 
			inValueString = ((ConstSFString)e.getValue()).getValue();

		if (inValueString != null) {
			if (bExecution) {
				if (inValueString.equalsIgnoreCase("true") == true)
					outValueField.setValue("false");
				else
					outValueField.setValue("true");
			}
			else
				outValueField.setValue(inValueString);
		}
    }

}
