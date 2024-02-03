/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	FilterRange.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class FilterRange extends Script {

	SFString	outValue;
	String		inValueString;
	boolean		bExecution;
	Double		lowValue;
	Double		highValue;

    public void initialize () {
		outValue		= (SFString)getEventOut("outValue"); 
		inValueString	= null;
   		bExecution		= true;

		String fieldValueString= ((SFString)getField("SYSTEM_FIELD_MODULE_VALUE")).getValue(); 
		int index1 = fieldValueString.indexOf(',');
		if (index1 != -1) {
			try {
				lowValue = new Double(new String(fieldValueString.toCharArray(), 0, (index1-1)+1));
				highValue = new Double(new String(fieldValueString.toCharArray(), index1+1, (fieldValueString.length()-1) - index1));
			}
			catch (NumberFormatException exp) {
				lowValue = new Double(0);
				highValue = new Double(0);
			}
		}
		else {
			lowValue = new Double(0);
			highValue = new Double(0);
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
				if (inValue.doubleValue() < lowValue.doubleValue())
					outValue.setValue(lowValue.toString());
				else if (highValue.doubleValue() < inValue.doubleValue())
					outValue.setValue(highValue.toString());
				else
					outValue.setValue(inValue.toString());
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
