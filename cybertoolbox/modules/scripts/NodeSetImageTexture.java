/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	NodeSetImageTexture.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class NodeSetImageTexture extends Script {

	Node		node;
	MFString	urlString;
	String		inValueString;
	boolean		bExecution;

	public void initialize () {
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_TARGET_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				urlString = (MFString)node.getExposedField("url");
			else
				urlString = null;
		}
		else {
			node = null;
			urlString = null;
		}

		inValueString	= null;
		bExecution		= true;
	}

	public void processEvent (Event e){
		
		if (urlString == null)
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
			if (0 < urlString.getSize())
				urlString.set1Value(0, inValueString);
			else
				urlString.addValue(inValueString);
		}
	}

}
