/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogRoute.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;
import vrml.route.*;

public class DialogRoute extends Dialog {

	World								mWorld;
	
	NodeComboBox						mEventOutNodeComboBox;
	EventOutFieldComboBox		mEventOutFieldComboBox;
	
	NodeComboBox						mEventInNodeComboBox;
	EventInFieldComboBox		mEventInFieldComboBox;

	public DialogRoute(Frame parentFrame, World world, Route route) {
		super(parentFrame, "New Route");
		
		mWorld = world;
		mEventOutNodeComboBox = new NodeComboBox(world, new EventOutNodeAction());
		mEventInNodeComboBox = new NodeComboBox(world, new EventInNodeAction());
		
		Node	eventOutNode	= null;
		Node	eventInNode	= null;
		
		if (route != null) {			
			eventOutNode	= route.getEventOutNode();
			eventInNode	= route.getEventInNode();
			mEventOutNodeComboBox.setNode(eventOutNode.getName());
			mEventInNodeComboBox.setNode(eventInNode.getName());
		}
		else {
			eventOutNode	= world.getSceneGraph().findNodeByName(mEventOutNodeComboBox.getNodeName());
			eventInNode	= world.getSceneGraph().findNodeByName(mEventInNodeComboBox.getNodeName());
		}

		mEventOutFieldComboBox = new EventOutFieldComboBox(eventOutNode, new EventOutFieldAction());
		mEventInFieldComboBox = new EventInFieldComboBox(eventInNode, new EventInFieldAction());
		
		Field eventOutField = null;
		Field eventInField = null;
		
		if (route != null) {			
			eventOutField = route.getEventOutField();
			eventInField = route.getEventInField();
			mEventOutFieldComboBox.setField(eventOutField.getName());
			mEventInFieldComboBox.setField(eventInField.getName());
		}
		else {
			if (eventOutNode != null) {
				String eventOutFieldName = mEventOutFieldComboBox.getFieldName();
				try {
					eventOutField = eventOutNode.getEventOut(eventOutFieldName);
				}
				catch (InvalidEventOutException ieoe) {
					eventOutField = eventOutNode.getExposedField(eventOutFieldName);
				}
			}
			if (eventInNode != null) {
				String eventInFieldName = mEventInFieldComboBox.getFieldName();
				try {
					eventInField = eventInNode.getEventIn(eventInFieldName);
				}
				catch (InvalidEventInException ieoe) {
					eventInField = eventInNode.getExposedField(eventInFieldName);
				}
			}
		}

		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = createRoutePanel(mEventOutNodeComboBox, mEventOutFieldComboBox, mEventInNodeComboBox, mEventInFieldComboBox);

		setComponents(dialogComponent);

		checkRoute();		
	}

	public DialogRoute(Frame parentFrame, World world) {
		this(parentFrame, world, null);
	}

	private JPanel createRoutePanel(NodeComboBox outNodeCombo, FieldComboBox outFieldCombo, NodeComboBox inNodeCombo, FieldComboBox inFieldCombo) {
		JPanel routePanel = new JPanel();
		routePanel.setLayout(new BoxLayout(routePanel, BoxLayout.X_AXIS));
		routePanel.add(createNodeFieldPanel("Output", outNodeCombo, outFieldCombo));
		routePanel.add(createNodeFieldPanel("Input", inNodeCombo, inFieldCombo));
		return routePanel;
	}

