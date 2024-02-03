/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	ObjectGetLocation.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class ObjectGetLocation extends Script {

	SFString	valueOut;
	Node		node;
	SFVec3f		translation;

    public void initialize () {
		valueOut			= (SFString)getEventOut("value"); 

		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				translation = (SFVec3f)node.getExposedField("translation");
			else
				translation = null;
		}
		else {
			node = null;
			translation = null;
		}
    }

    public void processEvent (Event e){
		if (translation != null) 
			valueOut.setValue(translation.getX() + "," + translation.getY() + "," + translation.getZ());
		else
			valueOut.setValue("");
    }

}
