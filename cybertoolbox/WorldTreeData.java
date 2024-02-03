/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	WorldTreeData.java
*
******************************************************************/

import vrml.*;
import vrml.node.*;

public class WorldTreeData extends Object
{
	private String	mString;
	private Node	mNode;

	public WorldTreeData(String string, Node node) {
		setText(string);
		setNode(node);
	}

	public void setNode(Node node) {
		mNode = node;
	}

	public Node getNode() {
		return mNode;
	}
	
	public void setText(String string) {
		mString = string;
	}

	public String getText() {
		return mString;
	}

	public String toString() {
		return mString;
	}
}
