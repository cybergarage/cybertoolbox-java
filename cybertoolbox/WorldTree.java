/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	WorldTree.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;

import vrml.*;
import vrml.node.*;
import vrml.route.*;

public class WorldTree extends JFrame implements MouseListener, MouseMotionListener, KeyListener, EventTypeConstants {
	private		JTree					mTree;
	private		WorldTreeModel		mTreeModel;
	private		World					mWorld;					
	private		WorldTreeNode		mRootTreeNode;
	private		WorldTreeNode		mSceneGraphRootTreeNode;
	private		WorldTreeNode		mEventRootTreeNode;
	private		WorldTreeNode		mRouteRootTreeNode;

	public World getWorld() {
		return mWorld;
	}

	public SceneGraph getSceneGraph() {
		return getWorld().getSceneGraph();
	}

	public WorldTreeModel getTreeModel() {
		return mTreeModel;
	}

	public JTree getTree() {
		return mTree;
	}

	/////////////////////////////////////////////
	//	Constructor
	/////////////////////////////////////////////

	public WorldTree(World world) {

		mWorld = world;

		getContentPane().add("North", new WorldTreeToolBar(this));
		
		JPanel		panel = new JPanel(true);

		setTitle("CyberToolbox");
		
		getContentPane().add("Center", panel);
		setBackground(Color.lightGray);

		WorldTreeNode root = createRootTreeNode();
		mTreeModel = new WorldTreeModel(root);

		mTree = new JTree(mTreeModel);
		
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
		mTree.addMouseListener(this);
		mTree.addMouseMotionListener(this);

		ToolTipManager.sharedInstance().registerComponent(mTree);
		mTree.setCellRenderer(new WorldTreeCellRenderer());
		mTree.setRowHeight(-1);

		JScrollPane sp = new JScrollPane();
		sp.setPreferredSize(new Dimension(300, 300));
		sp.getViewport().add(mTree);

		panel.setLayout(new BorderLayout());
		panel.add("Center", sp);

		createSceneGraphRootTreeNode();
		createRouteRootTreeNode();
		createEventRootTreeNode();
		reset();
				
		addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {System.exit(0);}});
		addKeyListener(this);

		pack();
		show();
    }
	
	/////////////////////////////////////////////
	//	TreeNode
	/////////////////////////////////////////////

	public void setSelectionPath(TreePath treePath) {
		getTree().setSelectionPath(treePath);
	}
	
	public TreePath getTreePathForMousePosition(int x, int y) {
		return getTree().getPathForLocation(x, y);
	}

	public WorldTreeNode getTreeNodeForTreePath(TreePath treePath) {
		if(treePath != null)
			return (WorldTreeNode)treePath.getLastPathComponent();
		return null;
	}
	
	public WorldTreeNode geTreeNodeForMousePosition(int x, int y) {
		return getTreeNodeForTreePath(getTreePathForMousePosition(x, y));
	}

	public WorldTreeNode getSelectedTreeNode() {
		TreePath selPath = getTree().getSelectionPath();
		if(selPath != null)
			return (WorldTreeNode)selPath.getLastPathComponent();
		return null;
	}
	
	public WorldTreeNode createNewTreeNode(String name, Node node) {
		return new WorldTreeNode(new WorldTreeData(name, node));
	}

	/////////////////////////////////////////////
	//	Root Node
	/////////////////////////////////////////////

	private WorldTreeNode createRootTreeNode() {
		mRootTreeNode = createNewTreeNode("Root", null);
		return mRootTreeNode;
	}
		
	public WorldTreeNode getRootTreeNode() {
		return (WorldTreeNode)mTreeModel.getRoot();
	}

	private WorldTreeNode createSceneGraphRootTreeNode()
	{
		mSceneGraphRootTreeNode = createNewTreeNode("SceneGraph", null);
		mTreeModel.insertNodeInto(mSceneGraphRootTreeNode, getRootTreeNode(), mTreeModel.getChildCount(getRootTreeNode()));
		return mSceneGraphRootTreeNode;
	}

	public WorldTreeNode getSceneGraphRootTreeNode() {
		return mSceneGraphRootTreeNode;
	}


	private WorldTreeNode createEventRootTreeNode()
	{
		mEventRootTreeNode = createNewTreeNode("Event", null);
		mTreeModel.insertNodeInto(mEventRootTreeNode, getRootTreeNode(), mTreeModel.getChildCount(getRootTreeNode()));
		return mEventRootTreeNode;
	}

	public WorldTreeNode getEventRootTreeNode() {
		return mEventRootTreeNode;
	}

	private WorldTreeNode createRouteRootTreeNode()
	{
		mRouteRootTreeNode = createNewTreeNode("Route", null);
		mTreeModel.insertNodeInto(mRouteRootTreeNode, getRootTreeNode(), mTreeModel.getChildCount(getRootTreeNode()));
		return mRouteRootTreeNode;
	}

	public WorldTreeNode getRouteRootTreeNode() {
		return mRouteRootTreeNode;
	}

	/////////////////////////////////////////////
	//	Operation
	/////////////////////////////////////////////
	
	public void clear() {
		removeSceneGraphNodeTreeNodes();
		removeEventTreeNodes();
	}

	public void reset() {
		clear();
		addSceneGraphNodeTreeNodes();
		addRouteTreeNodes();
		addEventTreeNodes();
	}
	
	/////////////////////////////////////////////
	//	SceneGraph Node
	/////////////////////////////////////////////

	public String getNodeTreeText(Node node) {
		String treeNodeName;
		String nodeName = node.getName();
		if (nodeName != null && 0 < nodeName.length()) {
			if (node.isInstanceNode()) 
				treeNodeName = node.getType() + " - " + nodeName + " (Instance)";
			else
				treeNodeName = node.getType() + " - " + nodeName;
		} 
		else {
			if (node.isInstanceNode()) 
				treeNodeName = node.getType() + " (Instance)";
			else
				treeNodeName = node.getType();
		}
		return treeNodeName;
	}
	
	public void addSceneGraphNodeTreeNode(Node node, Node parentNode)
	{
		if (getWorld().isSystemNode(node) == true)
			return;

		WorldTreeNode parentTreeNode;
		if (parentNode == null || parentNode == getSceneGraph().getRootNode())
			parentTreeNode = getSceneGraphRootTreeNode();
		else
			parentTreeNode = (WorldTreeNode)parentNode.getData();

		String treeNodeName = getNodeTreeText(node);
		
		WorldTreeNode newNode = createNewTreeNode(treeNodeName, node);
		node.setData(newNode);
		mTreeModel.insertNodeInto(newNode, parentTreeNode, mTreeModel.getChildCount(parentTreeNode));

//		if (node->isInstanceNode() == false) {
			for (Node cnode=node.getChildNodes(); cnode!=null; cnode=cnode.next())
				addSceneGraphNodeTreeNode(cnode, node);
//		}
	}

	public void addSceneGraphNodeTreeNodes() {
		SceneGraph sg = getSceneGraph();
		for (Node node=sg.getNodes(); node!=null; node=node.next())
			addSceneGraphNodeTreeNode(node, null);
	}

	public void removeSceneGraphNodeTreeNode(Node node) {
		WorldTreeNode treeNode = (WorldTreeNode)node.getData();
		if (treeNode != null) {
			getTreeModel().removeNodeFromParent(treeNode);
			node.setData(null);
		}
	}

	public void removeSceneGraphNodeTreeNodes() {
		SceneGraph sg = getSceneGraph();
		for (Node node=sg.getNodes(); node!=null; node=node.next())
			removeSceneGraphNodeTreeNode(node);
	}

	public Node findSceneGraphNode(WorldTreeNode treeNode) {
		if (treeNode == null)
			return null;
		
		if (treeNode == getSceneGraphRootTreeNode()) 
			return getSceneGraph().getRootNode();
			
		for (Node node=getSceneGraph().getNodes(); node!=null; node=node.nextTraversal()) {
			if (getWorld().isSystemNode(node) == false) {
				if (node.getData() == treeNode)
					return node;
			}
		}
		return null;
	}

	/////////////////////////////////////////////
	//	Route Node
	/////////////////////////////////////////////

	public String getRouteTreeText(Route route) {
		Node		eventOutNode		= route.getEventOutNode();
		Field	eventOutField	= route.getEventOutField();
		Node		eventInNode		= route.getEventInNode();
		Field	eventInField		= route.getEventInField();
		
		if (eventOutNode == null || eventOutField == null || eventInNode == null || eventInField == null)
			return null;
		
		if (eventOutNode.hasName() == false || eventInNode.hasName() == false)
			return null;
		
		return eventOutNode.getName() + "." + eventOutField.getName() + " TO " + eventInNode.getName() + "." + eventInField.getName();
	}
	
	public void addRouteTreeNode(Route route) {
		if (getWorld().isSystemRoute(route) == true)
			return;
		WorldTreeNode newNode = createNewTreeNode(getRouteTreeText(route), null);
		route.setData(newNode);
		mTreeModel.insertNodeInto(newNode, getRouteRootTreeNode(), mTreeModel.getChildCount(getRouteRootTreeNode()));
	}
	
	public void addRouteTreeNodes() {
		for (Route route=getWorld().getSceneGraph().getRoutes(); route != null; route=route.next()) 
			addRouteTreeNode(route);
	}

	public void removeRouteTreeNode(Route route) {
		WorldTreeNode treeNode = (WorldTreeNode)route.getData();
		if (treeNode != null) {
			getTreeModel().removeNodeFromParent(treeNode);
			route.setData(null);
		}
	}
	
	public void removeRouteTreeNodes() {
		for (Route route=getWorld().getSceneGraph().getRoutes(); route != null; route=route.next()) 
			removeRouteTreeNode(route);
	}

	public Route findRoute(WorldTreeNode treeNode) {
		for (Route route=getWorld().getSceneGraph().getRoutes(); route != null; route=route.next()) {
			if (route.getData() == treeNode)
				return route;
		}	
		return null;
	}

	/////////////////////////////////////////////
	//	Event Node
	/////////////////////////////////////////////

	public String getEventNodeTreeText(Event event) {
		String eventName = null;
		if (event.getOptionString() != null) 
			eventName = event.getEventTypeName() + " ( " + event.getOptionString() + " )";
		else
			eventName = event.getEventTypeName();
		return eventName;
	}
	
	public void addEventTreeNode(Event event) {
		WorldTreeNode newNode = createNewTreeNode(getEventNodeTreeText(event), null);
		event.getNode().setData(newNode);
		mTreeModel.insertNodeInto(newNode, getEventRootTreeNode(), mTreeModel.getChildCount(getEventRootTreeNode()));

		addDiagramTreeNodes(event);
	}
	
	public void addEventTreeNodes() {
		for (Node enode=getWorld().getEventNodes(); enode != null; enode=enode.next()) {
			Event event = new Event(getWorld(), enode);
			addEventTreeNode(event);
		}
	}

	public void removeEventTreeNode(Event event) {
		removeDiagramTreeNodes(event);
		
		WorldTreeNode treeNode = (WorldTreeNode)event.getNode().getData();
		if (treeNode != null) {
			getTreeModel().removeNodeFromParent(treeNode);
			event.getNode().setData(null);
		}
	}
	
	public void removeEventTreeNodes() {
		for (Node enode=getWorld().getEventNodes(); enode != null; enode=enode.next()) {
			Event event = new Event(getWorld(), enode);
			removeEventTreeNode(event);
		}
	}

	public Node findEventNode(WorldTreeNode treeNode) {
		for (Node enode=getWorld().getEventNodes(); enode != null; enode=enode.next()) {
			if (enode.getData() == treeNode)
				return enode;
		}
		return null;
	}

	/////////////////////////////////////////////
	//	Diagram Node
	/////////////////////////////////////////////

	public void addDiagramTreeNode(Event event, Diagram diagram) {
		WorldTreeNode newNode = createNewTreeNode(diagram.getName(), null);
		diagram.getTransformNode().setData(newNode);
		WorldTreeNode parentTreeNode = (WorldTreeNode)event.getNode().getData();
		mTreeModel.insertNodeInto(newNode, parentTreeNode, mTreeModel.getChildCount(parentTreeNode));
	}
	
	public void addDiagramTreeNodes(Event event) {
		for (TransformNode transform=getWorld().getDiagramNodes(); transform != null; transform=getWorld().nextDiagramNode(transform)) {
			Diagram diagram = new Diagram(getWorld(), transform);
			if (diagram.getEventNode() == event.getNode())
				addDiagramTreeNode(event, diagram);
		}
	}

	public void removeDiagramTreeNode(Diagram diagram) {
		WorldTreeNode treeNode = (WorldTreeNode)diagram.getTransformNode().getData();
		if (treeNode != null) {
			getTreeModel().removeNodeFromParent(treeNode);
			diagram.getTransformNode().setData(null);
		}
	}
	
	public void removeDiagramTreeNodes(Event event) {
		for (TransformNode transform=getWorld().getDiagramNodes(); transform != null; transform=getWorld().nextDiagramNode(transform)) {
			Diagram diagram = new Diagram(getWorld(), transform);
			if (diagram.getEventNode() == event.getNode())
				removeDiagramTreeNode(diagram);
		}
	}

	public TransformNode findDiagramNode(WorldTreeNode treeNode) {
		for (TransformNode transform=getWorld().getDiagramNodes(); transform != null; transform=getWorld().nextDiagramNode(transform)) {
			if (transform.getData() == treeNode)
				return transform;
		}
		return null;
	}
		
	/////////////////////////////////////////////
	//	ToolBar
	/////////////////////////////////////////////

	private Image loadImageIcon(String filename) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(filename);
		MediaTracker mt = new MediaTracker(this);
		mt.addImage (image, 0);
		try { mt.waitForAll(); }
		catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		if (mt.isErrorAny()) {
			Debug.warning("Couldn't loading a image file (" + filename + ") in WorldTree::loadImageIcon" );
		}
		return image;
	}
	
	///////////////////////////////////////////////
	//  Dragging Infomation
	///////////////////////////////////////////////
	
	private WorldTreeNode mDragedTreeNode = null;
	
	private void setDragedTreeNode(WorldTreeNode treeNode) {
		mDragedTreeNode = treeNode;
	}

	private WorldTreeNode getDragedTreeNode() {
		return mDragedTreeNode;
	}

	private WorldTreeNode mDropedTreeNode = null;
	
	private void setDropedTreeNode(WorldTreeNode treeNode) {
		mDropedTreeNode = treeNode;
	}

	private WorldTreeNode getDropedTreeNode() {
		return mDropedTreeNode;
	}

	public boolean isTreeNodeDraged () {
		if (getDragedTreeNode() == null)
			return false;
		if (getDropedTreeNode() == null)
			return false;
		if (getDragedTreeNode() == getDropedTreeNode())
			return false;
		return true;
	}
	
	///////////////////////////////////////////////
	//  mousePressed / mouseReleased
	///////////////////////////////////////////////
	
	public void mousePressed(MouseEvent e) {
		Debug.message("mousePressed");
		setDragedTreeNode(geTreeNodeForMousePosition(e.getX(), e.getY()));
		setDropedTreeNode(null);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public boolean moveTreeNode(WorldTreeNode dragedTreeNode, WorldTreeNode dropedTreeNode) {
		
		// Are SceneGraph nodes ?
		Node dragedNode = findSceneGraphNode(dragedTreeNode);
		Node dropedNode = findSceneGraphNode(dropedTreeNode);
		if (dragedNode != null && dropedNode != null) {
			if (dropedNode.isChildNodeType(dragedNode) == true) {
				removeSceneGraphNodeTreeNode(dragedNode);
				if (dropedNode.isRootNode() == true) 
					getSceneGraph().moveNode(dragedNode);
				else
					dropedNode.moveChildNode(dragedNode);
				addSceneGraphNodeTreeNode(dragedNode, dropedNode);
				return true;
			}
		}

		return false;		
	}

	public void mouseReleased(MouseEvent e) {
		Debug.message("mouseReleased");
		
		if (isTreeNodeDraged() == true) {
			if (moveTreeNode(getDragedTreeNode(), getDropedTreeNode()) == false)
				Toolkit.getDefaultToolkit().beep();
		}
		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	} 
	
	////////////////////////////////////////////////
	// Edit Action (Open Dialog)
	////////////////////////////////////////////////
	
	public void openSceneGraphNodeDialog(WorldTreeNode treeNode, Node sgNode) {
		boolean isSimulationRunning = getWorld().isSimulationRunning();
		if (isSimulationRunning == true)
			getWorld().stopSimulation();
		getWorld().stopPerspectiveViewThread();				
			
		DialogNode dialog = new DialogNode(this, sgNode);
		if (dialog.doModal() == Dialog.OK_OPTION) {
			sgNode.setName(dialog.getNodeName());
			String fieldValues[] = dialog.getFieldValues();
			int n;
			int nFields = sgNode.getNFields();
			for (n=0; n<nFields; n++) {
				Field field = sgNode.getField(n);
				Debug.message("\t" + field.getName() + " : " + fieldValues[n]);
				field.setValue(fieldValues[n]);
			}
			int nExposedFields = sgNode.getNExposedFields();
			for (n=0; n<nExposedFields; n++) {
				Field field = sgNode.getExposedField(n);
				Debug.message(field.getName() + " : " + fieldValues[n+nFields]);
				field.setValue(fieldValues[n+nFields]);
			}
		}			
			
		String nodeText = getNodeTreeText(sgNode);
		treeNode.setText(nodeText);

		if (isSimulationRunning == true)
			getWorld().startSimulation();
		getWorld().startPerspectiveViewThread();				
	}

	public void openRouteDialog(WorldTreeNode treeNode, Route route) {
		boolean isSimulationRunning = getWorld().isSimulationRunning();
		if (isSimulationRunning == true)
			getWorld().stopSimulation();
		getWorld().stopPerspectiveViewThread();				
		
		DialogRoute dialog = new DialogRoute(this, getWorld(), route);
		if (dialog.doModal() == Dialog.OK_OPTION) { 
			Route changedRoute = dialog.getRoute();
			if (changedRoute!= null) {
				route.set(changedRoute);
				treeNode.setText(getRouteTreeText(route));
			}
		}
				
		if (isSimulationRunning == true)
			getWorld().startSimulation();
		getWorld().startPerspectiveViewThread();				
	}

	public void openEventNodeDialog(WorldTreeNode treeNode, Node eventNode) {
		boolean isSimulationRunning = getWorld().isSimulationRunning();
		if (isSimulationRunning == true)
			getWorld().stopSimulation();
		getWorld().stopPerspectiveViewThread();				

		Event event = new Event(getWorld(), eventNode);
		DialogEvent dialog = new DialogEvent(this, event);
		if (dialog.doModal() == Dialog.OK_OPTION) {
			String optionString = dialog.getOptionString();
			if (optionString != null) {
				event.setOptionString(optionString);
				String nodeText = getEventNodeTreeText(event);
				treeNode.setText(nodeText);
			}
			else
				new MessageBeep();
		}
				
		if (isSimulationRunning == true)
			getWorld().startSimulation();
		getWorld().startPerspectiveViewThread();				
	}
	
	public void openDiagramNodeFrame(WorldTreeNode treeNode, TransformNode dgmNode) {
		if (getWorld().isDiagramFrameOpened(dgmNode) == false) {
			DiagramFrame dgmFrame = getWorld().createDiagramFrame(dgmNode);
			dgmFrame.repaint();
			if (getWorld().isSimulationRunning() == true)
				dgmFrame.start();
		}
	}
	
	//////////////////////////////////////////////////
	// Remove Action
	//////////////////////////////////////////////////

		private boolean removeSceneGraphNode(Node sgNode) {

		if (getSceneGraph().getRootNode() != sgNode) {
			int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this node ( " + sgNode + " ) ?", "", JOptionPane.OK_CANCEL_OPTION);
			repaint();
			if(result == JOptionPane.YES_OPTION) {
				getWorld().stopPerspectiveViewThread();				
				removeSceneGraphNodeTreeNode(sgNode);
				sgNode.remove();
				getWorld().startPerspectiveViewThread();				
				return true;
			}
		}
		return false;
	}

	private boolean removeRoute(Route route) {
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this route ?", "", JOptionPane.OK_CANCEL_OPTION);
		repaint();
		if(result == JOptionPane.YES_OPTION) {
			getWorld().stopPerspectiveViewThread();				
			removeRouteTreeNode(route);
			route.remove();
			getWorld().startPerspectiveViewThread();				
			return true;
		}
		return false;
	}

	private boolean removeEventNode(Node eventNode) {	
		Event event = new Event(getWorld(), eventNode);
		
		if (event.getAttributeType() == EVENTTYPE_ATTRIBUTE_SYSTEM)
			return false;
		
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this event ( " + getEventNodeTreeText(event) + " ) ?", "", JOptionPane.OK_CANCEL_OPTION);
		repaint();
		if(result == JOptionPane.YES_OPTION) {
			getWorld().stopPerspectiveViewThread();				
			TransformNode dgmNode = getWorld().getDiagramNodes();
			while (dgmNode != null) {
				TransformNode nextDgmNode = getWorld().nextDiagramNode(dgmNode);
				Diagram dgm = new Diagram(getWorld(), dgmNode);
				if (eventNode == dgm.getEventNode()) {
					getWorld().deleteDiagramFrame(dgmNode);
					dgmNode.remove();
				}
				dgmNode = nextDgmNode;
			}
			removeEventTreeNode(event);
			eventNode.remove();
			getWorld().startPerspectiveViewThread();				
			return true;
		}
		return false;
	}
		
	private boolean removeDiagramNode(TransformNode dgmNode) {
		Diagram dgm = new Diagram(getWorld(), dgmNode);
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this diagram ( " + dgm.getName() + " ) ?", "", JOptionPane.OK_CANCEL_OPTION);
		repaint();
		if(result == JOptionPane.YES_OPTION) {
			getWorld().stopPerspectiveViewThread();				
			getWorld().deleteDiagramFrame(dgmNode);
			removeDiagramTreeNode(dgm);
			dgmNode.remove();
			getWorld().startPerspectiveViewThread();				
			return true;
		}
		return false;
	}
	
	////////////////////////////////////////////////
	// Popup Menu
	////////////////////////////////////////////////
	
	private String mPopupMenuString[] = {
		"Edit",
		"Remove"
	};
		
	public class PopupMenu extends JPopupMenu {
		public PopupMenu(AbstractAction action) {
			for (int n=0; n<mPopupMenuString.length; n++) {
				if (0 < mPopupMenuString[n].length()) {
					JMenuItem menuItem = new JMenuItem(mPopupMenuString[n]);
					menuItem.addActionListener(action);
					add(menuItem);
				}
				else
					addSeparator();
			}
		}
	}

	private class PopupMenuSceneGraphNodeAction extends AbstractAction {
		private WorldTreeNode	mTreeNode;	
		private Node				mNode;
		
		public PopupMenuSceneGraphNodeAction(WorldTreeNode treeNode, Node node) {
			mTreeNode = treeNode;
			mNode = node;
		}
		
		public void actionPerformed(ActionEvent e) {
			Debug.message("PopupMenuAction.actionPerformed = " + e.getActionCommand());	
			if (mPopupMenuString[0].equals(e.getActionCommand())) 
				openSceneGraphNodeDialog(mTreeNode, mNode);
			if (mPopupMenuString[1].equals(e.getActionCommand())) 
				removeSceneGraphNode(mNode);
		}
	}

	private class PopupMenuRouteAction extends AbstractAction {
		private WorldTreeNode	mTreeNode;	
		private Route				mRoute;
		
		public PopupMenuRouteAction(WorldTreeNode treeNode, Route route) {
			mTreeNode = treeNode;
			mRoute = route;
		}
		
		public void actionPerformed(ActionEvent e) {
			Debug.message("PopupMenuAction.actionPerformed = " + e.getActionCommand());	
			if (mPopupMenuString[0].equals(e.getActionCommand())) 
				openRouteDialog(mTreeNode, mRoute);
			if (mPopupMenuString[1].equals(e.getActionCommand())) 
				removeRoute(mRoute);
		}
	}

	private class PopupMenuEventNodeAction extends AbstractAction {
		private WorldTreeNode	mTreeNode;	
		private Node				mNode;
		
		public PopupMenuEventNodeAction(WorldTreeNode treeNode, Node node) {
			mTreeNode = treeNode;
			mNode = node;
		}
		
		public void actionPerformed(ActionEvent e) {
			Debug.message("PopupMenuAction.actionPerformed = " + e.getActionCommand());	
			if (mPopupMenuString[0].equals(e.getActionCommand())) 
				openEventNodeDialog(mTreeNode, mNode);
			if (mPopupMenuString[1].equals(e.getActionCommand())) 
				removeEventNode(mNode);
		}
	}

	private class PopupMenuDiagramNodeAction extends AbstractAction {
		private WorldTreeNode	mTreeNode;	
		private TransformNode	mDiagramNode;
		
		public PopupMenuDiagramNodeAction(WorldTreeNode treeNode, TransformNode dgmNode) {
			mTreeNode = treeNode;
			mDiagramNode = dgmNode;
		}
		
		public void actionPerformed(ActionEvent e) {
			Debug.message("PopupMenuAction.actionPerformed = " + e.getActionCommand());	
			if (mPopupMenuString[0].equals(e.getActionCommand())) 
				openDiagramNodeFrame(mTreeNode, mDiagramNode);
			if (mPopupMenuString[1].equals(e.getActionCommand())) 
				removeDiagramNode(mDiagramNode);
		}
	}
		
	///////////////////////////////////////////////
	//  mouseClicked
	///////////////////////////////////////////////

	public void mouseClicked(MouseEvent e) {
	
		Debug.message("mouseClicked");
		
		TreePath treePath = getTreePathForMousePosition(e.getX(), e.getY());

		if (treePath == null)
			return;
		
		setSelectionPath(treePath);
			
		WorldTreeNode treeNode = getTreeNodeForTreePath(treePath);
		
		if (treeNode == null)
			return;

		int mouseButton = e.getModifiers();
					
		boolean dblClick		= false;
		boolean singleClick	= false;
		
		if(e.getClickCount() == 1)
			singleClick = true;
		if(e.getClickCount() == 2)
			dblClick = true;
				
		Node sgNode = findSceneGraphNode(treeNode);
		if (sgNode != null && getSceneGraph().getRootNode() != sgNode) {
			Debug.message("SceneGraph Node Clicked : " + sgNode);
			if (mouseButton == e.BUTTON1_MASK && dblClick == true)
				openSceneGraphNodeDialog(treeNode, sgNode);
			if (mouseButton == e.BUTTON3_MASK && singleClick == true) {
				PopupMenu popupMenu = new PopupMenu(new PopupMenuSceneGraphNodeAction(treeNode, sgNode));
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		Route route = findRoute(treeNode);
		if (route != null) {
			Debug.message("Route Node Clicked : " + route);
			if (mouseButton == e.BUTTON1_MASK && dblClick == true)
				openRouteDialog(treeNode, route);
			if (mouseButton == e.BUTTON3_MASK && singleClick == true) {
				PopupMenu popupMenu = new PopupMenu(new PopupMenuRouteAction(treeNode, route));
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
		
		Node eventNode = findEventNode(treeNode);
		if (eventNode != null) {
			Debug.message("Event Node Clicked (" + eventNode.getName() + ")");
			Event event = new Event(getWorld(), eventNode);
			if (event.getAttributeType() == EVENTTYPE_ATTRIBUTE_USER) {
				if (mouseButton == e.BUTTON1_MASK && dblClick == true)
					openEventNodeDialog(treeNode, eventNode);
			}
			if (mouseButton == e.BUTTON3_MASK && singleClick == true) {
				PopupMenu popupMenu = new PopupMenu(new PopupMenuEventNodeAction(treeNode, eventNode));
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
		
		TransformNode dgmNode = findDiagramNode(treeNode);
		if (dgmNode != null) {
			Debug.message("Diagram Node Clicked (" + dgmNode.getName() + ")");
			if (mouseButton == e.BUTTON1_MASK && dblClick == true)
				openDiagramNodeFrame(treeNode, dgmNode);
			if (mouseButton == e.BUTTON3_MASK && singleClick == true) {
				PopupMenu popupMenu = new PopupMenu(new PopupMenuDiagramNodeAction(treeNode, dgmNode));
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	///////////////////////////////////////////////
	//  mousDragged
	///////////////////////////////////////////////
	
	public void mouseDragged(MouseEvent e) {
		setDropedTreeNode(geTreeNodeForMousePosition(e.getX(), e.getY()));
		getTree().setSelectionPath(getTree().getPathForLocation(e.getX(), e.getY()));
	}

	///////////////////////////////////////////////
	//  MouseListener
	///////////////////////////////////////////////
	
	public void mouseEntered(MouseEvent e) {
		Debug.message("mouseEntered");
	}

	public void mouseExited(MouseEvent e) {
		Debug.message("mouseExited");
	} 

	public void mouseMoved(MouseEvent e) {
		//Debug.message("mouseMoved");
	}

	//////////////////////////////////////////////////
	// KeyListener
	//////////////////////////////////////////////////

	private boolean removeObject(WorldTreeNode treeNode) {
		Node sgNode = findSceneGraphNode(treeNode);
		if (sgNode != null && getSceneGraph().getRootNode() != sgNode) {
			removeSceneGraphNode(sgNode);
			return true;
		}

		Route route = findRoute(treeNode);
		if (route != null) {
			removeRoute(route);
			return true;
		}
		
		Node eventNode = findEventNode(treeNode);
		if (eventNode != null) {
			removeEventNode(eventNode);
			return true;
		}
		
		TransformNode dgmNode = findDiagramNode(treeNode);
		if (dgmNode != null) {
			removeDiagramNode(dgmNode);
			return true;
		}
		
		return false;
	}
		
	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DELETE:
			{
				if (removeObject(getSelectedTreeNode()) == false)
					new MessageBeep();
			}
			break;
		case KeyEvent.VK_P:
			{
				getSceneGraph().print();
			}
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
	}
}

