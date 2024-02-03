/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleObjectNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleObjectNode extends DialogModuleNode {
	
	private DialogObjectFieldComponent mObjectComboBoxComponent = null;
	
	public DialogModuleObjectNode(Frame parentFrame, World world, TransformNode node) {
		super(parentFrame, "Module Object");
		setSize(300, 200);
		
		mObjectComboBoxComponent = new DialogObjectFieldComponent(world);
		mObjectComboBoxComponent.setNode(node);
		
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mObjectComboBoxComponent;
		setComponents(dialogComponent);
	}
	
	public Node getNode() {
		return mObjectComboBoxComponent.getNode();
	}
}
		
