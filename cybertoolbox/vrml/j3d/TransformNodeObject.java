/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : TransformNodeObject.java
*
******************************************************************/

package vrml.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import vrml.node.*;
import vrml.field.*;

public class TransformNodeObject extends TransformGroup implements NodeObject {

	public TransformNodeObject(TransformNode node) {
		setCapability(ALLOW_CHILDREN_READ);
		setCapability(ALLOW_CHILDREN_WRITE);
		setCapability(ALLOW_TRANSFORM_READ);
		setCapability(ALLOW_TRANSFORM_WRITE);
		initialize(node);
	}
	
	public boolean initialize(vrml.node.Node node) {
		node.setRunnable(true);
		node.setRunnableType(vrml.node.Node.RUNNABLE_TYPE_ALWAYS);
		update(node);
		return true;
	}

	public boolean uninitialize(vrml.node.Node node) {
		return true;
	}
			
	public boolean update(vrml.node.Node node) {
		TransformNode transNode = (TransformNode)node;
		float translation[] = new float[3];
		float rotation[] = new float[4];
		
		transNode.getTranslation(translation);	
		transNode.getRotation(rotation);
		
		Transform3D trans3D = new Transform3D();

		getTransform(trans3D);
		 		 
		Vector3f vector = new Vector3f(translation);
		trans3D.setTranslation(vector);
				
		AxisAngle4f axisAngle = new AxisAngle4f(rotation);
		trans3D.setRotation(axisAngle);
		
		setTransform(trans3D);
	
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
}
