/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	BooleanAnd.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class BooleanAnd extends Script {

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

		if (inValue1String.equalsIgnoreCase("true") == true && inValue2String.equalsIgnoreCase("true") == true) 
			outValue = true;
		else
			outValue = false;

		if (outValue == true) 
			outValueField.setValue("true");
		else
			outValueField.setValue("false");
    }

}
