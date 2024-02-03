/******************************************************************
*
*	VRML library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File: Node.java
*
******************************************************************/

package vrml.node;

import java.util.Vector;
import java.io.*;
import vrml.*;
import vrml.util.*;
import vrml.route.*;
import vrml.field.*;
import vrml.field.SFMatrix;

public abstract class Node extends BaseNode implements Runnable, NodeConstatns { 

	public static final int		RUNNABLE_TYPE_NONE				= 0;
	public static final int		RUNNABLE_TYPE_ALWAYS			= 1;
	public static final int		RUNNABLE_DEFAULT_INTERVAL_TIME	= 100;

	////////////////////////////////////////////////
	//	Member
	////////////////////////////////////////////////

	private Vector		mField					= new Vector();
	private Vector		mPrivateField			= new Vector();
	private Vector		mExposedField			= new Vector();
	private Vector		mEventInField			= new Vector();
	private Vector		mEventOutField			= new Vector();
	private boolean		mInitializationFlag		= false;
	private NodeObject	mObject					= null;
	private Thread		mThreadObject			= null;

	private boolean		mRunnable				= false;			
	private int			mRunnableType			= RUNNABLE_TYPE_NONE;			
	private int			mRunnableIntervalTime	= RUNNABLE_DEFAULT_INTERVAL_TIME;
				
	private Node		mParentNode				= null;
	private LinkedList	mChildNodes				= new LinkedList();
	private SceneGraph	mSceneGraph				= null;
	private Object		mUserData				= null;
	private Node		mReferenceNode			= null;
	
	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////

	abstract public boolean	isChildNodeType(Node node);
	abstract public void	update();
	
	public void recreateNodeObject() {
		if (isRootNode() == false) {
			SceneGraph sg = getSceneGraph();
			if (sg == null)
				return;
			SceneGraphObject sgObject = sg.getObject();
			if (sgObject == null)
				return;
			NodeObject nodeObject = getObject();
			if (nodeObject != null)
				sgObject.removeNode(sg, this);
			nodeObject = sg.createNodeObject(this);
			setObject(nodeObject);
			if (nodeObject != null)
				sgObject.addNode(sg, this);
			Debug.message("Node::recreateNodeObject = " + this + ", " + nodeObject);
		}
	}
	
	public void initialize() {
		//recreateNodeObject();
	}
	
	abstract public void	uninitialize();

	////////////////////////////////////////////////
	//	Constractor
	////////////////////////////////////////////////

	public Node() {
		setHeaderFlag(true);
		setParentNode(null);
		setSceneGraph(null);
		setReferenceNode(null);
		setObject(null);
		setInitializationFlag(false);
		setThreadObject(null);
		setRunnable(false);
	}
	
	public Node(String nodeType, String nodeName) {
		this();
		setType(nodeType);
		setName(nodeName);
	}

	////////////////////////////////////////////////
	//	List management
	////////////////////////////////////////////////

	public void removeChildNodes() {
		Node node=getChildNodes();
		while (node != null) {
			Node nextNode = node.next();
			node.remove();
			node = nextNode;
		}
	}
	
	public void removeRoutes() {
		SceneGraph sg = getSceneGraph();
		if (sg != null) {
			Route route=sg.getRoutes();
			while (route != null) {
				Route nextRoute = route.next();
				if (route.getEventInNode() == this || route.getEventOutNode() == this)
					route.remove();
				route = nextRoute;
			}
		}
	}

	public void removeSFNodes() {
		SceneGraph sg = getSceneGraph();
		if (sg != null) {
			for (ScriptNode script = sg.findScriptNode(); script != null; script=(ScriptNode)script.nextTraversalSameType()) {
				for (int n=0; n<script.getNFields(); n++) {
					Field field = script.getField(n);
					if (field.getType() == fieldTypeSFNode) {
						SFNode sfnode = (SFNode)field;
						if (sfnode.getValue() == this)
							sfnode.setValue((Node)null);
					}
				}
			}
		}
	}

	public void removeInstanceNodes() {
		SceneGraph sg = getSceneGraph();
		if (sg != null && isInstanceNode() == false) {
			Node node = sg.getNodes();
			while (node != null) {
				Node nextNode = node.nextTraversal();
				if (node.isInstanceNode() == true) {
					Node refNode = node.getReferenceNode();
					while (refNode.isInstanceNode() == true)
						refNode = refNode.getReferenceNode();
					if (refNode == this) {
						node.removeChildNodes();
						nextNode = node.nextTraversal();
						node.remove();
					}
				}
				node = nextNode;
			}
		}
	}

	public void remove() { 
		SceneGraph sg = getSceneGraph();
		
		if (sg != null) 
			sg.removeNodeObject(this);
		
		super.remove();

		if (isInstanceNode() == false) {
			removeRoutes();
			removeSFNodes();
			removeInstanceNodes();

			if (isBindableNode() == true) {
				if (sg != null)
					sg.setBindableNode((BindableNode)this, false);			
			}
		}

		setParentNode(null);
		setSceneGraph(null);
	}

	////////////////////////////////////////////////
	//	Field
	////////////////////////////////////////////////

	// Get an exposed field by name. 
	//   Throws an InvalidExposedFieldException if fieldName isn't a valid
	//   exposed field name for a node of this type.

	public final Field getField(String fieldName) {
		if (fieldName == null)
			throw new InvalidFieldException(fieldName + " is not found.");
		for (int n=0; n<getNFields(); n++) {
			Field field = getField(n);
			if (fieldName.compareTo(field.getName()) == 0)
				return field;
		}
		throw new InvalidFieldException(fieldName + " is not found.");
//		return null;
	}

	public final int getNFields() {
		return mField.size();
	}

	public final void addField(Field field) {
		mField.addElement(field);
	}

