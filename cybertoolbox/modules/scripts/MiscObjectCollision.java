/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MiscObjectCollision.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class MiscObjectCollision extends Script {

	private SFString	outValueField;
	private Node		node1;
	private Node		node2;
	private SFVec3f		node1Translation;
	private SFVec3f		node2Translation;
	private float		node1Radius;
	private float		node2Radius;
	private boolean		bExecution;
	private float		pos1[] = new float[3];
	private float		pos2[] = new float[3];

    public void initialize () {
		outValueField = (SFString)getEventOut("outValue"); 

		SFNode sourceNodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_SOURCE_NODE");
		if (sourceNodeField != null) {
			node1 = (Node)sourceNodeField.getValue();
			if (node1 != null)
				node1Translation = (SFVec3f)node1.getExposedField("translation");
			else
				node1Translation = null;
		}
		else {
			node1 = null;
			node1Translation = null;
		}

		SFNode targetNodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_TARGET_NODE");
		if (targetNodeField != null) {
			node2 = (Node)targetNodeField.getValue();
			if (node2 != null)
				node2Translation = (SFVec3f)node2.getExposedField("translation");
			else
				node2Translation = null;
		}
		else {
			node2 = null;
			node2Translation = null;
		}

		node1Radius = node2Radius = 0.0f;
    	SFString valueField = (SFString)getField("SYSTEM_FIELD_MODULE_VALUE");
		if (valueField != null) {
	    	String fieldValueString= valueField.getValue(); 
			if (fieldValueString != null) {
				int index = fieldValueString.indexOf(',');
				if (index != -1) {
					try {
						Float radius1 = new Float(new String(fieldValueString.toCharArray(), 0, (index-1)+1) );
						Float radius2 = new Float(new String(fieldValueString.toCharArray(), index + 1, fieldValueString.length() - (index+1) ));
						node1Radius= radius1.floatValue();
						node2Radius= radius2.floatValue();
					}
					catch (NumberFormatException exception) {}
				}
			}
		}
  		bExecution = true;
	}

	private float getDistance(float pos1[], float pos2[]) {
		float x = pos1[0]- pos2[0];
		float y = pos1[1]- pos2[1];
		float z = pos1[2]- pos2[2];
		return (float)Math.sqrt(x*x + y*y + z*z);
    }

    public void processEvent (Event e){
		if (e.getName().equals("SYSTEM_EVENTIN_MODULE_EXECUTION") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue().toUpperCase();
			if (value.compareTo("TRUE") == 0)
				bExecution = true;
			else if (value.compareTo("FALSE") == 0)
				bExecution = false;
		}

		if (bExecution) {
			node1Translation.getValue(pos1);
			node2Translation.getValue(pos2);
			if (getDistance(pos1, pos2) <= (node1Radius + node2Radius))
				outValueField.setValue("true");
			else
				outValueField.setValue("false");
		}
    }

}
