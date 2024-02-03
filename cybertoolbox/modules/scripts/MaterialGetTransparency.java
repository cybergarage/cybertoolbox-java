/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MaterialGetTransparency.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class MaterialGetTransparency extends Script {

	SFString	valueOut;
	Node		node;
	SFFloat		transparency;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				transparency = (SFFloat)node.getExposedField("transparency");
			else
				transparency = null;
		}
		else {
			node = null;
			transparency = null;
		}
    }

    public void processEvent (Event e){
		if (transparency != null) { 
			Float value = new Float(transparency.getValue());
			valueOut.setValue(value.toString());
		}
		else {
			Float value = new Float(0.0f);
			valueOut.setValue(value.toString());
		}
    }

}
