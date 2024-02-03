/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SwitchNodeObject.java
*
******************************************************************/

package vrml.j3d;

import javax.media.j3d.*;
import vrml.node.*;
import vrml.field.*;

public class SwitchNodeObject extends Switch implements NodeObject {

	public SwitchNodeObject() {
		setCapability(ALLOW_CHILDREN_READ);
		setCapability(ALLOW_CHILDREN_WRITE);
		setCapability(ALLOW_SWITCH_READ);
		setCapability(ALLOW_SWITCH_WRITE);
	}

	public SwitchNodeObject(SwitchNode node) {
		this();
	}
	
	public boolean initialize(vrml.node.Node node) {
		update(node);
		return true;
	}

	public boolean uninitialize(vrml.node.Node node) {
		return true;
	}
			
	public boolean update(vrml.node.Node node) {
		SwitchNode switchNode = (SwitchNode)node;
		
		int j3dWhichChild	= getWhichChild();
		int whichChoice	= switchNode.getWhichChoice();
		
		if (j3dWhichChild != whichChoice) {
			if (0 <= whichChoice && whichChoice < numChildren())
				setWhichChild(whichChoice);
		}
		
		return true;
	}
	
	public Group getParentGroup(vrml.node.Node node) {
		vrml.node.Node	parentNode		= node.getParentNode();
		Group			parentGroupNode	= null;
		if (parentNode != null) {
			NodeObject parentNodeObject  = parentNode.getObject();
			if (parentNodeObject != null)
				parentGroupNode = (Group)parentNodeObject;
		}
		else {
			vrml.SceneGraph sg = node.getSceneGraph();
			if (sg != null) {
				SceneGraphJ3dObject	sgObject = (SceneGraphJ3dObject)sg.getObject();
				if (sgObject != null)
					parentGroupNode = sgObject.getRootNode();
			}
		}
		return parentGroupNode;
	}
	
	public boolean add(vrml.node.Node node) {
		if (getParent() == null) {
			Group parentGroupNode = getParentGroup(node);
			if (parentGroupNode != null) {
					parentGroupNode.addChild(this);
			}
		}
		return true;
	}

	public boolean remove(vrml.node.Node node) {
		Group parentGroupNode = getParentGroup(node);
		if (parentGroupNode != null) {
			for (int n=0; n<parentGroupNode.numChildren(); n++) {
				if (parentGroupNode.getChild(n) == this) {
					parentGroupNode.removeChild(n);
					return true;
				}
			}
		}
		return false;
	}

	public void removeAllChildren() {
		while (0 < numChildren()) 
			removeChild(numChildren()-1);
	}
}