	public final void addField(String name, Field field) {
		field.setName(name);
		mField.addElement(field);
	}

	public final Field getField(int index) {
		return (Field)mField.elementAt(index);
	}

	public final boolean removeField(Field removeField) {
		for (int n=0; n<getNFields(); n++) {
			Field field = getField(n);
			if (field == removeField) {
				mField.removeElement(field);
				return true;
			}
		}
		return false;
	}

	public final boolean removeField(String fieldName) {
		return removeField(getField(fieldName));
	}

	public int getFieldNumber(Field field) {
		for (int n=0; n<getNFields(); n++) {
			if (getField(n) == field)
				return n;
		}
		return -1;
	}
		
	////////////////////////////////////////////////
	//	Private Field
	////////////////////////////////////////////////

	public final Field getPrivateField(String fieldName) {
		if (fieldName == null)
			throw new InvalidPrivateFieldException(fieldName + " is not found.");
		for (int n=0; n<getNPrivateFields(); n++) {
			Field field = getPrivateField(n);
			if (fieldName.compareTo(field.getName()) == 0)
				return field;
		}
		throw new InvalidPrivateFieldException(fieldName + " is not found.");
//		return null;
	}

	public final int getNPrivateFields() {
		return mPrivateField.size();
	}

	public final void addPrivateField(Field field) {
		mPrivateField.addElement(field);
	}

	public final void addPrivateField(String name, Field field) {
		field.setName(name);
		mPrivateField.addElement(field);
	}

	public final Field getPrivateField(int index) {
		return (Field)mPrivateField.elementAt(index);
	}

	public final boolean removePrivateField(Field removeField) {
		for (int n=0; n<getNPrivateFields(); n++) {
			Field field = getPrivateField(n);
			if (field == removeField) {
				mPrivateField.removeElement(field);
				return true;
			}
		}
		return false;
	}

	public final boolean removePrivateField(String fieldName) {
		return removePrivateField(getPrivateField(fieldName));
	}

	public int getPrivateFieldNumber(Field field) {
		for (int n=0; n<getNPrivateFields(); n++) {
			if (getPrivateField(n) == field)
				return n;
		}
		return -1;
	}

	////////////////////////////////////////////////
	//	ExposedField
	////////////////////////////////////////////////

	// Get an exposed field by name. 
	//   Throws an InvalidExposedFieldException if fieldName isn't a valid
	//   exposed field name for a node of this type.

	public final Field getExposedField(String fieldName) {
		if (fieldName == null)
			throw new InvalidExposedFieldException(fieldName + " is not found.");
			
		for (int n=0; n<getNExposedFields(); n++) {
			Field field = getExposedField(n);
			if (fieldName.compareTo(field.getName()) == 0)
				return field;
			if (fieldName.startsWith(eventInStripString)) {
				if (fieldName.endsWith(field.getName()))
					return field;
			}
			if (fieldName.endsWith(eventOutStripString)) {
				if (fieldName.startsWith(field.getName()))
					return field;
			}
		}
		throw new InvalidExposedFieldException(fieldName + " is not found.");
//		return null;
	}

	public final int getNExposedFields() {
		return mExposedField.size();
	}

	public final void addExposedField(Field field) {
		mExposedField.addElement(field);
	}

	public final void addExposedField(String name, Field field) {
		field.setName(name);
		mExposedField.addElement(field);
	}

	public final Field getExposedField(int index) {
		return (Field)mExposedField.elementAt(index);
	}

	public final boolean removeExposedField(Field removeField) {
		for (int n=0; n<getNExposedFields(); n++) {
			Field field = getExposedField(n);
			if (field == removeField) {
				mExposedField.removeElement(field);
				return true;
			}
		}
		return false;
	}

	public final boolean removeExposedField(String fieldName) {
		return removeExposedField(getExposedField(fieldName));
	}

	public int getExposedFieldNumber(Field field) {
		for (int n=0; n<getNExposedFields(); n++) {
			if (getExposedField(n) == field)
				return n;
		}
		return -1;
	}

	////////////////////////////////////////////////
	//	EventIn
	////////////////////////////////////////////////

	// Get an EventIn by name. Return value is write-only.
	//   Throws an InvalidEventInException if eventInName isn't a valid
	//   event in name for a node of this type.

	public final Field getEventIn(String fieldName) {
		if (fieldName == null)
			throw new InvalidEventInException(fieldName + " is not found.");

		for (int n=0; n<getNEventIn(); n++) {
			Field field = getEventIn(n);
			if (fieldName.compareTo(field.getName()) == 0)
				return field;
			if (fieldName.startsWith(eventInStripString)) {
				if (fieldName.endsWith(field.getName()))
					return field;
			}
		}
		throw new InvalidEventInException(fieldName + " is not found.");
		//return null;
	}

	public final int getNEventIn() {
		return mEventInField.size();
	}

	public final void addEventIn(Field field) {
		mEventInField.addElement(field);
	}

	public final void addEventIn(String name, Field field) {
		field.setName(name);
		mEventInField.addElement(field);
	}

	public final Field getEventIn(int index) {
		return (Field)mEventInField.elementAt(index);
	}

	public final boolean removeEventIn(Field removeField) {
		for (int n=0; n<getNEventIn(); n++) {
			Field field = getEventIn(n);
			if (field == removeField) {
				mEventInField.removeElement(field);
				return true;
			}
		}
		return false;
	}

	public final boolean removeEventIn(String fieldName) {
		return removeEventIn(getEventIn(fieldName));
	}

	public int getEventInNumber(Field field) {
		for (int n=0; n<getNEventIn(); n++) {
			if (getEventIn(n) == field)
				return n;
		}
		return -1;
	}

	////////////////////////////////////////////////
	//	EventOut
	////////////////////////////////////////////////

