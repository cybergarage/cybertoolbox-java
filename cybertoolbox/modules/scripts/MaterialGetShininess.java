/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MaterialGetShininess.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class MaterialGetShininess extends Script {

	SFString	valueOut;
	Node		node;
	SFFloat		shininess;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				shininess = (SFFloat)node.getExposedField("shininess");
			else 
				shininess = null;
		}
		else {
			node = null;
			shininess = null;
		}
    }

    public void processEvent (Event e){
		if (shininess != null) { 
			Float value = new Float(shininess.getValue());
			valueOut.setValue(value.toString());
		}
		else {
			Float value = new Float(0.0f);
			valueOut.setValue(value.toString());
		}
    }

}
