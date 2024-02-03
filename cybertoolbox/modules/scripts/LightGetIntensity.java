/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	LightGetIntensity.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class LightGetIntensity extends Script {

	SFString	valueOut;
	Node		node;
	SFFloat		intensity;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 

		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				intensity = (SFFloat)node.getExposedField("intensity");
			else
				intensity = null;
		}
		else {
			node = null;
			intensity = null;
		}
    }

    public void processEvent (Event e){
		if (intensity != null) { 
			Float value = new Float(intensity.getValue());
			valueOut.setValue(value.toString());
		}
		else {
			Float value = new Float(0.0f);
			valueOut.setValue(value.toString());
		}
    }

}
