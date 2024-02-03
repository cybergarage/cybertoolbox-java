/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	Module.java
*
******************************************************************/

import java.io.*;

import vrml.*;
import vrml.node.*;
import vrml.field.*;
import vrml.route.*;
import vrml.util.*;

public class Module extends Object implements ModuleTypeConstants, ModuleConstants, vrml.Constants {
	
	private ModuleType	mModuleType	= null;
	private ScriptNode	mScriptNode	= null;
	private World		mWorld		= null;
	
	public void setWorld(World world) {
		mWorld = world;
	}
	
	public World getWorld() {
		return mWorld;
	}

	public Module(World world, ModuleType moduleType) {
		Debug.message("Module.<init>");
		Debug.message("\tModuleType\n" + moduleType);
		
		setWorld(world);
		setModuleType(moduleType);

		ScriptNode script = new ScriptNode();
		script.setDirectOutput(true);
		setScriptNode(script);

		// Set script
		script.addUrl(moduleType.getScriptFileName());

		// Add system eventIn fields
		int	n;
		for (n=0; n<getWorld().getNEventTypes(); n++) {
			EventType eventType = getWorld().getEventType(n);
			script.addEventIn(eventType.getFieldTypeName(), eventType.getModuleFieldName());
		}

		// Add ExecutionNode
		if (moduleType.hasExecutionNode() == true) 
			script.addEventIn(MODULE_EXECUTION_EVENTINNAME, new SFString("TRUE"));

		// Add system fields of the module type
		script.addField(MODULE_CLASSNAME_FIELDNAME, new SFString(moduleType.getClassName()));
		script.addField(MODULE_MODULENAME_FIELDNAME, new SFString(moduleType.getName()));
		script.addField(MODULE_XPOS_FIELDNAME, new SFInt32());
		script.addField(MODULE_YPOS_FIELDNAME, new SFInt32());
		script.addField(MODULE_VALUE_FIELDNAME, new SFString("0"));

		// Source Field
		script.addField(MODULE_SOURCE_NODENAME, new SFNode());

		// Target Field
		script.addField(MODULE_TARGET_NODENAME, new SFNode());

		// Add user eventType fields of the module type (Need to add the user fields at last)

		for (n=0; n<MODULE_INOUTNODE_MAXNUM; n++) {
			Debug.message("\tn : " + moduleType.getEventInFieldType(n) + ", " + moduleType.getEventOutFieldType(n));
			if (moduleType.getEventInFieldType(n) != fieldTypeNone)
				script.addEventIn(moduleType.getEventInFieldTypeName(n), moduleType.getEventInFieldName(n));
			if (moduleType.getEventOutFieldType(n) != fieldTypeNone) {
				script.addEventOut(moduleType.getEventOutFieldTypeName(n), moduleType.getEventOutFieldName(n));
			}
		}

		// Initialize script node (The initialization is hang up !! 06/04/1997)
		//	script->initialize();	
	}
	
	public Module(World world, ScriptNode script) {
		setWorld(world);
		setScriptNode(script);
		String className = ((SFString)script.getField(MODULE_CLASSNAME_FIELDNAME)).getValue();
		String moduleName = ((SFString)script.getField(MODULE_MODULENAME_FIELDNAME)).getValue();
		ModuleType cmType = getWorld().getModuleType(className, moduleName);
		setModuleType(cmType);
	}
	
	//////////////////////////////////////////////////
	// ModuleType
	//////////////////////////////////////////////////

	public void setModuleType(ModuleType moduleType) {
		mModuleType = moduleType;
	}

	public ModuleType getModuleType() {
		return mModuleType;
	}

	//////////////////////////////////////////////////
	// Script
	//////////////////////////////////////////////////

	public void setScriptNode(ScriptNode script) {
		mScriptNode = script;
	}

	public ScriptNode getScriptNode() {
		return mScriptNode;
	}

	////////////////////////////////////////////////
	//	Name
	////////////////////////////////////////////////

	public void setName(String name) {
		getScriptNode().setName(name);
	}

	public String getName() {
		return getScriptNode().getName();
	}

	////////////////////////////////////////////////
	//	X position
	////////////////////////////////////////////////

	public void setXPosition(int value) {
		SFInt32 xpos = (SFInt32)getScriptNode().getField(MODULE_XPOS_FIELDNAME);
		xpos.setValue(value);
	}
	public int getXPosition() {
		SFInt32 xpos = (SFInt32)getScriptNode().getField(MODULE_XPOS_FIELDNAME);
		return xpos.getValue();
	}

	////////////////////////////////////////////////
	//	Y position
	////////////////////////////////////////////////