	private JPanel createNodeFieldPanel(String name, NodeComboBox nodeCombo, FieldComboBox fieldCombo) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder(name));
		panel.add(nodeCombo);
		panel.add(fieldCombo);
		return panel;
	}

	public Node getEventOutNode() {
		String nodeName = mEventOutNodeComboBox.getNodeName();
		return mWorld.getSceneGraph().findNodeByName(nodeName);
	}

	public Node getEventInNode() {
		String nodeName = mEventInNodeComboBox.getNodeName();
		return mWorld.getSceneGraph().findNodeByName(nodeName);
	}
	
	public Field getEventOutField() {
		Node node = getEventOutNode();
		if (node == null)
			return null;
		
		if (mEventOutFieldComboBox == null)
			return null;
			
		String fieldName = mEventOutFieldComboBox.getFieldName();
		
		Field field = null;
		try {
			field = node.getEventOut(fieldName);
		}
		catch (InvalidEventOutException ieoe) {
			try {
				field = node.getExposedField(fieldName);
			}
			catch (InvalidExposedFieldException iefe) {};
		}
		return field;
	}

	public Field getEventInField() {
		Node node = getEventInNode();
		if (node == null)
			return null;
			
		if (mEventInFieldComboBox == null)
			return null;
			
		String fieldName = mEventInFieldComboBox.getFieldName();
		
		Field field = null;
		try {
			field = node.getEventIn(fieldName);
		}
		catch (InvalidEventInException ieoe) {
			try {
				field = node.getExposedField(fieldName);
			}
			catch (InvalidExposedFieldException iefe) {};
		}
		return field;
	}
	
	public void checkRoute() {
		Debug.message("RouteDialog.checkRoute");
		Node eventOutNode		= getEventOutNode();
		Node eventInNode		= getEventInNode();
		Field eventOutField	= getEventOutField();
		Field eventInField	= getEventInField();
		
		Debug.message("\teventOutNode = " + eventOutNode);
		Debug.message("\teventInNode = " + eventInNode);
		Debug.message("\teventOutField = " + eventOutField);
		Debug.message("\teventInField = " + eventInField);
		
		if (eventOutNode == null || eventInNode == null || eventOutField == null || eventInField == null) {
			setOkButtonEnabled(false);
			Debug.message("\tisOkButtonEnabled = " + isOkButtonEnabled());
			return;
		}
		
		Debug.message("\teventOutFieldType = " + Integer.toHexString(eventOutField.getType()) + ", " + Integer.toString(eventOutField.getType()));
		Debug.message("\teventInFieldType  = " + Integer.toHexString(eventInField.getType()) + ", " + Integer.toString(eventInField.getType()));
		
		if (eventOutField.isSameValueType(eventInField) == false) {
			setOkButtonEnabled(false);
			Debug.message("\tisOkButtonEnabled = " + isOkButtonEnabled());
			return;
		}
		setOkButtonEnabled(true);
		Debug.message("\tisOkButtonEnabled = " + isOkButtonEnabled());
	}
	
	public Route getRoute() {
		Node eventOutNode		= getEventOutNode();
		Node eventInNode		= getEventInNode();
		Field eventOutField	= getEventOutField();
		Field eventInField	= getEventInField();
		if (eventOutNode == null || eventInNode == null || eventOutField == null || eventInField == null) 
			return null;
		if (eventOutField.isSameValueType(eventInField) == false) 
			return null;
		return new Route(eventOutNode, eventOutField, eventInNode, eventInField);
	}	
	
	private class EventOutNodeAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (mEventOutFieldComboBox == null)
				return;
			mEventOutFieldComboBox.initialize(getEventOutNode());
			checkRoute();
		}
	}

	private class EventInNodeAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (mEventInFieldComboBox == null)
				return;
			mEventInFieldComboBox.initialize(getEventInNode());
			checkRoute();
		}
	}

	private class EventOutFieldAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			checkRoute();
		}
	}

	private class EventInFieldAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			checkRoute();
		}
	}
	
	private class NodeComboBox extends JComboBox {
		public NodeComboBox(World world, ActionListener actionListener) {
			for (Node node = world.getSceneGraph().getNodes(); node != null; node=(Node)node.nextTraversal()) {
				if (world.isSystemNode(node) == false) {
					if (node.hasName() == true)
							addItem(node.getName());
				}
			}
			addActionListener(actionListener);
			setBorder(new TitledBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Node")));
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
	
		public String getNodeName() {
			return (String)getSelectedItem();
		}
	}

	private abstract class FieldComboBox extends JComboBox {
	
		public FieldComboBox(ActionListener actionListener) {
			addActionListener(actionListener);
			setBorder(new TitledBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Field")));
		}
		
		public abstract void initialize(Node node);
		
		public void setField(String fieldName) {
			if (fieldName == null)
				return;
			for (int n=0; n<getItemCount(); n++) {
				String itemName = (String)getItemAt(n);
				if (itemName.equals(fieldName) == true) {
					setSelectedIndex(n);
					return;
				}
			}
		}
	
		public String getFieldName() {
			return (String)getSelectedItem();
		}
	}
	
	private class EventOutFieldComboBox extends FieldComboBox {
		public EventOutFieldComboBox(Node node, ActionListener actionListener) {
			super(actionListener);
			initialize(node);
		}

		public void initialize(Node node) {
			if (0 < getItemCount())
				removeAllItems();
			
			if (node == null)
				return;
				
			int		n, nFields;
			
			nFields = node.getNEventOut();
			for (n=0; n<nFields; n++) {
				ConstField field = node.getEventOut(n);
				addItem(field.getName());
			}

			nFields = node.getNExposedFields();
			for (n=0; n<nFields; n++) {
				Field field = node.getExposedField(n);
				addItem(field.getName());
			}
		}
	}
	
	private class EventInFieldComboBox extends FieldComboBox {
		public EventInFieldComboBox(Node node, ActionListener actionListener) {
			super(actionListener);
			initialize(node);
		}

		public void initialize(Node node) {
			if (0 < getItemCount())
				removeAllItems();
			
			if (node == null)
				return;
				
			int		n, nFields;
			
			nFields = node.getNEventIn();
			for (n=0; n<nFields; n++) {
				Field field = node.getEventIn(n);
				addItem(field.getName());
			}

			nFields = node.getNExposedFields();
			for (n=0; n<nFields; n++) {
				Field field = node.getExposedField(n);
				addItem(field.getName());
			}
		}
	}
}
