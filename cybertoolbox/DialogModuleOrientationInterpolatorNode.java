/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleOrientationInterpolatorNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleOrientationInterpolatorNode extends DialogModuleNode {

	public DialogModuleOrientationInterpolatorNode(Frame parentFrame, World world, OrientationInterpolatorNode node) {
		super(parentFrame, "Module OrientationInterpolator");
		
		setSize(300, 200);
		setWorld(world);
		
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = createNodeComboBox("OrientationInterpolator", world.getSceneGraph().findOrientationInterpolatorNode());
		setComponents(dialogComponent);
		
		setNode(node);
	}
	
	public Node getNode() {
		String nodeName = (String)getComboBox().getSelectedItem();
		return getWorld().getSceneGraph().findOrientationInterpolatorNode(nodeName);
	}
}
		
