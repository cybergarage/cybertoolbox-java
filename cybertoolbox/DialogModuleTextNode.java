/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleTextNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleTextNode extends DialogModuleNode {
	
	public DialogModuleTextNode(Frame parentFrame, World world, TextNode node) {
		super(parentFrame, "Module Text");
		
		setSize(300, 200);
		setWorld(world);
		
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = createNodeComboBox("Text", world.getSceneGraph().findTextNode());
		setComponents(dialogComponent);
		
		setNode(node);
	}
	
	public Node getNode() {
		String nodeName = (String)getComboBox().getSelectedItem();
		return getWorld().getSceneGraph().findTextNode(nodeName);
	}
}
		
