/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public abstract class DialogModuleNode extends Dialog {

	private JComboBox	mComboBox	= null;
	private World		mWorld		= null;
			
	public void setWorld(World world) {
		mWorld = world;
	}
						
	public World getWorld() {
		return mWorld;
	}

	public void setComboBox(JComboBox combo) {
		mComboBox = combo;
	}
						
	public JComboBox getComboBox() {
		return mComboBox;
	}

	public DialogModuleNode(Frame parentComponent, String title, JComponent components[]) {
		super(parentComponent, title, components);
		setWorld(null);
	}

    public DialogModuleNode(Frame parentComponent, String title) {
		super(parentComponent, title);
		setWorld(null);
	}

    public DialogModuleNode(Frame parentComponent) {
		super(parentComponent);
		setWorld(null);
	}
		
	public void setNode(String nodeName) {
		if (nodeName == null)
			return;
		JComboBox combo = getComboBox();
		for (int n=0; n<combo.getItemCount(); n++) {
			String itemName = (String)combo.getItemAt(n);
			if (itemName.equals(nodeName) == true) {
				combo.setSelectedIndex(n);
				return;
			}
		}
	}
	
	public void setNode(Node node) {
		if (node == null)
			return;
		setNode(node.getName());
	}
	
	abstract public Node getNode();
	
	public JComboBox createNodeComboBox(String borderName, Node firstNode) {
		JComboBox combo = new JComboBox();
		
		combo.setMaximumRowCount(2);		
		combo.setBorder(new TitledBorder(new TitledBorder(LineBorder.createBlackLineBorder(), borderName)));
		
		for (Node node = firstNode; node != null; node=node.nextTraversalSameType()) {
			if (getWorld().isSystemNode(node) == false) {
				String nodeName = node.getName();
				if (nodeName != null) {
					if (0 < nodeName.length())
						combo.addItem(nodeName);
				}
			}
		}
		
		setComboBox(combo);
		
		return combo;
	}

}
		
