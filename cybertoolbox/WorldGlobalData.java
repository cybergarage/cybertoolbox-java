/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	WorldGlobalData.java
*
******************************************************************/

import vrml.node.AnchorNode;
import vrml.field.SFString;

public class WorldGlobalData extends Object implements WorldConstants {
	
	private AnchorNode mAnchorNode = null;

	public WorldGlobalData(String name) {
		AnchorNode node = new AnchorNode();
		setName(name);
		setNode(node);
	}

	public WorldGlobalData(AnchorNode node) {
		setNode(node);
	}

	//////////////////////////////////////////////////
	// Node
	//////////////////////////////////////////////////

	private void setNode(AnchorNode node) {
		mAnchorNode = node;
	}

	public AnchorNode getNode() {
		return mAnchorNode;
	}

	//////////////////////////////////////////////////
	// Name		
	//////////////////////////////////////////////////

	public void setName(String name) {
		String nodeName = WORLD_GLOBALDATA_NODENAME + "_" + name;
		getNode().setName(nodeName);
	}
	
	public String getName() {
		String nodeName = getNode().getName();
		int index = nodeName.lastIndexOf('_');
		return new String(nodeName.getBytes(), index+1, nodeName.length() - (index+1));
	}

	//////////////////////////////////////////////////
	// Field
	//////////////////////////////////////////////////
/*
	public SFString getDataField() {
		return (SFString)getScriptNode().getEventIn(WORLD_GLOBALDATA_FIELDNAME);
	}
*/
}
