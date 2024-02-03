/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleAudioClipNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleAudioClipNode extends DialogModuleNode {
	
	public DialogModuleAudioClipNode(Frame parentFrame, World world, AudioClipNode node) {
		super(parentFrame, "Module AudioClip");
		
		setSize(300, 200);
		setWorld(world);
		
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = createNodeComboBox("AudioClip", world.getSceneGraph().findAudioClipNode());
		setComponents(dialogComponent);
		
		setNode(node);
	}
	
	public Node getNode() {
		String nodeName = (String)getComboBox().getSelectedItem();
		return getWorld().getSceneGraph().findAudioClipNode(nodeName);
	}
}
		
