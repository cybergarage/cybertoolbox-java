/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	LightGetOn.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class LightGetOn extends Script {

	SFString	valueOut;
	Node		node;
	SFBool		on;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 

		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				on = (SFBool)node.getExposedField("on");
			else
				on = null;
		}
		else {
			node = null;
			on = null;
		}
    }

    public void processEvent (Event e){
		if (on != null) { 
			Boolean value = new Boolean(on.getValue());
			valueOut.setValue(value.toString());
		}
		else {
			Boolean value = new Boolean(false);
			valueOut.setValue(value.toString());
		}
    }

}