	public void setYPosition(int value) {
		SFInt32 ypos = (SFInt32)getScriptNode().getField(MODULE_YPOS_FIELDNAME);
		ypos.setValue(value);
	}
	public int getYPosition() {
		SFInt32 ypos = (SFInt32)getScriptNode().getField(MODULE_YPOS_FIELDNAME);
		return ypos.getValue();
	}

	////////////////////////////////////////////////
	//	Position
	////////////////////////////////////////////////

	public void setPosition(int x, int y) {
		setXPosition(x);
		setYPosition(y);
	}

	////////////////////////////////////////////////
	//	Value
	////////////////////////////////////////////////

	public void setValue(String value) {
		SFString svalue = (SFString)getScriptNode().getField(MODULE_VALUE_FIELDNAME);
		svalue.setValue(value);
	}
	public String getValue() {
		SFString svalue = (SFString)getScriptNode().getField(MODULE_VALUE_FIELDNAME);
		return svalue.getValue();
	}

	////////////////////////////////////////////////
	//	Size
	////////////////////////////////////////////////

	public int getSize() { 
		return MODULE_SIZE; 
	}

	////////////////////////////////////////////////
	//	Number
	////////////////////////////////////////////////
		
	public int getNumber() {
		ScriptNode script = getScriptNode();
		String name = script.getName();
		int index = name.lastIndexOf('E'); 
		String valueString = new String(name.toCharArray(), (index + 1), name.length() - (index + 1));
		Integer value = new Integer(valueString);
		return value.intValue();
	}

	////////////////////////////////////////////////
	//	Event Field
	////////////////////////////////////////////////

	public int getNEventInFields() {
		return getScriptNode().getNEventIn() - getNSystemEventInNodes();
	}
	
	public int getNEventOutFields() {
		return getScriptNode().getNEventOut();
	}

	public Field getEventInField(int n) {
		return getScriptNode().getEventIn(n + getNSystemEventInNodes());
	}
		
	public Field getEventOutField(int n) {
		return getScriptNode().getEventOut(n);
	}

	////////////////////////////////////////////////
	//	Event Field Node
	////////////////////////////////////////////////
		
	public int getNodeSize() {
		return MODULE_NODE_SIZE;
	}

	public int getNodeSpacing() {
		return MODULE_NODE_SPACING;
	}
	
	public int getNSystemEventInNodes() {
		Node node = getScriptNode();
		int	nSystemEventIn = 0;
		int	nEventIn = node.getNEventIn();
		for (int n=0; n<nEventIn; n++) {
			Field field = node.getEventIn(n);
			try {
				String fieldHeaderName = new String(field.getName().toCharArray(), 0, MODULE_SYSTEMEVENT_FIELDNAME_HEADSTRING.length()); 
				if (fieldHeaderName.equals(MODULE_SYSTEMEVENT_FIELDNAME_HEADSTRING) == true)
					nSystemEventIn++;
			} catch (StringIndexOutOfBoundsException e){};
		}

		/**** Compatibility for Beta 1.0/1.1 ****/
		if (nSystemEventIn == 0)
			nSystemEventIn = 6;

		return nSystemEventIn;
	}
		
	public int getNEventInNodes() {
		return getNEventInFields();
	}
	
	public int getNEventOutNodes() {
		return getNEventOutFields();
	}
	
	public int getEventInNodeSpacing() {
		return (MODULE_SIZE - MODULE_NODE_SIZE * getNEventInNodes()) / (getNEventInNodes()+1);
	}
	
	public int getEventOutNodeSpacing()	{
		return (MODULE_SIZE - MODULE_NODE_SIZE * getNEventOutNodes()) / (getNEventOutNodes()+1);
	}

	public boolean hasEventInNode() {
		return (0 < getNEventInNodes()) ? true : false;
	}
	
	public boolean hasEventOutNode() {
		return (0 < getNEventOutNodes()) ? true : false;
	}

	public int getEventInNodeXPosition(int n) {
		return (getXPosition() - getNodeSize());
	}

	public int getEventInNodeYPosition(int n) {
		return (getYPosition() + (getEventInNodeSpacing() * (n+1)) + (getNodeSize() * n));
	}

	public int getEventInNodeXPosition(Field field) {
		if (field == getExecutionField()) 
			return getExecutionNodeXPosition();
		return getEventInNodeXPosition(getEventInNodeNumber(field));
	}
	
	public int getEventInNodeYPosition(Field field) {
		if (field == getExecutionField()) 
			return getExecutionNodeYPosition();
		return getEventInNodeYPosition(getEventInNodeNumber(field));
	}
		
	public int getEventOutNodeXPosition(int n) {
		return (getXPosition() + getSize());
	}

	public int getEventOutNodeYPosition(int n) {
		return (getYPosition() + (getEventOutNodeSpacing() * (n+1)) + (getNodeSize() * n));
	}

