/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	ObjectGetScale.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class ObjectGetScale extends Script {

	SFString	valueOut;
	Node		node;
	SFVec3f		scale;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				scale = (SFVec3f)node.getExposedField("scale");
			else
				scale = null;
		}
		else {
			node = null;
			scale = null;
		}
    }

    public void processEvent (Event e){
		if (scale != null) 
			valueOut.setValue(scale.getX() + "," + scale.getY() + "," + scale.getZ());
		else
			valueOut.setValue("");
    }

}
