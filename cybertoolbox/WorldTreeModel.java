/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	WorldTreeModel.java
*
******************************************************************/

import java.awt.Color;

import javax.swing.tree.*;

public class WorldTreeModel extends DefaultTreeModel
{
    public WorldTreeModel(TreeNode newRoot) {
		super(newRoot);
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
		DefaultMutableTreeNode	aNode = (DefaultMutableTreeNode)path.getLastPathComponent();
		WorldTreeData			sampleData = (WorldTreeData)aNode.getUserObject();
		sampleData.setText((String)newValue);
		nodeChanged(aNode);
    }
}
