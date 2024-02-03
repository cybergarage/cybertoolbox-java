/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ImageTextureNodeObject.java
*
******************************************************************/

package vrml.j3d;

import java.awt.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import vrml.node.*;
import vrml.field.*;

public class ImageTextureNodeObject extends Texture2D implements NodeObject {
	
	public ImageTextureNodeObject(ImageTextureLoader imgTexLoader) {
		super(BASE_LEVEL, RGBA, imgTexLoader.getWidth(), imgTexLoader.getHeight());
		setMinFilter(BASE_LEVEL_LINEAR);
		setMagFilter(BASE_LEVEL_LINEAR);
		setImage(0, imgTexLoader.getImageComponent());
		setEnable(true);
	}

	public boolean initialize(vrml.node.Node node) {
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
			if (parentNode.isAppearanceNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Appearance parentAppearanceNode = (Appearance)parentNodeObject;
					parentAppearanceNode.setTexture(this);
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
					parentAppearanceNode.setTexture(null);
				}
			}
		}
		
		return true;
	}
}
