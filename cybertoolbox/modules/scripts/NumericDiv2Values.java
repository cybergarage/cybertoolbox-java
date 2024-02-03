/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	NumericAdd2Values.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class NumericDiv2Values extends Script {

	SFString	valueOut;
	String		value1String;
	String		value2String;
	String		valueOutString;
	boolean		bExecution;

    public void initialize () {
		valueOut		= (SFString)getEventOut("value"); 
		value1String	= new String("0");
		value2String	= new String("0");
		valueOutString	= new String();
   		bExecution		= true;
    }

	private int getNValues(int index1, int index2, int index3) {
		if (0 <= index1) {
			if (index1 < index2) {
				if (index2 < index3) 
					return 4;
				else
					return 3;
			} 
			else
				return 2;
		}
		return 1;
	}

    public void processEvent (Event e){
		if (e.getName().equals("SYSTEM_EVENTIN_MODULE_EXECUTION") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue().toUpperCase();
			if (value.compareTo("TRUE") == 0)
				bExecution = true;
			else if (value.compareTo("FALSE") == 0)
				bExecution = false;
		}

		if (e.getName().equals("value1") == true) 
			value1String = ((ConstSFString)e.getValue()).getValue();
		else if (e.getName().equals("value2") == true) 
			value2String = ((ConstSFString)e.getValue()).getValue();

		if (bExecution) {

			int value1Index1 = value1String.indexOf(',');
			int value1Index2 = value1String.indexOf(',', value1Index1+1);
			int value1Index3 = value1String.indexOf(',', value1Index2+1);

			int value1NValues = getNValues(value1Index1, value1Index2, value1Index3);

			int value2Index1 = value2String.indexOf(',');
			int value2Index2 = value2String.indexOf(',', value2Index1+1);
			int value2Index3 = value2String.indexOf(',', value2Index2+1);
		
			int value2NValues = getNValues(value2Index1, value2Index2, value2Index3);

			try {
				if (value1NValues == 3 || value2NValues == 3) {
					
					String	valueString;
					int		index1, index2;
					Double	xDouble, yDouble, zDouble;

					if (value1NValues == 3) {
						valueString = value1String;
						index1 = value1Index1;
						index2 = value1Index2;
					}
					else {
						valueString = value2String;
						index1 = value2Index1;
						index2 = value2Index2;
					}

					xDouble = new Double(new String(valueString.toCharArray(), 0, (index1-1)+1));
					yDouble = new Double(new String(valueString.toCharArray(), index1+1, (index2-1)-(index1)));
					zDouble = new Double(new String(valueString.toCharArray(), index2+1, (valueString.length()-1) - index2));

					double vector[] = new double[3];

					vector[0] = xDouble.doubleValue();
					vector[1] = yDouble.doubleValue();
					vector[2] = zDouble.doubleValue();

					Double scaleDouble;

					if (value1NValues == 3) 
						scaleDouble = new Double(value2String);
					else 
						scaleDouble = new Double(value1String);
					
					double scale = scaleDouble.doubleValue();

					vector[0] /= scale;
					vector[1] /= scale;
					vector[2] /= scale;

					valueOutString = Double.toString(vector[0]) + "," + Double.toString(vector[1]) + "," + Double.toString(vector[2]);
					valueOut.setValue(valueOutString);
				}
				else {
					Double	dvalue1 = new Double(value1String);
					Double	dvalue2 = new Double(value2String);
					valueOutString = valueOutString.valueOf(dvalue1.doubleValue() / dvalue2.doubleValue());
					valueOut.setValue(valueOutString);
				}
			}
			catch (NumberFormatException exception) {
				valueOutString = valueOutString.valueOf("");
				valueOut.setValue(valueOutString);
			}
		}
		else {
			valueOut.setValue(value1String);
		}
    }

}
