/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	LightGetRadius.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class LightGetRadius extends Script {

	SFString	valueOut;
	Node		node;
	SFFloat		radius;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 

		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				radius = (SFFloat)node.getExposedField("radius");
			else
				radius = null;
		}
		else {
			node = null;
			radius = null;
		}
    }

    public void processEvent (Event e){
		if (radius != null) { 
			Float value = new Float(radius.getValue());
			valueOut.setValue(value.toString());
		}
		else {
			Float value = new Float(0.0f);
			valueOut.setValue(value.toString());
		}
    }

}
