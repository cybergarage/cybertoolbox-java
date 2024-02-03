/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MathIncrement.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class MathIncrement extends Script {

	SFString	outValue;
	String		inValueString;
	boolean		bExecution;

    public void initialize () {
		outValue		= (SFString)getEventOut("outValue"); 
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

		if (bExecution) {
			try {
				Double	dvalue = new Double(inValueString);
				Double	minusValue = new Double(dvalue.doubleValue()+1.0);
				outValue.setValue(minusValue.toString());
			}
			catch (NumberFormatException exp) {
				outValue.setValue("");
			}
		}
		else {
			outValue.setValue(inValueString);
		}
    }

}
