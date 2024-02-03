/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MaterialGetDiffuseColor.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class MaterialGetDiffuseColor extends Script {

	SFString	valueOut;
	Node		node;
	SFColor		color;

    public void initialize () {

		valueOut			= (SFString)getEventOut("value"); 
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				color = (SFColor)node.getExposedField("diffuseColor");
			else
				color = null;
		}
		else {
			node = null;
			color = null;
		}
    }

    public void processEvent (Event e){
		if (color != null) 
			valueOut.setValue(color.getRed() + "," + color.getGreen() + "," + color.getBlue());
		else
			valueOut.setValue(0 + "," + 0 + "," + 0);
    }

}
