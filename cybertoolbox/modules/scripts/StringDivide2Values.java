/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	StringDivide2Values.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class StringDivide2Values extends Script {

	SFString	value1;
	SFString	value2;

    public void initialize () {
		value1	= (SFString)getEventOut("value1"); 
		value2	= (SFString)getEventOut("value2"); 
    }

    public void processEvent (Event e){
		if (e.getName().equals("value") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue();
			int index1 = value.indexOf(',');
			if (index1 != -1) {
				value1.setValue(new String(value.toCharArray(), 0, (index1-1)+1));
				value2.setValue(new String(value.toCharArray(), index1+1, (value.length()-1) - index1));
			}
			else {
				value1.setValue("");
				value2.setValue("");
			}
		}
    }

}
