/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogWorldNewDiagram.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogWorldNewDiagram extends Dialog {
	private EventTypeComboBoxComponent mEventComboBoxComponent = null;
	private DialogTextFieldComponent mNameTextFieldComponent = null;
	private World mWorld = null;
	
	public DialogWorldNewDiagram(Frame parentFrame, World world) {
		super(parentFrame, "New Diagram");
		setSize(300, 200);
		mWorld = world;
		mEventComboBoxComponent = new EventTypeComboBoxComponent();
		mNameTextFieldComponent = new DialogTextFieldComponent("Name");
		JComponent dialogComponent[] = new JComponent[2];
		dialogComponent[0] = mEventComboBoxComponent;
		dialogComponent[1] = mNameTextFieldComponent;		
		setComponents(dialogComponent);
	}
	
	public int getEventIndex() {
		return mEventComboBoxComponent.getSelectedIndex();
	}
			
	public String getDiagramName() {
		return mNameTextFieldComponent.getText();
	}

	private World getWorld() {
		return mWorld;
	}
	
	private class EventTypeComboBoxComponent extends JComboBox {
		public EventTypeComboBoxComponent() {
			int	nEvent = 0;
			for (Node eventNode = getWorld().getEventNodes(); eventNode != null; eventNode=eventNode.next()) {
				Event	event = new Event(getWorld(), eventNode);
				String	eventName = event.getEventTypeName();
				String	optionName = event.getOptionString();
				String	itemName = null;
				if (optionName == null)
					itemName = eventName;
				else
					itemName = eventName + " ( " + optionName + " )";
				Debug.message("Event : " + itemName);
				addItem(itemName);
				nEvent++;
			}
			setBorder(new TitledBorder(new TitledBorder(LineBorder.createBlackLineBorder(), "Event Type")));
		}
	}
}
		
