/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	ObjectGetCenter.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class ObjectGetCenter extends Script {

	SFString	valueOut;
	Node		node;
	SFVec3f		center;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				center = (SFVec3f)node.getExposedField("center");
			else
				center = null;
		}
		else {
			node = null;
			center = null;
		}
    }

    public void processEvent (Event e){
		if (center != null) 
			valueOut.setValue(center.getX() + "," + center.getY() + "," + center.getZ());
		else
			valueOut.setValue("");
    }

}
