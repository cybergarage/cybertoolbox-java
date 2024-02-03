/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleScalarInterpolatorNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleScalarInterpolatorNode extends DialogModuleNode {
	
	public DialogModuleScalarInterpolatorNode(Frame parentFrame, World world, ScalarInterpolatorNode node) {
		super(parentFrame, "Module ScalarInterpolator");

		setSize(300, 200);
		setWorld(world);

		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = createNodeComboBox("ScalarInterpolator", world.getSceneGraph().findScalarInterpolatorNode());
		setComponents(dialogComponent);

		setNode(node);
	}
	
	public Node getNode() {
		String nodeName = (String)getComboBox().getSelectedItem();
		return getWorld().getSceneGraph().findScalarInterpolatorNode(nodeName);
	}
}
		
