/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleSwitchNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleSwitchNode extends DialogModuleNode {
	
	public DialogModuleSwitchNode(Frame parentFrame, World world, SwitchNode node) {
		super(parentFrame, "Module Switch");
		
		setSize(300, 200);
		setWorld(world);
		
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = createNodeComboBox("Switch", world.getSceneGraph().findSwitchNode());
		setComponents(dialogComponent);
		
		setNode(node);
	}
	
	public Node getNode() {
		String nodeName = (String)getComboBox().getSelectedItem();
		return getWorld().getSceneGraph().findSwitchNode(nodeName);
	}
}
		
