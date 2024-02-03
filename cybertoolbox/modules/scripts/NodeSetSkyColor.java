/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	NodeSetSkyColor.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class NodeSetSkyColor extends Script {

	Node		node;
	MFColor		skyColor;
	String		inValueString;
	boolean		bExecution;

	public void initialize () {
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_TARGET_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				skyColor = (MFColor)node.getExposedField("skyColor");
			else
				skyColor = null;
		}
		else {
			node = null;
			skyColor = null;
		}

		inValueString	= null;
		bExecution		= true;
	}

	public void processEvent (Event e){
		
		if (skyColor == null)
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
					Float	r = new Float(new String(inValueString.toCharArray(), 0, (index1-1)+1));
					Float	g = new Float(new String(inValueString.toCharArray(), index1+1, (index2-1)-(index1)));
					Float	b = new Float(new String(inValueString.toCharArray(), index2+1, (inValueString.length()-1) - index2));
					if (0 < skyColor.getSize())
						skyColor.set1Value(0, r.floatValue(), g.floatValue(), b.floatValue());
					else
						skyColor.addValue(r.floatValue(), g.floatValue(), b.floatValue());
				}
				catch (NumberFormatException exception) {
				}
			}
		}
	}

}
