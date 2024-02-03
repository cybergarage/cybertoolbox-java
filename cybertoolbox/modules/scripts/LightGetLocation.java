/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	LightGetLocation.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class LightGetLocation extends Script {

	SFString	valueOut;
	Node		node;
	SFVec3f		location;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				location = (SFVec3f)node.getExposedField("location");
			else
				location = null;
		}
		else {
			node = null;
			location = null;
		}
    }

    public void processEvent (Event e){
		if (location != null) 
			valueOut.setValue(location.getX() + "," + location.getY() + "," + location.getZ());
		else
			valueOut.setValue(0 + "," + 0 + "," + 0);
    }

}
