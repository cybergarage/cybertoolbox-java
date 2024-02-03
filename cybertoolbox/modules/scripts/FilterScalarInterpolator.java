/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	FilterScalarInterpolator.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class FilterScalarInterpolator extends Script {

	SFString	outValue;
	String		inValueString;
	boolean		bExecution;
	double		range[] = new double[2];

    public void initialize () {
		outValue		= (SFString)getEventOut("outValue"); 
		inValueString	= null;
   		bExecution		= true;

		String fieldValueString= ((SFString)getField("SYSTEM_FIELD_MODULE_VALUE")).getValue(); 
		int index1 = fieldValueString.indexOf('-');
		if (index1 != -1) {
			try {
				Double value;
				value = new Double(new String(fieldValueString.toCharArray(), 0, (index1-1)+1));
				range[0] = value.doubleValue();
				value = new Double(new String(fieldValueString.toCharArray(), index1+1, (fieldValueString.length()-1) - index1));
				range[1] = value.doubleValue();
			}
			catch (NumberFormatException exp) {
				range[0] = 0.0f;
				range[1] = 0.0f;
			}
		}
		else {
			range[0] = 0.0f;
			range[1] = 0.0f;
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
			if (range[0] != range[1]) {
				try {
					Double inValue = new Double(inValueString);
					double key = inValue.doubleValue();
					double keyValue;
					if (key < 0.0)
						keyValue = range[0];
					else if (1.0 < key) 
						keyValue = range[1];
					else
						keyValue = (range[1] - range[0])*key + range[0]; 
					Double	value = new Double(keyValue);
					outValue.setValue(value.toString());
				}
				catch (NumberFormatException exp) {
					outValue.setValue("");
				}
			}
			else
				outValue.setValue("");
		}
		else {
			outValue.setValue(inValueString);
		}
    }

}
