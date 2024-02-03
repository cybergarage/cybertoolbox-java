/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	World.java
*
******************************************************************/

import java.io.*;
import java.awt.Point;
import java.util.Properties;

import javax.swing.*;

import vrml.*;
import vrml.node.*;
import vrml.field.*;
import vrml.route.*;
import vrml.util.*;

public class World extends Object implements WorldConstants, ModuleTypeConstants, EventTypeConstants, EventConstants {

	private SceneGraph				mSceneGraph;
	private WorldTree					mWorldTree;
	private PerspectiveViewJava3D	mPerspectiveViewJava3D;
	private OrthoViewJava3D			mOrthoViewJava3D;
	private ModuleFrame				mModuleFrame;
	
	public World() {
		Properties prop = System.getProperties();
		Debug.message("User working directory = " + prop.getProperty("user.dir"));
		mSceneGraph				= new SceneGraph();

		loadEventTypes();
		loadModuleTypes();
		clearSceneGraph();

		mWorldTree				= new WorldTree(this);
		mWorldTree.setLocation(0, 100);
		
		mPerspectiveViewJava3D	= new PerspectiveViewJava3D(this);
		mPerspectiveViewJava3D.setLocation(355, 100);

		//mOrthoViewJava3D		= new OrthoViewJava3D(this);
		
		mModuleFrame			= new ModuleFrame(this);
		mModuleFrame.setLocation(0, 0);
		
		getSceneGraph().setHeadlightState(true);
		getSceneGraph().start();
	}

	public SceneGraph getSceneGraph() {
		return mSceneGraph;
	}

	public WorldTree getWorldTree() {
		return mWorldTree;
	}

	public PerspectiveViewJava3D getPerspectiveViewJava3D() {
		return mPerspectiveViewJava3D;
	}
		
	public ModuleFrame getModuleFrame() {
		return mModuleFrame;
	}
	
	//////////////////////////////////////////////////
	// Rendering mode
	//////////////////////////////////////////////////
	
	private int mRenderingMode = 0;

	public void setRenderingMode(int mode) {
		mRenderingMode = mode;
	}

	public int getRenderingMode() {
		return mRenderingMode;
	}

	//////////////////////////////////////////////////
	// Operation mode
	//////////////////////////////////////////////////
	
	private int mOperationMode = WORLD_OPETATION_MODE_NONE;

	public void setOperationMode(int mode) {
		mOperationMode = mode;
	}

	public int getOperationMode() {
		return mOperationMode;
	}

	//////////////////////////////////////////////////
	// Simulation
	//////////////////////////////////////////////////

	private void initialize() {
		getSceneGraph().initialize();
		initializeSimulation();
	}

	private void initializeSimulation() {
		TimeSensorNode startEvent = (TimeSensorNode)getEventNode(EVENTTYPE_START);
		if (startEvent == null)
			return;
		startEvent.setEnabled(true);
	}

	public void startPerspectiveViewThread() {
		PerspectiveViewJava3D pv = getPerspectiveViewJava3D();
		if (pv != null) 
			pv.startThread();
	}

	public void stopPerspectiveViewThread() {
		PerspectiveViewJava3D pv = getPerspectiveViewJava3D();
		if (pv != null) 
			pv.stopThread();
	}

	public void startSimulation() {
		SceneGraph sg = getSceneGraph();
		if (sg.isSimulationRunning() == false) {
			initialize();
			sg.startSimulation();
			for (DiagramFrame dgmFrame = getDiagramFrames(); dgmFrame != null; dgmFrame = dgmFrame.next())
				dgmFrame.start();
		}
	}

	public void stopSimulation() {
		SceneGraph sg = getSceneGraph();
		if (sg.isSimulationRunning() == true) {
			sg.stopSimulation();
			for (DiagramFrame dgmFrame = getDiagramFrames(); dgmFrame != null; dgmFrame = dgmFrame.next())
				dgmFrame.stop();
		}
	}

	public boolean isSimulationRunning() {
		SceneGraph sg = getSceneGraph();
		return sg.isSimulationRunning();
	}
				
	//////////////////////////////////////////////////
	// SceneGraph
	//////////////////////////////////////////////////

	public void clearSceneGraph() {
		deleteDiagramFrames();
		getSceneGraph().clear();
		createSystemEvents();
	}

	public boolean loadSceneGraph(String filename) {
		SceneGraph sceneGraph = getSceneGraph();
		sceneGraph.load(filename);
		return sceneGraph.isLoadingOK();
	}

