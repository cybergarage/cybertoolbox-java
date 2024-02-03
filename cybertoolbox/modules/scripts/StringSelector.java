/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	StringSelector.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class StringSelector extends Script {

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

		if (bExecution) 
			valueOut.setValue(value1String);
		else
			valueOut.setValue(value2String);
    }

}
