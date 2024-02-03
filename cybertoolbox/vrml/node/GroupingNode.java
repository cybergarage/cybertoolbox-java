/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: GroupingNode.java
*
******************************************************************/

package vrml.node;

import vrml.*;
import vrml.util.*;
import vrml.field.*;

public abstract class GroupingNode extends Node {

	private String	addChildrenEventIn			= "addChildren";
	private String	removeChildrenEventIn		= "removeChildren";
	private String	childrenExposedField		= "children";
	
	private String	bboxCenterFieldName			= "bboxCenter";
	private String	bboxSizeFieldName				= "bboxSize";

	public GroupingNode(boolean bAddChildrenField) {
		setHeaderFlag(false);

		if (bAddChildrenField == true) {
			// addChildren eventout field
			MFNode addChildren = new MFNode();
			addEventIn(addChildrenEventIn, addChildren);

			// removeChildren eventout field
			MFNode removeChildren = new MFNode();
			addEventIn(removeChildrenEventIn, removeChildren);

			// children exposedField
			MFNode children = new MFNode();
			addExposedField(childrenExposedField, children);
		}
		
		// bboxBBoxCenter field
		SFVec3f bboxBBoxCenter = new SFVec3f(0.0f, 0.0f, 0.0f);
		bboxBBoxCenter.setName(bboxCenterFieldName);
		addField(bboxBBoxCenter);

		// bboxSize field
		SFVec3f bboxSize = new SFVec3f(-1.0f, -1.0f, -1.0f);
		bboxSize.setName(bboxSizeFieldName);
		addField(bboxSize);
	}

	public GroupingNode() {
		this(true);
	}

	////////////////////////////////////////////////
	//	addChildren / removeChildren
	////////////////////////////////////////////////

	public MFNode getAddChildrenField() {
		return (MFNode)getEventIn(addChildrenEventIn);
	}

	public MFNode getRemoveChildrenField() {
		return (MFNode)getEventIn(removeChildrenEventIn);
	}

	////////////////////////////////////////////////
	//	children
	////////////////////////////////////////////////

	public MFNode getChildrenField() {
		return (MFNode)getExposedField(childrenExposedField);
	}

	public void updateChildrenField() {
		MFNode childrenField = getChildrenField();
		childrenField.removeAllValues();
		for (Node node=getChildNodes(); node != null; node=node.next()) 
			childrenField.addValue(node);
	}

	////////////////////////////////////////////////
	//	BBoxSize
	////////////////////////////////////////////////

	public void setBoundingBoxSize(float value[]) {
		SFVec3f bboxSize = (SFVec3f)getField(bboxSizeFieldName);
		bboxSize.setValue(value);
	}
	public void setBoundingBoxSize(float x, float y, float z) {
		SFVec3f bboxSize = (SFVec3f)getField(bboxSizeFieldName);
		bboxSize.setValue(x, y, z);
	}
	public void getBoundingBoxSize(float value[]) {
		SFVec3f bboxSize = (SFVec3f)getField(bboxSizeFieldName);
		bboxSize.getValue(value);
	}

	public float[] getBoundingBoxSize() {
		float size[] = new float[3];
		getBoundingBoxSize(size);
		return size;
	}

	////////////////////////////////////////////////
	//	BBoxCenter
	////////////////////////////////////////////////

	public void setBoundingBoxCenter(float value[]) {
		SFVec3f bboxBBoxCenter = (SFVec3f)getField(bboxCenterFieldName);
		bboxBBoxCenter.setValue(value);
	}
	public void setBoundingBoxCenter(float x, float y, float z) {
		SFVec3f bboxBBoxCenter = (SFVec3f)getField(bboxCenterFieldName);
		bboxBBoxCenter.setValue(x, y, z);
	}
	public void getBoundingBoxCenter(float value[]) {
		SFVec3f bboxBBoxCenter = (SFVec3f)getField(bboxCenterFieldName);
		bboxBBoxCenter.getValue(value);
	}

	public float[] getBoundingBoxCenter() {
		float center[] = new float[3];
		getBoundingBoxCenter(center);
		return center;
	}

	////////////////////////////////////////////////
	//	abstract method
	////////////////////////////////////////////////
	
	public void updateBoundingBox(Node node, BoundingBox bbox) {
		if (node.isGeometryNode() == true) {
			GeometryNode gnode = (GeometryNode)node;
			gnode.calculateBoundingBox();

			float bboxCenter[]	= new float[3];
			float bboxSize[]	= new float[3];
			float point[]		= new float[3];

			gnode.getBoundingBoxCenter(bboxCenter);
			gnode.getBoundingBoxSize(bboxSize);
						
			if (bboxSize[0] >= 0.0f && bboxSize[1] >= 0.0f && bboxSize[2] >= 0.0f) {
				SFMatrix mx = gnode.getTransformMatrix();
				for (int n=0; n<8; n++) {
					point[0] = (n < 4)			? bboxCenter[0] - bboxSize[0] : bboxCenter[0] + bboxSize[0];
					point[1] = ((n % 2) != 0)	? bboxCenter[1] - bboxSize[1] : bboxCenter[1] + bboxSize[1];
					point[2] = ((n % 4) < 2)	? bboxCenter[2] - bboxSize[2] : bboxCenter[2] + bboxSize[2];
					mx.multi(point);
					bbox.addPoint(point);
				}
			}
		}
		
		for (Node cnode=node.getChildNodes(); cnode != null; cnode=cnode.next())
			updateBoundingBox(cnode, bbox);
	}
	
	public void calculateBoundingBox() {
		BoundingBox bbox = new BoundingBox();
		for (Node node=getChildNodes(); node != null; node=node.next())
			updateBoundingBox(node, bbox);
		setBoundingBoxCenter(bbox.getCenter());
		setBoundingBoxSize(bbox.getSize());
	}
	
	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public GroupingNode next() {
		for (BaseNode node = super.next(); node != null; node = node.next()) {
			if (node.isGroup() || node.isTransform() || node.isBillboard() || node.isCollision() || node.isLOD() || node.isSwitch() || node.isInline())
				return (GroupingNode)node;
		}
		return null;
	}

	public GroupingNode nextTraversal() {
		for (BaseNode node = super.nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.isGroup() || node.isTransform() || node.isBillboard() || node.isCollision() || node.isLOD() || node.isSwitch() || node.isInline())
				return (GroupingNode)node;
		}
		return null;
	}
*/
}