	public void addRootNode(Node rootNode) {
		Node node = rootNode.getChildNodes();
		while (node != null) {
			Node nextNode = node.next();

			// Global Data Node
			if (isGlobalDataRootNode(node) == true) {
				AnchorNode gdataNode = node.getAnchorNodes();
				while (gdataNode != null) {
					AnchorNode nextGdataNode = (AnchorNode)gdataNode.next();
					addGlobalData(gdataNode);
					gdataNode = nextGdataNode;
				}
			}
		
			// Event Node
			if (isEventRootNode(node) == true) {
				Node eventNode = node.getChildNodes();
				while (eventNode != null) {
					Node nextEventNode = eventNode.next();
					addEventNode(eventNode);
					eventNode = nextEventNode;
				}
			}
		
			// Diagram Node
			if (isDiagramRootNode(node)) {
				Node diagramNode = node.getChildNodes();
				while (diagramNode != null) {
					Node nextdiagramNode = diagramNode.next();
					addDiagramNode((TransformNode)diagramNode);
					diagramNode = nextdiagramNode;
				}
			}

			node = nextNode;
		}
	}

	public boolean addSceneGraph(SceneGraph sceneGraph) {
	
		if (sceneGraph == null)
			return false;
			
		if (sceneGraph.isLoadingOK() == false) {
			Debug.warning("Loading Error");
			return false;
		}
		
		Node node = sceneGraph.getNodes();
		while (node != null) {
			Node nextNode = node.next();
			if (isRootNode(node) == true)
				addRootNode(node);
			else {
				getSceneGraph().moveNode(node);
			}
			node = nextNode;
		}

		// Route Infomation
		Route route = sceneGraph.getRoutes();
		while (route != null) {
			Route nextRoute = route.next();
			route.remove();
			Node eventInNode = route.getEventInNode();
			Node eventOutNode = route.getEventOutNode();
			Field eventInField = route.getEventInField();
			Field eventOutField = route.getEventOutField();
			if (eventInNode != null && eventOutNode != null && eventInField != null && eventOutField != null)
				getSceneGraph().addRoute(eventOutNode.getName(), eventOutField.getName(), eventInNode.getName(), eventInField.getName());
			route = nextRoute;
		}

		getSceneGraph().setDirectory(sceneGraph.getDirectory());
		getSceneGraph().setURL(sceneGraph.getURL());

		getSceneGraph().initialize();

		getSceneGraph().saveViewpointStartPositionAndOrientation();

		return true;
	}

	public boolean addSceneGraph(String filename) {
		SceneGraph sceneGraph = new SceneGraph();
		sceneGraph.setOption(getSceneGraph().getOption());
		sceneGraph.load(filename);
		return addSceneGraph(sceneGraph);
	}

	public boolean addSceneGraph(File file) {
		SceneGraph sceneGraph = new SceneGraph();
		sceneGraph.setOption(getSceneGraph().getOption());
		sceneGraph.load(file);
		return addSceneGraph(sceneGraph);
	}

	public void deleteExtraSceneGraphData() {
		SceneGraph sceneGraph = getSceneGraph();
		// Delete invald field of Module
		for (TransformNode diagramNode=getDiagramNodes(); diagramNode != null; diagramNode=nextDiagramNode(diagramNode)) {
			Diagram diagram = new Diagram(this, diagramNode);
			for (ScriptNode moduleNode = diagram.getModules(); moduleNode != null; moduleNode=(ScriptNode)moduleNode.next()) {
				Module module = new Module(this, moduleNode);
				if (module.getSourceNode() == null && module.getSourceNodeField() != null) {
					SFNode field = module.getSourceNodeField();
					moduleNode.removeField(field);
				}
				if (module.getTargetNode() == null && module.getTargetNodeField() != null) {
					SFNode field = module.getTargetNodeField();
					moduleNode.removeField(field);
				}
			}
		}
		
	}

	public void moveWorldNodes() {
		SceneGraph sceneGraph = getSceneGraph();
		// Move glabal date node at first
		GroupNode rootNode = getRootNode();
		GroupNode globalDataNode = getGlobalDataRootNode();
		rootNode.moveChildNodeAtFirst(globalDataNode);
		// Move root node at last for USE
		sceneGraph.moveNode(rootNode);
	}

