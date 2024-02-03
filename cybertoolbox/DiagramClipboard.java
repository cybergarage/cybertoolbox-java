/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File: DiagramClipboard.java
*
******************************************************************/

import vrml.*;
import vrml.node.*;
import vrml.util.*;
import vrml.route.*;

public class DiagramClipboard extends Object {

	private LinkedList	mModuleNodeList	= new LinkedList();
	private LinkedList	mRouteList			= new LinkedList();

	///////////////////////////////////////////////
	// Constructor
	///////////////////////////////////////////////
	
	public DiagramClipboard() {
	}

	public DiagramClipboard(World world, DiagramClipboard clipboard) {
		for (ScriptNode node=clipboard.getModuleNodes(); node != null; node=(ScriptNode)node.next()) 
			addModuleNode(world, node);

		for (Route route=clipboard.getRoutes(); route != null; route=route.next()) 
			addRoute(route);
	}

	///////////////////////////////////////////////
	//	ModuleNode
	///////////////////////////////////////////////

	public void addModuleNode(World world, ScriptNode node) {
		Module	orgModule = new Module(world, node);
		Module copyModule = new Module(world, orgModule.getModuleType());
		copyModule.setName(orgModule.getName());
		copyModule.setXPosition(orgModule.getXPosition());
		copyModule.setYPosition(orgModule.getYPosition());
		mModuleNodeList.addNode(copyModule.getScriptNode());		
	}
	
	public ScriptNode getModuleNodes() {
		return (ScriptNode)mModuleNodeList.getNodes();		
	}

	public ScriptNode getModuleNode(int n) {
		return (ScriptNode)mModuleNodeList.getNode(n);		
	}

	public int getNModuleNodes() {
		return mModuleNodeList.getNNodes();
	}

	public void clearModuleNodeList() {
		mModuleNodeList.deleteNodes();		
	}

	///////////////////////////////////////////////
	//	Route
	///////////////////////////////////////////////

	public void addRoute(Route route) {
		int nModule = getNModuleNodes();

		ScriptNode	outModule = (ScriptNode)route.getEventOutNode();
		Field			outField = null;
		
		for (int n=0; n<nModule; n++) {
			ScriptNode moduleNode = getModuleNode(n);
			String moduleNodeName = moduleNode.getName();
			if (moduleNodeName != null) {
				if (moduleNodeName.equals(outModule.getName()) == true) {
					outModule = moduleNode;
					outField = outModule.getEventOut(route.getEventOutField().getName());
					break;
				}
			}
		}

		ScriptNode	inModule = (ScriptNode)route.getEventInNode();
		Field			inField = null;
		for (int n=0; n<nModule; n++) {
			ScriptNode moduleNode = getModuleNode(n);
			String moduleNodeName = moduleNode.getName();
			if (moduleNodeName != null) {
				if (moduleNodeName.equals(inModule.getName()) == true) {
					inModule = moduleNode;
					inField = inModule.getEventIn(route.getEventInField().getName());
					break;
				}
			}
		}
	
		Route copyRoute = new Route(outModule, outField, inModule, inField);

		mRouteList.addNode(copyRoute);		
	}

	public Route getRoutes() {
		return (Route)mRouteList.getNodes();		
	}

	public Route getRoute(int n) {
		return (Route)mRouteList.getNode(n);		
	}

	public int getNRoutes() {
		return mRouteList.getNNodes();
	}

	public void clearRouteList() {
		mRouteList.deleteNodes();		
	}

	///////////////////////////////////////////////
	//	clear
	///////////////////////////////////////////////

	public void clear() {
		clearModuleNodeList();
		clearRouteList();
	}
}
