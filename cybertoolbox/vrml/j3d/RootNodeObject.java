/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : RootNodeObject.java
*
******************************************************************/

package vrml.j3d;

import javax.media.j3d.*;

import vrml.node.*;

public class RootNodeObject extends BranchGroup implements NodeObject {

	public RootNodeObject(RootNode node) {
		setCapability(ALLOW_BOUNDS_READ);
		setCapability(ALLOW_CHILDREN_READ);
		setCapability(ALLOW_CHILDREN_WRITE);
		setCapability(ALLOW_CHILDREN_EXTEND);
		setCapability(ALLOW_DETACH);
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
		return true;
	}

	public boolean remove(vrml.node.Node node) {
		return true;
	}
}
