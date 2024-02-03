/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	ObjectSetCenter.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class ObjectSetCenter extends Script {

	Node		node;
	SFVec3f		center;
	boolean		bExecution;
	String		inValueString;

    public void initialize () {
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_TARGET_NODE");
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

		inValueString	= null;
   		bExecution		= true;
    }

    public void processEvent (Event e){
		
		if (center == null)
			return;

		if (e.getName().equals("SYSTEM_EVENTIN_MODULE_EXECUTION") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue().toUpperCase();
			if (value.compareTo("TRUE") == 0)
				bExecution = true;
			else if (value.compareTo("FALSE") == 0)
				bExecution = false;
		}

		if (e.getName().equals("value") == true) 
			inValueString = ((ConstSFString)e.getValue()).getValue();

		if (bExecution == true) { 
			int index1 = inValueString.indexOf(',');
			int index2 = inValueString.indexOf(',', index1+1);
			if (index1 != -1 && index2 != -1 && index1 < index2) {
				try {
					Float	x = new Float(new String(inValueString.toCharArray(), 0, (index1-1)+1));
					Float	y = new Float(new String(inValueString.toCharArray(), index1+1, (index2-1)-(index1)));
					Float	z = new Float(new String(inValueString.toCharArray(), index2+1, (inValueString.length()-1) - index2));
					center.setValue(x.floatValue(), y.floatValue(), z.floatValue());
				}
				catch (NumberFormatException exception) {
				}
			}
		}
    }

}
