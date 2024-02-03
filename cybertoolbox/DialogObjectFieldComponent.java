/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogObjectFieldComponent.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogObjectFieldComponent extends JComboBox {

	private World mWorld;
	
	public DialogObjectFieldComponent(World world) {
		setMaximumRowCount(2);		
		for (TransformNode trans = world.getSceneGraph().findTransformNode(); trans != null; trans=(TransformNode)trans.nextTraversalSameType()) {
			if (world.isSystemNode(trans) == false) {
				String transName = trans.getName();
				if (transName != null) {
					if (0 < transName.length())
						addItem(transName);
				}
			}
		}
		setBorder(new TitledBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Object")));
		mWorld = world;
	}

	public void setNode(String nodeName) {
		if (nodeName == null)
			return;
		for (int n=0; n<getItemCount(); n++) {
			String itemName = (String)getItemAt(n);
			if (itemName.equals(nodeName) == true) {
				setSelectedIndex(n);
				return;
			}
		}
	}
	
	public void setNode(Node node) {
		if (node == null)
			return;
		setNode(node.getName());
	}
	
	public Node getNode() {
		String nodeName = (String)getSelectedItem();
		return mWorld.getSceneGraph().findTransformNode(nodeName);
	}
}
	
