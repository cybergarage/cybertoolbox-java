/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleNormalInterpolatorNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleNormalInterpolatorNode extends DialogModuleNode {
	private JComboBox mNormalInterpolatorComboBoxComponent = null;
	
	public DialogModuleNormalInterpolatorNode(Frame parentFrame, World world, NormalInterpolatorNode node) {
		super(parentFrame, "Module NormalInterpolator");
		
		setSize(300, 200);
		setWorld(world);
		
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = createNodeComboBox("NormalInterpolator", world.getSceneGraph().findNormalInterpolatorNode());
		setComponents(dialogComponent);
		
		setNode(node);
	}
	
	public Node getNode() {
		String nodeName = (String)getComboBox().getSelectedItem();
		return getWorld().getSceneGraph().findNormalInterpolatorNode(nodeName);
	}
}
		
