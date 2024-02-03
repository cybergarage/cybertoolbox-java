/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	LightGetDirection.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class LightGetDirection extends Script {

	SFString	valueOut;
	Node		node;
	SFVec3f		direction;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				direction = (SFVec3f)node.getExposedField("direction");
			else
				direction = null;
		}
		else {
			node = null;
			direction = null;
		}
    }

    public void processEvent (Event e){
		if (direction != null) 
			valueOut.setValue(direction.getX() + "," + direction.getY() + "," + direction.getZ());
		else
			valueOut.setValue(0 + "," + 0 + "," + 0);
    }

}
