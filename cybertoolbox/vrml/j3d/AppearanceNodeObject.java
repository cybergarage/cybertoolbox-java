/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : AppearanceNodeObject.java
*
******************************************************************/

package vrml.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import vrml.node.*;
import vrml.field.*;

public class AppearanceNodeObject extends Appearance implements NodeObject {
	
	public AppearanceNodeObject(AppearanceNode node) {
		setCapability(ALLOW_POLYGON_ATTRIBUTES_READ);
		setCapability(ALLOW_POLYGON_ATTRIBUTES_WRITE);
		initialize(node);
	}

	public boolean initialize(vrml.node.Node node) {
		PolygonAttributes polyAttr = new PolygonAttributes();
		polyAttr.setCapability(PolygonAttributes.ALLOW_MODE_READ);
		polyAttr.setCapability(PolygonAttributes.ALLOW_MODE_WRITE);
		setPolygonAttributes(polyAttr);
		return true;
	}
	
	public boolean uninitialize(vrml.node.Node node) {
		return true;
	}
			
	public boolean update(vrml.node.Node node) {
		return true;
	}
	
	public boolean add(vrml.node.Node node) {

		vrml.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setAppearance(this);
				}
			}
		}
		
		return true;
	}

	public boolean remove(vrml.node.Node node) {
	
		vrml.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setAppearance(new NullAppearanceObject());
				}
			}
		}
		
		return true;
	}
}
