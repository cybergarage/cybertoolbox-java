/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	InterpColor.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class InterpColor extends Script {

	Node		node;
	SFFloat		fraction;
	String		inValueString;
	boolean		bExecution;

    public void initialize () {
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_TARGET_NODE");

		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				fraction = (SFFloat)node.getEventIn("set_fraction");
			else
				fraction = null;
		}
		else {
			node = null;
			fraction = null;
		}

		inValueString	= null;
   		bExecution		= true;
    }

    public void processEvent (Event e){
		
		if (fraction == null)
			return;

		if (e.getName().equals("SYSTEM_EVENTIN_MODULE_EXECUTION") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue().toUpperCase();
			if (value.compareTo("TRUE") == 0)
				bExecution = true;
			else if (value.compareTo("FALSE") == 0)
				bExecution = false;
		}

		if (e.getName().equals("value") == true) 
			inValueString = ((ConstSFString)e.getValue()).getValue();

		if (bExecution == true) { 
			try {
				Float	fvalue = new Float(inValueString);
				fraction.setValue(fvalue.floatValue());
			}
			catch (NumberFormatException exception) {
			}
		}
    }

}
