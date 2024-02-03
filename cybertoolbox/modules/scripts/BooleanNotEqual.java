/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	BooleanNotEqual.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class BooleanNotEqual extends Script {

	boolean		outValue;
	SFString	outValueField;
	String		inValue1String;
	String		inValue2String;

    public void initialize () {
		outValueField	= (SFString)getEventOut("outValue"); 
		inValue1String	= new String();
		inValue2String	= new String();
    }

    public void processEvent (Event e){
		if (e.getName().equals("inValue1") == true) 
			inValue1String = ((ConstSFString)e.getValue()).getValue();
		else if (e.getName().equals("inValue2") == true) 
			inValue2String = ((ConstSFString)e.getValue()).getValue();

		if (inValue1String.compareTo(inValue2String) == 0) 
			outValue = false;
		else {
			try {
				int inValue1Index1 = inValue1String.indexOf(',');
				int inValue1Index2 = inValue1String.indexOf(',', inValue1Index1+1);
				int inValue1Index3 = inValue1String.indexOf(',', inValue1Index2+1);

				int inValue2Index1 = inValue2String.indexOf(',');
				int inValue2Index2 = inValue2String.indexOf(',', inValue2Index1+1);
				int inValue2Index3 = inValue2String.indexOf(',', inValue2Index2+1);

				if ((0 <= inValue1Index1) && (0 <= inValue2Index1)) {
					if ((inValue1Index1 < inValue1Index2) && (inValue2Index1 < inValue2Index2)) {
						if ((inValue1Index2 < inValue1Index3) && (inValue2Index2 < inValue2Index3)) {
					
							Double	x1 = new Double(new String(inValue1String.toCharArray(), 0, (inValue1Index1-1)+1));
							Double	y1 = new Double(new String(inValue1String.toCharArray(), inValue1Index1+1, (inValue1Index2-1)-(inValue1Index1)));
							Double	z1 = new Double(new String(inValue1String.toCharArray(), inValue1Index2+1, (inValue1Index3-1)-(inValue1Index2)));
							Double	r1 = new Double(new String(inValue1String.toCharArray(), inValue1Index3+1, (inValue1String.length()-1) - inValue1Index3));

							Double	x2 = new Double(new String(inValue2String.toCharArray(), 0, (inValue2Index1-1)+1));
							Double	y2 = new Double(new String(inValue2String.toCharArray(), inValue2Index1+1, (inValue2Index2-1)-(inValue2Index1)));
							Double	z2 = new Double(new String(inValue2String.toCharArray(), inValue2Index2+1, (inValue2Index3-1)-(inValue2Index2)));
							Double	r2 = new Double(new String(inValue2String.toCharArray(), inValue2Index3+1, (inValue2String.length()-1) - inValue2Index3));
	
							if (x1.doubleValue() == x2.doubleValue() && y1.doubleValue() == y2.doubleValue() && z1.doubleValue() == z2.doubleValue() && r1.doubleValue() == r2.doubleValue())
								outValue = false;
							else 
								outValue = true;

						} else {
							Double	x1 = new Double(new String(inValue1String.toCharArray(), 0, (inValue1Index1-1)+1));
							Double	y1 = new Double(new String(inValue1String.toCharArray(), inValue1Index1+1, (inValue1Index2-1)-(inValue1Index1)));
							Double	z1 = new Double(new String(inValue1String.toCharArray(), inValue1Index2+1, (inValue1String.length()-1) - inValue1Index2));

							Double	x2 = new Double(new String(inValue2String.toCharArray(), 0, (inValue2Index1-1)+1));
							Double	y2 = new Double(new String(inValue2String.toCharArray(), inValue2Index1+1, (inValue2Index2-1)-(inValue2Index1)));
							Double	z2 = new Double(new String(inValue2String.toCharArray(), inValue2Index2+1, (inValue2String.length()-1) - inValue2Index2));

							Double	x = new Double(x1.doubleValue() - x2.doubleValue());
							Double	y = new Double(y1.doubleValue() - y2.doubleValue());
							Double	z = new Double(z1.doubleValue() - z2.doubleValue());

							if (x1.doubleValue() == x2.doubleValue() && y1.doubleValue() == y2.doubleValue() && z1.doubleValue() == z2.doubleValue())
								outValue = false;
							else 
								outValue = true;

						}
					} else {
							Double	x1 = new Double(new String(inValue1String.toCharArray(), 0, (inValue1Index1-1)+1));
							Double	y1 = new Double(new String(inValue1String.toCharArray(), inValue1Index1+1, (inValue1String.length()-1) - inValue1Index1));

							Double	x2 = new Double(new String(inValue2String.toCharArray(), 0, (inValue2Index1-1)+1));
							Double	y2 = new Double(new String(inValue2String.toCharArray(), inValue2Index1+1, (inValue2String.length()-1) - inValue2Index1));

							if (x1.doubleValue() == x2.doubleValue() && y1.doubleValue() == y2.doubleValue())
								outValue = false;
							else 
								outValue = true;
					}
				}
				else {
					Double	value1 = new Double(inValue1String);
					Double	value2 = new Double(inValue2String);
					if (value1.doubleValue() == value2.doubleValue())
						outValue = false;
					else 
						outValue = true;
				}
			}
			catch (NumberFormatException exception) {
				outValue = false;
			}

		}

		if (outValue == true) 
			outValueField.setValue("true");
		else
			outValueField.setValue("false");
    }

}
