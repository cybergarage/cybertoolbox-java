/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MaterialNodeObject.java
*
******************************************************************/

package vrml.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import vrml.node.*;
import vrml.field.*;

public class MaterialNodeObject extends Material implements NodeObject {
	
	public MaterialNodeObject(MaterialNode node) {
		setCapability(ALLOW_COMPONENT_READ);
		setCapability(ALLOW_COMPONENT_WRITE);
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
		MaterialNode	matNode = (MaterialNode)node;
		float			color[] = new float[3];
		
		matNode.getAmbientColor(color);
		Color3f ambColor3f = new Color3f(color);
		setAmbientColor(ambColor3f);
		
		matNode.getDiffuseColor(color);
		Color3f diffColor3f = new Color3f(color);
		setDiffuseColor(diffColor3f);
		
		matNode.getEmissiveColor(color);
		Color3f emiColor3f = new Color3f(color);
		setEmissiveColor(emiColor3f);
		
		matNode.getSpecularColor(color);
		Color3f speColor3f = new Color3f(color);
		setSpecularColor(speColor3f);

		setShininess(matNode.getShininess());
		
		return true;
	}
	
	public boolean add(vrml.node.Node node) {

		vrml.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isAppearanceNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Appearance parentAppearanceNode = (Appearance)parentNodeObject;
					parentAppearanceNode.setMaterial(this);
				}
			}
		}
		
		return true;
	}

	public boolean remove(vrml.node.Node node) {
	
		vrml.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isAppearanceNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Appearance parentAppearanceNode = (Appearance)parentNodeObject;
					parentAppearanceNode.setMaterial(new NullMaterialObject());
				}
			}
		}
		
		return true;
	}
}
