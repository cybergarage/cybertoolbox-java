/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleLightNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogModuleLightNode extends DialogModuleNode {

	private static final int DIRECTIONAL_LIGHT	= 0x01;
	private static final int POINT_LIGHT		= 0x02;
	private static final int SPOT_LIGHT			= 0x04;
	
	private int mType = 0;
	
	public DialogModuleLightNode(Frame parentFrame, World world, LightNode node, int type) {
		super(parentFrame, "Module Light");
		setSize(300, 200);
		setType(type);
		setWorld(world);
		
		JComboBox comboBox = new LightComboBoxComponent();
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = comboBox;
		setComponents(dialogComponent);
		
		setComboBox(comboBox);
		setNode(node);
	}
	
	public DialogModuleLightNode(Frame parentFrame, World world, LightNode node) {
		this(parentFrame, world, node, DIRECTIONAL_LIGHT | POINT_LIGHT | SPOT_LIGHT);
	}
	
	public Node getNode() {
		String nodeName = (String)getComboBox().getSelectedItem();
		LightNode node = null;
		if (getType(DIRECTIONAL_LIGHT) == true)
			node = getWorld().getSceneGraph().findDirectionalLightNode(nodeName);
		if (node == null && getType(POINT_LIGHT) == true)
			node = getWorld().getSceneGraph().findPointLightNode(nodeName);
		if (node == null && getType(SPOT_LIGHT) == true)
			node = getWorld().getSceneGraph().findSpotLightNode(nodeName);
		return node;
	}
	
	private void setType(int type) {
		mType = type;
	}
	
	private int getType() {
		return mType;
	}
	 
	private boolean getType(int type) {
		if ((mType & type) != 0)
			return true;
		return false;
	}
	
	private class LightComboBoxComponent extends JComboBox {
		public LightComboBoxComponent() {
			setMaximumRowCount(2);		
			
			LightNode light;
			
			if (getType(DIRECTIONAL_LIGHT) == true) {
				for (light = getWorld().getSceneGraph().findDirectionalLightNode(); light != null; light=(LightNode)light.nextTraversalSameType()) {
					if (getWorld().isSystemNode(light) == false) {
						String nodeName = light.getName();
						if (nodeName != null) {
							if (0 < nodeName.length())
								addItem(nodeName);
						}
					}
				}
			}

			if (getType(POINT_LIGHT) == true) {
				for (light = getWorld().getSceneGraph().findPointLightNode(); light != null; light=(LightNode)light.nextTraversalSameType()) {
					if (getWorld().isSystemNode(light) == false) {
						String nodeName = light.getName();
						if (nodeName != null) {
							if (0 < nodeName.length())
								addItem(nodeName);
						}
					}
				}
			}

			if (getType(SPOT_LIGHT) == true) {
				for (light = getWorld().getSceneGraph().findSpotLightNode(); light != null; light=(LightNode)light.nextTraversalSameType()) {
					if (getWorld().isSystemNode(light) == false) {
						String nodeName = light.getName();
						if (nodeName != null) {
							if (0 < nodeName.length())
								addItem(nodeName);
						}
					}
				}
			}
											
			setBorder(new TitledBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Light")));
		}
	}
}
		
