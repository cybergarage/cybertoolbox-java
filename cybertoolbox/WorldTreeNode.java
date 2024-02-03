/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	WorldTreeNode.java
*
******************************************************************/

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.tree.*;

import vrml.*;
import vrml.node.*;

public class WorldTreeNode extends DefaultMutableTreeNode
{
    public WorldTreeNode(WorldTreeData o) {
		super(o);
    }

    public boolean isLeaf() {
		return false;
    }

    public int getChildCount() {
		return super.getChildCount();
    }

	public void setNode(Node node) {
		((WorldTreeData)getUserObject()).setNode(node);
	}

	public Node getNode() {
		return ((WorldTreeData)getUserObject()).getNode();
	}
	
	public void setText(String text) {
		((WorldTreeData)getUserObject()).setText(text);
	}

	public String getText() {
		return ((WorldTreeData)getUserObject()).getText();
	}

	public String toString() {
		return ((WorldTreeData)getUserObject()).toString();
	}
}