	public int getEventOutNodeXPosition(Field field) {
		return getEventOutNodeXPosition(getEventOutNodeNumber(field));
	}

	public int getEventOutNodeYPosition(Field field) {
		return getEventOutNodeYPosition(getEventOutNodeNumber(field));
	}

	public int getEventInNodeNumber(Field field) {
		int nodeNumber = getScriptNode().getEventInNumber(field);
		return nodeNumber - getNSystemEventInNodes();
	}
	
	public int getEventOutNodeNumber(Field field) {
		return getScriptNode().getEventOutNumber(field);
	}
	

	////////////////////////////////////////////////
	//	Inside
	////////////////////////////////////////////////

	private boolean isRect(int x0, int y0, int x1, int y1, int x, int y) {
		if (x0 <= x && x <= x1 && y0 <= y && y <= y1)
			return true;
		else
			return false;
	}

	public int isInside(int x, int y) {
		int mx = getXPosition();
		int my = getYPosition();
		int mSize = getSize();
		if (isRect(mx, my, mx+mSize, my+mSize, x, y))
			return MODULE_INSIDE;

		int		n;
		int		nodeSize = getNodeSize();
		int		nodex, nodey;

		for (n=0; n<getNEventInNodes(); n++) {
			nodex = getEventInNodeXPosition(n);
			nodey = getEventInNodeYPosition(n);
			if (isRect(nodex, nodey, nodex+nodeSize, nodey+nodeSize, x, y) == true)
				return (MODULE_INSIDE_INNODE | n);
		}

		for (n=0; n<getNEventOutNodes(); n++) {
			nodex = getEventOutNodeXPosition(n);
			nodey = getEventOutNodeYPosition(n);
			if (isRect(nodex, nodey, nodex+nodeSize, nodey+nodeSize, x, y) == true)
				return (MODULE_INSIDE_OUTNODE | n);
		}

		if (hasExecutionNode()) {
			nodex = getExecutionNodeXPosition();
			nodey = getExecutionNodeYPosition();
			if (isRect(nodex, nodey, nodex+nodeSize, nodey+nodeSize, x, y) == true)
				return MODULE_INSIDE_EXECUTIONNODE;
		}

		int		expandSize = getNodeSize();

		// Add expandSize to only x position
		for (n=0; n<getNEventInNodes(); n++) {
			nodex = getEventInNodeXPosition(n);
			nodey = getEventInNodeYPosition(n);
			if (isRect(nodex-expandSize, nodey, nodex+nodeSize, nodey+nodeSize, x, y) == true)
				return (MODULE_INSIDE_INNODE | n);
		}

		for (n=0; n<getNEventOutNodes(); n++) {
			nodex = getEventOutNodeXPosition(n);
			nodey = getEventOutNodeYPosition(n);
			if (isRect(nodex, nodey, nodex+nodeSize+expandSize, nodey+nodeSize, x, y) == true)
				return (MODULE_INSIDE_OUTNODE | n);
		}

		// Add expandSize to x/y position
		for (n=0; n<getNEventInNodes(); n++) {
			nodex = getEventInNodeXPosition(n);
			nodey = getEventInNodeYPosition(n);
			if (isRect(nodex-expandSize, nodey-expandSize, nodex+nodeSize, nodey+nodeSize+expandSize, x, y) == true)
				return (MODULE_INSIDE_INNODE | n);
		}

		for (n=0; n<getNEventOutNodes(); n++) {
			nodex = getEventOutNodeXPosition(n);
			nodey = getEventOutNodeYPosition(n);
			if (isRect(nodex, nodey-expandSize, nodex+nodeSize+expandSize, nodey+nodeSize+expandSize, x, y) == true)
				return (MODULE_INSIDE_OUTNODE | n);
		}

		if (hasExecutionNode()) {
			nodex = getExecutionNodeXPosition();
			nodey = getExecutionNodeYPosition();
			if (isRect(nodex-expandSize, nodey-expandSize, nodex+nodeSize+expandSize, nodey+nodeSize, x, y) == true)
				return MODULE_INSIDE_EXECUTIONNODE;
		}

		return MODULE_OUTSIDE;	
	}

	////////////////////////////////////////////////
	//	ExecutionNode
	////////////////////////////////////////////////

	public boolean hasExecutionNode() {
		return (getExecutionField() != null ? true : false);
	}

	public Field getExecutionField() {
		try {
			return getScriptNode().getEventIn(MODULE_EXECUTION_EVENTINNAME);
		} catch (InvalidEventInException e) {
			return null;
		}
	}

	public int getExecutionNodeXPosition() {
		return (getXPosition() + getSize()/2 - getNodeSize()/2);
	}

