/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModulePositionInterpolatorNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModulePositionInterpolatorNode extends DialogModuleNode {
	
	public DialogModulePositionInterpolatorNode(Frame parentFrame, World world, PositionInterpolatorNode node) {
		super(parentFrame, "Module PositionInterpolator");
		
		setSize(300, 200);
		setWorld(world);
		
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = createNodeComboBox("PositionInterpolator", world.getSceneGraph().findPositionInterpolatorNode());
		setComponents(dialogComponent);
		
		setNode(node);
	}
	
	public Node getNode() {
		String nodeName = (String)getComboBox().getSelectedItem();
		return getWorld().getSceneGraph().findPositionInterpolatorNode(nodeName);
	}
}
		
