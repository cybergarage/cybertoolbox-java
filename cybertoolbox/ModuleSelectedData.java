/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DiagramModuleData.java
*
******************************************************************/

import vrml.*;
import vrml.node.*;

public class ModuleSelectedData extends Object implements ModuleConstants {

	private ScriptNode	mNode;
	private int				mParts;
	
	public ModuleSelectedData() {
		setScriptNode(null);
		setParts(MODULE_OUTSIDE);
	}
	
	public ModuleSelectedData(ScriptNode node, int parts) {
		setScriptNode(node);
		setParts(parts);
	}
	
	public void setParts(int parts) {
		mParts = parts;
	}
	
	public int getParts() {
		return mParts;
	}

	public void setScriptNode(ScriptNode node) {
		mNode = node;
	}
	
	public ScriptNode getScriptNode() {
		return mNode;
	}
	
}
