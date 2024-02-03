/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogEventDragPanel.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogEventDragPanel extends DialogEventPanel {
	
	private DialogObjectFieldComponent mObjectComboBoxComponent = null;
	
	public DialogEventDragPanel(World world) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		mObjectComboBoxComponent = new DialogObjectFieldComponent(world);
		add(mObjectComboBoxComponent);
	}

	public DialogEventDragPanel(World world, Event event) {
		this(world);
		setNode(event.getOptionString());
	}

	public void setNode(String nodeName) {
		mObjectComboBoxComponent.setNode(nodeName);
	}
	
	public void setNode(Node node) {
		mObjectComboBoxComponent.setNode(node);
	}

	public Node getNode() {
		return mObjectComboBoxComponent.getNode();
	}
	
	public String getOptionString() {
		Node node = getNode();
		if (node == null) 
			return null;
		return node.getName();
	}
}
		
