/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	GeometryGetCross.java
*
----------------------------------------------------------------*/

import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class GeometryGetCross extends Script {

	SFString	valueOut;
	String		value1String;
	String		value2String;
	String		valueOutString;
	boolean		bExecution;

	public void initialize () {
		valueOut			= (SFString)getEventOut("outValue"); 
		value1String		= new String("0");
		value2String		= new String("0");
		valueOutString	= new String();
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

		if (e.getName().equals("inValue1") == true) 
			value1String = ((ConstSFString)e.getValue()).getValue();
		else if (e.getName().equals("inValue2") == true) 
			value2String = ((ConstSFString)e.getValue()).getValue();

		if (bExecution) {
			try {
				int value1Index1 = value1String.indexOf(',');
				int value1Index2 = value1String.indexOf(',', value1Index1+1);

				int value2Index1 = value2String.indexOf(',');
				int value2Index2 = value2String.indexOf(',', value2Index1+1);

				if ((0 <= value1Index1) && (0 <= value2Index1) && (0 <= value1Index2) && (0 <= value2Index2)) {
					Double	x1 = new Double(new String(value1String.toCharArray(), 0, (value1Index1-1)+1));
					Double	y1 = new Double(new String(value1String.toCharArray(), value1Index1+1, (value1Index2-1)-(value1Index1)));
					Double	z1 = new Double(new String(value1String.toCharArray(), value1Index2+1, (value1String.length()-1) - value1Index2));

					Double	x2 = new Double(new String(value2String.toCharArray(), 0, (value2Index1-1)+1));
					Double	y2 = new Double(new String(value2String.toCharArray(), value2Index1+1, (value2Index2-1)-(value2Index1)));
					Double	z2 = new Double(new String(value2String.toCharArray(), value2Index2+1, (value2String.length()-1) - value2Index2));

					Double	x = new Double(y1.doubleValue()*z2.doubleValue() - z1.doubleValue()*y2.doubleValue());
					Double	y = new Double(z1.doubleValue()*x2.doubleValue() - x1.doubleValue()*z2.doubleValue());
					Double	z = new Double(x1.doubleValue()*y2.doubleValue() - y1.doubleValue()*x2.doubleValue());

					valueOutString = valueOutString.valueOf(x.toString() + "," + y.toString() + "," + z.toString());
				}
				else 
					valueOutString = String.valueOf("");
			}
			catch (NumberFormatException exception) {
				valueOutString = String.valueOf("");
			}
			valueOut.setValue(valueOutString);
		}
		else {
			valueOut.setValue(value1String);
		}

    }

}
