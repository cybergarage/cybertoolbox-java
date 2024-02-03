/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	GeometryInverse.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class GeometryInverse extends Script {

	SFString	valueOut;
	String		value1String;
	String		valueOutString;
	boolean		bExecution;

	public void initialize () {
		valueOut		= (SFString)getEventOut("outValue"); 
		value1String	= new String("0");
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

		if (e.getName().equals("inValue") == true) 
			value1String = ((ConstSFString)e.getValue()).getValue();

		if (bExecution) {
			try {
				int value1Index1 = value1String.indexOf(',');
				int value1Index2 = value1String.indexOf(',', value1Index1+1);

				if ((0 <= value1Index1) && (0 <= value1Index2)) {
					Double	x = new Double(new String(value1String.toCharArray(), 0, (value1Index1-1)+1));
					Double	y = new Double(new String(value1String.toCharArray(), value1Index1+1, (value1Index2-1)-(value1Index1)));
					Double	z = new Double(new String(value1String.toCharArray(), value1Index2+1, (value1String.length()-1) - value1Index2));

					Double	newx = new Double(-x.doubleValue());
					Double	newy = new Double(-y.doubleValue());
					Double	newz = new Double(-z.doubleValue());

					valueOutString = valueOutString.valueOf(newx.toString() + "," + newy.toString() + "," + newz.toString());
				}
				else 
					valueOutString = valueOutString.valueOf("");
			}
			catch (NumberFormatException exception) {
				valueOutString = valueOutString.valueOf("");
			}
			valueOut.setValue(valueOutString);
		}
		else {
			valueOut.setValue(value1String);
		}

	}

}
