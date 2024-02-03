/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	System_Frame.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class ObjectGetRotation extends Script {

	SFString	valueOut;
	Node		node;
	SFRotation	rotation;
	float		rotationValue[];

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				rotation = (SFRotation)node.getExposedField("rotation");
			else
				rotation = null;
		}
		else {
			node = null;
			rotation = null;
		}

		rotationValue = new float[4];

		if (rotation != null) {
			rotation.getValue(rotationValue);
		}
    }

    public void processEvent (Event e){
		if (rotation != null) {
			rotation.getValue(rotationValue);
			valueOut.setValue(rotationValue[0] + "," + rotationValue[1] + "," + rotationValue[2] + "," + rotationValue[3]);
		}
		else
			valueOut.setValue("");
    }

}
