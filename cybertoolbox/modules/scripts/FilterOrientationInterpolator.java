/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	FilterOrientationInterpolator.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class FilterOrientationInterpolator extends Script {

	SFString	outValue;
	String		inValueString;
	boolean		bExecution;
	Double		fieldValue;
	double		range[][] = new double[2][4];

    public void initialize () {
		outValue		= (SFString)getEventOut("outValue"); 
		inValueString	= null;
   		bExecution		= true;

		String fieldValueString= ((SFString)getField("SYSTEM_FIELD_MODULE_VALUE")).getValue(); 
		int index = fieldValueString.indexOf('-');
		if (index != -1) {
			try {
				String string;
				Double value;
				string = new String(fieldValueString.toCharArray(), 0, (index-1)+1);
				int indexCanma1 = string.indexOf(',');
				int indexCanma2 = string.indexOf(',', indexCanma1+1);
				int indexCanma3 = string.indexOf(',', indexCanma2+1);
				if (indexCanma1 != -1 && indexCanma2 != -1 && indexCanma2 != -1 && indexCanma1 < indexCanma2 && indexCanma2 < indexCanma3) {
					value = new Double(new String(string.toCharArray(), 0, (indexCanma1-1)+1));
					range[0][0] = value.doubleValue();
					value = new Double(new String(string.toCharArray(), indexCanma1+1, (indexCanma2-1)- indexCanma1));
					range[0][1] = value.doubleValue();
					value = new Double(new String(string.toCharArray(), indexCanma2+1, (indexCanma3-1)- indexCanma2));
					range[0][2] = value.doubleValue();
					value = new Double(new String(string.toCharArray(), indexCanma3+1, (string.length()-1) - indexCanma3));
					range[0][3] = value.doubleValue();
				}
				else {
					range[0][0] = 0.0f;
					range[0][1] = 0.0f;
					range[0][2] = 0.0f;
					range[0][3] = 0.0f;
					range[1][0] = 0.0f;
					range[1][1] = 0.0f;
					range[1][2] = 0.0f;
					range[1][3] = 0.0f;
				}
				string = new String(fieldValueString.toCharArray(), index+1, (fieldValueString.length()-1) - index);
				indexCanma1 = string.indexOf(',');
				indexCanma2 = string.indexOf(',', indexCanma1+1);
				indexCanma3 = string.indexOf(',', indexCanma2+1);
				if (indexCanma1 != -1 && indexCanma2 != -1 && indexCanma2 != -1 && indexCanma1 < indexCanma2 && indexCanma2 < indexCanma3) {
					value = new Double(new String(string.toCharArray(), 0, (indexCanma1-1)+1));
					range[1][0] = value.doubleValue();
					value = new Double(new String(string.toCharArray(), indexCanma1+1, (indexCanma2-1)- indexCanma1));
					range[1][1] = value.doubleValue();
					value = new Double(new String(string.toCharArray(), indexCanma2+1, (indexCanma3-1)- indexCanma2));
					range[1][2] = value.doubleValue();
					value = new Double(new String(string.toCharArray(), indexCanma3+1, (string.length()-1) - indexCanma3));
					range[1][3] = value.doubleValue();
				}
				else {
					range[0][0] = 0.0f;
					range[0][1] = 0.0f;
					range[0][2] = 0.0f;
					range[0][3] = 0.0f;
					range[1][0] = 0.0f;
					range[1][1] = 0.0f;
					range[1][2] = 0.0f;
					range[1][3] = 0.0f;
				}
			}
			catch (NumberFormatException exp) {
				range[0][0] = 0.0f;
				range[0][1] = 0.0f;
				range[0][2] = 0.0f;
				range[0][3] = 0.0f;
				range[1][0] = 0.0f;
				range[1][1] = 0.0f;
				range[1][2] = 0.0f;
				range[1][3] = 0.0f;
			}
		}
		else {
			range[0][0] = 0.0f;
			range[0][1] = 0.0f;
			range[0][2] = 0.0f;
			range[0][3] = 0.0f;
			range[1][0] = 0.0f;
			range[1][1] = 0.0f;
			range[1][2] = 0.0f;
			range[1][3] = 0.0f;
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
			if (range[0][0] != range[1][0] || range[0][1] != range[1][1] || range[0][2] != range[1][2] || range[0][3] != range[1][3]) {
				try {
					Double inValue = new Double(inValueString);
					double key = inValue.doubleValue();
					double keyValue[] = new double[4];
					if (key < 0.0) {
						keyValue[0] = range[0][0];
						keyValue[1] = range[0][1];
						keyValue[2] = range[0][2];
						keyValue[3] = range[0][3];
					}
					else if (1.0 < key) {
						keyValue[0] = range[1][0];
						keyValue[1] = range[1][1];
						keyValue[2] = range[1][2];
						keyValue[3] = range[1][3];
					}
					else {
						keyValue[0] = (range[1][0] - range[0][0])*key + range[0][0]; 
						keyValue[1] = (range[1][1] - range[0][1])*key + range[0][1]; 
						keyValue[2] = (range[1][2] - range[0][2])*key + range[0][2]; 
						keyValue[3] = (range[1][3] - range[0][3])*key + range[0][3]; 
					}
					outValue.setValue(keyValue[0] + "," + keyValue[1] + "," + keyValue[2] + "," + keyValue[3]);
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