	// Get an EventOut by name. Return value is read-only.
	//   Throws an InvalidEventOutException if eventOutName isn't a valid
	//   event out name for a node of this type.

	public final ConstField getEventOut(String fieldName) {
		if (fieldName == null)
			throw new InvalidEventOutException(fieldName + " is not found.");
		for (int n=0; n<getNEventOut(); n++) {
			ConstField field = getEventOut(n);
			if (fieldName.compareTo(field.getName()) == 0)
				return field;
			if (fieldName.endsWith(eventOutStripString)) {
				if (fieldName.startsWith(field.getName()))
					return field;
			}
		}
		throw new InvalidEventOutException(fieldName + " is not found.");
//		return null;
	}

	public final int getNEventOut() {
		return mEventOutField.size();
	}

	public final void addEventOut(ConstField field) {
		mEventOutField.addElement(field);
	}

	public final void addEventOut(String name, ConstField field) {
		field.setName(name);
		mEventOutField.addElement(field);
	}

	public final ConstField getEventOut(int index) {
		return (ConstField)mEventOutField.elementAt(index);
	}

	public final boolean removeEventOut(ConstField removeField) {
		for (int n=0; n<getNEventOut(); n++) {
			ConstField field = getEventOut(n);
			if (field == removeField) {
				mEventOutField.removeElement(field);
				return true;
			}
		}
		return false;
	}

	public final boolean removeEventOut(String fieldName) {
		return removeEventOut(getEventOut(fieldName));
	}

	public int getEventOutNumber(Field field) {
		for (int n=0; n<getNEventOut(); n++) {
			if (getEventOut(n) == field)
				return n;
		}
		return -1;
	}

	////////////////////////////////////////////////
	//	Parent node
	////////////////////////////////////////////////

	public void setParentNode(Node parentNode) {
		mParentNode = parentNode;
	}

	public Node getParentNode() {
		return mParentNode;
	}
	
	public boolean isParentNode(Node node) {
		return (getParentNode() == node) ? true : false;
	}

	public boolean isAncestorNode(Node node) {
		for (Node parentNode = getParentNode(); parentNode != null; parentNode = parentNode.getParentNode()) {
			if (node == parentNode)
					return true;
		}
		return false;
	}

	////////////////////////////////////////////////
	//	find node list
	////////////////////////////////////////////////

	public Node nextTraversal() {
		Node nextNode = getChildNodes();
		if (nextNode != null)
			return nextNode;

		nextNode = next();
		if (nextNode == null) {
			Node parentNode = getParentNode();
			while (parentNode != null) { 
				Node parentNextNode = parentNode.next();
				if (parentNextNode != null)
					return parentNextNode;
				parentNode = parentNode.getParentNode();
			}
		}
		return nextNode;
	}

