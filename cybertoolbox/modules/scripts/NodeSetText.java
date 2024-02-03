/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	NodeSetText.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class NodeSetText extends Script {

	Node			node;
	MFString	textString;
	String		inValueString;
	boolean		bExecution;

	public void initialize () {
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_TARGET_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				textString = (MFString)node.getExposedField("string");
			else
				textString = null;
		}
		else {
			node = null;
			textString = null;
		}

		inValueString	= null;
		bExecution		= true;
	}

	public void processEvent (Event e){
		
		if (textString == null)
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
			if (0 < textString.getSize())
				textString.set1Value(0, inValueString);
			else
				textString.addValue(inValueString);
		}
	}

}
