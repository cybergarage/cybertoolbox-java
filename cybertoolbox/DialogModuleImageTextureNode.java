/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleImageTextureNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleImageTextureNode extends DialogModuleNode {
	
	public DialogModuleImageTextureNode(Frame parentFrame, World world, ImageTextureNode node) {
		super(parentFrame, "Module ImageTexture");
		
		setSize(300, 200);
		setWorld(world);
		
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = createNodeComboBox("ImageTexture", world.getSceneGraph().findImageTextureNode());
		setComponents(dialogComponent);
		
		setNode(node);
	}
	
	public Node getNode() {
		String nodeName = (String)getComboBox().getSelectedItem();
		return getWorld().getSceneGraph().findImageTextureNode(nodeName);
	}
}
		
