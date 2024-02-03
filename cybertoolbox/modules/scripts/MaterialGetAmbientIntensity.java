/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MaterialGetAmbientIntensity.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class MaterialGetAmbientIntensity extends Script {

	SFString	valueOut;
	Node		node;
	SFFloat		ambientIntensity;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				ambientIntensity = (SFFloat)node.getExposedField("ambientIntensity");
			else
				ambientIntensity = null;
		}
		else {
			node = null;
			ambientIntensity = null;
		}
    }

    public void processEvent (Event e){
		if (ambientIntensity != null) { 
			Float value = new Float(ambientIntensity.getValue());
			valueOut.setValue(value.toString());
		}
		else {
			Float value = new Float(0.0f);
			valueOut.setValue(value.toString());
		}
    }

}
