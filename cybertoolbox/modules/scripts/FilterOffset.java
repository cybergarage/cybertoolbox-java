/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	FilterOffset.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class FilterOffset extends Script {

	SFString	outValue;
	String		inValueString;
	boolean		bExecution;
	Double		fieldValue;

    public void initialize () {
		outValue		= (SFString)getEventOut("outValue"); 
		inValueString	= null;
   		bExecution		= true;

		String fieldValueString= ((SFString)getField("SYSTEM_FIELD_MODULE_VALUE")).getValue(); 
		try {
			fieldValue = new Double(fieldValueString);
		}
		catch (NumberFormatException exp) {
			fieldValue = new Double(0.0);
		}
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
				Double	inValue = new Double(inValueString);
				Double	offsetValue = new Double(fieldValue.doubleValue() + inValue.doubleValue());
				outValue.setValue(offsetValue.toString());
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
