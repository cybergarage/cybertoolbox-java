/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	StringMerge4Values.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class StringDivide4Values extends Script {

	SFString	value1;
	SFString	value2;
	SFString	value3;
	SFString	value4;

    public void initialize () {
		value1	= (SFString)getEventOut("value1"); 
		value2	= (SFString)getEventOut("value2"); 
		value3	= (SFString)getEventOut("value3"); 
		value4	= (SFString)getEventOut("value4"); 
    }

    public void processEvent (Event e){
		if (e.getName().equals("value") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue();
			int index1 = value.indexOf(',');
			int index2 = value.indexOf(',', index1+1);
			int index3 = value.indexOf(',', index2+1);
			if (index1 != -1 && index2 != -1 && index2 != -1 && index1 < index2 && index2 < index3) {
				value1.setValue(new String(value.toCharArray(), 0, (index1-1)+1));
				value2.setValue(new String(value.toCharArray(), index1+1, (index2-1)- index1));
				value3.setValue(new String(value.toCharArray(), index2+1, (index3-1)- index2));
				value4.setValue(new String(value.toCharArray(), index3+1, (value.length()-1) - index3));
			}
			else {
				value1.setValue("");
				value2.setValue("");
				value3.setValue("");
				value4.setValue("");
			}
		}
    }

}
