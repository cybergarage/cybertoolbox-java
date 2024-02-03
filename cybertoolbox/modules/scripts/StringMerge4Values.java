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

public class StringMerge4Values extends Script {

	SFString	valueOut;
	String		value1String;
	String		value2String;
	String		value3String;
	String		value4String;

    public void initialize () {
		valueOut		= (SFString)getEventOut("value"); 
		value1String	= new String();
		value2String	= new String();
		value3String	= new String();
		value4String	= new String();
    }

    public void processEvent (Event e){
		if (e.getName().equals("value1") == true) 
			value1String = ((ConstSFString)e.getValue()).getValue();
		else if (e.getName().equals("value2") == true) 
			value2String = ((ConstSFString)e.getValue()).getValue();
		else if (e.getName().equals("value3") == true) 
			value3String = ((ConstSFString)e.getValue()).getValue();
		else if (e.getName().equals("value4") == true) 
			value4String = ((ConstSFString)e.getValue()).getValue();

		valueOut.setValue(value1String + "," + value2String + "," + value3String + "," + value4String);
    }

}
