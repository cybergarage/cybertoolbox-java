/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: GeometryNode.java
*
******************************************************************/

package vrml.node;

import vrml.*;
import vrml.field.*;

public abstract class GeometryNode extends Node {

	private String	bboxCenterPrivateFieldName			= "bboxCenter";
	private String	bboxSizePrivateFieldName			= "bboxSize";
	private String	displayListNumberPrivateFieldString	= "displayListNumber";

	public GeometryNode() {
		setHeaderFlag(false);

		// bboxCenter field
		SFVec3f bboxCenter = new SFVec3f(0.0f, 0.0f, 0.0f);
		bboxCenter.setName(bboxCenterPrivateFieldName);
		addPrivateField(bboxCenter);

		// bboxSize field
		SFVec3f bboxSize = new SFVec3f(-1.0f, -1.0f, -1.0f);
		bboxSize.setName(bboxSizePrivateFieldName);
		addPrivateField(bboxSize);

		setBoundingBoxCenter(0.0f, 0.0f, 0.0f);
		setBoundingBoxSize(-1.0f, -1.0f, -1.0f);

		// display list field
		SFInt32 dispList = new SFInt32(0);
		dispList.setName(displayListNumberPrivateFieldString);
		addPrivateField(dispList);

		setDisplayListNumber(0);
	}

	////////////////////////////////////////////////
	//	BoundingBoxSize
	////////////////////////////////////////////////

	public void setBoundingBoxSize(float value[]) {
		SFVec3f bboxSize = (SFVec3f)getPrivateField(bboxSizePrivateFieldName);
		bboxSize.setValue(value);
	}
	public void setBoundingBoxSize(float x, float y, float z) {
		SFVec3f bboxSize = (SFVec3f)getPrivateField(bboxSizePrivateFieldName);
		bboxSize.setValue(x, y, z);
	}
	public void getBoundingBoxSize(float value[]) {
		SFVec3f bboxSize = (SFVec3f)getPrivateField(bboxSizePrivateFieldName);
		bboxSize.getValue(value);
	}
	
	public float[] getBoundingBoxSize() {
		float size[] = new float[3];
		getBoundingBoxSize(size);
		return size;
	}
	
	////////////////////////////////////////////////
	//	BoundingBoxCenter
	////////////////////////////////////////////////

	public void setBoundingBoxCenter(float value[]) {
		SFVec3f bboxCenter = (SFVec3f)getPrivateField(bboxCenterPrivateFieldName);
		bboxCenter.setValue(value);
	}
	public void setBoundingBoxCenter(float x, float y, float z) {
		SFVec3f bboxCenter = (SFVec3f)getPrivateField(bboxCenterPrivateFieldName);
		bboxCenter.setValue(x, y, z);
	}
	public void getBoundingBoxCenter(float value[]) {
		SFVec3f bboxCenter = (SFVec3f)getPrivateField(bboxCenterPrivateFieldName);
		bboxCenter.getValue(value);
	}

	public float[] getBoundingBoxCenter() {
		float center[] = new float[3];
		getBoundingBoxCenter(center);
		return center;
	}

	////////////////////////////////////////////////
	//	DisplayListNumber
	////////////////////////////////////////////////

	public void setDisplayListNumber(int n) {
		SFInt32 dispList = (SFInt32)getPrivateField(displayListNumberPrivateFieldString);
		dispList.setValue((int)n);
	}

	public int getDisplayListNumber() {
		SFInt32 dispList = (SFInt32)getPrivateField(displayListNumberPrivateFieldString);
		return dispList.getValue();
	} 

	////////////////////////////////////////////////
	//	Abstract method
	////////////////////////////////////////////////
	
	abstract public void calculateBoundingBox();
}

