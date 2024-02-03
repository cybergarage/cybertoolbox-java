/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleColorInterpolatorNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleColorInterpolatorNode extends DialogModuleNode {
	
	public DialogModuleColorInterpolatorNode(Frame parentFrame, World world, Node node) {
		super(parentFrame, "Module ColorInterpolator");
		
		setSize(300, 200);
		setWorld(world);
		
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = createNodeComboBox("ColorInterpolator", world.getSceneGraph().findColorInterpolatorNode());
		setComponents(dialogComponent);
		
		setNode(node);
	}
	
	public Node getNode() {
		String nodeName = (String)getComboBox().getSelectedItem();
		return getWorld().getSceneGraph().findColorInterpolatorNode(nodeName);
	}
}
		
