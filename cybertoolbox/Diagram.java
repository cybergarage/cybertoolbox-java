/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	Diagram.java
*
******************************************************************/

import java.io.*;
import java.awt.Rectangle;

import vrml.*;
import vrml.node.*;
import vrml.util.*;
import vrml.route.*;

public class Diagram extends Object implements vrml.Constants, WorldConstants, EventTypeConstants, ModuleConstants {

	public static final int EXTENTS_MIN = 0;
	public static final int EXTENTS_MAX = 1;

	private TransformNode	mTransform = null;
	private int				mExtents[][] = new int[2][2];
	private World	mWorld = null;
	
	public void setWorld(World world) {
		mWorld = world;
	}
	
	public World getWorld() {
		return mWorld;
	}

	//////////////////////////////////////////////////
	// Constructor		
	//////////////////////////////////////////////////
	
	public Diagram(World world, String name, Event event) {
		setWorld(world);
		TransformNode transform = new TransformNode();
		setTransformNode(transform);
		setTransformNodeName(name, event);
		//addBasisModule();
		setPosition(0, 0);
		setWidth(320);
		setHeight(240);
		updateExtents();
	}

	public Diagram(World world, TransformNode transform) {
		setWorld(world);
		setTransformNode(transform);
		updateExtents();
	}
	//////////////////////////////////////////////////
	// Node name		
	//////////////////////////////////////////////////

	public void setTransformNode(TransformNode transform) {
		mTransform = transform;
	}

	public TransformNode getTransformNode() {
		return mTransform;
	}

	public void setTransformNodeName(String name, Event event) {
		String transformName = WORLD_DIAGRAM_NODENAME + "_" + event.getName()  + "_" +  name;
		getTransformNode().setName(transformName);
	}
	
	public String getTransformNodeName() {
		return getTransformNode().getName();
	}

	//////////////////////////////////////////////////
	// Name
	//////////////////////////////////////////////////

	public void setName(String name) {
		Event event = new Event(getWorld(), getEventNode());
		setTransformNodeName(name, event);

		int	nScriptNode = 0;
		for (ScriptNode script = getTransformNode().getScriptNodes(); script != null; script = (ScriptNode)script.nextSameType()) {
			String moduleName = getTransformNode().getName() + "_MODULE" + nScriptNode;
			script.setName(moduleName);
			nScriptNode++;
		}
	}

	public String getName() {
		String transformName = getTransformNodeName();
		int index = transformName.lastIndexOf('_');
		String name = new String(transformName.toCharArray(), (index+1), transformName.length() - (index+1));
		return name;
	}

	//////////////////////////////////////////////////
	// EventType
	//////////////////////////////////////////////////

	public void setEventType(Event event) {
		setTransformNodeName(getName(), event);
	}

	public Node getEventNode() {
		String nodeName = getTransformNodeName();

		/* Event type name string */
		int	index1 = nodeName.indexOf('_', WORLD_DIAGRAM_NODENAME.length() + 1/*'_'*/);
		int	index2 = nodeName.indexOf('_', index1 + 1);
		String eventTypeName = new String(nodeName.toCharArray(), index1 +1, (index2-1) - index1);

		/* Event option string */
		int	index3 = nodeName.indexOf('_', index2 + 1);
		String eventOptionName = new String(nodeName.toCharArray(), index2 +1, (index3-1) - index2);

		Node eventNode = getWorld().getEventNode(eventTypeName, eventOptionName);
		return eventNode;
	}

	//////////////////////////////////////////////////
	// Position
	//////////////////////////////////////////////////

	public void setPosition(int x, int y) {
		getTransformNode().setTranslation((float)x, (float)y, 0.0f);
	}

	public void setXPosition(int x) {
		getTransformNode().setTranslation((float)x, (float)getYPosition(), 0.0f);
	}

	public void setYPosition(int y) {
		getTransformNode().setTranslation((float)getXPosition(), (float)y, 0.0f);
	}

	public int getXPosition() {
		float	translation[] = new float[3];
		getTransformNode().getTranslation(translation);
		return (int)translation[X];
	}

