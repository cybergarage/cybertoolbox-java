/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	ViewGetFOV.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class ViewGetFOV extends Script {

	SFString	valueOut;
	Node		node;
	SFFloat		fov;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				fov = (SFFloat)node.getExposedField("fieldOfView");
			else
				fov = null;
		}
		else {
			node = null;
			fov = null;
		}
    }

    public void processEvent (Event e){
		if (fov != null) { 
			Float value = new Float(fov.getValue());
			valueOut.setValue(value.toString());
		}
		else {
			Float value = new Float(0.0f);
			valueOut.setValue(value.toString());
		}
    }

}
