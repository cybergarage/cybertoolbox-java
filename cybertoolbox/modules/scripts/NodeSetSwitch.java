/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	NodeSetSwitch.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class NodeSetSwitch extends Script {

	Node		node;
	SFInt32		whichChoice;
	String		inValueString;
	boolean		bExecution;

	public void initialize () {
		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_TARGET_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				whichChoice = (SFInt32)node.getExposedField("whichChoice");
			else
				whichChoice = null;
		}
		else {
			node = null;
			whichChoice = null;
		}

		inValueString	= null;
		bExecution		= true;
	}

	public void processEvent (Event e){
		
		if (whichChoice == null)
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
			try {
				Integer value = new Integer(inValueString);
				whichChoice.setValue(value.intValue());
			}
			catch (NumberFormatException exception) {}
		}
	}

}
