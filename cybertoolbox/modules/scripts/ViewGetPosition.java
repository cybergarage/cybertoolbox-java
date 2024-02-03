/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	ViewGetPosition.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class ViewGetPosition extends Script {

	SFString	valueOut;
	Node		node;
	SFVec3f		position;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				position = (SFVec3f)node.getExposedField("position");
			else
				position = null;
		}
		else {
			node = null;
			position = null;
		}
    }

    public void processEvent (Event e){
		if (position != null) 
			valueOut.setValue(position.getX() + "," + position.getY() + "," + position.getZ());
		else
			valueOut.setValue(0 + "," + 0 + "," + 0);
    }

}