	public int getYPosition() {
		float	translation[] = new float[3];
		getTransformNode().getTranslation(translation);
		return (int)translation[Y];
	}

	//////////////////////////////////////////////////
	// Width/Height
	//////////////////////////////////////////////////

	public void setWidth(int width) {
		getTransformNode().setScale((float)width, (float)getHeight(), 0.0f);
	}

	public void setHeight(int height) {
		getTransformNode().setScale((float)getWidth(), (float)height, 0.0f);
	}

	public int getWidth() {
		float	scale[] = new float[3];
		getTransformNode().getScale(scale);
		return (int)scale[X];
	}

	public int getHeight() {
		float	scale[] = new float[3];
		getTransformNode().getScale(scale);
		return (int)scale[Y];
	}

	//////////////////////////////////////////////////
	// Module
	//////////////////////////////////////////////////

	public boolean isModule(ScriptNode script) {
		TransformNode transform = getTransformNode();
		String scriptHeadString = new String(script.getName().toCharArray(), 0, transform.getName().length());
		if (scriptHeadString.equals(transform.getName()) == true)
			return true;
		else
			return false;
	}

	public void setModuleDefaultRoute(Module module) {
		if (module.hasEventInNode() == false) {
			Event			event 			= new Event(getWorld(), getEventNode());
			EventType	eventType		= event.getEventType();
			Node			eventOutNode	= event.getSourceNode();
			Field			eventOutField	= event.getSourceFiled();
			ScriptNode	moduleScript	= module.getScriptNode();
			Field			eventInField	= moduleScript.getEventIn(eventType.getModuleFieldName());
			getWorld().getSceneGraph().addRoute(eventOutNode, eventOutField, moduleScript, eventInField);
		}
	}
	
	public void	addModule(Module module) {
		TransformNode	transform = getTransformNode();
		ScriptNode		moduleScript = module.getScriptNode();

		// Set the module's name
		int moduleNumber = 0;
		for (ScriptNode script = transform.getScriptNodes(); script != null; script = (ScriptNode)script.nextSameType()) {
			if (isModule(script)) {
				Module mod = new Module(getWorld(), script);
				int nodeNumber = mod.getNumber();
				if (moduleNumber < nodeNumber)
					moduleNumber = nodeNumber;
			}
		}
		String moduleName = transform.getName() + "_MODULE" + (moduleNumber+1);
		moduleScript.setName(moduleName);

		// Add the module into the diagram's list
		transform.addChildNode(moduleScript);

		// Add Default Route
		setModuleDefaultRoute(module);

		// Update Extents
		updateExtents();
	}
	
	public void removeModule(Module module) {
		ScriptNode moduleScript = module.getScriptNode();
		getWorld().removeNode(moduleScript);
		Debug.message("Diagram::removeModule = " + module);
	}

	public int getNModules() {
		int nScriptNode = 0;
		TransformNode transform = getTransformNode();
		for (ScriptNode script = transform.getScriptNodes(); script != null; script=(ScriptNode)script.nextSameType()) {
			if (isModule(script) == true)
				nScriptNode++;
		}
		return nScriptNode;
	}
	
	public int getNModules(Rectangle rect) {
		int nScriptNode = 0;
		TransformNode transform = getTransformNode();
		for (ScriptNode script = transform.getScriptNodes(); script != null; script=(ScriptNode)script.nextSameType()) {
			if (isModule(script) == true) {
				Module module = new Module(getWorld(), script);
				int mx = module.getXPosition();
				int my = module.getYPosition();
				int msize = module.getSize();
				if (rect.contains(mx, my) == true && rect.contains(mx+msize, my+msize) == true) 
					nScriptNode++;
			}
		}
		return nScriptNode;
	}

	public ScriptNode getModules() {
		return getTransformNode().getScriptNodes();
	}
	
	public ScriptNode getModule(int n) {
		int nScriptNode = 0;
		TransformNode transform = getTransformNode();
		for (ScriptNode script = transform.getScriptNodes(); script != null; script=(ScriptNode)script.nextSameType()) {
			if (isModule(script) == true) {
				if (nScriptNode == n)
					return script;
				nScriptNode++;
			}
		}
		return null;
	}
	
