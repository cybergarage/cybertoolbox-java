/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	ViewGetOrientation.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class ViewGetOrientation extends Script {

	SFString	valueOut;
	Node		node;
	SFRotation	orientation;
	float		orientationValue[];

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				orientation = (SFRotation)node.getExposedField("orientation");
			else
				orientation = null;
		}
		else {
			node = null;
			orientation = null;
		}

		orientationValue = new float[4];

		if (orientation != null) {
			orientation.getValue(orientationValue);
		}
    }

    public void processEvent (Event e){
		if (orientation != null) {
			orientation.getValue(orientationValue);
			valueOut.setValue(orientationValue[0] + "," + orientationValue[1] + "," + orientationValue[2] + "," + orientationValue[3]);
		}
		else
			valueOut.setValue(0 + "," + 0 + "," + 0 + "," + 0);
    }

}