	public Node nextTraversalByType(String type) {
		if (type == null)
			return null;

		for (Node node = nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.getType() != null) {
				if (type.compareTo(node.getType()) == 0)
					return node;
			}
		}
		return null;
	}

	public Node nextTraversalByName(String name) {
		if (name == null)
			return null;

		for (Node node = nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.getName() != null) {
				if (name.compareTo(node.getName()) == 0)
					return node;
			}
		}
		return null;
	}

	public Node nextTraversalSameType() {
		return nextTraversalByType(getType());
	}

	////////////////////////////////////////////////
	//	next node list
	////////////////////////////////////////////////

	public Node next() {
		return (Node)getNextNode();
	}

	public Node next(String type) {
		for (Node node = next(); node != null; node = node.next()) {
			if (type.compareTo(node.getType()) == 0)
				return node;
		}
		return null;
	}

	public Node nextSameType() {
		return next(getType());
	}

	////////////////////////////////////////////////
	//	child node list
	////////////////////////////////////////////////

	public Node getChildNodes() {
		return (Node)mChildNodes.getNodes();
	}

	public Node getFirstChildNodes() {
		int numChild = getNChildNodes();
		if (0 < numChild)
			return getChildNode(0);
		return null;
	}

	public Node getLastChildNodes() {
		int numChild = getNChildNodes();
		if (0 < numChild)
			return getChildNode(numChild - 1);
		return null;
	}

	public Node getChildNodes(String type) {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (type.compareTo(node.getType()) == 0)
				return node;
		}
		return null;
	}

	public Node getChildNode(int n) {
		return (Node)mChildNodes.getNode(n);
	}

	public int getNChildNodes() {
		return mChildNodes.getNNodes();
	}

	////////////////////////////////////////////////
	//	Add children 
	////////////////////////////////////////////////
	
	public void addChildNode(Node node) {
		moveChildNode(node);
		node.initialize();
	}

	public void addChildNodeAtFirst(Node node) {
		moveChildNodeAtFirst(node);
		node.initialize();
	}

	////////////////////////////////////////////////
	//	Move children 
	////////////////////////////////////////////////

	public void moveChildNode(Node node) {
		SceneGraph sg = getSceneGraph();
		mChildNodes.addNode(node); 
		node.setParentNode(this);
		node.setSceneGraph(sg);
		if (sg != null) {
			sg.removeNodeObject(this);
			sg.addNodeObject(this);
		}
	}

	public void moveChildNodeAtFirst(Node node) {
		SceneGraph sg = getSceneGraph();
		mChildNodes.addNodeAtFirst(node); 
		node.setParentNode(this);
		node.setSceneGraph(sg);
		if (sg != null) {
			sg.removeNodeObject(this);
			sg.addNodeObject(this);
		}
	}

	////////////////////////////////////////////////
	//	Add / Remove children (for Groupingnode)
	////////////////////////////////////////////////

	public boolean isChild(Node parentNode, Node node) {
		for (Node cnode = parentNode.getChildNodes(); cnode != null; cnode = cnode.next()) {
			if (cnode == node)
				return true;
		}
		return false;
	}

	public boolean isChild(Node node) {
		if (getChildNodes() != null) {
			for (Node cnode = getChildNodes(); cnode != null; cnode = cnode.next()) {
				if (cnode == node)
					return true;
				if (isChild(cnode, node))
					return true;
			}
		}
		return false;
	}

	public void	addChildren(Node node[]) {
		for (int n=0; n<node.length; n++) {
			if (!isChild(node[n]))
				addChildNode(node[n]);
		}

	}

	public void	removeChildren(Node node[]) {
		for (int n=0; n<node.length; n++) {
			if (isChild(node[n]))
				node[n].remove();
		}

	}

	////////////////////////////////////////////////
	//	get child node list
	////////////////////////////////////////////////

	public GroupingNode getGroupingNodes() {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isGroupingNode())
				return (GroupingNode)node;
		}
		return null;
	}

	public GeometryNode getGeometryNode() {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isGeometryNode())
				return (GeometryNode)node;
		}
		return null;
	}

	public Node getTextureNode() {
		for (Node node = getChildNodes(); node != null; node = node.next()) {
			if (node.isTextureNode())
				return node;
		}
		return null;
	}

	public AnchorNode getAnchorNodes() {
		return (AnchorNode)getChildNodes(anchorTypeName);
	}

	public AppearanceNode getAppearanceNodes() {
		return (AppearanceNode)getChildNodes(appearanceTypeName);
	}

	public AudioClipNode getAudioClipNodes() {
		return (AudioClipNode)getChildNodes(audioClipTypeName);
	}

	public BackgroundNode getBackgroundNodes() {
		return (BackgroundNode)getChildNodes(backgroundTypeName);
	}

	public BillboardNode getBillboardNodes() {
		return (BillboardNode)getChildNodes(billboardTypeName);
	}

	public BoxNode getBoxNodes() {
		return (BoxNode)getChildNodes(boxTypeName);
	}

	public CollisionNode getCollisionNodes() {
		return (CollisionNode)getChildNodes(collisionTypeName);
	}

	public ColorNode getColorNodes() {
		return (ColorNode)getChildNodes(colorTypeName);
	}

	public ColorInterpolatorNode getColorInterpolatorNodes() {
		return (ColorInterpolatorNode)getChildNodes(colorInterpolatorTypeName);
	}

	public ConeNode getConeNodes() {
		return (ConeNode)getChildNodes(coneTypeName);
	}

	public CoordinateNode getCoordinateNodes() {
		return (CoordinateNode)getChildNodes(coordinateTypeName);
	}

	public CoordinateInterpolatorNode getCoordinateInterpolatorNodes() {
		return (CoordinateInterpolatorNode)getChildNodes(coordinateInterpolatorTypeName);
	}

	public CylinderNode getCylinderNodes() {
		return (CylinderNode)getChildNodes(cylinderTypeName);
	}

	public CylinderSensorNode getCylinderSensorNodes() {
		return (CylinderSensorNode)getChildNodes(cylinderSensorTypeName);
	}

	public DirectionalLightNode getDirectionalLightNodes() {
		return (DirectionalLightNode)getChildNodes(directionalLightTypeName);
	}

	public ElevationGridNode getElevationGridNodes() {
		return (ElevationGridNode)getChildNodes(elevationGridTypeName);
	}

	public ExtrusionNode getExtrusionNodes() {
		return (ExtrusionNode)getChildNodes(extrusionTypeName);
	}

	public FogNode getFogNodes() {
		return (FogNode)getChildNodes(fogTypeName);
	}

	public FontStyleNode getFontStyleNodes() {
		return (FontStyleNode)getChildNodes(fontStyleTypeName);
	}

	public GroupNode getGroupNodes() {
		return (GroupNode)getChildNodes(groupTypeName);
	}

	public ImageTextureNode getImageTextureNodes() {
		return (ImageTextureNode)getChildNodes(imageTextureTypeName);
	}

	public IndexedFaceSetNode getIndexedFaceSetNodes() {
		return (IndexedFaceSetNode)getChildNodes(indexedFaceSetTypeName);
	}

	public IndexedLineSetNode getIndexedLineSetNodes() {
		return (IndexedLineSetNode)getChildNodes(indexedLineSetTypeName);
	}

	public InlineNode getInlineNodes() {
		return (InlineNode)getChildNodes(inlineTypeName);
	}

	public LODNode getLODNodes() {
		return (LODNode)getChildNodes(lodTypeName);
	}

	public MaterialNode getMaterialNodes() {
		return (MaterialNode)getChildNodes(materialTypeName);
	}

	public MovieTextureNode getMovieTextureNodes() {
		return (MovieTextureNode)getChildNodes(movieTextureTypeName);
	}

	public NavigationInfoNode getNavigationInfoNodes() {
		return (NavigationInfoNode)getChildNodes(navigationInfoTypeName);
	}

	public NormalNode getNormalNodes() {
		return (NormalNode)getChildNodes(normalTypeName);
	}

	public NormalInterpolatorNode getNormalInterpolatorNodes() {
		return (NormalInterpolatorNode)getChildNodes(normalInterpolatorTypeName);
	}

	public OrientationInterpolatorNode getOrientationInterpolatorNodes() {
		return (OrientationInterpolatorNode)getChildNodes(orientationInterpolatorTypeName);
	}

	public PixelTextureNode getPixelTextureNodes() {
		return (PixelTextureNode)getChildNodes(pixelTextureTypeName);
	}

	public PlaneSensorNode getPlaneSensorNodes() {
		return (PlaneSensorNode)getChildNodes(planeSensorTypeName);
	}

	public PointLightNode getPointLightNodes() {
		return (PointLightNode)getChildNodes(pointLightTypeName);
	}

	public PointSetNode getPointSetNodes() {
		return (PointSetNode)getChildNodes(pointSetTypeName);
	}

	public PositionInterpolatorNode getPositionInterpolatorNodes() {
		return (PositionInterpolatorNode)getChildNodes(positionInterpolatorTypeName);
	}

	public ProximitySensorNode getProximitySensorNodes() {
		return (ProximitySensorNode)getChildNodes(proximitySensorTypeName);
	}

	public ScalarInterpolatorNode getScalarInterpolatorNodes() {
		return (ScalarInterpolatorNode)getChildNodes(scalarInterpolatorTypeName);
	}

	public ScriptNode getScriptNodes() {
		return (ScriptNode)getChildNodes(scriptTypeName);
	}

	public ShapeNode getShapeNodes() {
		return (ShapeNode)getChildNodes(shapeTypeName);
	}

	public SoundNode getSoundNodes() {
		return (SoundNode)getChildNodes(soundTypeName);
	}

	public SphereNode getSphereNodes() {
		return (SphereNode)getChildNodes(sphereTypeName);
	}

	public SphereSensorNode getSphereSensorNodes() {
		return (SphereSensorNode)getChildNodes(sphereSensorTypeName);
	}

	public SpotLightNode getSpotLightNodes() {
		return (SpotLightNode)getChildNodes(spotLightTypeName);
	}

	public SwitchNode getSwitchNodes() {
		return (SwitchNode)getChildNodes(switchTypeName);
	}

	public TextNode getTextNodes() {
		return (TextNode)getChildNodes(textTypeName);
	}

	public TextureCoordinateNode getTextureCoordinateNodes() {
		return (TextureCoordinateNode)getChildNodes(textureCoordinateTypeName);
	}

	public TextureTransformNode getTextureTransformNodes() {
		return (TextureTransformNode)getChildNodes(textureTransformTypeName);
	}

	public TimeSensorNode getTimeSensorNodes() {
		return (TimeSensorNode)getChildNodes(timeSensorTypeName);
	}

	public TouchSensorNode getTouchSensorNodes() {
		return (TouchSensorNode)getChildNodes(touchSensorTypeName);
	}

	public TransformNode getTransformNodes() {
		return (TransformNode)getChildNodes(transformTypeName);
	}

	public ViewpointNode getViewpointNodes() {
		return (ViewpointNode)getChildNodes(viewpointTypeName);
	}

	public VisibilitySensorNode getVisibilitySensorNodes() {
		return (VisibilitySensorNode)getChildNodes(visibilitySensorTypeName);
	}

	public WorldInfoNode getWorldInfoNodes() {
		return (WorldInfoNode)getChildNodes(worldInfoTypeName);
	}

	////////////////////////////////////////////////
	//	
	////////////////////////////////////////////////

	public boolean isNode(String nodeType) {
		String nodeString = getType();
		if (nodeString.compareTo(nodeType) == 0)
			return true;
		else
			return false;
	}

	public boolean isGroupingNode() {
		if (isAnchorNode() || isBillboardNode() || isCollisionNode() || isGroupNode() || isTransformNode() || isInlineNode() || isSwitchNode())
			return true;
		else
			return false;
	}

	public boolean isSpecialGroupNode() {
		if (isInlineNode() || isLODNode() || isSwitchNode())
			return true;
		else
			return false;
	}

	public boolean isCommonNode() {
		if (isLightNode() || isAudioClipNode() || isScriptNode() || isShapeNode() || isSoundNode() || isWorldInfoNode())
			return true;
		else
			return false;
	}

	public boolean isLightNode() {
		if (isDirectionalLightNode() || isSpotLightNode() || isPointLightNode())
			return true;
		else
			return false;
	}

	public boolean isGeometryNode() {
		if (isBoxNode() || isConeNode() || isCylinderNode() || isElevationGridNode() || isExtrusionNode() || isIndexedFaceSetNode() || isIndexedLineSetNode() || isPointSetNode() || isSphereNode() || isTextNode())
			return true;
		else
			return false;
	}

	public boolean isGeometryPropertyNode() {
		if (isColorNode() || isCoordinateNode() || isNormalNode() || isTextureCoordinateNode())
			return true;
		else
			return false;
	}

	public boolean isTextureNode() {
		if (isMovieTextureNode() || isPixelTextureNode() || isImageTextureNode() )
			return true;
		else
			return false;
	}

	public boolean isSensorNode() {
		if (isCylinderSensorNode() || isPlaneSensorNode() || isSphereSensorNode() || isProximitySensorNode() || isTimeSensorNode() || isTouchSensorNode() || isVisibilitySensorNode())
			return true;
		else
			return false;
	}

	public boolean isInterpolatorNode() {
		if (isColorInterpolatorNode() || isCoordinateInterpolatorNode() || isNormalInterpolatorNode() || isOrientationInterpolatorNode() || isPositionInterpolatorNode() || isScalarInterpolatorNode())
			return true;
		else
			return false;
	}

	public boolean isAppearanceInfoNode() {
		if (isAppearanceNode() || isFontStyleNode() || isMaterialNode() || isTextureTransformNode() || isTextureNode())
			return true;
		else
			return false;
	}

	public boolean isBindableNode() {
		if (isBackgroundNode() || isFogNode() || isNavigationInfoNode() || isViewpointNode())
			return true;
		else
			return false;
	}


	public boolean isRootNode() {
		return isNode(rootTypeName);
	}
	
	public boolean isAnchorNode() {
		return isNode(anchorTypeName);
	}

	public boolean isAppearanceNode() {
		return isNode(appearanceTypeName);
	}

	public boolean isAudioClipNode() {
		return isNode(audioClipTypeName);
	}

	public boolean isBackgroundNode() {
		return isNode(backgroundTypeName);
	}

	public boolean isBillboardNode() {
		return isNode(billboardTypeName);
	}

	public boolean isBoxNode() {
		return isNode(boxTypeName);
	}

	public boolean isCollisionNode() {
		return isNode(collisionTypeName);
	}

	public boolean isColorNode() {
		return isNode(colorTypeName);
	}

	public boolean isColorInterpolatorNode() {
		return isNode(colorInterpolatorTypeName);
	}

	public boolean isConeNode() {
		return isNode(coneTypeName);
	}

	public boolean isCoordinateNode() {
		return isNode(coordinateTypeName);
	}

	public boolean isCoordinateInterpolatorNode() {
		return isNode(coordinateInterpolatorTypeName);
	}

	public boolean isCylinderNode() {
		return isNode(cylinderTypeName);
	}

	public boolean isCylinderSensorNode() {
		return isNode(cylinderSensorTypeName);
	}

	public boolean isDirectionalLightNode() {
		return isNode(directionalLightTypeName);
	}

	public boolean isElevationGridNode() {
		return isNode(elevationGridTypeName);
	}

	public boolean isExtrusionNode() {
		return isNode(extrusionTypeName);
	}

	public boolean isFogNode() {
		return isNode(fogTypeName);
	}

	public boolean isFontStyleNode() {
		return isNode(fontStyleTypeName);
	}

	public boolean isGroupNode() {
		return isNode(groupTypeName);
	}

	public boolean isImageTextureNode() {
		return isNode(imageTextureTypeName);
	}

	public boolean isIndexedFaceSetNode() {
		return isNode(indexedFaceSetTypeName);
	}

	public boolean isIndexedLineSetNode() {
		return isNode(indexedLineSetTypeName);
	}

	public boolean isInlineNode() {
		return isNode(inlineTypeName);
	}

	public boolean isLODNode() {
		return isNode(lodTypeName);
	}

	public boolean isMaterialNode() {
		return isNode(materialTypeName);
	}

	public boolean isMovieTextureNode() {
		return isNode(movieTextureTypeName);
	}

	public boolean isNavigationInfoNode() {
		return isNode(navigationInfoTypeName);
	}

	public boolean isNormalNode() {
		return isNode(normalTypeName);
	}

	public boolean isNormalInterpolatorNode() {
		return isNode(normalInterpolatorTypeName);
	}

	public boolean isOrientationInterpolatorNode() {
		return isNode(orientationInterpolatorTypeName);
	}

	public boolean isPixelTextureNode() {
		return isNode(pixelTextureTypeName);
	}

	public boolean isPlaneSensorNode() {
		return isNode(planeSensorTypeName);
	}

	public boolean isPointLightNode() {
		return isNode(pointLightTypeName);
	}

	public boolean isPointSetNode() {
		return isNode(pointSetTypeName);
	}

	public boolean isPositionInterpolatorNode() {
		return isNode(positionInterpolatorTypeName);
	}

	public boolean isProximitySensorNode() {
		return isNode(proximitySensorTypeName);
	}

	public boolean isScalarInterpolatorNode() {
		return isNode(scalarInterpolatorTypeName);
	}

	public boolean isScriptNode() {
		return isNode(scriptTypeName);
	}

	public boolean isShapeNode() {
		return isNode(shapeTypeName);
	}

	public boolean isSoundNode() {
		return isNode(soundTypeName);
	}

	public boolean isSphereNode() {
		return isNode(sphereTypeName);
	}

	public boolean isSphereSensorNode() {
		return isNode(sphereSensorTypeName);
	}

	public boolean isSpotLightNode() {
		return isNode(spotLightTypeName);
	}

	public boolean isSwitchNode() {
		return isNode(switchTypeName);
	}

	public boolean isTextNode() {
		return isNode(textTypeName);
	}

	public boolean isTextureCoordinateNode() {
		return isNode(textureCoordinateTypeName);
	}

	public boolean isTextureTransformNode() {
		return isNode(textureTransformTypeName);
	}

	public boolean isTimeSensorNode() {
		return isNode(timeSensorTypeName);
	}

	public boolean isTouchSensorNode() {
		return isNode(touchSensorTypeName);
	}

	public boolean isTransformNode() {
		return isNode(transformTypeName);
	}

	public boolean isViewpointNode() {
		return isNode(viewpointTypeName);
	}

	public boolean isVisibilitySensorNode() {
		return isNode(visibilitySensorTypeName);
	}

	public boolean isWorldInfoNode() {
		return isNode(worldInfoTypeName);
	}

	////////////////////////////////////////////////
	//	output
	////////////////////////////////////////////////

	public String getIndentLevelString(int nIndentLevel) {
		char indentString[] = new char[nIndentLevel];
		for (int n=0; n<nIndentLevel; n++)
			indentString[n] = '\t' ;
		return new String(indentString);
	}

	public void outputHead(PrintWriter printStream, String indentString) {
		String nodeName = getName();
		if (nodeName != null && 0 < nodeName.length())
			printStream.println(indentString + "DEF " + nodeName + " " + getType() + " {");
		else
			printStream.println(indentString +  getType() + " {");
	}

	public void outputTail(PrintWriter printStream, String indentString) {
		printStream.println(indentString + "}");
	}

	////////////////////////////////////////////////
	//	output
	////////////////////////////////////////////////

	abstract public void outputContext(PrintWriter printStream, String indentString);

	public void output(PrintWriter printStream, int indentLevet) {

		String indentString = getIndentLevelString(indentLevet);

		if (isInstanceNode() == false) {

			outputHead(printStream, indentString);
			outputContext(printStream, indentString);
	
			if (!isElevationGridNode() && !isShapeNode() && !isSoundNode() && !isPointSetNode() && !isIndexedFaceSetNode() && 
				!isIndexedLineSetNode() && !isTextNode() && !isAppearanceNode() && !isScriptNode()) {
				if (getChildNodes() != null) {
					if (isLODNode()) 
						printStream.println(indentString + "\tlevel [");
					else if (isSwitchNode()) 
						printStream.println(indentString + "\tchoice [");
					else
						printStream.println(indentString + "\tchildren [");
			
					for (Node cnode = getChildNodes(); cnode != null; cnode = cnode.next()) {
						cnode.output(printStream, indentLevet+2);
					}
			
					printStream.println(indentString + "\t]");
				}
			}
			outputTail(printStream, indentString);
		}
		else
			printStream.println(indentString + "USE " + getName());
	}

	public void save(FileOutputStream outputStream){
		PrintWriter pr = new PrintWriter(outputStream);
		output(pr, 0);
		pr.close();
	}

	public void save(String filename) {
		try {
			FileOutputStream outputStream = new FileOutputStream(filename);
			save(outputStream);
			outputStream.close();
		}
		catch (IOException e) {
			System.out.println("Couldn't open the file (" + filename + ")");
		}
	}

	////////////////////////////////////////////////
	//	getTransformMatrix
	////////////////////////////////////////////////

	public void getTransformMatrix(SFMatrix mxOut) {
		mxOut.init();
		for (Node node=this; node != null ; node=node.getParentNode()) {
			if (node.isTransformNode()) {
				SFMatrix mxTransform = ((TransformNode)node).getSFMatrix();
				mxTransform.add(mxOut);
				mxOut.setValue(mxTransform);
			}
			else if (node.isBillboardNode()) {
				SFMatrix mxBillboard = ((BillboardNode)node).getSFMatrix();
				mxBillboard.add(mxOut);
				mxOut.setValue(mxBillboard);
			}
		}
	}

	public SFMatrix getTransformMatrix() {
		SFMatrix	mx = new SFMatrix();
		getTransformMatrix(mx);
		return mx;
	}

	public void getTransformMatrix(float value[][]) {
		SFMatrix	mx = new SFMatrix();
		getTransformMatrix(mx);
		mx.getValue(value);
	}

	////////////////////////////////////////////////
	//	SceneGraph
	////////////////////////////////////////////////

	public void setSceneGraph(SceneGraph sceneGraph) {
		mSceneGraph = sceneGraph;
		for (Node node = getChildNodes(); node != null; node = node.next())
			node.setSceneGraph(sceneGraph);
	}
	
	public SceneGraph getSceneGraph() {
		return mSceneGraph;
	}

	////////////////////////////////////////////////
	//	Route
	////////////////////////////////////////////////

	public void sendEvent(Field eventOutField) {
		SceneGraph sg = getSceneGraph();
		if (sg != null)
			sg.updateRoute(this, eventOutField);
	}

	////////////////////////////////////////////////
	//	Initialized
	////////////////////////////////////////////////

	public void setInitializationFlag(boolean flag) {
		mInitializationFlag = flag; 
	}

	public boolean isInitialized() {
		return mInitializationFlag; 
	}

	////////////////////////////////////////////////
	//	user data
	////////////////////////////////////////////////

	public void setData(Object data) {
		mUserData = data;
	}

	public Object getData() {
		return mUserData;
	}

	////////////////////////////////////////////////
	//	Instance node
	////////////////////////////////////////////////

	public boolean isInstanceNode() {
		return (getReferenceNode() != null ? true : false);
	}

	public void setReferenceNodeMember(Node node) {
		if (node == null)
			return;
			
		mName					= node.mName;

		mField					= node.mField;
		mPrivateField			= node.mPrivateField;
		mExposedField			= node.mExposedField;
		mEventInField			= node.mEventInField;
		mEventOutField			= node.mEventOutField;
		mInitializationFlag		= node.mInitializationFlag;
		mObject					= node.mObject;
		mThreadObject			= node.mThreadObject;
	}
	
	public void setReferenceNode(Node node) {
		mReferenceNode = node;
	}
	
	public Node getReferenceNode() {
		return mReferenceNode;
	}

	public void setAsInstanceNode(Node node) {
		setReferenceNode(node);
		setReferenceNodeMember(node);
	}
	
	public Node createInstanceNode() {
		Node instanceNode = null;
		
		if (isAnchorNode())
			instanceNode = new AnchorNode();
		else if (isAppearanceNode()) 
			instanceNode = new AppearanceNode();
		else if (isAudioClipNode())
			instanceNode = new AudioClipNode();
		else if (isBackgroundNode())
			instanceNode = new BackgroundNode();
		else if (isBillboardNode())
			instanceNode = new BillboardNode();
		else if (isBoxNode())
			instanceNode = new BoxNode();
		else if (isCollisionNode())
			instanceNode = new CollisionNode();
		else if (isColorNode())
			instanceNode = new ColorNode();
		else if (isColorInterpolatorNode())
			instanceNode = new ColorInterpolatorNode();
		else if (isConeNode())
			instanceNode = new ConeNode();
		else if (isCoordinateNode())
			instanceNode = new CoordinateNode();
		else if (isCoordinateInterpolatorNode())
			instanceNode = new CoordinateInterpolatorNode();
		else if (isCylinderNode())
			instanceNode = new CylinderNode();
		else if (isCylinderSensorNode())
			instanceNode = new CylinderSensorNode();
		else if (isDirectionalLightNode())
			instanceNode = new DirectionalLightNode();
		else if (isElevationGridNode())
			instanceNode = new ElevationGridNode();
		else if (isExtrusionNode())
			instanceNode = new ExtrusionNode();
		else if (isFogNode())
			instanceNode = new FogNode();
		else if (isFontStyleNode())
			instanceNode = new FontStyleNode();
		else if (isGroupNode())
			instanceNode = new GroupNode();
		else if (isImageTextureNode())
			instanceNode = new ImageTextureNode();
		else if (isIndexedFaceSetNode())
			instanceNode = new IndexedFaceSetNode();
		else if (isIndexedLineSetNode()) 
			instanceNode = new IndexedLineSetNode();
		else if (isInlineNode()) 
			instanceNode = new InlineNode();
		else if (isLODNode())
			instanceNode = new LODNode();
		else if (isMaterialNode())
			instanceNode = new MaterialNode();
		else if (isMovieTextureNode())
			instanceNode = new MovieTextureNode();
		else if (isNavigationInfoNode())
			instanceNode = new NavigationInfoNode();
		else if (isNormalNode())
			instanceNode = new NormalNode();
		else if (isNormalInterpolatorNode())
			instanceNode = new NormalInterpolatorNode();
		else if (isOrientationInterpolatorNode())
			instanceNode = new OrientationInterpolatorNode();
		else if (isPixelTextureNode())
			instanceNode = new PixelTextureNode();
		else if (isPlaneSensorNode())
			instanceNode = new PlaneSensorNode();
		else if (isPointLightNode())
			instanceNode = new PointLightNode();
		else if (isPointSetNode())
			instanceNode = new PointSetNode();
		else if (isPositionInterpolatorNode())
			instanceNode = new PositionInterpolatorNode();
		else if (isProximitySensorNode())
			instanceNode = new ProximitySensorNode();
		else if (isScalarInterpolatorNode())
			instanceNode = new ScalarInterpolatorNode();
		else if (isScriptNode())
			instanceNode = new ScriptNode();
		else if (isShapeNode())
			instanceNode = new ShapeNode();
		else if (isSoundNode())
			instanceNode = new SoundNode();
		else if (isSphereNode())
			instanceNode = new SphereNode();
		else if (isSphereSensorNode())
			instanceNode = new SphereSensorNode();
		else if (isSpotLightNode())
			instanceNode = new SpotLightNode();
		else if (isSwitchNode())
			instanceNode = new SwitchNode();
		else if (isTextNode())
			instanceNode = new TextNode();
		else if (isTextureCoordinateNode())
			instanceNode = new TextureCoordinateNode();
		else if (isTextureTransformNode())
			instanceNode = new TextureTransformNode();
		else if (isTimeSensorNode())
			instanceNode = new TimeSensorNode();
		else if (isTouchSensorNode())
			instanceNode = new TouchSensorNode();
		else if (isTransformNode())
			instanceNode = new TransformNode();
		else if (isViewpointNode())
			instanceNode = new ViewpointNode();
		else if (isVisibilitySensorNode())
			instanceNode = new VisibilitySensorNode();
		else if (isWorldInfoNode())
			instanceNode = new WorldInfoNode();
			
		if (instanceNode != null) {
			instanceNode.setAsInstanceNode(this);
			for (Node cnode=getChildNodes(); cnode != null; cnode = cnode.next()) {
				Node childInstanceNode = cnode.createInstanceNode();
				instanceNode.addChildNode(childInstanceNode);
			}
		}		
		else
			System.out.println("Node::createInstanceNode : this = " + this + ", instanceNode = null");
		
		return instanceNode;
	}

	////////////////////////////////////////////////
	//	Node Object
	////////////////////////////////////////////////
	
	public void setObject(NodeObject object) {
		mObject = object;
	}
	
	public NodeObject getObject() {
		return mObject;
	}
	
	public boolean hasObject() {
		return (mObject != null ? true : false);
	}
	
	public boolean initializeObject() {
		if (hasObject() == true)
			return mObject.initialize(this);
		return false;
	}
	
	public boolean uninitializeObject() {
		if (hasObject() == true)
			return mObject.uninitialize(this);
		return false;
	}
	
	public boolean updateObject() {
		if (hasObject() == true)
			return mObject.update(this);
		return false;
	}
	
	public boolean addObject() {
		Debug.message("Node::addObject = " + this + ", " + mObject);
		if (isRootNode() == true) {
			Debug.warning("\tThis node is a root node !!");
			return false;
		}
		if (hasObject() == true)
			return mObject.add(this);
		return false;
	}

	public boolean removeObject() {
		Debug.message("Node::addObject = " + this + ", " + mObject);
		if (isRootNode() == true) {
			Debug.warning("\tThis node is a root node !!");
			return false;
		}
		if (hasObject() == true)
			return mObject.remove(this);
		return false;
	}

	////////////////////////////////////////////////
	//	Runnable
	////////////////////////////////////////////////
	
	public void setRunnable(boolean value) {
		mRunnable = value;
	}
	
	public boolean isRunnable() {
		if (isInstanceNode() == true)
			return false;
		return mRunnable;
	}
	
	public void setRunnableType(int type) {
		mRunnableType = type;
	}

	public int getRunnableType() {
		return mRunnableType;
	}

	public void setRunnableIntervalTime(int time) {
		mRunnableIntervalTime = time;
	}

	public int getRunnableIntervalTime() {
		return mRunnableIntervalTime;
	}
				
	public void setThreadObject(Thread obj) {
		mThreadObject = obj;
	}

	public Thread getThreadObject() {
		return mThreadObject;
	}

	////////////////////////////////////////////////
	//	Thread
	////////////////////////////////////////////////

	public void run() {
		while (true) {
			update();
			updateObject();
			Thread threadObject = getThreadObject();
			if (threadObject != null) { 
//				threadObject.yield();
				try {
					threadObject.sleep(getRunnableIntervalTime());
				} catch (InterruptedException e) {}
			}
		}
	}
	
	public void start() {
		Thread threadObject = getThreadObject();
		if (threadObject == null) {
			threadObject = new Thread(this);
			setThreadObject(threadObject);
			threadObject.start();
		}
	}
	
	public void stop() {
		Thread threadObject = getThreadObject();
		if (threadObject != null) {
			//threadObject.destroy();
			threadObject.stop();
			setThreadObject(null);
		}
	}
	
}