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

public class NumericMod2Values extends Script {

	SFString	value1Out;
	SFString	value2Out;
	String		value1String;
	String		value2String;
	String		value1OutString;
	String		value2OutString;
	boolean		bExecution;

	public void initialize () {
		value1Out		= (SFString)getEventOut("value1"); 
		value2Out		= (SFString)getEventOut("value2"); 
		value1String	= new String("0");
		value2String	= new String("0");
		value1OutString	= new String();
		value2OutString	= new String();
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
				Double	dvalue1 = new Double(value1String);
				Double	dvalue2 = new Double(value2String);
				double remainder = dvalue1.doubleValue() % dvalue2.doubleValue();				
				value1OutString = String.valueOf((dvalue1.doubleValue() - remainder) / dvalue2.doubleValue());
				value1Out.setValue(value1OutString);
				value2OutString = String.valueOf(remainder);
				value2Out.setValue(value2OutString);
			}
			catch (NumberFormatException exception) {
				value1OutString = String.valueOf("");
				value1Out.setValue(value1OutString);
				value2OutString = String.valueOf("");
				value2Out.setValue(value2OutString);
			}
		}
		else {
			value1Out.setValue(value1String);
			value2Out.setValue(value2String);
		}
    }

}