	public int getExecutionNodeYPosition() {
		return (getYPosition() - getNodeSize());
	}

	////////////////////////////////////////////////
	//	SourceNode
	////////////////////////////////////////////////

	public SFNode createSourceNodeField() {
		SFNode field = new SFNode();
		getScriptNode().addField(MODULE_SOURCE_NODENAME, field);
		return field;
	}

	public SFNode getSourceNodeField() {
		try {
			return (SFNode)getScriptNode().getField(MODULE_SOURCE_NODENAME);
		} catch (InvalidFieldException e) {
			return null;
		}
	}

	public void setSourceNode(Node node) {
		if (node != null) {
			SFNode sfnode = getSourceNodeField();
			if (sfnode == null)
				sfnode = createSourceNodeField();
			sfnode.setValue(node);
		}
		else {
			SFNode sfnode = getSourceNodeField();
			if (sfnode != null)
				getScriptNode().removeField(sfnode);
		}
		initialize();
	}

	public Node	getSourceNode() {
		SFNode sfnode = getSourceNodeField();
		if (sfnode != null)
			return (Node)sfnode.getValue();
		return null;
	}

	////////////////////////////////////////////////
	//	TargetNode
	////////////////////////////////////////////////

	public SFNode createTargetNodeField() {
		SFNode field = new SFNode();
		getScriptNode().addField(MODULE_TARGET_NODENAME, field);
		return field;
	}

	public SFNode getTargetNodeField() {
		try {
			return (SFNode)getScriptNode().getField(MODULE_TARGET_NODENAME);
		} catch (InvalidFieldException e) {
			return null;
		}
	}

	public void setTargetNode(Node node) {
		if (node != null) {
			SFNode sfnode = getTargetNodeField();
			if (sfnode == null)
				sfnode = createTargetNodeField();
			sfnode.setValue(node);
		}
		else {
			SFNode sfnode = getTargetNodeField();
			if (sfnode != null)
				getScriptNode().removeField(sfnode);
		}

		initialize();
	}

	public Node getTargetNode() {
		SFNode sfnode = getTargetNodeField();
		if (sfnode != null)
			return (Node)sfnode.getValue();
		return null;
	}

	////////////////////////////////////////////////
	//	initialize
	////////////////////////////////////////////////

	public void initialize() {
		ScriptNode snode = getScriptNode();
		if (snode != null) {
			snode.uninitialize();
			snode.initialize();
		}
	}

	////////////////////////////////////////////////
	//	Route
	////////////////////////////////////////////////

	public int getNInputRoutes() {
		int nInputRoutes = 0;
		Node node = getScriptNode();
		for (int n=0; n<node.getNEventIn(); n++) {
			Field field = node.getEventIn(n);
			for (Route route=getWorld().getSceneGraph().getRoutes(); route != null; route=route.next()) {
				if (node == route.getEventInNode() && field == route.getEventInField())
					nInputRoutes++;
			}
		}
		for (int n=0; n<node.getNExposedFields(); n++) {
			Field field = node.getExposedField(n);
			for (Route route=getWorld().getSceneGraph().getRoutes(); route != null; route=route.next()) {
				if (node == route.getEventInNode() && field == route.getEventInField())
					nInputRoutes++;
			}
		}
		return nInputRoutes;
	}
	
	public int getNOutputRoutes() {
		int nOutputRoutes = 0;
		Node node = getScriptNode();
		for (int n=0; n<node.getNEventOut(); n++) {
			Field field = node.getEventOut(n);
			for (Route route=getWorld().getSceneGraph().getRoutes(); route != null; route=route.next()) {
				if (node == route.getEventOutNode() && field == route.getEventOutField())
					nOutputRoutes++;
			}
		}
		for (int n=0; n<node.getNExposedFields(); n++) {
			Field field = node.getExposedField(n);
			for (Route route=getWorld().getSceneGraph().getRoutes(); route != null; route=route.next()) {
				if (node == route.getEventOutNode() && field == route.getEventOutField())
					nOutputRoutes++;
			}
		}
		return nOutputRoutes;
	}
	
	public int getNRoutes() {
		int nRoutes = 0;
		Node node = getScriptNode();
		for (Route route=getWorld().getSceneGraph().getRoutes(); route != null; route=route.next()) {
			if (node == route.getEventOutNode() || node == route.getEventInNode())
				nRoutes++;
		}
		return nRoutes;
	}


	public void setOutputRoute(Field thisOutField, ScriptNode targetNode, Field targetField) {
		getWorld().getSceneGraph().removeRoutes(targetNode, targetField);
		getWorld().getSceneGraph().addRoute(getScriptNode(), thisOutField, targetNode, targetField);
	}
}
