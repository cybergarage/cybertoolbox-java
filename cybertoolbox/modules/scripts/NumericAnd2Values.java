/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	NumericAnd2Values.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class NumericAnd2Values extends Script {

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
			try {
				int value1Index1 = value1String.indexOf(',');
				int value1Index2 = value1String.indexOf(',', value1Index1+1);
				int value1Index3 = value1String.indexOf(',', value1Index2+1);
	
				int value2Index1 = value2String.indexOf(',');
				int value2Index2 = value2String.indexOf(',', value2Index1+1);
				int value2Index3 = value2String.indexOf(',', value2Index2+1);
	
				if ((0 <= value1Index1) && (0 <= value2Index1)) {
					if ((value1Index1 < value1Index2) && (value2Index1 < value2Index2)) {
						if ((value1Index2 < value1Index3) && (value2Index2 < value2Index3)) {
							Double	x1 = new Double(new String(value1String.toCharArray(), 0, (value1Index1-1)+1));
							Double	y1 = new Double(new String(value1String.toCharArray(), value1Index1+1, (value1Index2-1)-(value1Index1)));
							Double	z1 = new Double(new String(value1String.toCharArray(), value1Index2+1, (value1Index3-1)-(value1Index2)));
							Double	r1 = new Double(new String(value1String.toCharArray(), value1Index3+1, (value1String.length()-1) - value1Index3));
	
							Double	x2 = new Double(new String(value2String.toCharArray(), 0, (value2Index1-1)+1));
							Double	y2 = new Double(new String(value2String.toCharArray(), value2Index1+1, (value2Index2-1)-(value2Index1)));
							Double	z2 = new Double(new String(value2String.toCharArray(), value2Index2+1, (value2Index3-1)-(value2Index2)));
							Double	r2 = new Double(new String(value2String.toCharArray(), value2Index3+1, (value2String.length()-1) - value2Index3));

							Integer	x = new Integer(x1.intValue() & x2.intValue());
							Integer	y = new Integer(y1.intValue() & y2.intValue());
							Integer	z = new Integer(z1.intValue() & z2.intValue());
							Integer	r = new Integer(r1.intValue() & r2.intValue());

							valueOutString = valueOutString.valueOf(x.toString() + "," + y.toString() + "," + z.toString() + "," + r.toString());
						} else {
							Double	x1 = new Double(new String(value1String.toCharArray(), 0, (value1Index1-1)+1));
							Double	y1 = new Double(new String(value1String.toCharArray(), value1Index1+1, (value1Index2-1)-(value1Index1)));
							Double	z1 = new Double(new String(value1String.toCharArray(), value1Index2+1, (value1String.length()-1) - value1Index2));

							Double	x2 = new Double(new String(value2String.toCharArray(), 0, (value2Index1-1)+1));
							Double	y2 = new Double(new String(value2String.toCharArray(), value2Index1+1, (value2Index2-1)-(value2Index1)));
							Double	z2 = new Double(new String(value2String.toCharArray(), value2Index2+1, (value2String.length()-1) - value2Index2));

							Integer	x = new Integer(x1.intValue() & x2.intValue());
							Integer	y = new Integer(y1.intValue() & y2.intValue());
							Integer	z = new Integer(z1.intValue() & z2.intValue());

							valueOutString = valueOutString.valueOf(x.toString() + "," + y.toString() + "," + z.toString());
						}
					} else {
							Double	x1 = new Double(new String(value1String.toCharArray(), 0, (value1Index1-1)+1));
							Double	y1 = new Double(new String(value1String.toCharArray(), value1Index1+1, (value1String.length()-1) - value1Index1));
	
							Double	x2 = new Double(new String(value2String.toCharArray(), 0, (value2Index1-1)+1));
							Double	y2 = new Double(new String(value2String.toCharArray(), value2Index1+1, (value2String.length()-1) - value2Index1));

							Integer	x = new Integer(x1.intValue() & x2.intValue());
							Integer	y = new Integer(y1.intValue() & y2.intValue());

							valueOutString = valueOutString.valueOf(x.toString() + "," + y.toString());
					}
				}
				else {
					Double	dvalue1 = new Double(value1String);
					Double	dvalue2 = new Double(value2String);
					valueOutString = valueOutString.valueOf(dvalue1.intValue() & dvalue2.intValue());
				}
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