	public void copyScriptFile(File classFile, File saveFile) {
		try {
			FileInputStream inStream = new FileInputStream(classFile);
			FileOutputStream outStream = new FileOutputStream(saveFile);
			
			try {
				byte buffer[] = new byte[512];
				int nReadByte = inStream.read(buffer);
				while (0 < nReadByte) {
					outStream.write(buffer, 0, nReadByte);		
					nReadByte = inStream.read(buffer);
				}
			}
			catch (IOException ioe) {}
			
			inStream.close();
			outStream.close();
		}
		catch (FileNotFoundException fnfe) {}
		catch (IOException ioe) {}
	}
	
	public void saveScriptFiles(String path) {
		Debug.message("World.saveScriptFiles");
		String systemScriptPath = WORLD_MODULETYPE_SCRIPT_DIRECTORY;
		for (ScriptNode scriptNode=getSceneGraph().findScriptNode(); scriptNode != null; scriptNode=(ScriptNode)scriptNode.nextTraversalSameType()) {
			if (isSystemNode(scriptNode) == true) {
				if (scriptNode.getNUrls() < 1)
					continue;
				String classFileName = scriptNode.getUrl(0);
				File classFile = new File(systemScriptPath, classFileName);
				if (classFile.exists() == false)
					continue;
				File saveFile = new File(path, classFileName);
				Debug.message("\tCopy a file from " + classFile + " to " + saveFile);
				copyScriptFile(classFile, saveFile);
			}
		}
	}

	public boolean saveSceneGraph(File file) {
		SceneGraph sceneGraph = getSceneGraph();
		initialize();
		deleteExtraSceneGraphData();
		moveWorldNodes();
		if (sceneGraph.save(file) == true)
			saveScriptFiles(file.getParent());
		return true;
	}

	public boolean saveSceneGraph(String filename) {
		return saveSceneGraph(new File(filename));
	}

	//////////////////////////////////////////////////
	// Default Node
	//////////////////////////////////////////////////

	public boolean hasDefaultViewpointNode() {
		return (getDefaultViewpointNode() != null) ? true : false;
	}

	public boolean hasDefaultNavigationInfoNode() {
		return (getDefaultNavigationInfoNode() != null) ? true : false;
	}

	public boolean hasDefaultBackgroundNode() {
		return (getDefaultBackgroundNode() != null) ? true : false;
	}

	public boolean hasDefaultFogNode() {
		return (getDefaultFogNode() != null) ? true : false;
	}

	public ViewpointNode getDefaultViewpointNode() {
		return getSceneGraph().getViewpointNode();
	}

	public NavigationInfoNode getDefaultNavigationInfoNode() {
		return getSceneGraph().getNavigationInfoNode();
	}

	public BackgroundNode getDefaultBackgroundNode() {
		return getSceneGraph().getBackgroundNode();
	}

	public FogNode getDefaultFogNode() {
		return getSceneGraph().getFogNode();
	}