	public ModuleSelectedData getModule(int x, int y) {
		int selectedPart = MODULE_OUTSIDE;
		ScriptNode selectedNode = null;
		
		for (ScriptNode script = getTransformNode().getScriptNodes(); script != null; script=(ScriptNode)script.nextSameType()) {
			if (isModule(script) == true) {
				Module module = new Module(getWorld(), script);
				int selectingPart = module.isInside(x, y);
				if (selectingPart != MODULE_OUTSIDE) {
					selectedPart = selectingPart;
					selectedNode = script;
					break;
				}
			}
		}

		return  new ModuleSelectedData(selectedNode, selectedPart); 
	}

	public boolean hasModule(Node node) {
		if (node == null)
			return false;
		if (node.isScriptNode() == false)
			return false;
		for (ScriptNode script = getTransformNode().getScriptNodes(); script != null; script=(ScriptNode)script.nextSameType()) {
			if (script == node)
				return true;
		}
		return false;
	}

	public boolean hasRoute(Route route) {
		if (hasModule(route.getEventInNode()) && hasModule(route.getEventOutNode())) 
			return true;
		return false;
	}

	public void addBasisModule() {
		Event		event		= new Event(getWorld(), getEventNode());
		EventType	eventType	= event.getEventType();
		ModuleType	moduleType	= getWorld().getModuleType(EVENTTYPE_CLASSNAME_SYSTEM_STRING, eventType.getName());

		if (moduleType == null)
			return;
	
		Module module = new Module(getWorld(), moduleType);
		module.setPosition(0, 0);
		addModule(module);
	}

	//////////////////////////////////////////////////
	// Extents
	//////////////////////////////////////////////////

	public void getExtents(int extents[][]) {
		extents[EXTENTS_MIN][X] = mExtents[EXTENTS_MIN][X];
		extents[EXTENTS_MIN][Y] = mExtents[EXTENTS_MIN][Y];
		extents[EXTENTS_MAX][X] = mExtents[EXTENTS_MAX][X];
		extents[EXTENTS_MAX][Y] = mExtents[EXTENTS_MAX][Y];
	}

	public void setExtents(int extents[][]) {
		mExtents[EXTENTS_MIN][X] = extents[EXTENTS_MIN][X];
		mExtents[EXTENTS_MIN][Y] = extents[EXTENTS_MIN][Y];
		mExtents[EXTENTS_MAX][X] = extents[EXTENTS_MAX][X];
		mExtents[EXTENTS_MAX][Y] = extents[EXTENTS_MAX][Y];
	}

	public void updateExtents() {
		int extents[][] = new int[2][2];

		if (getNModules() == 0) {
			extents[EXTENTS_MIN][X] = 0;
			extents[EXTENTS_MIN][Y] = 0;
			extents[EXTENTS_MAX][X] = 0;
			extents[EXTENTS_MAX][Y] = 0;
		}
		else {
			Module firstModule = new Module(getWorld(), getModule(0));
			int x = firstModule.getXPosition();
			int y = firstModule.getYPosition();
			int size = firstModule.getSize();
			extents[EXTENTS_MIN][X] = x;
			extents[EXTENTS_MIN][Y] = y;
			extents[EXTENTS_MAX][X] = x + size;
			extents[EXTENTS_MAX][Y] = y + size;
			for (int n=1; n<getNModules(); n++) {
				Module module = new Module(getWorld(), getModule(n));
				x = module.getXPosition();
				y = module.getYPosition();
				if (extents[EXTENTS_MIN][X] > x)
					extents[EXTENTS_MIN][X] = x;
				if (extents[EXTENTS_MIN][Y] > y)
					extents[EXTENTS_MIN][Y] = y;
				if (extents[EXTENTS_MAX][X] < (x + size))
					extents[EXTENTS_MAX][X] = x + size;
				if (extents[EXTENTS_MAX][Y] < (y + size))
					extents[EXTENTS_MAX][Y] = y + size;
			}
		}

		extents[EXTENTS_MAX][X] += MODULE_SIZE;
		extents[EXTENTS_MAX][Y] += MODULE_SIZE;

		setExtents(extents);
	}
}
