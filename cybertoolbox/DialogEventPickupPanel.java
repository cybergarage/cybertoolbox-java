/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogEventPickupPanel.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;

public class DialogEventPickupPanel extends DialogEventPanel {
	
	private DialogObjectFieldComponent mObjectComboBoxComponent = null;
	
	public DialogEventPickupPanel(World world) {
		setLayout(new BorderLayout());
		mObjectComboBoxComponent = new DialogObjectFieldComponent(world);
		add(mObjectComboBoxComponent, BorderLayout.NORTH);
	}

	public DialogEventPickupPanel(World world, Event event) {
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
		