    public static void main(String args[]) {
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception exc) {
			System.err.println("Error loading L&F: " + exc);
		}
		World world = new World();
    }

	//////////////////////////////////////////////////
	// Root
	//////////////////////////////////////////////////

	public boolean isRootNode(Node node) {
		if (WORLD_ROOT_NODE_NAME.equals(node.getName()) == true)
			return true;
		else
			return false;
	}

	public GroupNode getRootNode() {
		SceneGraph sceneGraph = getSceneGraph();
		for (GroupNode group=sceneGraph.getGroupNodes(); group != null; group=(GroupNode)group.nextSameType()) {
			if (isRootNode(group) == true)
				return group;
		}
		GroupNode rootNode = createRootNode();
		return rootNode;
	}

	private GroupNode createRootNode() {
		GroupNode rootNode = new GroupNode();
		rootNode.setName(WORLD_ROOT_NODE_NAME);
		getSceneGraph().addNode(rootNode);
		return rootNode;
	}

	//////////////////////////////////////////////////
	// Event RootNode 
	//////////////////////////////////////////////////

	public boolean isEventRootNode(Node node) {
		if (WORLD_EVENT_ROOT_NODE_NAME.equals(node.getName()) == true)
			return true;
		else
			return false;
	}

	public GroupNode getEventRootNode() {
		GroupNode eventRootNode = null;
		for (GroupNode group=getRootNode().getGroupNodes(); group != null; group=(GroupNode)group.nextSameType()) {
			if (isEventRootNode(group) == true) {
				eventRootNode = group;
				break;
			}
		}
		if (eventRootNode == null)
			eventRootNode = createEventRootNode();
		return eventRootNode;
	}

	private GroupNode createEventRootNode() {
		GroupNode eventRootNode = new GroupNode();
		eventRootNode.setName(WORLD_EVENT_ROOT_NODE_NAME);
		getRootNode().addChildNode(eventRootNode);
		return eventRootNode;
	}

	//////////////////////////////////////////////////
	// Sub System events
	//////////////////////////////////////////////////

	public boolean isSubEventRootNode(Node node) {
		if (WORLD_SUBEVENT_ROOT_NODE_NAME.equals(node.getName()) == true)
			return true;
		else
			return false;
	}

	public void addSubSystemEvent(Node eventNode) {
		GroupNode subSystemEvents = getSubSystemEventsNode();
		if (subSystemEvents == null)
			subSystemEvents = createSubSystemEventsNode();
		subSystemEvents.addChildNode(eventNode);
	}

	private GroupNode createSubSystemEventsNode() {
		GroupNode subSystemEventGroup = new GroupNode();
		subSystemEventGroup.setName(WORLD_SUBEVENT_ROOT_NODE_NAME);
		getRootNode().addChildNode(subSystemEventGroup);
		return subSystemEventGroup;
	}

	public boolean hasSubSystemEventsNode() {
		return (getSubSystemEventsNode() != null ? true : false);
	}

	public GroupNode getSubSystemEventsNode() {
		for (GroupNode group=getRootNode().getGroupNodes(); group != null; group=(GroupNode)group.nextSameType()) {
			if (WORLD_SUBEVENT_ROOT_NODE_NAME.equals(group.getName()) == true)
				return group;
		}
		return null;
	}

	public int getNSubSystemEvents() {
		return getSubSystemEventsNode().getNChildNodes();
	}

	//////////////////////////////////////////////////
	// GlobalData
	//////////////////////////////////////////////////

	private AnchorNode addGlobalData(String name) {
		WorldGlobalData gdata = new WorldGlobalData(name);
		AnchorNode node = gdata.getNode();
		getGlobalDataRootNode().addChildNode(node);
		return node;
	}

	private void addGlobalData(AnchorNode node) {
		getGlobalDataRootNode().addChildNode(node);
	}

	public int getNGlobalDataNodes() {
		int	nGlobalDataNodes = 0;
		for (AnchorNode node=getGlobalDataNodes(); node != null; node=(AnchorNode)node.nextSameType()) 
			nGlobalDataNodes++;
		return nGlobalDataNodes;
	}

	public AnchorNode getGlobalDataNodes() {
		for (AnchorNode node=getGlobalDataRootNode().getAnchorNodes(); node != null; node=(AnchorNode)node.nextSameType()) {
			String nodeHeadString = new String(node.getName().toCharArray(), 0, WORLD_GLOBALDATA_NODENAME.length());
			if (nodeHeadString.equals(WORLD_GLOBALDATA_NODENAME) == true)
				return node;
		}
		return null;
	}

	public AnchorNode getGlobalDataNode(String name) {
		for (AnchorNode node=getGlobalDataNodes(); node != null; node=(AnchorNode)node.nextSameType()) {
			WorldGlobalData gdata = new WorldGlobalData(node);
			if (name.equals(gdata.getName()) == true)
				return node;
		}
		return null;
	}


	boolean isGlobalData(AnchorNode targetNode) {
		for (AnchorNode node=getGlobalDataNodes(); node != null; node=(AnchorNode)node.nextSameType()) {
			if (node == targetNode)
				return true;
		}
		return false;
	}
	
	//////////////////////////////////////////////////
	// Diagram Root
	//////////////////////////////////////////////////

	public boolean isDiagramRootNode(Node node) {
		if (WORLD_DIAGRAM_ROOT_NODE_NAME.equals(node.getName()) == true)
			return true;
		else
			return false;
	}

	public GroupNode getDiagramRootNode() {
		for (GroupNode group=getRootNode().getGroupNodes(); group != null; group=(GroupNode)group.nextSameType()) {
			if (isDiagramRootNode(group) == true)
				return group;
		}
		GroupNode dgmRootNode = createDiagramRootNode();
		return dgmRootNode;
	}

	private GroupNode createDiagramRootNode() {
		GroupNode dgmRootNode = new GroupNode();
		dgmRootNode.setName(WORLD_DIAGRAM_ROOT_NODE_NAME);
		getRootNode().addChildNode(dgmRootNode);
		return dgmRootNode;
	}

	//////////////////////////////////////////////////
	// Global Data Root
	//////////////////////////////////////////////////

	public boolean isGlobalDataRootNode(Node node) {
		if (WORLD_GLOBALDATA_ROOT_NODE_NAME.equals(node.getName()) == true)
			return true;
		else
			return false;
	}

	public GroupNode getGlobalDataRootNode() {
		for (GroupNode group=getRootNode().getGroupNodes(); group != null; group=(GroupNode)group.nextSameType()) {
			if (WORLD_GLOBALDATA_ROOT_NODE_NAME.equals(group.getName()) == true)
				return group;
		}

		GroupNode gdataRootNode = createGlobalDataRootNode();
		return gdataRootNode;
	}

	private GroupNode createGlobalDataRootNode() {
		GroupNode gdataRootNode = new GroupNode();
		gdataRootNode.setName(WORLD_GLOBALDATA_ROOT_NODE_NAME);
		getRootNode().addChildNode(gdataRootNode);
		return gdataRootNode;
	}

	//////////////////////////////////////////////////
	//	Node Check
	//////////////////////////////////////////////////

	public boolean isSystemNode(Node node) {
		if (node == null)
			return false;
		try {
			String nodeHeaderName = new String(node.getName().toCharArray(), 0, WORLD_NODE_NAME.length());
			if (nodeHeaderName.equals(WORLD_NODE_NAME) == true)
				return true;
		} catch (Exception e) {}
		return false;
	}

	public boolean isSystemRoute(Route route) {
		if (route == null)
			return false;
		try {
			Node eventOutNode = route.getEventOutNode();
			if (isSystemNode(eventOutNode) == true)
				return true;
			Node eventInNode = route.getEventInNode();
			if (isSystemNode(eventInNode) == true)
				return true;
		} catch (Exception e) {}
		return false;
	}

	//////////////////////////////////////////////////
	//	ModuleType
	//////////////////////////////////////////////////

	private LinkedList mModuleTypeList = new LinkedList();

	public ModuleType loadModuleType(String filename) {
		ModuleType moduleType = new ModuleType();
		if (moduleType.load(filename)) 
			addModuleType(moduleType);
		return moduleType;
	}
	
	public int loadModuleTypes() {
		int	 nModule = 0;

		ProgressDialog progressDialog = new ProgressDialog("Loading Module Types", 0, 0, 300, 100);
		
		String dirName = WORLD_MODULETYPE_DIRECTORY;
		String fileSep = System.getProperty("file.separator");
		
		progressDialog.setMinimum(0);
		progressDialog.setMaximum(MODULETYPE_FILENAME.length);
		progressDialog.setValue(0);
		
		for (int n=0; n<MODULETYPE_FILENAME.length; n++) {
			progressDialog.setValue(n);
			progressDialog.setText("Loading " + MODULETYPE_FILENAME[n]);
			Debug.message("====== ModuleType" + n + "(" + MODULETYPE_FILENAME[n] + ")" + " ======");
			String filename = dirName + fileSep + MODULETYPE_FILENAME[n];
			ModuleType moduleType = loadModuleType(filename);
			if (moduleType != null)
				nModule++;
		}

		progressDialog.dispose();
		
		return nModule;
	}
	
	public int loadModuleTypesFromDirectory() {
		int	 nModule = 0;

		ProgressDialog progressDialog = new ProgressDialog("Loading Module Types", 0, 0, 300, 100);
		
		String fileSep = System.getProperty("file.separator");
		
		String dirName = WORLD_MODULETYPE_DIRECTORY;
		
		File moduleDir = new File(WORLD_MODULETYPE_DIRECTORY);
		String moduleTypeFileName[] = moduleDir.list();

		progressDialog.setMinimum(0);
		progressDialog.setMaximum(moduleTypeFileName.length);
		progressDialog.setValue(0);
		
		for (int n=0; n<moduleTypeFileName.length; n++) {
			progressDialog.setValue(n);
			progressDialog.setText("Loading " + moduleTypeFileName[n]);
			Debug.message("====== ModuleType" + n + "(" + moduleTypeFileName[n] + ")" + " ======");
			String filename = dirName + fileSep + moduleTypeFileName[n];
			ModuleType moduleType = loadModuleType(filename);
			if (moduleType != null)
				nModule++;
		}

		progressDialog.dispose();
		
		return nModule;
	}

	public ModuleType getModuleTypes() {
		return (ModuleType)mModuleTypeList.getNodes();
	}

	public ModuleType getModuleTypes(String className) {
		for (ModuleType cmType=getModuleTypes(); cmType != null; cmType=cmType.next()) {
			if (className.equalsIgnoreCase(cmType.getClassName()) == true) 
				return cmType;
		}
		return null;
	}

	public ModuleType getModuleType(int n) {
		int nModule = 0;
		for (ModuleType cmType=getModuleTypes(); cmType != null; cmType=cmType.next()) {
			if (nModule == n)
				return cmType;
			nModule++;
		}
		return null;
	}
	
	public ModuleType getModuleType(String className, int n) {
		int nModule = 0;
		for (ModuleType cmType=getModuleTypes(className); cmType != null; cmType=cmType.next(className)) {
			if (nModule == n)
				return cmType;
			nModule++;
		}
		return null;
	}
	
	public ModuleType getModuleType(String className, String moduleName) {
		for (ModuleType cmType=getModuleTypes(); cmType != null; cmType=cmType.next()) {
			if (className.equalsIgnoreCase(cmType.getClassName()) && moduleName.equalsIgnoreCase(cmType.getName()) ) 
				return cmType;
		}
		return null;
	}
	
	public int getNModuleTypes() {
		int nModule = 0;
		for (ModuleType cmType=getModuleTypes(); cmType != null; cmType=cmType.next())
			nModule++;
		return nModule;
	}
	
	public int getNModuleTypes(String className) {
		int nModule = 0;
		for (ModuleType cmType=getModuleTypes(className); cmType != null; cmType=cmType.next(className))
			nModule++;
		return nModule;
	}

	public void addModuleType(ModuleType cmType) {
		mModuleTypeList.addNode(cmType);
	}

	//////////////////////////////////////////////////
	// Event Types
	//////////////////////////////////////////////////

	private LinkedList mEventTypeList = new LinkedList();

	public boolean loadEventTypes() {
	mEventTypeList = new LinkedList();
		for (int n=0; n<EVENTTYPE_DATA.length; n++) {
			EventType event = new EventType(EVENTTYPE_DATA[n][0], EVENTTYPE_DATA[n][1], EVENTTYPE_DATA[n][2], EVENTTYPE_DATA[n][3], EVENTTYPE_DATA[n][4]);
			addEventType(event);
		}

		return true;
	}
	
	public EventType getEventTypes() {
		return (EventType)mEventTypeList.getNodes();
	}

	public EventType getEventType(String name) {
		for (int n=0; n<getNEventTypes(); n++) {
			EventType eventType = getEventType(n);
			if (name.equals(eventType.getName()) == true)
				return eventType;
		}
		return null;
	}

	public EventType getEventType(int eventType) {
		return (EventType)mEventTypeList.getNode(eventType);
	}

	public int getEventTypeNumber(EventType eventType) {
		for (int n=0; n<getNEventTypes(); n++) {
			if (eventType == getEventType(n))
				return n;
		}
		return -1;
	}

	public int getNEventTypes() {
		return mEventTypeList.getNNodes();
	}

	public void addEventType(EventType eventType) {
		mEventTypeList.addNode(eventType);
	}

	//////////////////////////////////////////////////
	// Event 
	//////////////////////////////////////////////////

	private void createSystemEvents() {
		for (int n=0; n<getNEventTypes(); n++) {
			EventType eventType = getEventType(n);
			Debug.message("World::createSystemEvents (" + eventType + ")");
			if (eventType.getAttributeType() == EVENTTYPE_ATTRIBUTE_SYSTEM) {
				Event event = new Event(this, eventType, EVENT_NONE_OPTION_NAME);
				addEventNode(event.getNode());
			}
		}
	}

	private boolean hasSystemEventsNodes() {
		for (EventType eventType=getEventTypes(); eventType != null; eventType=eventType.next()) {
			if (eventType.getAttributeType() == EVENTTYPE_ATTRIBUTE_SYSTEM) {
				boolean bHasEvent = false;
				for (Node node = getEventRootNode().getChildNodes(); node != null; node = node.next()) {
					Event event = new Event(this, node);
					if (eventType.getName().equals(event.getEventTypeName()) == true) {
						bHasEvent = true;
						break;
					}
				}
				if (bHasEvent == false)
					return false;
			}

		}
		return true;
	}
	
	public Node getEventNodes() {
		if (hasSystemEventsNodes() == false)
			createSystemEvents();

		for (Node node = getEventRootNode().getChildNodes(); node != null; node = node.next()) {
			Event event = new Event(this, node);
			if (event.isEventNode() == true)
				return node;
		}
		return null;
	}

	public Node getEventNode(String typeName, String optionName) {
		if (optionName.equals(EVENT_NONE_OPTION_NAME) == true)
			optionName = null;

		for (Node node=getEventNodes(); node != null; node=node.next()) {
			Event event = new Event(this, node);
			if (typeName.equals(event.getEventTypeName()) == true) {
				if (optionName == null)
					return node;
				else if (optionName.equals(event.getOptionString()) == true)
					return node;
			}
		}
		return null;
	}

	public Node getEventNode(EventType eventType) {
		for (Node node=getEventNodes(); node != null; node=node.next()) {
			Event event = new Event(this, node);
			if (event.getEventType() == eventType)
				return node;
		}
		return null;
	}

	public Node getEventNode(int nEvent) {
		Node node = getEventNodes();
		for (int n=0; n<nEvent; n++)
			node = node.next();
		return node;
	}

	public int getNEventNodes() {
		int nEventNode = 0;
		for (Node node = getEventRootNode().getChildNodes(); node != null; node = node.next()) {
			Event event = new Event(this, node);
			if (event.isEventNode() == true)
				nEventNode++;;
		}
		return nEventNode;
	}

	public boolean hasSameEventNode(Node eventNode) {
		String eventNodeName = eventNode.getName();
		if (eventNodeName == null)
			return false; 
		for (Node node = getEventRootNode().getChildNodes(); node != null; node = node.next()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (eventNodeName.equals(nodeName) == true)
					return true;
			}
		}
		return false;
	}
	
	public boolean addEventNode(Node eventNode) {
		if (eventNode == null)
			return false;
		if (hasSameEventNode(eventNode) == true)
			return false;
		getEventRootNode().addChildNode(eventNode);
		return true;
	}

	public boolean isEventNode(Node eventNode) {
		for (Node node = getEventRootNode().getChildNodes(); node != null; node = node.next()) {
			Event event = new Event(this, node);
			if (event.isEventNode() == true) {
				if (node == eventNode)
					return true;
			}
		}
		return false;
	}

	public void deleteEventNode(Node eventNode) {
		if (isEventNode(eventNode) == false)
			return;
		eventNode.remove();
	}

	//////////////////////////////////////////////////
	// Diagram
	//////////////////////////////////////////////////

	public TransformNode addDiagram(String name, Event event) {
		if (getDiagramNode(name, event.getNode()) == null) {
			Diagram	dgm = new Diagram(this, name, event);
			TransformNode transform = dgm.getTransformNode();
			addDiagramNode(transform);
			dgm.addBasisModule();
			return transform;
		}
		else
			return null;
	}

	public TransformNode addDiagram(String name, Node eventNode) {
		Event event = new Event(this, eventNode);
		return addDiagram(name, event);
	}

	public void addDiagramNode(TransformNode node) {
		getDiagramRootNode().addChildNode(node);
	}

	public int getNDiagramNodes() {
		int	nDiagramNodes = 0;
		for (TransformNode transform=getDiagramNodes(); transform != null; transform=nextDiagramNode(transform)) 
			nDiagramNodes++;
		return nDiagramNodes;
	}

	public TransformNode getDiagramNodes() {
		for (TransformNode transform=getDiagramRootNode().getTransformNodes(); transform != null; transform=(TransformNode)transform.nextSameType()) {
			String nodeHeadName = new String(transform.getName().toCharArray(), 0, WORLD_DIAGRAM_NODENAME.length());
			if (nodeHeadName.equals(WORLD_DIAGRAM_NODENAME) == true)
				return transform;
		}
		return null;
	}

	public TransformNode getDiagramNode(String name) {
		for (TransformNode transform=getDiagramNodes(); transform != null; transform=nextDiagramNode(transform)) {
			Diagram diagram =  new Diagram(this, transform);
			if (name.equals(diagram.getName()) == true)
				return transform;
		}
		return null;
	}

	public TransformNode getDiagramNode(String name , Node eventNode) {
		for (TransformNode transform=getDiagramNodes(); transform != null; transform=nextDiagramNode(transform)) {
			Diagram diagram = new Diagram(this, transform);
			if (diagram.getEventNode() == eventNode && name.equals(diagram.getName()) == true)
				return transform;
		}
		return null;
	}

	public TransformNode nextDiagramNode(TransformNode node) {
		for (TransformNode transform=(TransformNode)node.nextSameType(); transform != null; transform=(TransformNode)transform.nextSameType()) {
			String nodeHeadName = new String(transform.getName().toCharArray(), 0, WORLD_DIAGRAM_NODENAME.length());
			if (nodeHeadName.equals(WORLD_DIAGRAM_NODENAME) == true)
				return transform;
		}
		return null;
	}

	public boolean isDiagram(Node node) {
		if (node == null)
			return false;

		if (node.isTransformNode() == false)
			return false;

		TransformNode tnode = (TransformNode)node;
		for (TransformNode transform=getDiagramNodes(); transform != null; transform=nextDiagramNode(transform)) {
			if (transform == tnode)
				return true;
		}
		return false;
	}

	//////////////////////////////////////////////////
	// Diagram Frames
	//////////////////////////////////////////////////

	private LinkedList mDiagramFrameList = new LinkedList();

	public DiagramFrame getDiagramFrames() {
		return (DiagramFrame)mDiagramFrameList.getNodes();
	}

	public DiagramFrame getDiagramFrame(int n) {
		return (DiagramFrame)mDiagramFrameList.getNode(n);
	}

	public DiagramFrame getDiagramFrame(TransformNode dgmNode) {
		for (int n=0; n<getNDiagramFrames(); n++) {
			DiagramFrame dgmFrame = getDiagramFrame(n);
			if (dgmNode == dgmFrame.getDiagramNode())
				return dgmFrame;
		}
		return null;
	}

	private boolean isRect(int x0, int y0, int x1, int y1, int x, int y) {
		if (x0 <= x && x <= x1 && y0 <= y && y <= y1)
			return true;
		else
			return false;
	}
		
	public DiagramFrame getDiagramFrame(int screenx, int screeny) {
		for (int n=0; n<getNDiagramFrames(); n++) {
			DiagramFrame dgmFrame = getDiagramFrame(n);
			Point screenPos = dgmFrame.getMainComponentLocationOnScreen();
			int   width     = dgmFrame.getMainComponentWidth();
			int   height    = dgmFrame.getMainComponentHeight();
			
			int x0 = screenPos.x;
			int y0 = screenPos.y;
			int x1 = x0 + width;
			int y1 = y0 + height;
			
			if (isRect(x0, y0, x1, y1, screenx, screeny) == true)
				return dgmFrame;
		}
		return null;
	}
	
	public int getNDiagramFrames() {
		return mDiagramFrameList.getNNodes();
	}

	public void addDiagramFrame(DiagramFrame dgmFrame) {
		mDiagramFrameList.addNode(dgmFrame);
	}

	public DiagramFrame createDiagramFrame(TransformNode dgmNode) {
		DiagramFrame dgmFrame = new DiagramFrame(this, dgmNode);
		addDiagramFrame(dgmFrame);
		return dgmFrame;
	}
	
	public boolean isDiagramFrameOpened(TransformNode dgmNode) {
		return (getDiagramFrame(dgmNode) != null ? true : false);
	}

	public boolean deleteDiagramFrame(TransformNode dgmNode) {
		DiagramFrame dgm = getDiagramFrame(dgmNode);
		if (dgm != null) {
			dgm.getFrame().dispose();
			return true;
		}
		return false;
	}
	public void deleteDiagramFrames() {
		DiagramFrame dgm = getDiagramFrames();
		while (dgm != null) {
			DiagramFrame nextDgm = dgm.next();
			dgm.getFrame().dispose();
			dgm = nextDgm;
		}
	}
		
	//////////////////////////////////////////////////
	// Node Operation
	//////////////////////////////////////////////////

	public void removeNode(Node node) {
		getSceneGraph().removeNode(node);
		Debug.message("World::removeNode = " + node);
	}

	//////////////////////////////////////////////////
	// Node Operation
	//////////////////////////////////////////////////
	
	private boolean mbSimulationActive = false;
	
	void setSimulationActiveStatus(boolean on) {
		mbSimulationActive = on;
	}
	
	boolean isSimulationActive() {
		return mbSimulationActive;
	}
}
